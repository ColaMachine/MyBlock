package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.protobuf.ChunksProtobuf;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraDenseArray16Bit;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/2/7.
 */
public class ChunkResponseCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.CHUNKRESPONSE;

    public Chunk chunk;
    public ChunkResponseCmd(byte[] bytes){
        parse(bytes);
    }
    public ChunkResponseCmd(Chunk chunk){
        this.chunk =chunk;


    }

    //equip 4 |userId|part 2|item |itemId|
    public byte[] toBytes(){

        ByteBufferWrap wrap =   ByteUtil.createBuffer()
            .put(cmdType.getType());
        Integer[] data = chunk.zip();
        wrap.put(chunk.getPos().x).put(chunk.getPos().y).put(chunk.getPos().z);
        /*for(int i=0;i< chunk.getChunkSizeX();i++){
            for(int j=0;i< chunk.getChunkSizeY();j++){
                for(int k=0;i< chunk.getChunkSizeZ();k++){
                    Block block  = chunk.getBlock(i,j,k);
                    if(block!=null){
                        wrap.put((byte)chunk.getBlock(i,j,k).getId());
                    }else{
                        wrap.put(0);
                    }

                }
            }
        }*/
        wrap.put(data.length);
        for(int i=0;i<data.length;i++){
            /*if(ByteUtil.get24_16Value(data[i])>16){
                LogUtil.println("hello");
            }*/
            wrap.put(data[i]);
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
        this.y=byteBufferWrap.getInt();
        this.z = byteBufferWrap.getInt();
        this.length = byteBufferWrap.getInt();
        //data=  byteBufferWrap.array(16);
       // short val ;
         chunk =new ChunkImpl(x,y,z);
        int[] tt;
        data = new Integer[length];
        for(int i=0;i<length;i++){
            data[i]=byteBufferWrap.getInt();
           // tt= ByteUtil.getValueSplit8Slot(data[i]);
            if(ByteUtil.get24_16Value(data[i])>16){
                LogUtil.println("hello");
            }
            chunk.setBlock(ByteUtil.get32_28Value(data[i]),ByteUtil.get24_16Value(data[i]),ByteUtil.get28_24Value(data[i]),ByteUtil.get16_0Value(data[i]));
        }



    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
