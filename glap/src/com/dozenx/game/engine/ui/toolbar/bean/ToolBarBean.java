package com.dozenx.game.engine.ui.toolbar.bean;

import cola.machine.game.myblocks.engine.Constants;
import com.dozenx.game.engine.ui.inventory.bean.IconBean;
import com.dozenx.game.engine.ui.inventory.bean.ItemBean;

/**
 * Created by luying on 17/2/5.
 */
public class ToolBarBean {
    private ItemBean[] itemBeens=new ItemBean[10];
    public ItemBean getItemBean(int index){
        return index>itemBeens.length?null : itemBeens[index-1];
    }
}
