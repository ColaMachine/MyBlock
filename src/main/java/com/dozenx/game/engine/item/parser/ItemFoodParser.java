package com.dozenx.game.engine.item.parser;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.ColorBlock;
import com.dozenx.game.engine.command.ItemMainType;
import com.dozenx.game.engine.element.model.CakeModel;
import com.dozenx.game.engine.element.model.IconModel;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.item.bean.ItemFoodProperties;

import java.util.Map;

/**
 * Created by dozen.zhang on 2017/5/9.
 */
public class ItemFoodParser {
    public static void parse(ItemDefinition item,Map map){

        ItemFoodProperties foodProperties = new ItemFoodProperties();
        if (map.get("hp") != null) {
            foodProperties.hp = (int) map.get("hp");
        }
        if (map.get("mp") != null) {
            foodProperties.mp = (int) map.get("mp");
        }
        item.itemTypeProperties = foodProperties;
        item.setType(ItemMainType.FOOD);
        if (GamingState.player != null) {//区分服务器版本和客户端版本

            String shapeName = (String) map.get("shape");
                        /*if(icon.equals("fur_helmet")){
                            LogUtil.println("123");
                        }*/
            item.getItemModel().setIcon(item.itemModel.getIcon());
            ColorBlock shape = (ColorBlock)TextureManager.getShape(shapeName);
            if (shape == null) {//如果没有shape 说明还没有用到该物体 还没有定义shape
                item.getItemModel().init();
            } else {
                item.setShape(shape);
                item.itemModel.wearModel = null;
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
