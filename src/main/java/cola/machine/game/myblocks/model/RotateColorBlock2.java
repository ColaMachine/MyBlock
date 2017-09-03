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


public class RotateColorBlock2 extends ColorBlock{
    public RotateColorBlock2(){

    }



    public   float rotateX;
    public float rotateY;
    public float rotateZ;
    public float centerX=0.5f;
    public float centerY=0.5f;
    public float centerZ=0.5f;


    public RotateColorBlock2(float x, float y, float z){
        this.x =x;
        this.y=y;
        this.z=z;


    }
    public RotateColorBlock2(float x, float y, float z, float width, float height, float thick){
        this.x =x;
        this.y=y;
        this.z=z;
        this.width =width;
        this.height =height;
        this.thick =thick;

    }
    public RotateColorBlock2(float x, float y, float z, float width, float height, float thick, float rf, float gf, float bf, float opacity){
        this.x =x;
        this.y=y;
        this.z=z;
        this.opacity = opacity;
        this.width =width;
        this.height =height;
        this.thick =thick;
        this.rf =rf;
        this.gf =gf;
        this.bf =bf;
    }



    public void update(){


        GL_Vector[] dirAry = BoxModel.dirAry;




        ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z, this.points,dirAry,  rf, gf, bf,this.opacity);

    }

    public RotateColorBlock2 copy(){
        RotateColorBlock2 colorBlock  =new RotateColorBlock2(this.x,this.y,this.z,this.width,this.height,this.thick,this.rf,this.gf,this.bf,this.opacity);
        colorBlock .rotateX= this.rotateX;
        colorBlock .rotateY= this.rotateY;
        colorBlock.centerY = this.centerY;
        colorBlock.centerX = this. centerX;
        colorBlock .centerZ = this. centerZ;
        colorBlock .rotateZ= this.rotateZ;
        colorBlock.reComputePoints();
        return colorBlock;
    }
    public float  rotateX(float value){
        this.rotateX+=value*0.1;//reComputePoints();
        return this.rotateX;
    }
    public float rotateY(float value){
        this.rotateY+=value*0.1;
        return this.rotateY;
        //reComputePoints();
    }
    public float rotateZ(float value){
        this.rotateZ+=value*0.1;
        return this.rotateZ;
        //reComputePoints();
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
        //ShaderUtils.draw3dColorBox(config, vao, x, y, z, new GL_Vector(rf, gf, bf), width, height, thick, /*selectBlockList.size()>0?0.5f:*/this.opacity);

        ShaderUtils.draw3dColorBox(config, vao,parentX,parentY,parentZ, this.points,BoxModel.dirAry,  rf, gf, bf,this.opacity);

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
        ShaderUtils.draw3dColorBox(config, vao,x,y,z, this.points,BoxModel.dirAry,  rf, gf, bf,this.opacity);

    }


    public static RotateColorBlock2 parse(JSONObject map){

        RotateColorBlock2 block =new RotateColorBlock2();
        block.parse(block,map);
        block.reComputePoints();
        return block;


    }

    public  void parseRotateColor(RotateColorBlock2 block ,JSONObject map){
        parseColor(block,map);

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
                .append("blocktype:'rotatecolorblock',")
                .append("width:").append(this.width).append(",")
                .append("height:").append(this.height).append(",")
                .append("thick:").append(this.thick).append(",")
                .append("x:").append(this.x).append(",")
                .append("y:").append(this.y).append(",")
                .append("z:").append(this.z).append(",")
                .append("r:").append(this.rf).append(",")
                .append("g:").append(this.gf).append(",")
                .append("b:").append(this.bf).append(",")
                .append("a:").append(this.opacity).append(",")
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
