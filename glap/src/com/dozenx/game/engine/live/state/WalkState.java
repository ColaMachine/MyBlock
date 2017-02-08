package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.game.engine.command.GameCmd;

/**
 * Created by luying on 17/2/7.
 */
public class WalkState extends  HumanState {
    private LivingThing livingThing;
    public void receive(GameCmd gameCmd){
        if(gameCmd instanceof  WalkState){
            gameCmd.delete();
        }
    }
    public void update(){

    }
}
