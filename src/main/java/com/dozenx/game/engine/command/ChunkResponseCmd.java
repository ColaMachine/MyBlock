package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;

import java.util.List;
import java.util.Map;

import com.dozenx.game.engine.edit.view.ColorGroup;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

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
       
        
        for (Map.Entry<Integer, IBlock > entry : chunk.getBlockMap().entrySet()) { 
            BaseBlock block = (BaseBlock)entry.getValue();
            if(block.dir>0){
                Integer index = entry.getKey();
                int y = index /16/16;
                int yu = index %( 16 *16);
                int z = yu /16;
                int x = yu % 16;
                wrap.put(x);
                wrap.put(y);
                wrap.put(z);
                wrap.put(block.dir);
            }
        
            
          
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
      //  int[] tt;
        data = new Integer[length];
        for(int i=0;i<length;i++){
            data[i]=byteBufferWrap.getInt();
           // tt= ByteUtil.getValueSplit8Slot(data[i]);
            if(ByteUtil.get24_16Value(data[i])>16){
               // LogUtil.println("hello");
            }
            chunk.setBlock(ByteUtil.get32_28Value(data[i]),ByteUtil.get24_16Value(data[i]),ByteUtil.get28_24Value(data[i]),ByteUtil.get16_0Value(data[i]));
        }
        
        int value;
        while(byteBufferWrap.buffer.position()<=byteBufferWrap.buffer.limit()-4){
            int x = byteBufferWrap.getInt();
            int y = byteBufferWrap.getInt();
            int z = byteBufferWrap .getInt();
            int dir =byteBufferWrap.getInt();
            chunk.setBlockStatus(x, y, z, dir);
           
        }



    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
