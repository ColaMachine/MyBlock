package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.*;
import cola.machine.game.myblocks.model.textture.BoneBlock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.command.ItemMainType;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.edit.EditEngine;
import com.dozenx.game.engine.edit.view.AnimationBlock;
import com.dozenx.game.engine.edit.view.GroupBlock;
import com.dozenx.game.engine.item.action.ItemFactory;
import com.dozenx.util.BinaryUtil;
import com.dozenx.util.MapUtil;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.Map;
import java.util.HashMap;
/**
 * item 模板存放在
 */
public class ItemDefinition implements Cloneable{
   /* Block[] blocks;
    public static HashMap<String,Block[]> map =new HashMap<>();
    TextureInfo icon;*/
    public int itemTypeId;
    public ItemType itemType;

    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public boolean live=false;
   // private Integer itemType;//物品的具体类型 详见ItemType
    //public ItemType itemTypeOri;
    public  ItemModel itemModel = new ItemModel();//模型描述
    public String engine;
    public int stackNum=20;//可堆叠数量
    public String script ;

    public boolean isFar;
    public int shootBallId;
    public ItemDefinition shootBallItem;
    public boolean isLive;
    public ItemTypeProperties itemTypeProperties ;//block food wear
    String name;//英文名称

    ItemMainType type;//大类
    String icon ;
    //TODO del
    String remark;//中文描述
    /**武器属性**/
    int spirit;
    int agile;
    int intelli;
    int strenth;

    int position;//如果是装备的画就会起作用 head body leg foot hand arm
    BaseBlock shape;//含着一些box 绘制的属性 这个东西不是应该在 itemModel里吗
    //ShapeType shapeType;//描述 是饼状 盒形 icon状

/*
    public TextureInfo getIcon() {
        return icon;
    }

    public void setIcon(TextureInfo icon) {
        this.icon = icon;
    }*/



/*

    public void init1(){
        List<Block> list = new ArrayList<Block>();
        try {

          int height=  this.icon.img
                  .h;
            int width=  this.icon.img
                    .w;
            int minX = (int)(height*this.icon.minX);

            int maxX = (int)(height*this.icon.maxX);
            int minY=height-(int)(height*this.icon.maxY);
            int maxY= height-(int)(height*this.icon.minY);
           *//* int minY=height-(int)(height*this.icon.maxY);
            int maxY= height-(int)(height*this.icon.minY);*//*
            int[] pix = this.icon.img.getPixelInts();
            int length = pix.length;
            for (int i = minX; i < maxX; i++)
                for (int j = minY; j < maxY; j++) {
                    int value = pix[i*height+width];
                    int a = value>>24&255;

                    int r = value<<8>>24;
                    int g = value<<16>>24;
                    int b = value&255;
                    if(value!=-14606047&& value!=0){
                        Color color = new Color(r,g,b);
                        Block soil = new ColorBlock(i-minX, 0, j-minY, color);
                        list.add(soil);
                    }


                }
            blocks = list.toArray(new Block[list.size()]);

        } catch (Exception e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        }
    }*//*
    public void init() {
        if(blocks!=null){
            return;
        }
        this.blocks =  map.get(this.icon.name);
        if(blocks!=null){
            return;
        }
        map.put(this.icon.name,blocks);
        int height=  this.icon.img
                .h;
        int width=  this.icon.img
                .w;
        int minX = (int)(height*this.icon.minX);

        int maxX = (int)(height*this.icon.maxX);

        int minY=height-(int)(height*this.icon.maxY);
        int maxY= height-(int)(height*this.icon.minY);
        List<Block> list = new ArrayList<Block>();
        if(this.name.equals("wood_sword")){
            LogUtil.println("hello");
        }
        HashMap<Integer ,Block> blockMap =new HashMap<Integer,Block>();
        try {
            Color[][] colors = ImageUtil.getGrayPicture(this.getIcon().img.tmpi, minX,
                    minY, maxX, maxY);
            int _width = maxX-minX;
            int _height=maxY-minY;
            for (int i = 0; i < _width; i++)
                for (int j = 0; j < _height; j++) {
                    Color color = colors[i][j];
                    if (color != null)
                    {

                        Block soil = new ColorBlock(0, i, _height-j, color);
                        list.add(soil);

                        blockMap.put(i * _height + _height-j, soil);

                    }
                }

            for (int i = 0; i < _width; i++)
                for (int j = 0; j < _height; j++) {
                       Block block = blockMap.get(i * _height + j);
                    if(block!=null){
                        if(j!=_height && blockMap.get(i * _height + j+1)!=null){
                            block.setZh(false);
                        }
                        if(j!=0&& blockMap.get(i * _height + j-1)!=null){
                            block.setZl(false);
                        }
                        if(i!=_width && blockMap.get((i +1)* _height + j)!=null){
                            block.setYh(false);
                        }
                        if(i!=0 && blockMap.get((i -1)* _height + j)!=null){
                            block.setYl(false);
                        }
                       // block.setXh(false);
                       // block.setXl(false);
                    }

                }
            blocks = list.toArray(new Block[list.size()]);
            map.put(this.icon.imageName,blocks);
        } catch (FileNotFoundException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        }
    }*/
/*
    public void init3 () {
        int height=  this.icon.img
                .h;
        int width=  this.icon.img
                .w;
        int minX = (int)(height*this.icon.minX);

        int maxX = (int)(height*this.icon.maxX);

        int minY=height-(int)(height*this.icon.maxY);
        int maxY= height-(int)(height*this.icon.minY);
        List<Block> list = new ArrayList<Block>();
        if(this.name.equals("wood_sword")){
            LogUtil.println("hello");
        }
        try {
            Color[][] colors = ImageUtil.getGrayPicture(this.getIcon().img.uri.getPath().toString(), minX,
                    minY, maxX, maxY);
            for (int i = 0; i < 16; i++)
                for (int j = 0; j < 16; j++) {
                    Color color = colors[i][j];
                    if (color != null)
                    {

                        Block soil = new ColorBlock(i, 0, j, color);
                        list.add(soil);
                    }
                }
            blocks = list.toArray(new Block[list.size()]);

        } catch (FileNotFoundException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    public static void main(String args[]){
        int value = 111111111;
        //BinaryUtil.toString(1);
        //BinaryUtil.toString(1<<1);
        //BinaryUtil.toString(1<<30);
        //BinaryUtil.toString(1<<30>>30);

        BinaryUtil.toString(value);
        int a = value>>24;
        BinaryUtil.toString(a);
        int r = (value<<8)>>24&255;
        System.out.println(r);
        BinaryUtil.toString(r);
        int g = (value<<16)>>24&255;
        System.out.println(g);
        BinaryUtil.toString(g);
        int b = (value<<24)>>24&255;
        System.out.println(b);
        BinaryUtil.toString(b);

    }

    /*public void renderShader(ShaderConfig config , GL_Matrix rotateMatrix){
            //rotateMatrix= GL_Matrix.rotateMatrix( 0, 0, rotateZ);
        *//*rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( -90*3.14f/180, 0, 0));
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( 0, 0, -45*3.14f/180));
*//*
        if(blocks==null){
            this.init();
        }

        for (Block block : blocks) {
          //  GL11.glColor3f(block.rf(), block.bf() , block.gf());
            block.renderShader(config,rotateMatrix);
        }
    }
    public void render(){


            // 先缩小
            //GL11.glRotated(180, 0, 1, 0);

            //GL11.glTranslatef(x-2,y, z);

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);//GL11.glTranslatef(2,0f,-4f);
            // GL11.glRotated(135, 1,0 , 0);
            //  GL11.glRotated(90, 0, 0, 1);

            GL11.glScalef(0.1f, 0.1f, 0.1f);
            //GL11.glTranslatef(-3.5f,-0.5f, -11f);
            for (Block block : blocks) {
                GL11.glColor3f(block.rf(), block.bf(), block.gf());
                block.render();
            }
            // GL11.glTranslatef(3.5f,0.5f,11f);
            GL11.glScalef(10f, 10f, 10f);

            // GL11.glRotated(-90, 0, 0, 1);
            //GL11.glRotated(-135, 1, 0, 0);//GL11.glTranslatef(-2f,0f,4f);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            //GL11.glTranslatef(-x+2,-y, -z);
            //GL11.glRotated(-180, 0, 1, 0);
        }*/

    public void  beforePlace(){

    }
    public void place(){

    }
    //在被放置的状态下 被使用了
    public boolean beUsed(BaseBlock block){
        try{
        if(StringUtil.isNotEmpty(script)){
            //传入当前的block 状态 
            
            //需要他返回几个元素 当前的animationstate  当前的penatrate 当前的 x y z 当前的width height thick
            //groovy.lang.Binding
            Binding binding = new Binding();
            GroovyShell shell = new GroovyShell(binding);

            binding.setVariable("penetrate", block.isPenetrate());
           
            binding.setVariable("x", block.x);
            binding.setVariable("y", block.y);
            binding.setVariable("z", block.z);
            

            binding.setVariable("width", block.width);
            binding.setVariable("height", block.height);
            binding.setVariable("thick", block.thick);
            
            
            
            String jsonString = (String)shell.evaluate(script);
            
            
            Map<String,Object> resultMap = JSON.parseObject(jsonString, HashMap.class);
            String state = MapUtil.getStringValue(resultMap,"state");
            if(StringUtil.isNotEmpty(state)){
                if(block instanceof AnimationBlock){
                    AnimationBlock animationBlock = (AnimationBlock)block;
                    animationBlock.animations = animationBlock.animationMap.get(state);
                    animationBlock.play();
                    
                }
            }
            Object penetration = resultMap.get("penetrate");
            if(penetration!=null){
                if(block instanceof AnimationBlock){
                    block.penetration=(Boolean)penetration;
                    
                }
            }
            
            System.out.println("Array join:" + jsonString);
            shell = null;
            binding = null;
            return true;
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void onChooseUse(){

    }
    public void afterPlace(){

    }
    public void afterUse(){

    }
    public void beforeUse(){

    }
    public void use(){

    }
    public ItemModel getItemModel() {
        return itemModel;
    }

    public void setItemModel(ItemModel itemModel) {
        this.itemModel = itemModel;
    }

//    public Integer getItemType() {
//        return itemType;
//    }
//
//    public void setItemType(Integer itemType) {
//        this.itemType = itemType;
//    }


    public BaseBlock getShape() {

        return shape;
    }

    public void setShape(BaseBlock shape) {

        this.shape = shape;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemMainType getType() {
        return type;
    }

    public void setType(ItemMainType type) {
        this.type = type;
    }

    /*public ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

*/
    /*public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }*/

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSpirit() {
        return spirit;
    }

    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    public int getAgile() {
        return agile;
    }

    public void setAgile(int agile) {
        this.agile = agile;
    }

    public int getIntelli() {
        return intelli;
    }

    public void setIntelli(int intelli) {
        this.intelli = intelli;
    }

    public int getStrenth() {
        return strenth;
    }

    public void setStrenth(int strenth) {
        this.strenth = strenth;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public  ItemDefinition clone() throws CloneNotSupportedException {
        ItemDefinition itemDef =  (ItemDefinition)super.clone();
        itemDef.setShape(new BoneBlock());
        itemDef.setItemModel(new ItemModel());
        itemDef.itemTypeProperties=null;
        return itemDef;
    }

    public void beDestroyed(BaseBlock block,int chunkX,int chunkY,int chunkZ,int x,int y,int z){

    }
    public void use(GL_Vector placePoint,Integer itemType,GL_Vector viewDir){

    }

//    public String toString(){
//
//        if(firstType==1) {
//            selectBlock.live = true;
//        }
//        if (selectBlock == null) {
//            return;
//        }
//
//        if (isLight && selectBlock instanceof ColorBlock) {
//            ((ColorBlock) selectBlock).isLight = true;
//        }
//        //获取现在设定的id
//        StringBuffer sb = new StringBuffer();
//        selectBlock.setName(name);
//        //  TextureManager.putShape(selectBlock);
//
//        ItemFactory itemFactory = new ItemFactory();
//        selectBlock.penetration = isPenetrate;
//
//        sb.append("{id:").append(id).append(",")
//                .append("name:'").append(name).append("',")
//
//                .append("icon:'").append(this.icon).append("',")
//                .append("remark:'").append(name).append("',")
//                .append("script:'").append(script.replaceAll("\"", "\\\\\"").replaceAll("'", "\\\\\'").replaceAll("\r\n", "")).append("',");
//        if(  firstType==1){
//            sb  .append("live:").append("true,");
//            sb .append("type:'").append("block").append("',");
//            sb  .append("baseon:'mantle',");
//        }else
//
//            sb.append("stack:'").append(MapUtil.getIntValue(param,"stack",1)).append("',");
//
//        if(  firstType==2){//是装备
//
//            sb  .append("type:'").append("wear").append("',");
//            sb  .append("spirit:").append(MapUtil.getIntValue(param,"spirit",0)).append(",");
//            sb  .append("agile:").append(MapUtil.getIntValue(param,"agile",0)).append(",");
//            sb  .append("intelli:").append(MapUtil.getIntValue(param,"intelli",0)).append(",");
//            sb  .append("strenth:").append(MapUtil.getIntValue(param,"strenth",0)).append(",");
//            sb  .append("tili:").append(MapUtil.getIntValue(param,"tili",0)).append(",");
//            if(secondType== Constants.WEAR_POSI_HEAD){
//                sb  .append("position:'").append("head").append("',");
//                sb  .append("baseon:'fur_helmet',");
//            }else if(secondType==Constants.WEAR_POSI_BODY){
//                sb  .append("position:'").append("body").append("',");
//                sb  .append("baseon:'fur_armor',");
//            }else if(secondType==Constants.WEAR_POSI_LEG){
//                sb  .append("position:'").append("leg").append("',");
//                sb  .append("baseon:'fur_pants',");
//            }else if(secondType==Constants.WEAR_POSI_HAND){
//                sb  .append("position:'").append("hand").append("',");
//                sb  .append("baseon:'wood_sword',");
//
//                //如果是远程武器的画//如果是武器的画
//                boolean isFar = MapUtil.getBooleanValue(param,"isFar",false);
//                sb  .append("isFar:"+isFar+",");
//                String ballId= MapUtil.getStringValue(param,"shootBallId");
//                sb  .append("shootBallId:"+ballId+",");
//            }
//        }else{
//            sb .append("type:'").append("block").append("',");
//            sb  .append("baseon:'mantle',");
//        }
//
//        sb.append("remark:'").append(name).append("',")
//                .append("shape:").append(selectBlock.toString()).append(",")
//                .append("}");
//
//    }
    public void receive(Map map ){

        String type = MapUtil.getStringValue(map, "type");
        String name = MapUtil.getStringValue(map,"name");
        if(name.equals("stone")){
            LogUtil.err("123");
        }
        String category = MapUtil.getStringValue(map,"category");
        String baseOn = MapUtil.getStringValue(map,"baseon");
        String icon =MapUtil.getStringValue(map,"icon");//获取icon图片
        String engine = MapUtil.getStringValue(map,"engine");
        String script  = MapUtil.getStringValue(map,"script");
        boolean isFar = MapUtil.getBooleanValue(map,"isFar",false);
        Integer shootBallId = MapUtil.getIntValue(map,"shootBallId",0);
        this.isFar = isFar;
        this.shootBallId = shootBallId;
        if(StringUtil.isNotEmpty(script)){
            LogUtil.println(script);
            this.script = script;
        }
        Integer id = (int)map.get("id");
        this.itemTypeId =id;
        this.itemType = ItemType.getItemTypeById(id);
        if(this.itemType==null){
            LogUtil.err("it 's null");
        }
        if(StringUtil.isNotEmpty(name)){
            this.name =name;

        }

        this.engine = StringUtil.isNotEmpty(engine)?engine : (StringUtil.isNotEmpty(category)?category:type);
        //this.icon = icon;
        if(GamingState.player!=null) {
            if (StringUtil.isNotEmpty(icon)) {
                this.itemModel.setIcon(TextureManager.getTextureInfo(icon));

            }


            Object shapeObj = map.get("shape");
            if(shapeObj instanceof  String){
                String shapeStr = MapUtil.getStringValue(map,"shape");


                if (StringUtil.isNotEmpty(shapeStr)) {
                    this.setShape(TextureManager.getShape(shapeStr));
                } else {
                    this.setShape(TextureManager.getShape(name));
                }
                 shape = TextureManager.getShape(shapeStr);
                if(shape == null ){
                    LogUtil.err("can't found "+ shapeStr);
                    TextureManager.getShape(shapeStr);
                }
                if(shape==null){
                    LogUtil.err("shape not find :"+ name);
                }
                shape.id=id;
                TextureManager.stateIdShapeMap.put(id,shape);
            }else if(shapeObj instanceof JSONObject){
                String blockType = (String)((JSONObject) shapeObj).get("blocktype");
                BaseBlock  block = EditEngine.parse((JSONObject) shapeObj);
//                if("imageblock".equals(blockType)){
//                      block = ImageBlock.parse((JSONObject) shapeObj);
//
//
//                }else if("colorblock".equals(blockType)){
//                    block = ColorBlock.parse((JSONObject) shapeObj);
//                }else if("groupblock".equals(blockType)){
//                    block = GroupBlock.parse((JSONObject) shapeObj);
//                }
//                else if("animationblock".equals(blockType)){
//                    block = AnimationBlock.parse((JSONObject) shapeObj);
//                }else if("rotateimageblock".equals(blockType)){
//                    block = RotateImageBlock.parse((JSONObject) shapeObj);
//                }else if("rotatecolorblock".equals(blockType)){
//                    block = RotateColorBlock2.parse((JSONObject) shapeObj);
//                }else if("bonerotateimageblock".equals(blockType)){
//                    block = BoneRotateImageBlock.parse((JSONObject) shapeObj);
//                }else if("boneblock".equals(blockType)){
//                    block = BoneBlock.parse((JSONObject) shapeObj);
//                }

                block.id =id;

                this.setShape( block);
                TextureManager.putShape(block);
            }

        }

    }
}
