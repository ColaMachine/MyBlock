package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.Animation;
import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.AttackManager;
import cola.machine.game.myblocks.skill.Ball;
import cola.machine.game.myblocks.skill.TimeString;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.ItemUtil;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.ui.inventory.control.BagController;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import javax.vecmath.Vector2f;

/**
 * Created by luying on 17/3/5.
 */
public class State {
    boolean disposed =false;
    public void dispose(){
        this.disposed=true;
    }

    long lastTime = 0;
   // long beginTime = 0;
    //GL_Vector walkDir = null;
    float distance =0;
    long  nowTime = 0 ;
    public void update(){
        if(this.livingThing.getDest()!=null){
            //计算当前和目的地的距离
            distance= GL_Vector.length(GL_Vector.sub(livingThing.getPosition(),livingThing.getDest()));
            if(distance>1){
                nowTime = TimeUtil.getNowMills();
                if(lastTime==0){       //如果开始时间是0说明刚开始
                    lastTime = nowTime;  //修正第一帧 让lastTime一开始等于开始时间 防止移动超过最大距离
                   // beginTime= TimeUtil.getNowMills();
                   // walkDir = GL_Vector.sub(livingThing.getDest(),livingThing.getPosition()).normalize();
                    return;
                }else{

                    if (nowTime - lastTime >50) {//如果大于50ms才执行
                        //调整计算新的位置
                        GL_Vector newPosition = nowPosition(nowTime, lastTime, livingThing.speed, livingThing.getPosition(), livingThing.getDest());
                        float length = GL_Vector.length(GL_Vector.sub(livingThing.getPosition(),livingThing.getDest()));
                        if(length<1){
                            livingThing.setDest(null);
                            //退出移动
                            return;
                        }else if(length>distance){//如果移动举例超过了最大距离 结束移动
                            livingThing.move(livingThing.getDest().x,livingThing.getDest().y,livingThing.getDest().z);
                            livingThing.setDest(null);
                            return;
                        }else {
                            livingThing.move(newPosition.x, newPosition.y, newPosition.z);
                            lastTime = TimeUtil.getNowMills();
                        }
                        //LogUtil.println(newPosition.toString());

                    }

                }
               // GL_Vector dir = GL_Vector.sub(livingThing.getDest(),livingThing.getPosition());
                //根据时间和速度 起开始位置 目的地 计算当前位置


            }else{

                livingThing.setDest(null);
                //放弃移动了
                return;
            }
            //livingThing.moveTo(livingThing.getDest());
        }else{
            lastTime=0;//归零
        }
    }
    public GL_Vector nowPosition(long nowTime,long lastTime,float speed,GL_Vector from,GL_Vector to){
        GL_Vector walkDir = GL_Vector.sub(to,from).normalize();
        GL_Vector newPosition =  GL_Vector.add(from, GL_Vector.multiplyWithoutY(walkDir,
                speed/10* (nowTime-lastTime)/1000));
        return newPosition;
    }

    public State(){



    }
    protected LivingThingBean livingThing;
    int intervalTime=500;
    public State(LivingThingBean livingThing){
        this.livingThing = livingThing;
    }
    GL_Matrix projection = GL_Matrix.perspective3(45, (Constants.WINDOW_WIDTH) / (Constants.WINDOW_HEIGHT), 1f, 1000.0f);
    public void receive(GameCmd gameCmd){
        if(gameCmd.getCmdType()==CmdType.DIED){
            DiedCmd diedCmd = (DiedCmd)gameCmd;
            /*this.from = walkCmd2.from;
            this.to=walkCmd2.to;*/
          /* CoreRegistry.get(AnimationManager.class).apply( livingThing.getModel().bodyComponent, "walkerFoward");*/
            this.livingThing.changeState( new DiedState(this.livingThing));
        }else if(gameCmd.getCmdType()==CmdType.CHASE){
            ChaseCmd chaseCmd = (ChaseCmd)gameCmd;
            /*this.from = walkCmd2.from;
            this.to=walkCmd2.to;*/
          /* CoreRegistry.get(AnimationManager.class).apply( livingThing.getModel().bodyComponent, "walkerFoward");*/
            this.livingThing.changeState( new ChaseState(this.livingThing,chaseCmd));
        }else
        if(gameCmd.getCmdType()==CmdType.WALK2){
            WalkCmd2 walkCmd2 = (WalkCmd2)gameCmd;
            if(walkCmd2.stop ==true){
                //this.livingThing.changeState(new IdleState(this.livingThing));
                livingThing.setDest(walkCmd2.to);//本来想设置成null的 但是为了服务器和人物的同步还是传了
                return;
            }
            /*this.from = walkCmd2.from;
            this.to=walkCmd2.to;*/
          /* CoreRegistry.get(AnimationManager.class).apply( livingThing.getModel().bodyComponent, "walkerFoward");*/
           // this.livingThing.changeState(new WalkState(this.livingThing, walkCmd2.from, walkCmd2.to));
            livingThing.setBodyAngle(walkCmd2.bodyAngle);

            this.livingThing.setDest(walkCmd2.to);
        }else
        if(gameCmd.getCmdType()==CmdType.WALK){
            WalkCmd walkCmd = (WalkCmd)gameCmd;
            /*this.from = walkCmd2.from;
            this.to=walkCmd2.to;*/
          /* CoreRegistry.get(AnimationManager.class).apply( livingThing.getModel().bodyComponent, "walkerFoward");*/
            this.livingThing.changeState(new WalkState(this.livingThing, walkCmd.dir));
        }
        else
        if(gameCmd.getCmdType()==CmdType.DROP ){
            DropCmd cmd = (DropCmd ) gameCmd;
            if(cmd.getUserId()==0){

            }else
           // 遍历所有的物品
            if(livingThing== GamingState.player && cmd.getUserId() == livingThing.getId()){//是主角掉落的
                /* ItemBean[] itemBeens = livingThing.getItemBeans();
               for(int i=0;i<itemBeens.length;i++){
                ItemBean nowBean = itemBeens[i];
                if(nowBean!=null && nowBean.getId()== cmd.getItemId()){
                    itemBeens[i]=null;
                    if(livingThing== GamingState.player){
                        CoreRegistry.get(BagController.class).refreshBag();
                        break;
                    }


                }
            }*/
            }else{//就是其他人掉落的 或者是数据同步到你机器上的 早就掉落的

            }
            ItemManager.add(new Ball(cmd.getItemId(), new GL_Vector(cmd.getX(), cmd.getY(), cmd.getZ()), livingThing.getWalkDir().getClone(), 0, cmd.getItemType(), livingThing));
            //livingThing.getItemBeans()[24]=null;




        }else if(gameCmd.getCmdType()==CmdType.PICK){
            PickCmd cmd = (PickCmd ) gameCmd;

            /*// 遍历所有的物品
            if(livingThing== GamingState.player && cmd.getUserId() == livingThing.getId()){//是主角掉落的
                ItemBean[] itemBeens = livingThing.getItemBeans();
                for(int i=0;i<itemBeens.length;i++){
                    ItemBean nowBean = itemBeens[i];
                    if(nowBean==null && nowBean.getId()== cmd.getItemId()){
                        itemBeens[i]=null;
                        if(livingThing== GamingState.player){
                            CoreRegistry.get(BagController.class).refreshBag();
                            break;
                        }


                    }
                }
            }else{//就是其他人掉落的 或者是数据同步到你机器上的 早就掉落的

            }*/
            ItemManager.removeWorldItem(cmd.getItemId());
            //livingThing.getItemBeans()[24]=null;




        }else
        if(gameCmd.getCmdType()== CmdType.JUMP){
             JumpCmd jumpCmd = (JumpCmd) gameCmd;
           // this.livingThing.jump();
            this.livingThing.changeState( new JumpState(this.livingThing,jumpCmd));
        }else

        if(gameCmd instanceof AttackCmd){

            Long notTime = TimeUtil.getNowMills();
            if(notTime-livingThing.getLastAttackTime() >intervalTime){
                livingThing.setLastAttackTime(notTime);
            }else{
                return;
            }
            /*Wep
            SkillDefinition skill = livingThing.getNowSkill();
            if(livingThing.getNowSkill()){

            }*/
            AttackCmd cmd =(AttackCmd) gameCmd;

            if(cmd.getAttackType()== AttackType.KAN){
                livingThing.changeAnimationState("kan");
                if(livingThing.getTarget()!=null)
                    {
                        livingThing.getTarget().beAttack(cmd.getAttackValue());
//                AttackManager.add(new TimeString(cmd.getAttackValue(),));
                        //如果是客户端可以加服务器就免了
                        if(GamingState.player!=null) {
                            Vector2f xy = OpenglUtils.wordPositionToXY(projection, livingThing.getTarget().getPosition(), GamingState.instance.camera.Position, GamingState.instance.camera.getViewDir());
                            AttackManager.addText(new TimeString("砍伤" + cmd.getAttackValue(), xy.x * Constants.WINDOW_WIDTH, xy.y * Constants.WINDOW_HEIGHT));
                            Document.needUpdate = true;
                        }
                    }
                //getExecutor().getCurrentState().dispose();
                //livingThing.getExecutor().getModel().
            }else if(cmd.getAttackType()== AttackType.ARROW){
                AttackManager.add(new Ball(1,new GL_Vector(livingThing.getPosition().x,livingThing.getPosition().y+1.5f,livingThing.getPosition().z),
                        livingThing.getViewDir().getClone(), 5.1f,ItemType.arrow,livingThing
                ));
                this.livingThing.changeState( new ShootState(this.livingThing));
                if(livingThing.getTarget()!=null){
                    livingThing.getTarget().beAttack(cmd.getAttackValue());

                    Vector2f xy = OpenglUtils.wordPositionToXY(projection,livingThing.getTarget().getPosition(),GamingState.instance.camera.Position,GamingState.instance.camera.getViewDir());
                    AttackManager.addText(new TimeString("箭伤"+cmd.getAttackValue(),xy.x* Constants.WINDOW_WIDTH,xy.y*Constants.WINDOW_HEIGHT));
                    Document.needUpdate=true;
                }

            }
            /*if(this.livingThing!=null && this.livingThing.getTarget()!=null ) {
                GL_Vector direction =  GL_Vector.sub(this.livingThing.getTarget().position,
                        this.livingThing.position);
                if(GL_Vector.length(direction)>220){
                    LogUtil.println("超出距离");
                    return;
                }
                if(ItemUtil.isFarWeapon(livingThing.getHandEquip()*//*.getItemDefinition().getItemType()*//*.ordinal())){
                    this.livingThing.changeState( new ShootState(this.livingThing));

                }
               *//* if(livingThing.getMainWeapon()!=null && livingThing.getMainWeapon().getName().equals("arch")){
                   this.livingThing.changeState( new IdleShootState(this.livingThing));

                }*//*

            }*/

            }
        }
    public void preCheck(){
        if(this.disposed){
            LogUtil.err("已经结束了");
        }
    }
    public void  diedProcessor(){
        if(livingThing.isDied()){
            this.livingThing.changeState( new DiedState(this.livingThing));
            LogUtil.println(livingThing.getId()+"已经死去");
        }
    }
    public void noTargetProcessor(){
        if(livingThing.getTarget() ==null && livingThing.getTargetId() ==0){
            this.livingThing.changeState( new IdleState(livingThing));
            LogUtil.println(livingThing.getId()+"正变得平静");
        }
    }

    public void commonCmdReceive(GameCmd gameCmd){
        if(gameCmd .getCmdType() == CmdType.DIED) {

            this.livingThing.changeState( new DiedState(this.livingThing));

        }else if(gameCmd .getCmdType() == CmdType.ATTACK) {

            Long notTime = TimeUtil.getNowMills();
            if(notTime-livingThing.getLastAttackTime() >intervalTime){
                livingThing.setLastAttackTime(notTime);
            }else{
                return;
            }
            /*Wep
            SkillDefinition skill = livingThing.getNowSkill();
            if(livingThing.getNowSkill()){

            }*/
            AttackCmd cmd =(AttackCmd) gameCmd;
            if(cmd.getAttackType()== AttackType.KAN){
                livingThing.changeAnimationState("kan");

                livingThing.getTarget().beAttack(cmd.getAttackValue());
                Document.needUpdate=true;
                //getExecutor().getCurrentState().dispose();
                //livingThing.getExecutor().getModel().
            }else if(cmd.getAttackType()== AttackType.ARROW){
                this.livingThing.changeState( new ShootState(this.livingThing));
                livingThing.getTarget().beAttack(cmd.getAttackValue());
            }

        }
    }
    public void commonUpdate(){

    }


}
