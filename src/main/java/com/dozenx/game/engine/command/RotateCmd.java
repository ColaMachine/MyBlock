package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class RotateCmd extends UserBaseCmd{
    float from;

    float to;

    public boolean stop = false;
    public RotateCmd(byte[] bytes){
        parse(bytes);
    }
    public RotateCmd(float from ,float to ,int userId){

        this.from = from ;
        this.to =to;
        this.userId =userId;

    }

    @Override
    public byte[] toBytes(){
        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)
                .put(this.from)
                .put(this.to)

                .put(this.stop)
                .array();

    }
    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();



        this.from = byteBufferWrap.getFloat();
        this.to = byteBufferWrap.getFloat();
        this.stop = byteBufferWrap.getBoolean();
    }
    private CmdType cmdType =CmdType.ROTATE;
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
