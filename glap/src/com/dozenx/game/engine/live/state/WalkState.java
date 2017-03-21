package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;
import org.lwjgl.Sys;

/**
 * Created by luying on 17/2/7.
 */
public class WalkState extends State {
    //private LivingThing livingThing;
    float speedForward;
    float speedLeft;
    float speedRight;
    float speedBack;
    long forwardPressedTime;
    long leftTime;
    long backTime;
    GL_Vector from ;
    GL_Vector to;
    long startTime;
    GL_Vector walkDir;
    float distance ;
    public WalkState(LivingThingBean livingThing, GL_Vector from ,GL_Vector to ){

        super(livingThing);
        if(from== null || to == null){
            LogUtil.err("from and to can't be null ");
        }
        this.from = from.getClone();
        this.to =to.getClone();

        this.startTime = TimeUtil.getNowMills();
        walkDir = GL_Vector.sub(to,from);

        this.distance = GL_Vector.length(walkDir);
        this.walkDir.normalize();
        livingThing.walkDir =walkDir;
        livingThing.setBodyAngle(GL_Vector.getAnagleFromXZVectory(walkDir));
        livingThing.changeAnimationState("walkerFoward");

    }
    //任何命令都应该是一次性的不应该保存任何内部状态,或者状态的
    public void receive(GameCmd gameCmd){//11
        if(gameCmd .getCmdType() == CmdType.WALK){

            if(from== null || to == null){
                LogUtil.err("from and to can't be null ");
            }
            this.from = from.getClone();
            this.to =to.getClone();

            this.startTime = TimeUtil.getNowMills();
            walkDir = GL_Vector.sub(to,from);

            this.distance = GL_Vector.length(walkDir);
            this.walkDir.normalize();
            livingThing.walkDir =walkDir;
            livingThing.setBodyAngle(GL_Vector.getAnagleFromXZVectory(walkDir));
            livingThing.changeAnimationState("walkerFoward");

            /*if(gameCmd.val()== WalkCmd.FORWARD ){

            }*/
            //gameCmd.delete();


        }else if(gameCmd .getCmdType() == CmdType.DIED) {
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
    long lastMoveTime ;
    public void update(){
       if(this.disposed){
           LogUtil.err("该state 应该已经结束了 错误了");
           return;
       }
        long nowTime = TimeUtil.getNowMills();
        if (nowTime - lastMoveTime >200) {
            //Player player= CoreRegistry.get(Player.class);
            //AnimationManager manager = CoreRegistry.get(AnimationManager.class);
            //manager.apply(getModel().bodyComponent, "walkerFoward");
            livingThing.setPosition(GL_Vector.add(from, GL_Vector.multiplyWithoutY(walkDir,
                    0.2f* (nowTime-startTime)/200)));
            if(GL_Vector.length(GL_Vector.sub(livingThing.getPosition(),from))>distance){
                this.livingThing.changeState( new IdleState(this.livingThing));
            }
//        LogUtil.println(position+"");
            lastMoveTime = TimeUtil.getNowMills();
            // System.out.printf("position: %f %f %f viewdir: %f %f %f
            // \r\n",position.x,position.y,position.z,ViewDir.x,ViewDir.y,ViewDir.z);
            //}
        }

       // livingThing.setPosition(G);


    }
}
