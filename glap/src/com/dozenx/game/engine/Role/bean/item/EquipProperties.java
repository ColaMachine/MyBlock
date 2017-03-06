package com.dozenx.game.engine.Role.bean.item;

import com.dozenx.game.engine.item.bean.ItemBean;

/**
 * Created by luying on 17/3/5.
 */
public class EquipProperties extends ItemProperties{

    ItemBean headEquip;
    ItemBean bodyEquip;

    public ItemBean getHeadEquip() {
        return headEquip;
    }

    public void setHeadEquip(ItemBean headEquip) {
        this.headEquip = headEquip;
    }

    public ItemBean getBodyEquip() {
        return bodyEquip;
    }

    public void setBodyEquip(ItemBean bodyEquip) {
        this.bodyEquip = bodyEquip;
    }

    public ItemBean getHandEquip() {
        return handEquip;
    }

    public void setHandEquip(ItemBean handEquip) {
        this.handEquip = handEquip;
    }

    public ItemBean getLegEquip() {
        return legEquip;
    }

    public void setLegEquip(ItemBean legEquip) {
        this.legEquip = legEquip;
    }

    public ItemBean getFootEquip() {
        return footEquip;
    }

    public void setFootEquip(ItemBean footEquip) {
        this.footEquip = footEquip;
    }

    ItemBean handEquip;
    ItemBean legEquip;
    ItemBean footEquip;




}
