package com.dozenx.game.engine.command;

import com.alibaba.fastjson.JSON;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

import java.nio.ByteBuffer;

/**
 * Created by luying on 17/2/7.
 */
public class ResultCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.RESULT;
    byte result ;

    public byte getResult() {
        return result;
    }

    public void setResult(byte result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getThreadId() {

        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    String msg ;
    int threadId;
    public ResultCmd(byte[] bytes){

        parse(bytes);
    }
    public ResultCmd(int val,String msg,int threadId){

       this.result=(byte)val;
        this.msg = msg;
        this.threadId =threadId;
    }

    public static void main(String[] args){
        ResultCmd cmd = new ResultCmd(0,"hello",123);
        ResultCmd cmd2 = new ResultCmd(cmd.toBytes());
    }


    //|result|length|msg|threadId|
    public byte[] toBytes(){
        LogUtil.println(JSON.toJSONString(this));
        return ByteUtil.createBuffer().put(cmdType.getType()).put(result).putLenStr(msg).put(threadId).array();
    }


    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.result= byteBufferWrap.get();
        this.msg = byteBufferWrap.getLenStr();
        this.threadId=byteBufferWrap.getInt();
        LogUtil.println(JSON.toJSONString(this));
    }
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
