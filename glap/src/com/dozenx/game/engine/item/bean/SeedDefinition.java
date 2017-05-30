package com.dozenx.game.engine.item.bean;

import com.dozenx.game.engine.item.action.ItemDoorParser;
import com.dozenx.game.engine.item.action.ItemSeedParser;

import java.util.Map;

/**
 * item 模板存放在
 */
public class SeedDefinition extends  BlockDefinition{
    public void receive(Map map ){
        super.receive(map);
        ItemSeedParser.parse(this, map);
    }

}
