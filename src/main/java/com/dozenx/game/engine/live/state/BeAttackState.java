package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.model.ui.html.Document;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * 这个状态从被攻击开始 到 落地结束 被攻击的物体退后并飞起一段距离 期间可以 攻击别人 但是不能移动 然后又回到原来的状态
 * Created by luying on 17/2/7.
 */
public class BeAttackState extends State {


    //有几率受到击退影响
    int jitui=0;
    long jituiStartTime =0;
//    //普通攻击
//    public BeAttackState(LivingThingBean livingThing, GameCmd gameCmd){
//        //if the lvingthing is player than syn the data to server  form the data to walkcmd2
//        super(livingThing);
//        if(Math.random()>0.5){
//            //
//            LogUtil.println("受到了击退效果");
//            jitui=1;
//            jituiStartTime=TimeUtil.getNowMills();
//        }
//        int dir=1;
//        if(from== null || to == null){
//            LogUtil.err("from and to can't be null ");
//        }
//        computeDest(dir);
//        //
//
//    }
    //击退攻击

    /**
     *
     * @param livingThing  //生物的位置
     * @param from

     */
    public BeAttackState(LivingThingBean livingThing, GL_Vector dir){

        super(livingThing);
        lastMoveTime=TimeUtil.getNowMills();
        if(Math.random()>0.5){
            //
            from = livingThing.getPosition().copyClone();
            LogUtil.println("受到了击退效果");
            jitui=1;
            jituiStartTime=TimeUtil.getNowMills();

            this.walkDir =// attacker.getPosition().copyClone().sub(livingThing.getPosition());
                    dir.copyClone();
            this.startTime = TimeUtil.getNowMills();
           // livingThing.changeAnimationState("walkerFoward");
            limitDistance=2;
        }



    }
    GL_Vector from;
    //private LivingThing livingThing;

    long startTime;
    GL_Vector walkDir;
    float limitDistance ;

    //经常用的是这个 拿到对方的walcmd2 之后就用他来构造walkstate

    //任何命令都应该是一次性的不应该保存任何内部状态,或者状态的
    Queue<GameCmd> queue = new LinkedList<>();

    public void receive(GameCmd gameCmd){//11

    }
    long lastMoveTime ;

    public void update(){
        this.updatePosition();
    }
    public void updatePosition(){
        if(jitui>0) {
       if(this.disposed){
           LogUtil.err("该state 应该已经结束了 错误了");
           return;
       }
        long nowTime = TimeUtil.getNowMills();

            if (nowTime - lastMoveTime > 50 ) {
                //Player player= CoreRegistry.get(Player.class);
                //AnimationManager manager = CoreRegistry.get(AnimationManager.class);
                //manager.apply(getModel().bodyComponent, "walkerFoward");
                 GL_Vector newPosition = GL_Vector.add(from, GL_Vector.multiplyWithoutY(walkDir,
                        livingThing.speed / 10 * (nowTime - startTime) / 1000));
//            livingThing.getPosition().x=newPosition.x;
//            livingThing.getPosition().z=newPosition.z;

                //如果超过了击退的终点的位置
                float length = GL_Vector.length(GL_Vector.sub(newPosition.copyClone(), from));
                if (length > limitDistance) {
                    // this.livingThing.setPosition(this.to);

                    this.livingThing.move(from.add(walkDir.mult(limitDistance)));
                    this.livingThing.changeState(new IdleState(this.livingThing,null));

                }else {

                    this.livingThing.move(newPosition.x, livingThing.getPosition().y, newPosition.z);
                }
            /*else{

            }*/
//        LogUtil.println(position+"");
                lastMoveTime = TimeUtil.getNowMills();
                // System.out.printf("position: %f %f %f viewdir: %f %f %f
                // \r\n",position.x,position.y,position.z,ViewDir.x,ViewDir.y,ViewDir.z);
                //}
            }
        }else{
            this.livingThing.changeState(new IdleState(this.livingThing,null));
        }

       // livingThing.setPosition(G);


    }
}
