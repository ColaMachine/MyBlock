package cola.machine.game.myblocks.model;


import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import glmodel.GL_Matrix;


public class BoneRotateImageBlock extends ImageBlock implements RotateBlock{

    public BoneRotateImageBlock(){

    }



    public   float rotateX;
    public float rotateY;
    public float rotateZ;
    public float centerX=0.5f;
    public float centerY=0.5f;
    public float centerZ=0.5f;


    public BoneRotateImageBlock(float x, float y, float z){
        this.x =x;
        this.y=y;
        this.z=z;


    }
    public BoneRotateImageBlock(float x, float y, float z, float width, float height, float thick){
        this.x =x;
        this.y=y;
        this.z=z;
        this.width =width;
        this.height =height;
        this.thick =thick;

    }
    public BoneRotateImageBlock(float x, float y, float z, float width, float height, float thick, float rf, float gf, float bf, float opacity){
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
    
    
    public BoneRotateImageBlock copy(){
        BoneRotateImageBlock colorBlock  =new BoneRotateImageBlock();
        copyBaseBlock(colorBlock);
        copyImageBlock(colorBlock);
        copyRotateImageBlock(colorBlock);
        colorBlock.reComputePoints();
        return colorBlock;
    }
    
    public void copyRotateImageBlock(BoneRotateImageBlock block){
        block .rotateX= this.rotateX;
        block .rotateY= this.rotateY;
        block.centerY = this.centerY;
        block.centerX = this. centerX;
        block .centerZ = this. centerZ;
        block .rotateZ= this.rotateZ;
        copyBaseBlock(block);
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
        //要走到 不加x y z 会出现中心点无效的问题
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(x+centerX ,y+centerY ,z+centerZ);

        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(rotateX,rotateY,rotateZ);

        rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
        rotateMatrix =GL_Matrix.multiply(rotateMatrix,GL_Matrix.translateMatrix(-x-centerX ,-y-centerY ,-z-centerZ));
        for(int i=0;i<points.length;i++){
            points[i]=rotateMatrix.multiply(rotateMatrix ,points[i]);
        }

    }

    public  void reComputePoints(GL_Matrix rotateMatrix){
        this.points = BoxModel.getSmallPoint(x,y,z,width,height,thick);

        GL_Matrix rotateMatrix1 = GL_Matrix.rotateMatrix(0,this.dir*3.14f/2,0);
        for(int i=0;i<points.length;i++){
            points[i] = rotateMatrix.multiply(rotateMatrix,points[i]);

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

    @Override
    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    @Override
    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    @Override
    public void setCenterZ(float centerZ) {
        this.centerZ = centerZ;
    }

    public static BoneRotateImageBlock parse(JSONObject map){

        BoneRotateImageBlock block =new BoneRotateImageBlock();
        block.parseRotateImage(block,map);
        block.reComputePoints();
        return block;


    }

    public  void parseRotateImage(BoneRotateImageBlock block , JSONObject map){
        parseImage(block,map);

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

    public String toString(){
        StringBuffer buffer =new StringBuffer();
        buffer.append("{")
                .append("blocktype:'rotateimageblock',")
                .append(toBaseBlockString())
                .append(toImageBlockString())
                .append("centerX:").append(this.centerX).append(",")
                .append("centerY:").append(this.centerY).append(",")
                .append("centerZ:").append(this.centerZ).append(",")
                .append("rotateX:").append(this.rotateX).append(",")
                .append("rotateY:").append(this.rotateY).append(",")
                .append("rotateZ:").append(this.rotateZ).append(",")

                .append("}");

        return buffer.toString();
    }

}
