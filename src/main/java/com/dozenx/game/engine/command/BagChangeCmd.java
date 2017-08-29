package com.dozenx.game.engine.command;

import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class BagChangeCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.BAGCHANGE;

    private int userId;
    private int toId;
    private ItemServerBean itemBean ;
    private int fromPosition;
    private int destPosition;

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    private int boxId;

    public BagChangeCmd(byte[] bytes){
        parse(bytes);
    }
    public BagChangeCmd(int userId , ItemServerBean itemServerBean,int fromPosition,int destPosition){
            this.itemBean =itemServerBean;
        this.userId =userId;
        this.destPosition =destPosition;
        this.fromPosition =fromPosition;

    }
    public BagChangeCmd(int userId , ItemServerBean itemServerBean,int fromPosition,int destPosition,int toId){
        this.itemBean =itemServerBean;
        this.userId =userId;
        this.destPosition =destPosition;
        this.fromPosition =fromPosition;
        this.toId = toId;

    }

    //equip 4 |userId|part 2|item |itemId|
    public byte[] toBytes(){

        ByteBufferWrap wrap =   ByteUtil.createBuffer()
            .put(cmdType.getType())
                .put( userId);



            wrap.put(itemBean.getId())
                    .put(itemBean.getNum())
                    .put(itemBean.getItemType())
                    .put(itemBean.getPosition())
                    .put(destPosition)
                    .put(fromPosition)
                    .put(toId).put(boxId);

            return wrap.array();


    }
    public void parse(byte[] bytes){
        ByteBufferWrap  byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId =  byteBufferWrap.getInt();



             itemBean =new ItemServerBean();
            itemBean.setId(byteBufferWrap.getInt());
            itemBean.setNum(byteBufferWrap.getInt());
            itemBean.setItemType(byteBufferWrap.getInt());
            itemBean.setPosition(byteBufferWrap.getInt());

        setDestPosition(byteBufferWrap.getInt());

        setFromPosition(byteBufferWrap.getInt());
        this.toId= byteBufferWrap.getInt();
        this.boxId = byteBufferWrap.getInt();
         // byte[] bytes = ByteUtil.getBytes(byteArray,1,1);

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

    public ItemServerBean getItemBean() {
        return itemBean;
    }

    public void setItemBean(ItemServerBean itemBean) {
        this.itemBean = itemBean;
    }

    public int getFromPosition() {
        return fromPosition;
    }

    public void setFromPosition(int fromPosition) {
        this.fromPosition = fromPosition;
    }

    public int getDestPosition() {
        return destPosition;
    }

    public void setDestPosition(int destPosition) {
        this.destPosition = destPosition;
    }
}
