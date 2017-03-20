package com.dozenx.game.engine.Role.bean.item;

import com.dozenx.game.engine.Role.bean.SynInfo;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.network.server.bean.PlayerStatus;

/**
 * Created by luying on 17/3/5.
 */
public class ComplexEquipProperties extends CommonEquipProperties implements SynInfo {

    @Override
    public void getInfo(PlayerStatus info) {
        super.getInfo(info);
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
    @Override
    public void changeProperty( ){
        super.changeProperty();
        // totalPower = basePower+

        // acculateProperty(this.getExecutor().getModel().bodyComponent);
        ItemType[] itemTypes = new ItemType[]{this.headEquip,this.handEquip,this.bodyEquip,this.legEquip,this.footEquip};
        for(int i=0;i<itemTypes.length;i++){
            ItemType itemType  = itemTypes[i];

           ItemDefinition itemDefinition =  ItemManager.getItemDefinition(itemType);
            if(itemDefinition!=null){
                this.totalPower+=itemDefinition.getStrenth();
                this.totalAgility+=itemDefinition.getAgile();
                this.totalIntell+=itemDefinition.getIntelli();
                this.totalSpirit+=itemDefinition.getSpirit();
            }

        }




        this.HP=this.totalPower;
        this.MP=this.totalIntell;
        this.pattack = (int)(this.totalPower*0.6+this.totalAgility*0.4);
        this.defense =this.totalAgility;




    }

}
