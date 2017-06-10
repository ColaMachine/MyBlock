package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class WalkCmd extends UserBaseCmd{
    public GL_Vector from  ;
    public GL_Vector to;

    float fromX;
    float fromY;
    float fromZ;
    float toX;
    float toY;
    float toZ;
    public int dir;
    public static int FORWARD=1;
    public static int LEFT=2;
    public static int RIGHT=3;
    public static int BACK=4;
    public WalkCmd(){

    }
    public WalkCmd(int userId ,int dir){
        this.userId =userId;
        this.dir =dir;
    }

    public WalkCmd(byte[] bytes){
        parse(bytes);
    }


    @Override
    public byte[] toBytes(){
        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)
                .put(this.dir)
                .array();

    }
    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();

       this.dir = byteBufferWrap.getInt();

    }
    private CmdType cmdType =CmdType.WALK;
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
