package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.BagChangeCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;

/**
 * Created by luying on 17/2/18.
 */
public class BagChangeHandler extends GameServerHandler {
    BagService bagService;

    public BagChangeHandler(ServerContext serverContext) {
        super(serverContext);
        bagService = (BagService) serverContext.getService(BagService.class);
    }

    public ResultCmd handler(GameServerRequest request, GameServerResponse response) {

        BagChangeCmd cmd = (BagChangeCmd) request.getCmd();
        int userId = cmd.getUserId();
        int fromPos = cmd.getFromPosition();
        int destPos = cmd.getDestPosition();

        int fromIndex = fromPos > 35 ? fromPos - 35 : fromPos;
        int destIndex = destPos > 35 ? destPos - 35 : destPos;
        int boxId = cmd.getBoxId();
        //  List<ItemServerBean>  itemList =serverContext.getItemByUserId(userId);

        ItemServerBean[] itemBeans = bagService.getItemAryUserId(userId);
        ItemServerBean[] boxItemBeans = null;
        if (boxId > 0) {
            boxItemBeans = bagService.getItemAryUserId(boxId);
            if (boxItemBeans == null) {
                boxItemBeans = new ItemServerBean[20];
                serverContext.userId2ItemArrayMap.put(boxId, boxItemBeans);
            }
        }
        // ItemServerBean clientItemBean  = cmd.getItemBean();


        //拿到这个人的所有物品信息 和bagController中的逻辑一样



      /* // ItemBean[] itemBeans = this.getItemBeanList();
        if(boxId==0) {
            ItemServerBean fromBean  = itemBeans[fromPos];
            ItemServerBean destBean = itemBeans[destPos];
            if (fromBean == null)
                return new ResultCmd(1, "无效操作", 0);
            ;
            if (fromBean.getId() <= 0)
                return new ResultCmd(1, "无效操作", 0);
            ;
            if (destBean == null && fromBean == null)
                return new ResultCmd(1, "无效操作", 0);
            ;
            //ItemBean oldBean = itemBeans[position];
            if (destBean != null && fromBean != null && destBean.getId() == fromBean.getId())
                return new ResultCmd(1, "无效操作", 0);
            ;

            if (destBean == null) {//拖过去
                itemBeans[destPos] = fromBean;
                fromBean.setPosition(destPos);
                itemBeans[fromPos] = null;
            } else if (destBean.getItemType() == fromBean.getItemType()) {//堆叠
                destBean.setNum(destBean.getNum() + fromBean.getNum());
                itemBeans[fromPos] = null;
            } else {//交换
                itemBeans[fromPos] = destBean;
                destBean.setPosition(fromPos);
                itemBeans[destPos] = fromBean;
                fromBean.setPosition(destPos);

            }
        }else{*/
        ItemServerBean[] ItemFromBeans = null;
        if (fromPos >= 35) {
            ItemFromBeans = boxItemBeans;

        } else {
            ItemFromBeans = itemBeans;
        }
        ItemServerBean[] ItemDestBeans = null;
        if (destPos >= 35) {
            ItemDestBeans = boxItemBeans;

        } else {
            ItemDestBeans = itemBeans;
        }

        ItemServerBean fromBean = ItemFromBeans[fromIndex];
        ItemServerBean destBean = ItemDestBeans[destIndex];
        if (fromBean == null)
            return new ResultCmd(1, "无效操作", 0);
        ;
        if (fromBean.getId() <= 0)
            return new ResultCmd(1, "无效操作", 0);
        ;
        if (destBean == null && fromBean == null)
            return new ResultCmd(1, "无效操作", 0);
        ;
        //ItemBean oldBean = itemBeans[position];
        if (destBean != null && fromBean != null && destBean.getId() == fromBean.getId())
            return new ResultCmd(1, "无效操作", 0);


        if (destBean == null) {//拖过去
            ItemDestBeans[destIndex] = fromBean;
            fromBean.setPosition(destPos);
            ItemFromBeans[fromIndex] = null;
        } else if (destBean.getItemType() == fromBean.getItemType()) {//堆叠
            destBean.setNum(destBean.getNum() + fromBean.getNum());
            ItemFromBeans[fromIndex] = null;
        } else {//交换
            ItemFromBeans[fromIndex] = destBean;
            destBean.setPosition(fromPos);
            ItemDestBeans[destIndex] = fromBean;
            fromBean.setPosition(destPos);

        }

        // }


        return new ResultCmd(0, "成功", 0);

        //更新其他附近人的此人的装备属性

    }

}
