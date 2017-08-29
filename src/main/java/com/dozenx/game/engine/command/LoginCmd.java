package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class LoginCmd extends   BaseGameCmd{
    private String userName;

    private int threadId;
    private String pwd;
    final CmdType cmdType = CmdType.LOGIN;

    public LoginCmd(byte[] bytes){

        parse(bytes);
    }
    public LoginCmd(String userName, String pwd,int threadId){
        this.userName = userName;
        this.pwd =pwd;
        this.threadId =threadId;

    }

    //|length|threadId|userName|lengthPwd|
    public byte[] toBytes(){

        return ByteUtil.createBuffer().put(cmdType.getType())

                .put(this.threadId)
                .putLenStr(this.userName)
                .putLenStr(this.pwd).array();
       // ByteUtil.createBuffer().put(userNameLength);

    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public static void main(String args[]){
        LoginCmd cmd =new LoginCmd("nihao","imma",0);
        System.out.println(cmd.toBytes().length);

        cmd = new LoginCmd(cmd.toBytes());

        System.out.println(cmd.userName);
        System.out.println(cmd.pwd);
    }
    public void parse(byte[] bytes){

        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.threadId = byteBufferWrap.getInt();
        this.userName= byteBufferWrap.getLenStr();
        this.pwd = byteBufferWrap.getLenStr();



    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
