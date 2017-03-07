package com.dozenx.game.engine.item;

import cola.machine.game.myblocks.manager.TextureManager;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemServerBean;

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

    public static ItemBean toItemBean(ItemServerBean itemServerBean){
        ItemBean  itemBean=new ItemBean();
        itemBean.setId(itemServerBean.getId());
        itemBean.setItemDefinition(TextureManager.getItemDefinition(ItemType.values()[itemServerBean.getItemType()]));
        itemBean.setNum(itemServerBean.getNum());
        itemBean.setPosition(itemServerBean.getPosition());
        return itemBean;
    }
}
