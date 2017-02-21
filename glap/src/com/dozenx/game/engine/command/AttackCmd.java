package com.dozenx.game.engine.command;

import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class AttackCmd extends BaseGameCmd{
    private AttackType type;
    private CmdType cmdType = CmdType.ATTACK;

    public AttackCmd(AttackType type){

    }
    public byte[] toByte(){
        return ByteUtil.getBytes(type.ordinal());

    }
    public void parse(byte[] byteArray){
        this.type = AttackType.values()[ ByteUtil.getInt(byteArray)];
    }
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
