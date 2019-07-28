package com.dozenx.util;

/**
 * Created by dozen.zhang on 2016/10/10.
 */

import com.dozenx.game.opengl.util.OpenglUtils;
import core.log.LogUtil;
import org.lwjgl.opengl.GL15;

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
    public int size=120*10;
    public FloatBufferWrap(){
        this.buffer= ByteBuffer.allocateDirect(size) .order(ByteOrder.nativeOrder()).asFloatBuffer();;
        buffers.add(buffer);
    }

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

    public FloatBufferWrap put(float[] ary){
        for(int i=0;i<ary.length;i++){
            this.put(ary[i]);
        }
        return  this;
    }
    public FloatBufferWrap put(float val){
       /* if(buffer.position()==buffer.limit()){
            buffer.limit(buffer.capacity());
        }*/
        if(buffer.position()==buffer.capacity()){

            listPosition++;
            if(listPosition>10000){
                LogUtil.err("may explot memory");
            }
            if(buffers.size()<listPosition+1){

                buffer = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder()).asFloatBuffer();
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
        OpenglUtils.checkGLError();
        GL15.glBufferData(type1, position*4, type2);//put data
        int offset = 0;
        OpenglUtils.checkGLError();
        listLimit = listPosition;
        for(int i=0;i<=listLimit;i++){
            int points = buffers.get(i).position();
            if(points==0){
                continue;
            }
            buffers.get(i).flip();
            GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, offset*4,buffers.get(i));
            OpenglUtils.checkGLError();
            offset+= points;
        }
        this.rewind();
        OpenglUtils.checkGLError();


    }
    public void rewind(){
        if(this.listPosition>10000){
            LogUtil.err(this.listPosition+"");
        }
        this.limit = position;
        this.position =0;
        this.listLimit = listPosition;
        this.listPosition = 0;
        for(int i=0;i<=listLimit;i++){
            FloatBuffer buffer = buffers.get(i);
            buffer.rewind();
            buffer.clear();
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
