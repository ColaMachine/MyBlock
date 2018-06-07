package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.ui.inventory.control.BagController;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;

/**
 * Created by luying on 17/2/7.
 */
public class AttackState extends IdleState {
    public AttackState(LivingThingBean livingThing,GameCmd gameCmd){
        super(livingThing,gameCmd);
        this.livingThing = livingThing;
        startTime= TimeUtil.getNowMills();
        if(CoreRegistry.get(BagController.class).has("arrow")){
           // livingThing.addHandEquip(TextureManager.getItemDefinition("arrow"));
        }
        //state 不应该直接操作 animationmanager 那放在哪里好呢 ,放在changeanimationcallback里?

        livingThing.changeAnimationState("shoot");

    }
    protected LivingThingBean livingThing;
    public long startTime;
    boolean shooted=false;
    public int currentState =0;
    public void update(){
        if(TimeUtil.getNowMills()-lastTime >1000){
            livingThing.changeState(new IdleState(livingThing,null));
        }
        //过3秒 就结束

    }
    public void dispose(){

            livingThing.changeAnimationState(null);
       // CoreRegistry.get(AnimationManager.class).clear(livingThing.bodyComponent);
    }
    public void receive(GameCmd gameCmd){


    }



}
