package cola.machine.game.myblocks.bean;

import cola.machine.game.myblocks.engine.Constants;
import com.dozenx.game.engine.item.bean.ItemBean;

/**
 * Created by luying on 16/7/3.
 */
public class BagEntity {
    private ItemBean[] itemBeen = new ItemBean[Constants.BAG_CAPACITY];

    public ItemBean[] getItemBeen() {
        return itemBeen;
    }

    public void setItemBeen(ItemBean[] itemBeen) {
        this.itemBeen = itemBeen;
    }
}
