package com.dozenx.game.engine.command;

import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/2/7.
 */
public class BagCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.BAG;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<ItemServerBean> getItemBeanList() {
        return itemBeanList;
    }

    public void setItemBeanList(List<ItemServerBean> itemBeanList) {
        this.itemBeanList = itemBeanList;
    }

    private int userId;
    private List<ItemServerBean> itemBeanList =new ArrayList<ItemServerBean>();


    public BagCmd(byte[] bytes){
        parse(bytes);
    }
    public BagCmd(int userId ,  List<ItemServerBean>  itemBeanList){
        if(itemBeanList!=null)
            this.itemBeanList =itemBeanList;
        this.userId =userId;
    }

    //equip 4 |userId|part 2|item |itemId|
    public byte[] toBytes(){

        ByteBufferWrap wrap =   ByteUtil.createBuffer()
            .put(cmdType.getType())
                 .put( userId) .put( itemBeanList.size());;

        for(int i=0;i<itemBeanList.size();i++){
            ItemServerBean itemBean  =  itemBeanList .get(i);
            wrap.put(itemBean.getId()).put(itemBean.getNum()).put(itemBean.getItemType()).put(itemBean.getPosition());
        }
            return wrap.array();


    }
    public void parse(byte[] bytes){
        ByteBufferWrap  byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId =  byteBufferWrap.getInt();
        int size =  byteBufferWrap.getInt();
        this.itemBeanList =new ArrayList<>();
        for(int i=0;i<size;i++){
            ItemServerBean itemBean =new ItemServerBean();
            itemBean.setId(byteBufferWrap.getInt());
            itemBean.setNum(byteBufferWrap.getInt());
            itemBean.setItemType(byteBufferWrap.getInt());
            itemBean.setPosition(byteBufferWrap.getInt());
            itemBeanList.add(itemBean);
        }


         // byte[] bytes = ByteUtil.getBytes(byteArray,1,1);

    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
