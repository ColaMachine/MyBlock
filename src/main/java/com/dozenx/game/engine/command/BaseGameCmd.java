package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import com.sun.deploy.util.BufferUtil;

/**
 * Created by luying on 17/2/7.
 */
public abstract class BaseGameCmd  implements  GameCmd{
    public ByteBufferWrap  getBuffer(){
        return ByteUtil.createBuffer().put(this.getCmdType().getType());
    }

    private boolean deleted;

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void delete() {
        this.deleted=true;
    }



    public int val;


    @Override
    public int val(){
        return val;

    }
    public BaseGameCmd(){

    }
    public void setVal(int val){
        this.val=val;
    }

    /*public String toString(){
        return this.getCmdType()+"";.
    }*/
}
