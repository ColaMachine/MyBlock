package com.dozenx.game.engine.item.action;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.item.ItemConfig;
import cola.machine.game.myblocks.item.ItemRepository;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.Ball;
import com.alibaba.fastjson.JSON;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.command.PickCmd;
import com.dozenx.game.engine.item.bean.DoorDefinition;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.item.bean.StairDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.FileUtil;
import com.dozenx.util.TimeUtil;
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

    public static List<Ball> list = new ArrayList<>();
    private static HashMap<String, ItemDefinition> itemDefinitionMap = new HashMap<String, ItemDefinition>();
    public static HashMap<Integer, ItemDefinition> itemType2ItemDefinitionMap = new HashMap<Integer, ItemDefinition>();
    static float distance = 0;
    long lastCheckTime = 0;

    public ItemManager() {

        ItemConfig config = new ItemConfig();
        //config.init();
        ItemRepository itemRepository = new ItemRepository();
        //itemRepository.init(config);
        //完成初始化工作

    }

    public static void removeWorldItem(int itemId) {
        int index = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            Ball ball = list.get(i);
            if (ball.getId() == itemId) {
                list.remove(i);
            }
        }

    }

    public static void add(Ball ball) {
        long now = TimeUtil.getNowMills();

        for (int i = list.size() - 1; i >= 0; i--) {
            Ball checkBall = list.get(i);
            if (checkBall.getId() == ball.getId()) {
                ball.lastSynTime = now;
                //更新ball的位置信息
                return;
            }
        }
        ball.lastSynTime = now;
        list.add(ball);//如果没有重复 就添加
    }

    public static void render() {
        for (int i = list.size() - 1; i >= 0; i--) {
            Ball ball = list.get(i);
            ball.render();
        }
        if (list.size() > 0) {
            ShaderUtils.finalDraw(ShaderManager.dropItemShaderConfig, ShaderManager.dropItemShaderConfig.getVao());
        }


    }

    public static ItemDefinition getItemDefinition(String name) {

        ItemDefinition itemCfg = itemDefinitionMap.get(name);
        if (itemCfg == null) {
            LogUtil.println("itemCfg 为null:" + name);
            // System.exit(0);
        }
        return itemCfg;
    }

    public static ItemDefinition getItemDefinition(Integer itemType) {
        if (itemType == null || itemType <=0) {
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

    public void update() {
        long now = TimeUtil.getNowMills();
        if (now - lastCheckTime < 200) {//减少检查和网络发送
            return;
        }
        lastCheckTime = now;
        int deleteIndex = -1;
        ShaderManager.dropItemShaderConfig.getVao().getVertices().rewind();
        for (int i = list.size() - 1; i >= 0; i--) {
            Ball ball = list.get(i);
            if (now - ball.lastSynTime > 50000) {
                deleteIndex = i;
                continue;
            }
            if (now - ball.lastPickTime > 2000) {
                GL_Vector vector = ball.getPosition();
                LivingThing player = CoreRegistry.get(Player.class);
                distance = GL_Vector.length(new GL_Vector(vector, player.getPosition()));
                if (distance < 1.8) {
                    if (player.getItemBeansList().size() < 24) {
                        ball.lastPickTime = now;
                        CoreRegistry.get(Client.class).send(new PickCmd(player.getId(), ball.getId()));
                    }


                    //TODO 拾取
                    // ball.=true;
                    // LivingThingManager.livingThings.get(j).beAttack(5);
                }
            }


            ball.update(ShaderManager.dropItemShaderConfig);
          /* if(ball.died){
                list.remove(i);
            }*/
        }
        if (deleteIndex >= 0) {
            list.remove(deleteIndex);
        }
        ShaderUtils.freshVao(ShaderManager.dropItemShaderConfig, ShaderManager.dropItemShaderConfig.getVao());
    }

    /**
     * 加载这个物体的定义 这个物体可能是 生物 也可能是普通方块 也可能是 食物 装备 等等 所有 游戏里的组成元素都是一个定义
     * 可能来自 itemType中已经有的基本物体 也可能来自 item 文件夹里的.cfg配置出来的物体 还有item/newitem 也就是 制作工具里制作出来的东西
     * @throws Exception
     */
    public void loadItemDefinition() throws Exception {

        try {
            //先按照itemtype enum加载数据 这里是偷懒了 不想都写item.cfg
            /*for (int i = 1; i < ItemType.values().length; i++) {
                ItemDefinition item = new ItemDefinition();//
                item.setName(ItemType.values()[i].toString());//简单设置英文名称
                this.putItemDefinition(ItemType.values()[i].toString(), item);//入仓库

                if (GamingState.player != null) {//区分服务器版本和客户端版本

                    TextureInfo ti = TextureManager.getTextureInfo(ItemType.values()[i].toString());
                    if (ti != null) {//先不要shape了 因为很多都没有shape 后续在 item.cfg里配置的去读取shape
                        item.getItemModel().setIcon(TextureManager.getTextureInfo(ItemType.values()[i].toString()));
                        Shape shape = new Shape();
                        shape.setShapeType(ShapeType.CAKE.ordinal());
                        shape.setWidth(0.5f);
                        shape.setHeight(0.5f);
                        shape.setThick(0.5f);
                        item.setShape(shape);
                        item.getItemModel().init();//如果是cake 就需要初始化block
                    }


                }
            }*/
            //读取item文件夹下所有的配置信息
            ItemFactory itemFactory =new ItemFactory();
            List<File> fileList = FileUtil.readAllFileInFold(PathManager.getInstance().getHomePath().resolve("config/item").toString());
            for (File file : fileList) {//遍历配置文件
                LogUtil.println("开始解析"+file.getName());
                if(file.getName().startsWith("box")){
                    LogUtil.println("steve");
                }
                String json = FileUtil.readFile2Str(file);

                if(json.trim().startsWith("[")) {//说ing是一个数组
                    List<HashMap> textureCfgBeanList = JSON.parseArray(json, HashMap.class);

                    for (int i = 0; i < textureCfgBeanList.size(); i++) {
                        HashMap map = textureCfgBeanList.get(i);

                        ItemDefinition itemDef = itemFactory.parse(map);

                        if(itemDef.getName().equals("box_image")){
                            LogUtil.println("wood_door_up");
                        }
                        if(itemDef.getName().equals("wood_door_down")){
                            LogUtil.println("wood_door_down");
                        }
                        // ItemDefinition itemDef = new ItemDefinition();


                        this.putItemDefinition(itemDef.getName(), itemDef);


                    }
                }else{//只有一个单体
                    HashMap map = JSON.parseObject(json, HashMap.class);
                    ItemDefinition itemDef = itemFactory.parse(map);
                    // ItemDefinition itemDef = new ItemDefinition();


                    this.putItemDefinition(itemDef.getName(), itemDef);

                }
            }
            //this.loadDiyItem();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to load config", e);
        }

    }
    //加载复杂类物品定义
    public static void loadDiyItem(){
//        DoorDefinition definition = new DoorDefinition();
//        definition.init();
//        putItemDefinition("wood_block",definition);
//
//
//        StairDefinition stairDefinition = new StairDefinition();
//        stairDefinition.init();
//        putItemDefinition("wood_block",stairDefinition);
    }
    public static void putItemDefinition(String name, ItemDefinition item) {
        itemDefinitionMap.put(name, item);
        try {
            item.setItemTypeId(item.itemTypeId);
        } catch (Exception e) {
            LogUtil.err(name);
        }
        if(item.itemTypeId==0){
            LogUtil.println("item.id should not be 0s");
        }
        itemType2ItemDefinitionMap.put(/*ItemType.valueOf(name).ordinal()*/item.itemTypeId, item);
        if (name.equals("wood_axe")) {
            LogUtil.println("fur_helmet");
        }
    }


}
