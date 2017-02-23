package com.dozenx.game.engine.command;

import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

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
    GET,
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


    public static void printSend(GameCmd type){


        if(type.getCmdType() != CmdType.POS){
            LogUtil.println("send "+type.getCmdType());
        }
    }

    public static void printReceive(GameCmd type){
        if(type.getCmdType() != CmdType.POS) {
            LogUtil.println("receive " + type.getCmdType());
        }
    }

    public static void printSend(byte[] bytes){
        //LogUtil.println("send "+CmdType.values()[ByteUtil.getInt(bytes)]);
        if(ByteUtil.getInt(bytes) != CmdType.POS.ordinal()){
            LogUtil.println("send "+CmdType.values()[ByteUtil.getInt(bytes) ] +" content"+bytes.length);
        }
    }

    public static void printReceive(byte[] bytes){
        if(ByteUtil.getInt(bytes) != CmdType.POS.ordinal()){
            LogUtil.println("receive "+CmdType.values()[ByteUtil.getInt(bytes)]+" content"+bytes.length);
        }

    }
}