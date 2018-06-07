package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;

/**
 * Created by luying on 17/2/7.
 */
public class IdleState extends State{

    long lastTime =0;

    public void update(){
        super.update();
        if(GamingState.player!=null) {
            if (TimeUtil.getNowMills() - lastTime > 5000) {
                /*if (Math.random() * 10 > 5)

                    CoreRegistry.get(AnimationManager.class).apply(this.livingThing.getModel().getRootComponent(), "run");
                    //CoreRegistry.get(AnimationManager.class).apply(this.livingThing.getModel().getRootComponent(),"wag_head");
                else*/
                    CoreRegistry.get(AnimationManager.class).apply(this.livingThing.getModel().getRootComponent(), this.livingThing.idleAnimation[(int)(Math.random()*this.livingThing.idleAnimation.length-1)]);

                lastTime = TimeUtil.getNowMills();
            }
        }

//        if(GamingState.player!=null) //只能客户端使用
//        if(!this.livingThing.isPlayer() &&!(this.livingThing instanceof Player) ){
//            int x =0;
//            int y=0;
//            int z=0;
//            x= RandomUtil.getRandom(-15,15);
//            z=  RandomUtil.getRandom(-15,15);
//            //这里不加时间间隔会导致多次重复发送
//          /*  WalkCmd2 walkCmd = new WalkCmd2(livingThing.getPosition(),new GL_Vector(x,1,z),this.livingThing.getId());
//
//            CoreRegistry.get(Client.class).send(walkCmd);*/
//        }

    }

    public IdleState(LivingThingBean livingThing,GameCmd gameCmd){
        this.livingThing = livingThing;
    }
    public void receive(GameCmd gameCmd){
       super.receive(gameCmd);

    }



}
