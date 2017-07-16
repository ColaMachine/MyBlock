package com.dozenx.game.engine.ui.inventory.control;

import cola.machine.game.myblocks.bean.BagEntity;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;

import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.ItemUtil;
import com.dozenx.game.engine.item.bean.ItemBean;
import cola.machine.game.myblocks.service.BagService;
import com.dozenx.game.engine.ui.inventory.bean.InventoryBean;

import com.dozenx.game.engine.ui.inventory.view.InventoryPanel;
import com.dozenx.game.engine.ui.inventory.view.PersonPanel;
import com.dozenx.game.engine.ui.toolbar.bean.ToolBarBean;
import com.dozenx.game.engine.ui.toolbar.view.ToolBarView;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by luying on 16/7/3.
 */
public class BagController {
     /*  private BagEntity bag;*/
        private BagService bagService =new BagService();

    public BagController (Player player){
        this.player =player;
        CoreRegistry.put(BagController.class,this);
    }
   /*    public BagEntity getBag(){
           return bag;
       }*/
      /* public ItemBean[] getItemEntity(){
           return bag.getItemBeen();
       }*/
    /**
     * 将物品添加到包裹中
     * @param item
     */
     /* public void addItem(ItemBean item){
          boolean has=false;
          int nullIndex=-1;

          for(int i = 0, length = bag.getItemBeen().length; i<length ; i++){
               ItemBean itemIn = bag.getItemBeen()[i];
              if(itemIn!=null){

                  if(itemIn.getId()==item.getId()){
                      itemIn.setNum(itemIn.getNum()+item.getNum());
                      has=true;
                      break;
                  }
              }else if ( nullIndex==-1){
                  nullIndex=i;
              }
          }

          if(!has && nullIndex>=0){
              bag.getItemBeen()[nullIndex]=item;//放入物品
          }
      }*/

    /**
     * 判断是否有同类物品
     * @param bag
     * @param item
     * @return
     */
    public boolean hasItem(BagEntity bag, ItemBean item){
        for(ItemBean itemIn:bag.getItemBeen()){
            if(itemIn!=null && itemIn.getId() == item.getId()){
              return true;
            }
        }
        return false;

    }

    public Map<Integer,ItemBean> getAllItemEntity(){
        return bagService.getBagItemEntitys();
    }
    InventoryPanel inventoryPanel ;
    PersonPanel personPanel ;
    ToolBarView toolBarView;
    public void refreshBag(){
        if( inventoryPanel ==null) {
            inventoryPanel = CoreRegistry.get(InventoryPanel.class);

        }
        if( personPanel ==null) {
            personPanel = CoreRegistry.get(PersonPanel.class);

        }
        if( toolBarView ==null) {
            toolBarView = CoreRegistry.get(ToolBarView.class);

        }
       // if( inventoryPanel!=null) {
            inventoryPanel.reload();
       // }
        //if( personPanel!=null) {
            personPanel.reload();
       // }
        toolBarView.reload(this);
        Document.needUpdate=true;
    }
    public ItemBean[] getItemBeanList(int index){
        if(index<35) {
            return bagService.getItemBeanList();
        }else{
            return CoreRegistry.get(BoxController.class).getNowItemBeans();

        }
    }



    InventoryBean inventoryBean =new InventoryBean();
    ToolBarBean toolBarBean =new ToolBarBean();
    Player player;
    public boolean has(String name){
        // if()
        ItemBean[] itemBeans = inventoryBean.getItemBean();
        for(int i=0;i<itemBeans.length;i++){
            if(itemBeans[i]!=null&& itemBeans[i].getItemDefinition().getName().equals("name")){
                return true;
            }
        }
        return false;
    }
    public void loadEquip(int slotType, ItemBean itemBean){
        LivingThingManager livingThingManager =CoreRegistry.get(LivingThingManager.class);
        if(player ==null) {
            player = CoreRegistry.get(Player.class);
        }

        if(itemBean !=null ) {//穿上装备
            //ItemDefinition itemDef =  TextureManager.getItemDefinition(itemBean.getName());
            /*if(IconView.getIcon2()!=null) {
                this.setBackgroundImage(new Image(IconView.getIcon2()));
            }*/


            if (slotType == Constants.SLOT_TYPE_HEAD) {

                livingThingManager.addHeadEquipStart(itemBean);
            }
            if (slotType == Constants.SLOT_TYPE_LEG) {
                livingThingManager.addLegEquipStart(itemBean);
            }
            if (slotType== Constants.SLOT_TYPE_BODY) {
                livingThingManager.addBodyEquipStart(itemBean);
            }
            if (slotType == Constants.SLOT_TYPE_HAND) {
                livingThingManager.addHandEquipStart(itemBean);

            } if (slotType == Constants.SLOT_TYPE_FOOT) {
                livingThingManager.addShoeEquipStart(itemBean);

            }
        }else{//卸下装备
            if (slotType == Constants.SLOT_TYPE_HEAD) {
                livingThingManager.addHeadEquipStart(null);
            }
            if (slotType == Constants.SLOT_TYPE_LEG) {
                livingThingManager.addLegEquipStart(null);

            }
            if (slotType == Constants.SLOT_TYPE_BODY) {
                livingThingManager.addBodyEquipStart(null);
            }
            if (slotType == Constants.SLOT_TYPE_HAND) {
                livingThingManager.addHandEquipStart(null);
            }
            if (slotType == Constants.SLOT_TYPE_FOOT) {
                livingThingManager.addShoeEquipStart(null);

            }
        }

    }

    public void changePosition(ItemBean itemBean, int position){

        int destPosition = position;
        int fromPosition = itemBean.getPosition();
        int fromIndex = fromPosition>35?fromPosition-35:fromPosition;
        int destIndex = destPosition>35?destPosition-35:destPosition;
        ItemBean[] itemFromBeans = this.getItemBeanList(fromPosition);
        ItemBean[] itemDestBeans = this.getItemBeanList(destPosition);
        if(destPosition>35){//it 's in a box

        }
        ItemBean destBean = itemDestBeans[destIndex];


        //ItemBean oldBean = itemBeans[position];
        if(destBean== itemBean)
            return ;
       /* if(oldBean!=null){
            itemBeans[itemBean.getPosition()]= oldBean;
            oldBean.setPosition(itemBean.getPosition());
        }
        itemBeans[position]= itemBean;
        itemBean.setPosition(position);*/
        if(destBean==null || destBean.getItemDefinition()==null){//拖过去
            itemDestBeans[destIndex]= itemBean;itemBean.setPosition(destPosition);
            itemFromBeans[fromIndex]= null;
        }else
        if(destBean.getItemDefinition().getItemType()== itemBean .getItemDefinition().getItemType()){//堆叠
            destBean.setNum(destBean.getNum()+itemBean.getNum());
            itemFromBeans[fromIndex]= null;
        }else{//交换
            itemFromBeans[fromIndex]= destBean;destBean.setPosition(fromPosition);
            itemDestBeans[destIndex]= itemBean;itemBean.setPosition(destPosition);

        }

        BagChangeCmd bagCmd =new BagChangeCmd(player.getId(), ItemUtil.toItemServerBean(itemBean),fromPosition,destPosition);
        bagCmd.setBoxId(CoreRegistry.get(BoxController.class).getNowBoxId());
      ResultCmd resultCmd =  CoreRegistry.get(Client.class).syncSend( bagCmd);
        if(resultCmd!=null &&resultCmd.getResult()==0 ){
            LogUtil.println("执行成功了");
        }else{
            LogUtil.err(resultCmd.toString());
        }
    }
}
