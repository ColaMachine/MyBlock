package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class GetCmd extends   BaseGameCmd{

byte[] data;
    private int threadId;

    final CmdType cmdType = CmdType.GET;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public GetCmd(byte[] bytes){

        parse(bytes);
    }
    public GetCmd(GameCmd cmd ,int threadId){

        this.threadId =threadId;
        this.data = cmd.toBytes();

    }

    //|length|threadId|userName|lengthPwd|
    public byte[] toBytes(){

        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(data.length)
                .put(data)
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
        int length = byteBufferWrap.getInt();
        this.data= byteBufferWrap.getByteAry(length);
        this.threadId = byteBufferWrap.getInt();




    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
