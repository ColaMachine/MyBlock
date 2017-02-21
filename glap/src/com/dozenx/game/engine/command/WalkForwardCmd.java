package com.dozenx.game.engine.command;

/**
 * Created by luying on 17/2/7.
 */
public class WalkForwardCmd extends BaseGameCmd{

    public WalkForwardCmd(boolean forward, boolean left, boolean right, boolean back){
        
    }
    private CmdType cmdType =CmdType.WALK;
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
