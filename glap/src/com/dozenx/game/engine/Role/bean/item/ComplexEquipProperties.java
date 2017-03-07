package com.dozenx.game.engine.Role.bean.item;

import com.dozenx.game.engine.Role.bean.SynInfo;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.network.server.bean.PlayerStatus;

/**
 * Created by luying on 17/3/5.
 */
public class ComplexEquipProperties extends CommonEquipProperties implements SynInfo {

    @Override
    public void getInfo(PlayerStatus info) {
        super.setInfo(info);
        info.setHandEquip(this.getHeadEquip().ordinal());
        info.setBodyEquip(this.getBodyEquip().ordinal());
        info.setHandEquip(this.getHandEquip().ordinal());
        info.setLegEquip(this.getLegEquip().ordinal());
        info.setFootEquip(this.getFootEquip().ordinal());
    }
    @Override
    public void setInfo(PlayerStatus info ){
        super.setInfo(info);
        this.setHeadEquip(ItemType.values()[info.getHeadEquip()]);
        this.setBodyEquip(ItemType.values()[info.getBodyEquip()]);
        this.setHandEquip(ItemType.values()[info.getHandEquip()]);
        this.setLegEquip(ItemType.values()[info.getLegEquip()]);
        this.setFootEquip(ItemType.values()[info.getFootEquip()]);
    }


}
