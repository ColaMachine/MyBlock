package com.dozenx.game.engine.ui.inventory.BoxService;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.BoxBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.BoxItemsReqCmd;
import com.dozenx.game.engine.command.BoxOpenCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.client.Client;

import java.util.List;

/**
 * Created by dozen.zhang on 2017/7/14.
 */
public class BoxClientService {

    public List<ItemServerBean> openAndGetItemBeanList(BaseBlock boxBlock1,int chunkX,int chunkZ,int cx,int cy,int cz){

        //========创建 create bloxOpenCmd ======
//        int chunkX =boxBlock.chunk.chunkPos.x;
//        int chunkZ=boxBlock.chunk.chunkPos.z;
        BoxOpenCmd cmd = new BoxOpenCmd(chunkX, chunkZ,cx,cy,cz,1);


       // int newId=  (boxBlock.open<<12| boxBlock.dir<<8 )| boxBlock.id;


        ResultCmd result =  CoreRegistry.get(Client.class).syncSend(cmd);
        BoxItemsReqCmd boxCmd = new BoxItemsReqCmd(result.getMsg());
        return boxCmd.getItemBeanList();//并且拿到数据

    }

   /* public void putIn(BoxBlock boxBlock, int slotIndex, ItemBean itemBean){
        BoxCmd boxCmd =new BoxCmd();
        boxCmd.setDestPosition(slotIndex);
        ItemServerBean itemServerBean = new ItemServerBean();
        itemServerBean.setId(itemBean.getId());
        itemServerBean.setNum(itemBean.getNum());
        itemServerBean.setItemType(itemBean.getItemDefinition().getItemType().ordinal());
        itemServerBean.setPosition(slotIndex);
        boxCmd.setItemBean(itemServerBean);

        CoreRegistry.get(Client.class).send(boxCmd);
    }*/
}
