package com.dozenx.game.engine.item.bean;

/**
 * Created by luying on 16/7/3.
 */
public class ItemServerBean {
    int id;
    int position;
    float x;
    float y;
    float z;
    public boolean died =false;//要被系统回收了
    public long dropTime =0;//什么时候掉落在地上的 超过一定时间会被系统回收的

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

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
