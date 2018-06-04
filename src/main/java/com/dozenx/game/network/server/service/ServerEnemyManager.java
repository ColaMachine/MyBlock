package com.dozenx.game.network.server.service;

import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;

import com.dozenx.game.engine.PhysicsEngine;
import com.dozenx.game.engine.command.AttackCmd;
import com.dozenx.game.engine.command.AttackType;
import com.dozenx.game.engine.command.PosCmd;
import com.dozenx.game.engine.command.WalkCmd2;
import com.dozenx.game.engine.live.state.IdleState;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.MathUtil;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.util.*;

/**
 * Created by luying on 17/2/26.
 */
public class ServerEnemyManager implements Runnable {
   // public Map<Integer, Integer> alreadyMap = new HashMap<>();
    UserService userService;
    EnemyService enemyService;
    long lastUpdateTime = 0;

    private ServerContext serverContext;
    private ChunkProvider chunkProvider;

    public ServerEnemyManager(ServerContext serverContext) {
        this.serverContext = serverContext;
        this.userService = (UserService) serverContext.getService(UserService.class);
        this.enemyService = (EnemyService) serverContext.getService(EnemyService.class);

        this.chunkProvider = serverContext.chunkProvider;
    }

    @Override
    //首先要确认的是服务器端的怪物逻辑是多少时间一个轮询的
    //200毫秒一次行不行
    //各种生物执行update的地方
    //各种怪物执行思考和行动的地方
    public void run() {
        while (true) {
            try {


               // enemyServerLoop();
                synEnemyPos();  //每隔200ms同步变化的位置给用户
                //this.findThing();
                // this.doSomeThing();
                // this.changeDir();
                //this.moveOrAttack();
                //this.testCmd();
                try {
                    Thread.sleep(400);//1秒同步一次
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void synEnemyPos() {
        for (LivingThingBean enemy : enemyService.getAllEnemies()) {
            if (!enemy.isDied() && enemy.updateTime > lastUpdateTime) {
                lastUpdateTime = TimeUtil.getNowMills();

                serverContext.broadCast(new PosCmd(enemy.getInfo()).toBytes());
                //serverContext.broadCast(new WalkCmd2(enemy.getPosition(),enemy.getPosition(),enemy.getId()).toBytes());
            }
        }
    }

    //怪物逻辑
    public void enemyServerLoop() {
        for (LivingThingBean enemy : enemyService.getAllEnemies()) {
            if (!enemy.isDied()) {//如果自身是有效单位 没有死

                if (enemy.getTargetId() > 0) {//并且是有目标
                    if (checkEnemyTarget(enemy))//释放无用target 补全缺少target
                    {//追击或者攻击
                        moveOrAttack(enemy);
                    }


                } else {//暂时没有目标
                    //if(enemy.getExecutor().getCurrentState() instanceof  IdleState){
                    //找寻目标

                    //

                    findTarget(enemy);
                }
                //怪物
//                    if(enemy.getTarget() != null && enemy.getDest()==null && enemy.getFinalDest()!=null){
//                        if( enemy.isBlock() ){//如果立马取消掉dest
//                            if (enemy.routes.size() == 0&&enemy.routes == null || enemy.routes.size() == 0) {
//                                
//                                AStar astar =new  AStar();
//                               // astar.map
//                                this.pathRoute2(enemy.getPosition(), enemy.getFinalDest(), enemy);
//                                enemy.setFinalDest(null);
//                            }else{
//
//
//                            }
//                        }else {
//                            //如果当前有pathroute的结果
//                            if(enemy.getDest()==null){
//                                enemy.setDest(enemy.getFinalDest());
//                            }
//                           
//                        }
//                        //如果长时间由于卡住过不去 就动用path寻路
//                      /*  if (enemy.routes == null || enemy.routes.size() == 0) {
//                            this.pathRoute(enemy.getPosition(), enemy.getFinalDest(), enemy);
//                            enemy.setFinalDest(null);
//                        }else{
//
//
//                        }*/
//                    }
                    //}

                enemy.getExecutor().getCurrentState().update();

                CoreRegistry.get(PhysicsEngine.class).checkIsDrop(enemy);
               CoreRegistry.get(PhysicsEngine.class).gravitation(enemy);
            }
        }
        for (LivingThingBean player : userService.getAllOnlinePlayer()) {
            if (!player.isDied()) {//如果自身是有效单位

                /*if(enemy.getTargetId() > 0){//并且是有目标
                    if(checkEnemyTarget(enemy))//释放无用target 补全缺少target
                    {
                        moveOrAttack(enemy);
                    }
                    //追击或者攻击

                }else{//暂时没有目标
                    if(enemy.getExecutor().getCurrentState() instanceof  IdleState){
                        //找寻目标

                        //findTarget(enemy);
                    }
                }*/
                //player.getExecutor().getCurrentState().update();

            }
        }
    }

    public boolean checkEnemyTarget(LivingThingBean livingThingBean) {
        LivingThingBean player = userService.getOnlinePlayerById(livingThingBean.getTargetId());
        if (player == null) { //如果没有目标
            livingThingBean.setTargetId(0);
            livingThingBean.setTarget(null);
            return false;
        } else if (GL_Vector.length(GL_Vector.sub(livingThingBean.getPosition(), player.getPosition())) > 100) {//如果距离太远了 就失去目标
            if (TimeUtil.getNowMills() - livingThingBean.getLastHurtTime() > 10 * 1000) {//如果上次伤害还没超过多少时间

                livingThingBean.setTargetId(0);
                livingThingBean.setTarget(null);
            }
            livingThingBean.setTarget(player);
            //enemy.setTarget(player);
            return false;
        } else {
            livingThingBean.setTarget(player);
            return true;
        }

    }
/*
    public void findThing(){
        for(LivingThingBean enemy : enemyService.getAllEnemies()) {
            if (enemy.getTargetId() >0) {
                //检查举例 如果举例过远 放弃追逐
                LivingThingBean player= userService.getOnlinePlayerById(enemy.getTargetId());
                if(player==null){
                    enemy.setTargetId(0);
                }else
                if (GL_Vector.length(GL_Vector.sub(player.getPosition(), player.getPosition())) >15 ) {
                    if(TimeUtil.getNowMills()-enemy.getLastHurtTime()<10*1000) {//如果上次伤害还没超过多少时间

                        enemy.setTargetId(0);
                    }
                    //enemy.setTarget(player);
                }
            }

            for(LivingThingBean player: userService.getAllOnlinePlayer()){


                    if (GL_Vector.length(GL_Vector.sub(enemy.getPosition(), player.getPosition())) < 5) {
                        enemy.setTargetId(player.getId());
                        //enemy.setTarget(player);
                    }

            }

        }
    }*/
/*
    public void doSomeThing(){
        for(LivingThingBean enemy : enemyService.getAllEnemies()) {
           enemy.doSomeThing(serverContext);

        }
    }*/

    public void findTarget(LivingThingBean enemy) {

        for (LivingThingBean player : userService.getAllOnlinePlayer()) {

            if (player != null)
                if (GL_Vector.length(GL_Vector.sub(enemy.getPosition(), player.getPosition())) < 5) {
                    enemy.setTarget(player);
                    //enemy.setTarget(player);
                }

        }

    }
/*
    public void attack(LivingThingBean source ,LivingThingBean target){
           serverContext.getAllHandlerMap().get(CmdType.ATTACK).handler(new GameServerRequest(new AttackCmd(source.getId(), AttackType.ARROW,target.getId()),null),new GameServerResponse());

    }*/

    public void testCmd() {
        for (LivingThingBean enemy : enemyService.getAllEnemies()) {


            if (!enemy.isDied() && enemy.getTargetId() > 0) {

                if (enemy.getExecutor().getCurrentState() instanceof IdleState) {
                    LivingThingBean player = userService.getOnlinePlayerById(enemy.getTargetId());
                    if (player != null) {
                        WalkCmd2 walkCmd2 = new WalkCmd2(enemy.getPosition(), player.getPosition(), enemy.getId());
                        serverContext.broadCast(walkCmd2.toBytes());
                        enemy.getExecutor().receive(walkCmd2);
                    }
                }


            }
        }
    }

    /**
     * move or attack
     *
     * @param enemy
     */
    public void moveOrAttack(LivingThingBean enemy) {


        GL_Vector direction = GL_Vector.sub(enemy.getTarget().getPosition(), enemy.getPosition());
        //enemy.setWalkDir(direction);

        float length = GL_Vector.length(direction);
        if (length < 2) {
            if(TimeUtil.getNowMills()-enemy.getLastAttackTime()>1000){
                AttackCmd attackCmd = new AttackCmd(enemy.getId(), AttackType.KAN, enemy.getTargetId());
                serverContext.broadCast(attackCmd.toBytes());
                enemy.getExecutor().receive(attackCmd);
                enemy.setLastAttackTime(TimeUtil.getNowMills());
            }
           
            
            //开始攻击
                  /* enemy.
                    attack(enemy,enemy.getTarget());*/
            //livingThing.nextPosition=null;
        } else if(length<50){
            // this.getAnimationManager().apply(livingThing.bodyComponent,"walkerFoward");
                   /*GL_Vector newPosition = GL_Vector.add(enemy.getPosition(),GL_Vector.multiply(direction.normalize(),1*1));
                    enemy .setPosition(newPosition);
                   serverContext.broadCast(new PosCmd(enemy.getInfo()).toBytes());

*/                      //这段代码会导致一个问题 怪物到了目的地后才会再计算下一个目的地
            //每隔一段时间就修正怪物的walkstate
            
            if(enemy.getDest()==null && enemy.getFinalDest()==null){
                LivingThingBean player = userService.getOnlinePlayerById(enemy.getTargetId());
                if (player != null) {
                    //移动到制定位置设置目标

                    //可达性分析

                    
                    enemy.setFinalDest(player.getPosition());
                   // enemy.setDest(player.getPosition());
                    //
                    // enemy.setBodyAngle( GL_Vector.getAnagleFromXZVectory(direction));
                }
                   
                  
            }else{
                if( enemy.isBlock() ){//如果立马取消掉dest
                    if (enemy.routes.size() == 0&&enemy.routes == null || enemy.routes.size() == 0) {
                        
                        //AStar astar =new  AStar();
                       // astar.map
                        this.pathRoute2(enemy.getPosition(), enemy.getFinalDest(), enemy);
                        enemy.setBlock(false);
                        //enemy.setFinalDest(null);
                    }else{
                        

                    }
                }else {
                    //如果当前有pathroute的结果
                    if(enemy.getDest()==null && enemy.routes.size()==0){
                        enemy.setDest(enemy.getFinalDest());
                    }
                }
            }
           

//                       if(enemy.getExecutor().getCurrentState() instanceof IdleState ){//就是说还没开始追击
//
//                           if(player!= null) {
//
//                               //WalkCmd2 walkCmd2 = new WalkCmd2(enemy.getPosition(), player.getPosition(), enemy.getId());
//                               //serverContext.broadCast(walkCmd2.toBytes()); 这里不再用walkcmd2去同步怪物的移动了
//                               //enemy.getExecutor().receive(walkCmd2);
//
//
//                               /*ChaseCmd chaseCmd = new ChaseCmd(enemy.getId(), enemy.getTargetId());
//                               serverContext.broadCast(chaseCmd.toBytes());
//                               enemy.getExecutor().receive(chaseCmd);*/
//                           }
//                       }


        }else{
            enemy.setTarget(null);
            enemy.setDest(null);
            enemy.setFinalDest(null);
        }

    }
    public void  pathRoute2(GL_Vector from, GL_Vector to, LivingThingBean livingThingBean) {
        int xDistance = (int) (to.x - from.x);//caculate the x distance
        int zDistance = (int) (to.z - from.z);//caculate the z distance
        int width =40;
        int height=40;
        int map[][] =new int[width][height];
        int srcX = (int)from.x;
        int srcZ= (int)from.z;
        int dstX = (int )to.x;
        int dstZ =(int )to.z;
        int minX = srcX-10;
        int minZ = srcZ-10;
      
       for(int x =0;x<width;x++){
           for(int z =0;z<height;z++){
               int offsetX =MathUtil.getOffesetChunk(x+srcX-10);
               int offsetZ = MathUtil.getOffesetChunk(z+srcZ-10);
               Chunk chunk = chunkProvider.getChunk(MathUtil.getBelongChunkInt(x), 0, MathUtil.getBelongChunkInt(z));
               IBlock block = chunk.getBlock(offsetX, 1, offsetZ);


               if (block == null || block.getId() == 0 || block.isPenetrate()) { //说明这边的block也是空的 it's empt
                   
               }else{
                   map[x][z]=1;
               }
               
               
              
           }
       }
     
        
        
        AStar astar =new AStar();
        astar.map=map;
        List<GL_Vector> routeList = new ArrayList<>();
        CloseSet p = astar.start(srcX-minX, srcZ-minZ, dstX-minX, dstZ-minZ);
        int step =0;
        if(p!=null){
            while (p.from!=null)
            {routeList.add(new GL_Vector(p.cur.x+minX,1,p.cur.y+minZ));
                
               System.out.printf("（%d，%d）→\n", p.cur.x+minX,p.cur.y+minZ);
                p = p.from;
                step++;
            }
           
            livingThingBean.routes.add(new GL_Vector((int)livingThingBean.getPosition().x+0.5f,(int)livingThingBean.getPosition().y,(int)livingThingBean.getPosition().z+0.5f));
            livingThingBean.routes.addAll(routeList);

            livingThingBean.setFinalDest(null);
            GL_Vector point = routeList.get(0);
            
           livingThingBean.setDest(point);
        }
    }
    public List<GL_Vector> pathRoute(GL_Vector from, GL_Vector to, LivingThingBean livingThingBean) {
        //首先判断from 下面有没有基础方块
        List<GL_Vector> routeList = new ArrayList<>();
        //first we found if from was on a block
       /* Block block = chunkProvider.getBlockAt((int) from.x, (int) from.y-1, (int) from.z);
        if (block != null && block.getId() > 0) {*/
            //x

            int xDistance = (int) (to.x - from.x);//caculate the x distance
            int zDistance = (int) (to.z - from.z);//caculate the z distance
            int xDir = 1;
            if (xDistance < 0) {
                xDir = -1;
            }
            int zDir = 1;
            if (zDistance < 0) {
                zDir = -1;
            }
            int xAbsDistance = Math.abs(xDistance);
            int zAbsDistance = Math.abs(zDistance);

            int fromX = (int) from.x;
            int fromY = (int) from.y;
            int fromZ = (int) from.z;

            int toX = (int) to.x;
            int toY = (int) to.y;
            int toZ = (int) to.z;

            int nowX = fromX;
            int nowY = fromY;
            int nowZ = fromZ;
            //  for(int x =0 ;x<xAbsDistance;x++){
            //   for(int z =0 ;z<zAbsDistance;z++){

            GL_Vector now = new GL_Vector(xAbsDistance, from.y, zAbsDistance);
             Map<Integer, Integer> alreadyMap = new HashMap<>();

            int centerX = (fromX+toX)/2;
            int centerZ = (fromZ+toZ)/2;
            int width = Math.abs(fromX-toX);
            int height = Math.abs(fromZ-toZ);
            //centerX-width,centerZ-height,centerX+width,centerZ+height
            if (diguiTest(fromX, fromY, fromZ, nowX, nowY, nowZ, toX, toY, toZ, xDir, zDir,alreadyMap,routeList,1,1)) {


                livingThingBean.routes.clear();
                Collections.reverse(routeList);
                livingThingBean.routes.add(new GL_Vector((int)livingThingBean.getPosition().x+0.5f,(int)livingThingBean.getPosition().y,(int)livingThingBean.getPosition().z+0.5f));
                livingThingBean.routes.addAll(routeList);

                livingThingBean.setFinalDest(null);
                GL_Vector point = routeList.get(0);
                
               livingThingBean.setDest(point);
                //先判断左

            } else {
                //can't find way;
                livingThingBean.routes.clear();
                livingThingBean.setDest(null);
            }
            // }
            //}
            //z
       // }
        return  routeList;
    }

    //return ture means find the way
    public boolean diguiTest(int fromX, int fromY, int fromZ, int nowX, int nowY, int nowZ, int toX, int toY, int toZ, int xDir, int zDir, Map<Integer, Integer> alreadyMap,List<GL_Vector> routeList ,int lastDir,int level) {
        //先左 前 右边
        level++;
        if(level>50){//if digui > 100 times then cancel it
            return false;
        }
        if(nowY==2){
//            LogUtil.println("why");
        }
        if (alreadyMap.get(nowX * 1000 * 1000 + nowZ * 1000 + nowY) != null) {
            return false;
        }
        alreadyMap.put(nowX * 1000 * 1000 + nowZ * 1000 + nowY, 1);

        //判断当前点是否已经判断过了
        //x max dir
        //x++
        //当前y的层高
        //check xMax
        if(nowX==toX && nowZ==toZ){
            //y 先不判断了 原来是要 && nowY == toY

            routeList.add(new GL_Vector(nowX+0.5f, nowY, nowZ+0.5f));
            return true;
        }
        int dir=3;//0 is x 1 is z
        //优先靠近原则
        //
        boolean xPrioprity =true;
        //优先维度 也就是将制定 6个方向的先后测试循序 顺便把来的方向排到最后 0 z+ 1x+ 2z- 3x- 4y+ 5y-
        int dirArr[]  =new int[4];

        if(Math.abs(toX-nowX) > Math.abs(toZ-nowZ)){
            //优先x

            if(toX>nowX){
                dirArr[0]=1;

            }else{
                dirArr[0]=3;
            }
            if(toZ>nowZ){
                dirArr[1]=0;
            }else{
                dirArr[1]=2;
            }
            if(toZ>nowZ){
                dirArr[2]=2;
            }else{
                dirArr[2]=0;
            }
            if(toX>nowX){
                dirArr[3]=3;

            }else{
                dirArr[3]=1;
            }
        }else{
            if(toZ>nowZ){
                dirArr[0]=0;
            }else{
                dirArr[0]=2;
            }
            if(toX>nowX){
                dirArr[1]=1;

            }else{
                dirArr[1]=3;
            }
            if(toX>nowX){
                dirArr[2]=3;

            }else{
                dirArr[2]=1;
            }
            if(toZ>nowZ){
                dirArr[3]=2;
            }else{
                dirArr[3]=0;
            }
        }

        for(int i = 0 ;i<dirArr.length;i++){
            int nextX =nowX,nextY=nowY,nextZ=nowZ;

            if(dirArr[i]==0){
                nextZ++;
            }else
            if(dirArr[i]==1){
                nextX++;
            }else
            if(dirArr[i]==2){
                nextZ--;
            }else
            if(dirArr[i]==3){
                nextX--;
            }

            int luocha = luocha(nextX, nextY, nextZ);
            if (luocha == -1 || luocha == 0 || luocha == 1 || luocha == -2) {
                if(luocha==0){
                    dir=dirArr[i];
                }

                if (diguiTest(fromX, fromY, fromZ, nextX, nowY + luocha, nextZ, toX, toY, toZ, xDir, zDir,alreadyMap ,routeList,dir,level)) {
                   // if(lastDir != dir) {
                        //如果这次和上次 还有这次和下次都是在同一个方向上 本次就不需要记录节点
                        routeList.add(new GL_Vector(nowX+0.5f, nowY, nowZ+0.5f));
                   // }
                    return true;
                }
            }
        }
        return false;
    }

   /* public boolean doWithXZ(int fromX, int fromY, int fromZ, int nowX, int nowY, int nowZ, int toX, int toY, int toZ, int xDir, int zDir, Map<Integer, Integer> alreadyMap,List<GL_Vector> routeList ,int lastDir,int nowDir){
        int nextY =nowY,nextX = nowX,nextZ=nowZ;
        int[] dirAry = new int[6];


        if(nowDir==0){
            nextZ +=zDir;
        }
        int luocha = luocha(nowX+xDir, nowY, nowZ);
        if (luocha == -1 || luocha == 0 || luocha == 1) {
            if(luocha==0){
                nowDir=1;
            }
            if (diguiTest(fromX, fromY, fromZ, nowX + xDir, nowY + luocha, nowZ, toX, toY, toZ, xDir, zDir,alreadyMap ,routeList,nowDir,level)) {
                if(lastDir != nowDir) {
                    //如果这次和上次 还有这次和下次都是在同一个方向上 本次就不需要记录节点
                    routeList.add(new GL_Vector(nowX, nowY, nowZ));
                }
                return true;
            }
        }
        return false;
    }*/
    public static void main(String args[]){
        System.out.println(MathUtil.getOffesetChunk(15));
    }
    /**
     * caculate the given y and the actual first block in given x,z 's minus
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public int luocha(int x, int y, int z) {
        // Block block = chunkProvider.getBlockAt(thereX,thereY,thereZ);
        if(x==15 && z==0){
           // LogUtil.println("why");
        }
        int offsetX =MathUtil.getOffesetChunk(x);
        int offsetZ = MathUtil.getOffesetChunk(z);
        Chunk chunk = chunkProvider.getChunk(MathUtil.getBelongChunkInt(x), 0, MathUtil.getBelongChunkInt(z));
        IBlock block = chunk.getBlock(offsetX, y, offsetZ);


        if (block == null || block.getId() == 0 || block.isPenetrate()) { //说明这边的block也是空的 it's empty
           if(y<1){
               return 0;
           }
            IBlock blockYLow = chunk.getBlock(offsetX, y-1, offsetZ);
            if (blockYLow == null || blockYLow.getId() == 0 || blockYLow.isPenetrate()) {//如果平移过去 下面的基石是空的
                //test y-1 again
                if(y<2){
                    return -1;
                }
                IBlock blockYLowLow = chunk.getBlock(offsetX, y-2, offsetZ);
                if (blockYLowLow == null || blockYLowLow.getId() == 0 ||blockYLowLow.isPenetrate()) {//说明过去的话下面是空的 过去会发生跌落 means the ydistance >1
                    return -2;

                } else {//说明y-1 是有石头的 means y-1 there is block livingthing will change y=y-1
                    return -1;
                }
            } else if (blockYLow != null && blockYLow.getId() > 0 &&  !blockYLow.isPenetrate()) {//如果平移过去 下面的基石是有的
                //可以过去
                return 0;//means ok just go there livingthing will not change y
            }
        } else if (block.getId() > 0) {//说明旁边是有块的means y there is block livingthing will change y=y+1
            IBlock blockYHigh = chunk.getBlock(offsetX, y + 1, offsetZ);
            if (blockYHigh == null || blockYHigh.getId() == 0 || blockYHigh.isPenetrate()) {//means y+1 there is no block livingthing can go there  and will lift the livingthing
                return 1;
            } else {
                return 2;
            }

        }

        return -100;


    }
}
