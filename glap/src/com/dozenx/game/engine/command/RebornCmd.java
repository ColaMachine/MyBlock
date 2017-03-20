package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class RebornCmd extends UserBaseCmd{

    public RebornCmd(byte[] bytes){
        parse(bytes);
    }
    public RebornCmd(GL_Vector from, GL_Vector to, int userId){

        this.userId =userId;

    }

    @Override
    public byte[] toBytes(){
        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)

                .array();

    }
    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();



    }
    private CmdType cmdType =CmdType.REBORN;
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
