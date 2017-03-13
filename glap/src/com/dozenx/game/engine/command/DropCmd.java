package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.item.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class DropCmd extends BaseGameCmd{
    private int userId;

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

    private int itemId;
    private CmdType cmdType = CmdType.ATTACK;


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

               .array();

    }
    public void parse(byte[] bytes){
        int num;
        ItemType itemType ;
        int itemId;
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();
        itemId=byteBufferWrap.getInt();

    }
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
