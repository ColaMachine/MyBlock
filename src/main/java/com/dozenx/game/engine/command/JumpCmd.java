package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class JumpCmd extends UserBaseCmd{

   public  float fromX;//起跳的位置
    public float fromY;
    public float fromZ;
    public float dirX;
    public float dirY;
    public float dirZ;//跳的方向
    public float speed;//速度

    public JumpCmd(byte[] bytes){
        parse(bytes);
    }
    public JumpCmd(GL_Vector from, GL_Vector dir, int userId,float speed){
        this.fromX = from.x;
        this.fromY =from .y;
        this.fromZ = from .z;
        this.dirX= dir.x;
        this.dirY = dir .y;
        this.dirZ = dir .z;
        this.speed = speed;
        this.userId =userId;

    }

    @Override
    public byte[] toBytes(){
        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)
                .put(this.fromX)
                .put(this.fromY)
                .put(this.fromZ)
                .put(this.dirX)
                .put(this.dirY)
                .put(this.dirZ)
                .put(this.speed)
                .array();

    }
    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();

        this.fromX = byteBufferWrap.getFloat();
        this.fromY = byteBufferWrap.getFloat();
        this.fromZ = byteBufferWrap.getFloat();
        this.dirX = byteBufferWrap.getFloat();
        this.dirY = byteBufferWrap.getFloat();
        this.dirZ = byteBufferWrap.getFloat();
        this.speed =byteBufferWrap.getFloat();
    }
    private CmdType cmdType =CmdType.JUMP;
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
