package com.dozenx.game.engine.Role.controller;

import cola.machine.game.myblocks.engine.BlockEngine;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.PhysicsEngine;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.Role.bean.Wolf;
import com.dozenx.game.engine.Role.model.PlayerModel;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.engine.ui.head.view.HeadPanel;
import com.dozenx.game.engine.ui.inventory.control.BagController;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.network.client.bean.GameCallBackTask;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.PlayerStatus;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by luying on 16/9/17.
 */
public class LivingThingManager {


    public  List<LivingThing> livingThings = new CopyOnWriteArrayList<>();
    private Map<Integer,LivingThing> livingThingsMap =new HashMap();
    //public LivingThing target ;
    public  LivingThing player;
    Component bendComponent;
    Wolf wolf =null;

    public void checkPlayerDrop(){
        if(player.isStable()) {
            CoreRegistry.get(PhysicsEngine.class).checkIsDrop(player);
        }
        //remove the died one

    }
    public void removeAllDiedOne(){
        for(int i=livingThings.size()-1;i>=0;i--){
            LivingThing  livingThing = livingThings.get(i);
            if((/*livingThing.died || */livingThing.nowHP<=0) ){
                this.livingThingsMap.remove(livingThing.getId());
                livingThings.remove(i);
            }
        }
    }
    public LivingThingManager(){

       /*  wolf =new Wolf(999);
        wolf.position=new GL_Vector(-1,3,-1);
        add(wolf);*/
        /*   component =new Component(2,16,2);
        component.bend(180,50);
        component.setShape(TextureManager.getShape("human_body"));*/
        CoreRegistry.put(LivingThingManager.class,this);
        // Thread thread = new Thread(behaviorManager);

        //behaviorManager.run();
        // livingThings.add(CoreRegistry.get(Human.class));

        /*new Thread(){
            @Override
            public void run() {
                while (true) {
                    WalkCmd2 walkCmd = new WalkCmd2(new GL_Vector(0, 1, 0), new GL_Vector(0, 1, -10), wolf.getId());

                    wolf.receive(walkCmd);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();*/
    }
    public void setPlayer(LivingThing livingThing){
        this.player=livingThing;
    }
    public void add(LivingThing livingThing){
        livingThings.add(livingThing);
        livingThingsMap.put(livingThing.getId(),livingThing);
    }
    public LivingThing getLivingThingById(int id){
        if(player.getId() == id){
            return player;
        }
        return livingThingsMap.get(id);
    }


    public void render(){
        //glUseProgram(ShaderManager.livingThingShaderConfig.getProgramId());
        //if(/*ShaderManager.livingThingShaderConfig.getVao().getVaoId()==0*/GamingState.livingThingChanged){

        //}
        //livingThing render
        //TODO targetId 已经设置好了 但是target还是为空的
        ShaderManager.livingThingShaderConfig.getVao().getVertices().rewind();



        //livingthing update
        for (LivingThing livingThing : livingThings) {
           // this.getLivingThingById();
            /*if(livingThing.getTargetId()>0 && livingThing.getTarget() == null ){
                livingThing.setTarget(this.getLivingThingById(livingThing.getTargetId()));

            }*/
            livingThing.render();//先更新


            //livingThing.renderBloodBar();
           // livingThing.distance = GL_Vector.length(GL_Vector.sub(player.position, livingThing.position));

        }
        if(this.player.getTarget()!=null){
            GL_Vector position = this.player.getTarget().getPosition();
            ShaderUtils.draw3dColorBox(ShaderManager.livingThingShaderConfig, ShaderManager.livingThingShaderConfig.getVao(), position.x, position.y+1,position.z, new GL_Vector(1,0,0), 0.5f, 0.5f, 0.5f, 1);
        }
        //player update
        this.player.render();//心更新
        if (Switcher.SHADER_ENABLE) {
            ShaderUtils.freshVao(ShaderManager.livingThingShaderConfig, ShaderManager.livingThingShaderConfig.getVao());
            //ShaderManager.CreateLivingVAO(ShaderManager.livingThingShaderConfig, ShaderManager.livingThingShaderConfig.getVao());
        }
        
       
        //ShaderUtils.updateLivingVao(ShaderManager.livingThingShaderConfig.getVao());//createVAO(floatBuffer);
        GamingState.livingThingChanged = false;

/*
        for(LivingThing livingThing:livingThings){
            if(Switcher.SHADER_ENABLE){
                //livingThing.renderShader();
            }else{
                livingThing.getModel().build(ShaderManager.livingThingShaderConfig,new GL_Matrix());
            }

            //livingThing.renderBloodBar();
           livingThing.distance = GL_Vector.length( GL_Vector.sub(player.position,livingThing.position));

        }
        //player render
        if(Switcher.SHADER_ENABLE){
            //this.player.renderShader();
            //livingThing.renderShader();
        }else{
           *//* this.player.position.x+=0.01;
            if(this.player.position.x>10)
                this.player.position.x=0;*//*
            this.player.getModel().build(ShaderManager.livingThingShaderConfig,new GL_Matrix());
            //livingThing.render();
        }*/
        ShaderUtils.finalDraw(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao());
       // component.renderBend();


    }


    public void cycle(){

    }

    /**
     *
     * @param physicsEngine
     */
    @Deprecated
    public void checkAllLivingThingCollising(  PhysicsEngine physicsEngine){

        for(LivingThing livingThing:livingThings){
            if(livingThing.isPlayer() )continue;//不对玩家进行校验 怕玩家离开自己太远的时候还进行校验
            if(livingThing.position.y<0){
                livingThing.position.y=0;
                livingThing.setStable(true);
            }
            physicsEngine.collision(livingThing);

        }
        if(player.position.y<0){
            player.position.y=0;
            player.setStable(true);
        }
        physicsEngine.collision(player);
    }

    /*public LivingThing findTarget(Point3f position){
        for(int i=0;i<livingThings.size();i++){
            LivingThing livingThing=livingThings.get(i);
            if(Math.sqrt((livingThing.position.x-position.x)*(livingThing.position.x-position.x) +
                    (livingThing.position.y-position.y)*(livingThing.position.y-position.y)+
                    (livingThing.position.y-position.y)*(livingThing.position.y-position.y))<4){

                return  livingThing;
            }


        }
        return null;
    }*/
    public void chooseObject(LivingThing livingThing){
       if(livingThing==null || player.getTarget()!=null){//取消选择物体
           CoreRegistry.get(HeadPanel.class).setVisible(false);
          // player.getTarget().getModel().unSelect();
           player.setTarget(null);
           Document.needUpdate=true;
       }

    }
    public LivingThing chooseObject(GL_Vector from, GL_Vector direction){
       // LogUtil.println("开始选择");
        Vector3f fromV= new Vector3f(from.x,from.y,from.z);
        Vector3f directionV= new Vector3f(direction.x,direction.y,direction.z);
        for(int i=0;i<livingThings.size();i++){
            LivingThing livingThing=livingThings.get(i);
            AABB aabb = new AABB(new Vector3f(livingThing.position.x-0.5f,livingThing.position.y,livingThing.position.z-0.5f),new Vector3f(livingThing.position.x+0.5f,livingThing.position.y+1.5f,livingThing.position.z+0.5f));

           // LogUtil.println(fromV.toString() );
           // LogUtil.println(directionV.toString() );
           if( aabb.intersectRectangle(fromV,directionV)){
            //GL_Vector.chuizhijuli(GL_Vector.sub(livingThing.position,from),direction)<3){
            //   LogUtil.println("选中了");
               //this.target=livingThing;
               livingThing.select();
               player.setTarget(livingThing);
               CoreRegistry.get(HeadPanel.class).bind(livingThing).show();
                return livingThing;
           }


        }
       // LogUtil.println("未选中");
        return null;
    }
    
    
    public LivingThing chooseObject(float x, float y){
        // LogUtil.println("开始选择");
        GL_Vector from = GamingState.instance.camera.Position.copyClone();
        GL_Vector direction =OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(),x,Constants.WINDOW_HEIGHT-y);
         direction=direction.normalize();

         // LogUtil.println("开始选择");
         Vector3f fromV= new Vector3f(from.x,from.y,from.z);
         Vector3f directionV= new Vector3f(direction.x,direction.y,direction.z);
         float distance =0;
         LivingThing theNearestBlock = null;
         float[] xiangjiao=null;
         float[] right=null;
         for(int i=0;i<livingThings.size();i++){
             LivingThing colorBlock =  livingThings.get(i);
             AABB aabb = new AABB(new Vector3f(colorBlock.getX(),colorBlock.getY(),colorBlock.getZ()),new Vector3f(colorBlock.getX()+1,colorBlock.getY()+1,colorBlock.getZ()+1));

             // LogUtil.println(fromV.toString() );
             // LogUtil.println(directionV.toString() );
             if( (xiangjiao=aabb.intersectRectangle2(fromV,directionV))!=null){//这里进行了按照list的顺序进行选择 其实应该按照距离的最近选择

                 //计算点在了那个面上
                 //有上下左右前后6个面
                 LogUtil.println("选中了");
                 float _tempDistance = xiangjiao[0]* xiangjiao[0]+ xiangjiao[1]* xiangjiao[1]+ xiangjiao[2]* xiangjiao[2];

                 if(distance ==0||_tempDistance<distance){
                     distance=_tempDistance;
                     theNearestBlock=colorBlock;
                     right = xiangjiao;
                 }




             }


         }
         return theNearestBlock;
     }
    public void findWay(){


    }

    public void move(){

    }
    Client client =CoreRegistry.get(Client.class);
    long lastUpdateTime=System.currentTimeMillis();



    /**
     * 通过定时线程来控制
     * 主要是网络同步各种状态到任务上 you synctask 驱动
     */
    public void update(){
        Long nowTime = TimeUtil.getNowMills();
        /*if(!(BlockEngine.engine.getState() instanceof  GamingState) ){
            LogUtil.println("当前状态不对");
            return;
        }*/


        for(int i=livingThings.size()-1;i>=0;i--){
            LivingThing  livingThing = livingThings.get(i);
            if((/*livingThing.died || */livingThing.nowHP<=0)&& nowTime - livingThing.getLastHurtTime()> 10000 ){
              // this.livingThingsMap.remove(livingThing.getId());
                //livingThings.remove(i);
            }
        }
      /*  long nowTime = System.currentTimeMillis();
        if(nowTime-lastUpdateTime >5000){lastUpdateTime=nowTime;
            for(LivingThing livingThing:livingThings){
                if(Switcher.SHADER_ENABLE){
                    livingThing.setPosition(0,3,0);
                    livingThing.update();

                }else{

                }

                //livingThing.renderBloodBar();
                livingThing.distance = GL_Vector.length( GL_Vector.sub(player.position,livingThing.position));

            }
            this.player.update();
            ShaderManager.CreateTerrainVAO(ShaderManager.livingThingShaderConfig, ShaderManager.livingThingShaderConfig.getVao());
            //ShaderUtils.updateLivingVao(ShaderManager.livingThingShaderConfig.getVao());//createVAO(floatBuffer);

        }*/
        while(client.movements.size()>0 && client.movements.peek()!=null){
            PosCmd cmd = (PosCmd)client.movements.pop();
            int id = cmd.userId;
            if(player.getId() == id){ //不要同步本大爷的数据给本大爷
               /* player.setPosition(x,y,z);
                player.WalkDir.x= x1;
                player.WalkDir.y= y1;
                player.WalkDir.z= z1;*/
                continue;
            }

            float x = cmd.x;//Float.valueOf(msg[1]);
            float y = cmd.y;//Float.valueOf(msg[2]);
            float z = cmd.z;//Float.valueOf(msg[3]);

            float bodyAngle = cmd.bodyAngle;//Float.valueOf(msg[4]);
            float headAngle = cmd.headAngle;//Float.valueOf(msg[5]);
            float headAngle2 = cmd.headAngle2;//Float.valueOf(msg[6]);


            //appendRow("color"+curColor, msg);
            LivingThing livingThing = this.getLivingThingById(id);
            if(livingThing==null ){
               // LogUtil.err("that's bug");
                continue;
               /*  livingThing =new LivingThing();livingThing.setPlayer(true);
                livingThing.id=id;
               // livingThing.name=name;
                livingThing.setPosition(x,y,z);

                this.add(livingThing);*/
            }
            //同步用户的位置 面向
            livingThing.setDest(new GL_Vector(x,y,z));
            //livingThing.setPosition(x,y,z);
            livingThing.setBodyAngle(bodyAngle);
            livingThing.setHeadAngle( headAngle);
            livingThing.setHeadAngle2( headAngle2);

        }

        while(client.equips.size()>0 && client.equips.peek()!=null){

            EquipCmd cmd = (EquipCmd)client.equips.pop();
            int id = cmd.getUserId();
            /*if(player.getId() == id){
                continue;
            }*/
            //appendRow("color"+curColor, msg);
            LivingThing livingThing = this.getLivingThingById(id);
            //y原来是直接绑定到itemDefinition 现在要对应到有id的items里的物品
            //ItemBean itemBean = livingThing.getItemById(cmd.getItemId());
           // ItemType itemType =
            if(livingThing!=null  /*&& itemBean!=null*/ ){
                if(cmd.getPart()== EquipPartType.BODY){
                    livingThing.setBodyEquip(cmd.getItemType());
                    ((PlayerModel)livingThing.getModel()).addBodyEquip(new ItemBean(ItemManager.getItemDefinition(cmd.getItemType()), 1));
                }else if(cmd.getPart()== EquipPartType.HEAD){
                    livingThing.setHeadEquip(cmd.getItemType());
                    ((PlayerModel)livingThing.getModel()).addHeadEquip(new ItemBean(ItemManager.getItemDefinition(cmd.getItemType()),1));
                }else if(cmd.getPart()== EquipPartType.HAND){
                    livingThing.setHandEquip(cmd.getItemType());
                    ((PlayerModel)livingThing.getModel()).addHandEquip(new ItemBean(ItemManager.getItemDefinition(cmd.getItemType()),1));
                }else if(cmd.getPart()== EquipPartType.LEG){
                    livingThing.setLegEquip(cmd.getItemType());
                    ((PlayerModel)livingThing.getModel()).addLegEquip(new ItemBean(ItemManager.getItemDefinition(cmd.getItemType()),1));
                }else if(cmd.getPart()== EquipPartType.FOOT){
                    livingThing.setFootEquip(cmd.getItemType());
                    ((PlayerModel)livingThing.getModel()).addShoeEquip(new ItemBean(ItemManager.getItemDefinition(cmd.getItemType()),1));
                }
                //change trigger
                livingThing.changeProperty();

            }




        }


            //偶尔发生 或者当该用户登录 或者被创建的时候
        while(client.playerSync.size()>0 && client.playerSync.peek()!=null /*&& BlockEngine.engine.getState() instanceof  GamingState*/){
            PlayerSynCmd cmd = (PlayerSynCmd)client.playerSync.poll();
            PlayerStatus info  = cmd.getPlayerStatus();
            int id =info.getId();
            /*if(player.id == id){
                continue;
            }*/
            //appendRow("color"+curColor, msg);
            LivingThing livingThing = this.getLivingThingById(id);
            boolean exsits =true;
            if(livingThing==null ) {
                ItemManager.getItemDefinition(info.getName());
                if(info.species==1){;
                    livingThing = new Wolf(info.getId());

                }else if(info.species>1){

                    livingThing = new Wolf(info.getId(),ItemManager.getItemDefinition(Integer.valueOf(info.species)));
                }else{
                    livingThing = new Wolf(info.getId(),ItemManager.getItemDefinition(Integer.valueOf(info.species)));
                  //  livingThing = new Player(info.getId());
                }
                //
                exsits =false;

            }


       /*         if(info.getBodyEquip()>0){
                    livingThing.getModel().addBodyEquip(TextureManager.getItemDefinition(ItemType.values()[info.getBodyEquip()]));
                    //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
                }if(info.getHeadEquip()>0){
                    livingThing.getModel().addBodyEquip(TextureManager.getItemDefinition(ItemType.values()[info.getHeadEquip()]));
                    //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
                }if(info.getHandEquip()>0){
                    livingThing.getModel().addBodyEquip(TextureManager.getItemDefinition(ItemType.values()[info.getHandEquip()]));
                    //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
                }if(info.getLegEquip()>0){
                    livingThing.getModel().addBodyEquip(TextureManager.getItemDefinition(ItemType.values()[info.getLegEquip()]));
                    //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
                }if(info.getShoeEquip()>0){
                    livingThing.getModel().addBodyEquip(TextureManager.getItemDefinition(ItemType.values()[info.getShoeEquip()]));
                    //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
                }*/
            if(livingThing.isDied() && info.nowHP>0){//原来是死的 现在是活的 复活了
               // livingThing.exist=true;
                livingThing.getExecutor().getModel().getRootComponent().rotateX=0;
            }else{

            }
            livingThing.setInfo(info);

            //livingThing.setTarget()
           /* try {
                livingThing.setPosition(info.getX(), info.getY(), info.getZ());
            }catch (Exception e){
                e.printStackTrace();
            }
           // livingThing.setPosition(x,y,z);
            livingThing.setBodyAngle(info.getBodyAngle());
            livingThing.setHeadAngle( info.getHeadAngle());
            livingThing.setHeadAngle2( info.getHeadAngle2());*/
            if(!exsits) {
                this.add(livingThing);
            }
        }

        while(client.attacks.size()>0 && client.attacks.peek()!=null){
            AttackCmd cmd = (AttackCmd)client.attacks.pop();
            int id = cmd.getUserId();

            LivingThing livingThing = this.getLivingThingById(id);
            if(livingThing==null ){
                continue;
            }
            livingThing.setTarget(this.getLivingThingById(cmd.getTargetId()));

            livingThing.receive(cmd);
        }

        while(client.bags.size()>0 && client.bags.peek()!=null){
            BagCmd cmd = (BagCmd)client.bags.pop();
            int userId = cmd.getUserId();
            List<ItemServerBean> items =cmd.getItemBeanList();

            LivingThing livingThing = this.getLivingThingById(userId);
            if(livingThing==null ){
                continue;
            }
            livingThing.setItems(items);

            CoreRegistry.get(BagController.class).refreshBag();
            //livingThing.receive(cmd);
        }

        while(client.drops.size()>0 && client.drops.peek()!=null){
            DropCmd cmd = client.drops.pop();
            int userId = cmd.getUserId();

            if(userId==0){//说明是世界掉落物品更新
                player.getExecutor().receive(cmd);
            }else{
            LivingThing from = this.getLivingThingById(userId);
            if(from != null){
                from.getExecutor().receive(cmd);
            }else{
                player.getExecutor().receive(cmd);
            }}

         /*   if(userId == player.getId()){
                player.getItemBeans()[24]=null;
            }else{

            }
            ItemManager.add(new Ball(player.getPosition().getClone(),player.getWalkDir().getClone(),0,TextureManager.getShape("arrow"),player));
            CoreRegistry.get(BagController.class).refreshBag();*/
            //livingThing.receive(cmd);
        }
        while(client.humanStates.size()>0 && client.humanStates.peek()!=null) {
            UserBaseCmd cmd = (UserBaseCmd)client.humanStates.pop();
            int userId = cmd.getUserId();

            LivingThing from = this.getLivingThingById(userId);
            if(cmd.getCmdType() == CmdType.CHASE ){
                from.setTarget(this.getLivingThingById(((ChaseCmd )cmd).getTargetId()));
            }
            if (from != null) {
                from.getExecutor().receive(cmd);
            } else {
                player.getExecutor().receive(cmd);
            }
        }
        while(client.picks.size()>0 && client.picks.peek()!=null){
            PickCmd cmd  = client.picks.pop();
            int userId = cmd.getUserId();

            LivingThing from = this.getLivingThingById(userId);
            /*if(from != null){
                from.getExecutor().receive(cmd);
            }else{*/
                player.getExecutor().receive(cmd);
            //}


            ItemManager.removeWorldItem(cmd.getItemId());

         /*   if(userId == player.getId()){
                player.getItemBeans()[24]=null;
            }else{

            }
            ItemManager.add(new Ball(player.getPosition().getClone(),player.getWalkDir().getClone(),0,TextureManager.getShape("arrow"),player));
            CoreRegistry.get(BagController.class).refreshBag();*/
            //livingThing.receive(cmd);
        }
       /* while(client.newborns.size()>0 && client.newborns.peek()!=null){
            String[] msg = client.movements.pop();
            int id = Integer.getInteger(msg[0]);
            String name = msg[1];
            float x = Float.valueOf(msg[2]);
            float y = Float.valueOf(msg[3]);
            float z = Float.valueOf(msg[4]);
            if(id== player.id){

                player.id=id;
                player.name=name;
                player.setPosition(x,y,z);
                continue;
            }

            //appendRow("color"+curColor, msg);
            LivingThing livingThing =new LivingThing();
            livingThing.id=id;
            livingThing.name=name;
            livingThing.setPosition(x,y,z);

           this.add(livingThing);

        }*/
        //if(GamingState.livingThingChanged ) {

        //}
        this.player.update();
        for(int i=livingThings.size()-1;i>=0;i--){
            LivingThing  livingThing = livingThings.get(i);
            livingThing.update();
        }


    }
   /* public void attack(){
        if(player.target!=null)
        if(GL_Vector.length(GL_Vector.sub(player.target.position,player.position))<4){
            player.target.nowBlood-=5;
            if(player.target.nowBlood<=0){
                player.target.died();
                AnimationManager manager = CoreRegistry.get(AnimationManager.class);
                manager.clear(player.target.bodyComponent);
                manager.apply(player.target.bodyComponent,"died");
                player.target=null;
                target=null;
            }
        }

    }*/
    public void beAttack(){


    }





    public void login(String userName,String pwd){
        //  CoreRegistry.get(Client.class).send(new LoginCmd(userName.getText(),pwd.getText()));
        // ResultDTO dto = CoreRegistry.get(LoginClientController.class).login(userName.getText(),pwd.getText());
       // String userName1 =userName;
        GameCallBackTask task = new GameCallBackTask(){
            @Override
            public void run(){
                if(getResult().getResult()==0){

                    if(getResult().getMsg()!=null){
                        PlayerSynCmd cmd =new  PlayerSynCmd(getResult().getMsg());
                        //PlayerStatus status = JSON.parseObject(getResult().getMsg(),PlayerStatus.class);
                        GamingState state = new GamingState();
                        Constants.USER_ID=cmd.getPlayerStatus().getId();
                        Constants.userName = cmd.getPlayerStatus().getName();

                        //创建Human
                        Player player = new Player(cmd.getPlayerStatus().getId());
                        //player.setPlayerStatus(cmd.getPlayerStatus() );
                        changePlayerInfoToPlayer(player,cmd.getPlayerStatus());
                        //human.setPlayerStatus(status);
                        CoreRegistry.put(Player.class, player);

                        LivingThingManager livingThingManager =new LivingThingManager();
                        CoreRegistry.put(LivingThingManager.class,livingThingManager);

                        livingThingManager.setPlayer(player);
                        //livingThingManager.add(livingThing);
                        state.player = player;
                        BlockEngine.engine.changeState(state);
                            /*int threadId = (int)(Math.random()*100000);

                            GameCallBackTask task = new GameCallBackTask() {
                                @Override
                                public void run() {

                                }
                            };
                            Client.taskMap.put(threadId, task);

                            GetCmd getCmd =new GetCmd(threadId);*/



                    }

                }else{
                    return;
                }
            }
        };
        int threadId = (int)(Math.random()*100000);
        Client.SyncTaskMap.put(threadId, task);

        CoreRegistry.get(Client.class).send(new LoginCmd(userName,pwd,threadId));
    }
    public void loadPlayerInfoFromServer(){

    }



    public void changePlayerToInfo(Role role ,PlayerStatus info ){
        //  super.setPlayerStatus(status);
        role.getInfo(info);

/*
        role.setId(info.getId());
        //setPwd(status.getPwd());
        role. setBodyAngle(info.getBodyAngle());
        role. setHeadAngle(info.getHeadAngle());
        role. setHeadAngle2(info.getHeadAngle2());

        role. setTargetId(info.getTargetId());
        role. setTarget(null);
        // role. setIsplayer(status.isplayer);

        //this.id = status.getId();
        role.setPosition(info.getX(),info.getY(),info.getZ());




        role.setHeadEquip(role.getItemById(info.getHeadEquip()));
        role.setBodyEquip(role.getItemById(info.getBodyEquip()));
        role.setHandEquip(role.getItemById(info.getHandEquip()));
        role.setLegEquip(role.getItemById(info.getLegEquip()));
        role.setFootEquip(role.getItemById(info.getShoeEquip()));*/


    }
    public void changePlayerInfoToPlayer(Role role ,PlayerStatus info ){
        //  super.setPlayerStatus(status);

/*
        role.setId(info.getId());
        //setPwd(status.getPwd());
        role. setBodyAngle(info.getBodyAngle());
        role. setHeadAngle(info.getHeadAngle());
        role. setHeadAngle2(info.getHeadAngle2());

        role. setTargetId(info.getTargetId());
        role. setTarget(null);
        // role. setIsplayer(status.isplayer);

        //this.id = status.getId();
        role.setPosition(info.getX(),info.getY(),info.getZ());




        role.setHeadEquip(role.getItemById(info.getHeadEquip()));
        role.setBodyEquip(role.getItemById(info.getBodyEquip()));
        role.setHandEquip(role.getItemById(info.getHandEquip()));
        role.setLegEquip(role.getItemById(info.getLegEquip()));
        role.setFootEquip(role.getItemById(info.getShoeEquip()));*/

        role.setInfo(info);


    }




    public void addBodyEquipStart(ItemBean itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd( player, EquipPartType.BODY, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }
    public void addLegEquipStart(ItemBean itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(player, EquipPartType.LEG, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }

    public void addHandEquipStart(ItemBean itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(player, EquipPartType.HAND, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }

    public void addShoeEquipStart(ItemBean itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(player, EquipPartType.FOOT, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }
    public void addHeadEquipStart(ItemBean itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(player, EquipPartType.HEAD, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }

    public static void enemyLoop(List<LivingThingBean > livingThingBeanList){


    }

    public static float chaseCanAttack(LivingThingBean livingThing,long interval){
        if(livingThing.getTarget().getPosition() == livingThing.getPosition()){
            LogUtil.err("this cant't be same target's position and his position");
        }
        GL_Vector distanceVector = GL_Vector.sub(livingThing.getTarget().getPosition(), livingThing.getPosition());
        float distance = GL_Vector.length(distanceVector);
        if(distance>2){
            GL_Vector walkDir  = distanceVector.normalize();
            GL_Vector walkDistance= GL_Vector.multiplyWithoutY(walkDir,
                    1f* (interval)/1000);
            livingThing.setBodyAngle(GL_Vector.getAnagleFromXZVectory(walkDir));
            if(GL_Vector.length(walkDistance)<distance){
                livingThing.move(GL_Vector.add(livingThing.position, walkDistance));
            }else{
                livingThing.move(livingThing.getTarget().getPosition().copyClone());
            }
        }
        return distance;


    }
}
