package com.dozenx.game.network.server.handler;

import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import core.log.LogUtil;

import java.util.List;

/**
 * Created by luying on 17/2/18.
 */
public class BagChangeHandler extends GameServerHandler {
  BagService bagService;
    public BagChangeHandler(ServerContext serverContext){
        super(serverContext);
        bagService = (BagService)serverContext.getService(BagService.class);
    }
    public ResultCmd  handler(GameServerRequest request, GameServerResponse response){

        BagChangeCmd cmd = (BagChangeCmd)request.getCmd();
        int userId = cmd.getUserId();
        int fromPos= cmd.getFromPosition();
        int destPos=cmd.getDestPosition();
      //  List<ItemServerBean>  itemList =serverContext.getItemByUserId(userId);

        ItemServerBean[] itemBeans = bagService.getItemAryUserId(userId);
       // ItemServerBean clientItemBean  = cmd.getItemBean();
        ItemServerBean fromBean  = itemBeans[fromPos];

        //拿到这个人的所有物品信息 和bagController中的逻辑一样



       // ItemBean[] itemBeans = this.getItemBeanList();

        ItemServerBean destBean = itemBeans[destPos];
        if(fromBean==null)
            return new ResultCmd(1, "无效操作",0);;
        if(fromBean.getId()<=0)
            return new ResultCmd(1, "无效操作",0);;
        if(destBean==null && fromBean==null)
            return new ResultCmd(1, "无效操作",0);;
        //ItemBean oldBean = itemBeans[position];
        if(destBean!=null && fromBean!=null &&  destBean.getId()== fromBean.getId())
              return new ResultCmd(1, "无效操作",0);;
       /* if(oldBean!=null){
            itemBeans[itemBean.getPosition()]= oldBean;
            oldBean.setPosition(itemBean.getPosition());
        }
        itemBeans[position]= itemBean;
        itemBean.setPosition(position);*/
        if(destBean==null){//拖过去
            itemBeans[destPos]= fromBean;fromBean.setPosition(destPos);
            itemBeans[fromPos]= null;
        }else
        if(destBean.getItemType()== fromBean .getItemType()){//堆叠
            destBean.setNum(destBean.getNum()+fromBean.getNum());
            itemBeans[fromPos]= null;
        }else{//交换
            itemBeans[fromPos]= destBean;destBean.setPosition(fromPos);
            itemBeans[destPos]= fromBean;fromBean.setPosition(destPos);

        }


        return new ResultCmd(0, "成功",0);

        //更新其他附近人的此人的装备属性

    }

}
