package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import com.dozenx.util.StringUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

public class ImageBlock extends BaseBlock{

   public TextureInfo front;
    public   TextureInfo back;
    public TextureInfo left;
    public TextureInfo right;
    public TextureInfo top;
    public TextureInfo bottom;

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

    public void setRight(TextureInfo right) {
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

    public ImageBlock(){

    }






    public ImageBlock(String name, int i, boolean b) {
        super(name,i,b);
    }

//    public void update(float x,float y,float z,float width,float height,float thick){
//
//
//        GL_Vector[] dirAry = BoxModel.dirAry;
//
//        if(top!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z, points[4], points[5], points[6], points[7], dirAry[0], top);
//        }
//
//        if(bottom!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[3], points[2], points[1], points[0], dirAry[1], bottom);
//        }
//
//        if(front!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z,  points[0], points[1], points[5], points[4], dirAry[2], front);
//        }
//
//        if(back!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[2], points[3], points[7], points[6], dirAry[3], back);
//        }
//
//        if(left!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z,  points[3], points[0], points[4], points[7], dirAry[4], left);
//        }
//        if(right!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[1], points[2], points[6], points[5], dirAry[5], right);
//        }
//
//
//    }


    @Override
    public void renderShader(ShaderConfig config, Vao vao, GL_Matrix matrix) {
        GL_Vector[] dirAry = BoxModel.dirAry;

        if(top!=null) {

            ShaderUtils.draw3dImage(points[4], points[5], points[6], points[7],  matrix, dirAry[0], top, vao.getVertices(), config);
        }

        if(bottom!=null) {
            ShaderUtils.draw3dImage(points[3], points[2], points[1], points[0],  matrix, dirAry[1], top, vao.getVertices(), config);
        }

        if(front!=null) {
            ShaderUtils.draw3dImage(points[0], points[1], points[5], points[4],   matrix, dirAry[2], top, vao.getVertices(), config);

        }

        if(back!=null) {
            ShaderUtils.draw3dImage( points[2], points[3], points[7], points[6],  matrix, dirAry[3], top, vao.getVertices(), config);

        }

        if(left!=null) {
            ShaderUtils.draw3dImage(points[3], points[0], points[4], points[7],  matrix, dirAry[4], top, vao.getVertices(), config);

        }
        if(right!=null) {
            ShaderUtils.draw3dImage( points[1], points[2], points[6], points[5],  matrix, dirAry[0], top, vao.getVertices(), ShaderManager.terrainShaderConfig);

        }
    }

   // public void update(){


//        GL_Vector[] dirAry = BoxModel.dirAry;
//
//        if(top!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z, points[4], points[5], points[6], points[7], dirAry[0], top);
//        }
//
//        if(bottom!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[3], points[2], points[1], points[0], dirAry[1], bottom);
//        }
//
//        if(front!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z,  points[0], points[1], points[5], points[4], dirAry[2], front);
//        }
//
//        if(back!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[2], points[3], points[7], points[6], dirAry[3], back);
//        }
//
//        if(left!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z,  points[3], points[0], points[4], points[7], dirAry[4], left);
//        }
//        if(right!=null) {
//            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[1], points[2], points[6], points[5], dirAry[5], right);
//        }

//}

    public ImageBlock copy(){

        ImageBlock block  =new ImageBlock();

       

        copyImageBlock( block);
        return block;
    }
    
    public void copyImageBlock(ImageBlock block){
        copyBaseBlock(block);
        block.top=top;
        block.bottom=bottom;
        block.front=front;
        block.back=back;
        block.left=left;
        block.right=right;
     
    }


    @Override
    public void render(ShaderConfig config, Vao vao, float x, float y, float z, boolean hastop, boolean hasbottom, boolean hasleft, boolean hasright, boolean hasfront, boolean hasback) {
        GL_Vector[] dirAry = BoxModel.dirAry;
        if(top!=null && hastop) {
            ShaderUtils.draw3dImage(config, vao,x,y,z, points[4], points[5], points[6], points[7], dirAry[0], top);
        }

        if(bottom!=null &&hasbottom) {
            ShaderUtils.draw3dImage(config, vao, x,y,z, points[3], points[2], points[1], points[0], dirAry[1], bottom);
        }

        if(front!=null && hasfront) {
            ShaderUtils.draw3dImage(config, vao,x,y,z,  points[0], points[1], points[5], points[4], dirAry[2], front);
        }

        if(back!=null && hasback) {
            ShaderUtils.draw3dImage(config, vao, x,y,z, points[2], points[3], points[7], points[6], dirAry[3], back);
        }

        if(left!=null && hasleft) {
            ShaderUtils.draw3dImage(config, vao,x,y,z,  points[3], points[0], points[4], points[7], dirAry[4], left);
        }
        if(right!=null && hasright)  {
            ShaderUtils.draw3dImage(config, vao, x,y,z, points[1], points[2], points[6], points[5], dirAry[5], right);
        }
    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, float parentX,float parentY,float parentZ, float childX,float childY,float childZ, float width, float height, float thick,  boolean hastop, boolean hasbottom, boolean hasleft, boolean hasright, boolean hasfront, boolean hasback) {

        GL_Vector[] dirAry = BoxModel.dirAry;

        if(top!=null && hastop) {
            ShaderUtils.draw3dImage(config, vao,parentX,parentY,parentZ, points[4], points[5], points[6], points[7], dirAry[0], top);
        }

        if(bottom!=null &&hasbottom) {
            ShaderUtils.draw3dImage(config, vao,parentX,parentY,parentZ, points[3], points[2], points[1], points[0], dirAry[1], bottom);
        }

        if(front!=null && hasfront) {
            ShaderUtils.draw3dImage(config, vao,parentX,parentY,parentZ,  points[0], points[1], points[5], points[4], dirAry[2], front);
        }

        if(back!=null && hasback) {
            ShaderUtils.draw3dImage(config, vao, parentX,parentY,parentZ,  points[2], points[3], points[7], points[6], dirAry[3], back);
        }

        if(left!=null && hasleft) {
            ShaderUtils.draw3dImage(config, vao,parentX,parentY,parentZ,  points[3], points[0], points[4], points[7], dirAry[4], left);
        }
        if(right!=null && hasright)  {
            ShaderUtils.draw3dImage(config, vao,parentX,parentY,parentZ, points[1], points[2], points[6], points[5], dirAry[5], right);
        }

    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, GL_Matrix matrix, GL_Vector[] childPoints) {
        GL_Vector[] dirAry = BoxModel.dirAry;

        if(top!=null ) {
            ShaderUtils.draw3dImage(config, vao,matrix, points[4], points[5], points[6], points[7], dirAry[0], top);
        }

        if(bottom!=null) {
            ShaderUtils.draw3dImage(config, vao,matrix, points[3], points[2], points[1], points[0], dirAry[1], bottom);
        }

        if(front!=null ) {
            ShaderUtils.draw3dImage(config, vao,matrix,  points[0], points[1], points[5], points[4], dirAry[2], front);
        }

        if(back!=null ) {
            ShaderUtils.draw3dImage(config, vao, matrix,  points[2], points[3], points[7], points[6], dirAry[3], back);
        }

        if(left!=null ) {
            ShaderUtils.draw3dImage(config, vao,matrix,  points[3], points[0], points[4], points[7], dirAry[4], left);
        }
        if(right!=null )  {
            ShaderUtils.draw3dImage(config, vao,matrix, points[1], points[2], points[6], points[5], dirAry[5], right);
        }
    }

    public String toImageBlockString(){


        StringBuffer buffer =new StringBuffer();
        buffer
                .append("front:'").append(this.front==null?"":this.front.name).append("',")
                .append("back:'").append(this.back==null?"":this.back.name).append("',")
                .append("bottom:'").append(this.bottom==null?"":this.bottom.name).append("',")
                .append("top:'").append(this.top==null?"":this.top.name).append("',")
                .append("left:'").append(this.left==null?"":this.left.name).append("',")
                .append("right:'").append(this.right==null?"":this.right.name).append("',");




        return buffer.toString();
    }
    public String toString(){


        StringBuffer buffer =new StringBuffer();
        buffer.append("{")
                .append("blocktype:'imageblock',")
               .append(toBaseBlockString())
                .append(toImageBlockString())
                .append("}");

       /* StringBuffer sb =new StringBuffer();

        sb.append(x).append(",").append(y).append(",").append(z).append(",")
                .append(width).append(",").append(height).append(",").append(thick).append(",");


        sb.append(this.x).append(",").append(this.y).append(",").append(this.z).append(",")
                .append(this.width).append(",").append(this.height).append(",").append(this.thick).append(",")
                .append(0).append(",").append(0).append(",").append(0)
                .append(",").append(0)
                .append(",") .append(this.top==null?" ":this.top.name)
                .append(",") .append(this.bottom==null?" ":this.bottom.name)
                .append(",") .append(this.front==null?" ":this.front.name)
                .append(",") .append(this.back==null?" ":this.back.name)
                .append(",") .append(this.left==null?" ":this.left.name)
                .append(",").append(this.right==null?" ":this.right.name);

        return sb.toString();
*/
        return buffer.toString();
    }
    public static ImageBlock parse(JSONObject map){
        ImageBlock imageBlock =new ImageBlock();
        parseImage(imageBlock,map);
        return imageBlock;
    }

    public static void  parseImage(ImageBlock imageBlock,JSONObject map){


        parse(imageBlock,map);
        String front = (String) map.get("front");
        String back = (String) map.get("back");
        String left = (String) map.get("left");
        String right = (String) map.get("right");
        String top = (String) map.get("top");
        String name = (String) map.get("name");
        imageBlock.name = name ;
        String bottom = (String) map.get("bottom");
        String allSide =  MapUtil.getStringValue(map, "allSide");
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
        if(StringUtil.isNotEmpty(front)){
            imageBlock.front = TextureManager.getTextureInfo(front);
        }
        if(StringUtil.isNotEmpty(back))
        imageBlock.back = TextureManager.getTextureInfo(back);
        if(StringUtil.isNotEmpty(top))
        imageBlock.top = TextureManager.getTextureInfo(top);
        if(StringUtil.isNotEmpty(bottom))
        imageBlock.bottom = TextureManager.getTextureInfo(bottom);
        if(StringUtil.isNotEmpty(left))
        imageBlock.left = TextureManager.getTextureInfo(left);
        if(StringUtil.isNotEmpty(right))
        imageBlock.right = TextureManager.getTextureInfo(right);



    }
    @Override
    public IBlock clone() {
        return null;
    }
}
