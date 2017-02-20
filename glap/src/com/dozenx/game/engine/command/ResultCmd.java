package com.dozenx.game.engine.command;

import com.dozenx.util.ByteUtil;

import java.nio.ByteBuffer;

/**
 * Created by luying on 17/2/7.
 */
public class ResultCmd extends   BaseGameCmd{
    byte result ;
    String msg ;
    public ResultCmd(byte[] bytes){

        parse(bytes);
    }
    public ResultCmd(int val,String msg){

       this.result=(byte)val;
        this.msg = msg;
    }


    //|result|length|msg|
    public byte[] toBytes(){
        ByteUtil.createBuffer().put(result).putLenStr(msg);
        return new byte[]{result};

       // ByteUtil.createBuffer().put(userNameLength);

    }


    public void parse(byte[] bytes){
       this.result = bytes[0];

    }


}
