package com.dozenx.game.engine.ui.inventory.control;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.model.textture.ItemDefinition;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.server.NetWorkManager;
import com.dozenx.game.engine.command.EquipPartType;
import com.dozenx.game.engine.command.EquipmentCmd;
import com.dozenx.game.engine.ui.inventory.bean.InventoryBean;
import com.dozenx.game.engine.ui.inventory.bean.ItemBean;
import com.dozenx.game.engine.ui.toolbar.bean.ToolBarBean;

/**
 * Created by dozen.zhang on 2017/2/4.
 */
public class InventoryController {
    InventoryBean inventoryBean =new InventoryBean();
    ToolBarBean toolBarBean =new ToolBarBean();
    Human human;
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
        if(human==null) {
            human = CoreRegistry.get(Human.class);
        }

        if(itemBean !=null ) {//穿上装备
            ItemDefinition itemDef =  TextureManager.getItemDefinition(itemBean.getName());
            /*if(IconView.getIcon2()!=null) {
                this.setBackgroundImage(new Image(IconView.getIcon2()));
            }*/


            if (slotType == Constants.SLOT_TYPE_HEAD) {

                human.addHeadEquipStart(itemDef);
            }
            if (slotType == Constants.SLOT_TYPE_LEG) {
                human.addLegEquipStart(itemDef);
            }
            if (slotType== Constants.SLOT_TYPE_BODY) {
                human.addBodyEquipStart(itemDef);
            }
            if (slotType == Constants.SLOT_TYPE_HAND) {
                human.addHandEquipStart(itemDef);

            } if (slotType == Constants.SLOT_TYPE_FOOT) {
                human.addShoeEquipStart(itemDef);

            }
        }else{//卸下装备
            if (slotType == Constants.SLOT_TYPE_HEAD) {
                human.addHeadEquipStart(null);
            }
            if (slotType == Constants.SLOT_TYPE_LEG) {
                human.addLegEquipStart(null);

            }
            if (slotType == Constants.SLOT_TYPE_BODY) {
                human.addBodyEquipStart(null);
            }
            if (slotType == Constants.SLOT_TYPE_HAND) {
                human.addHandEquipStart(null);
            }
            if (slotType == Constants.SLOT_TYPE_FOOT) {
                human.addShoeEquipStart(null);

            }
        }

    }
}
