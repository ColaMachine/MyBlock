package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.CmdType;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.WalkCmd;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class ChaseAttackState extends State {
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
    public ChaseAttackState(LivingThingBean livingThing, GL_Vector from, GL_Vector to){

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
        if(GamingState.player!=null){
            CoreRegistry.get(AnimationManager.class).apply( livingThing.getModel().bodyComponent, "walkerFoward");
        }

    }
    //任何命令都应该是一次性的不应该保存任何内部状态,或者状态的
    public void receive(GameCmd gameCmd){//11
        if(gameCmd .getCmdType()== CmdType.DIED){
            this.livingThing.changeState( new DiedState(this.livingThing));
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
            walkDir = GL_Vector.sub(livingThing.getTarget().getPosition(),livingThing.getPosition()).normalize();
            livingThing.setPosition(GL_Vector.add(livingThing.position, GL_Vector.multiplyWithoutY(walkDir,
                    0.2f* (nowTime-lastMoveTime)/200)));
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
