package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.manager.TextureManager;
import com.dozenx.game.engine.item.action.ItemManager;

/**
 * Created by luying on 16/7/3.
 */
public class ItemBean  {
    int id;
    int position;
    ItemDefinition itemDefinition;
    int num;
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public ItemDefinition getItemDefinition() {
        return itemDefinition;
    }

    public void setItemDefinition(ItemDefinition itemDefinition) {
        this.itemDefinition = itemDefinition;
    }


    int brokenDegree;// 1/100
    public ItemBean()
    {

    }
    public ItemBean(ItemDefinition itemDefinition, int num)
    {
        this.itemDefinition=itemDefinition;
        this.num=num;
    }

    public ItemBean(String itemName, int num)
    {
        this.itemDefinition= ItemManager.getItemDefinition(itemName);
        this.num=num;
    }
    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
