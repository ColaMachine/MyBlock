package com.dozenx.game.engine.item.bean;

import com.dozenx.game.engine.item.parser.ItemEquipParser;

import java.util.Map;

/**
 * item 模板存放在
 */
public class WearDefinition extends  ItemDefinition{
   boolean haveAnotherBlock;
    float zuni;
    boolean water;
    boolean trough;
    boolean isDoor;
    public void receive(Map map ){
        super.receive(map);
        ItemEquipParser.parse(this, map);
    }
}
