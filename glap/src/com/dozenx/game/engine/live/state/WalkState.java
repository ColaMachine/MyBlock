package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;
import com.google.common.collect.LinkedListMultimap;
import core.log.LogUtil;
import glmodel.GL_Vector;
import org.lwjgl.Sys;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by luying on 17/2/7.
 */
public class WalkState extends State {


    public WalkState(LivingThingBean livingThing, int dir ){
        //if the lvingthing is player than syn the data to server  form the data to walkcmd2
        super(livingThing);
        if(from== null || to == null){
            LogUtil.err("from and to can't be null ");
        }
        computeDest(dir);
        //

    }

    public WalkState(LivingThingBean livingThing, GL_Vector from ,GL_Vector to ){

        super(livingThing);
        if(from== null || to == null){
            LogUtil.err("from and to can't be null ");
        }

        computeFromTo(from,to);

    }
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
    public void computeFromTo(GL_Vector from,GL_Vector to){
        this.from = from.getClone();
        this.livingThing.position=from.getClone();//发现人物的位置经常不能同步
        this.to =to.getClone();
        this.startTime = TimeUtil.getNowMills();
        walkDir = GL_Vector.sub(to,from);

        this.distance = GL_Vector.length(walkDir);
        this.walkDir.normalize();
        if( !livingThing .isPlayer()) {
            livingThing.walkDir = walkDir.getClone();
            livingThing.setBodyAngle(GL_Vector.getAnagleFromXZVectory(walkDir));
        }
        livingThing.changeAnimationState("walkerFoward");
    }

    public void computeDest(int dir){
       walkDir =  livingThing.getWalkDir().normalize().getClone();
        livingThing.setRightVector(GL_Vector.crossProduct(livingThing.walkDir, livingThing.upVector));
        GL_Vector add= null;
        if(dir == WalkCmd.FORWARD){
            add=livingThing.getWalkDir().normalize().getClone().mult(livingThing.speed);
        }else if(dir == WalkCmd.RIGHT){
            add=livingThing.getRightVector().getClone().mult(livingThing.speed);
        }else if(dir == WalkCmd.LEFT){
            add=livingThing.getRightVector().getClone().mult(-livingThing.speed);
        }else if(dir == WalkCmd.BACK){
            add=livingThing.getWalkDir().getClone().mult(-livingThing.speed);
        }

        this.to =livingThing.getPosition().getClone().add(add);

        computeFromTo(livingThing.getPosition(),to);


    }
    //任何命令都应该是一次性的不应该保存任何内部状态,或者状态的
    Queue<GameCmd> queue = new LinkedList<>();

    public void receive(GameCmd gameCmd){//11
        if(gameCmd .getCmdType() == CmdType.WALK2){
            queue.clear();
            this.queue.offer(gameCmd);

            WalkCmd2 walkCmd2 =(WalkCmd2)gameCmd;
            this.livingThing.setBodyAngle(walkCmd2.bodyAngle);

            if(walkCmd2.stop==true){
                this.livingThing.setPosition(walkCmd2.from.x,walkCmd2.from.y,walkCmd2.from.z);
                this.livingThing.changeState(new IdleState(this.livingThing));
                return;
            }
            computeFromTo(walkCmd2.from,walkCmd2.to);
        }else
       if(gameCmd .getCmdType() == CmdType.WALK){
           queue.clear();
            this.queue.offer(gameCmd);

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
                //这里要判断target 是否为null

                if(livingThing.getTarget()!=null){
                    livingThing.getTarget().beAttack(cmd.getAttackValue());
                    Document.needUpdate=true;
                }

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
        if (nowTime - lastMoveTime >50) {
            //Player player= CoreRegistry.get(Player.class);
            //AnimationManager manager = CoreRegistry.get(AnimationManager.class);
            //manager.apply(getModel().bodyComponent, "walkerFoward");
           GL_Vector newPosition =  GL_Vector.add(from, GL_Vector.multiplyWithoutY(walkDir,
                    livingThing.speed/10* (nowTime-startTime)/1000));
//            livingThing.getPosition().x=newPosition.x;
//            livingThing.getPosition().z=newPosition.z;

            //如果newposition 的位置
            if(GL_Vector.length(GL_Vector.sub(livingThing.getPosition(),from))>distance){
               // this.livingThing.setPosition(this.to);

                this.livingThing.move(this.to.x,livingThing.getPosition().y,this.to.z);
                if(queue.peek()!=null){
                    //从新开始
                    GameCmd gameCmd =queue.poll();
                    if(gameCmd.getCmdType()==CmdType.WALK){
                        computeDest(((WalkCmd)gameCmd).dir);
                    }else{
                        computeFromTo(((WalkCmd2)gameCmd).from,((WalkCmd2)gameCmd).to);
                    }
                }else {
                    this.livingThing.changeState(new IdleState(this.livingThing));
                }
            }

            this.livingThing.move(newPosition.x,livingThing.getPosition().y,newPosition.z);
            /*else{
                this.livingThing.changeState(new IdleState(this.livingThing));
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
