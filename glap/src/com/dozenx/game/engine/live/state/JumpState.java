package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.command.ChaseCmd;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.JumpCmd;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Vector;
import org.lwjgl.Sys;

/**
 * Created by luying on 17/2/7.
 */
public class JumpState extends State {
    //private LivingThing livingThing;
    long startTime;
    float speed;
    GL_Vector dir;
    public JumpState(LivingThingBean livingThing, JumpCmd jumpCmd){
        super(livingThing);
        this.speed =jumpCmd.speed;
        this.dir= new GL_Vector(jumpCmd.dirX,jumpCmd.dirY,jumpCmd.dirZ).normalize();

        //livingThing.setTargetId(chaseCmd.getTargetId());
        //livingThing.setTarget(livingThingMa);
        lastMoveTime = startTime = TimeUtil.getNowMills();
        livingThing.changeAnimationState("walkerFoward");

        if (GamingState.player.getId() == this.livingThing.getId()&&Switcher.IS_GOD) {
            this.livingThing.position.y += 2;
            GamingState.setCameraChanged(true);
        } else if (this.livingThing.isStable()) {
            this.livingThing.v = 10.2f;
         //   this.livingThing.position.y+=12;
            this.livingThing.jumpStartY = (int) this.livingThing.position.y;
            this.livingThing.lastTime = TimeUtil.getNowMills();
            //lastTime = Sys.getTime();
            this.livingThing.flip((int)this.livingThing.position.y);
            this.livingThing.setStable( false);
        }

        /*if(GamingState.player!=null){
            CoreRegistry.get(AnimationManager.class).apply( livingThing.getModel().bodyComponent, "walkerFoward");
        }*/

    }
    //任何命令都应该是一次性的不应该保存任何内部状态,或者状态的
    public void receive(GameCmd gameCmd){
        super.receive(gameCmd);

    }
    long lastMoveTime ;
    @Override
    public void update(){

        long nowTime = TimeUtil.getNowMills();

        if(this.livingThing.isStable()){
            this.livingThing.changeState(new IdleState(livingThing));
        }else
        if (nowTime - lastMoveTime >200) {
            //this.position.y+=1;
            // ��¼��ǰ��ʱ��
            this.livingThing.position=GL_Vector.add(livingThing.position,GL_Vector.multiply(dir,speed*(nowTime - lastMoveTime )/1000));
            //this.livingThing.position.add(dir,)
            lastMoveTime = TimeUtil.getNowMills();
            // System.out.printf("position: %f %f %f viewdir: %f %f %f
            // \r\n",position.x,position.y,position.z,ViewDir.x,ViewDir.y,ViewDir.z);
            //}
        }

       // livingThing.setPosition(G);
        if(nowTime - startTime >3000){

            this.livingThing.changeState(new IdleState(this.livingThing));
        }
    }
}
