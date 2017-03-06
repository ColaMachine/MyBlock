package com.dozenx.game.engine.ui.inventory.control;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.ui.inventory.bean.InventoryBean;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.ui.toolbar.bean.ToolBarBean;

/**
 * Created by dozen.zhang on 2017/2/4.
 */
public class InventoryController {
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
}
