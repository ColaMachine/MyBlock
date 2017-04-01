package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.protobuf.ChunksProtobuf;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/2/7.
 */
public class ChunkResponseCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.CHUNK;

    Chunk chunk;
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
        wrap.put(chunk.getChunkWorldPosX()).put(chunk.getChunkWorldPosY()).put(chunk.getChunkWorldPosZ());
        for(int i=0;i< chunk.getChunkSizeX();i++){
            for(int j=0;i< chunk.getChunkSizeY();j++){
                for(int k=0;i< chunk.getChunkSizeZ();k++){
                    wrap.put((byte)chunk.getBlock(i,j,k).getId());
                }
            }
        }


            return wrap.array();


    }
    public int x;
    public int y;
    public int z;
    public byte[] data =null;
    public void parse(byte[] bytes){

        ByteBufferWrap  byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.x = byteBufferWrap.getInt();
        this.y=byteBufferWrap.getInt();
        this.z = byteBufferWrap.getInt();
        data=  byteBufferWrap.array(16);


    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
