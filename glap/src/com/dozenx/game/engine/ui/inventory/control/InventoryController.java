package com.dozenx.game.engine.ui.inventory.control;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import com.dozenx.game.engine.Role.bean.Player;
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
            if(itemBeans[i]!=null&& itemBeans[i].getName().equals("name")){
                return true;
            }
        }
        return false;
    }
    public void loadEquip(int slotType, ItemBean itemBean){
        if(player ==null) {
            player = CoreRegistry.get(Player.class);
        }

        if(itemBean !=null ) {//穿上装备
            ItemDefinition itemDef =  TextureManager.getItemDefinition(itemBean.getName());
            /*if(IconView.getIcon2()!=null) {
                this.setBackgroundImage(new Image(IconView.getIcon2()));
            }*/


            if (slotType == Constants.SLOT_TYPE_HEAD) {

                player.addHeadEquipStart(itemDef);
            }
            if (slotType == Constants.SLOT_TYPE_LEG) {
                player.addLegEquipStart(itemDef);
            }
            if (slotType== Constants.SLOT_TYPE_BODY) {
                player.addBodyEquipStart(itemDef);
            }
            if (slotType == Constants.SLOT_TYPE_HAND) {
                player.addHandEquipStart(itemDef);

            } if (slotType == Constants.SLOT_TYPE_FOOT) {
                player.addShoeEquipStart(itemDef);

            }
        }else{//卸下装备
            if (slotType == Constants.SLOT_TYPE_HEAD) {
                player.addHeadEquipStart(null);
            }
            if (slotType == Constants.SLOT_TYPE_LEG) {
                player.addLegEquipStart(null);

            }
            if (slotType == Constants.SLOT_TYPE_BODY) {
                player.addBodyEquipStart(null);
            }
            if (slotType == Constants.SLOT_TYPE_HAND) {
                player.addHandEquipStart(null);
            }
            if (slotType == Constants.SLOT_TYPE_FOOT) {
                player.addShoeEquipStart(null);

            }
        }

    }
}
