package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 * 死亡指令
 */
public class DiedCmd extends UserBaseCmd{

    public DiedCmd(byte[] bytes){
        parse(bytes);
    }
    public DiedCmd( int userId){
       super(userId);

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
    private CmdType cmdType =CmdType.DIED;
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
