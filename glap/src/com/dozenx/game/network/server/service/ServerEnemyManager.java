package com.dozenx.game.network.server.service;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.live.state.IdleState;
import com.dozenx.game.network.server.bean.*;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.TimeUtil;
import com.sun.jna.platform.win32.Netapi32Util;
import glmodel.GL_Vector;

import javax.vecmath.Point3i;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luying on 17/2/26.
 */
public class ServerEnemyManager implements  Runnable {
    private ServerContext serverContext  ;
    private ChunkProvider chunkProvider;
    public ServerEnemyManager(ServerContext serverContext){
        this.serverContext =serverContext;
        this.userService  =(UserService) serverContext.getService(UserService.class);
        this.enemyService =(EnemyService) serverContext.getService(EnemyService.class);

        this.chunkProvider = serverContext.chunkProvider;
    }
    UserService userService;
    EnemyService enemyService;
    @Override
    //首先要确认的是服务器端的怪物逻辑是多少时间一个轮询的
    //200毫秒一次行不行
    //各种生物执行update的地方
    //各种怪物执行思考和行动的地方
    public void run() {
        while(true) {
            try {


                enemyServerLoop();
                synEnemyPos();  //每隔200ms同步变化的位置给用户
                //this.findThing();
               // this.doSomeThing();
               // this.changeDir();
                //this.moveOrAttack();
                //this.testCmd();
                try {
                    Thread.sleep(1000);//1秒同步一次
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    long lastUpdateTime = 0;
    public void synEnemyPos(){
        for(LivingThingBean enemy : enemyService.getAllEnemies()) {
            if(!enemy.isDied() && enemy.updateTime>lastUpdateTime){
                lastUpdateTime=TimeUtil.getNowMills();

                serverContext.broadCast(new PosCmd(enemy.getInfo()).toBytes());
                //serverContext.broadCast(new WalkCmd2(enemy.getPosition(),enemy.getPosition(),enemy.getId()).toBytes());
            }
        }
    }
    //怪物逻辑
    public void enemyServerLoop(){
        for(LivingThingBean enemy : enemyService.getAllEnemies()) {
            if (!enemy.isDied() ) {//如果自身是有效单位 没有死

                if(enemy.getTargetId() > 0){//并且是有目标
                    if(checkEnemyTarget(enemy))//释放无用target 补全缺少target
                    {//追击或者攻击
                        moveOrAttack(enemy);
                    }


                }else{//暂时没有目标
                    //if(enemy.getExecutor().getCurrentState() instanceof  IdleState){
                        //找寻目标

                        findTarget(enemy);
                    //}
                }
                enemy.getExecutor().getCurrentState().update();

            }
        }
        for(LivingThingBean player : userService.getAllOnlinePlayer()) {
            if (!player.isDied() ) {//如果自身是有效单位

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

    public boolean checkEnemyTarget(LivingThingBean livingThingBean){
        LivingThingBean player= userService.getOnlinePlayerById(livingThingBean.getTargetId());
        if(player==null){ //如果没有目标
            livingThingBean.setTargetId(0);
            livingThingBean.setTarget(null);
            return false;
        }else
        if (GL_Vector.length(GL_Vector.sub(livingThingBean.getPosition(), player.getPosition())) >100 ) {//如果距离太远了 就失去目标
            if(TimeUtil.getNowMills()-livingThingBean.getLastHurtTime()>10*1000) {//如果上次伤害还没超过多少时间

                livingThingBean.setTargetId(0);
                livingThingBean.setTarget(null);
            }
            livingThingBean.setTarget(player);
            //enemy.setTarget(player);
            return false;
        }else{
            livingThingBean.setTarget(player);
            return true;
        }

    }
    public void findTarget(LivingThingBean enemy){

        for(LivingThingBean player: userService.getAllOnlinePlayer()){

            if(player!=null)
            if (GL_Vector.length(GL_Vector.sub(enemy.getPosition(), player.getPosition())) < 5) {
                enemy.setTarget(player);
                //enemy.setTarget(player);
            }

        }

    }

    public void testCmd(){
        for(LivingThingBean enemy : enemyService.getAllEnemies()) {


            if (!enemy.isDied() &&enemy.getTargetId() > 0) {

                if(enemy.getExecutor().getCurrentState() instanceof IdleState ){
                    LivingThingBean player= userService.getOnlinePlayerById(enemy.getTargetId());
                    if(player!= null) {
                        WalkCmd2 walkCmd2 = new WalkCmd2(enemy.getPosition(), player.getPosition(), enemy.getId());
                        serverContext.broadCast(walkCmd2.toBytes());
                        enemy.getExecutor().receive(walkCmd2);
                    }
                }



            }
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

    /**
     * move or attack
     * @param enemy
     */
    public void moveOrAttack(LivingThingBean enemy) {


                GL_Vector direction = GL_Vector.sub(enemy.getTarget().getPosition(),enemy.getPosition());
                //enemy.setWalkDir(direction);
                   if(GL_Vector.length(direction)<2){
                       AttackCmd attackCmd = new AttackCmd( enemy.getId(),AttackType.KAN,enemy.getTargetId());
                       serverContext.broadCast(attackCmd.toBytes());
                       enemy.getExecutor().receive(attackCmd);
                        //开始攻击
                      /* enemy.
                        attack(enemy,enemy.getTarget());*/
                        //livingThing.nextPosition=null;
                    }else{
                       // this.getAnimationManager().apply(livingThing.bodyComponent,"walkerFoward");
                       /*GL_Vector newPosition = GL_Vector.add(enemy.getPosition(),GL_Vector.multiply(direction.normalize(),1*1));
                        enemy .setPosition(newPosition);
                       serverContext.broadCast(new PosCmd(enemy.getInfo()).toBytes());

*/                      //这段代码会导致一个问题 怪物到了目的地后才会再计算下一个目的地
                       //每隔一段时间就修正怪物的walkstate
                       LivingThingBean player= userService.getOnlinePlayerById(enemy.getTargetId());
                       if(player!= null){
                           //移动到制定位置设置目标

                           //可达性分析

                           enemy.setDest(player.getPosition());
                           //
                           enemy.setBodyAngle( GL_Vector.getAnagleFromXZVectory(direction));
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



                   }

    }
/*
    public void attack(LivingThingBean source ,LivingThingBean target){
           serverContext.getAllHandlerMap().get(CmdType.ATTACK).handler(new GameServerRequest(new AttackCmd(source.getId(), AttackType.ARROW,target.getId()),null),new GameServerResponse());

    }*/


    public void pathRoute(GL_Vector from,GL_Vector to,LivingThingBean livingThingBean ){
        //首先判断from 下面有没有基础方块

        //first we found if from was on a block
        Block block = chunkProvider.getBlockAt((int)from.x,(int)from.y,(int)from.z);
        if(block!=null && block.getId()>0){
            //x

            int xDistance = (int)(to.x - from.x);//caculate the x distance
            int zDistance =(int ) (to.z - from.z);//caculate the z distance
            int xDir =1;
            if(xDistance<0){
                xDir=-1;
            }
            int zDir = 1;
            if(zDistance<0){
                zDir= -1;
            }
            int xAbsDistance =Math.abs(xDistance);
            int zAbsDistance = Math.abs(zDistance);

            int fromX = (int)from.x;
            int fromY = (int) from.y;
            int fromZ = (int) from.z;

            int toX = (int)to.x;
            int toY = (int) to.y;
            int toZ = (int) to.z;

            int nowX=fromX;
            int nowY=fromY;
            int nowZ=fromZ;
          //  for(int x =0 ;x<xAbsDistance;x++){
             //   for(int z =0 ;z<zAbsDistance;z++){

                    GL_Vector now = new GL_Vector(xAbsDistance,from.y,zAbsDistance);
                    if(diguiTest(fromX,fromY,fromZ,nowX,nowY,nowZ,toX,toY,toZ,xDir,zDir)){


                        livingThingBean.routes.clear();
                        livingThingBean.routes.addAll(routeList);
                        //先判断左

                    }else{
                        //can't find way;
                    }
               // }
            //}
            //z
        }
    }
    public Map<Integer,Integer> alreadyMap =new HashMap<>();

    //return ture means find the way
    public boolean diguiTest(int fromX,int fromY,int fromZ,int nowX,int nowY,int nowZ,int toX,int toY,int toZ,int xDir,int zDir){
        //先左 前 右边
        if(alreadyMap.get(fromX*1000*1000+nowZ*1000+nowZ)!=null){
            return false;
        }
        alreadyMap.put(fromX*1000*1000+nowZ*1000+nowZ,1);

        //判断当前点是否已经判断过了
        //x max dir
        //x++
        //当前y的层高
        //check xMax
        if(nowX!=toX){


            // x++
            int x = nowX +xDir;
            int luocha = luocha(x,nowY,nowZ);
            if(luocha ==-1 || luocha==0 || luocha == 1){
                if(diguiTest(fromX, fromY, fromZ, nowX+xDir, nowY+luocha, nowZ, toX, toY, toZ, xDir, zDir)){
                    routeList.add(new Point3i(nowX,nowY,nowZ));
                    return true;
                }
            }else{

            }

        }
        if(nowZ!=toZ){
            //z++

            int luocha = luocha(nowX,nowY,nowZ+zDir);

            if(luocha ==-1 || luocha==0 || luocha == 1){
                if(diguiTest(fromX, fromY, fromZ, nowX, nowY+luocha, nowZ+zDir, toX, toY, toZ, xDir, zDir)){
                    routeList.add(new Point3i(nowX,nowY,nowZ));
                    return true;
                }
            }else{

            }
        }

        return false;
    }


    public  int luocha(int x,int y,int z){
       // Block block = chunkProvider.getBlockAt(thereX,thereY,thereZ);

        Chunk chunk = chunkProvider.getChunk(x,y,z);
        Block block = chunk.getBlock(x,y,z);





        if(block==null || block.getId()==0){ //说明这边的block也是空的 it's empty
            Block blockYLow = chunk.getBlock(x, y - 1, z);
            if(blockYLow ==null || blockYLow.getId()==0){//如果平移过去 下面的基石是空的
                //test y-1 again
                Block blockYLowLow = chunk.getBlock(x, y - 2, z);
                if(blockYLowLow ==null || blockYLowLow.getId()==0){//说明过去的话下面是空的 过去会发生跌落 means the ydistance >1
                    return -2;

                }else{//说明y-1 是有石头的 means y-1 there is block livingthing will change y=y-1
                   return -1;
                }
            }else
            if(blockYLow!=null && blockYLow.getId()>0){//如果平移过去 下面的基石是有的
                //可以过去
               return 0;//means ok just go there livingthing will not change y
            }
        }else
        if( block.getId()>0){//说明旁边是有块的means y there is block livingthing will change y=y+1
            Block blockYHigh = chunk.getBlock(x,y+1,z);
            if(blockYHigh==null ||blockYHigh.getId()==0 ){//means y+1 there is no block livingthing can go there  and will lift the livingthing
                return 1;
            }else{
                return 2;
            }

        }

        return -100;


    }

    List routeList =new ArrayList();
}
