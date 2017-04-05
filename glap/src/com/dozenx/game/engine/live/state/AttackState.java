package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.AttackManager;
import cola.machine.game.myblocks.skill.Ball;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.ui.inventory.control.BagController;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class AttackState extends IdleState {
    public AttackState(LivingThingBean livingThing){
        super(livingThing);
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


    }
    public void dispose(){

            livingThing.changeAnimationState(null);
       // CoreRegistry.get(AnimationManager.class).clear(livingThing.bodyComponent);
    }
    public void receive(GameCmd gameCmd){


    }



}