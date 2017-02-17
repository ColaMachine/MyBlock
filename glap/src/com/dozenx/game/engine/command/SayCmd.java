package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.util.ByteUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by luying on 17/2/7.
 */
public class SayCmd implements  GameCmd{
    private boolean deleted;
    int userId;
    String userName;

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

    String msg;
    private CmdType cmdType = CmdType.SAY;
    private EquipPartType part;
    // userId | length| username:msg|
    public SayCmd(byte[] bytes){
        parse(bytes);
    }
    public SayCmd(int userId,String userName,String msg){
        this.userId =userId;
        this.userName=userName;
        this.msg = msg;
       // this.part =pos;
    }


    public byte[] toBytes(){
        try {
            byte[] msgByteAry = msg.getBytes("utf-8");

            int length =msgByteAry.length;
            return ByteUtil.getBytes(ByteUtil.getBytes((byte)cmdType.ordinal()),ByteUtil.getBytes(length), msgByteAry);
             //msg.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void parse(byte[] bytes){
        this.userId = (int)bytes[0];
        int length =ByteUtil.getInt( ByteUtil.getBytes(bytes,1,4));
        this.msg= new String(ByteUtil.getBytes(bytes,5,length));//最后一个应该是结束符号
       // this.item = TextureManager.getItemDefinition( ItemType.values()[ bytes[1]]);
        //bytes = ByteUtil.getBytes(byteArray,8,4);
      //  this.item = TextureManager.getItemDefinition( ItemType.values()[ ByteUtil.getInt(bytes)]);
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

    public void setVal(int val){
        this.val=val;
    }
}
