package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.game.engine.item.ItemUtil;
import com.dozenx.game.network.server.bean.LivingThingBean;
import core.log.LogUtil;
import com.dozenx.game.engine.command.AttackCmd;
import com.dozenx.game.engine.command.GameCmd;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class IdleState extends State{
    public void dispose(){

    }
    public void update(){

    }
    protected LivingThingBean livingThing;
    public IdleState(LivingThingBean livingThing){
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
                if(ItemUtil.isFarWeapon(livingThing.getHandEquip())){
                    this.livingThing.changeState( new ShootState(this.livingThing));

                }
               /* if(livingThing.getMainWeapon()!=null && livingThing.getMainWeapon().getName().equals("arch")){
                   this.livingThing.changeState( new IdleShootState(this.livingThing));

                }*/

            }
        }

    }



}
