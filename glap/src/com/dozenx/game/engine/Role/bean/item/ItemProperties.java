package com.dozenx.game.engine.Role.bean.item;

import com.dozenx.game.engine.Role.bean.SkillProperties;
import com.dozenx.game.engine.item.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/3/5.
 */
public class ItemProperties extends SkillProperties {

    List<ItemBean> itemBeans =new ArrayList<ItemBean>();

    public ItemBean getItemById(int id ){
        for(int i=0;i<itemBeans.size();i++){
                if(itemBeans.get(i).getId() == id);
            return itemBeans.get(i);
        }
        return null;
    }
}
