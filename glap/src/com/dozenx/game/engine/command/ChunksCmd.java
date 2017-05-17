package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import javax.vecmath.Point4f;
import javax.vecmath.Point4i;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by luying on 17/2/7.
 */
public class ChunksCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.CHUNKS;
    public int chunkX;
    public int chunkZ;
    public List<Point4i> list =new ArrayList<>();
    public ChunksCmd(byte[] bytes){
        parse(bytes);
    }
    public ChunksCmd(List<Point4i> list){
        this.list=list;


    }

    //equip 4 |userId|part 2|item |itemId|
    public byte[] toBytes(){

        ByteBufferWrap wrap =   ByteUtil.createBuffer()
            .put(cmdType.getType());

        wrap.put(chunkX).put(chunkZ);

        wrap.put(list.size()*4);
        for(int i=0;i<list.size();i++){
            Point4i value = list.get(i);
            int unionValue = ByteUtil.unionBinary4_4_8_16(value.x,value.y,value.z,value.w);
            wrap.put(unionValue);
        }



        return wrap.array();


    }
    public int x;
    public int y;
    public int z;
    public Integer[] data =null;
    public int length;

    public void parse(byte[] bytes){

        ByteBufferWrap  byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.x = byteBufferWrap.getInt();
        //this.y=byteBufferWrap.getInt();
        this.z = byteBufferWrap.getInt();
        this.length = byteBufferWrap.getInt();
        //data=  byteBufferWrap.array(16);
       // short val ;

        int[] tt;
        data = new Integer[length];
        for(int i=0;i<length;i++){
            data[i]=byteBufferWrap.getInt();
           // tt= ByteUtil.getValueSplit8Slot(data[i]);
            if(ByteUtil.get24_16Value(data[i])>16){
                LogUtil.println("hello");
            }
           Point4i vector = new Point4i(ByteUtil.get32_28Value(data[i]),ByteUtil.get24_16Value(data[i]),ByteUtil.get28_24Value(data[i]),ByteUtil.get16_0Value(data[i]));
            list.add(vector);
        }



    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
