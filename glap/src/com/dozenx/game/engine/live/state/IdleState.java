package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.AttackType;
import com.dozenx.game.engine.command.DropCmd;
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


    public void dispose(){

    }
    public void update(){

    }

    public IdleState(LivingThingBean livingThing){
        this.livingThing = livingThing;
    }
    public void receive(GameCmd gameCmd){
       super.receive(gameCmd);

    }



}
