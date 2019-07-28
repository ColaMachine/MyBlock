package com.dozenx.game.engine.item.parser;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import com.dozenx.game.engine.item.bean.BoxDefinition;
import com.dozenx.game.engine.item.bean.ItemBlockProperties;
import com.dozenx.util.MapUtil;

import java.util.Map;

/**
 * Created by dozen.zhang on 2017/5/9.
 */
public class ItemBoxParser {
    public static void parse(BoxDefinition item,Map map){
        ItemBlockProperties blockProperties = (ItemBlockProperties)item.itemTypeProperties;




        if (GamingState.player != null) {//区分服务器版本和客户端版本
            String shapeOpen = MapUtil.getStringValue(map, "shapeOpen");
            item.shapeOpen = TextureManager.getShape(shapeOpen);
            //ItemDefinition已经解析好了 不需要再对shape 进行解析
//            String shapeName = MapUtil.getStringValue(map,"shape");
//                        /*if(icon.equals("fur_helmet")){
//                            LogUtil.println("123");
//                        }*/
//            item.getItemModel().setIcon(item.itemModel.getIcon());
//            BaseBlock shape = TextureManager.getShape(shapeName);
//            if (shape == null) {//如果没有shape 说明还没有用到该物体 还没有定义shape
//                item.getItemModel().init();
//            } else {
//                item.setShape(shape);
//                item.itemModel.wearModel = null;
//                item.itemModel.handModel = new BoxModel(item.getShape());
//                item.itemModel.outdoorModel = new BoxModel(item.getShape());
//
//                item.itemModel.placeModel = new BoxModel(item.getShape());
//            }
//            item.setType(ItemMainType.BLOCK);
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
