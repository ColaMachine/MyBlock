package com.dozenx.game.engine.command;

/**
 * Created by luying on 17/2/7.
 */
public class BaseGameCmd  implements  GameCmd{
    final CmdType cmdType =CmdType.POS;
    private boolean deleted;

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void delete() {
        this.deleted=true;
    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

    public int val;


    @Override
    public int val(){
        return val;

    }

    public void setVal(int val){
        this.val=val;
    }
}
