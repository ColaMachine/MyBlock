package com.dozenx.game.engine.command;

/**
 * Created by luying on 17/2/7.
 */
public class AttackArrowCmd extends BaseGameCmd{
    private CmdType cmdType = CmdType.ATTACK;
    public AttackArrowCmd(){
            
    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
