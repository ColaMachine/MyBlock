package cola.machine.game.myblocks.service;

import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.item.bean.ItemBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luying on 16/7/3.
 */
public class BagService {
    public Map<Integer,ItemBean> getBagItemEntitys(){
        //test
        HashMap<Integer,ItemBean> itemEntityMap = new HashMap<Integer,ItemBean>();
        itemEntityMap.put(0,new ItemBean("fur_helmet",11));
        itemEntityMap.put(1,new ItemBean("fur_armor",1));
        itemEntityMap.put(2,new ItemBean("fur_pants",1));
        itemEntityMap.put(3,new ItemBean("fur_shoe",1));
        itemEntityMap.put(4,new ItemBean("wood_sword",1));
        itemEntityMap.put(5,new ItemBean("wood_pick",1));
        itemEntityMap.put(6,new ItemBean("wood_axe",1));
        itemEntityMap.put(7,new ItemBean("wood_hoe",1));
        itemEntityMap.put(8,new ItemBean("arrow",11));
        itemEntityMap.put(9,new ItemBean("arch",1));
        itemEntityMap.put(10,new ItemBean("gold_sword",1));
        return itemEntityMap;
    }

    public ItemBean[] getItemBeanList(){
       Player human = CoreRegistry.get(Player.class);
        return human.getItemBeans();
    }
}
