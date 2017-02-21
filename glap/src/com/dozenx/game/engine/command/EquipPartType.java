package com.dozenx.game.engine.command;

/**
 * Created by dozen.zhang on 2017/2/13.
 */
public enum EquipPartType {
  /*  LHAND,
    RHAND,*/
    LEG,
    SHOE,
    BODY,
/*
    LLEG,
    RLEG,

    LSHOE,
    RSHOE,*/
    HEAD,
    HAND;

    public  byte getType(){
        return (byte)this.ordinal();
    }

}
