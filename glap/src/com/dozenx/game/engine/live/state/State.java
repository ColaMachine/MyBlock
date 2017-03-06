package com.dozenx.game.engine.live.state;

import com.dozenx.game.engine.command.AttackCmd;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.item.ItemUtil;
import com.dozenx.game.network.server.bean.LivingThingBean;
import core.log.LogUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/3/5.
 */
public class State {
    public void dispose(){

    }
    public void update(){

    }
    public State(){

    }
    protected LivingThingBean livingThing;
    public State(LivingThingBean livingThing){
        this.livingThing = livingThing;
    }
    public void receive(GameCmd gameCmd){
        if(gameCmd instanceof AttackCmd){
            /*Wep
            SkillDefinition skill = livingThing.getNowSkill();
            if(livingThing.getNowSkill()){

            }*/
            if(this.livingThing!=null && this.livingThing.getTarget()!=null ) {
                GL_Vector direction =  GL_Vector.sub(this.livingThing.getTarget().position,
                        this.livingThing.position);
                if(GL_Vector.length(direction)>220){
                    LogUtil.println("超出距离");
                    return;
                }
                if(ItemUtil.isFarWeapon(livingThing.getHandEquip().getItemDefinition().getItemType().ordinal())){
                    this.livingThing.changeState( new ShootState(this.livingThing));

                }
               /* if(livingThing.getMainWeapon()!=null && livingThing.getMainWeapon().getName().equals("arch")){
                   this.livingThing.changeState( new IdleShootState(this.livingThing));

                }*/

            }
        }

    }



}
