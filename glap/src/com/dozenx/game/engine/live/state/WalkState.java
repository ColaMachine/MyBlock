package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.WalkCmd;
import com.dozenx.game.network.server.bean.LivingThingBean;

/**
 * Created by luying on 17/2/7.
 */
public class WalkState extends IdleState {
    //private LivingThing livingThing;
    float speedForward;
    float speedLeft;
    float speedRight;
    float speedBack;
    long forwardPressedTime;
    long leftTime;
    long backTime;
    public WalkState(LivingThingBean livingThing){
        super(livingThing);
    }
    //任何命令都应该是一次性的不应该保存任何内部状态,或者状态的
    public void receive(GameCmd gameCmd){//11
        if(gameCmd instanceof WalkCmd){
            if(gameCmd.val()== WalkCmd.FORWARD ){

            }
            //gameCmd.delete();
            if(true){

            }
        }
    }
    public void update(){

    }
}
