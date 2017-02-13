package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
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
            Ball ball =new Ball(this.livingThing.position.getClone(), GL_Vector.sub(this.livingThing.target.position,
                    this.livingThing.position),1,TextureManager.getShape("arrow"));
            AttackManager.add(ball);
        }

    }



}
