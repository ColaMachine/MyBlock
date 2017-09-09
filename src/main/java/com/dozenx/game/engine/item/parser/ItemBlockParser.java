package com.dozenx.game.engine.item.parser;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.ColorBlock;

import com.dozenx.game.engine.command.ItemMainType;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.item.bean.ItemBlockProperties;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.util.MapUtil;

import java.util.Map;

/**
 * Created by dozen.zhang on 2017/5/9.
 */
public class ItemBlockParser {
    public static void parse(ItemDefinition item,Map map){

        ItemBlockProperties blockProperties = (ItemBlockProperties)item.itemTypeProperties;//new ItemBlockProperties();
        item.live = MapUtil.getBooleanValue(map,"live",false);
        Object penetrate = map.get("penetrate");
        if(blockProperties ==null){
            blockProperties = new ItemBlockProperties();
        }
        if(penetrate!=null){
                blockProperties.setPenetrate((Boolean)penetrate);
        }
        if (map.get("hardness") != null) {
            blockProperties.hardness = (int) map.get("hardness");
        }
        item.itemTypeProperties = blockProperties;
        item.setType(ItemMainType.BLOCK);
        
        if (GamingState.player != null) {//区分服务器版本和客户端版本
//            String shapeName = (String) map.get("shape");
                        /*if(icon.equals("fur_helmet")){
                            LogUtil.println("123");
                        }*/
            item.getItemModel().setIcon(item.itemModel.getIcon());
           // BaseBlock shape = TextureManager.getShape(shapeName);
            if (item.getShape() == null) {//如果没有shape 说明还没有用到该物体 还没有定义shape
                item.getItemModel().init();
            } else {

                item.itemModel.wearModel = null;
                item.itemModel.handModel = new BoxModel(item.getShape() );
                item.itemModel.outdoorModel = item.itemModel.handModel;

                item.itemModel.placeModel = item.itemModel.handModel;
            }
        }else{
            ColorBlock colorBlock =new ColorBlock();
            colorBlock.id = item.id;
            item.setShape(colorBlock);
        }
      //  return null;
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
