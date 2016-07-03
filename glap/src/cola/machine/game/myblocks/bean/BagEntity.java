package cola.machine.game.myblocks.bean;

import cola.machine.game.myblocks.engine.Constants;

/**
 * Created by luying on 16/7/3.
 */
public class BagEntity {
    private ItemEntity[] itemEntitys= new ItemEntity[Constants.BAG_CAPACITY];

    public ItemEntity[] getItemEntitys() {
        return itemEntitys;
    }

    public void setItemEntitys(ItemEntity[] itemEntitys) {
        this.itemEntitys = itemEntitys;
    }
}
