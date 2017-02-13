package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.skill.AttackManager;
import cola.machine.game.myblocks.skill.Ball;
import cola.machine.game.myblocks.skill.SkillDefinition;
import com.dozenx.game.engine.command.AttackCmd;
import com.dozenx.game.engine.command.GameCmd;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class HumanState {
    public void dispose(){

    }
    public void update(){

    }
    protected LivingThing livingThing;
    public HumanState (LivingThing livingThing){
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
                if(livingThing.getMainWeapon()!=null && livingThing.getMainWeapon().getName().equals("arch")){
                   this.livingThing.changeState( new HumanShootState(this.livingThing));

                }

            }
        }

    }



}
