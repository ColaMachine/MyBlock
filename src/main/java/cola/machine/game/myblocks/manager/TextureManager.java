package cola.machine.game.myblocks.manager;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.model.ImageBlock;
import cola.machine.game.myblocks.model.RotateColorBlock2;
import cola.machine.game.myblocks.model.textture.BoneBlock;
import cola.machine.game.myblocks.model.textture.ImageInfo;
import cola.machine.game.myblocks.model.textture.TextureCfgBean;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.edit.EditEngine;
import com.dozenx.game.engine.edit.view.AnimationBlock;
import com.dozenx.game.engine.edit.view.GroupBlock;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.util.FileUtil;
import com.dozenx.util.MapUtil;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import de.matthiasmann.twl.renderer.Texture;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import glapp.GLApp;
import glapp.GLImage;
import glmodel.GLModel;
import glmodel.GLModelContainer;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by luying on 14-8-28.
 */
public class TextureManager {

    private static final Logger logger = LoggerFactory.getLogger(TextureManager.class);
    Path installPath;
    public static HashMap<String, GLImage> imageMap = new HashMap<String, GLImage>();
    public static HashMap<String ,List<BoneBlock>> shapeGroups =new HashMap<>();
    public static HashMap<String, TextureInfo> textureInfoMap = new HashMap<String, TextureInfo>();
    public static HashMap<String, Texture> textureMap = new HashMap<String, Texture>();

    public static HashMap<String, GLModel> objMap = new HashMap<String, GLModel>();
/*    public static HashMap<String, ItemDefinition> itemDefinitionMap = new HashMap<String, ItemDefinition>();
    public static HashMap<ItemType, ItemDefinition> itemType2ItemDefinitionMap = new HashMap<ItemType, ItemDefinition>();*/
    public static HashMap<String, BaseBlock> shapeMap = new HashMap<String, BaseBlock>();
    //stateid to block
    public static HashMap<Integer, BaseBlock> idShapeMap = new HashMap<Integer, BaseBlock>();

    public HashMap<String, ImageInfo> ImageInfoMap = new HashMap<>();

    public TextureManager() {

        CoreRegistry.put(TextureManager.class,this);
        installPath = PathManager.getInstance().getInstallPath();
        try {
            Util.checkGLError();
            loadImage();
            loadTexture();
            loadObj();
            loadShape();
            //loadItem();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
      /*  this.putImage("grass_top", "assets/blockTiles/plant/Grass.png");
        this.putImage(
                "human", "assets.images/char.png"
        );*/
        //textureInfoMap.put("human", new TextureInfo("human"));
        //this.putImage("heightmap", "assets.images/gray.png");
        // textureInfoMap.put("heightmap", new TextureInfo("heightmap"));
        // this.putImage("background", "assets.images/background.png");

        // textureInfoMap.put("background", new TextureInfo("background"));
        // this.putImage("gui", "assets.images/gui.png");
        // textureInfoMap.put("cross", new TextureInfo("gui", 1 / 12f + 0.01f, 10 / 12f, 1 / 12f, 1 / 12f, true));

        //textureInfoMap.put("selectBox", new TextureInfo("gui", 0, 210, 23, 23));


        // this.putImage("widgets", "assets/minecraft/textures/gui/widgets.png");
        // textureInfoMap.put("toolbar", new TextureInfo("widgets", 0, 469, 362, 43));

        //this.put("night", "assets.images/night.jpg");
        //textureInfoMap.put("night", new TextureInfo("night"));

        //this.put("most","assets.images/fewest.tif");
        //textureInfoMap.put("most",new TextureInfo("most"));
        /*this.putImage("sun", "assets/minecraft/textures/environment/sun.png");
        textureInfoMap.put("sun", new TextureInfo("sun"));*/

        //this.putImage("inventory", "assets/minecraft/textures/gui/container/inventory.png");
        //textureInfoMap.put("bag", new TextureInfo("inventory", 0, 179, 352, 332));

        //   this.putImage("human", "assets.images/2000.png");
        //textureInfoMap.put("bag",new TextureInfo("inventory",0,179,352,332));

        /*this.putImage("apple_golden", "assets/minecraft/textures/items/apple_golden.png");

        this.putImage("items", "assets.images/items.png");


        textureInfoMap.put("apple_golden", new TextureInfo("apple_golden"));

        this.putImage("terrain", "assets/minecraft/textures/terrain.png");
        textureInfoMap.put("water", new TextureInfo("terrain", 14, 2, 1, 1, 16, 16));

       this.putImage("gold_armor","assets/minecraft/textures/models/armor/gold_layer_2.png");
        textureInfoMap.put("gold_armor", new TextureInfo("gold_armor"));
      
       // this.putImage("particle","assets.images/Particle.bmp");
       // textureInfoMap.put("particle", new TextureInfo("particle"));
      
       
        textureInfoMap.put("grass_top", new TextureInfo("terrain", 8, 5, 0.8f, 0.8f, 16, 16));
        textureInfoMap.put("mantle", new TextureInfo("terrain", 1, 14, 1, 1, 16, 16));
        textureInfoMap.put("glass", new TextureInfo("terrain", 1, 12, 1, 1, 16, 16));

        textureInfoMap.put("wood", new TextureInfo("terrain", 4, 15, 1, 1, 16, 16));


        textureInfoMap.put("soil", new TextureInfo("terrain", 2, 15, 1, 1, 16, 16));

        textureInfoMap.put("soil_side", new TextureInfo("terrain", 3, 15, 1, 1, 16, 16));

        textureInfoMap.put("stone", new TextureInfo("terrain", 1, 15, 1, 1, 16, 16));

        textureInfoMap.put("sand", new TextureInfo("terrain", 2, 14, 1, 1, 16, 16));
        textureInfoMap.put("grass_side", new TextureInfo("terrain", 3, 15, 1, 1, 16, 16));
        textureInfoMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));

        textureInfoMap.put("borken_0", new TextureInfo("terrain", 0, 0, 1, 1, 16, 16));
        textureInfoMap.put("borken_1", new TextureInfo("terrain", 1, 0, 1, 1, 16, 16));
        textureInfoMap.put("borken_2", new TextureInfo("terrain", 2, 0, 1, 1, 16, 16));
        textureInfoMap.put("borken_3", new TextureInfo("terrain", 3, 0, 1, 1, 16, 16));
        textureInfoMap.put("borken_4", new TextureInfo("terrain", 4, 0, 1, 1, 16, 16));
        textureInfoMap.put("borken_5", new TextureInfo("terrain", 5, 0, 1, 1, 16, 16));
        textureInfoMap.put("borken_6", new TextureInfo("terrain", 6, 0, 1, 1, 16, 16));
        textureInfoMap.put("borken_7", new TextureInfo("terrain", 7, 0, 1, 1, 16, 16));
        textureInfoMap.put("borken_8", new TextureInfo("terrain", 8, 0, 1, 1, 16, 16));
        textureInfoMap.put("borken_9", new TextureInfo("terrain", 9, 0, 1, 1, 16, 16));
        textureInfoMap.put("borken_10", new TextureInfo("terrain", 10, 0, 1, 1, 16, 16));

        textureInfoMap.put("humanBody-front", new TextureInfo("human", 20, 0, 8, 12, 64, 32));
        textureInfoMap.put("humanBody-back", new TextureInfo("human", 32, 0, 8, 12, 64, 32));
        textureInfoMap.put("humanBody-left", new TextureInfo("human", 16, 0, 4, 12, 64, 32));
        textureInfoMap.put("humanBody-right", new TextureInfo("human", 28, 0, 4, 12, 64, 32));
        textureInfoMap.put("humanBody-top", new TextureInfo("human", 20, 12, 8, 4, 64, 32));
        textureInfoMap.put("humanBody-bottom", new TextureInfo("human", 28, 12, 8, 4, 64, 32));

        textureInfoMap.put("humanHand-front", new TextureInfo("human", 40, 0, 4, 12, 64, 32));
        textureInfoMap.put("humanHand-back", new TextureInfo("human", 44, 0, 4, 12, 64, 32));
        textureInfoMap.put("humanHand-left", new TextureInfo("human", 48, 0, 4, 12, 64, 32));
        textureInfoMap.put("humanHand-right", new TextureInfo("human", 52, 0, 4, 12, 64, 32));
        textureInfoMap.put("humanHand-top", new TextureInfo("human", 44, 12, 4, 4, 64, 32));
        textureInfoMap.put("humanHand-bottom", new TextureInfo("human", 48, 12, 4, 4, 64, 32));

        textureInfoMap.put("humanLeg-front", new TextureInfo("human", 0, 0, 4, 12, 64, 32));
        textureInfoMap.put("humanLeg-back", new TextureInfo("human", 0, 0, 4, 12, 64, 32));
        textureInfoMap.put("humanLeg-left", new TextureInfo("human", 0, 0, 4, 12, 64, 32));
        textureInfoMap.put("humanLeg-right", new TextureInfo("human", 0, 0, 4, 12, 64, 32));
        textureInfoMap.put("humanLeg-top", new TextureInfo("human", 4, 12, 4, 4, 64, 32));
        textureInfoMap.put("humanLeg-bottom", new TextureInfo("human", 4, 12, 4, 4, 64, 32));

        textureInfoMap.put("humanHead-front", new TextureInfo("human", 8, 16, 8, 8, 64, 32));
        textureInfoMap.put("humanHead-back", new TextureInfo("human", 24,16, 8, 8, 64, 32));
        textureInfoMap.put("humanHead-left", new TextureInfo("human", 16, 16, 8, 8, 64, 32));
        textureInfoMap.put("humanHead-right", new TextureInfo("human", 0, 16, 8, 8, 64, 32));
        textureInfoMap.put("humanHead-top", new TextureInfo("human", 8, 24, 8, 8, 64, 32));
        textureInfoMap.put("humanHead-bottom", new TextureInfo("human", 16, 16, 8, 8, 64, 32));*/

//        textureInfoMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));
//        textureInfoMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));
//        textureInfoMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));
//        textureInfoMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));
//        textureInfoMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));

    }

/*

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
        ItemDefinition itemCfg = itemType2ItemDefinitionMap.get(itemType);
        if (itemCfg == null) {
            LogUtil.println("itemCfg 为null:" + itemType);
            //System.exit(0);
        }
        return itemCfg;
    }
*/

    public void putImage(String name, String textureImagePath) {
        File file = installPath.resolve(textureImagePath).toFile();
        if (!file.exists()) {
            LogUtil.println("not exists:" + file.getPath());
            System.exit(0);
        }



        GLImage textureImg;
        try {
            LWJGLRenderer renderer = new LWJGLRenderer();//调用lwjgl能力
           // Texture texture = renderer.loadTexture(new URL(installPath.toUri().toURL(), textureImagePath), "RGBA", "linear");
            Util.checkGLError();
            textureImg = GLApp.loadImage(installPath.resolve(textureImagePath).toUri());//
            //Image image=        ImageIO.read(new File(installPath.resolve(textureImagePath).toUri()));
            if (textureImg != null) {

                textureImg.textureHandle = GLApp.makeTexture(textureImg);
                Util.checkGLError();
                if(!name.equals("zhongwen")) {
                    LogUtil.println("生成字体的时候关闭mipmap能提高字体清晰度");
                    GLApp.makeTextureMipMap(textureImg.textureHandle, textureImg);
                }
                Util.checkGLError();
            }
           // textureMap.put(name, texture);
            if(StringUtil.isNotEmpty(textureIndex2NameMap.get(textureImg.textureHandle))){
               LogUtil.err("name exists"+name);
            }
//            LogUtil.println(name+":"+textureImg.textureHandle);
            textureIndex2NameMap.put(textureImg.textureHandle,name);

            imageMap.put(name, textureImg);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }

    }

    public static HashMap<Integer,String> textureIndex2NameMap =new HashMap<Integer,String>();


    public void preInit() {

    }

    public void loadImage() throws Exception {
        Util.checkGLError();
        try {
            List<File> fileList = FileUtil.readAllFileInFold(PathManager.getInstance().getHomePath().resolve("config/image").toString());
            for(File file : fileList){
                String json = FileUtil.readFile2Str(file);
                LogUtil.println("homepath:" + PathManager.getInstance().getHomePath());
                List<ImageInfo> imageInfoList = JSON.parseArray(json, ImageInfo.class);
                for (int i = 0; i < imageInfoList.size(); i++) {
                    ImageInfo imageInfo = imageInfoList.get(i);
                    this.putImage(imageInfo.getName(), imageInfo.getUri());

                }
            }


        } catch (Exception e) {
            throw new Exception("Failed to load config", e);
        }
    }

    public void loadTexture() throws Exception {

        try {
            List<File> fileList = FileUtil.readAllFileInFold(PathManager.getInstance().getHomePath().resolve("config/texture").toString());
            for(File file : fileList){
                String json = FileUtil.readFile2Str(file);

            List<TextureCfgBean> textureCfgBeanList = JSON.parseArray(json, TextureCfgBean.class);
            for (int i = 0; i < textureCfgBeanList.size(); i++) {
                TextureCfgBean textureCfgBean = textureCfgBeanList.get(i);
                String xywh = textureCfgBean.getXywh();
                String color = textureCfgBean.getColor();

                String ary[] = xywh.split(",");
                String splitx = textureCfgBean.getSplitx();
                String splity = textureCfgBean.getSplity();
                int x = Integer.valueOf(ary[0].trim());
                int y = Integer.valueOf(ary[1].trim());
                int w = Integer.valueOf(ary[2].trim());
                int h = Integer.valueOf(ary[3].trim());
                //int hegihtAry


               TextureInfo ti =  new TextureInfo(textureCfgBean.getImage(), x,
                        y,
                        w,
                        h, textureCfgBean.getName()
                );

                if (StringUtil.isNotEmpty(splitx)) {
                    //createGridImage(x,y,w,h,);
                    String[]  splitxAry = splitx.split(",");
                    int[] splitxIntAry = new int[splitxAry.length];
                    for(int j=0;j<splitxAry.length;j++){
                        splitxIntAry[j]=Integer.valueOf(splitxAry[j]);
                    }
                    ti.setSplitx(splitxIntAry);
                }
                if (StringUtil.isNotEmpty(splity)) {
                    //createGridImage(x,y,w,h,);
                    String[]  splityAry = splity.split(",");
                    int[] splityIntAry = new int[splityAry.length];
                    for(int j=0;j<splityAry.length;j++){
                        splityIntAry[j]=Integer.valueOf(splityAry[j]);
                    }
                    ti.setSplity(splityIntAry);

                }


                if(StringUtil.isNotEmpty(color)){
                    String[] colorAry = color.split(",");
                    GL_Vector color_v = new GL_Vector(Float.valueOf(colorAry[0])/255,
                            Float.valueOf(colorAry[1])/255,
                            Float.valueOf(colorAry[2])/255
                    );

                    ti.color=color_v;
                }
                try {
                    textureInfoMap.put(textureCfgBean.getName().replace(" ", "_"), ti);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to load config", e);
        }

    }
/*

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

                    String type = (String) map.get("type");
               */
/* if (type.equals("wear")) {*//*

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

                    String shapeName = (String) map.get("shape");


                    item.getItemModel().setIcon(this.getTextureInfo(icon));
                    Shape shape = this.getShape(shapeName);
                    item.setShape(shape);
                  */
/*  if(shapeName.equals("iron_helmet")){
                        System.out.println(shape==null);
                    }*//*



                    //item.init();
               */
/* } else if (type.equals("food")) {
                    item.setType(Constants.ICON_TYPE_FOOD);
                }*//*

                    this.putItemDefinition(name, item);


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to load config", e);
        }

    }
*/


    public void loadShape() throws Exception {

        try {
            List<File> fileList = FileUtil.readAllFileInFold(PathManager.getInstance().getHomePath().resolve("config/shape").toString());
            for(File file : fileList) {
                String json = FileUtil.readFile2Str(file);
                JSONArray list;
                try {   //LogUtil.println(json.substring(3616,3699));
                  list = //JSON.parseArray(json, HashMap.class);
                    JSON.parseArray(json);
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtil.err("load "+file.toPath()+" error ");
                    return ;
                }
                for (int i = 0; i < list.size(); i++) {

                    JSONObject map = (JSONObject) list.get(i);
                    BaseBlock block = EditEngine.parse(map);
                    if (block.getName().startsWith("wood_door")){
                        block.reComputePoints();
                        LogUtil.println("wood_door");
                    }
                    this.shapeMap.put(block.getName(), block);
                    logger.debug(MapUtil.getStringValue(map,"name"));
                    if(MapUtil.getStringValue(map,"name").equals("wood_door_down")||  MapUtil.getStringValue(map,"name").equals("wood_door_up")|| "wood_door_up".equals(block.getName())  ||  "wood_door_down".equals(block.getName())){
                       LogUtil.err("wood_door_up");
                    }
                   /* String blockType =(String) map.get("blocktype");
                    if("imageblock".equals(blockType)){
                        ImageBlock shape = ImageBlock.parse(map);
                        this.shapeMap.put(shape.getName(), shape);
                    }else if("colorblock".equals(blockType)){
                        ColorBlock shape = ColorBlock.parse(map);
                        this.shapeMap.put(shape.getName(), shape);
                    }else if("rotatecolorblock".equals(blockType)){
                        RotateColorBlock2 shape = RotateColorBlock2.parse(map);
                        this.shapeMap.put(shape.getName(), shape);
                    }else if("groupblock".equals(blockType)){
                        GroupBlock shape = GroupBlock.parse(map);
                        this.shapeMap.put(shape.getName(), shape);
                    }else if("animationblock".equals(blockType)){
                        AnimationBlock shape = AnimationBlock.parse(map);
                        this.shapeMap.put(shape.getName(), shape);
                    }else{
                        BoneBlock shape = BoneBlock.parse(map);
                        this.shapeMap.put(shape.getName(), shape);
                    }*/




                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to load config", e);
        }

    }


    public static HashMap<String,GLModelContainer> objsMap =new HashMap<>();

    public void loadObj() throws Exception {


        try {
            List<File> fileList = FileUtil.readAllFileInFold(PathManager.getInstance().getHomePath().resolve("config/obj").toString());
            for(File file : fileList) {
                if(!file.getName().endsWith(".obj"))
                    continue;
                String json = FileUtil.readFile2Str(file);
                JSONArray list;
                try {   //LogUtil.println(json.substring(3616,3699));
                    list = //JSON.parseArray(json, HashMap.class);
                            JSON.parseArray(json);
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtil.err("load "+file.toPath()+" error ");
                    return ;
                }
                Path objDir = PathManager.getInstance().getHomePath().resolve("config/obj");
                for (int i = 0; i < list.size(); i++) {

                    JSONObject map = (JSONObject) list.get(i);
                    String objFileName =objDir.resolve( MapUtil.getStringValue(map,"file")).toString();
                    String name = MapUtil.getStringValue(map,"name");

                    GLModel glModel =new GLModel(objFileName );
                    //GLModelContainer gLModelContainer =new GLModelContainer(objDir.resolve(objFileName).toString(),5,5);

                    objMap.put(name,glModel);


                    logger.debug("load obj:"+file);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to load config", e);
        }



    }



    public static GLModel getObj(String name){//
        GLModel glModel =  objMap.get(name);
        return glModel;
    }


    public static TextureInfo getTextureInfo(String name) {
        TextureInfo textureInfo = textureInfoMap.get(name);
        if (textureInfo == null) {
            assert textureInfo != null;
            LogUtil.err("textureinfo 为空" + name);
           // System.exit(0);
        }
        return textureInfo;
    }

    public static GLImage getImage(String name) {
        return imageMap.get(name);
    }

    public static Texture getTexture(String name) {
        return textureMap.get(name);
    }

    public static BaseBlock getShape(String name) {
        return shapeMap.get(name);
    }
    public static void putShape(BaseBlock block ){
        if(block.getId()==13){
            LogUtil.println("hello]");
        }
        if(block.getName().startsWith("wood_door")){
            LogUtil.println("hello]");
        }
        shapeMap.put(block.getName(),block);
        idShapeMap.put(block.getId(),block);
    }
    public static void putIdShapeMap(Integer id,BaseBlock block){
        idShapeMap.put(id,block);
    }
//    public static BoneBlock createDoorShape(){
//        float minX=0;
//        float minY=0;
//        float minZ=0;
//        int width=1;
//        int height=2;
//        float thick =0.125f;
//        int tex_minx = 16;
//        //16,160,16,16
//        float tex_miny=144;
//        float tex_width=16;
//        float tex_height=32;
//        float imageHeight=256;
//        float imageWidth=256;
//        TextureInfo frontTi =new TextureInfo();
//        frontTi.minX=tex_minx/imageWidth;
//        frontTi.minY = tex_miny / imageHeight;
//
//        frontTi.maxX=(tex_minx+tex_width)/imageWidth;
//        frontTi.maxY = (tex_miny+tex_height) / imageHeight;
//        TextureInfo woodTi =new TextureInfo();
//        woodTi.minX=tex_minx/imageWidth;
//        woodTi.minY = tex_miny / imageHeight;
//
//        woodTi.maxX=(tex_minx+5)/imageWidth;
//        woodTi.maxY = (tex_miny+5) / imageHeight;
//
//        //StringBuffer sb =new StringBuffer();
//        /*sb.append("\"frontFace\": {\n")
//                .append("\"vertices\": [[0, 0, "+thick+"], ["+width+", 0, "+thick+"], ["+width+", "+height+", "+thick+"], [0, "+height+", "+thick+"]],")
//                .append("\"normals\": [["+ BoxModel.FRONT_DIR.x+","+BoxModel.FRONT_DIR.y+","+BoxModel.FRONT_DIR.z+"], ["+ BoxModel.FRONT_DIR.x+","+BoxModel.FRONT_DIR.y+","+BoxModel.FRONT_DIR.z+"], ["+ BoxModel.FRONT_DIR.x+","+BoxModel.FRONT_DIR.y+","+BoxModel.FRONT_DIR.z+"], ["+ BoxModel.FRONT_DIR.x+","+BoxModel.FRONT_DIR.y+","+BoxModel.FRONT_DIR.z+"]],")
//                .append(" \"texcoords\": [["+tex_minx/imageWidth+","+tex_miny/imageHeight+"], ["+(tex_minx+tex_width)/imageWidth+", "+tex_miny/imageHeight+"], ["+(tex_minx+tex_width)/imageWidth+", "+(tex_miny+tex_height)/imageHeight+"], ["+tex_minx/imageWidth+", "+(tex_miny+tex_height)/imageHeight+"]],")
//                .append("\"faces\": [[0, 1, 2, 3] ],")
//                .append("  \"fullSide\": false")
//                .append("},");*/
//        BoneBlock shape =new BoneBlock();
//
//        ShapeFace frontFace =new ShapeFace();
//
//
//
//        //float[][] vertices = BoxModel.frontVertices;
//        frontFace.setVertices(BoxModel.getFrontVertices(minX, minY, minZ, width, height, thick));
//        frontFace.setFaces(new int[][]{{0, 1, 2, 3}});
//        //TextureInfo ti =TextureManager.getTextureInfo("wood_door_part1");
//        TextureInfo ti =frontTi;
//        frontFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});
//
//        GL_Vector nowDir = BoxModel.FRONT_DIR;
//        frontFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});
//
//        shape.setFrontFace(frontFace);
//
//        ///////////
//        ShapeFace backFace =new ShapeFace();
//
//        backFace.setVertices(BoxModel.getBackVertices(minX, minY, minZ, width, height, thick));
//        backFace.setFaces(new int[][]{{0, 1, 2, 3}});
//        // ti =TextureManager.getTextureInfo("wood_door_part1");
//        backFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});
//
//        nowDir = BoxModel.BACK_DIR;
//        backFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});
//
//        shape.setBackFace(backFace);
//
//
//
//        ////////////
//        ti =woodTi;
//        ShapeFace topFace =new ShapeFace();
//
//        topFace.setVertices(BoxModel.getTopVertices(minX, minY, minZ, width, height, thick));
//        topFace.setFaces(new int[][]{{0, 1, 2, 3}});
//        // ti =TextureManager.getTextureInfo("wood_door_part1");
//        topFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});
//
//        nowDir = BoxModel.TOP_DIR;
//        topFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});
//
//        shape.setTopFace(topFace);
//
//
//        ShapeFace bottomFace =new ShapeFace();
//
//        bottomFace.setVertices(BoxModel.getBottomVertices(minX, minY, minZ, width, height, thick));
//        bottomFace.setFaces(new int[][]{{0, 1, 2, 3}});
//        // ti =TextureManager.getTextureInfo("wood_door_part1");
//        bottomFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});
//
//        nowDir = BoxModel.DOWN_DIR;
//        bottomFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});
//
//        shape.setBottomFace(bottomFace);
//
//
//        ShapeFace leftFace =new ShapeFace();
//
//        leftFace.setVertices(BoxModel.getLeftVertices(minX, minY, minZ, width, height, thick));
//        leftFace.setFaces(new int[][]{{0, 1, 2, 3}});
//        // ti =TextureManager.getTextureInfo("wood_door_part1");
//        leftFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});
//
//        nowDir = BoxModel.LEFT_DIR;
//        leftFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});
//
//        shape.setLeftFace(leftFace);
//
//
//        ShapeFace rightFace =new ShapeFace();
//
//        rightFace.setVertices(BoxModel.getRightVertices(minX, minY, minZ, width, height, thick));
//        rightFace.setFaces(new int[][]{{0, 1, 2, 3}});
//        // ti =TextureManager.getTextureInfo("wood_door_part1");
//        rightFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});
//
//        nowDir = BoxModel.RIGHT_DIR;
//        rightFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});
//
//        shape.setRightFace(rightFace);
//
//
//        System.out.println("frontFace:"+JSON.toJSONString(shape.getFrontFace())+",");
//        System.out.println("backFace:"+JSON.toJSONString(shape.getBackFace())+",");
//        System.out.println("topFace:"+JSON.toJSONString(shape.getTopFace())+",");
//        System.out.println("bottomFace:"+JSON.toJSONString(shape.getBottomFace())+",");
//        System.out.println("leftFace:"+JSON.toJSONString(shape.getLeftFace())+",");
//        System.out.println("rightFace:"+JSON.toJSONString(shape.getRightFace())+",");
//        return shape;
//    }


    public void print(Component component,BoneBlock shape){







        /////////// back







    }

//    public static BoneBlock createBoxShape(){
//
//
//        Component rootComponent =new Component();
//
//        BoneBlock bodyShape = (BoneBlock)TextureManager.getShape("box_open_body");//加载箱体shape
//        rootComponent =
//                new Component(bodyShape);
//
//        rootComponent.addChild((BoneBlock)TextureManager.getShape("box_open_head"));//加载箱子的盖子
//
//
//        List<float[]> vertices = new ArrayList<>();
//
//        float[][] verticesAry= new float[vertices.size()][3];
//        List<float[] > texcoords = new ArrayList<>();
//
//        List<int[]> faces =new ArrayList<>();
//        List<float[]> normals= new ArrayList<>();
//        HashMap map =new HashMap();
//        rootComponent.getVertices(0,GL_Matrix.translateMatrix(0,0,0),vertices,texcoords,faces,normals);//将绘制的结果放在vertices里
//        bodyShape.setFrontFace(new ShapeFace());
//        bodyShape.getFrontFace().setVertices(vertices.toArray(verticesAry));
//        bodyShape.getFrontFace().setNormals(normals.toArray(new float[normals.size()][3]));
//
//        bodyShape.getFrontFace().setTexcoords(texcoords.toArray(new float[texcoords.size()][3]));
//
//        bodyShape.getFrontFace().setFaces(faces.toArray(new int[faces.size()][6]));
//
//        System.out.println("frontFace:" + JSON.toJSONString(bodyShape.getFrontFace()) + ",");
//
//
//        return bodyShape;
//    }
//    public static BoneBlock createBoxCloseShape(){
//
//
//        Component rootComponent =new Component();
//
//        BoneBlock bodyShape = (BoneBlock)TextureManager.getShape("box_close");//加载箱体shape
//        rootComponent =
//                new Component(bodyShape);
//
//
//        List<float[]> vertices = new ArrayList<>();
//
//        float[][] verticesAry= new float[vertices.size()][3];
//        List<float[] > texcoords = new ArrayList<>();
//
//        List<int[]> faces =new ArrayList<>();
//        List<float[]> normals= new ArrayList<>();
//        HashMap map =new HashMap();
//        rootComponent.getVertices(0,GL_Matrix.translateMatrix(0,0,0),vertices,texcoords,faces,normals);//将绘制的结果放在vertices里
//        bodyShape.setFrontFace(new ShapeFace());
//        bodyShape.getFrontFace().setVertices(vertices.toArray(verticesAry));
//        bodyShape.getFrontFace().setNormals(normals.toArray(new float[normals.size()][3]));
//
//        bodyShape.getFrontFace().setTexcoords(texcoords.toArray(new float[texcoords.size()][3]));
//
//        bodyShape.getFrontFace().setFaces(faces.toArray(new int[faces.size()][6]));
//
//        System.out.println("frontFace:" + JSON.toJSONString(bodyShape.getFrontFace()) + ",");
//
//
//        return bodyShape;
//    }
    public static void main(String args[]){

      //  TextureManager.createBoxCloseShape();
    }


}
