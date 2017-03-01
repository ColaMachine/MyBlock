package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.AttackManager;
import cola.machine.game.myblocks.skill.Ball;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.ui.inventory.control.InventoryController;
import com.dozenx.game.network.server.bean.LivingThingBean;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class ShootState extends IdleState {
    public ShootState(LivingThingBean livingThing){
        super(livingThing);
        this.livingThing = livingThing;
        startTime=System.currentTimeMillis();
        if(CoreRegistry.get(InventoryController.class).has("arrow")){
            livingThing.addHandEquip(TextureManager.getItemDefinition("arrow"));
        }
        CoreRegistry.get(AnimationManager.class).clear(livingThing.bodyComponent);
        CoreRegistry.get(AnimationManager.class).apply(livingThing.bodyComponent,"shoot");
    }
    protected LivingThingBean livingThing;
    public long startTime;
    boolean shooted=false;
    public int currentState =0;
    public void update(){

        Long now=System.currentTimeMillis();
        if(!shooted) {
            if (currentState == 0 && now - startTime > 1 * 1000) {
//arch1
                currentState = 1;
                livingThing.bodyComponent.findChild("weapon").itemDefinition = TextureManager.getItemDefinition(ItemType.arch1);
                livingThing.setHandEquip();

            } else if (currentState == 1 && now - startTime > 3 * 1000) {
//arch2
                currentState++;
                livingThing.bodyComponent.findChild("weapon").itemDefinition = TextureManager.getItemDefinition(ItemType.arch2);

            } else if (currentState == 2 && now - startTime > 5 * 1000) {

                currentState++;
                livingThing.bodyComponent.findChild("weapon").itemDefinition = TextureManager.getItemDefinition(ItemType.arch3);

            } else if (currentState == 3 && now - startTime > 7 * 1000) {

                currentState = 4;
                livingThing.bodyComponent.findChild("weapon").itemDefinition = TextureManager.getItemDefinition(ItemType.arch);
                Ball ball = new Ball(this.livingThing.position.getClone(), GL_Vector.sub(this.livingThing.getTarget().position,
                        this.livingThing.position), 10, TextureManager.getShape("arrow"),livingThing);
                shooted = true;
                AttackManager.add(ball);
            }
        }

        if (now - startTime > 8 * 1000) {
            this.livingThing.changeState(new IdleState(this.livingThing));

        }

    }
    public void dispose(){
        CoreRegistry.get(AnimationManager.class).clear(livingThing.bodyComponent);
    }
    public void receive(GameCmd gameCmd){


    }



}
