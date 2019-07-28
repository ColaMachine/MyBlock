package com.dozenx.game.engine.item.parser;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.base.ColorBlock;

import com.dozenx.game.engine.command.ItemMainType;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.item.bean.ItemBlockProperties;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.util.MapUtil;

import java.util.HashMap;
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

        String script = (String)map.get("script");
//        if(StringUtil.isNotEmpty(script)){
//            parseScript(script);
//        }
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
            colorBlock.id = item.itemTypeId;
            item.setShape(colorBlock);
        }
      //  return null;
    }

    public void parseScript(String script){
        HashMap<String,String> conditions=new HashMap<String,String>();

        //
        // top:{1:model(''),2:model('')}
        // face:rot(1,1,1 90azw3xd45yuiopoiuyre3*$(face))
        // open:rot(0,0,0 90);
        //解析的时候


        //模型计算的时候 我们拿到了 基础的模型
        int i=0;
        int length =script.length();
        int index =0;
        String lastPattern ="";
        while(index<length){
            index++;
            if(script.charAt(index )=='i'&& script.charAt(index )=='f'){
                //找寻后面的()里的条件
                lastPattern="if";
            }
        }
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
