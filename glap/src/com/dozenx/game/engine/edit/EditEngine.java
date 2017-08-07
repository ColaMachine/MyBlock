package com.dozenx.game.engine.edit;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.ui.head.view.HeadPanel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.FileUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/8/5.
 */
public class EditEngine {

    public List<ColorBlock> colorBlockList  =new ArrayList<>();
    public List<ColorBlock> selectBlockList  =new ArrayList<>();

    public void update(){
        for(int i=0;i<selectBlockList.size();i++){
            ColorBlock colorBlock = selectBlockList.get(i);
           // ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),colorBlock.x,colorBlock.y,colorBlock.z,new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf),colorBlock.width,colorBlock.height,colorBlock.thick,1);
            GL_Vector[] gl_vectors = BoxModel.getSmaillPoint(colorBlock.x,colorBlock.y,colorBlock.z,colorBlock.width,colorBlock.height,colorBlock.thick);
            for(int j =0;j<gl_vectors.length;j++){
                ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),gl_vectors[j].x,gl_vectors[j].y,gl_vectors[j].z,new GL_Vector(1,1,1),0.3f,0.3f,0.3f,1);

            }
        }
        for(int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock = colorBlockList.get(i);




                ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(), colorBlock.x, colorBlock.y, colorBlock.z, new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), colorBlock.width, colorBlock.height, colorBlock.thick, 1f);

        }
        if(startPoint!=null){

            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),startPoint.x,startPoint.y,startPoint.z,new GL_Vector(1,0,0),0.3f,0.3f,0.3f,1);

            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),endPoint.x,endPoint.y,endPoint.z,new GL_Vector(0,0,1),0.3f,0.3f,0.3f,1);


            ShaderUtils.draw3dColorTriangle(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(), startPoint,endPoint,new GL_Vector(endPoint.x+5,endPoint.y,endPoint.z),BoxModel.BACK_DIR,new GL_Vector(1,1,1));
        }
        //绘制
    }
public GL_Vector startPoint;
    public GL_Vector endPoint;

    //在没有圈框的时候 进行点射选择 在另一个selectObject中被调用
        public void chooseObject(GL_Vector from, GL_Vector direction){
            direction=direction.normalize();
           startPoint =from;
            endPoint= from.getClone().add(direction.mult(50));
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

                    float _tempDistance = xiangjiao[0]* xiangjiao[0]+ xiangjiao[1]* xiangjiao[1]+ xiangjiao[2]* xiangjiao[2];

                    if(distance ==0||_tempDistance<distance){
                        theNearestBlock=colorBlock;
                    }
                    //GL_Vector.chuizhijuli(GL_Vector.sub(livingThing.position,from),direction)<3){
                    //   LogUtil.println("选中了");
                    //this.target=livingThing;
                    //colorBlock.selected=true;


                }


            }
            selectBlockList.clear();
            if(theNearestBlock!=null){

                selectBlockList.add(theNearestBlock);
            }

        }

    //选择区域
    public void selectObject(float minX,float minY,float maxX,float maxY){
        //遍历所有的方块查找所有的在方块里的方块
        //取出
        if(maxX<minX){
            maxX =minX;
        }
        if(maxY<minY){
            maxY=minY;
        }
        selectBlockList.clear();
        for( int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock  =  colorBlockList.get(i);
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

        if(selectBlockList.size()==0){
           chooseObject(GamingState.instance.camera.Position.getClone(), OpenglUtils.getLookAtDirection3(GamingState.instance.camera.getViewDir().getClone(),minX,Constants.WINDOW_HEIGHT-minY));
        }
    }

    //选择区域
    public void selectObjects(float minX,float minY,float maxX,float maxY){
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

    public  void deleteSelect(){
       /* for( int i=0;i<selectBlockList.size();i++){
            ColorBlock colorBlock  =  selectBlockList.get(i);
            colorBlockList.remove(colorBlock);
        }*/

        for( int i=selectBlockList.size()-1;i>=0;i--){
            ColorBlock colorBlock  =  selectBlockList.get(i);

               colorBlockList.remove(colorBlock);

        }
        selectBlockList.clear();
    }


    public void adjustWidth(int num,boolean position){
        if(Switcher.size&&position) {
        for( int i=0;i<selectBlockList.size();i++){
            ColorBlock colorBlock  =  selectBlockList.get(i);

                colorBlock.width += num;//(false);

        }}else{
            for( int i=0;i<selectBlockList.size();i++){
                ColorBlock colorBlock  =  selectBlockList.get(i);

                colorBlock.x += num;//(false);

            }
        }
    }
    public void select(ColorBlock block){

        selectBlockList.clear();
        selectBlockList.add(block);
    }
    public void adjustHeight(int num,boolean position){
        if(Switcher.size && position) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                ColorBlock colorBlock = selectBlockList.get(i);

                colorBlock.height += num;//(false);

            }
        }else{
            for (int i = 0; i < selectBlockList.size(); i++) {
                ColorBlock colorBlock = selectBlockList.get(i);

                colorBlock.y += num;//(false);

            }
        }
    }

    public void adjustThick(int num,boolean position){
        if(Switcher.size&&position){
            for( int i=0;i<selectBlockList.size();i++){
                ColorBlock colorBlock  =  selectBlockList.get(i);

                colorBlock.thick += num;//(false);

            }
        }else{
            for( int i=0;i<selectBlockList.size();i++){
                ColorBlock colorBlock  =  selectBlockList.get(i);

                colorBlock.z += num;//(false);

            }
        }

    }

    public void copySelect(){
        for( int i=selectBlockList.size()-1;i>=0;i--){
            ColorBlock colorBlock  =  selectBlockList.get(i);
            ColorBlock copyBlock =new ColorBlock(colorBlock.x,colorBlock.y,colorBlock.z);
            copyBlock.rf = colorBlock.rf;
            colorBlock.gf =colorBlock.gf;
            colorBlock.bf = colorBlock.bf;
            copyBlock.width = colorBlock.width;
            copyBlock.height =colorBlock.height;
            copyBlock.thick =colorBlock.thick;
            //colorBlock.selected=false;
            colorBlockList.add(copyBlock);
            selectBlockList.set(i,copyBlock);
          //  copyBlock.selected=true;

        }
    }

    public void setColor(float red,float green ,float blue) {
        for( int i=selectBlockList.size()-1;i>=0;i--){
            ColorBlock colorBlock  =  selectBlockList.get(i);

            colorBlock.rf = red;
            colorBlock.gf =green;
            colorBlock.bf = blue;


        }
    }

    public void saveWork(){
        StringBuffer stringBuffer =new StringBuffer();
        try {
            //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
            for(ColorBlock colorBlock : colorBlockList){
               // outputStream .write();
                stringBuffer.append(colorBlock.x).append(",").append(colorBlock.y).append(",").append(colorBlock.z).append(",")
                .append(colorBlock.width).append(",").append(colorBlock.height).append(",").append(colorBlock.thick).append(",")
                        .append(colorBlock.rf).append(",").append(colorBlock.gf).append(",").append(colorBlock.bf).append("\n");
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
        ColorBlock colorBlock = selectBlockList.get(0);

        for(int x=0;x<colorBlock.width;x++){
            for(int y =0;y<colorBlock.height;y++){
                for(int z =0;z<colorBlock.thick;z++){
                    ColorBlock childBlock = new ColorBlock(colorBlock.x+x,colorBlock.y+y,colorBlock.z+z);
                    childBlock.width=1;
                    childBlock.height=1;
                    childBlock.thick=1;
                    childBlock.rf = colorBlock.rf();
                    childBlock.gf = colorBlock.gf();
                    childBlock.bf = colorBlock.bf();
                    colorBlockList.add(childBlock);
                }
            }
        }
        selectBlockList.clear();
        colorBlockList.remove(colorBlock);

    }
}
