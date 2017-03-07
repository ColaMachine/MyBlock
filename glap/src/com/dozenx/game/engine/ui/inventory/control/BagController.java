package com.dozenx.game.engine.ui.inventory.control;

import cola.machine.game.myblocks.bean.BagEntity;
import cola.machine.game.myblocks.registry.CoreRegistry;

import com.dozenx.game.engine.item.bean.ItemBean;
import cola.machine.game.myblocks.service.BagService;
import com.dozenx.game.engine.ui.inventory.view.InventoryPanel;

import java.util.List;
import java.util.Map;

/**
 * Created by luying on 16/7/3.
 */
public class BagController {
     /*  private BagEntity bag;*/
        private BagService bagService =new BagService();

    public BagController (){
        CoreRegistry.put(BagController.class,this);
    }
   /*    public BagEntity getBag(){
           return bag;
       }*/
      /* public ItemBean[] getItemEntity(){
           return bag.getItemBeen();
       }*/
    /**
     * 将物品添加到包裹中
     * @param item
     */
     /* public void addItem(ItemBean item){
          boolean has=false;
          int nullIndex=-1;

          for(int i = 0, length = bag.getItemBeen().length; i<length ; i++){
               ItemBean itemIn = bag.getItemBeen()[i];
              if(itemIn!=null){

                  if(itemIn.getId()==item.getId()){
                      itemIn.setNum(itemIn.getNum()+item.getNum());
                      has=true;
                      break;
                  }
              }else if ( nullIndex==-1){
                  nullIndex=i;
              }
          }

          if(!has && nullIndex>=0){
              bag.getItemBeen()[nullIndex]=item;//放入物品
          }
      }*/

    /**
     * 判断是否有同类物品
     * @param bag
     * @param item
     * @return
     */
    public boolean hasItem(BagEntity bag, ItemBean item){
        for(ItemBean itemIn:bag.getItemBeen()){
            if(itemIn!=null && itemIn.getId() == item.getId()){
              return true;
            }
        }
        return false;

    }

    public Map<Integer,ItemBean> getAllItemEntity(){
        return bagService.getBagItemEntitys();
    }
    InventoryPanel inventoryPanel ;
    public void refreshBag(){
        if( inventoryPanel ==null) {
            inventoryPanel = CoreRegistry.get(InventoryPanel.class);

        }
        if( inventoryPanel!=null) {
            inventoryPanel.reload();
        }
    }
    public ItemBean[] getItemBeanList(){
        return bagService.getItemBeanList();
    }
}
