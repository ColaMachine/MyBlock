package com.dozenx.game.engine.item;

import com.dozenx.game.engine.command.ItemType;

/**
 * Created by luying on 17/2/28.
 */
public class ItemUtil {
    public static  boolean isFarWeapon(int  itemType){
        if(itemType == ItemType.arch.ordinal()){
            return true;
        }
        return false;
    }
}
