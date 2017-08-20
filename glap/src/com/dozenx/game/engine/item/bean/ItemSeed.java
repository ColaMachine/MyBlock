package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.math.Vector3i;
import com.dozenx.game.engine.command.ItemType;
import glmodel.GL_Vector;

/**
 * Created by dozen.zhang on 2017/5/9.
 */
public class ItemSeed {
    GL_Vector position ;
    Integer seedType;
    Long plantedTime;

    public GL_Vector getPosition() {
        return position;
    }

    public void setPosition(GL_Vector position) {
        this.position = position;
    }

    public Integer getSeedType() {
        return seedType;
    }

    public void setSeedType(Integer seedType) {
        this.seedType = seedType;
    }

    public Long getPlantedTime() {
        return plantedTime;
    }

    public void setPlantedTime(Long plantedTime) {
        this.plantedTime = plantedTime;
    }
}
