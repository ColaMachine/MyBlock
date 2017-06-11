package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class WalkCmd2 extends UserBaseCmd{
    public GL_Vector from  ;
    public GL_Vector to;
    public GL_Vector walkDir;
    float fromX;
    float fromY;
    float fromZ;
    float toX;
    float toY;
    float toZ;
    public float bodyAngle;
    public static int FORWARD=1;
    public static int LEFT=2;
    public static int RIGHT=3;
    public static int BACK=4;
    public boolean stop = false;
    public WalkCmd2(byte[] bytes){
        parse(bytes);
    }
    public WalkCmd2(GL_Vector from, GL_Vector to, int userId){
        this.fromX = from.x;
        this.fromY =from .y;
        this.fromZ = from .z;
        this.toX= to.x;
        this.toY = to .y;
        this.toZ = to .z;
        this.from = from ;
        this.to =to;
        this.userId =userId;

    }

    @Override
    public byte[] toBytes(){
        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)
                .put(this.fromX)
                .put(this.fromY)
                .put(this.fromZ)
                .put(this.toX)
                .put(this.toY)
                .put(this.toZ)
                .put(this.stop)
                .put(this.bodyAngle)
                .array();

    }
    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();

        this.fromX = byteBufferWrap.getFloat();
        this.fromY = byteBufferWrap.getFloat();
        this.fromZ = byteBufferWrap.getFloat();
        this.toX = byteBufferWrap.getFloat();
        this.toY = byteBufferWrap.getFloat();
        this.toZ = byteBufferWrap.getFloat();
        this.stop = byteBufferWrap.getBoolean();
        this.bodyAngle = byteBufferWrap.getFloat();
        this.from = new GL_Vector( this.fromX,this.fromY,this.fromZ);
        this.to = new GL_Vector( this.toX,this.toY,this.toZ);

    }
    private CmdType cmdType =CmdType.WALK2;
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
