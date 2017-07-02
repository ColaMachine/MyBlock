package cola.machine.game.myblocks.manager;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import core.log.LogUtil;
import cola.machine.game.myblocks.model.textture.*;
import com.alibaba.fastjson.JSON;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;
import de.matthiasmann.twl.renderer.Texture;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import glapp.GLApp;
import glapp.GLImage;

import java.io.File;
import java.nio.FloatBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import glmodel.GL_Vector;
import org.lwjgl.opengl.Util;
import com.dozenx.util.MapUtil;


/**
 * Created by luying on 14-8-28.
 */
public class TextureManager {
    Path installPath;
    public static HashMap<String, GLImage> imageMap = new HashMap<String, GLImage>();
    public static HashMap<String ,List<Shape>> shapeGroups =new HashMap<>();
    public static HashMap<String, TextureInfo> textureInfoMap = new HashMap<String, TextureInfo>();
    public static HashMap<String, Texture> textureMap = new HashMap<String, Texture>();
/*    public static HashMap<String, ItemDefinition> itemDefinitionMap = new HashMap<String, ItemDefinition>();
    public static HashMap<ItemType, ItemDefinition> itemType2ItemDefinitionMap = new HashMap<ItemType, ItemDefinition>();*/
    public static HashMap<String, Shape> shapeMap = new HashMap<String, Shape>();

    public HashMap<String, ImageInfo> ImageInfoMap = new HashMap<>();

    public TextureManager() {

        CoreRegistry.put(TextureManager.class,this);
        installPath = PathManager.getInstance().getInstallPath();
        try {
            Util.checkGLError();
            loadImage();
            loadTexture();
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
                List<HashMap> list;
                try {   //LogUtil.println(json.substring(3616,3699));
                  list = JSON.parseArray(json, HashMap.class);

                }catch (Exception e){
                    e.printStackTrace();
                    LogUtil.err("load "+file.toPath()+" error ");
                    return ;
                }
                for (int i = 0; i < list.size(); i++) {

                    HashMap map = list.get(i);
                    Shape shape = new Shape();
                    String name = (String) map.get("name");
                    if(name.equals("mantle")){
                        LogUtil.println("mantle");
                    }
                    shape.setName(name);

                    int shapeType =
                    MapUtil.getIntValue(map,"shapeType");
                    if(shapeType!=2 && shapeType!=3){
                        LogUtil.err("shaperType is error ");
                    }
                    String group   = MapUtil.getStringValue(map,"group");
                    if(group!=null ){
                        shape.setGroup(group);
                        List shapeGroupList = shapeGroups.get(group);
                        if(shapeGroupList == null ){
                            shapeGroupList =new ArrayList();
                            shapeGroups .put(group , shapeGroupList);
                            shapeGroupList.add(shape);
                        }else{
                            shapeGroupList.add(shape);
                        }
                    }


                    Object frontObj = map.get("frontFace");
                    if(frontObj instanceof  com.alibaba.fastjson.JSONObject){
                        ShapeFace shapeFace = getShapeFace(frontObj);
                        shape.setFrontFace(shapeFace);
                    }
                    Object bottomObj = map.get("bottomFace");
                    if(bottomObj instanceof  com.alibaba.fastjson.JSONObject){
                        ShapeFace shapeFace = getShapeFace(bottomObj);
                        shape.setBottomFace(shapeFace);
                    }
                    Object topObj = map.get("topFace");
                    if(topObj instanceof  com.alibaba.fastjson.JSONObject){
                        ShapeFace shapeFace = getShapeFace(topObj);
                        shape.setTopFace(shapeFace);
                    }
                    Object backObj = map.get("backFace");
                    if(backObj instanceof  com.alibaba.fastjson.JSONObject){
                        ShapeFace shapeFace = getShapeFace(backObj);
                        shape.setBackFace(shapeFace);
                    }
                    Object leftObj = map.get("leftFace");
                    if(frontObj instanceof  com.alibaba.fastjson.JSONObject){
                        ShapeFace shapeFace = getShapeFace(leftObj);
                        shape.setLeftFace(shapeFace);
                    }
                    Object rightObj = map.get("rightFace");
                    if(rightObj instanceof  com.alibaba.fastjson.JSONObject){
                        ShapeFace shapeFace = getShapeFace(rightObj);
                        shape.setRightFace(shapeFace);
                    }
                   /* if(frontObj instanceof  com.alibaba.fastjson.JSONObject){
                        continue;
                    }*/


                    String front = (String) map.get("front");
                    String back = (String) map.get("back");
                    String left = (String) map.get("left");
                    String right = (String) map.get("right");
                    String top = (String) map.get("top");

                    String bottom = (String) map.get("bottom");
                    String allSide =  MapUtil.getStringValue(map,"allSide");
                    if(StringUtil.isNotEmpty(allSide)){
                         front = allSide;
                         back = allSide;
                         left = allSide;
                         right = allSide;
                         top = allSide;
                         bottom = allSide;
                    }
                    String side =  MapUtil.getStringValue(map,"side");
                    if(StringUtil.isNotEmpty(side)){
                        front = side;
                        back = side;
                        left = side;
                        right = side;

                    }
                    String topBottom =  MapUtil.getStringValue(map,"topBottom");
                    if(StringUtil.isNotEmpty(topBottom)){
                        top = topBottom;
                        bottom = topBottom;

                    }


                    shape.setShapeType(shapeType);

                    String parent = MapUtil.getStringValue(map, "parent");
                    shape.setParent(parent);
                    if (!"root".equals(parent)&& parent!=null) {
                        String p_posi_xStr = MapUtil.getStringValue(map, "p_posi_x");
                        String p_posi_yStr = MapUtil.getStringValue(map, "p_posi_y");
                        String p_posi_zStr = MapUtil.getStringValue(map, "p_posi_z");

                        Shape parentShape = this.getShape(parent);
                        if (shape == null) {
                            LogUtil.err("can 't find shape" + parent);
                        }
                        // String c_posi_xStr =  MapUtil.getStringValue(map,"c_posi_x");
                        try {//有shape 不一定有parentShape
                            /*if(shape==null || parentShape ==null){
                                LogUtil.err(" is null");


                            }*/

                           // Object widthObj = map.get("width");

                            float width = Shape.parsePosition(MapUtil.getStringValue(map, "width"), 0f, 0f, 0f, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick());


                            float height = Shape.parsePosition(MapUtil.getStringValue(map, "height"), 0f, 0f, 0f, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick());



                            float thick = Shape.parsePosition(MapUtil.getStringValue(map, "thick"), 0f, 0f, 0f, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick());

                            shape.setWidth(width);
                            shape.setHeight(height);
                            shape.setThick(thick);

                            if(/*shape!=null &&*/ parentShape!=null && StringUtil.isNotEmpty(MapUtil.getStringValue(map, "c_posi_x"))) {

                                shape.setC_posi_x(Shape.parsePosition(MapUtil.getStringValue(map, "c_posi_x"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));

                                //String c_posi_yStr =  MapUtil.getStringValue(map,"c_posi_y");
                                shape.setC_posi_y(Shape.parsePosition(MapUtil.getStringValue(map, "c_posi_y"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));

                                shape.setC_posi_z(Shape.parsePosition(MapUtil.getStringValue(map, "c_posi_z"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));

                                shape.setP_posi_x(Shape.parsePosition(MapUtil.getStringValue(map, "p_posi_x"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));
                                shape.setP_posi_y(Shape.parsePosition(MapUtil.getStringValue(map, "p_posi_y"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));
                                shape.setP_posi_z(Shape.parsePosition(MapUtil.getStringValue(map, "p_posi_z"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));

                            }
                        }catch(Exception e){
                            LogUtil.err(e);
                        }
                    }else{
                        float width = MapUtil.getFloatValue(map, "width");


                        float height = MapUtil.getFloatValue(map, "height");



                        float thick = MapUtil.getFloatValue(map, "thick");

                        shape.setWidth(width);
                        shape.setHeight(height);
                        shape.setThick(thick);


                        shape.setC_posi_x(Shape.parsePosition(MapUtil.getStringValue(map, "c_posi_x"), width, height, thick, 0f, 0f,0f));

                        //String c_posi_yStr =  MapUtil.getStringValue(map,"c_posi_y");
                        shape.setC_posi_y(Shape.parsePosition(MapUtil.getStringValue(map, "c_posi_y"), width, height, thick, 0f, 0f,0f));

                        shape.setC_posi_z(Shape.parsePosition(MapUtil.getStringValue(map, "c_posi_z"), width, height, thick,  0f, 0f,0f));

                        shape.setP_posi_x(Shape.parsePosition(MapUtil.getStringValue(map, "p_posi_x"), width, height, thick,  0f, 0f,0f));
                        shape.setP_posi_y(Shape.parsePosition(MapUtil.getStringValue(map, "p_posi_y"), width, height, thick,  0f, 0f,0f));
                        shape.setP_posi_z(Shape.parsePosition(MapUtil.getStringValue(map, "p_posi_z"), width, height, thick,  0f, 0f,0f));

                    }
                    if (shapeType == 3) {
                        if (!isEmpty(front)) {
                            shape.setFront(this.getTextureInfo(front));
                        }
                        if (!isEmpty(back)) {
                            shape.setBack(this.getTextureInfo(back));
                        }
                        if (!isEmpty(left)) {
                            shape.setLeft(this.getTextureInfo(left));
                        }
                        if (!isEmpty(right)) {
                            shape.setRight(this.getTextureInfo(right));
                        }
                        if (!isEmpty(top)) {
                            shape.setTop(this.getTextureInfo(top));
                        }
                        if (!isEmpty(bottom)) {
                            shape.setBottom(this.getTextureInfo(bottom));
                        }
                    }

                    this.shapeMap.put(name, shape);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to load config", e);
        }

    }

    public boolean isEmpty(String name) {
        if (name == null || name.equals("")) {
            return true;
        } else {
            return false;
        }
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
    public ShapeFace getShapeFace(Object frontObj ){
        ShapeFace shapeFace =new ShapeFace();
        JSONObject jsonObj = (JSONObject)frontObj;
        //  HashMap keyValue = (HashMap)jsonObj.get(0);
        JSONArray vertices =  jsonObj.getJSONArray("vertices");
        float[][] verticesAry = new float[vertices.size()][3];
        for(int j=0;j<vertices.size();j++){
            for(int k=0;k<3;k++){
                verticesAry[j][k]= vertices.getJSONArray(j).getFloat(k);
            }
        }

        shapeFace.setVertices(verticesAry);
        JSONArray tecoordsJAry =  jsonObj.getJSONArray("texcoords");
        float[][] tecoords = new float[tecoordsJAry.size()][2];
        for(int j=0;j<tecoordsJAry.size();j++){
            for(int k=0;k<2;k++){
                tecoords[j][k]= tecoordsJAry.getJSONArray(j).getFloat(k);
            }
        }
        shapeFace.setTexcoords(tecoords);

        JSONArray normalJAry =  jsonObj.getJSONArray("normals");
        float[][] normals = new float[normalJAry.size()][3];
        for(int j=0;j<normalJAry.size();j++){
            for(int k=0;k<2;k++){
                normals[j][k]= normalJAry.getJSONArray(j).getFloat(k);
            }
        }
        shapeFace.setNormals(normals);

        JSONArray facesAry =  jsonObj.getJSONArray("faces");
        int[][] faces = new int[1][facesAry.getJSONArray(0).size()];
        for(int j=0;j<facesAry.getJSONArray(0).size();j++){
            faces[0][j]= facesAry.getJSONArray(0).getInteger(j);
        }
        shapeFace.setFaces(faces);
        return shapeFace;
    }
    public static GLImage getImage(String name) {
        return imageMap.get(name);
    }

    public static Texture getTexture(String name) {
        return textureMap.get(name);
    }

    public static Shape getShape(String name) {
        return shapeMap.get(name);
    }

    public static Shape createDoorShape(){
        float minX=0;
        float minY=0;
        float minZ=0;
        int width=1;
        int height=2;
        float thick =0.125f;
        int tex_minx = 16;
        //16,160,16,16
        float tex_miny=144;
        float tex_width=16;
        float tex_height=32;
        float imageHeight=256;
        float imageWidth=256;
        TextureInfo frontTi =new TextureInfo();
        frontTi.minX=tex_minx/imageWidth;
        frontTi.minY = tex_miny / imageHeight;

        frontTi.maxX=(tex_minx+tex_width)/imageWidth;
        frontTi.maxY = (tex_miny+tex_height) / imageHeight;
        TextureInfo woodTi =new TextureInfo();
        woodTi.minX=tex_minx/imageWidth;
        woodTi.minY = tex_miny / imageHeight;

        woodTi.maxX=(tex_minx+5)/imageWidth;
        woodTi.maxY = (tex_miny+5) / imageHeight;

        //StringBuffer sb =new StringBuffer();
        /*sb.append("\"frontFace\": {\n")
                .append("\"vertices\": [[0, 0, "+thick+"], ["+width+", 0, "+thick+"], ["+width+", "+height+", "+thick+"], [0, "+height+", "+thick+"]],")
                .append("\"normals\": [["+ BoxModel.FRONT_DIR.x+","+BoxModel.FRONT_DIR.y+","+BoxModel.FRONT_DIR.z+"], ["+ BoxModel.FRONT_DIR.x+","+BoxModel.FRONT_DIR.y+","+BoxModel.FRONT_DIR.z+"], ["+ BoxModel.FRONT_DIR.x+","+BoxModel.FRONT_DIR.y+","+BoxModel.FRONT_DIR.z+"], ["+ BoxModel.FRONT_DIR.x+","+BoxModel.FRONT_DIR.y+","+BoxModel.FRONT_DIR.z+"]],")
                .append(" \"texcoords\": [["+tex_minx/imageWidth+","+tex_miny/imageHeight+"], ["+(tex_minx+tex_width)/imageWidth+", "+tex_miny/imageHeight+"], ["+(tex_minx+tex_width)/imageWidth+", "+(tex_miny+tex_height)/imageHeight+"], ["+tex_minx/imageWidth+", "+(tex_miny+tex_height)/imageHeight+"]],")
                .append("\"faces\": [[0, 1, 2, 3] ],")
                .append("  \"fullSide\": false")
                .append("},");*/
        Shape shape =new Shape();

        ShapeFace frontFace =new ShapeFace();



        //float[][] vertices = BoxModel.frontVertices;
        frontFace.setVertices(BoxModel.getFrontVertices(minX, minY, minZ, width, height, thick));
        frontFace.setFaces(new int[][]{{0, 1, 2, 3}});
        //TextureInfo ti =TextureManager.getTextureInfo("wood_door_part1");
        TextureInfo ti =frontTi;
        frontFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});

        GL_Vector nowDir = BoxModel.FRONT_DIR;
        frontFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});

        shape.setFrontFace(frontFace);

        ///////////
        ShapeFace backFace =new ShapeFace();

        backFace.setVertices(BoxModel.getBackVertices(minX, minY, minZ, width, height, thick));
        backFace.setFaces(new int[][]{{0, 1, 2, 3}});
        // ti =TextureManager.getTextureInfo("wood_door_part1");
        backFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});

        nowDir = BoxModel.BACK_DIR;
        backFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});

        shape.setBackFace(backFace);



        ////////////
        ti =woodTi;
        ShapeFace topFace =new ShapeFace();

        topFace.setVertices(BoxModel.getTopVertices(minX, minY, minZ, width, height, thick));
        topFace.setFaces(new int[][]{{0, 1, 2, 3}});
        // ti =TextureManager.getTextureInfo("wood_door_part1");
        topFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});

        nowDir = BoxModel.TOP_DIR;
        topFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});

        shape.setTopFace(topFace);


        ShapeFace bottomFace =new ShapeFace();

        bottomFace.setVertices(BoxModel.getBottomVertices(minX, minY, minZ, width, height, thick));
        bottomFace.setFaces(new int[][]{{0, 1, 2, 3}});
        // ti =TextureManager.getTextureInfo("wood_door_part1");
        bottomFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});

        nowDir = BoxModel.DOWN_DIR;
        bottomFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});

        shape.setBottomFace(bottomFace);


        ShapeFace leftFace =new ShapeFace();

        leftFace.setVertices(BoxModel.getLeftVertices(minX, minY, minZ, width, height, thick));
        leftFace.setFaces(new int[][]{{0, 1, 2, 3}});
        // ti =TextureManager.getTextureInfo("wood_door_part1");
        leftFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});

        nowDir = BoxModel.LEFT_DIR;
        leftFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});

        shape.setLeftFace(leftFace);


        ShapeFace rightFace =new ShapeFace();

        rightFace.setVertices(BoxModel.getRightVertices(minX, minY, minZ, width, height, thick));
        rightFace.setFaces(new int[][]{{0, 1, 2, 3}});
        // ti =TextureManager.getTextureInfo("wood_door_part1");
        rightFace.setTexcoords(new float[][]{{ti.minX,ti.minY},{ti.maxX,ti.minY},{ti.maxX,ti.maxY},{ti.minX,ti.maxY}});

        nowDir = BoxModel.RIGHT_DIR;
        rightFace.setNormals(new float[][]{{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z},{nowDir.x,nowDir.y,nowDir.z}});

        shape.setRightFace(rightFace);


        System.out.println("frontFace:"+JSON.toJSONString(shape.getFrontFace())+",");
        System.out.println("backFace:"+JSON.toJSONString(shape.getBackFace())+",");
        System.out.println("topFace:"+JSON.toJSONString(shape.getTopFace())+",");
        System.out.println("bottomFace:"+JSON.toJSONString(shape.getBottomFace())+",");
        System.out.println("leftFace:"+JSON.toJSONString(shape.getLeftFace())+",");
        System.out.println("rightFace:"+JSON.toJSONString(shape.getRightFace())+",");
        return shape;
    }
    public static void main(String args[]){

        TextureManager.createDoorShape();
    }
}
