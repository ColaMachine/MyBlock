package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

import javax.vecmath.Point4i;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/2/7.
 */
public class ChunkssCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.CHUNKSS;
    public int chunkX;
    public int chunkZ;
    public List<Integer> list =new ArrayList<>();
    public ChunkssCmd(byte[] bytes){
        parse(bytes);
    }
    public ChunkssCmd(List<Integer> list){
        this.list=list;


    }

    //equip 4 |userId|part 2|item |itemId|
    public byte[] toBytes(){

        ByteBufferWrap wrap =   ByteUtil.createBuffer()
            .put(cmdType.getType());

        wrap.put(chunkX).put(chunkZ);

        wrap.put(list.size());
        for(int i=0;i<list.size();i++){
            Integer value = list.get(i);

            wrap.put(value);
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



        for(int i=0;i<length;i++){
             list.add(byteBufferWrap.getInt());
        }



    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
