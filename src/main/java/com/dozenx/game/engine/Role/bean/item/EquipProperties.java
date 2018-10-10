package com.dozenx.game.engine.Role.bean.item;

import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.network.server.bean.PlayerStatus;

/**
 * Created by luying on 17/3/5.
 */
public class EquipProperties extends ItemProperties{

    ItemBean headEquip;
    ItemBean bodyEquip;
    ItemBean handEquip;
    ItemBean legEquip;
    ItemBean footEquip;


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



    public void getInfo(PlayerStatus info){
        if(getHeadEquip()!=null){
            info.setHeadEquip(getHeadEquip().getItemDefinition().getItemTypeId());
        }
        if(getBodyEquip()!=null){
            info.setBodyEquip(getBodyEquip().getItemDefinition().getItemTypeId());
        }

        if(getHandEquip()!=null){
            info.setHandEquip(getHandEquip().getItemDefinition().getItemTypeId());
        }

        if(getLegEquip()!=null){
            info.setLegEquip(getLegEquip().getItemDefinition().getItemTypeId());
        }

        if(getFootEquip()!=null){
            info.setFootEquip(getFootEquip().getItemDefinition().getItemTypeId());
        }

    }
        public void setInfo(PlayerStatus info ){
            this.setHeadEquip(this.getItemById(info.getHeadEquip()));
            this.setBodyEquip(this.getItemById(info.getBodyEquip()));
            this.setHandEquip(this.getItemById(info.getHandEquip()));
            this.setLegEquip(this.getItemById(info.getLegEquip()));
            this.setFootEquip(this.getItemById(info.getFootEquip()));
        }


    public void changeProperty( ){
        super.changeProperty();
        /*// totalPower = basePower+

        // acculateProperty(this.getExecutor().getModel().bodyComponent);
        ItemBean
        if(this.handEquip!=null){
            this.totalPower+=this.handEquip.getItemDefinition().getStrenth();
            this.totalAgility+=this.handEquip.getItemDefinition().getStrenth();
        }


        totalAgility=this.baseAgility;
        totalIntell=this.baseIntell;

        totalSpirit=this.baseSpirit;

        this.HP=this.totalPower;
        this.MP=this.totalIntell;

*/


    }
}
