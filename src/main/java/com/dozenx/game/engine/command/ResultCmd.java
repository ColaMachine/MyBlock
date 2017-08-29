package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

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

    public byte[] getMsg() {
        return msg;
    }



    public int getThreadId() {

        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }
    GameCmd[] cmds;
    byte[] msg ;
    int threadId;
    public ResultCmd(byte[] bytes){

        parse(bytes);
    }
  /*  public ResultCmd(int val,GameCmd ... bytes){

        this.result=(byte)val;
        this.cmds = cmds
        this.msg = bytes;
        this.threadId =threadId;
    }*/
    public ResultCmd(int val,byte[] bytes,int threadId){

       this.result=(byte)val;
        this.msg = bytes;
        this.threadId =threadId;
    }
    public ResultCmd(int val,String msg ,int threadId){

        this.result=(byte)val;
        this.msg = new MsgCmd(msg).toBytes();
        this.threadId =threadId;
    }


    public static void main(String[] args){
        //ResultCmd cmd = new ResultCmd(0,"hello",123);
        //ResultCmd cmd2 = new ResultCmd(cmd.toBytes());
    }


    //|result|length|msg|threadId|
    public byte[] toBytes(){
       // LogUtil.println(JSON.toJSONString(this));
        if(msg == null){
            LogUtil.err(" null str");
        }
        return ByteUtil.createBuffer().put(cmdType.getType()).put(result).put(msg.length).put(msg)/*putLenStr(msg)*/.put(threadId).array();
    }


    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.result= byteBufferWrap.get();
        this.msg = byteBufferWrap.getLenByteAry();
        this.threadId=byteBufferWrap.getInt();
       //   LogUtil.println(JSON.toJSONString(this));
    }
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
