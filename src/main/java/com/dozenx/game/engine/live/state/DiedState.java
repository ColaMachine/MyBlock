package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.model.ui.html.Document;
import com.dozenx.game.engine.command.CmdType;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.network.server.bean.LivingThingBean;

/**
 * Created by luying on 17/2/7.
 */
public class DiedState extends State {
    //private LivingThing livingThing;

    public DiedState(LivingThingBean livingThing,GameCmd gameCmd){

        super(livingThing);
        this.livingThing.nowHP=0;
        this.livingThing.nowMP=0;

        Document.needUpdate=true;
        livingThing.changeAnimationState("died");


    }
    //任何命令都应该是一次性的不应该保存任何内部状态,或者状态的
    public void receive(GameCmd gameCmd){//11
        //可以接收复活指令
        if(livingThing.nowHP>0){
            this.livingThing.changeState( new IdleState(this.livingThing,gameCmd));
        }
        if(gameCmd.getCmdType() == CmdType.REBORN){
            this.livingThing.changeState( new IdleState(this.livingThing,gameCmd));
        }else
            return;
    }
    long lastMoveTime ;
    public void update(){
       return;

       // livingThing.setPosition(G);


    }
}
