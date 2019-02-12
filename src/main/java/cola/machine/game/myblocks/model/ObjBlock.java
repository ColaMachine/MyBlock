package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.FloatBufferWrap;
import com.dozenx.util.MapUtil;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glmodel.*;

import java.util.ArrayList;
import java.util.List;

public class ObjBlock extends BaseBlock{

   public TextureInfo front;
    String fileName;
    GLModel glModel ;

    List<Float[]> metas = new ArrayList<Float[]>();
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
        block.glModel =this.glModel.copyClone();
        block.front=front;

     
    }


    @Override
    public void render(ShaderConfig config, Vao vao, float x, float y, float z, boolean hastop, boolean hasbottom, boolean hasleft, boolean hasright, boolean hasfront, boolean hasback) {
        GL_Vector[] dirAry = BoxModel.dirAry;


        glModel.renderShader(config,vao,x,y,z);
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
        ObjBlock objBlock =new ObjBlock();
        parseObj(objBlock, map);
        return objBlock;
    }

    public static void  parseObj(ObjBlock objBlock,JSONObject map){

        parse(objBlock,map);
        String front = (String) map.get("front");

       // imageBlock.name = name ;
        //String bottom = (String) map.get("bottom");
        //String allSide =  MapUtil.getStringValue(map, "allSide");

        //if(StringUtil.isNotEmpty(front)){
        objBlock.front = TextureManager.getTextureInfo(front);
        //objBlock.glModel=TextureManager.getObj(MapUtil.getStringValue(map,"obj"));
        //}
        String objName=MapUtil.getStringValue(map,"obj");
        objBlock. glModel = TextureManager.getObj(objName);


        if(objBlock.glModel ==null){
            LogUtil.err("can't find "+objName);
        }

//        GL_Mesh m = objBlock. glModel.mesh;
//        for (int i=0; i < m.triangles.length; ) {
//
//            t = m.triangles[i];
//
//            // activate new material and texture
//          //  currMtl = t.materialID;
//          //  mtl = (materials != null && materials.length>0 && currMtl >= 0)? materials[currMtl] : defaultMtl;
////            mtl.apply();
////            if(mtl!=null){
////                // ShaderUtils. glColor(mtl.diffuse.get(0),mtl.diffuse.get(1),mtl.diffuse.get(2));
////                ShaderUtils.bindTexture(TextureManager.getTextureInfo("human_body_front"));
////            }
//GL_Triangle t;
//            for ( ; i < m.triangles.length && (t=m.triangles[i])!=null; i++) {
//                //   GL11.glTexCoord2f(t.uvw1.x, t.uvw1.y);
//               // veticesBuffer.put(x).put(y).put(z).put(normalX).put(normalY).put(normalZ).put(texCoordX).put(texCoordY).put(0).put(nowTextureId);//p1
//
//                //Float[] floats =new Float[]{t.p1.pos.x, t.p1.pos.y, t.p1.pos.z,t.norm1.x, t.norm1.y, t.norm1.z,}
//                ShaderUtils.glTexCoord2f(t.uvw1.x, t.uvw1.y);
//                ShaderUtils.glNormal3f(t.norm1.x, t.norm1.y, t.norm1.z);
//                objBlock.metas.add(ShaderUtils.getFloats((float) t.p1.pos.x, (float) t.p1.pos.y, (float) t.p1.pos.z));
//
//                ShaderUtils.glTexCoord2f(t.uvw2.x, t.uvw2.y);
//                ShaderUtils.glNormal3f(t.norm2.x, t.norm2.y, t.norm2.z);
//                objBlock.metas.add(ShaderUtils.getFloats((float) t.p2.pos.x, (float) t.p2.pos.y, (float) t.p2.pos.z));
//
//                ShaderUtils.glTexCoord2f(t.uvw3.x, t.uvw3.y);
//                ShaderUtils.glNormal3f(t.norm3.x, t.norm3.y, t.norm3.z);
//                objBlock.metas.add(ShaderUtils.getFloats((float) t.p3.pos.x, (float) t.p3.pos.y, (float) t.p3.pos.z));
//            }
//
//        }


    }

    public void reComputePoints(GL_Matrix glMatrix) {
//        for(int i=0;i<glModel.mesh.vertices.length;i++){
//            glModel.mesh.vertices[i] =glMatrix.multiply();//glModel.mesh.vertices[i]
//        }
//        glModel.mesh.vertices
        //this.points = BoxModel.getSmallPoint(0, 0, 0, width, height, thick);
        // this.points = BoxModel.getSmallPoint(x,y,z,width,height,thick);
        //GL_Matrix rotateMatrix = GL_Matrix.multiply(GL_Matrix.multiply(GL_Matrix.translateMatrix(width / 2, 0, thick / 2), GL_Matrix.rotateMatrix(0, this.dir * Constants.PI90, 0)), GL_Matrix.translateMatrix(-width / 2, 0, -thick / 2));
        //  GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,this.dir*3.14f/2,0);
//        for (int i = 0; i < points.length; i++) {
//            points[i] = rotateMatrix.multiply(rotateMatrix, points[i]);
//
//        }

        //GL_Mesh mesh = glModel.mesh;
        glModel. mesh =   glModel.mesh.makeClone();

        for (int i = 0; i < glModel.mesh.vertices.length; i++) {


            GL_Vertex  gl_vertex = glModel.mesh.vertices[i];
//new GL_Vector(0,0,0);//
            gl_vertex.pos = glMatrix.multiply(glMatrix, gl_vertex.pos);//旋转

        }
    }

}
