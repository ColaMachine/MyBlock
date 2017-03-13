package cola.machine.game.myblocks.item;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.Shape;
import com.alibaba.fastjson.JSON;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.util.FileUtil;
import core.log.LogUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luying on 14-8-29.
 */
public class ItemManager {

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

        ItemDefinition itemCfg = TextureManager.getItemDefinition(itemType);
        if(itemCfg==null){
            itemCfg = itemType2ItemDefinitionMap.get(itemType);
        }

        if (itemCfg == null) {
            LogUtil.println("itemCfg 为null:" + itemType);
            //System.exit(0);
        }
        return itemCfg;
    }
}