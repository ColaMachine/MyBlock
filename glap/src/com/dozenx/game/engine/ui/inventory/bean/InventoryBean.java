package com.dozenx.game.engine.ui.inventory.bean;

import cola.machine.game.myblocks.engine.Constants;

/**
 * Created by dozen.zhang on 2017/2/4.
 */
public class InventoryBean {
    private ItemBean[] itemBean=new ItemBean[Constants.INVENTORY_SLOT_X_NUM * Constants.INVENTORY_SLOT_Y_NUM];

    public ItemBean[] getItemBean(){
        return itemBean;
    }

}
