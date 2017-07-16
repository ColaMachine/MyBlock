package com.dozenx.game.engine.ui.toolbar.view;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.inventory.ItemSlot;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.ui.inventory.control.BagController;
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
        for(int i=0;i<10;i++){

            this.slot[i].index=25+i;
        }
    }

    public void keyDown(int num){
        for(int i=0;i<10;i++){
            this.slot[i].setBorderWidth(1);

        }
       this.slot[num-1].setBorderWidth(5);
        Document.needUpdate=true;
        if(this.slot[num-1].getIconView()!=null) {
            LivingThingManager livingThingManager = CoreRegistry.get(LivingThingManager.class);

            livingThingManager.addHandEquipStart(this.slot[num-1].getIconView().getItemBean());
        }

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

    public void reload(BagController bagController){
        ItemBean[] itemBeanList=bagController.getItemBeanList(1);


        for(int i=25;i<=34;i++){
            if(itemBeanList[i]!=null && itemBeanList[i].getItemDefinition()!=null) {
                slot[i-25].setIconView(new IconView(itemBeanList[i]));
            }else{
                slot[i-25].setIconView(null);
            }
        }

    }
}
