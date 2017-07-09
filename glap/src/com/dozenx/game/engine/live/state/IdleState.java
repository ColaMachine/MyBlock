package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.Animation;
import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.Random;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.ItemUtil;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.MathUtil;
import com.dozenx.util.RandomUtil;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

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

    public IdleState(LivingThingBean livingThing){
        this.livingThing = livingThing;
    }
    public void receive(GameCmd gameCmd){
       super.receive(gameCmd);

    }



}
