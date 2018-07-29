package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import com.dozenx.util.StringUtil;
import glmodel.GLModel;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

public class ObjBlock extends BaseBlock{

   public TextureInfo front;
    String fileName;
    GLModel glModel ;
    public TextureInfo getFront() {
        return front;
    }

    public void setFront(TextureInfo front) {
        this.front = front;
    }



    public ObjBlock(){

    }






    public ObjBlock(String name, int i, boolean b) {
        super(name,i,b);
    }



    @Override
    public void renderShader(ShaderConfig config, Vao vao, GL_Matrix matrix) {
        GL_Vector[] dirAry = BoxModel.dirAry;

        glModel.renderShader();
    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, float parentX, float parentY, float parentZ, float childX, float childY, float childZ, float width, float height, float thick, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

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

    public ObjBlock copy(){

        ObjBlock block  =new ObjBlock();

       

        copyObjBlock( block);
        return block;
    }
    
    public void copyObjBlock(ObjBlock block){
        copyBaseBlock(block);

        block.front=front;

     
    }


    @Override
    public void render(ShaderConfig config, Vao vao, float x, float y, float z, boolean hastop, boolean hasbottom, boolean hasleft, boolean hasright, boolean hasfront, boolean hasback) {
        GL_Vector[] dirAry = BoxModel.dirAry;

    }


    public String toImageBlockString(){


        StringBuffer buffer =new StringBuffer();
        buffer .append(toBaseBlockString())
                .append("front:'").append(this.front==null?"":this.front.name).append("',")
               ;
        return buffer.toString();
    }
    public String toString(){


        StringBuffer buffer =new StringBuffer();
        buffer.append("{")
                .append("blocktype:'imageblock',")

                .append("obj:'"+fileName+"'")
                .append("}");


        return buffer.toString();
    }
    public static ObjBlock parse(JSONObject map){
        ObjBlock imageBlock =new ObjBlock();
        parseImage(imageBlock,map);
        return imageBlock;
    }

    public static void  parseImage(ObjBlock imageBlock,JSONObject map){

        parse(imageBlock,map);
        String front = (String) map.get("front");

       // imageBlock.name = name ;
        //String bottom = (String) map.get("bottom");
        //String allSide =  MapUtil.getStringValue(map, "allSide");

        //if(StringUtil.isNotEmpty(front)){
            imageBlock.front = TextureManager.getTextureInfo(front);
        //}
        String objName=MapUtil.getStringValue(map,"obj");
        GLModel glModel1 = TextureManager.getObj(objName);



    }

    public void reComputePoints(GL_Matrix glMatrix) {
//        for(int i=0;i<glModel.mesh.vertices.length;i++){
//            glModel.mesh.vertices[i] =glMatrix.multiply();//glModel.mesh.vertices[i]
//        }
//        glModel.mesh.vertices
        this.points = BoxModel.getSmallPoint(0, 0, 0, width, height, thick);
        // this.points = BoxModel.getSmallPoint(x,y,z,width,height,thick);
        GL_Matrix rotateMatrix = GL_Matrix.multiply(GL_Matrix.multiply(GL_Matrix.translateMatrix(width / 2, 0, thick / 2), GL_Matrix.rotateMatrix(0, this.dir * Constants.PI90, 0)), GL_Matrix.translateMatrix(-width / 2, 0, -thick / 2));
        //  GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,this.dir*3.14f/2,0);
        for (int i = 0; i < points.length; i++) {
            points[i] = rotateMatrix.multiply(rotateMatrix, points[i]);

        }
    }

}
