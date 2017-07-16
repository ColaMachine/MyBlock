package com.dozenx.game.engine.command;

import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

import javax.vecmath.Point4i;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/2/7.
 */
public class BoxOpenCmd extends   BaseGameCmd{

    final CmdType cmdType = CmdType.BOXOPEN;

    private int userId;
    private int chunkX;
    private  int chunkZ;
    private int x;
    private int y;
    private int z;
    private int open;//0 关闭 1 打开


    public BoxOpenCmd(byte[] bytes){
        parse(bytes);
    }
    public BoxOpenCmd(int chunkX,int chunkZ,int x,int y,int z,int open){
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.x=x;
        this.y=y;
        this.z=z;
        this.open=open;

    }


    //equip 4 |userId|part 2|item |itemId|
    public byte[] toBytes(){

        ByteBufferWrap wrap =   ByteUtil.createBuffer()
                .put(cmdType.getType())
                .put( userId);



        wrap.put(chunkX)
                .put(chunkZ)
                .put(x)
                .put(y)
                .put(z).put(open);



        return wrap.array();


    }


    public void parse(byte[] bytes){
        ByteBufferWrap  byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId =  byteBufferWrap.getInt();
        this.chunkX =byteBufferWrap.getInt();

        this.chunkZ = byteBufferWrap.getInt();
        this.x = byteBufferWrap.getInt();
        this.y=byteBufferWrap.getInt();
        this.z=byteBufferWrap.getInt();
        this.open =byteBufferWrap.getInt();


    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getChunkX() {
        return chunkX;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public void setChunkZ(int chunkZ) {
        this.chunkZ = chunkZ;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }
}
