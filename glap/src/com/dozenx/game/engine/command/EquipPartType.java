package com.dozenx.game.engine.command;

/**
 * Created by dozen.zhang on 2017/2/13.
 */
public enum EquipPartType {
    HEAD,
    BODY,
    HAND,
  LHAND,
    RHAND,
    LEG,

    LLEG,
    RLEG,
    FOOT,



    LFOOT,
    RFOOT

    ;

    public  byte getType(){
        return (byte)this.ordinal();
    }

}
