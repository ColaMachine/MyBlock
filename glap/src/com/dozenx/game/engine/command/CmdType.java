package com.dozenx.game.engine.command;

/**
 * Created by dozen.zhang on 2017/2/13.
 */
public enum CmdType {
    SAY,
    RESULT,
    LOGIN,
    EQUIP,
    POS,
    PLAYERSTATUS,
    ATTACK,
    DROP,
    GROUP,
    CHUNK,
    BACK,
    LIVI,
    WALK;

    public int getType(){
        return this.ordinal();
    }
}
