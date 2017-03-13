package com.dozenx.game.engine.item.action;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.item.ItemConfig;
import cola.machine.game.myblocks.item.ItemRepository;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.assets.texture.Texture;
import cola.machine.game.myblocks.skill.Ball;
import com.alibaba.fastjson.JSON;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.FileUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luying on 16/9/25.
 */
public class ItemManager {



    public static List<Ball> list =new ArrayList<>();
    public static void add(Ball ball){
        list.add(ball);
    }
    public static void update(){
        ShaderManager.dropItemShaderConfig.getVao().getVertices().rewind();
        for(int i=list.size()-1;i>=0;i--){
            Ball ball = list.get(i);
            GL_Vector vector = ball.getPosition();
                LivingThing player = CoreRegistry.get(Player.class);
                if( GL_Vector.length(new GL_Vector(vector,player.getPosition()))<0.5){

                    //TODO 拾取
                   // ball.=true;
                   // LivingThingManager.livingThings.get(j).beAttack(5);
                }

            ball.update(ShaderManager.dropItemShaderConfig);
           /* if(ball.died){
                list.remove(i);
            }*/
        }
        ShaderUtils.createVao(ShaderManager.dropItemShaderConfig, ShaderManager.dropItemShaderConfig.getVao(), new int[]{3, 3, 3, 1});
    }


    public static void render(){
        for(int i=list.size()-1;i>=0;i--){
            Ball ball = list.get(i);
            ball.render();
        }

        ShaderUtils.finalDraw(ShaderManager.dropItemShaderConfig,ShaderManager.dropItemShaderConfig.getVao());
    }

    public static HashMap<String, ItemDefinition> itemDefinitionMap = new HashMap<String, ItemDefinition>();
    public static HashMap<ItemType, ItemDefinition> itemType2ItemDefinitionMap = new HashMap<ItemType, ItemDefinition>();

    public ItemManager(){
        ItemConfig config =new ItemConfig();
        //config.init();
        ItemRepository itemRepository =new ItemRepository();
        //itemRepository.init(config);
        //完成初始化工作

    }


    public void loadItem() throws Exception {

        try {

            List<File> fileList = FileUtil.readAllFileInFold(PathManager.getInstance().getHomePath().resolve("config/item").toString());
            for(File file : fileList) {
                String json = FileUtil.readFile2Str(file);
                List<HashMap> textureCfgBeanList = JSON.parseArray(json, HashMap.class);
                for (int i = 1; i < ItemType.values().length; i++) {
                    ItemDefinition item = new ItemDefinition();
                    item.setName(ItemType.values()[i].toString());
                    this.putItemDefinition(ItemType.values()[i].toString(), item);

                    if(GamingState.player!=null){//区分服务器版本和客户端版本

                        TextureInfo ti = TextureManager.getTextureInfo(ItemType.values()[i].toString());
                        if(ti!=null){
                            item.getItemModel().setIcon(TextureManager.getTextureInfo(ItemType.values()[i].toString()));
                            Shape shape=new Shape();
                            shape.setShapeType(2);
                            shape.setWidth(0.5f);
                            shape.setHeight(0.5f);
                            shape.setThick(0.5f);
                            item.setShape(shape);
                            item.getItemModel().init();
                        }


                    }
                }
                for (int i = 0; i < textureCfgBeanList.size(); i++) {
                    HashMap map = textureCfgBeanList.get(i);
                    ItemDefinition item = new ItemDefinition();
                    String name = (String) map.get("name");

                    String icon = (String) map.get("icon");
                    item.setName(name);
                    // item.setIcon(this.getTextureInfo(icon));
                    String type = (String) map.get("type");
               /* if (type.equals("wear")) {*/
                    item.setType(Constants.ICON_TYPE_WEAR);
                    String position = (String) map.get("position");
                    if (position.equals("head")) {
                        item.setPosition(Constants.WEAR_POSI_HEAD);
                    } else if (position.equals("body")) {
                        item.setPosition(Constants.WEAR_POSI_BODY);
                    } else if (position.equals("leg")) {
                        item.setPosition(Constants.WEAR_POSI_LEG);
                    } else if (position.equals("foot")) {
                        item.setPosition(Constants.WEAR_POSI_FOOT);
                    } else if (position.equals("hand")) {
                        item.setPosition(Constants.WEAR_POSI_HAND);
                    }
                    int spirit = (int) map.get("spirit");
                    item.setSpirit(spirit);
                    int agile = (int) map.get("agile");
                    item.setAgile(agile);

                    int intelli = (int) map.get("intelli");
                    item.setIntelli(intelli);

                    int strenth = (int) map.get("strenth");
                    item.setStrenth(strenth);


                    this.putItemDefinition(name, item);

                    if(GamingState.player!=null){//区分服务器版本和客户端版本
                        String shapeName = (String) map.get("shape");

                        item.getItemModel().setIcon(TextureManager.getTextureInfo(icon));
                        Shape shape = TextureManager.getShape(shapeName);
                        if(shape==null){
                            item.getItemModel().init();
                        }else
                         item.setShape(shape);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to load config", e);
        }

    }




    public void putItemDefinition(String name, ItemDefinition item) {
        this.itemDefinitionMap.put(name, item);
        try {
            item.setItemType(ItemType.valueOf(name));
        }catch (Exception e){
            LogUtil.err(name);
        }
        itemType2ItemDefinitionMap.put(ItemType.valueOf(name),item);
        if (name.equals("fur_helmet")) {
            //LogUtil.println("fur_helmet");
        }
    }

    public static ItemDefinition getItemDefinition(String name) {

        ItemDefinition itemCfg = itemDefinitionMap.get(name);
        if (itemCfg == null) {
            LogUtil.println("itemCfg 为null:" + name);
            System.exit(0);
        }
        return itemCfg;
    }

    public static ItemDefinition getItemDefinition(ItemType itemType) {
        if(itemType ==null ||itemType ==  ItemType.NULL){
            return null;
        }

       // ItemDefinition itemCfg = ItemManager.getItemDefinition(itemType);
       // if(itemCfg==null){
        ItemDefinition itemCfg = itemType2ItemDefinitionMap.get(itemType);
       // }

        if (itemCfg == null) {
            LogUtil.println("itemCfg 为null:" + itemType);
            //System.exit(0);
        }
        return itemCfg;
    }
}
