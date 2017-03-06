package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.manager.TextureManager;

/**
 * Created by luying on 16/7/3.
 */
public class ItemServerBean {
    int id;
    int position;
    int itemType;

    int num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
