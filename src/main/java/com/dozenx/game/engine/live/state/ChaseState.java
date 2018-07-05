package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.command.ChaseCmd;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Vector;

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
        chase();
        long nowTime = TimeUtil.getNowMills();
//        if (nowTime - lastMoveTime >100) {
//
//            CoreRegistry.get(AnimationManager.class).apply( livingThing.getModel().getRootComponent(), "walkerFoward");
//            //Player player= CoreRegistry.get(Player.class);
//            //AnimationManager manager = CoreRegistry.get(AnimationManager.class);
//            //manager.apply(getModel().bodyComponent, "walkerFoward");
//
//            try {
//               LivingThingManager.chaseCanAttack(livingThing,nowTime-lastMoveTime);
//               // GL_Vector walkDir = GL_Vector.sub(livingThing.getTarget().getPosition(), livingThing.getPosition()).normalize();
//                /*GL_Vector distanceVector = GL_Vector.sub(livingThing.getTarget().getPosition(), livingThing.getPosition());
//                float distance = GL_Vector.length(distanceVector);
//                if(distance<2){
//                    return;
//                }
//                GL_Vector walkDir  = distanceVector.normalize();
//                GL_Vector walkDistance= GL_Vector.multiplyWithoutY(walkDir,
//                        0.2f* (nowTime-lastMoveTime)/200);
//                if(GL_Vector.length(walkDistance)<distance){
//                    livingThing.setPosition(GL_Vector.add(livingThing.position, walkDistance));
//                }else{
//                    livingThing.setPosition(livingThing.getTarget().getPosition().getClone());
//                }*/
//
//
//            }catch (Exception e ){
//                e.printStackTrace();
//            }
//           /* if(GL_Vector.length(GL_Vector.sub(livingThing.getPosition(),from))>distance){
//                this.livingThing.changeState( new IdleState(this.livingThing));
//            }*/
////        LogUtil.println(position+"");
//            lastMoveTime = TimeUtil.getNowMills();
//            // System.out.printf("position: %f %f %f viewdir: %f %f %f
//            // \r\n",position.x,position.y,position.z,ViewDir.x,ViewDir.y,ViewDir.z);
//            //}
//        }

       // livingThing.setPosition(G);


    }

    public  void chase(){
        if (this.livingThing.routes != null && livingThing.routes.size() > 0) {
            if (livingThing.getDest() == null) {
                GL_Vector dest = livingThing.routes.remove(0);
                livingThing.setDest(dest);

            } else {
                //livingThing.setDest(livingThing.routes.get(0));
            }

        }

        if (this.livingThing.getDest() != null) {
            //计算当前和目的地的距离
            distance = GL_Vector.length(GL_Vector.sub(livingThing.getPosition(), livingThing.getDest()));
            if (distance > 0.5) {
                nowTime = TimeUtil.getNowMills();
                if (GamingState.player != null) {
                    if (lastTime == 0 || nowTime - lastTime > 3000) {       //如果开始时间是0说明刚开始
                        lastTime = nowTime;  //修正第一帧 让lastTime一开始等于开始时间 防止移动超过最大距离
                        // beginTime= TimeUtil.getNowMills();

                        CoreRegistry.get(AnimationManager.class).apply(this.livingThing.getModel().getRootComponent(), "walkerFoward");
                    } // walkDir = GL_Vector.sub(livingThing.getDest(),livingThing.getPosition()).normalize();

                }

                if (nowTime - lastTime > 50) {//如果大于50ms才执行
                    //调整计算新的位置

                    float moveDistance = livingThing.speed * /*(nowTime-lastTime)*/100f / 1000;

                    //判断移动过程中是否有碰到障碍物
                    GL_Vector walkDir = GL_Vector.sub(livingThing.getDest(), livingThing.getPosition()).normalize();
                    if (!livingThing.isPlayer()) {
                        livingThing.setWalkDir(walkDir);
                    }
                    //GL_Vector newPosition = nowPosition(nowTime, lastTime, livingThing.speed, livingThing.getPosition(), livingThing.getDest());
                    //float length = GL_Vector.length(GL_Vector.sub(livingThing.getPosition(),livingThing.getDest()));
                    //float moveDistance = GL_Vector.length(GL_Vector.sub(livingThing.getPosition(),newPosition));
                       /* if(length<1){ //如果距离够近了 停止移动

                            livingThing.setDest(null);

                            return;  //退出移动
                        }else*/

                       /* if(GamingState.player==null){
                            livingThing.setDest(livingThing.getDest());
                        }*/
                    this.livingThing.speed = 3;
                    if (moveDistance > distance - 0.1f) {//如果移动举例超过了最大距离 说明到达了目的地 防止因为卡顿引起超距离移动 结束移动
                        livingThing.move(livingThing.getDest().x, livingThing.position.y, livingThing.getDest().z);
                        livingThing.setDest(null);
                        lastTime = TimeUtil.getNowMills();
                        return;
                    } else {
                        if (GamingState.player == null) {
                            //        LogUtil.println(""+livingThing.getPosition());
                        }
                        GL_Vector newPosition = GL_Vector.add(livingThing.getPosition(), GL_Vector.multiply(walkDir, moveDistance));
                        livingThing.move(newPosition.x, newPosition.y, newPosition.z);
                        if (walkDir.y == 1 || walkDir.y == -1) {
                            livingThing.setDest(null);
                        }
                        //如果长期被拒绝
                        // LogUtil.println(""+livingThing.getPosition());
                        lastTime = TimeUtil.getNowMills();
                    }
                    //LogUtil.println(newPosition.toString());

                }


                // GL_Vector dir = GL_Vector.sub(livingThing.getDest(),livingThing.getPosition());
                //根据时间和速度 起开始位置 目的地 计算当前位置


            } else {

                livingThing.setDest(null);
                //放弃移动了
                return;
            }
            //livingThing.moveTo(livingThing.getDest());
        } else {
            lastTime = 0;//归零
        }

    }
}
