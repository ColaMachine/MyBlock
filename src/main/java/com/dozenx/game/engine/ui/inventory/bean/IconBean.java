package com.dozenx.game.engine.ui.inventory.bean;

import cola.machine.game.myblocks.model.textture.TextureInfo;

/**
 * Created by dozen.zhang on 2017/2/4.
 */
public class IconBean {
    private String id;
    private String name;
    private TextureInfo textureInfo;
    private int num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextureInfo getTextureInfo() {
        return textureInfo;
    }

    public void setTextureInfo(TextureInfo textureInfo) {
        this.textureInfo = textureInfo;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;
}
