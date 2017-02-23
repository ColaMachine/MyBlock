package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class GetCmd extends   BaseGameCmd{


    private int threadId;

    final CmdType cmdType = CmdType.LOGIN;

    public GetCmd(byte[] bytes){

        parse(bytes);
    }
    public GetCmd(int threadId){

        this.threadId =threadId;

    }

    //|length|threadId|userName|lengthPwd|
    public byte[] toBytes(){

        return ByteUtil.createBuffer().put(cmdType.getType())

                .put(this.threadId)
               .array();
        // ByteUtil.createBuffer().put(userNameLength);

    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }


    public void parse(byte[] bytes){

        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.threadId = byteBufferWrap.getInt();




    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
