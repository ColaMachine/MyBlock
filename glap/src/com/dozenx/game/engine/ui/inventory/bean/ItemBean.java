package com.dozenx.game.engine.ui.inventory.bean;

import com.dozenx.game.engine.command.ItemType;

/**
 * Created by luying on 16/7/3.
 */
public class ItemBean {
    int id;

    String name;
    int num;
    int brokenDegree;// 1/100
    public ItemBean(String name, int num)
    {
        this.name=name;
        this.num=num;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
