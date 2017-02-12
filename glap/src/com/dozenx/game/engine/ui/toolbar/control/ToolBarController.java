package com.dozenx.game.engine.ui.toolbar.control;

import com.dozenx.game.engine.ui.inventory.bean.ItemBean;
import com.dozenx.game.engine.ui.toolbar.bean.ToolBarBean;

/**
 * Created by luying on 17/2/12.
 */
public class ToolBarController {
    ToolBarBean toolBarBean;
    public ToolBarController(){
        this.toolBarBean =new ToolBarBean();

    }
     public void use(int index){
         ItemBean item =toolBarBean.getItemBean(index);
        // if(item.)
     }

}
