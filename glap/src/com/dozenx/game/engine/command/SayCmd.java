package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by luying on 17/2/7.
 */
public class SayCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.SAY;
    int userId;
    String userName;
    String msg;


    // userId | length| username:msg|
    public SayCmd(byte[] bytes){

        parse(bytes);
    }
    public SayCmd(int userId,String userName,String msg){
        this.userId =userId;
        this.userName=userName;
        this.msg = msg;

    }


    public byte[] toBytes(){

        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)
                .putLenStr(this.userName)
                .putLenStr(this.getMsg()).array();

    }
    public void parse(byte[] bytes){

        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();
        this.userName = byteBufferWrap.getLenStr();
        this.msg = byteBufferWrap.getLenStr();

    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
