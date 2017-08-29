package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class DropCmd extends BaseGameCmd{
    private int userId;


    private int itemId;

    private Integer  itemType;
    private float x;
    private float y;
    private float z;
    private int num;

    private CmdType cmdType = CmdType.DROP;


    public DropCmd(byte[] bytes){
        parse(bytes);
    }
    public DropCmd(int userId, int itemId){
        this.userId = userId;
        this.itemId =itemId;

    }
    @Override
    public byte[] toBytes(){
        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)
                .put(itemId)
                .put(itemType==null?0:itemType)
                .put(x).put(y).put(z).put(num)
               .array();

    }
    public void parse(byte[] bytes){


        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();
        itemId=byteBufferWrap.getInt();
        int itemTypeIndex= byteBufferWrap.getInt();

        this.itemType =itemTypeIndex;
        this.x=byteBufferWrap.getFloat();
        this.y=byteBufferWrap.getFloat();
        this.z= byteBufferWrap.getFloat();;
       this.num =  byteBufferWrap.getInt();


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

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
