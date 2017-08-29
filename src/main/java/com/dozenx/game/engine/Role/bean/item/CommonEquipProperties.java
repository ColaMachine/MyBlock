package com.dozenx.game.engine.Role.bean.item;

import com.dozenx.game.network.server.bean.PlayerStatus;

/**
 * Created by luying on 17/3/5.
 */
public class CommonEquipProperties extends ItemProperties{

    Integer headEquip;
    Integer bodyEquip;
    Integer handEquip;
    Integer legEquip;

    Integer footEquip;

    public Integer getHeadEquip() {
        return headEquip;
    }

    public void setHeadEquip(Integer headEquip) {
        this.headEquip = headEquip;
    }

    public Integer getBodyEquip() {
        return bodyEquip;
    }

    public void setBodyEquip(Integer bodyEquip) {
        this.bodyEquip = bodyEquip;
    }

    public Integer getHandEquip() {
        return handEquip;
    }

    public void setHandEquip(Integer handEquip) {
        this.handEquip = handEquip;
    }

    public Integer getLegEquip() {
        return legEquip;
    }

    public void setLegEquip(Integer legEquip) {
        this.legEquip = legEquip;
    }

    public Integer getFootEquip() {
        return footEquip;
    }

    public void setFootEquip(Integer footEquip) {
        this.footEquip = footEquip;
    }


    public void getInfo(PlayerStatus info) {
        super.getInfo(info);

    }

    public void setInfo(PlayerStatus info ){
        super.setInfo(info);

    }



}
