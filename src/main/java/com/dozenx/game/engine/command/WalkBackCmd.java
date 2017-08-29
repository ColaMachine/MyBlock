package com.dozenx.game.engine.command;

/**
 * Created by luying on 17/2/7.
 */
public abstract class WalkBackCmd extends BaseGameCmd{

    public WalkBackCmd(boolean forward, boolean left, boolean right, boolean back){
        
    }

    private CmdType cmdType =CmdType.WALK;
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
