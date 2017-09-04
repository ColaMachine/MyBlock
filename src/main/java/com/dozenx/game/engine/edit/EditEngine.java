package com.dozenx.game.engine.edit;

import cola.machine.game.myblocks.animation.Animation;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.*;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkConstants;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import com.alibaba.fastjson.JSON;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ChunkResponseCmd;
import com.dozenx.game.engine.command.SayCmd;
import com.dozenx.game.engine.edit.view.AnimationBlock;
import com.dozenx.game.engine.edit.view.GroupBlock;
import com.dozenx.game.engine.item.action.ItemFactory;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.FileUtil;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/8/5.
 */
public class EditEngine {

    public List<BaseBlock> colorBlockList  =new ArrayList<>();
    public List<BaseBlock> selectBlockList  =new ArrayList<>();
    public float prevX ;
    public float prevY;
    public float nowX;
    public float nowY;



    ColorBlock mouseOvercolorBlock =null;
    int curentX = 0;
    int curentZ =0;

    public int prevFaceX = 0;
    public int prevFaceZ =0;
    public int prevMaxFaceX = 0;
    public int prevMaxFaceZ = 0;


    public int lastFaceX=0;
    public int lastFaceZ=0;
    public int lastFaceMaxX=0;
    public int lastFaceMaxZ=0;
    /**
     * 当前编辑的组件库
     */
    GroupBlock currentChoosedGroupForEdit =null;
    public float red ,green,blue,alpha=1;

    /**
     * 在组件库里选中的对象
     */
    //public ColorGroup chooseColorGroup = null;
    HashMap<String,BaseBlock > colorGroupHashMap =new HashMap<>();

    int minX,minZ;
    public GL_Vector startPoint;
    public GL_Vector endPoint;


    public void update(){
       /* for(int i =0;i<groups.size();i++){
            groups.get(i).update();
        }*/
        //绘制当前的画布顶平面的网格
        for(int i=-15;i<15;i++){

            ShaderUtils.drawLine(new GL_Vector(GamingState.instance.player.getX()+i,0,GamingState.instance.player.getZ()-15),new GL_Vector(GamingState.instance.player.getX()+i,0,GamingState.instance.player.getZ()+15));
            ShaderUtils.drawLine(new GL_Vector(GamingState.instance.player.getX()-15,0,GamingState.instance.player.getZ()+i),new GL_Vector(GamingState.instance.player.getX()+15,0,GamingState.instance.player.getZ()+i));

        }

        ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),0,0,0,new GL_Vector(0.3f,0.3f,0.3f),100,0,100,0.2f);

        for(int i=0;i<selectBlockList.size();i++){
            BaseBlock colorBlock = selectBlockList.get(i);

            ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig,ShaderManager.lineShaderConfig.getVao(),colorBlock.x,colorBlock.y,colorBlock.z,colorBlock.width,colorBlock.height,colorBlock.thick);

        }

        for(int i=0;i<colorBlockList.size();i++){
            BaseBlock colorBlock = colorBlockList.get(i);
            GL_Matrix matrx = GL_Matrix.translateMatrix(colorBlock.x, colorBlock.y, colorBlock.z);
            colorBlock.renderShaderInGivexyzwht(ShaderManager.terrainShaderConfig,ShaderManager.anotherShaderConfig.getVao(), matrx, colorBlock.points);
            // colorBlock.render(ShaderManager.terrainShaderConfig,ShaderManager.anotherShaderConfig.getVao(),colorBlock.x,colorBlock.y,colorBlock.z,true,true,true,true,true,true);
        }
        if(startPoint!=null){

            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),startPoint.x,startPoint.y,startPoint.z,new GL_Vector(1,0,0),0.3f,0.3f,0.3f,1);

            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),endPoint.x,endPoint.y,endPoint.z,new GL_Vector(0,0,1),0.3f,0.3f,0.3f,1);


            ShaderUtils.drawLine( startPoint,endPoint);
          //  ShaderUtils.drawLine( startPoint,endPoint);
        }

        ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),curentX,0,curentZ,new GL_Vector(1,1,1),1,0.2f,1,1f);

        if(Switcher.mouseState==Switcher.faceSelectMode) {
            minX = Math.min(prevFaceX, lastFaceX);
            minZ = Math.min(prevFaceZ, lastFaceZ);
            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(), Math.min(prevFaceX, lastFaceX), 0, Math.min(prevFaceZ, lastFaceZ), new GL_Vector(1, 1, 1), Math.max(lastFaceMaxX, prevMaxFaceX) - minX, 0.2f, Math.max(lastFaceMaxZ, prevMaxFaceZ) - minZ, 1f);
        }
        //绘制
    }


    /**
     * 通过选中的视角来选取方块
     * @param from
     * @param direction
     * @return
     */
   /* public ColorBlock getSelectBlock(GL_Vector from, GL_Vector direction ){
        direction=direction.normalize();
        //  startPoint =GamingState.instance.player.getPosition().copyClone();
       *//* startPoint =GamingState.instance.camera.Position.copyClone();
        endPoint= startPoint.copyClone().add(direction.copyClone().mult(12));*//*
        // LogUtil.println("开始选择");
        Vector3f fromV= new Vector3f(from.x,from.y,from.z);
        Vector3f directionV= new Vector3f(direction.x,direction.y,direction.z);
        float distance =0;
        ColorBlock theNearestBlock = null;
        float[] xiangjiao=null;
        for(int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock =  colorBlockList.get(i);
            AABB aabb = new AABB(new Vector3f(colorBlock.x,colorBlock.y,colorBlock.z),new Vector3f(colorBlock.x+colorBlock.width,colorBlock.y+colorBlock.height,colorBlock.z+colorBlock.thick));

            // LogUtil.println(fromV.toString() );
            // LogUtil.println(directionV.toString() );
            if( (xiangjiao=aabb.intersectRectangle2(fromV,directionV))!=null){//这里进行了按照list的顺序进行选择 其实应该按照距离的最近选择

                //计算点在了那个面上
                //有上下左右前后6个面
                // LogUtil.println("选中了");
                float _tempDistance = xiangjiao[0]* xiangjiao[0]+ xiangjiao[1]* xiangjiao[1]+ xiangjiao[2]* xiangjiao[2];

                if(distance ==0||_tempDistance<distance){
                    distance=_tempDistance;
                    theNearestBlock=colorBlock;
                }
                //GL_Vector.chuizhijuli(GL_Vector.sub(livingThing.position,from),direction)<3){
                //   LogUtil.println("选中了");
                //this.target=livingThing;
                //colorBlock.selected=true;


            }


        }
        return theNearestBlock;
    }*/
    //在没有圈框的时候 进行点射选择 在另一个selectObject中被调用

    /**
     *调用getSelectBlock 来选择方块
     * @param x
     * @param y
     * @return
     */

        public BaseBlock  selectSingle(float x,float y){
            Object[] results= this.getMouseChoosedBlock(x,y);
            BaseBlock theNearestBlock = (BaseBlock)results[0];

            if(theNearestBlock!=null){

                selectBlockList.add(theNearestBlock);

              /*  if(selectBlockList.size()>0 && selectBlockList.get(0) instanceof  ColorGroup){
                    ColorGroup colorGroup =(ColorGroup)selectBlockList.get(0);
                    ColorBlock colorBlock= colorGroup.selectSingle(from,direction);
                    if(colorBlock!=null){
                        return null;
                    }
                }*/

                return theNearestBlock;
            }


            return theNearestBlock;
        }




    public BaseBlock getSelectFirstBlock(){
        if(selectBlockList.size()>0){
            return selectBlockList.get(0);
        }
        return null;
    }
    public void enterComponentEdit(){


        BaseBlock colorBlock = getSelectFirstBlock();

        if(colorBlock!=null &&( colorBlock instanceof AnimationBlock ||  colorBlock instanceof GroupBlock
        )){
            Switcher.isEditComponent =true;
            currentChoosedGroupForEdit = (GroupBlock)colorBlock;
        }else{
            Switcher.isEditComponent =false;
            currentChoosedGroupForEdit=null;
        }
    }

    //选择区域
    public void selectMany(float minX,float minY,float maxX,float maxY,boolean clearbefore){

        if(Switcher.isEditComponent){
            currentChoosedGroupForEdit.selectMany( minX, minY, maxX, maxY, clearbefore);
            return ;
            //只在当前组件内编辑
        }
        //遍历所有的方块查找所有的在方块里的方块
        //取出
        if(maxX<minX){
            maxX =minX;
        }
        if(maxY<minY){
            maxY=minY;
        }
        if(clearbefore)
        selectBlockList.clear();
        for( int i=0;i<colorBlockList.size();i++){
            BaseBlock colorBlock  =  colorBlockList.get(i);
          /*  GL_Vector[] gl_vectors = BoxModel.getSmaillPoint(colorBlock.x,colorBlock.y,colorBlock.z,colorBlock.width, colorBlock.height, colorBlock.thick);
           for(){

           }*/

            Vector2f xy = OpenglUtils.wordPositionToXY(ShaderManager.projection,new GL_Vector(colorBlock.x,colorBlock.y,colorBlock.z), GamingState.instance.camera.Position, GamingState.instance.camera.getViewDir());
            xy.x *= Constants.WINDOW_WIDTH;
            xy.y*=Constants.WINDOW_HEIGHT;
            if(xy.x>minX && xy.x <maxX &&
            xy.y>minY  && xy.y <maxY
            ){
                selectBlockList.add(colorBlock);
             //   colorBlock.setSelected(true);
            }else{
              //  colorBlock.setSelected(false);
            }
        }

        if(!clearbefore || selectBlockList.size()==0){
           selectSingle(minX,minY);
        }
    }

    //选择区域
 //   @Deprecated
  /*  public void selectObjects(float minX,float minY,float maxX,float maxY){
        //遍历所有的方块查找所有的在方块里的方块
        //取出
        selectBlockList.clear();
        for( int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock  =  colorBlockList.get(i);
            Vector2f xy = OpenglUtils.wordPositionToXY(ShaderManager.projection,new GL_Vector(colorBlock.x,colorBlock.y,colorBlock.z), GamingState.instance.camera.Position, GamingState.instance.camera.getViewDir());
            xy.x *= Constants.WINDOW_WIDTH;
            xy.y*=Constants.WINDOW_HEIGHT;
            if(xy.x>minX && xy.x <maxX &&
                    xy.y>minY  && xy.y <maxY
                    ){
                selectBlockList.add(colorBlock);
               // colorBlock.setSelected(true);
            }else{
              //  colorBlock.setSelected(false);
            }
        }
    }
*/

    public  void deleteSelect(){
       /* for( int i=0;i<selectBlockList.size();i++){
            ColorBlock colorBlock  =  selectBlockList.get(i);
            colorBlockList.remove(colorBlock);
        }*/
        if(Switcher.isEditComponent){
            currentChoosedGroupForEdit.deleteSelect();
        }else {
            for (int i = selectBlockList.size() - 1; i >= 0; i--) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlockList.remove(colorBlock);

            }
            selectBlockList.clear();
        }
    }

    public void addBlock(){

        // Color commonColor =new Color( color.getRed() ,color.getGreen(),color.getBlue());



        minX = Math.min(prevFaceX,lastFaceX);
        minZ = Math.min(prevFaceZ,lastFaceZ);
        int width = Math.max(lastFaceMaxX,prevMaxFaceX)-minX;
        int thick = Math.max(lastFaceMaxZ,prevMaxFaceZ)-minZ;

        BaseBlock colorBlock =null;
        colorBlock =  readyShootBlock.copy();
        for(int x =minX;x< width+minX;x++){
            for(int z=minZ;z<thick+minZ;z++){
                colorBlock =  readyShootBlock.copy();
                //set(colorBlock,x,0,z,Math.max(width,1),1,Math.max(thick,1),red,green,blue,alpha);
                set(colorBlock,x,0,z,1,1,1,red,green,blue,alpha);
                colorBlockList.add(colorBlock);
            }
        }
        //set(colorBlock,minX,0,minZ,Math.max(width,1),1,Math.max(thick,1),red,green,blue,alpha);

      /*  if(chooseColorGroup!=null){
            colorBlock= chooseColorGroup.copy();
            colorBlock.x=minX;
            colorBlock.y =0;
            colorBlock.z=minZ;
        }else{
            colorBlock=new ColorBlock(minX,0,minZ);
            colorBlock.rf = red;
            colorBlock.gf = green;
            colorBlock.bf =blue;
            colorBlock.width =Math.max(width,1);
            colorBlock.height=1;
            colorBlock.thick  =Math.max(thick,1);
        }*/

        /*for(int x=minX;x<width;x++){
            for(int z=minZ;z<thick;z++){

            }
        }*/


        GamingState.editEngine.select(colorBlock);
    }
    public void set(BaseBlock block, int x, int y, int z, float width, float height, float thick, float red, float green , float blue, float alpha){
        block.x=x;
        block.y =y;
        block.z=z;

        block.width =Math.max(width,1);
        block.height=height;
        block.thick  =Math.max(thick,1);
        if(block instanceof  ColorBlock){
            ColorBlock colorBlock = (ColorBlock) block;
            colorBlock.rf = red;
            colorBlock.gf = green;
            colorBlock.bf =blue;

            colorBlock.opacity=alpha;
        }

    }
    public void select(BaseBlock block){

        selectBlockList.clear();
        selectBlockList.add(block);
    }

    public float adjustWidth(float num,boolean position){
        if(Switcher.size&&position) {
            for( int i=0;i<selectBlockList.size();i++){
                BaseBlock colorBlock  =  selectBlockList.get(i);

                return colorBlock.addWidth(num);//(false);

            }}else{
            for( int i=0;i<selectBlockList.size();i++){
                BaseBlock colorBlock  =  selectBlockList.get(i);

               return  colorBlock.addX(num);//(false);

            }
        }
        return 0;
    }
    public float adjustHeight(float num,boolean position){
        if(Switcher.size && position) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

               return colorBlock.addHeight(num);//(false);

            }
        }else{
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                return colorBlock.addY(num) ;//(false);

            }
        }
        return 0;
    }

    public float adjustThick(float num,boolean position){
        if(Switcher.size&&position){
            for( int i=0;i<selectBlockList.size();i++){
                BaseBlock colorBlock  =  selectBlockList.get(i);

                return colorBlock.addThick( num);//(false);

            }
        }else{
            for( int i=0;i<selectBlockList.size();i++){
                BaseBlock colorBlock  =  selectBlockList.get(i);

                return colorBlock.addZ( num);//(false);

            }
        }
        return 0;

    }

    public void copySelect(){
        for( int i=selectBlockList.size()-1;i>=0;i--){
            BaseBlock colorBlock  =  selectBlockList.get(i).copy();
         /*   ColorBlock copyBlock =new ColorBlock(colorBlock.x,colorBlock.y,colorBlock.z);
            copyBlock.rf = colorBlock.rf;
            copyBlock.gf =colorBlock.gf;
            copyBlock.bf = colorBlock.bf;
            copyBlock.width = colorBlock.width;
            copyBlock.height =colorBlock.height;
            copyBlock.thick =colorBlock.thick;*/
            //colorBlock.selected=false;
            colorBlockList.add(colorBlock);
            selectBlockList.set(i,colorBlock);
          //  copyBlock.selected=true;

        }
    }

    public void setColor(float red,float green ,float blue,float alpha) {
        this.red = red;
        this.green= green;
        this.blue =blue;
        this.alpha =alpha;
        if(currentChoosedGroupForEdit!=null){
            currentChoosedGroupForEdit.setColor(red,green,blue,alpha);
            return;
        }

        for( int i=selectBlockList.size()-1;i>=0;i--){
            BaseBlock colorBlock  =  selectBlockList.get(i);
            if(colorBlock instanceof  ColorBlock){
                ColorBlock colorBlock1 =(ColorBlock ) colorBlock;
                colorBlock1.rf = red;
                colorBlock1.gf =green;
                colorBlock1.bf = blue;
                colorBlock1.opacity = alpha;
            }


        }
    }

    public void saveWork(){
        StringBuffer stringBuffer =new StringBuffer();
        try {
            //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
            for(BaseBlock colorBlock : colorBlockList){
               // outputStream .write();
                stringBuffer.append(colorBlock.toString()).append("\n");
            }
            FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile(),stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reloadWork(){
        try {
           List<String> list =  FileUtil.readFile2List(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
            for(String s :list){
                String[] ary = s.split(",");
                ColorBlock colorBlock =new ColorBlock(Integer.valueOf(ary[0]),Integer.valueOf(ary[1]),Integer.valueOf(ary[2]));
                colorBlock .width= Float.valueOf(ary[3]);
                colorBlock .height= Float.valueOf(ary[4]);
                colorBlock .thick= Float.valueOf(ary[5]);
                colorBlock.rf = Float.valueOf(ary[6]);
                colorBlock.gf = Float.valueOf(ary[7]);
                colorBlock.bf = Float.valueOf(ary[8]);
                colorBlockList.add(colorBlock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void seperateSelect(){
        if(selectBlockList.size()==0){
            LogUtil.println("未选中任何东西");
            return;
        }
        BaseBlock colorBlock = selectBlockList.get(0);

        for(int x=0;x<colorBlock.width;x++){
            for(int y =0;y<colorBlock.height;y++){
                for(int z =0;z<colorBlock.thick;z++){

                    BaseBlock  childBlock = colorBlock.copy();
                    childBlock.x  = colorBlock.x+x;
                    childBlock.y  = colorBlock.y+y;
                    childBlock.z  = colorBlock.z+z;

                    childBlock.width=1;
                    childBlock.height=1;
                    childBlock.thick=1;




                    colorBlockList.add(childBlock);
                }
            }
        }
        selectBlockList.clear();
        colorBlockList.remove(colorBlock);

    }

    public void brushBlock(float x,float y){
       if(currentChoosedGroupForEdit!=null){//内部绘制
           currentChoosedGroupForEdit.brushBlock( x, y,red,green,blue);
           return;
       }
        Object[] result =this.getMouseChoosedBlock(x,y);

        ColorBlock block =(ColorBlock) result[0];
        if(block == null)
            return;
        block.rf = red;
        block.gf = green;
        block.bf = blue;
    }
//,float red,float green ,float blue
    public void shootBlock(float x,float y){

        if(Switcher.isEditComponent){
            currentChoosedGroupForEdit.shootBlock(x,y);
            return;


        }
        Object[] results = this.getMouseChoosedBlock(x,y);
        BaseBlock theNearestBlock = (BaseBlock)results[0];
        GL_Vector touchPoint = (GL_Vector) results[2];
        GL_Vector placePoint = (GL_Vector) results[3];
        endPoint=touchPoint;
        startPoint =placePoint;
        int face = (int)results[1];
        if(theNearestBlock!=null){
            BaseBlock addColorBlock =null;

                addColorBlock = readyShootBlock.copy();//new RotateColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            set(addColorBlock,(int) (placePoint.x), (int) (placePoint.y ), (int) (placePoint.z ),1,1,1,red,green,blue,alpha);
         /*   }else{
                addColorBlock = new ColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            }*/


            colorBlockList.add(addColorBlock);


            LogUtil.println("是那个面:"+face);

        }else{//射在地面上
            BaseBlock addColorBlock =null;


            addColorBlock = readyShootBlock.copy();//new RotateColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            set(addColorBlock,curentX, 0, curentZ,addColorBlock.width,addColorBlock.height,addColorBlock.thick,red,green,blue,alpha);

            this.colorBlockList.add(addColorBlock);

        }
    }
/*
    public void brushBlock(float x,float y){
        if(currentChoosedGroupForEdit!=null){//内部绘制
            currentChoosedGroupForEdit.brushBlock( x, y,red,green,blue);
            return;
        }
        ColorBlock block = this.getSelectBlock(GamingState.instance.camera.Position.copyClone(), OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(),x,Constants.WINDOW_HEIGHT-y));
        if(block == null)
            return;
        block.rf = red;
        block.gf = green;
        block.bf = blue;
    }*/
    //,float red,float green ,float blue

    public  Object[] getMouseChoosedBlock(float x,float y){

        GL_Vector from = GamingState.instance.camera.Position.copyClone();
       GL_Vector direction =OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(),x,Constants.WINDOW_HEIGHT-y);
        direction=direction.normalize();

        // LogUtil.println("开始选择");
        Vector3f fromV= new Vector3f(from.x,from.y,from.z);
        Vector3f directionV= new Vector3f(direction.x,direction.y,direction.z);
        float distance =0;
        BaseBlock theNearestBlock = null;
        float[] xiangjiao=null;
        float[] right=null;
        for(int i=0;i<colorBlockList.size();i++){
            BaseBlock colorBlock =  colorBlockList.get(i);
            AABB aabb = new AABB(new Vector3f(colorBlock.x,colorBlock.y,colorBlock.z),new Vector3f(colorBlock.x+colorBlock.width,colorBlock.y+colorBlock.height,colorBlock.z+colorBlock.thick));

            // LogUtil.println(fromV.toString() );
            // LogUtil.println(directionV.toString() );
            if( (xiangjiao=aabb.intersectRectangle2(fromV,directionV))!=null){//这里进行了按照list的顺序进行选择 其实应该按照距离的最近选择

                //计算点在了那个面上
                //有上下左右前后6个面
                LogUtil.println("选中了");
                float _tempDistance = xiangjiao[0]* xiangjiao[0]+ xiangjiao[1]* xiangjiao[1]+ xiangjiao[2]* xiangjiao[2];

                if(distance ==0||_tempDistance<distance){
                    distance=_tempDistance;
                    theNearestBlock=colorBlock;
                    right = xiangjiao;
                }




            }


        }

        int face =-1;
        GL_Vector touchPoint =new GL_Vector();
        GL_Vector  placePoint =new GL_Vector();
        if(theNearestBlock!=null ) {
            //计算是那一面



            float[] ary = AABB.xyFaces[(int) right[3] - 1];
            float[] ary1 = AABB.xzFaces[(int) right[4] - 1];
            float[] ary2 = AABB.yzFaces[(int) right[5] - 1];

            for (int i = 0; i < ary.length; i++) {
                for (int j = 0; j < ary.length; j++) {
                    if (ary[i] == ary1[j]) {//在xz的集合当中也有


                        for (int k = 0; k < ary.length; k++) {
                            //在yz的集合当中也有
                            if (ary[i] == ary2[k]) {
                                face = (int) ary[i];

                                placePoint.x=touchPoint.x=(from.x + right[0]);
                                placePoint.y=touchPoint.y= (from.y + right[1]);

                                placePoint.z=touchPoint.z = (from.z + right[2]);

                           /*     if (face == Constants.BACK ) {
                                    placePoint.z -=1;
                                } else if (face == Constants.LEFT) {
                                    placePoint.x -= 1;
                                } else if (face == Constants.BOTTOM) {
                                    placePoint.y -= 1;
                                }*/

                                if (face == Constants.BACK  ||  face == Constants.FRONT) {
                                    float fangdabeishu = (touchPoint.z-from.z)/direction.z;
                                    touchPoint.x= (from.x+direction.x*fangdabeishu);
                                    touchPoint.y= (from.y+direction.y*fangdabeishu);
                                } else if (face == Constants.LEFT || face == Constants.RIGHT) {
                                    float fangdabeishu = (touchPoint.x-from.x)/direction.x;
                                    touchPoint.y= (from.y+direction.y*fangdabeishu);
                                    touchPoint.z= (from.z+direction.z*fangdabeishu);
                                } else if (face == Constants.BOTTOM || face == Constants.TOP ) {
                                    float fangdabeishu = (touchPoint.y-from.y)/direction.y;
                                    touchPoint.x= (from.x+direction.x*fangdabeishu);
                                    touchPoint.z= (from.z+direction.z*fangdabeishu);
                                }
                                placePoint.x=touchPoint.x;
                                placePoint.y=touchPoint.y;

                                placePoint.z=touchPoint.z ;
                                    if (face == Constants.BACK ) {
                                    placePoint.z -=1;
                                } else if (face == Constants.LEFT) {
                                    placePoint.x -= 1;
                                } else if (face == Constants.BOTTOM) {
                                    placePoint.y -= 1;
                                }
                                //调整
                                /*if (face == Constants.BACK) {
                                    //addColorBlock.z=theNearestBlock.z-1;
                                    if(addColorBlock.x>theNearestBlock.x +(int)theNearestBlock.width-1){
                                        addColorBlock.x=theNearestBlock.x +(int)theNearestBlock.width-1;
                                    }
                                    if(addColorBlock.x<theNearestBlock.x){
                                        addColorBlock.x=theNearestBlock.x ;
                                    }

                                    if(addColorBlock.y>theNearestBlock.y +(int)theNearestBlock.height-1){
                                        addColorBlock.y=theNearestBlock.y +(int)theNearestBlock.height-1;
                                    }
                                    if(addColorBlock.y<theNearestBlock.y){
                                        addColorBlock.y=theNearestBlock.y ;
                                    }



                                } else  if (face == Constants.FRONT) {
                                    //addColorBlock.z=theNearestBlock.z+(int)theNearestBlock.thick+1;
                                    if(addColorBlock.x>theNearestBlock.x +(int)theNearestBlock.width-1){
                                        addColorBlock.x=theNearestBlock.x +(int)theNearestBlock.width-1;
                                    }
                                    if(addColorBlock.x<theNearestBlock.x){
                                        addColorBlock.x=theNearestBlock.x ;
                                    }

                                    if(addColorBlock.y>theNearestBlock.y +(int)theNearestBlock.height-1){
                                        addColorBlock.y=theNearestBlock.y +(int)theNearestBlock.height-1;
                                    }
                                    if(addColorBlock.y<theNearestBlock.y){
                                        addColorBlock.y=theNearestBlock.y ;
                                    }



                                } else if (face == Constants.LEFT) {



                                    if(addColorBlock.y>theNearestBlock.y +(int)theNearestBlock.height-1){
                                        addColorBlock.y=theNearestBlock.y +(int)theNearestBlock.height-1;
                                    }
                                    if(addColorBlock.y<theNearestBlock.y){
                                        addColorBlock.y=theNearestBlock.y ;
                                    }

                                    if(addColorBlock.z>theNearestBlock.z +(int)theNearestBlock.thick-1){
                                        addColorBlock.z=theNearestBlock.z +(int)theNearestBlock.thick-1;
                                    }
                                    if(addColorBlock.z<theNearestBlock.z){
                                        addColorBlock.z=theNearestBlock.z ;
                                    }
                                } else if (face == Constants.RIGHT) {




                                    if(addColorBlock.y>theNearestBlock.y +(int)theNearestBlock.height-1){
                                        addColorBlock.y=theNearestBlock.y +(int)theNearestBlock.height-1;
                                    }
                                    if(addColorBlock.y<theNearestBlock.y){
                                        addColorBlock.y=theNearestBlock.y ;
                                    }

                                    if(addColorBlock.z>theNearestBlock.z +(int)theNearestBlock.thick-1){
                                        addColorBlock.z=theNearestBlock.z +(int)theNearestBlock.thick-1;
                                    }
                                    if(addColorBlock.z<theNearestBlock.z){
                                        addColorBlock.z=theNearestBlock.z ;
                                    }
                                } else if (face == Constants.BOTTOM) {
                                    //addColorBlock.y=theNearestBlock.y-1;

                                    if(addColorBlock.x>theNearestBlock.x +(int)theNearestBlock.width-1){
                                        addColorBlock.x=theNearestBlock.x +(int)theNearestBlock.width-1;
                                    }
                                    if(addColorBlock.x<theNearestBlock.x){
                                        addColorBlock.x=theNearestBlock.x ;
                                    }


                                    if(addColorBlock.z>theNearestBlock.z +(int)theNearestBlock.thick-1){
                                        addColorBlock.z=theNearestBlock.z +(int)theNearestBlock.thick-1;
                                    }
                                    if(addColorBlock.z<theNearestBlock.z){
                                        addColorBlock.z=theNearestBlock.z ;
                                    }
                                }else if (face == Constants.TOP) {
                                    //addColorBlock.y=theNearestBlock.y+(int)theNearestBlock.height+1;

                                    if(addColorBlock.x>theNearestBlock.x +(int)theNearestBlock.width-1){
                                        addColorBlock.x=theNearestBlock.x +(int)theNearestBlock.width-1;
                                    }
                                    if(addColorBlock.x<theNearestBlock.x){
                                        addColorBlock.x=theNearestBlock.x ;
                                    }


                                    if(addColorBlock.z>theNearestBlock.z +(int)theNearestBlock.thick-1){
                                        addColorBlock.z=theNearestBlock.z +(int)theNearestBlock.thick-1;
                                    }
                                    if(addColorBlock.z<theNearestBlock.z){
                                        addColorBlock.z=theNearestBlock.z ;
                                    }
                                }*/

                                return new Object[]{theNearestBlock,face,touchPoint,placePoint};

                            }
                        }
                    }
                }
            }
        }
            return new Object[]{theNearestBlock,face,touchPoint,placePoint};
    }
    public void brushImageOnBlock(float x,float y){


        if(currentChoosedGroupForEdit!=null){//内部绘制
            currentChoosedGroupForEdit.brushImageOnBlock( x, y,red,green,blue);
            return;
        }

        Object[] result = this.getMouseChoosedBlock(x,y);
        BaseBlock block= (BaseBlock)result[0];
        int face =(int) result[1];

            if(block!=null && block instanceof  ImageBlock) {
                ImageBlock imageBlock  =(ImageBlock) block;
                //调整
                if (face == Constants.BACK) {

                    imageBlock.back = nowTextureInfo;

                } else if (face == Constants.FRONT) {
                    imageBlock.front = nowTextureInfo;

                } else if (face == Constants.LEFT) {

                    imageBlock.left = nowTextureInfo;

                } else if (face == Constants.RIGHT) {

                    imageBlock.right = nowTextureInfo;

                } else if (face == Constants.BOTTOM) {
                    imageBlock.bottom = nowTextureInfo;
                } else if (face == Constants.TOP) {
                    imageBlock.top = nowTextureInfo;
                }

            }
            LogUtil.println("是那个面:"+face);


    }

    /**
     * 当前选中的纹理
     */
    public TextureInfo nowTextureInfo;

    public void saveSelectAsComponent(String name){


        StringBuffer stringBuffer =new StringBuffer();
        try {
            //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
            for(BaseBlock block : selectBlockList) {
                // outputStream .write();

                stringBuffer.append(block.toString()).append("\n");
            }
            FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("save").resolve(name+".block").toFile(),stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveSelectAsItem(String name){

//        {
//            name:"grass",
//                    icon:"grass_top",
//                type:"block",
//                remark:"草方块",
//                shape:"grass",
//                baseon:"mantle"
//        },
        StringBuffer stringBuffer =new StringBuffer();
        try {
            //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
            BaseBlock block = selectBlockList.get(0);
            stringBuffer.append("{name:'").append(name).append("',")
                    .append("type:'").append("block").append("',")
                    .append("remark:'").append(name).append("',")
                    .append("remark:'").append(name).append("',")
                    .append("shape:'").append(block.toString()).append(",")
                    .append("baseon:'mantle'").append("'}");
                // outputStream .write();

                stringBuffer.append(block.toString()).append("\n");

            FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("config/item").resolve(name+".block").toFile(),stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void readComponentFromFile(File file) {
        try {
            List<String> list = FileUtil.readFile2List(file);
            for (String s : list) {
                String[] ary = s.split(",");
                ColorBlock colorBlock = new ColorBlock(Integer.valueOf(ary[0]), Integer.valueOf(ary[1]), Integer.valueOf(ary[2]));
                colorBlock.width = Float.valueOf(ary[3]);
                colorBlock.height = Float.valueOf(ary[4]);
                colorBlock.thick = Float.valueOf(ary[5]);
                colorBlock.rf = Float.valueOf(ary[6]);
                colorBlock.gf = Float.valueOf(ary[7]);
                colorBlock.bf = Float.valueOf(ary[8]);
                colorBlockList.add(colorBlock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void buildAnimationComponent(){
        AnimationBlock group =new AnimationBlock(1,1,1,1,1,1);
        Integer  minx=null,miny=null,minz=null;
             Float   maxx=null,maxy=null,maxz=null;

            //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
        for(int i=selectBlockList.size()-1;i>=0;i--){
            BaseBlock colorBlock = selectBlockList.get(i);
            group.addChild(selectBlockList.get(i));
            selectBlockList.remove(i);
            colorBlockList.remove(colorBlock);
            if(minx==null){
                minx =(int) colorBlock.x;
                miny=(int) colorBlock.y;
                minz =(int) colorBlock.z;
                maxx=(int) colorBlock.x+colorBlock.width;
                maxy=(int) colorBlock.y+colorBlock.height;
                maxz =(int)  colorBlock.z+colorBlock.thick;
            }
            else{
                if( colorBlock.x<minx){
                    minx =(int)  colorBlock.x;
                }
                if( colorBlock.y<miny){
                    miny = (int) colorBlock.y;
                }
                if( colorBlock.z<minz){
                    minz =(int)  colorBlock.z;
                }
                if(colorBlock.x+colorBlock.width>maxx){
                    maxx=(int) colorBlock.x+colorBlock.width;
                }
                if(colorBlock.y+colorBlock.height>maxy){
                    maxy=(int) colorBlock.y+colorBlock.height;
                }
                if(colorBlock.z+colorBlock.thick>maxz){
                    maxz=(int) colorBlock.z+colorBlock.thick;
                }
            }
        }
        group.x=minx;
        group.y=miny;
        group.z =minz;
        group.width = maxx-minx;
        group.height =maxy-miny;
        group.thick = maxz-minz;
        //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
        for(int i=group.colorBlockList.size()-1;i>=0;i--){
            BaseBlock colorBlock = group.colorBlockList.get(i);
            colorBlock.x-=group.x;
            colorBlock.y-=group.y;
            colorBlock.z-=group.z;
        }
        group.reComputePoints();

       /* for(BaseBlock colorBlock : group.colorBlockList){
            colorBlock.x-= group.x;
            colorBlock.y-=group.y;
            colorBlock.z-=group.z;
        }*/
        this.colorBlockList.add(group);

    }


    public void buildComponent(){
        GroupBlock group =new GroupBlock(1,1,1,1,1,1);
        Integer  minx=null,miny=null,minz=null;
        Float   maxx=null,maxy=null,maxz=null;

        //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
        for(int i=selectBlockList.size()-1;i>=0;i--){
            BaseBlock colorBlock = selectBlockList.get(i);
            group.addChild(selectBlockList.get(i));
            selectBlockList.remove(i);
            colorBlockList.remove(colorBlock);
            if(minx==null){
                minx =(int) colorBlock.x;
                miny=(int) colorBlock.y;
                minz =(int) colorBlock.z;
                maxx=(int) colorBlock.x+colorBlock.width;
                maxy=(int) colorBlock.y+colorBlock.height;
                maxz =(int)  colorBlock.z+colorBlock.thick;
            }
            else{
                if( colorBlock.x<minx){
                    minx =(int)  colorBlock.x;
                }
                if( colorBlock.y<miny){
                    miny = (int) colorBlock.y;
                }
                if( colorBlock.z<minz){
                    minz =(int)  colorBlock.z;
                }
                if(colorBlock.x+colorBlock.width>maxx){
                    maxx=(int) colorBlock.x+colorBlock.width;
                }
                if(colorBlock.y+colorBlock.height>maxy){
                    maxy=(int) colorBlock.y+colorBlock.height;
                }
                if(colorBlock.z+colorBlock.thick>maxz){
                    maxz=(int) colorBlock.z+colorBlock.thick;
                }
            }
        }
        group.x=minx;
        group.y=miny;
        group.z =minz;
        group.width = maxx-minx;
        group.height =maxy-miny;
        group.thick = maxz-minz;
        //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
        for(int i=group.colorBlockList.size()-1;i>=0;i--){
            BaseBlock colorBlock = group.colorBlockList.get(i);
            colorBlock.x-=group.x;
            colorBlock.y-=group.y;
            colorBlock.z-=group.z;
        }
        group.reComputePoints();

       /* for(BaseBlock colorBlock : group.colorBlockList){
            colorBlock.x-= group.x;
            colorBlock.y-=group.y;
            colorBlock.z-=group.z;
        }*/
        this.colorBlockList.add(group);

    }

   /* List<ColorGroup> groups =new ArrayList<>();*/

    public void adjustComponent(float xzoom,float yzoom,float zzoom,float xoffset ,float yoffset , float zoffset){
        if(selectBlockList.size()>0 && selectBlockList.get(0)instanceof AnimationBlock){
            AnimationBlock animationBlock = (AnimationBlock) selectBlockList.get(0);
            animationBlock.xzoom=xzoom;
            animationBlock.yzoom=yzoom;
            animationBlock.zzoom=zzoom;

            animationBlock.xoffset=xoffset;
            animationBlock.yoffset=yoffset;
            animationBlock.zoffset=zoffset;
            animationBlock.scale(xzoom,yzoom,zzoom);
        }
    }



    public void mouseMove(int x,int y){
        // mouseOvercolorBlock = selectSingle(GamingState.instance.camera.Position.copyClone(), OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(),x,Constants.WINDOW_HEIGHT-y));
        //计算 鼠标和xz平面的交接处
        GL_Vector from = GamingState.instance.camera.Position;
        GL_Vector viewDir =  OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(),x,y);

        float weizhi = -from.y/viewDir.y;
        curentX=(int)(from.x+weizhi*viewDir.x);
        curentZ= (int)(from.z+weizhi*viewDir.z);


    }

    public void mouseClick(int x,int y){
        if(Switcher.mouseState==Switcher.faceSelectMode) {
            prevFaceX = curentX;
            prevMaxFaceX = curentX + 1;
            prevFaceZ = curentZ;
            prevMaxFaceZ = curentZ + 1;
            lastFaceX = curentX + 1;
            lastFaceZ = curentZ + 1;

        }
    }
    int lastPaintX =0;
    int lastPaintZ=0;
    public void mouseDrag(int x,int y){

        if(Switcher.mouseState==Switcher.faceSelectMode) {
            lastFaceX = curentX;
            lastFaceZ = curentZ;
            lastFaceMaxX = curentX + 1;
            lastFaceMaxZ = curentZ + 1;
        }else if(Switcher.mouseState==Switcher.shootMode){

            if(lastPaintX!=curentX  || lastPaintZ != curentZ){
            shootBlock(x,y);
                lastPaintX=curentX;
                lastPaintZ =curentZ;
            }
        }else if(Switcher.mouseState==Switcher.shootComponentMode){
            if(lastPaintX!=curentX  || lastPaintZ != curentZ){
                shootBlock(x,y);
                lastPaintX=curentX;
                lastPaintZ =curentZ;
            }

        }
    }
    public void mouseUp(int x,int y){
        if(Switcher.mouseState==Switcher.faceSelectMode) {
            lastFaceX=curentX;
            lastFaceZ=curentZ;
        }
    }

    /**
     * 获得当前动画有多少针
     * @return
     */
    public int getCurrentColorGroupAnimationFrameCount(){
        if(selectBlockList.size()>0 && selectBlockList.get(0) instanceof AnimationBlock){
            return ((AnimationBlock) selectBlockList.get(0) ).animations.size();
        }
        return 0;
    }
    public void saveToCurFrame(){
       AnimationBlock animationBlock = (AnimationBlock)currentChoosedGroupForEdit;
        if(animationBlock !=null){
            animationBlock.saveToCurFrame();
        }
       // currentChoosedGroupForEdit.animations(currentChoosedGroupForEdit.)
    }
    public void deleteCurFrame(){
        AnimationBlock animationBlock = (AnimationBlock)currentChoosedGroupForEdit;
        if(animationBlock !=null){
            animationBlock.deleteCurFrame();
        }
    }


    /**
     * colorgroup如何显示成一个动画  动画是由帧组成的 每个帧都可以是完全不同的东西 但是 所有的帧都挂靠在 一个父级的 ColorGroup里面
     * 把当前的colorgroup 复制并新增一个动画帧
     */
    public void currentColorAddGroupAnimationFrame(){
        if(selectBlockList.size()>0 && selectBlockList.get(0) instanceof AnimationBlock){
            AnimationBlock group = (AnimationBlock) selectBlockList.get(0);
            AnimationBlock newGroup =group.copy();
            newGroup .animations=null;
            GroupBlock groupBlock =new GroupBlock();
            groupBlock.colorBlockList = newGroup .colorBlockList;
            group.animations.add(groupBlock);
        }

    }



    /**
     * 改变当前选中的组件 由鼠表选中组件 触发
     * @param componentName
     */
    public void changeCurrentComponent(String componentName){
       // chooseColorGroup = colorGroupHashMap.get(componentName);
        readyShootBlock = colorGroupHashMap.get(componentName);
    }

    /*public void loadColorGroup(String componentName,List<String > contents) {
        chooseColorGroup = colorGroupHashMap.get(componentName);
    }*/
    public int readColorGroupFromList(List<String> contents,int nowIndex,AnimationBlock animationBlock){

        String groupInfo = contents.get(nowIndex);
        String infoAry[] = groupInfo.split(",");
        animationBlock.width=Float.valueOf(infoAry[0]);
        animationBlock.height=Float.valueOf(infoAry[1]);
        animationBlock.thick=Float.valueOf(infoAry[2]);

        animationBlock.xoffset=Float.valueOf(infoAry[3]);
        animationBlock.yoffset=Float.valueOf(infoAry[4]);
        animationBlock.zoffset=Float.valueOf(infoAry[5]);

        animationBlock.xzoom=Float.valueOf(infoAry[6]);
        animationBlock.yzoom=Float.valueOf(infoAry[7]);
        animationBlock.zzoom=Float.valueOf(infoAry[8]);
        int lastIndex =0;
        for(int i=nowIndex+1,size= contents.size();i<size;i++){
            String s = contents.get(i);
            if(s.equalsIgnoreCase("animation")){
             return i+1;
            }
            String[] ary = s.split(",");
            if(ary.length>10){
                ImageBlock block = new ImageBlock();
                set(block,Math.round(Float.valueOf(ary[0])), Math.round(Float.valueOf(ary[1])), Math.round(Float.valueOf(ary[2])),Float.valueOf(ary[3]),Float.valueOf(ary[4]),Float.valueOf(ary[5]),
                        Float.valueOf(ary[6]),Float.valueOf(ary[7]),Float.valueOf(ary[8]),Float.valueOf(ary[9]));
                block.top = TextureManager.getTextureInfo(String.valueOf(ary[10]));
                block.bottom = TextureManager.getTextureInfo(String.valueOf(ary[11]));
                block.front = TextureManager.getTextureInfo(String.valueOf(ary[12]));
                block.back = TextureManager.getTextureInfo(String.valueOf(ary[13]));
                block.left = TextureManager.getTextureInfo(String.valueOf(ary[14]));
                block.right = TextureManager.getTextureInfo(String.valueOf(ary[15]));
                animationBlock.colorBlockList.add(block);
            }else{
                ColorBlock colorBlock = new ColorBlock(Math.round(Float.valueOf(ary[0])), Math.round(Float.valueOf(ary[1])), Math.round(Float.valueOf(ary[2])));
                colorBlock.width = Float.valueOf(ary[3]);
                colorBlock.height = Float.valueOf(ary[4]);
                colorBlock.thick = Float.valueOf(ary[5]);
                colorBlock.rf = Float.valueOf(ary[6]);
                colorBlock.gf = Float.valueOf(ary[7]);
                colorBlock.bf = Float.valueOf(ary[8]);
                if(ary.length>=10){
                    colorBlock.opacity = Float.valueOf(ary[9]);
                }
                animationBlock.colorBlockList.add(colorBlock);
            }


        }

        return -1;

    }

    /**
     * 从指定文件读取colorGroup并 加载到系统组件当中
     * @param file
     */
//    public void readAndLoadColorGroupFromFile(File file) {
//
//
//        try {
//
//            List<String> list = FileUtil.readFile2List(file);
//        if(file.getName().equals("yanjiang3.block")){
//            LogUtil.println("hello");
//        }
//            AnimationBlock animationBlock = new AnimationBlock(0,0,0,1,1,1);
//            int index = this.readColorGroupFromList(list,0, animationBlock);
//            while(index != -1){
//                AnimationBlock animationGroup = new AnimationBlock(0,0,0,1,1,1);
//                index = this.readColorGroupFromList(list,index,animationGroup);
//                animationBlock.animations.add(animationGroup);
//            }
//
//            colorGroupHashMap.put(file.getName(), animationBlock);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    /**
     * 根据给定的名称保存当前选中的colorGroup 并保存到内存colorGroupHashMap中
     * @param name
     */
    public void saveSelectAsColorGroup(int id,String name,boolean live,String script ,boolean isPenetrate){

        //这里还要增加imageblock colorblock 的支持 然后原生的支持

        BaseBlock selectBlock = this.getSelectFirstBlock();
        selectBlock.live=live;
        if(selectBlock == null){
            return;
        }
        //获取现在设定的id
        StringBuffer sb =new StringBuffer();
        selectBlock.setName(name);
      //  TextureManager.putShape(selectBlock);

        ItemFactory itemFactory =new ItemFactory();
        selectBlock.penetration = isPenetrate;

        sb.append("{id:").append(id).append(",")
       .append("name:'").append(name).append("',")
                .append("type:'").append("block").append("',")
                .append("remark:'").append(name).append("',")
                .append("script:'").append(script.replaceAll("\"","\\\\\"").replaceAll("'","\\\\\'").replaceAll("\r\n", "")).append("',")
                .append("live:'").append(live).append("',")
                .append("remark:'").append(name).append("',")
                .append("shape:").append(selectBlock.toString()).append(",")
                .append("baseon:'mantle'").append("}");

        

        try {

            //保存之后存入到内存当中
            LogUtil.println(sb.toString());
            ItemDefinition itemDef =  itemFactory.parse(JSON.parseObject(sb.toString(), HashMap.class));

            ItemManager.putItemDefinition(itemDef.getName(), itemDef);

            FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("config/item/newItem").resolve(id+"_"+name+".block").toFile(),sb.toString());
            colorGroupHashMap.put(name,selectBlock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animationFrameShowNum(int num){
        ( (AnimationBlock)currentChoosedGroupForEdit).showAnimationFrame(num);
    }

    public boolean playAnimation(){
        if(currentChoosedGroupForEdit!= null){
           return  ((AnimationBlock)currentChoosedGroupForEdit).play();
        }
        return true;
    }

    public float adjustRotatex(float value){
        float rotateX = 0 ;
        for(int i=0;i<selectBlockList.size();i++){
            BaseBlock colorBlock  = selectBlockList.get(i);
            if(colorBlock instanceof RotateColorBlock2){
                rotateX= ((RotateColorBlock2) colorBlock) .rotateX(value);
            }
            if(colorBlock instanceof RotateImageBlock){
                rotateX= ((RotateImageBlock) colorBlock) .rotateX(value);
            }
            if(colorBlock instanceof AnimationBlock){
                rotateX= ((AnimationBlock) colorBlock) .rotateX(value);
            }
            if(colorBlock instanceof GroupBlock){
                rotateX= ((GroupBlock) colorBlock) .rotateX(value);
            }
        }
        return rotateX;
    }

    public float adjustRotateY(float value){
        float rotate=0;
        for(int i=0;i<selectBlockList.size();i++){
            BaseBlock colorBlock  = selectBlockList.get(i);
            //if(colorBlock instanceof RotateColorBlock2){
               // ((RotateColorBlock2) colorBlock) .rotateY(value);
            rotate= colorBlock .rotateY(value);
           // }
        }
        return rotate;
    }
    public float  adjustRotateZ(float value){
        float rotate=0;
        for(int i=0;i<selectBlockList.size();i++){
            BaseBlock colorBlock  = selectBlockList.get(i);
            if(colorBlock instanceof RotateColorBlock2){
                rotate=((RotateColorBlock2) colorBlock) .rotateZ(value);
            }
            if(colorBlock instanceof RotateImageBlock){
                rotate=((RotateImageBlock) colorBlock) .rotateZ(value);
            }
            if(colorBlock instanceof AnimationBlock){
                rotate= ((AnimationBlock) colorBlock) .rotateZ(value);
            }
        }
        return rotate;
    }

    public BaseBlock readyShootBlock = new ColorBlock();

    public void saveToGame(){


        int preBlockIndex=-1;
        int chunkIndex=-1;
        Integer preChunkIndex=null;  //if preChunkIndex != chunkIndex bianlichunk will to find it's new chunk and also use to find the place block
        int blockIndex=0;
        Chunk bianliChunk=null;//need to be initialize
        int chunkX,preChunkX,chunkZ,preChunkZ;
        //int chunkY;
        float nowX,nowY,nowZ,preX,preY,preZ;
        nowX=nowY=nowZ=preX=preY=preZ=0;
        int worldX,worldY,worldZ ,preWorldX,preWorldY,preWorldZ;
        int offsetX ,offsetZ ,preOffsetX,preOffsetZ;
        ChunkProvider localChunkProvider= CoreRegistry.get(ChunkProvider.class);
        Client client =  CoreRegistry.get(Client.class);
        for(int i =colorBlockList.size()-1;i>=0;i--){
           BaseBlock  colorBlock = colorBlockList.get(i);
            int id = colorBlock.id;

            boolean live= colorBlock.live;
            worldX = colorBlock.getX();
            worldY = colorBlock.getY();
            worldZ = colorBlock.getZ();

            if(live){
                SayCmd sayCmd = new SayCmd(0,"","/create monster "+worldX+" "+worldY+" "+worldZ+" "+colorBlock.getId());
                client.send(sayCmd);
                continue;


            }

            offsetX = MathUtil.getOffesetChunk(worldX);
            offsetZ = MathUtil.getOffesetChunk(worldZ);
            blockIndex=offsetX*10000+offsetZ*100+worldY;//推进后落入的blockINdex
            if(preBlockIndex==-1){
                preBlockIndex = blockIndex;
            }
            chunkX=MathUtil.getBelongChunkInt(worldX);//换算出新的chunkX
            chunkZ=MathUtil.getBelongChunkInt(worldZ);//换算出新的chunkZ
            chunkIndex =  chunkX*10000+chunkZ;
          /*  if(preChunkIndex==null){
                bianliChunk = localChunkProvider.getChunk(new Vector3i(chunkX,0,chunkZ));//因为拉远距离了之后 会导致相机的位置其实是在很远的位置 改为只其实还没有chunk加载 所以最好是从任务的头顶开始出发
                preChunkIndex = chunkIndex;
            }

            if( chunkIndex!=preChunkIndex){//如果进入到新的chunk方格子空间
                bianliChunk = localChunkProvider.getChunk(new Vector3i(chunkX,0,chunkZ));//因为拉远距离了之后 会导致相机的位置其实是在很远的位置 改为只其实还没有chunk加载 所以最好是从任务的头顶开始出发

            }


            bianliChunk.setBlock(offsetX,worldY,offsetZ,colorBlock.id);*/


            colorBlockList.remove(i);


            ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
            cmd.cx = offsetX;
            cmd.cz = offsetZ;
            cmd.cy = worldY;
            cmd.type = 1;
            cmd.dir=colorBlock.dir;
            cmd.blockType = id;
            client.send(cmd);

//            if(colorBlock instanceof  ColorGroup){
//                ColorGroup colorGroup = (ColorGroup)colorBlock;
//                //每个block都可以有单独的id
//                //block 又分为多个类型 colorblock imageblock  GroupBlock  rotateBlock 这几种 每个都有自己单独的id 和name
//
//                //
//                // colorGroup都有单独的id
//                //把这个id 写入到对应的chunkimp中 array中 如果碰到有状态的可旋转的还有单独放出来记录他的实体对象 拿出chunk的x y z
//                //然后每个 在以后的历史长河中 我希望是这样的 玩家 拥有自己的一套 骨骼系统 这套系统支持 骨骼相连接 其他的 非骨骼系统就可以直接使用模型 colorgroup
//
//                //color group 里有 各种 colorBlock imageBlock rotateBlock组成 boneBlock组成 那么 所有的block 所有的模型都可以通过colorGroup来表达
//                //每个colorgroup 都有单独的id
//
//
//            }else if(colorBlock instanceof  ImageBlock){
//
//            }


        }
    }


    public void readBlocksFromCurrentChunk() {

        //获取当前chunk

        ChunkProvider localChunkProvider= CoreRegistry.get(ChunkProvider.class);


        int  chunkX=MathUtil.getBelongChunkInt(GamingState.instance.player.getX());//换算出新的chunkX
        int chunkZ=MathUtil.getBelongChunkInt(GamingState.instance.player.getZ());//换算出新的chunkZ

        Chunk chunk = localChunkProvider.getChunk(chunkX,0,chunkZ);

        TeraArray data= chunk.getBlockData();
        List<Integer> arr = new ArrayList<>();
        for(int x =0;x<ChunkConstants.SIZE_X;x++){

            for(int y =0;y<ChunkConstants.SIZE_Y;y++){

                for(int z =0;z<ChunkConstants.SIZE_Z;z++){
                    int value = chunk.getBlockData(x,y,z);
                    //x<<12 && y<<8&& z <<4 && value
                    if(value>0){
                      BaseBlock block = ItemManager.getItemDefinition(value).getShape().copy();
                      chunk.setBlock(x,y,z,0);
                        block.x=chunkX*16+x;
                        block.y=y;
                        block.z=chunkZ*16+z;
                        this.colorBlockList.add(block);
                    }

                }
            }
        }

        chunk.setNeedUpdate(true);
    }

    public void synChunkFromServer() {

        ChunkProvider localChunkProvider= CoreRegistry.get(ChunkProvider.class);


        int  chunkX=MathUtil.getBelongChunkInt(GamingState.instance.player.getX());//换算出新的chunkX
        int chunkZ=MathUtil.getBelongChunkInt(GamingState.instance.player.getZ());//换算出新的chunkZ



        Chunk chunk = localChunkProvider.getChunk(chunkX,0,chunkZ);

      localChunkProvider.reload(chunk);

    }

    public void synChunkFromClient() {
        ChunkProvider localChunkProvider= CoreRegistry.get(ChunkProvider.class);


        int  chunkX=MathUtil.getBelongChunkInt(GamingState.instance.player.getX());//换算出新的chunkX
        int chunkZ=MathUtil.getBelongChunkInt(GamingState.instance.player.getZ());//换算出新的chunkZ



        Chunk chunk = localChunkProvider.getChunk(chunkX,0,chunkZ);

        ChunkResponseCmd cmd =new ChunkResponseCmd(chunk);
        CoreRegistry.get(Client.class).send(cmd);
    }

    public void synChunkFromEditToClient() {
        ChunkProvider localChunkProvider= CoreRegistry.get(ChunkProvider.class);


        int  chunkX=MathUtil.getBelongChunkInt(GamingState.instance.player.getX());//换算出新的chunkX
        int chunkZ=MathUtil.getBelongChunkInt(GamingState.instance.player.getZ());//换算出新的chunkZ



        Chunk chunk = localChunkProvider.getChunk(chunkX,0,chunkZ);

        ChunkResponseCmd cmd =new ChunkResponseCmd(chunk);
        CoreRegistry.get(Client.class).send(cmd);
    }

    public void setCenter(float x,float y,float z ){
        for(BaseBlock block : selectBlockList){
            if(block instanceof  RotateColorBlock2){
                RotateColorBlock2  rotateBlock = (RotateColorBlock2) block;
                rotateBlock.centerX = x;
                rotateBlock.centerY = y;
                rotateBlock.centerZ = z;

            }else{
                if(block instanceof AnimationBlock){
                    AnimationBlock group =(AnimationBlock) block;
                    group.setCenter(x,y,z);
                }
            }
        }
    }


    public void addAnimationMap(String name) {
        // TODO Auto-generated method stub
        
        BaseBlock block  =  this.getSelectFirstBlock();
        if(block instanceof AnimationBlock){
            AnimationBlock group = (AnimationBlock) block;
            List<GroupBlock> copyAnimationList = new ArrayList<>();
            
            for(GroupBlock animationBlock : group.animations){
                copyAnimationList.add(animationBlock.copy());
            }
            group.animationMap.put(name, copyAnimationList);
        }
        
    }


    public void animationNameShow(String selectedItem) {
        BaseBlock block  =  this.getSelectFirstBlock();
        if(block instanceof AnimationBlock){
            AnimationBlock group = (AnimationBlock) block;
            group.animations.clear();//不合适吧
            List<GroupBlock> copyAnimationList = group.animationMap.get(selectedItem);
            
            for(GroupBlock animationBlock : copyAnimationList){
                group.animations.add(animationBlock.copy());
                
             
            }
           
        }
        
    }
    
    
    public List<String> getAnimationName() {
        List nameList =new ArrayList<String>();
        BaseBlock block  =  this.getSelectFirstBlock();
        if(block instanceof AnimationBlock){
            AnimationBlock group = (AnimationBlock) block;
            Map<String, List<GroupBlock>> map =group.animationMap;
            
            
            for (Map.Entry<String, List<GroupBlock>> entry : map.entrySet()) {
              
                nameList.add(entry.getKey() );
              
            }  
            
        }
        return nameList;
    }
}
