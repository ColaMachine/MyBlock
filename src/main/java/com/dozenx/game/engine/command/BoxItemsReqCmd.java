package com.dozenx.game.engine.command;

import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/2/7.
 */
public class BoxItemsReqCmd extends   BaseGameCmd{

    final CmdType cmdType = CmdType.BOXITEM;


    private List<ItemServerBean> itemBeanList =null;

    public BoxItemsReqCmd(byte[] bytes){
        parse(bytes);
    }
    public BoxItemsReqCmd(List<ItemServerBean> itemBeanList){
        this.itemBeanList =itemBeanList;

    }


    //equip 4 |userId|part 2|item |itemId|
    public byte[] toBytes(){

        ByteBufferWrap wrap =   ByteUtil.createBuffer()
                .put(cmdType.getType())
                .put( itemBeanList.size());




        for(int i=0;i<itemBeanList.size();i++){
            ItemServerBean itemBean  =  itemBeanList .get(i);
            wrap.put(itemBean.getId()).put(itemBean.getNum()).put(itemBean.getItemType()).put(itemBean.getPosition());
        }

        return wrap.array();


    }


    public void parse(byte[] bytes){
        ByteBufferWrap  byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();

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


    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

    public List<ItemServerBean> getItemBeanList() {
        return itemBeanList;
    }

    public void setItemBeanList(List<ItemServerBean> itemBeanList) {
        this.itemBeanList = itemBeanList;
    }
}
