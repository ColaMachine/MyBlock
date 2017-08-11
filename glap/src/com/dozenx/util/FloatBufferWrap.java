package com.dozenx.util;

/**
 * Created by dozen.zhang on 2016/10/10.
 */

import cola.machine.game.myblocks.engine.Constants;
import core.log.LogUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class FloatBufferWrap
{
    public int listLimit =0;
    public int listPosition;
    public int limit =0;
    public int position;
    private FloatBuffer buffer;

    public  List<FloatBuffer> buffers= new ArrayList<FloatBuffer>();
    public FloatBufferWrap(){
        this.buffer= ByteBuffer.allocateDirect(1024*120) .order(ByteOrder.nativeOrder()).asFloatBuffer();;
        buffers.add(buffer);
    }
    public int size;
    public FloatBufferWrap(int size){
        this.size=size;
        this.buffer= ByteBuffer.allocateDirect(size) .order(ByteOrder.nativeOrder()).asFloatBuffer();;
        // this.buffer.flip();

    }
    public FloatBufferWrap(float[] ary){
        this.buffer= FloatBuffer.wrap(ary);
        buffers.add(buffer);
       // this.buffer.flip();

    }


    public Float getFloat(){
        return buffer.get();
    }
    public FloatBufferWrap put(float val){
        if(buffer.position()==buffer.limit()){
            buffer.limit(buffer.capacity());
        }
        if(buffer.position()==buffer.capacity()){

            listPosition++;
            if(buffers.size()<listPosition+1){
                buffer = ByteBuffer.allocateDirect(1024*120).order(ByteOrder.nativeOrder()).asFloatBuffer();
                buffers.add(buffer);
            }else{
                buffer = buffers.get(listPosition);
                buffer.rewind();
            }


        }
       position++;
try {
    buffer.put(val);
}catch (Exception e){
    e.printStackTrace();
}
        return this;
    }

    public void glBufferData(int type1,int type2){
       // for(int i=0;i<=listLimit;i++){
            GL15.glBufferData(type1, buffers.get(0), type2);//put data
        //}

    }
    public void rewind(){
        this.limit = position;
        this.position =0;
        this.listLimit = listPosition;
        this.listPosition = 0;
        for(int i=0;i<=listLimit;i++){
            FloatBuffer buffer = buffers.get(i);
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
            FloatBuffer buffer = buffers.get(i);
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
            FloatBuffer buffer = buffers.get(i);
            buffer.clear();
        }
        buffer = buffers.get(0);
    }


    public int position(){
        return position;
    }


}
