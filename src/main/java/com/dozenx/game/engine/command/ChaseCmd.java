package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class ChaseCmd extends UserBaseCmd{
    int targetId;
    public ChaseCmd(byte[] bytes){
        parse(bytes);
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public ChaseCmd(int userId ,int targetId){

        this.userId =userId;

        this.targetId= targetId;

    }

    @Override
    public byte[] toBytes(){
        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)
                .put(targetId)
                .array();

    }
    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();
        this.targetId =byteBufferWrap.getInt();

    }
    private CmdType cmdType =CmdType.CHASE;
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
