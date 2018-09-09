package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import core.log.LogUtil;
import glmodel.GLModel;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import glmodel.GL_Vertex;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * raw block is mainly ground floor product used to render not
 * to design something  all design'ed block is finally changed into the rawblock to redner
 * for opengl
 * though it has an arry to render though all block has it's Corresponding rawblock
 * but some
 *
 */
public class RawBlock extends BaseBlock{

   public TextureInfo front;
    List<Float[]> metas = new ArrayList<Float[]>();
    public TextureInfo getFront() {
        return front;
    }

    public void setFront(TextureInfo front) {
        this.front = front;
    }



    public RawBlock(){

    }






    public RawBlock(String name, int i, boolean b) {
        super(name,i,b);
    }



    @Override
    public void renderShader(ShaderConfig config, Vao vao, GL_Matrix matrix) {

    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, float parentX, float parentY, float parentZ, float childX, float childY, float childZ, float width, float height, float thick, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }


    public RawBlock copy(){

        RawBlock block  =new RawBlock();

       

        copyObjBlock( block);
        return block;
    }
    
    public void copyObjBlock(RawBlock block){
        copyBaseBlock(block);

        block.front=front;

     
    }


    @Override
    public void render(ShaderConfig config, Vao vao, float x, float y, float z, boolean hastop, boolean hasbottom, boolean hasleft, boolean hasright, boolean hasfront, boolean hasback) {

    }


    public String toRawBlockString(){


        StringBuffer buffer =new StringBuffer();
        buffer .append(toBaseBlockString())
                .append("data:'").append(JSON.toJSONString(metas)).append("',")
               ;
        return buffer.toString();
    }
    public String toString(){


        StringBuffer buffer =new StringBuffer();
        buffer.append("{")
                .append("blocktype:'rawblock',")

                .append(toRawBlockString())
                .append("}");


        return buffer.toString();
    }
    public static RawBlock parse(JSONObject map){
        RawBlock objBlock =new RawBlock();
        parseRaw(objBlock, map);
        return objBlock;
    }

    public static void  parseRaw(RawBlock objBlock,JSONObject map){

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
        JSONArray arr =(JSONArray) map.get("data");


    }

    public void reComputePoints(GL_Matrix glMatrix) {


//        for (int i = 0; i < glModel.mesh.vertices.length; i++) {
//
//
//            GL_Vertex  gl_vertex = glModel.mesh.vertices[i];
////new GL_Vector(0,0,0);//
//            gl_vertex.pos = glMatrix.multiply(glMatrix, gl_vertex.pos);//旋转
//
//        }
    }

}
