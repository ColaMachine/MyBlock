package com.dozenx.game.engine.ui.toolbar.view;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.inventory.ItemSlot;
import com.dozenx.game.engine.ui.inventory.view.IconView;
import com.dozenx.game.engine.ui.inventory.view.ItemSlotView;
import com.dozenx.game.engine.ui.inventory.view.SlotPanel;
import core.log.LogUtil;

/**
 * Created by luying on 17/2/5.
 */
public class ToolBarView extends SlotPanel {
    public ToolBarView(int numSlotsX, int numSlotsY) {
        super( numSlotsX,  numSlotsY);
        this.setPosition(HtmlObject.POSITION_ABSOLUTE);
        this.setTop(Constants.WINDOW_HEIGHT-40);
        this.setLeft(0);
        CoreRegistry.put(ToolBarView.class,this);
    }

    public void keyDown(int num){
        for(int i=0;i<10;i++){
            this.slot[i].setBorderWidth(1);
        }
       this.slot[num-1].setBorderWidth(5);
        Document.needUpdate=true;
        //LogUtil.println("hello");


      /*  switch (num){
            case 1:
                ItemSlotView icon = this.getIconInSlot(num);
        }*/

    }
  /*  public ItemSlotView getIconInSlot(int num){

        ItemSlotView  itemSlot=slot[num];
        if(itemSlot.getIconView()!=null && itemSlot.getIconView().getItemDefinition()!=null){


        }
    }*/
}
