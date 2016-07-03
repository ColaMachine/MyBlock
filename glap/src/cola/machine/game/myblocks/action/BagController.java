package cola.machine.game.myblocks.action;

import cola.machine.game.myblocks.bean.BagEntity;
import cola.machine.game.myblocks.bean.ItemEntity;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.service.BagService;

import java.util.Map;

/**
 * Created by luying on 16/7/3.
 */
public class BagController {
       private BagEntity bag;
        private BagService bagService =new BagService();
       public BagEntity getBag(){
           return bag;
       }
       public ItemEntity[] getItemEntity(){
           return bag.getItemEntitys();
       }
    /**
     * 将物品添加到包裹中
     * @param item
     */
      public void addItem(ItemEntity item){
          boolean has=false;
          int nullIndex=-1;

          for(int i=0,length=bag.getItemEntitys().length;i<length ;i++){
               ItemEntity itemIn = bag.getItemEntitys()[i];
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
              bag.getItemEntitys()[nullIndex]=item;//放入物品
          }
      }

    /**
     * 判断是否有同类物品
     * @param bag
     * @param item
     * @return
     */
    public boolean hasItem(BagEntity bag, ItemEntity item){
        for(ItemEntity itemIn:bag.getItemEntitys()){
            if(itemIn!=null && itemIn.getId() == item.getId()){
              return true;
            }
        }
        return false;

    }

    public Map<Integer,ItemEntity> getAllItemEntity(){
        return bagService.getBagItemEntitys();
    }
}
