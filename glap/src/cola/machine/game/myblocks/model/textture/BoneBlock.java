package cola.machine.game.myblocks.model.textture;

import cola.machine.game.myblocks.block.Block;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luying on 16/9/12.
 */
public class BoneBlock extends BaseBlock {
    public int getShapeType() {
        return shapeType;
    }
    public HashMap<String ,ShapeFace> shapeFaceMap =new HashMap<>();
    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }
    ShapeFace frontFace;
    ShapeFace backFace;
    ShapeFace topFace;
    String group;
    String parent;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ShapeFace getFrontFace() {
        return frontFace;
    }

    public void setFrontFace(ShapeFace frontFace) {
        this.frontFace = frontFace;
    }

    public ShapeFace getBackFace() {
        return backFace;
    }

    public void setBackFace(ShapeFace backFace) {
        this.backFace = backFace;
    }

    public ShapeFace getTopFace() {
        return topFace;
    }

    public void setTopFace(ShapeFace topFace) {
        this.topFace = topFace;
    }

    public ShapeFace getBottomFace() {
        return bottomFace;
    }

    public void setBottomFace(ShapeFace bottomFace) {
        this.bottomFace = bottomFace;
    }

    public ShapeFace getLeftFace() {
        return leftFace;
    }

    public void setLeftFace(ShapeFace leftFace) {
        this.leftFace = leftFace;
    }

    public ShapeFace getRightFace() {
        return rightFace;
    }

    public void setRightFace(ShapeFace rightFace) {
        this.rightFace = rightFace;
    }

    ShapeFace bottomFace;
    ShapeFace leftFace;
    ShapeFace rightFace;
//    String name ;
    int shapeType;
    TextureInfo front;
    TextureInfo back;
    TextureInfo left;
    TextureInfo right;
    TextureInfo top;
    TextureInfo bottom;
    float width;
    float height;
    float thick;
    float p_posi_x;
    float c_posi_y;
    float c_posi_z;
    float p_posi_y;
    float p_posi_z;
    float c_posi_x;

    public float getP_posi_x() {
        return p_posi_x;
    }

    public void setP_posi_x(float p_posi_x) {
        this.p_posi_x = p_posi_x;
    }

    public float getP_posi_y() {
        return p_posi_y;
    }

    public void setP_posi_y(float p_posi_y) {
        this.p_posi_y = p_posi_y;
    }

    public float getP_posi_z() {
        return p_posi_z;
    }

    public void setP_posi_z(float p_posi_z) {
        this.p_posi_z = p_posi_z;
    }

    public float getC_posi_x() {
        return c_posi_x;
    }

    public void setC_posi_x(float c_posi_x) {
        this.c_posi_x = c_posi_x;
    }

    public float getC_posi_y() {
        return c_posi_y;
    }

    public void setC_posi_y(float c_posi_y) {
        this.c_posi_y = c_posi_y;
    }

    public float getC_posi_z() {
        return c_posi_z;
    }

    public void setC_posi_z(float c_posi_z) {
        this.c_posi_z = c_posi_z;
    }


    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getThick() {
        return thick;
    }

    public void setThick(float thick) {
        this.thick = thick;
    }



/*

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
*/

    public TextureInfo getFront() {
        return front;
    }

    public void setFront(TextureInfo front) {
        this.front = front;
    }

    public TextureInfo getBack() {
        return back;
    }

    public void setBack(TextureInfo back) {
        this.back = back;
    }

    public TextureInfo getLeft() {
        return left;
    }

    public void setLeft(TextureInfo left) {
        this.left = left;
    }

    public TextureInfo getRight() {
        return right;
    }

    public void setRight( TextureInfo right) {
        this.right = right;
    }

    public TextureInfo getTop() {
        return top;
    }

    public void setTop(TextureInfo top) {
        this.top = top;
    }

    public TextureInfo getBottom() {
        return bottom;
    }

    public void setBottom(TextureInfo bottom) {
        this.bottom = bottom;
    }


    /**
     * 根据父节点的属性来求子属性的值
     * @param str
     * @param width
     * @param height
     * @param thick
     * @param pwidth
     * @param pheight
     * @param pthick
     * @return
     * @throws Exception
     */
    public static Float parsePosition(String str,Float width,Float height,Float thick,Float pwidth,Float pheight,Float pthick) throws Exception {
    if(StringUtil.isBlank(str)){
        return 0f;
    }
    Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
    Matcher matcher = pattern.matcher(str);
    if(matcher.matches()){
        return Float.valueOf(str);
    }

        //2 ([a-zA-Z_]+) 匹配parent 或者child  . 匹配.  3([a-zA-Z_]+)匹配width height thick 4 5 分子 6 分母
         pattern = Pattern.compile("(([a-zA-Z_]+)\\.)?([a-zA-Z_]+)(\\*(\\d{1,3}\\.{0,1}\\d{0,3}))?/?(\\d{1,3}\\.{0,1}\\d{0,3})?");

        matcher = pattern.matcher(str);


    if(matcher.matches()){
        String parentname = matcher.group(2);
        String attr = matcher.group(3);
        String fenzi = matcher.group(5);
        String fenmu= matcher.group(6);
        String parent = matcher.group(1);
        Float value =0f;
        Float pvalue=0f;
        if(attr.equals("width")){
            value=width;  pvalue=pwidth;
        }else if(attr.equals("height")){
            value=height;  pvalue=pheight;
        }else if(attr.equals("thick")){
            value=thick;  pvalue=pthick;
        }else{
            throw new Exception("解析shape position时候出错: attr不能为空" );
        }

        if(parentname!=null) {
             value=pvalue;
        }

        if(fenzi!=null){
            value*=Float.valueOf(fenzi);
        }
        if(fenmu!=null){
            value/=Float.valueOf(fenmu);
        }


        return value;


    }else{
        throw new Exception("解析shape position时候出错 无法解析"+str );
    }
}


    public static void main(String args[]){

        Pattern pattern = Pattern.compile("(([a-zA-Z_]+)\\.)?([a-zA-Z_]+)(\\*(\\d{1,3}\\.{0,1}\\d{0,3}))?/?(\\d{1,3}\\.{0,1}\\d{0,3})?");
        Matcher matcher = pattern.matcher("parent.height*1.2");

        if(matcher.matches()){
            System.out.println(matcher.groupCount());
            int count = matcher.groupCount();
            for(int i=1;i<=count;i++){
                System.out.println(i+":"+matcher.group(i));
               // System.out.println(i+":"+(matcher.group(i)==null));
            }
        }
    }

    @Override
    public void update(float x, float y, float z, float width, float height, float thick) {

    }

    @Override
    public void update() {

    }

    @Override
    public BaseBlock copy() {
        return null;
    }

    @Override
    public void render(ShaderConfig config, Vao vao, int worldX, int worldY, int worldZ, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

       GL_Vector[] points =  BoxModel.getPoint(worldX, worldY, worldZ);

        if(top && this.top!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao, points[4], points[5], points[6], points[7], BoxModel.dirAry[0], this.top);
        }

        if(bottom&& this.bottom!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao,  points[3], points[2], points[1], points[0],BoxModel. dirAry[1], this.bottom);
        }

        if(front && this.front!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao,  points[0], points[1], points[5], points[4], BoxModel.dirAry[2], this.front);
        }

        if(back && this.back!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao,  points[2], points[3], points[7], points[6], BoxModel.dirAry[3], this.back);
        }

        if(left && this.left!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao,  points[3], points[0], points[4], points[7],BoxModel. dirAry[4], this.left);
        }
        if(right&& this.right!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao, points[1], points[2], points[6], points[5],BoxModel. dirAry[5], this.right);
        }
    }

    public static BoneBlock parse(JSONObject map) throws Exception {

        BoneBlock shape = new BoneBlock();
        String name = (String) map.get("name");
                    /*if(name.equals("mantle")){
                        LogUtil.println("mantle");
                    }*/
        shape.setName(name);

        int shapeType =
                MapUtil.getIntValue(map, "shapeType");
        if(shapeType!=2 && shapeType!=3){
            LogUtil.err("shaperType is error ");
        }
        String group   = MapUtil.getStringValue(map,"group");
        if(group!=null ){
            shape.setGroup(group);
            List shapeGroupList = TextureManager.shapeGroups.get(group);
            if(shapeGroupList == null ){
                shapeGroupList =new ArrayList();
                TextureManager.shapeGroups .put(group , shapeGroupList);
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
        if(leftObj instanceof  com.alibaba.fastjson.JSONObject){
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
        if(name.equalsIgnoreCase("box")){
            LogUtil.println("box");
        }
        Object model =  map.get("model");

        if(model!=null && model instanceof  com.alibaba.fastjson.JSONArray){
            //转成数组 shapeface的数组
            //com.alibaba.fastjson.JSONArray
            com.alibaba.fastjson.JSONArray arr = (com.alibaba.fastjson.JSONArray)model;

            for(int j=0;j<arr.size();j++){

                JSONObject modelShapeFaceObj=(JSONObject)arr.get(j);
                ShapeFace modelShapeFace= getShapeFace(modelShapeFaceObj);

                shape.shapeFaceMap.put(modelShapeFace.getName(),modelShapeFace);
            }
                   /*     ShapeFace shapeFace = getShapeFace(frontObj);
                        shape.setFrontFace(shapeFace);*/
        }

        String parent = MapUtil.getStringValue(map, "parent");
        shape.setParent(parent);
        if (!"root".equals(parent)&& parent!=null) {
            String p_posi_xStr = MapUtil.getStringValue(map, "p_posi_x");
            String p_posi_yStr = MapUtil.getStringValue(map, "p_posi_y");
            String p_posi_zStr = MapUtil.getStringValue(map, "p_posi_z");

            BoneBlock parentShape = (BoneBlock)TextureManager.getShape(parent);
            if (shape == null) {
                LogUtil.err("can 't find shape" + parent);
            }
            // String c_posi_xStr =  MapUtil.getStringValue(map,"c_posi_x");
            try {//有shape 不一定有parentShape
                            /*if(shape==null || parentShape ==null){
                                LogUtil.err(" is null");


                            }*/

                // Object widthObj = map.get("width");

                float width = BoneBlock.parsePosition(MapUtil.getStringValue(map, "width"), 0f, 0f, 0f, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick());


                float height = BoneBlock.parsePosition(MapUtil.getStringValue(map, "height"), 0f, 0f, 0f, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick());



                float thick = BoneBlock.parsePosition(MapUtil.getStringValue(map, "thick"), 0f, 0f, 0f, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick());

                shape.setWidth(width);
                shape.setHeight(height);
                shape.setThick(thick);

                if(/*shape!=null &&*/ parentShape!=null && StringUtil.isNotEmpty(MapUtil.getStringValue(map, "c_posi_x"))) {

                    shape.setC_posi_x(BoneBlock.parsePosition(MapUtil.getStringValue(map, "c_posi_x"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));

                    //String c_posi_yStr =  MapUtil.getStringValue(map,"c_posi_y");
                    shape.setC_posi_y(BoneBlock.parsePosition(MapUtil.getStringValue(map, "c_posi_y"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));

                    shape.setC_posi_z(BoneBlock.parsePosition(MapUtil.getStringValue(map, "c_posi_z"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));

                    shape.setP_posi_x(BoneBlock.parsePosition(MapUtil.getStringValue(map, "p_posi_x"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));
                    shape.setP_posi_y(BoneBlock.parsePosition(MapUtil.getStringValue(map, "p_posi_y"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));
                    shape.setP_posi_z(BoneBlock.parsePosition(MapUtil.getStringValue(map, "p_posi_z"), width, height, thick, parentShape.getWidth(), parentShape.getHeight(), parentShape.getThick()));

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


            shape.setC_posi_x(BoneBlock.parsePosition(MapUtil.getStringValue(map, "c_posi_x"), width, height, thick, 0f, 0f,0f));

            //String c_posi_yStr =  MapUtil.getStringValue(map,"c_posi_y");
            shape.setC_posi_y(BoneBlock.parsePosition(MapUtil.getStringValue(map, "c_posi_y"), width, height, thick, 0f, 0f,0f));

            shape.setC_posi_z(BoneBlock.parsePosition(MapUtil.getStringValue(map, "c_posi_z"), width, height, thick,  0f, 0f,0f));

            shape.setP_posi_x(BoneBlock.parsePosition(MapUtil.getStringValue(map, "p_posi_x"), width, height, thick,  0f, 0f,0f));
            shape.setP_posi_y(BoneBlock.parsePosition(MapUtil.getStringValue(map, "p_posi_y"), width, height, thick,  0f, 0f,0f));
            shape.setP_posi_z(BoneBlock.parsePosition(MapUtil.getStringValue(map, "p_posi_z"), width, height, thick,  0f, 0f,0f));

        }
        if (shapeType == 3) {
            if (!isEmpty(front)) {
                shape.setFront(TextureManager.getTextureInfo(front));
            }
            if (!isEmpty(back)) {
                shape.setBack(TextureManager.getTextureInfo(back));
            }
            if (!isEmpty(left)) {
                shape.setLeft(TextureManager.getTextureInfo(left));
            }
            if (!isEmpty(right)) {
                shape.setRight(TextureManager.getTextureInfo(right));
            }
            if (!isEmpty(top)) {
                shape.setTop(TextureManager.getTextureInfo(top));
            }
            if (!isEmpty(bottom)) {
                shape.setBottom(TextureManager.getTextureInfo(bottom));
            }
        }
        return shape;
    }
    public static ShapeFace getShapeFace(Object frontObj ){
        ShapeFace shapeFace =new ShapeFace();


        JSONObject jsonObj = (JSONObject)frontObj;
        //  HashMap keyValue = (HashMap)jsonObj.get(0);
        String name = jsonObj.getString("name");
        shapeFace.setName(name);
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
        int[][] faces = new int[facesAry.size()][facesAry.getJSONArray(0).size()];
        for(int i=0;i<faces.length;i++) {
            for (int j = 0; j < facesAry.getJSONArray(i).size(); j++) {
                faces[i][j] = facesAry.getJSONArray(i).getInteger(j);
            }
        }
        shapeFace.setFaces(faces);
        return shapeFace;
    }
    @Override
    public IBlock clone() {
        return null;
    }

    public static boolean isEmpty(String name) {
        if (name == null || name.equals("")) {
            return true;
        } else {
            return false;
        }
    }

}