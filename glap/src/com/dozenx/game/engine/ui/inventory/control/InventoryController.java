package com.dozenx.game.engine.ui.inventory.control;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.model.textture.ItemDefinition;
import cola.machine.game.myblocks.registry.CoreRegistry;
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
                human.addHeadEquip(itemDef);
            }
            if (slotType == Constants.SLOT_TYPE_LEG) {
                human.addLegEquip(itemDef);
            }
            if (slotType== Constants.SLOT_TYPE_BODY) {
                human.addBodyEquip(itemDef);
            }
            if (slotType == Constants.SLOT_TYPE_HAND) {
                human.addHandEquip(itemDef);
            }
        }else{//卸下装备
            if (slotType == Constants.SLOT_TYPE_HEAD) {
                human.addHeadEquip(null);
            }
            if (slotType == Constants.SLOT_TYPE_LEG) {
                human.addLegEquip(null);

            }
            if (slotType == Constants.SLOT_TYPE_BODY) {
                human.addBodyEquip(null);
            }
            if (slotType == Constants.SLOT_TYPE_HAND) {
                human.addHandEquip(null);
            }
        }

    }
}
