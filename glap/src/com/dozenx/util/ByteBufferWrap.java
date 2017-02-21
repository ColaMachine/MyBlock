package com.dozenx.util;

/**
 * Created by dozen.zhang on 2016/10/10.
 */

import cola.machine.game.myblocks.engine.Constants;

import java.io.UnsupportedEncodingException;
import java.nio.*;
import java.nio.charset.Charset;

public class ByteBufferWrap
{

    private ByteBuffer buffer;
    public ByteBufferWrap(){
        this.buffer= ByteBuffer.allocate(256);

    }
    public ByteBufferWrap(byte[] bytes){
        this.buffer= ByteBuffer.wrap(bytes);
       // this.buffer.flip();

    }

    public byte get(){
        return buffer.get();
    }
    public boolean  getBoolean(){
        return buffer.get()==0?false:true;
    }
    public int getInt(){
        return buffer.getInt();
    }
    public char getChar(){
        return buffer.getChar();
    }

    public Float getFloat(){
        return buffer.getFloat();
    }
    public ByteBufferWrap put(byte val){
        buffer.put(val);
        return this;
    }
    public ByteBufferWrap put(int val){
        buffer.put(ByteUtil.getBytes(val));
        return this;
    }
    public ByteBufferWrap put(boolean val){
        buffer.put(val?(byte)1:(byte)0);
        return this;
    }
    public ByteBufferWrap put(float val){
        buffer.put(ByteUtil.getBytes(val));
        return this;
    }
    public ByteBufferWrap putLenStr(String str){
        byte[] bytes=str.getBytes(Constants.CHARSET);
        buffer.put(ByteUtil.getBytes(bytes.length));
        buffer.put(bytes);
        return this;
    }
    public String getLenStr(){
        int length = this.getInt();;
        byte[] msg = new byte[length];
        buffer.get(msg,0,length);

        return  new String(msg,Constants.CHARSET);


    }

    public byte[] array(){
        int length = buffer.position();
        byte[] bytes = new byte[length+4];
        byte[] lenAry = ByteUtil.getBytes(length);
        bytes[0]= lenAry[0];
        bytes[1]= lenAry[1];
        bytes[2]= lenAry[2];
        bytes[3]= lenAry[3];
        buffer.flip();
        buffer.get(bytes,4,length);
        return bytes;
    }

    public static void main(String[] args){
        ByteBuffer buffer =ByteBuffer.allocate(256);
        buffer.put((byte)1);
        System.out.println(buffer.array().length);
    }
}
