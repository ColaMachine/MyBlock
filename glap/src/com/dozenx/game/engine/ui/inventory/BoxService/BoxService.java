package com.dozenx.game.engine.ui.inventory.BoxService;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.BoxBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.BoxOpenCmd;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.network.client.Client;

/**
 * Created by dozen.zhang on 2017/7/14.
 */
public class BoxService {

    public void openAndGetItemBeanList(BoxBlock boxBlock){


        int chunkX =boxBlock.chunk.chunkPos.x;
        int chunkZ=boxBlock.chunk.chunkPos.z;
        ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
        cmd.cx = boxBlock.getX();
        cmd.cz = boxBlock.getZ();
        cmd.cy = boxBlock.getY();
        cmd.type = 1;
        boxBlock.open =1;


        int newId=  (boxBlock.open<<12| boxBlock.dir<<8 )| boxBlock.id;
        cmd.blockType= newId;

       ResultCmd result =  CoreRegistry.get(Client.class).syncSend(cmd);
        BoxOpenCmd boxCmd = new BoxOpenCmd(result.getMsg());
        boxCmd.getItemBean();//并且拿到数据

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
