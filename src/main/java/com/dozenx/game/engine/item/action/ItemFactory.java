package com.dozenx.game.engine.item.action;

import com.dozenx.game.engine.item.bean.*;
import com.dozenx.util.MapUtil;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;

import java.util.Map;

/**
 * Created by luying on 17/5/27.
 */
public class ItemFactory {

    public ItemFactory(){
        //注册各种parse

        //注册各种已知的item
    }
    public ItemDefinition parse(Map map ) throws CloneNotSupportedException {
//        String type = MapUtil.getStringValue(map, "type");
      String name = MapUtil.getStringValue(map,"name");

        int id = MapUtil.getIntValue(map,"id");
        if(id==7){
            LogUtil.println("7");
        }
        if(name.equals("langan3")){
            LogUtil.println("is arrow");
        }
//        String category = MapUtil.getStringValue(map,"category");
        String baseOn = MapUtil.getStringValue(map,"baseon");
//        String icon =MapUtil.getStringValue(map,"icon");//获取icon图片
        String engine = MapUtil.getStringValue(map,"engine");

        String type = MapUtil.getStringValue(map,"type");
        String category = MapUtil.getStringValue(map,"category");

        engine = StringUtil.isNotEmpty(engine)?engine : (StringUtil.isNotEmpty(category)?category:type);
        ItemDefinition itemDef = null;

        //itemDef.setName(name);
       // itemDef.getShape().getTi
        //物品的定义是允许baseOn的 就先继承父亲的所有属性
        if(StringUtil.isNotEmpty(baseOn)){
            //copy 一份定义过来
            ItemDefinition  parentDef = ItemManager.getItemDefinition(baseOn);
            if(parentDef==null){
                LogUtil.err(baseOn+"is null ");//说明顺序在她后面
            }
            itemDef = parentDef.clone();//继承
            //type= itemDef.getType().toString().toLowerCase();
            itemDef.receive(map);//如果是一个新的方块的画就有问题 如果是一个box 方块 那么她必须要继承box

            //解析position

        }else
        //再处理特殊的属性
        //首先确定engine engine 决定她的 definition的类型.
        if(engine!=null ){
            if(engine .equals("block")){
                itemDef = new BlockDefinition();
            }else
            if(engine .equals("door")){
                itemDef = new DoorDefinition();
            }else
            if(engine .equals("stair")){
                itemDef = new StairDefinition();
            }else if(engine .equals("box")){
                itemDef = new BoxDefinition();
            }else
            if(engine .equals("food")){
                itemDef = new FoodDefinition();
            }else
            if(engine .equals("wear")){
                itemDef = new WearDefinition();
            }else
            if(engine .equals("weapon")){
                itemDef = new WeaponDefinition();
            }else
            if(engine .equals("seed")){
                itemDef = new SeedDefinition();
            }else
            if(engine .equals("dirblock")){
                itemDef = new DirBlockDefinition();
            }else
            if(engine .equals("objblock")){
                itemDef = new ObjDefinition();
            }else{
                LogUtil.err("not here");
            }
            itemDef.receive(map);
        }/**/


        if(itemDef == null ){
            itemDef =new ItemDefinition();
        }
       /* itemDef.setName(name);
        if(type==null){
            LogUtil.err("err");
        }*/

        //itemDef.setIcon(TextureManager.getTextureInfo(icon));
        //String type = (String) map.get("type");
        //分类

        /*if (GamingState.player != null) {//区分服务器版本和客户端版本
            TextureInfo ti = TextureManager.getTextureInfo(icon);
            if(ti ==null){
                LogUtil.err(name + "'s icon should not be null");
            }
            itemDef.getItemModel().setIcon(ti);
        }
        if("door".equals(category)){
            ItemDoorParser.parse(itemDef,map);
        }else
        if("seed".equals(category)){
            ItemSeedParser.parse(itemDef,map);
        }else
        if (type.equals("wear")) {
            ItemEquipParser.parse(itemDef,map);

        } else if (type.equals("food")) {
            ItemFoodParser.parse(
                    itemDef,map);

        } else if (type.equals("block")) {
            ItemBlockParser.parse(itemDef,map);

        }*/
                    /*String spiritStr = map.get("spirit");
                    if(StringUtil.isNotEmpty(spiritStr)){
                        item.setSpirit(spirit);
                    }*/
        itemDef.itemTypeId = id;
    return itemDef;

    }
}
