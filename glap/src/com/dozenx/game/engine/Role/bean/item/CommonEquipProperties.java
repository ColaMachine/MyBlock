package com.dozenx.game.engine.Role.bean.item;

import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.network.server.bean.PlayerStatus;

/**
 * Created by luying on 17/3/5.
 */
public class CommonEquipProperties extends ItemProperties{

    ItemType headEquip;
    ItemType bodyEquip;
    ItemType handEquip;
    ItemType legEquip;

    ItemType footEquip;

    public ItemType getHeadEquip() {
        return headEquip;
    }

    public void setHeadEquip(ItemType headEquip) {
        this.headEquip = headEquip;
    }

    public ItemType getBodyEquip() {
        return bodyEquip;
    }

    public void setBodyEquip(ItemType bodyEquip) {
        this.bodyEquip = bodyEquip;
    }

    public ItemType getHandEquip() {
        return handEquip;
    }

    public void setHandEquip(ItemType handEquip) {
        this.handEquip = handEquip;
    }

    public ItemType getLegEquip() {
        return legEquip;
    }

    public void setLegEquip(ItemType legEquip) {
        this.legEquip = legEquip;
    }

    public ItemType getFootEquip() {
        return footEquip;
    }

    public void setFootEquip(ItemType footEquip) {
        this.footEquip = footEquip;
    }


    public void getInfo(PlayerStatus info) {
        super.getInfo(info);

    }

    public void setInfo(PlayerStatus info ){
        super.setInfo(info);

    }



}
