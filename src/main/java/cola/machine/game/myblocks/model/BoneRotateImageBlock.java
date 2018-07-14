package cola.machine.game.myblocks.model;


import cola.machine.game.myblocks.animation.Transform;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.edit.EditEngine;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import java.util.ArrayList;
import java.util.List;


public class BoneRotateImageBlock extends RotateImageBlock{
    public Transform transform =new Transform();
public GL_Vector parentPosition = new GL_Vector();
    public GL_Vector childPosition=new GL_Vector();
   public  BaseBlock block =null;
    public List<BoneRotateImageBlock > children =new ArrayList<>();
    public BoneRotateImageBlock(){

    }






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

        copyBoneRotateImageBlock(colorBlock);
        colorBlock.reComputePoints();
        return colorBlock;
    }
    
    public void copyBoneRotateImageBlock(BoneRotateImageBlock block){

        super.copyRotateImageBlock(block);
        block.parentPosition=this.parentPosition.copyClone();
        block.childPosition=this.childPosition.copyClone();
        for(BoneRotateImageBlock childBlock :children){
            block.children.add(childBlock.copy());
        }
    }

   
    public float rotateX(float value){
        if(value>1){
            LogUtil.println("hello");
        }
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

    public BoneRotateImageBlock findChild(String nodeName)  {
        if(this.name.equals(nodeName))
            return this;
        if(children.size()>0){
            for(int i=0;i<children.size();i++){
                if(children.get(i).name.equals(nodeName)){
                    return children.get(i);
                }else{
                    BoneRotateImageBlock child = (children.get(i)).findChild(nodeName);
                    if(child!=null){
                        return child;
                    }

                }


            }
        }

        return null;

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


//
//        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(x+centerX ,y+centerY ,z+centerZ);
//        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(rotateX,rotateY,rotateZ);
//
//        rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
//        rotateMatrix =GL_Matrix.multiply(rotateMatrix,GL_Matrix.translateMatrix(-centerX ,-centerY ,-centerZ));
//        for(int i=0;i<points.length;i++){
//            points[i]=rotateMatrix.multiply(rotateMatrix ,points[i]);
//        }

//
//        for (BaseBlock block : children) {
//            if (block instanceof BoneRotateImageBlock) {
//                BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock) block;
//                //先进行移动
//                 translateMatrix = GL_Matrix.translateMatrix(boneRotateImageBlock.parentPosition.x, boneRotateImageBlock.parentPosition.y, boneRotateImageBlock.parentPosition.z);
//
//
//                translateMatrix = GL_Matrix.multiply(rotateMatrix, translateMatrix);
//
//                //GL_Matrix rotateMatrix =GL_Matrix.multiply(translateMatrix,GL_Matrix.rotateMatrix(0,0,0));
//
//                 rotateMatrix = GL_Matrix.multiply(translateMatrix, GL_Matrix.rotateMatrix(boneRotateImageBlock.rotateX, boneRotateImageBlock.rotateY, boneRotateImageBlock.rotateZ));
//                translateMatrix = GL_Matrix.translateMatrix(-boneRotateImageBlock.childPosition.x, -boneRotateImageBlock.childPosition.y, -boneRotateImageBlock.childPosition.z);
////
////            if(this.name.equals("rhand")&& this.rotateX>0){
//////            LogUtil.println("hello");
////            }
//                rotateMatrix = GL_Matrix.multiply(rotateMatrix, translateMatrix);
//                block.reComputePoints(rotateMatrix);
//            } else {
//                block.reComputePoints(rotateMatrix);
//            }
//        }
    }
    @Override
    public void reComputePointsInGroup(){
        points= BoxModel.getSmallPoint(0,0,0,width,height,thick);
        //要走到 不加x y z 会出现中心点无效的问题
       /* GL_Matrix translateMatrix = GL_Matrix.translateMatrix(x+centerX ,y+centerY ,z+centerZ);

        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(rotateX,rotateY,rotateZ);

        rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
        rotateMatrix =GL_Matrix.multiply(rotateMatrix,GL_Matrix.translateMatrix(-x-centerX ,-y-centerY ,-z-centerZ));
        for(int i=0;i<points.length;i++){
            points[i]=rotateMatrix.multiply(rotateMatrix ,points[i]);
        }*/

    }

//    public  void reComputePoints(GL_Matrix rotateMatrix) {
//        this.points = BoxModel.getSmallPoint(0, 0, 0, width, height, thick);
//        for(int i=0;i<points.length;i++){
//            points[i] = rotateMatrix.multiply(rotateMatrix,points[i]);
//
//        }
//
//        for (int i = 0; i < points.length; i++) {
//            points[i] = matrix.multiply(matrix, points[i]);
//
//        }
//        this.x=points[0].x;
//        this.y=points[0].y;
//        this.z=points[0].z;
//        for (BaseBlock block : children) {
//            if (block instanceof BoneRotateImageBlock) {
//                BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock) block;
//                //先进行移动
//                GL_Matrix translateMatrix = GL_Matrix.translateMatrix(boneRotateImageBlock.parentPosition.x, boneRotateImageBlock.parentPosition.y, boneRotateImageBlock.parentPosition.z);
//
//
//                translateMatrix = GL_Matrix.multiply(matrix, translateMatrix);
//
//                //GL_Matrix rotateMatrix =GL_Matrix.multiply(translateMatrix,GL_Matrix.rotateMatrix(0,0,0));
//
//                GL_Matrix rotateMatrix = GL_Matrix.multiply(translateMatrix, GL_Matrix.rotateMatrix(boneRotateImageBlock.rotateX, boneRotateImageBlock.rotateY, boneRotateImageBlock.rotateZ));
//                translateMatrix = GL_Matrix.translateMatrix(-boneRotateImageBlock.childPosition.x, -boneRotateImageBlock.childPosition.y, -boneRotateImageBlock.childPosition.z);
////
////            if(this.name.equals("rhand")&& this.rotateX>0){
//////            LogUtil.println("hello");
////            }
//                rotateMatrix = GL_Matrix.multiply(rotateMatrix, translateMatrix);
//                block.reComputePoints(rotateMatrix);
//            } else {
//                block.reComputePoints(matrix);
//            }
//        }
   // }
    /**
     * 长长使用再在group中

     */
    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao,float parentX,float parentY,float parentZ, float childX,float childY,float childZ, float width, float height, float thick, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back){
        LogUtil.println("hello");
    }
    @Override
    public void renderShader(ShaderConfig config, Vao vao, GL_Matrix matrix){
        GL_Vector[] dirAry = BoxModel.dirAry;

        matrix.multiply(matrix,GL_Matrix.translateMatrix(x,y,z));

//        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(parentPosition.x-childPosition.x, parentPosition.y-childPosition.y
//                ,parentPosition.z -childPosition.z);
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(parentPosition.x, parentPosition.y
                ,parentPosition.z );

        translateMatrix= GL_Matrix.multiply(matrix,translateMatrix);

        //GL_Matrix rotateMatrix =GL_Matrix.multiply(translateMatrix,GL_Matrix.rotateMatrix(0,0,0));

        GL_Matrix rotateMatrix =GL_Matrix.multiply(translateMatrix,GL_Matrix.rotateMatrix( rotateX+transform.rotateX,
                rotateY+transform.rotateY, rotateZ+transform.rotateZ));
        translateMatrix = GL_Matrix.translateMatrix(-childPosition.x-transform.translateX,
                -childPosition.y-transform.translateY, -childPosition.z-transform.translateZ);
//

//            if(this.name.equals("rhand")&& this.rotateX>0){
////            LogUtil.println("hello");
//            }
        rotateMatrix= GL_Matrix.multiply(rotateMatrix,translateMatrix);
      //  rotateMatrix= GL_Matrix.multiply(GL_Matrix.scaleMatrix(transform.scaleX,transform.scaleY,transform.scaleZ),rotateMatrix);
        rotateMatrix= GL_Matrix.multiply(rotateMatrix,GL_Matrix.scaleMatrix(transform.scaleX,transform.scaleY,transform.scaleZ));
        if(transform.scaleX<0.5){
            LogUtil.println(transform.scaleX+"");
        }
        if(top!=null ) {
            ShaderUtils.draw3dImage(config, vao, rotateMatrix, points[4], points[5], points[6], points[7], dirAry[0], top);
        }

        if(bottom!=null) {
            ShaderUtils.draw3dImage(config, vao,rotateMatrix, points[3], points[2], points[1], points[0], dirAry[1], bottom);
        }

        if(front!=null ) {
            ShaderUtils.draw3dImage(config, vao,rotateMatrix,  points[0], points[1], points[5], points[4], dirAry[2], front);
        }

        if(back!=null ) {
            ShaderUtils.draw3dImage(config, vao, rotateMatrix,  points[2], points[3], points[7], points[6], dirAry[3], back);
        }

        if(left!=null ) {
            ShaderUtils.draw3dImage(config, vao,rotateMatrix,  points[3], points[0], points[4], points[7], dirAry[4], left);
        }
        if(right!=null )  {
            ShaderUtils.draw3dImage(config, vao,rotateMatrix, points[1], points[2], points[6], points[5], dirAry[5], right);
        }
        if(block!=null){
            block.renderShader(config,vao,rotateMatrix);
        }
        for(BaseBlock block:children){
            if(block instanceof  BoneRotateImageBlock){
                BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock) block;
                //先进行移动

                //boneRotateImageBlock.reComputePoints(rotateMatrix);
                block.renderShader( config,  vao,  rotateMatrix);
            }else{
                block.renderShader( config,  vao,  matrix);
            }

        }
    }

    public void renderShader2(ShaderConfig config, Vao vao, GL_Matrix matrix){
        GL_Vector[] dirAry = BoxModel.dirAry;

        matrix.multiply(matrix,GL_Matrix.translateMatrix(x,y,z));
        float newX= parentPosition.x-childPosition.x+transform.translateX;
        float newY= parentPosition.y-childPosition.y+transform.translateY;
        float newZ= parentPosition.z-childPosition.z+transform.translateZ;
        float newRotateX= rotateX+transform.rotateX;
        float newRotateY = rotateY+transform.rotateY;
        float newRotateZ = rotateZ+transform.rotateZ;


        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(newX, newY,newZ);


        translateMatrix= GL_Matrix.multiply(matrix,translateMatrix);


        GL_Matrix rotateMatrix =GL_Matrix.multiply(translateMatrix,GL_Matrix.rotateMatrix( newRotateX, newRotateY, newRotateZ));


        rotateMatrix= GL_Matrix.multiply(rotateMatrix,translateMatrix);

        if(transform.scaleX!=null){
            rotateMatrix= GL_Matrix.multiply(GL_Matrix.scaleMatrix(transform.scaleX,transform.scaleY,transform.scaleZ),translateMatrix);
        }

        if(top!=null ) {
            ShaderUtils.draw3dImage(config, vao, rotateMatrix, points[4], points[5], points[6], points[7], dirAry[0], top);
        }

        if(bottom!=null) {
            ShaderUtils.draw3dImage(config, vao,rotateMatrix, points[3], points[2], points[1], points[0], dirAry[1], bottom);
        }

        if(front!=null ) {
            ShaderUtils.draw3dImage(config, vao,rotateMatrix,  points[0], points[1], points[5], points[4], dirAry[2], front);
        }

        if(back!=null ) {
            ShaderUtils.draw3dImage(config, vao, rotateMatrix,  points[2], points[3], points[7], points[6], dirAry[3], back);
        }

        if(left!=null ) {
            ShaderUtils.draw3dImage(config, vao,rotateMatrix,  points[3], points[0], points[4], points[7], dirAry[4], left);
        }
        if(right!=null )  {
            ShaderUtils.draw3dImage(config, vao,rotateMatrix, points[1], points[2], points[6], points[5], dirAry[5], right);
        }
        if(block!=null){
            block.renderShader(config,vao,rotateMatrix);
        }
        for(BaseBlock block:children){
            if(block instanceof  BoneRotateImageBlock){
                BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock) block;
                //先进行移动

                //boneRotateImageBlock.reComputePoints(rotateMatrix);
                block.renderShader( config,  vao,  rotateMatrix);
            }else{
                block.renderShader( config,  vao,  matrix);
            }

        }
    }
//    @Override
//    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, GL_Matrix matrix, GL_Vector[] childPoints) {
//        GL_Vector[] dirAry = BoxModel.dirAry;
//
//        matrix.multiply(matrix,GL_Matrix.translateMatrix(x,y,z));
//        if(top!=null ) {
//            ShaderUtils.draw3dImage(config, vao, matrix, points[4], points[5], points[6], points[7], dirAry[0], top);
//        }
//
//        if(bottom!=null) {
//            ShaderUtils.draw3dImage(config, vao,matrix, points[3], points[2], points[1], points[0], dirAry[1], bottom);
//        }
//
//        if(front!=null ) {
//            ShaderUtils.draw3dImage(config, vao,matrix,  points[0], points[1], points[5], points[4], dirAry[2], front);
//        }
//
//        if(back!=null ) {
//            ShaderUtils.draw3dImage(config, vao, matrix,  points[2], points[3], points[7], points[6], dirAry[3], back);
//        }
//
//        if(left!=null ) {
//            ShaderUtils.draw3dImage(config, vao,matrix,  points[3], points[0], points[4], points[7], dirAry[4], left);
//        }
//        if(right!=null )  {
//            ShaderUtils.draw3dImage(config, vao,matrix, points[1], points[2], points[6], points[5], dirAry[5], right);
//        }
//
//        for(BaseBlock block:children){
//            if(block instanceof  BoneRotateImageBlock){
//                BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock) block;
//                //先进行移动
//                GL_Matrix translateMatrix = GL_Matrix.translateMatrix(boneRotateImageBlock.parentPosition.x, boneRotateImageBlock.parentPosition.y,boneRotateImageBlock.parentPosition.z);
//
//
//                translateMatrix= GL_Matrix.multiply(matrix,translateMatrix);
//
//                //GL_Matrix rotateMatrix =GL_Matrix.multiply(translateMatrix,GL_Matrix.rotateMatrix(0,0,0));
//
//             GL_Matrix rotateMatrix =GL_Matrix.multiply(translateMatrix,GL_Matrix.rotateMatrix( boneRotateImageBlock.rotateX, boneRotateImageBlock.rotateY, boneRotateImageBlock.rotateZ));
//                translateMatrix = GL_Matrix.translateMatrix(-boneRotateImageBlock.childPosition.x, -boneRotateImageBlock.childPosition.y, -boneRotateImageBlock.childPosition.z);
////
////            if(this.name.equals("rhand")&& this.rotateX>0){
//////            LogUtil.println("hello");
////            }
//                rotateMatrix= GL_Matrix.multiply(rotateMatrix,translateMatrix);
//                boneRotateImageBlock.reComputePoints(rotateMatrix);
//                block.renderShader( config,  vao,  rotateMatrix);
//            }else{
//                block.renderShader( config,  vao,  matrix);
//            }
//
//        }
//    }
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
        block.parseBoneRotateImage(block, map);
        block.reComputePoints();
        return block;


    }

    public  void parseBoneRotateImage(BoneRotateImageBlock block , JSONObject map){
        parseRotateImage(block, map);
        String parentPos = MapUtil.getStringValue(map,"parentPos");
        if(StringUtil.isNotEmpty(parentPos)){
            String[] arg = parentPos.split(",");
            parentPosition.x = Float.valueOf(arg[0]);
            parentPosition.y = Float.valueOf(arg[1]);
            parentPosition.z = Float.valueOf(arg[2]);
        }
        String childPos = MapUtil.getStringValue(map,"childPos");
        if(StringUtil.isNotEmpty(childPos)){
            String[] arg = childPos.split(",");
            childPosition.x = Float.valueOf(arg[0]);
            childPosition.y = Float.valueOf(arg[1]);
            childPosition.z = Float.valueOf(arg[2]);
        }



        JSONArray ary = (JSONArray) map.get("children");
        if (ary != null) {
            for (int i = 0; i < ary.size(); i++) {
                JSONObject object = (JSONObject) ary.get(i);
               BaseBlock childBlock =  EditEngine.parse(object);
                if(childBlock instanceof  BoneRotateImageBlock){
                    block.addChild((BoneRotateImageBlock)childBlock );
                }else{
                    block.block = childBlock;
                }

//                String blockType = (String) object.get("blocktype");
//                if ("imageblock".equals(blockType)) {
//                    ImageBlock imageBlock = ImageBlock.parse(object);
//                    block.addChild(imageBlock);
//                } else if ("colorblock".equals(blockType)) {
//                    ColorBlock colorBlock = ColorBlock.parse(object);
//                    block.addChild(colorBlock);
//                } else if ("rotatecolorblock".equals(blockType)) {
//                    RotateColorBlock2 shape = RotateColorBlock2.parse(object);
//                    block.addChild(shape);
//                } else if ("rotateimageblock".equals(blockType)) {
//                    RotateImageBlock shape = RotateImageBlock.parse(object);
//                    block.addChild(shape);
//                }else if ("bonerotateimageblock".equals(blockType)) {
//                    BoneRotateImageBlock shape = BoneRotateImageBlock.parse(object);
//                    block.addChild(shape);
//                }


            }
        }


    }

    public void removeChild(BoneRotateImageBlock component){
        for(int i=children.size()-1;i>=0;i--){
            BaseBlock child =  children.get(i);
            if(child == component){
                children.remove(i);
            }

        }
    }

    public String toString(){
        StringBuffer buffer =new StringBuffer();
        buffer.append("{")
                .append("blocktype:'bonerotateimageblock',")

.append(toBoneRotateImageBlock())
                .append("}");
        return buffer.toString();
    }
    public String toBoneRotateImageBlock(){
        StringBuffer buffer =new StringBuffer();
        buffer.append(toRotateImageBlockString());
        buffer.append("parentPos:'").append(parentPosition.x).append(",")
                .append(parentPosition.y).append(",")
                .append(parentPosition.z).append("',")

                .append("childPos:'").append(childPosition.x).append(",")
                .append(childPosition.y).append(",")
                .append(childPosition.z).append("',")
                .append("children:[");
        int index = 0 ;

        for(BaseBlock baseBlock:children){
            if(index>0){
                buffer.append(",");
            }
            buffer.append(baseBlock.toString());
            index++;
        }
        buffer .append("],");
return buffer.toString();
    }
    public void addChild(BoneRotateImageBlock block){
        this.children.add(block);
    }

}
