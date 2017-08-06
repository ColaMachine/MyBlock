package com.dozenx.game.engine.edit;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
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
import glmodel.GL_Vector;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
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
        public void chooseObject(GL_Vector from, GL_Vector direction){
            direction=direction.normalize();
           startPoint =from;
            endPoint= from.getClone().add(direction.mult(50));
            // LogUtil.println("开始选择");
            Vector3f fromV= new Vector3f(from.x,from.y,from.z);
            Vector3f directionV= new Vector3f(direction.x,direction.y,direction.z);
            for(int i=0;i<colorBlockList.size();i++){
                ColorBlock colorBlock =  colorBlockList.get(i);
                AABB aabb = new AABB(new Vector3f(colorBlock.x,colorBlock.y,colorBlock.z),new Vector3f(colorBlock.x+colorBlock.width,colorBlock.y+colorBlock.height,colorBlock.z+colorBlock.thick));

                // LogUtil.println(fromV.toString() );
                // LogUtil.println(directionV.toString() );
                if( aabb.intersectRectangle(fromV,directionV)){
                    //GL_Vector.chuizhijuli(GL_Vector.sub(livingThing.position,from),direction)<3){
                    //   LogUtil.println("选中了");
                    //this.target=livingThing;
                    //colorBlock.selected=true;
                    selectBlockList.clear();
                    selectBlockList.add(colorBlock);
                    break;
                }


            }

        }

    //选择区域
    public void selectObject(float minX,float minY,float maxX,float maxY){
        //遍历所有的方块查找所有的在方块里的方块
        //取出
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
}
