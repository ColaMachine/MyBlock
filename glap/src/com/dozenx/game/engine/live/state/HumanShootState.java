package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.AttackManager;
import cola.machine.game.myblocks.skill.Ball;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.ui.inventory.control.InventoryController;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class HumanShootState extends HumanState{
    public HumanShootState(LivingThing livingThing){
        super(livingThing);
        this.livingThing = livingThing;
        startTime=System.currentTimeMillis();
        if(CoreRegistry.get(InventoryController.class).has("arrow")){
            livingThing.addHandEquip(TextureManager.getItemDefinition("arrow"));
        }
        CoreRegistry.get(AnimationManager.class).clear(livingThing.bodyComponent);
        CoreRegistry.get(AnimationManager.class).apply(livingThing.bodyComponent,"shoot");
    }
    protected LivingThing livingThing;
    public long startTime;
    boolean shooted=false;
    public void update(){

        Long now=System.currentTimeMillis();
        if(now-startTime > 0.5*1000 && !shooted){
            Ball ball = new Ball(this.livingThing.position.getClone(), GL_Vector.sub(this.livingThing.getTarget().position,
                    this.livingThing.position), 10, TextureManager.getShape("arrow"));
            shooted=true;
            AttackManager.add(ball);
        }
        if(now-startTime > 3*1000){
            this.livingThing.changeState( new HumanState(this.livingThing));

        }

    }
    public void dispose(){
        CoreRegistry.get(AnimationManager.class).clear(livingThing.bodyComponent);
    }
    public void receive(GameCmd gameCmd){


    }



}
