package com.dozenx.game.engine.live.state;

import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.command.ChaseCmd;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;

/**
 * Created by luying on 17/2/7.
 */
public class ChaseState extends State {
    //private LivingThing livingThing;
    long startTime;
    public ChaseState(LivingThingBean livingThing,GameCmd gameCmd){

        super(livingThing);

        ChaseCmd chaseCmd = (ChaseCmd) gameCmd;
        //livingThing.setTargetId(chaseCmd.getTargetId());
        //livingThing.setTarget(livingThingMa);
        lastMoveTime = startTime = TimeUtil.getNowMills();
        livingThing.changeAnimationState("walkerFoward");
        /*if(GamingState.player!=null){
            CoreRegistry.get(AnimationManager.class).apply( livingThing.getModel().bodyComponent, "walkerFoward");
        }*/

    }
    //任何命令都应该是一次性的不应该保存任何内部状态,或者状态的
    public void receive(GameCmd gameCmd){
        super.receive(gameCmd);
    //11
         /*if(gameCmd .getCmdType() == CmdType.DIED) {
            this.livingThing.changeState( new DiedState(this.livingThing));

        }else if(gameCmd .getCmdType() == CmdType.ATTACK) {

            Long notTime = TimeUtil.getNowMills();
            if(notTime-livingThing.getLastAttackTime() >intervalTime){
                livingThing.setLastAttackTime(notTime);
            }else{
                return;
            }
            *//*Wep
            SkillDefinition skill = livingThing.getNowSkill();
            if(livingThing.getNowSkill()){

            }*//*
            AttackCmd cmd =(AttackCmd) gameCmd;
            if(cmd.getAttackType()== AttackType.KAN){
                CoreRegistry.get(AnimationManager.class).apply(livingThing.getModel().bodyComponent,"kan");
                livingThing.getTarget().beAttack(cmd.getAttackValue());
                Document.needUpdate=true;
                //getExecutor().getCurrentState().dispose();
                //livingThing.getExecutor().getModel().
            }else if(cmd.getAttackType()== AttackType.ARROW){
                this.livingThing.changeState( new ShootState(this.livingThing));
                livingThing.getTarget().beAttack(cmd.getAttackValue());
            }

        }*/
    }
    long lastMoveTime ;
    @Override
    public void update(){
        preCheck();
        diedProcessor();
        noTargetProcessor();
        long nowTime = TimeUtil.getNowMills();
        if (nowTime - lastMoveTime >1000) {
            //Player player= CoreRegistry.get(Player.class);
            //AnimationManager manager = CoreRegistry.get(AnimationManager.class);
            //manager.apply(getModel().bodyComponent, "walkerFoward");
            try {
               LivingThingManager.chaseCanAttack(livingThing,nowTime-lastMoveTime);
               // GL_Vector walkDir = GL_Vector.sub(livingThing.getTarget().getPosition(), livingThing.getPosition()).normalize();
                /*GL_Vector distanceVector = GL_Vector.sub(livingThing.getTarget().getPosition(), livingThing.getPosition());
                float distance = GL_Vector.length(distanceVector);
                if(distance<2){
                    return;
                }
                GL_Vector walkDir  = distanceVector.normalize();
                GL_Vector walkDistance= GL_Vector.multiplyWithoutY(walkDir,
                        0.2f* (nowTime-lastMoveTime)/200);
                if(GL_Vector.length(walkDistance)<distance){
                    livingThing.setPosition(GL_Vector.add(livingThing.position, walkDistance));
                }else{
                    livingThing.setPosition(livingThing.getTarget().getPosition().getClone());
                }*/


            }catch (Exception e ){
                e.printStackTrace();
            }
           /* if(GL_Vector.length(GL_Vector.sub(livingThing.getPosition(),from))>distance){
                this.livingThing.changeState( new IdleState(this.livingThing));
            }*/
//        LogUtil.println(position+"");
            lastMoveTime = TimeUtil.getNowMills();
            // System.out.printf("position: %f %f %f viewdir: %f %f %f
            // \r\n",position.x,position.y,position.z,ViewDir.x,ViewDir.y,ViewDir.z);
            //}
        }

       // livingThing.setPosition(G);


    }
}
