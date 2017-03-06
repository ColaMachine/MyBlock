package com.dozenx.game.engine.Role.bean.item;

import com.dozenx.game.engine.Role.bean.SkillProperties;
import com.dozenx.game.engine.item.bean.ItemBean;

/**
 * Created by luying on 17/3/5.
 */
public class ItemProperties extends SkillProperties {

    ItemBean[] itemBeans ;

    public ItemBean getItemById(int id ){
        for(int i=0;i<itemBeans.length;i++){
                if(itemBeans[i].getId() == id);
            return itemBeans[i];
        }
        return null;
    }
}
