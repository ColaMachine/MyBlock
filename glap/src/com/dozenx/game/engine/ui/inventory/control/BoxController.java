package com.dozenx.game.engine.ui.inventory.control;

import cola.machine.game.myblocks.bean.BagEntity;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.model.BoxBlock;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.service.BagService;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.command.BagChangeCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.engine.item.ItemUtil;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.engine.ui.inventory.BoxService.BoxService;
import com.dozenx.game.engine.ui.inventory.bean.InventoryBean;
import com.dozenx.game.engine.ui.inventory.view.InventoryPanel;
import com.dozenx.game.engine.ui.inventory.view.PersonPanel;
import com.dozenx.game.engine.ui.toolbar.bean.ToolBarBean;
import com.dozenx.game.engine.ui.toolbar.view.ToolBarView;
import com.dozenx.game.network.client.Client;
import core.log.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * 容器
 * container like box
 * Created by luying on 16/7/3.
 */
public class BoxController {
     /*  private BagEntity bag;*/

    private BoxService boxService =new BoxService();
    Player player;
    public BoxController(Player player){
        this.player =player;
        CoreRegistry.put(BoxController.class,this);
    }

    /**
     * 创建一个箱子
     * @param worldx
     * @param worldy
     * @param worldz
     */
    public void create(int worldx,int worldy,int worldz){

    }

    /**
     * 塞入物体
     * @param worldx
     * @param worldy
     * @param worldz
     * @param slotIndex
     * @param itemBean
     */
    public void putin(int worldx,int worldy,int worldz,int slotIndex,ItemBean itemBean){
        boxService.putIn(currentBox,slotIndex,itemBean);
    }
    private Integer nowBoxX,nowBoxY,nowBoxZ;
    BoxBlock currentBox;
    public void openBox( BoxBlock boxBlock){
        //=========记录当前box==========
        nowBoxX=boxBlock.getX();
        nowBoxY=boxBlock.getY();
        nowBoxZ =boxBlock.getZ();
        currentBox = boxBlock;
        //========修改当前box属性=========
        boxBlock.open=1;
        List<ItemServerBean> list = boxService.openAndGetItemBeanList(boxBlock);
    }
    /**
     * 取出物体
     * @param worldx
     * @param worldy
     * @param worldz
     * @param slotIndex
     * @param itemBean
     */
    public void getout(int worldx,int worldy,int worldz,int slotIndex,ItemBean itemBean){

    }

    /**
     * 扔掉物体
     * @param worldx
     * @param worldy
     * @param worldz
     * @param slotIndex
     * @param itemBean
     */
    public void drop(int worldx,int worldy,int worldz,int slotIndex,ItemBean itemBean){

    }

    /**
     * 内部交换
     * @param worldx
     * @param worldy
     * @param worldz
     * @param fromslotIndex
     * @param fromItemBean
     * @param toSlotIndex
     * @param toItemBean
     */
    public void changeinner(int worldx,int worldy,int worldz,int fromslotIndex,ItemBean fromItemBean,int toSlotIndex,ItemBean toItemBean){

    }

    /**
     * 和任务的背包容器进行交换
     * @param worldx
     * @param worldy
     * @param worldz
     * @param fromslotIndex
     * @param fromItemBean
     * @param toSlotIndex
     * @param toItemBean
     */
    public void changeoutinner(int worldx,int worldy,int worldz,int fromslotIndex,ItemBean fromItemBean,int toSlotIndex,ItemBean toItemBean){

    }


}
