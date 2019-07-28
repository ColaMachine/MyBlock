package com.dozenx.game.engine.item.parser;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.base.BaseBlock;
import com.dozenx.game.engine.command.EquipPartType;
import com.dozenx.game.engine.command.ItemMainType;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.element.model.CakeModel;
import com.dozenx.game.engine.element.model.IconModel;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.item.bean.ItemWearProperties;
import core.log.LogUtil;

import java.util.Map;

/**
 * Created by dozen.zhang on 2017/5/9.
 */
public class ItemEquipParser {
    public static void parse(ItemDefinition item,Map map){

        //item.setType(Constants.ICON_TYPE_WEAR);
        ItemWearProperties properties = new ItemWearProperties();
        item.itemTypeProperties = properties;

        if (map.get("spirit") != null) {
            int spirit = (int) map.get("spirit");
            item.setSpirit(spirit);
            properties.spirit = spirit;
        }
        if (map.get("agile") != null) {
            int agile = (int) map.get("agile");
            item.setAgile(agile);
            properties.agile = agile;
        }
        if (map.get("intelli") != null) {
            int intelli = (int) map.get("intelli");
            item.setIntelli(intelli);
            properties.intel = intelli;
        }
        if (map.get("strenth") != null) {
            int strenth = (int) map.get("strenth");
            item.setStrenth(strenth);
            properties.strength = strenth;
        }
        String position = (String) map.get("position");
        if (position != null) {
            if (position.equals("head")) {
                item.setPosition(Constants.WEAR_POSI_HEAD);
                properties.part = EquipPartType.HEAD;
            } else if (position.equals("body")) {
                item.setPosition(Constants.WEAR_POSI_BODY);
                properties.part = EquipPartType.BODY;
            } else if (position.equals("leg")) {
                item.setPosition(Constants.WEAR_POSI_LEG);
                properties.part = EquipPartType.LEG;
            } else if (position.equals("foot")) {
                item.setPosition(Constants.WEAR_POSI_FOOT);
                properties.part = EquipPartType.FOOT;
            } else if (position.equals("hand")) {
                item.setPosition(Constants.WEAR_POSI_HAND);
                properties.part = EquipPartType.HAND;
            }
        }
        item.setType(ItemMainType.WEAR);
        if (GamingState.player != null) {//区分服务器版本和客户端版本
           // String shapeName = (String) map.get("shape");
                        /*if(icon.equals("fur_helmet")){
                            LogUtil.println("123");
                        }*/
            item.getItemModel().setIcon(item.itemModel.getIcon());
            BaseBlock shape = TextureManager.getShape(item.getName());
            if (shape == null) {//如果没有shape 说明还没有用到该物体 还没有定义shape
                LogUtil.println("hello");
                item.getItemModel().init();
            } else {
                item.setShape(shape);
                item.itemModel.wearModel = new BoxModel(shape);
                item.itemModel.handModel = new CakeModel(item.itemModel.getIcon());
                item.itemModel.outdoorModel = new IconModel(item.itemModel.getIcon());

                item.itemModel.placeModel = null;
            }
        }
       // return null;
    }
    public void renderHand(){

    }

    public void renderBody(){

    }
    public void renderTerrain(){

    }
    public void renderDrop(){

    }

}
