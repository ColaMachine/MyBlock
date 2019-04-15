package com.dozenx.util;

/**
 * Created by dozen.zhang on 2016/10/10.
 */

import cola.machine.game.myblocks.engine.Constants;
import core.log.LogUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class ByteBufferWrap
{
    int listLimit =0;
    int listPosition;
    int limit =0;
    int position;
    public  ByteBuffer buffer;

    private List<ByteBuffer> buffers= new ArrayList<ByteBuffer>();
    public ByteBufferWrap(){
        this.buffer= ByteBuffer.allocateDirect(1256).order(ByteOrder.nativeOrder());
        buffers.add(buffer);
    }
    public ByteBufferWrap(byte[] bytes){
        this.buffer= ByteBuffer.wrap(bytes);
        buffers.add(buffer);
       // this.buffer.flip();

    }

    public byte get(){
       // buffer.position()>buffer.limit()
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

        if(buffer.position()==buffer.limit()){

            listPosition++;
            if(buffers.size()<listPosition+1){
                buffer = ByteBuffer.allocateDirect(1256).order(ByteOrder.nativeOrder());
                buffers.add(buffer);
            }else{
                buffer = buffers.get(listPosition);
                buffer.rewind();
            }


        }
       position++;

        buffer.put(val);
        return this;
    }

    public void glBufferData(int type1,int type2){
        for(int i=0;i<listLimit;i++){
            GL15.glBufferData(type1, buffers.get(i), type2);//put data
        }

    }
    public int limit(){
        return this.limit;
    }
    public void rewind(){
        this.limit = position;
        this.position =0;
        this.listLimit = listPosition;
        this.listPosition = 0;
        for(int i=0;i<=listLimit;i++){
            ByteBuffer buffer = buffers.get(i);
            buffer.rewind();
        }
        buffer = buffers.get(0);
    }
    public void flip(){
        this.limit = position;
        this.position =0;
        this.listLimit = listPosition;
        this.listPosition = 0;
        for(int i=0;i<=listLimit;i++){
            ByteBuffer buffer = buffers.get(i);
            buffer.flip();
        }
        buffer = buffers.get(0);
    }
    public void clear(){
        this.limit = position;
        this.position =0;
        this.listLimit = listPosition;
        this.listPosition = 0;
        for(int i=0;i<=listLimit;i++){
            ByteBuffer buffer = buffers.get(i);
            buffer.clear();
        }
        buffer = buffers.get(0);
    }
    public ByteBufferWrap put(byte[] ary){
        //preCheck(ary.length);
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

    public int position(){
        return position;
    }
    public ByteBufferWrap put(int val){

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
    public ByteBufferWrap put(short val){
       // preCheck(4);
        this.put(ByteUtil.getBytes(val));
        //buffer.put(ByteUtil.getBytes(val));
        return this;
    }
    public ByteBufferWrap put(boolean val){
        //preCheck(4);
       // buffer.put(val?(byte)1:(byte)0);
        this.put(val?(byte)1:(byte)0);
        return this;
    }
    public ByteBufferWrap put(float val){ //preCheck(4);
       // buffer.put(ByteUtil.getBytes(val));
        this.put(ByteUtil.getBytes(val));
        return this;
    }
    public ByteBufferWrap putLenStr(String str){// preCheck(str.length()*4);
        byte[] bytes=str.getBytes(Constants.CHARSET);
        //buffer.put(ByteUtil.getBytes(bytes.length));
        //buffer.put(bytes);
        this.put(ByteUtil.getBytes(bytes.length)).put(bytes);
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
    public long getLong(){
        long length = buffer.getLong();;


        return  length;


    }
    public ByteBufferWrap put(long val){
    buffer.put(ByteUtil.getBytes(val));;


        position+=4;
        return this;

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
        int length =0,position=0;
        for(int i =0 ;i<buffers.size();i++){
            length+= buffers.get(i).position();
        }

        byte[] bytes = new byte[length];
        //byte[] lenAry = ByteUtil.getBytes(length);
       /* bytes[0]= lenAry[0];
        bytes[1]= lenAry[1];
        bytes[2]= lenAry[2];
        bytes[3]= lenAry[3];*/
       // buffer.flip();
        for(int i =0 ;i<buffers.size();i++){
            int size = buffers.get(i).position();
            buffers.get(i).flip();
            buffers.get(i).get(bytes,position,size);
            position +=size;
        }



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
        ByteBuffer buffer =ByteBuffer.allocate(2);
        System.out.println("position:"+buffer.position()+" limit:"+buffer.limit()+" capcity:"+buffer.capacity());

        buffer.put((byte)1);
        System.out.println("position:"+buffer.position()+" limit:"+buffer.limit()+" capcity:"+buffer.capacity());

        buffer.flip();
        System.out.println("position:"+buffer.position()+" limit:"+buffer.limit()+" capcity:"+buffer.capacity());

        System.out.println( buffer.get());;

        System.out.println("position:"+buffer.position()+" limit:"+buffer.limit()+" capcity:"+buffer.capacity());

        System.out.println("尝试读取超过写入的界限 会报错的");
        System.out.println( buffer.get());;
        System.out.println("position:"+buffer.position()+" limit:"+buffer.limit()+" capcity:"+buffer.capacity());
        System.out.println("尝试读取越界");
        System.out.println( buffer.get());;
        System.out.println("position:"+buffer.position()+" limit:"+buffer.limit()+" capcity:"+buffer.capacity());
    }
}
