package com.dozenx.game.engine.item.bean;

/**
 * Created by luying on 16/7/3.
 */
public class ItemBean {
    int id;
    ItemDefinition itemDefinition;

    public ItemDefinition getItemDefinition() {
        return itemDefinition;
    }

    public void setItemDefinition(ItemDefinition itemDefinition) {
        this.itemDefinition = itemDefinition;
    }

    int num;
    int brokenDegree;// 1/100
    public ItemBean(ItemDefinition itemDefinition, int num)
    {
        this.itemDefinition=itemDefinition;
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
