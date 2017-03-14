package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class PickCmd extends BaseGameCmd{
    private int userId;


    private int itemId;



    private CmdType cmdType = CmdType.DROP;


    public PickCmd(byte[] bytes){
        parse(bytes);
    }
    public PickCmd(int userId, int itemId){
        this.userId = userId;
        this.itemId =itemId;

    }
    @Override
    public byte[] toBytes(){
        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)
                .put(itemId)

               .array();

    }
    public void parse(byte[] bytes){


        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();
        itemId=byteBufferWrap.getInt();





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

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

}
