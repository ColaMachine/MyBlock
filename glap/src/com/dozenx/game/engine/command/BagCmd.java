package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.manager.TextureManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.bean.PlayerStatus;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/2/7.
 */
public class BagCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.BAG;
    private int userId;
    private List<ItemServerBean> itemBeanList =new ArrayList<ItemServerBean>();


    public BagCmd(byte[] bytes){
        parse(bytes);
    }
    public BagCmd(int userId ,  List<ItemServerBean>  itemBeanList){
        this.userId =userId;
        this.itemBeanList =itemBeanList;


    }

    //equip 4 |userId|part 2|item |itemId|
    public byte[] toBytes(){

        ByteBufferWrap wrap =   ByteUtil.createBuffer()
            .put(CmdType.PLAYERSTATUS.getType())
                .put( userId) .put( itemBeanList.size());;

        for(int i=0;i<itemBeanList.size();i++){
            ItemServerBean itemBean  =  itemBeanList .get(i);
            wrap.put(itemBean.getId()).put(itemBean.getNum()).put(itemBean.getItemType()).put(itemBean.getPosition());
        }
            return wrap.array();


    }
    public void parse(byte[] bytes){
        ByteBufferWrap  byteBufferWrap = ByteUtil.createBuffer(bytes);

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
