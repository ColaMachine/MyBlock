package com.dozenx.game.engine.item;

import com.dozenx.game.engine.item.bean.*;

/**
 * Created by luying on 17/5/28.
 */
public enum ItemDefEnum {

   BLOCK("block",new BlockDefinition()),
    WEAR("wear",new WearDefinition()),
    FOOD("food",new FoodDefinition()),
    WEAPON("weapon",new WeaponDefinition());

    private String type;
    private ItemDefinition itemDefinition;
     ItemDefEnum(String type,ItemDefinition itemDefinition){
        this.type = type;
        this.itemDefinition = itemDefinition;

    }

}
