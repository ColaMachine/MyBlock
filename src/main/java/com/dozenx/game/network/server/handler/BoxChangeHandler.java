package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.BoxItemsReqCmd;
import com.dozenx.game.engine.command.BoxOpenCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/2/18.
 */
public class BoxChangeHandler extends GameServerHandler {
  BagService bagService;
    public BoxChangeHandler(ServerContext serverContext){
        super(serverContext);
        bagService = (BagService)serverContext.getService(BagService.class);
    }
    public ResultCmd  handler(GameServerRequest request, GameServerResponse response){
        //===========根据block的位置获取boxid according to block position to get boxId
        BoxOpenCmd cmd = (BoxOpenCmd)request.getCmd();

        int chunkX = cmd.getChunkX();
        int chunkZ= cmd.getChunkZ();
        int x = cmd.getX();
        int y = cmd.getY();
        int z = cmd.getZ();
        int boxId = (chunkX*16 +x)*100000+(chunkZ*16+z)*1000+y;
        //=========根据id获取物品列表 没有就创建  get itemList by boxid============
        ItemServerBean[] itemBeans = bagService.getItemAryUserId(boxId);
        List<ItemServerBean> list =new ArrayList();
       if(itemBeans!= null) {
           for (int i = 0; i < itemBeans.length; i++) {
               if (itemBeans[i] != null) {
                   itemBeans[i].setPosition(35 + i);
                   list.add(itemBeans[i]);

               }
           }
       }else
        //=====if not found create it in serverContext.itemArrayMap

       {
            itemBeans =new ItemServerBean[20];
                    serverContext.userId2ItemArrayMap.put(boxId, itemBeans);
        }

        //=========return the list data;

        return new ResultCmd(0, new BoxItemsReqCmd(list).toBytes(),0);

        //更新其他附近人的此人的装备属性

    }

}
