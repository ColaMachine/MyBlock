package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.math.Vector3i;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class ChunkRequestCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.CHUNKREQUEST;

    public int x;
    public int z;

    public int cx;
    public int cy;
    public int cz;
    public int type;//0 all  1add 2delete
    public int blockType;

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
                 .put( x) .put( z) .put( cx) .put( cy) .put( cz) .put( type) .put( blockType);


        return wrap.array();


    }
    public void parse(byte[] bytes){
        ByteBufferWrap  byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.x=byteBufferWrap.getInt();
        this.z=byteBufferWrap.getInt();

        this.cx=byteBufferWrap.getInt();
        this.cy=byteBufferWrap.getInt();
        this.cz=byteBufferWrap.getInt();
        this.type=byteBufferWrap.getInt();
        this.blockType=byteBufferWrap.getInt();

    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
