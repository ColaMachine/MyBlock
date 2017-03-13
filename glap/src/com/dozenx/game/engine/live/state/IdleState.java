package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.AttackType;
import com.dozenx.game.engine.item.ItemUtil;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import com.dozenx.game.engine.command.AttackCmd;
import com.dozenx.game.engine.command.GameCmd;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class IdleState extends State{

    int intervalTime=500;
    public void dispose(){

    }
    public void update(){

    }
    protected LivingThingBean livingThing;
    public IdleState(LivingThingBean livingThing){
        this.livingThing = livingThing;
    }
    public void receive(GameCmd gameCmd){
        Long notTime = TimeUtil.getNowMills();
        if(notTime-livingThing.getLastAttackTime() >intervalTime){
            livingThing.setLastAttackTime(notTime);
        }else{
            return;
        }
        if(gameCmd instanceof AttackCmd){
            /*Wep
            SkillDefinition skill = livingThing.getNowSkill();
            if(livingThing.getNowSkill()){

            }*/
            AttackCmd cmd =(AttackCmd) gameCmd;
            if(cmd.getAttackType()== AttackType.KAN){
                CoreRegistry.get(AnimationManager.class).apply(livingThing.getModel().bodyComponent,"kan");
                livingThing.getTarget().beAttack(cmd.getAttackValue());
                Document.needUpdate=true;
                //getExecutor().getCurrentState().dispose();
                //livingThing.getExecutor().getModel().
            }
            /*if(this.livingThing!=null && this.livingThing.getTarget()!=null ) {
                GL_Vector direction =  GL_Vector.sub(this.livingThing.getTarget().position,
                        this.livingThing.position);
                if(GL_Vector.length(direction)>220){
                    LogUtil.println("超出距离");
                    return;
                }
                if(ItemUtil.isFarWeapon(livingThing.getHandEquip()*//*.getItemDefinition().getItemType()*//*.ordinal())){
                    this.livingThing.changeState( new ShootState(this.livingThing));

                }
               *//* if(livingThing.getMainWeapon()!=null && livingThing.getMainWeapon().getName().equals("arch")){
                   this.livingThing.changeState( new IdleShootState(this.livingThing));

                }*//*

            }*/
        }

    }



}
