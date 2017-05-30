package com.dozenx.game.engine.item.bean;

import com.dozenx.game.engine.item.action.ItemEquipParser;
import com.dozenx.game.engine.item.action.ItemFoodParser;

import java.util.Map;

/**
 * item 模板存放在
 */
public class FoodDefinition extends  ItemDefinition{
   boolean haveAnotherBlock;
    float zuni;
    boolean water;
    boolean trough;
    boolean isDoor;
    public void receive(Map map ){
        super.receive(map);
        ItemFoodParser.parse(this, map);
    }
}
