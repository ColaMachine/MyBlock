package cola.machine.game.myblocks.service;

import cola.machine.game.myblocks.bean.ItemEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luying on 16/7/3.
 */
public class BagService {
    public Map<Integer,ItemEntity> getBagItemEntitys(){
        //test
        HashMap<Integer,ItemEntity > itemEntityMap = new HashMap<Integer,ItemEntity >();
        itemEntityMap.put(0,new ItemEntity("icon.wood_helmet",11));
        itemEntityMap.put(1,new ItemEntity("icon.wood_armour",1));
        itemEntityMap.put(2,new ItemEntity("icon.wood_pants",1));
        itemEntityMap.put(3,new ItemEntity("icon.wood_shoe",1));
        itemEntityMap.put(4,new ItemEntity("icon.wood_sword",1));
        itemEntityMap.put(5,new ItemEntity("icon.wood_pickax",1));
        itemEntityMap.put(6,new ItemEntity("icon.wood_axe",1));
        itemEntityMap.put(7,new ItemEntity("icon.wood_hoe",1));
        return itemEntityMap;
    }
}
