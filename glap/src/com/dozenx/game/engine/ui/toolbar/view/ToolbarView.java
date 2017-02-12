package com.dozenx.game.engine.ui.toolbar.view;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.ui.inventory.ItemSlot;
import com.dozenx.game.engine.ui.inventory.view.IconView;
import com.dozenx.game.engine.ui.inventory.view.ItemSlotView;
import com.dozenx.game.engine.ui.inventory.view.SlotPanel;

/**
 * Created by luying on 17/2/5.
 */
public class ToolBarView extends SlotPanel {
    public ToolBarView(int numSlotsX, int numSlotsY) {
        super( numSlotsX,  numSlotsY);
        this.setPosition(HtmlObject.POSITION_ABSOLUTE);
        this.setTop(Constants.WINDOW_HEIGHT-40);
        this.setLeft(0);
    }

    /*public void keyDown(int num){
        switch (num){
            case 1:
                ItemSlotView icon = this.getIconInSlot(num);
        }

    }*/
  /*  public ItemSlotView getIconInSlot(int num){

        ItemSlotView  itemSlot=slot[num];
        if(itemSlot.getIconView()!=null && itemSlot.getIconView().getItemDefinition()!=null){


        }
    }*/
}
