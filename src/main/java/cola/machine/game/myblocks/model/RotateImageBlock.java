package cola.machine.game.myblocks.model;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;


public class RotateImageBlock extends ImageBlock{
    public RotateImageBlock(){

    }



    public   float rotateX;
    public float rotateY;
    public float rotateZ;
    public float centerX=0.5f;
    public float centerY=0.5f;
    public float centerZ=0.5f;


    public RotateImageBlock(float x, float y, float z){
        this.x =x;
        this.y=y;
        this.z=z;


    }
    public RotateImageBlock(float x, float y, float z, float width, float height, float thick){
        this.x =x;
        this.y=y;
        this.z=z;
        this.width =width;
        this.height =height;
        this.thick =thick;

    }
    public RotateImageBlock(float x, float y, float z, float width, float height, float thick, float rf, float gf, float bf, float opacity){
        this.x =x;
        this.y=y;
        this.z=z;

        this.width =width;
        this.height =height;
        this.thick =thick;

    }


/*
    public void update(){


        GL_Vector[] dirAry = BoxModel.dirAry;




        ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z, this.points,dirAry,  rf, gf, bf,this.opacity);

    }*/

    public RotateImageBlock copy(){
        RotateImageBlock block  =new RotateImageBlock(this.x,this.y,this.z,this.width,this.height,this.thick);
        block.front=front;
        block.back = back;
        block.left = left;
        block.right = right;
        block.top =top;
        block.bottom = bottom;
        block .rotateX= this.rotateX;
        block .rotateY= this.rotateY;
        block.centerY = this.centerY;
        block.centerX = this. centerX;
        block .centerZ = this. centerZ;
        block .rotateZ= this.rotateZ;
        block.reComputePoints();
        return block;
    }
    public float rotateX(float value){
        this.rotateX+=value*0.1;reComputePoints();
        return this.rotateX;
    }
    public float rotateY(float value){
        this.rotateY+=value*0.1;
        reComputePoints();
        return this.rotateY;
    }
    public float rotateZ(float value){
        this.rotateZ+=value*0.1;
        reComputePoints();
        return this.rotateZ;
    }

    @Override
    public void reComputePoints(){
        points= BoxModel.getSmallPoint(0,0,0,width,height,thick);
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(centerX ,centerY ,centerZ);
        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(rotateX,rotateY,rotateZ);

        rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
        rotateMatrix =GL_Matrix.multiply(rotateMatrix,GL_Matrix.translateMatrix(-centerX ,-centerY ,-centerZ));
        for(int i=0;i<points.length;i++){
            points[i]=rotateMatrix.multiply(rotateMatrix ,points[i]);
        }
    }
    @Override
    public void reComputePointsInGroup(){
        points= BoxModel.getSmallPoint(x,y,z,width,height,thick);
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(centerX ,centerY ,centerZ);
        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(rotateX,rotateY,rotateZ);

        rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
        rotateMatrix =GL_Matrix.multiply(rotateMatrix,GL_Matrix.translateMatrix(-centerX ,-centerY ,-centerZ));
        for(int i=0;i<points.length;i++){
            points[i]=rotateMatrix.multiply(rotateMatrix ,points[i]);
        }

    }

    /**
     * 长长使用再在group中

     */
    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao,float parentX,float parentY,float parentZ, float childX,float childY,float childZ, float width, float height, float thick, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back){

    }

    /**
     * 在chunk当中直接使用
     * @param config
     * @param vao
     * @param x
     * @param y
     * @param z
     * @param top
     * @param bottom
     * @param left
     * @param right
     * @param front
     * @param back
     */
    @Override
    public void render(ShaderConfig config, Vao vao, float x, float y, float z, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }


    public static RotateImageBlock parse(JSONObject map){

        RotateImageBlock block =new RotateImageBlock();
        block.parseRotateImage(block,map);
        block.reComputePoints();
        return block;


    }

    public  void parseRotateImage(RotateImageBlock block ,JSONObject map){
        parseImage(block,map);

        float rotateX = MapUtil.getFloatValue(map,"rotateX");
        float rotateY = MapUtil.getFloatValue(map,"rotateY");
        float rotateZ = MapUtil.getFloatValue(map,"rotateZ");

        block.rotateX= rotateX;

        block.rotateY= rotateY;
        block.rotateX= rotateX;


    }

    public String toString(){
        StringBuffer buffer =new StringBuffer();
        buffer.append("{").append("name:'").append(this.name).append("',")
                .append("blocktype:'rotateimageblock',")
                .append("width:").append(this.width).append(",")
                .append("height:").append(this.height).append(",")
                .append("thick:").append(this.thick).append(",")
                .append("x:").append(this.x).append(",")
                .append("y:").append(this.y).append(",")
                .append("z:").append(this.z).append(",")
                .append("front:'").append(this.front==null?"":this.front.name).append("',")
                .append("back:'").append(this.back==null?"":this.back.name).append("',")
                .append("bottom:'").append(this.bottom==null?"":this.bottom.name).append("',")
                .append("top:'").append(this.top==null?"":this.top.name).append("',")
                .append("left:'").append(this.left==null?"":this.left.name).append("',")
                .append("right:'").append(this.right==null?"":this.right.name).append("',")
                .append("rotateX:").append(this.rotateX).append(",")
                .append("rotateY:").append(this.rotateY).append(",")
                .append("rotateZ:").append(this.rotateZ).append(",")
                .append("points:").append(JSON.toJSON(this.points)).append(",")
                .append("}");
        /*StringBuffer sb = new StringBuffer();
        sb.append(this.x).append(",").append(this.y).append(",").append(this.z).append(",")
                .append(this.width).append(",").append(this.height).append(",").append(this.thick).append(",")
                .append(this.rf).append(",").append(this.gf).append(",").append(this.bf);
        return sb.toString();*/
        return buffer.toString();
    }

}
