package com.dozenx.game.engine.command;

import com.alibaba.fastjson.JSON;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

/**
 * Created by luying on 17/2/7.
 */
public class MsgCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.MSG;
    String msg   ;


    public MsgCmd(byte[] bytes){

        parse(bytes);
    }
    public MsgCmd(String msg){


        this.msg = msg;

    }


    //|result|length|msg|threadId|
    public byte[] toBytes(){
        //LogUtil.println(JSON.toJSONString(this));
        return ByteUtil.createBuffer().put(cmdType.getType()).putLenStr(msg).array();
    }


    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.msg = byteBufferWrap.getLenStr();

    }
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
