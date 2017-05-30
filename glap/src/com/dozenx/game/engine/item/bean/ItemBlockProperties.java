package com.dozenx.game.engine.item.bean;

/**
 * Created by luying on 17/4/8.
 */
public class ItemBlockProperties extends ItemTypeProperties {
    public int hardness;//硬度
    public boolean liquid;//液体
    public boolean penetrate;

    public int getHardness() {
        return hardness;
    }

    public void setHardness(int hardness) {
        this.hardness = hardness;
    }

    public boolean isLiquid() {
        return liquid;
    }

    public void setLiquid(boolean liquid) {
        this.liquid = liquid;
    }

    public boolean isPenetrate() {
        return penetrate;
    }

    public void setPenetrate(boolean penetrate) {
        this.penetrate = penetrate;
    }


}
