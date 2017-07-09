package com.dozenx.util;

/**
 * Created by dozen.zhang on 2016/10/10.
 */

import cola.machine.game.myblocks.engine.Constants;
import core.log.LogUtil;
import org.lwjgl.BufferUtils;

import java.io.UnsupportedEncodingException;
import java.nio.*;
import java.nio.charset.Charset;

public class ByteBufferWrap
{

    private ByteBuffer buffer;
    public ByteBufferWrap(){
        this.buffer= ByteBuffer.allocate(1256);

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
    public short getShort(){
        return buffer.getShort();
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
    public ByteBufferWrap put(byte[] ary){
        preCheck(ary.length);
        for(int i=0;i<ary.length;i++){
           // if(buffer.remaining()<10){
                if(buffer.position()>buffer.limit()-10){
                    ByteBuffer  newfloatBuffer= BufferUtils.createByteBuffer(buffer.limit() *2);
                    newfloatBuffer.put(buffer);
                  //  config.getVao().setVertices(newfloatBuffer);
                    buffer = newfloatBuffer;
                }
           // }
            buffer.put(ary[i]);
        }

        return this;
    }
    public ByteBufferWrap put(int val){
        preCheck(4);
        buffer.put(ByteUtil.getBytes(val));
        return this;
    }
    public void preCheck(int size){
        if(buffer.position()>buffer.limit()-size){
            LogUtil.err("长度快到限了");
            /*if(buffer.limit()>size) {
                ByteBuffer newbyteBuffer = BufferUtils.createByteBuffer(buffer.limit() * 2);
                buffer.flip();
                newbyteBuffer.put(buffer);
                buffer = newbyteBuffer;
            }else{*/
                int num = ( buffer.limit()+size)/1256;
                ByteBuffer newbyteBuffer = BufferUtils.createByteBuffer(1256*(num+1) );
            buffer.flip();
            newbyteBuffer.put(buffer);
            buffer = newbyteBuffer;
            //}
        }
    }
    public ByteBufferWrap put(short val){
        preCheck(4);
        buffer.put(ByteUtil.getBytes(val));
        return this;
    }
    public ByteBufferWrap put(boolean val){
        preCheck(4);
        buffer.put(val?(byte)1:(byte)0);
        return this;
    }
    public ByteBufferWrap put(float val){ preCheck(4);
        buffer.put(ByteUtil.getBytes(val));
        return this;
    }
    public ByteBufferWrap putLenStr(String str){ preCheck(str.length()*4);
        byte[] bytes=str.getBytes(Constants.CHARSET);
        buffer.put(ByteUtil.getBytes(bytes.length));
        buffer.put(bytes);
        return this;
    }
    public String getLenStr(){
        int length = this.getInt();;
        byte[] msg = new byte[length];
        try {
           // LogUtil.println(buffer.remaining()+"");
            buffer.get(msg, 0, length);
        }catch(java.nio.BufferUnderflowException e){
            LogUtil.err(e);
            throw e;
        }

        return  new String(msg,Constants.CHARSET);


    }
    public byte[] getByteAry(int length){
        byte[] bytes = new byte[length];
        for(int i = 0;i<length;i++){
            bytes[i]= this.get();
        }
        return bytes;

    }
    public byte[] getLenByteAry(){
        int length = this.getInt();;
        byte[] msg = this.getByteAry(length);
       return msg;


    }

    public byte[] array(){
       /* int length = buffer.position();
        byte[] bytes = new byte[length+4];
        byte[] lenAry = ByteUtil.getBytes(length);
        bytes[0]= lenAry[0];
        bytes[1]= lenAry[1];
        bytes[2]= lenAry[2];
        bytes[3]= lenAry[3];
        buffer.flip();

        buffer.get(bytes,4,length);*/

        int length = buffer.position();
        byte[] bytes = new byte[length];
        //byte[] lenAry = ByteUtil.getBytes(length);
       /* bytes[0]= lenAry[0];
        bytes[1]= lenAry[1];
        bytes[2]= lenAry[2];
        bytes[3]= lenAry[3];*/
        buffer.flip();

        buffer.get(bytes,0,length);


        return bytes;
    }

    public byte[] array(int index){


        int dataLen = buffer.position();
        byte[] bytes = new byte[dataLen-index];
        //byte[] lenAry = ByteUtil.getBytes(length);
       /* bytes[0]= lenAry[0];
        bytes[1]= lenAry[1];
        bytes[2]= lenAry[2];
        bytes[3]= lenAry[3];*/
        buffer.flip();

        buffer.get(bytes,index,dataLen-index);


        return bytes;
    }

    public static void main(String[] args){
        ByteBuffer buffer =ByteBuffer.allocate(256);
        buffer.put((byte)1);
        System.out.println(buffer.array().length);
    }
}
