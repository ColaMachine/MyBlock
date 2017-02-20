package com.dozenx.game.engine.command;

import com.dozenx.util.ByteUtil;

import java.nio.ByteBuffer;

/**
 * Created by luying on 17/2/7.
 */
public class LoginCmd extends   BaseGameCmd{
    private String userName;

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

    private String pwd;
    final CmdType cmdType = CmdType.POS;

    public LoginCmd(byte[] bytes){

        parse(bytes);
    }
    public LoginCmd(String userName, String pwd){
        this.userName = userName;
        this.pwd =pwd;

    }

    //|length|userName|lengthPwd|
    public byte[] toBytes(){

        byte[] userAry = userName.getBytes();
        byte[] pwdAry = pwd.getBytes();
        int userLen = userAry.length;
        int pwdLen = pwdAry.length;

       // byte[] bytes = new byte[userNameLength+pwdLenght+1+1+1];
        ByteBuffer buffer=  ByteBuffer.allocate(userLen+pwdLen+4+4+2+1);
        buffer.put(cmdType.getType());
        buffer.putInt(userLen);
        buffer.put(userAry);
        buffer.putInt(pwdLen);
        buffer.put(pwdAry);
        buffer.putChar('\0');
        return buffer.array();
       // ByteUtil.createBuffer().put(userNameLength);

    }

    public static void main(String args[]){
        LoginCmd cmd =new LoginCmd("nihao","imma");
        System.out.println(cmd.toBytes().length);

        cmd = new LoginCmd(cmd.toBytes());
        System.out.println(cmd.userName);
        System.out.println(cmd.pwd);
    }
    public void parse(byte[] bytes){
        int userLen= ByteUtil.getInt(ByteUtil.slice(bytes,1,4));
         userName = ByteUtil.getString(ByteUtil.slice(bytes,5,userLen));

        int pwdLen= ByteUtil.getInt(ByteUtil.slice(bytes,5+userLen,4));
         pwd = ByteUtil.getString(ByteUtil.slice(bytes,5+userLen+4,pwdLen));


    }


}
