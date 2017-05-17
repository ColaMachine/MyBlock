package com.dozenx.game.engine.command;

import com.dozenx.game.network.server.handler.GameServerHandler;
import com.dozenx.game.network.server.handler.LoginHandler;
import com.dozenx.game.network.server.handler.SayHandler;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;
import sun.plugin2.main.server.ResultHandler;

/**
 * Created by dozen.zhang on 2017/2/13.
 */
public enum CmdType {
    SAY,//0
    RESULT,//1
    MSG,//2
    LOGIN,//3
    EQUIP,//4
    POS,//5
    PLAYERSTATUS,//6
    BAG,//7
    BAGCHANGE,//8
    GET,//9
    PICK,//10
    ATTACK,//1
    DROP,//12
    GROUP,//13
    CHUNKREQUEST,//14
    CHUNKRESPONSE,//15
    CHUNKS,//16
    CHUNKSS,//17
    BACK,
    LIVI,
    DIED,
    REBORN,
    CHASE,
    JUMP,

    WALK;

    /*public CmdType(class<T> extends BaseGameCmd,class<T> extends GameServerHandler){


    }*/
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

