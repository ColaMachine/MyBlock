package com.dozenx.util;

/**
 * Created by dozen.zhang on 2016/10/10.
 */

import cola.machine.game.myblocks.engine.Constants;
import core.log.LogUtil;
import org.lwjgl.BufferUtils;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ByteBufferWrap2
{
    int nowIndex =0;

    private ByteBuffer buffer;

    private List<ByteBuffer> buffers= new ArrayList<ByteBuffer>();
    public ByteBufferWrap2(){
        this.buffer= ByteBuffer.allocate(1256);
        buffers.add(buffer);
    }
    public ByteBufferWrap2(byte[] bytes){
        this.buffer= ByteBuffer.wrap(bytes);
        buffers.add(buffer);
       // this.buffer.flip();

    }

    public byte get(){
        if(buffer.position()==buffer.capacity()){
            nowIndex+=1;
            buffer = buffers.get(nowIndex);
            buffer.flip();
        }

        return buffer.get();
    }
    public boolean  getBoolean(){
        return this.get()==0?false:true;
    }
    public int getInt(){

        return ByteUtil.getInt(this.getBytes(4));

    }

    public byte[] getBytes(int num){

        byte[] bytes = new byte[num];

        for(int i=0;i<num;i++){
            if(buffer.position()==buffer.capacity()){
                nowIndex+=1;
                buffer = buffers.get(nowIndex);
                buffer.flip();
            }
            bytes[i]=buffer.get();
        }
        return bytes;
    }

    public short getShort(){
        return ByteUtil.getShort(this.getBytes(2));
    }
    public char getChar(){
        return ByteUtil.getChar(this.getBytes(2));
    }

    public Float getFloat(){
        return  ByteUtil.getFloat(this.getBytes(4));
    }
    public ByteBufferWrap2 put(byte val){

        if(buffer.position()==buffer.limit()){

            nowIndex++;
            buffer = ByteBuffer.allocate(1256);
            buffers.add(buffer);
        }
        buffer.put(val);
        return this;
    }
    public ByteBufferWrap2 put(byte[] ary){
        preCheck(ary.length);
        for(int i=0;i<ary.length;i++){
           // if(buffer.remaining()<10){
               /* if(buffer.position()>buffer.limit()-10){
                    ByteBuffer  newfloatBuffer= BufferUtils.createByteBuffer(buffer.limit() *2);
                    newfloatBuffer.put(buffer);
                  //  config.getVao().setVertices(newfloatBuffer);
                    buffer = newfloatBuffer;
                }*/


           // }
           // buffer.put(ary[i]);
            this.put(ary[i]);
        }

        return this;
    }
    public ByteBufferWrap2 put(int val){

        this.put(ByteUtil.getBytes(val));
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
    public ByteBufferWrap2 put(short val){
       // preCheck(4);
        this.put(ByteUtil.getBytes(val));
        //buffer.put(ByteUtil.getBytes(val));
        return this;
    }
    public ByteBufferWrap2 put(boolean val){
        //preCheck(4);
       // buffer.put(val?(byte)1:(byte)0);
        this.put(val?(byte)1:(byte)0);
        return this;
    }
    public ByteBufferWrap2 put(float val){ //preCheck(4);
       // buffer.put(ByteUtil.getBytes(val));
        this.put(ByteUtil.getBytes(val));
        return this;
    }
    public ByteBufferWrap2 putLenStr(String str){// preCheck(str.length()*4);
        byte[] bytes=str.getBytes(Constants.CHARSET);
        //buffer.put(ByteUtil.getBytes(bytes.length));
        //buffer.put(bytes);
        this.put(ByteUtil.getBytes(bytes.length)).put(bytes);
        return this;
    }




    public String getLenStr(){
        int length = this.getInt();
        byte[] msg =this.getBytes(length) ;//new byte[];


        return  new String(msg,Constants.CHARSET);


    }
    public byte[] get(int length){

        return this.getBytes(length);

    }
    public byte[] getLenByteAry(){
        int length = this.getInt();;
        byte[] msg = this.getBytes(length);
       return msg;


    }

    public byte[] array(){


        int length = buffer.position();
        byte[] bytes = new byte[length];

        buffer.flip();

        buffer.get(bytes,0,length);


        return bytes;
    }

    /*public byte[] array(int index){

        nowIndex=0;
        buffer = buffers.get(0);

        int dataLen = buffer.position();
        byte[] bytes = new byte[dataLen-index];
        //byte[] lenAry = ByteUtil.getBytes(length);
       *//* bytes[0]= lenAry[0];
        bytes[1]= lenAry[1];
        bytes[2]= lenAry[2];
        bytes[3]= lenAry[3];*//*
        buffer.flip();

        buffer.get(bytes,index,dataLen-index);


        return bytes;
    }*/

    public static void main(String[] args){
        ByteBuffer buffer =ByteBuffer.allocate(256);
        buffer.put((byte)1);
        System.out.println(buffer.array().length);
    }
}
