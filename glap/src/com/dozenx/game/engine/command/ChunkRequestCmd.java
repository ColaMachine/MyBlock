package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.math.Vector3i;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/2/7.
 */
public class ChunkRequestCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.CHUNK;

    private int x;
    private int z;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public ChunkRequestCmd(byte[] bytes){
        parse(bytes);
    }
    public ChunkRequestCmd(Vector3i pos){

        this.x =pos.x;
        this.z = pos.z;
    }

    //equip 4 |userId|part 2|item |itemId|
    public byte[] toBytes(){

        ByteBufferWrap wrap =   ByteUtil.createBuffer()
            .put(cmdType.getType())
                 .put( x) .put( z);


        return wrap.array();


    }
    public void parse(byte[] bytes){
        ByteBufferWrap  byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.x=byteBufferWrap.getInt();
        this.z=byteBufferWrap.getInt();


    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
