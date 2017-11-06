package com.dozenx.game.engine.Role.bean.item;

import com.dozenx.game.engine.Role.bean.SkillProperties;
import com.dozenx.game.engine.item.ItemUtil;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.bean.PlayerStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/3/5.
 */
public class ItemProperties extends SkillProperties {

    ItemBean[] itemBeans =new ItemBean[45];
    public void getInfo(PlayerStatus info) {
        super.getInfo(info);
    }

    public ItemBean[] getItemBeans() {
        return itemBeans;
    }
    public List<ItemServerBean> getItemBeansList() {
        List<ItemServerBean> itemServerBeanList =new ArrayList<ItemServerBean>();
        for(ItemBean itemBean:itemBeans){
            ItemServerBean itemServerBean =ItemUtil.toItemServerBean(itemBean);
            if(itemServerBean!=null && itemServerBean.getPosition()<35){
                itemServerBeanList.add(itemServerBean);
            }
        }
        return itemServerBeanList;
    }


/*

    public void setItemBeans(List<ItemBean> itemBeans) {
        this.itemBeans = itemBeans;
    }
*/

    public void setInfo(PlayerStatus info ){
        super.setInfo(info);

    }
    public void setItem(int position, ItemBean itemBean){

        itemBeans[position] = itemBean;if(itemBean!=null ){itemBean.setPosition(position);}
    }
    public void  setItems(List<ItemServerBean > list){
       // itemBeans.clear();
        for(int i=0;i<this.itemBeans.length;i++){
           /*if(bean.getPosition()>=list.size()){
                   for(int i=0;i<list.size()-bean.getPosition()+1;i++){
                       itemBeans.add(null);
                   }
           }*/
            itemBeans[i]=null;
        }
       for(ItemServerBean bean : list){
           /*if(bean.getPosition()>=list.size()){
                   for(int i=0;i<list.size()-bean.getPosition()+1;i++){
                       itemBeans.add(null);
                   }
           }*/
           setItem(bean.getPosition(), ItemUtil.toItemBean(bean));
       }
    }


    public ItemBean getItemById(int id ){
        for(ItemBean itemBean: itemBeans){
            if(itemBean.getId() == id);
            return itemBean;
        }
  /*      for(int i=0;i<itemBeans.size();i++){
                if(itemBeans.get(i).getId() == id);
            return itemBeans.get(i);
        }*/
        return null;
    }
}
