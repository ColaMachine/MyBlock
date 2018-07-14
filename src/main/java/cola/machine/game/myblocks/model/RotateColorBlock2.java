package cola.machine.game.myblocks.model;


import cola.machine.game.myblocks.engine.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;


public class RotateColorBlock2 extends ColorBlock implements RotateBlock{
    public RotateColorBlock2(){

    }



    public   float rotateX;
    public float rotateY;
    public float rotateZ;
    public float centerX=0.5f;
    public float centerY=0.5f;

    public float getRotateX() {
        return rotateX;
    }

    public void setRotateX(float rotateX) {
        this.rotateX = rotateX;
    }

    public float getRotateY() {
        return rotateY;
    }

    public void setRotateY(float rotateY) {
        this.rotateY = rotateY;
    }

    public float getRotateZ() {
        return rotateZ;
    }

    public void setRotateZ(float rotateZ) {
        this.rotateZ = rotateZ;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public void setCenterZ(float centerZ) {
        this.centerZ = centerZ;
    }

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
        RotateColorBlock2 colorBlock  =new RotateColorBlock2();

        copyRotateColorBlock(colorBlock);
        colorBlock.reComputePoints();
        return colorBlock;
    }
    
    public void copyRotateColorBlock(RotateColorBlock2 block){

        copyColorBlock(block);
        block .rotateX= this.rotateX;
        block .rotateY= this.rotateY;
        block.centerY = this.centerY;
        block.centerX = this. centerX;
        block .centerZ = this. centerZ;
        block .rotateZ= this.rotateZ;

    }
   
    public float  rotateX(float value){
        this.rotateX+=value*0.1;//reComputePoints();
        reComputePoints();
        return this.rotateX;
    }
    public float rotateY(float value){
        this.rotateY+=value*0.1;

        reComputePoints();

        return this.rotateY;
        //reComputePoints();
    }
    public float rotateZ(float value){
        this.rotateZ+=value*0.1;
        reComputePoints();
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

    public static void main(String args[]){
        GL_Vector point =new GL_Vector(0,1,0);
        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0.9f,0,0);
        point= rotateMatrix.multiply(rotateMatrix ,point);
        System.out.println(point);

        //x=0.4 y=0.1 z=1.4 width = 0.5 height =1 thick=0.5 centerX=0.25 centerY=0.5 centerZ=0.25
        float x=0.4f,y=0.1f,z=1.4f,height=1,thick=0.5f,width=0.5f,centerX=0.25f,centerY=0.5f,centerZ=0.25f;
        GL_Vector[] points= BoxModel.getSmallPoint(x,y,z,width,height,thick);
        //必须按照x y z 增量旋转
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(x+centerX ,y+centerY ,z+centerZ);
        // GL_Matrix translateMatrix = GL_Matrix.translateMatrix(centerX ,centerY ,centerZ);
         rotateMatrix = GL_Matrix.rotateMatrix(Constants.PI,0,0);
        GL_Vector[] points2=new GL_Vector[8];
        rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
        rotateMatrix =GL_Matrix.multiply(rotateMatrix,GL_Matrix.translateMatrix(-x-centerX ,-y-centerY ,-z-centerZ));
        for(int i=0;i<points.length;i++){
            points2[i]=rotateMatrix.multiply(rotateMatrix ,points[i]);
        }
        System.out.println(points[0]);
    }
    @Override
    public void reComputePointsInGroup(){
        points= BoxModel.getSmallPoint(x,y,z,width,height,thick);
        //必须按照x y z 增量旋转
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(x+centerX ,y+centerY ,z+centerZ);
       // GL_Matrix translateMatrix = GL_Matrix.translateMatrix(centerX ,centerY ,centerZ);
        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(rotateX,rotateY,rotateZ);

        rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
        rotateMatrix =GL_Matrix.multiply(rotateMatrix,GL_Matrix.translateMatrix(-x-centerX ,-y-centerY ,-z-centerZ));
        for(int i=0;i<points.length;i++){
            points[i]=rotateMatrix.multiply(rotateMatrix ,points[i]);
        }

    }
    //x=0.4 y=0.1 z=1.4 width = 0.5 height =1 thick=0.5 centerX=0.25 centerY=0.5 centerZ=0.25
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
        block.parseRotateColor(block,map);
        block.reComputePoints();
        return block;


    }

    public  void parseRotateColor(RotateColorBlock2 block ,JSONObject map){
        parseColor(block,map);

        float rotateX = MapUtil.getFloatValue(map,"rotateX");
        float rotateY = MapUtil.getFloatValue(map,"rotateY");
        float rotateZ = MapUtil.getFloatValue(map,"rotateZ");


        block.centerX = MapUtil.getFloatValue(map,"centerX",0f);
        block.centerY = MapUtil.getFloatValue(map,"centerY",0f);
        block.centerZ = MapUtil.getFloatValue(map,"centerZ",0f);

        block.rotateX= rotateX;

        block.rotateY= rotateY;
        block.rotateX= rotateX;


    }
    public String toRotateColorString(){
        StringBuffer buffer =new StringBuffer();

        buffer  .append(toColorBlockString())

                .append("centerX:").append(this.centerX).append(",")
                .append("centerY:").append(this.centerY).append(",")
                .append("centerZ:").append(this.centerZ).append(",")
                .append("rotateX:").append(this.rotateX).append(",")
                .append("rotateY:").append(this.rotateY).append(",")
                .append("rotateZ:").append(this.rotateZ).append(",");



        return buffer.toString();
    }
    public String toString(){
        StringBuffer buffer =new StringBuffer();
        buffer.append("{")
                .append("blocktype:'rotatecolorblock',")
                .append(toRotateColorString())


                .append("}");
        /*StringBuffer sb = new StringBuffer();
        sb.append(this.x).append(",").append(this.y).append(",").append(this.z).append(",")
                .append(this.width).append(",").append(this.height).append(",").append(this.thick).append(",")
                .append(this.rf).append(",").append(this.gf).append(",").append(this.bf);
        return sb.toString();*/
        return buffer.toString();
    }
    @Override
    public float getCenterX() {
        return centerX;
    }

    @Override
    public float getCenterY() {
        return centerY;
    }

    @Override
    public float getCenterZ() {
        return centerZ;
    }
}
