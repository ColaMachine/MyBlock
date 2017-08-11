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
import com.dozenx.game.engine.edit.view.ColorGroup;
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
    public float prevX ;
    public float prevY;
    public float nowX;
    public float nowY;
    public void update(){
       /* for(int i =0;i<groups.size();i++){
            groups.get(i).update();
        }*/

        for(int i=0;i<100;i++){

            ShaderUtils.drawLine(new GL_Vector(i,0,0),new GL_Vector(i,0,100));
            ShaderUtils.drawLine(new GL_Vector(0,0,i),new GL_Vector(100,0,i));

        }

        ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),0,0,0,new GL_Vector(0.3f,0.3f,0.3f),100,0,100,1);

        for(int i=0;i<selectBlockList.size();i++){
            ColorBlock colorBlock = selectBlockList.get(i);

            ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig,ShaderManager.lineShaderConfig.getVao(),colorBlock.x,colorBlock.y,colorBlock.z,colorBlock.width,colorBlock.height,colorBlock.thick);

        }

        for(int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock = colorBlockList.get(i);
              colorBlock.update();
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
    int minX,minZ;
public GL_Vector startPoint;
    public GL_Vector endPoint;
    public ColorBlock getSelectBlock(GL_Vector from, GL_Vector direction ){
        direction=direction.normalize();
        //  startPoint =GamingState.instance.player.getPosition().getClone();
        startPoint =GamingState.instance.camera.Position.getClone();
        endPoint= startPoint.getClone().add(direction.getClone().mult(12));
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
    }
    //在没有圈框的时候 进行点射选择 在另一个selectObject中被调用
        public ColorBlock  selectSingle(GL_Vector from, GL_Vector direction){

            ColorBlock theNearestBlock = getSelectBlock(from,direction);


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



            return null;
        }




    //选择区域
    public void selectMany(float minX,float minY,float maxX,float maxY,boolean clearbefore){
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

        if(!clearbefore || selectBlockList.size()==0){
           selectSingle(GamingState.instance.camera.Position.getClone(), OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().getClone(),minX,Constants.WINDOW_HEIGHT-minY));
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

    public void addBlock(){

        // Color commonColor =new Color( color.getRed() ,color.getGreen(),color.getBlue());



        minX = Math.min(prevFaceX,lastFaceX);
        minZ = Math.min(prevFaceZ,lastFaceZ);
        int width = Math.max(lastFaceMaxX,prevMaxFaceX)-minX;
        int thick = Math.max(lastFaceMaxZ,prevMaxFaceZ)-minZ;

        ColorBlock colorBlock =new ColorBlock(minX,0,minZ);
        colorBlock.rf = red;
        colorBlock.gf = green;
        colorBlock.bf =blue;
        colorBlock.width =width;
        colorBlock.height=1;
        colorBlock.thick  =thick;
        /*for(int x=minX;x<width;x++){
            for(int z=minZ;z<thick;z++){

            }
        }*/
        colorBlockList.add(colorBlock);
        GamingState.editEngine.select(colorBlock);
    }

    public void select(ColorBlock block){

        selectBlockList.clear();
        selectBlockList.add(block);
    }

    public void adjustWidth(int num,boolean position){
        if(Switcher.size&&position) {
            for( int i=0;i<selectBlockList.size();i++){
                ColorBlock colorBlock  =  selectBlockList.get(i);

                colorBlock.addWidth(num);//(false);

            }}else{
            for( int i=0;i<selectBlockList.size();i++){
                ColorBlock colorBlock  =  selectBlockList.get(i);

                colorBlock.addX(num);//(false);

            }
        }
    }
    public void adjustHeight(int num,boolean position){
        if(Switcher.size && position) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                ColorBlock colorBlock = selectBlockList.get(i);

                colorBlock.addHeight(num);//(false);

            }
        }else{
            for (int i = 0; i < selectBlockList.size(); i++) {
                ColorBlock colorBlock = selectBlockList.get(i);

                colorBlock.addY(num) ;//(false);

            }
        }
    }

    public void adjustThick(int num,boolean position){
        if(Switcher.size&&position){
            for( int i=0;i<selectBlockList.size();i++){
                ColorBlock colorBlock  =  selectBlockList.get(i);

                colorBlock.addThick( num);//(false);

            }
        }else{
            for( int i=0;i<selectBlockList.size();i++){
                ColorBlock colorBlock  =  selectBlockList.get(i);

                colorBlock.addZ( num);//(false);

            }
        }

    }

    public void copySelect(){
        for( int i=selectBlockList.size()-1;i>=0;i--){
            ColorBlock colorBlock  =  selectBlockList.get(i);
            ColorBlock copyBlock =new ColorBlock(colorBlock.x,colorBlock.y,colorBlock.z);
            copyBlock.rf = colorBlock.rf;
            copyBlock.gf =colorBlock.gf;
            copyBlock.bf = colorBlock.bf;
            copyBlock.width = colorBlock.width;
            copyBlock.height =colorBlock.height;
            copyBlock.thick =colorBlock.thick;
            //colorBlock.selected=false;
            colorBlockList.add(copyBlock);
            selectBlockList.set(i,copyBlock);
          //  copyBlock.selected=true;

        }
    }
    public float red ,green,blue;
    public void setColor(float red,float green ,float blue) {
        this.red = red;
        this.green= green;
        this.blue =blue;
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

    public void brushBlock(float x,float y){
        ColorBlock block = this.getSelectBlock(GamingState.instance.camera.Position.getClone(), OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().getClone(),x,Constants.WINDOW_HEIGHT-y));
        if(block == null)
            return;
        block.rf = red;
        block.gf = green;
        block.bf = blue;
    }
//,float red,float green ,float blue
    public void shootBlock(float x,float y){

       GL_Vector from = GamingState.instance.camera.Position.getClone();
        GL_Vector direction =OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().getClone(),x,Constants.WINDOW_HEIGHT-y);
        direction=direction.normalize();
        startPoint =from;
        endPoint= from.getClone().add(direction.mult(50));
        // LogUtil.println("开始选择");
        Vector3f fromV= new Vector3f(from.x,from.y,from.z);
        Vector3f directionV= new Vector3f(direction.x,direction.y,direction.z);
        float distance =0;
        ColorBlock theNearestBlock = null;
        float[] xiangjiao=null;
        float[] right=null;
        for(int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock =  colorBlockList.get(i);
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

               // addColorBlock.rf = color.red;
              //  addColorBlock.gf=color.green;
              //  addColorBlock.bf =color.blue;
                //GL_Vector.chuizhijuli(GL_Vector.sub(livingThing.position,from),direction)<3){
                //   LogUtil.println("选中了");
                //this.target=livingThing;
                //colorBlock.selected=true;


            }


        }

        int face =-1;
        if(theNearestBlock!=null){
            //计算是那一面
           /* if(right[0]<0){//x
                if((int) right[3] == 1 ){
                    right[3] =2;
                }
                if((int) right[3] == 2 ){
                    right[3] =1;
                }

                if((int) right[4] == 1 ){
                    right[4] =2;
                }
                if((int) right[4] == 2 ){
                    right[4] =1;
                }
            }
            if(right[1]<0){//y
                if((int) right[3] == 3 ){
                    right[3] =4;
                }else
                if((int) right[3] == 4 ){
                    right[3] =3;
                }
                if((int) right[5] == 1 ){
                    right[5] =2;
                }
                if((int) right[5] == 2 ){
                    right[5] =1;
                }
            }
            if(right[2]<0){//z
                if((int) right[4] == 3 ){
                    right[4] =4;
                }else
                if((int) right[4] == 4 ){
                    right[4] =3;
                }
                if((int) right[5] == 3 ){
                    right[5] =4;
                }
                if((int) right[5] == 4 ){
                    right[5] =3;
                }
            }*/


           float[] ary =  AABB.xyFaces[  (int) right[3]-1];
            float[] ary1 =  AABB.xzFaces[  (int) right[4]-1];
            float[] ary2 =  AABB.yzFaces[  (int) right[5]-1];

            for(int i=0;i<ary.length;i++){
                for(int j=0;j<ary.length;j++){
                    if(ary[i]==ary1[j]){//在xz的集合当中也有


                        for(int k=0;k<ary.length;k++){
                        //在yz的集合当中也有
                            if(ary[i]==ary2[k]) {
                                face =(int)ary[i];
                                try {
                                    ColorBlock addColorBlock = new ColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
                                    addColorBlock.rf = red;
                                    addColorBlock.gf =green;
                                    addColorBlock.bf = blue;
                                    colorBlockList.add(addColorBlock);
                                    if (face == Constants.BACK ) {
                                        addColorBlock.z -= 1;
                                    } else if (face == Constants.LEFT) {
                                        addColorBlock.x -= 1;
                                    } else if (face == Constants.BOTTOM) {
                                        addColorBlock.y -= 1;
                                    }

                                    if (face == Constants.BACK  ||  face == Constants.FRONT) {
                                        float fangdabeishu = (addColorBlock.z-from.z)/direction.z;
                                        addColorBlock.x= (int)(from.x+direction.x*fangdabeishu);
                                        addColorBlock.y= (int)(from.y+direction.y*fangdabeishu);
                                    } else if (face == Constants.LEFT || face == Constants.RIGHT) {
                                        float fangdabeishu = (addColorBlock.x-from.x)/direction.x;
                                        addColorBlock.y= (int)(from.y+direction.y*fangdabeishu);
                                        addColorBlock.z= (int)(from.z+direction.z*fangdabeishu);
                                    } else if (face == Constants.BOTTOM || face == Constants.TOP ) {
                                        float fangdabeishu = (addColorBlock.y-from.y)/direction.y;
                                        addColorBlock.x= (int)(from.x+direction.x*fangdabeishu);
                                        addColorBlock.z= (int)(from.z+direction.z*fangdabeishu);
                                    }
                                    //调整
                                    if (face == Constants.BACK) {
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

                                     /*   if(addColorBlock.z>theNearestBlock.z +(int)theNearestBlock.thick){
                                            addColorBlock.z=theNearestBlock.z +(int)theNearestBlock.thick;
                                        }
                                        if(addColorBlock.z<theNearestBlock.z){
                                            addColorBlock.z=theNearestBlock.z ;
                                        }*/

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

                                     /*   if(addColorBlock.z>theNearestBlock.z +(int)theNearestBlock.thick){
                                            addColorBlock.z=theNearestBlock.z +(int)theNearestBlock.thick;
                                        }
                                        if(addColorBlock.z<theNearestBlock.z){
                                            addColorBlock.z=theNearestBlock.z ;
                                        }*/

                                    } else if (face == Constants.LEFT) {




                                        //addColorBlock.x=theNearestBlock.x-1;
                                       /* if(addColorBlock.x>theNearestBlock.x +(int)theNearestBlock.width){
                                            addColorBlock.x=theNearestBlock.x +(int)theNearestBlock.width;
                                        }
                                        if(addColorBlock.y<theNearestBlock.y){
                                            addColorBlock.y=theNearestBlock.y ;
                                        }*/

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




                                        //addColorBlock.x=theNearestBlock.x+(int)theNearestBlock.width+1;
                                       /* if(addColorBlock.x>theNearestBlock.x +(int)theNearestBlock.width){
                                            addColorBlock.x=theNearestBlock.x +(int)theNearestBlock.width;
                                        }
                                        if(addColorBlock.y<theNearestBlock.y){
                                            addColorBlock.y=theNearestBlock.y ;
                                        }*/

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

                                     /*   if(addColorBlock.y>theNearestBlock.y +(int)theNearestBlock.height){
                                            addColorBlock.y=theNearestBlock.y +(int)theNearestBlock.height;
                                        }
                                        if(addColorBlock.y<theNearestBlock.y){
                                            addColorBlock.y=theNearestBlock.y ;
                                        }
*/
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

                                     /*   if(addColorBlock.y>theNearestBlock.y +(int)theNearestBlock.height){
                                            addColorBlock.y=theNearestBlock.y +(int)theNearestBlock.height;
                                        }
                                        if(addColorBlock.y<theNearestBlock.y){
                                            addColorBlock.y=theNearestBlock.y ;
                                        }
*/
                                        if(addColorBlock.z>theNearestBlock.z +(int)theNearestBlock.thick-1){
                                            addColorBlock.z=theNearestBlock.z +(int)theNearestBlock.thick-1;
                                        }
                                        if(addColorBlock.z<theNearestBlock.z){
                                            addColorBlock.z=theNearestBlock.z ;
                                        }
                                    }

                                }catch ( Exception e){
                                    e.printStackTrace();
                                }


                            }
                        }
                    }
                }
            }



            LogUtil.println("是那个面:"+face);

        }
    }

    public void saveSelectAsComponent(String name){
        StringBuffer stringBuffer =new StringBuffer();
        try {
            //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
            for(ColorBlock colorBlock : selectBlockList){
                // outputStream .write();
                stringBuffer.append(colorBlock.x).append(",").append(colorBlock.y).append(",").append(colorBlock.z).append(",")
                        .append(colorBlock.width).append(",").append(colorBlock.height).append(",").append(colorBlock.thick).append(",")
                        .append(colorBlock.rf).append(",").append(colorBlock.gf).append(",").append(colorBlock.bf).append("\n");
            }
            FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("save").resolve(name+".block").toFile(),stringBuffer.toString());
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
    public void buildComponent(){
        ColorGroup group =new ColorGroup(0,0,0);
        Integer  minx=null,miny=null,minz=null;
             Float   maxx=null,maxy=null,maxz=null;

            //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
        for(int i=selectBlockList.size()-1;i>=0;i--){
            ColorBlock colorBlock = selectBlockList.get(i);
            group.addChild(selectBlockList.get(i));
            selectBlockList.remove(i);
            colorBlockList.remove(colorBlock);
            if(minx==null){
                minx = colorBlock.x;
                miny=colorBlock.y;
                minz =colorBlock.z;
                maxx=colorBlock.x+colorBlock.width;
                maxy=colorBlock.y+colorBlock.height;
                maxz = colorBlock.z+colorBlock.thick;
            }
            else{
                if( colorBlock.x<minx){
                    minx = colorBlock.x;
                }
                if( colorBlock.y<miny){
                    miny = colorBlock.y;
                }
                if( colorBlock.z<minz){
                    minz = colorBlock.z;
                }
                if(colorBlock.x+colorBlock.width>maxx){
                    maxx=colorBlock.x+colorBlock.width;
                }
                if(colorBlock.y+colorBlock.height>maxy){
                    maxy=colorBlock.y+colorBlock.height;
                }
                if(colorBlock.z+colorBlock.thick>maxz){
                    maxz=colorBlock.z+colorBlock.thick;
                }
            }
        }
        group.x=minx;
        group.y=miny;
        group.z =minz;
        group.width = maxx-minx;
        group.height =maxy-miny;
        group.thick = maxz-minz;
        for(ColorBlock colorBlock : group.colorBlockList){
            colorBlock.x-= group.x;
            colorBlock.y-=group.y;
            colorBlock.z-=group.z;
        }
        this.colorBlockList.add(group);

    }

   /* List<ColorGroup> groups =new ArrayList<>();*/

    public void adjustComponent(float xzoom,float yzoom,float zzoom,float xoffset ,float yoffset , float zoffset){
        if(selectBlockList.size()>0 && selectBlockList.get(0)instanceof ColorGroup){
            ColorGroup colorGroup = (ColorGroup ) selectBlockList.get(0);
            colorGroup.xzoom=xzoom;
            colorGroup.yzoom=yzoom;
            colorGroup.zzoom=zzoom;

            colorGroup.xoffset=xoffset;
            colorGroup.yoffset=yoffset;
            colorGroup.zoffset=zoffset;
        }
    }
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
    public void mouseMove(int x,int y){
        // mouseOvercolorBlock = selectSingle(GamingState.instance.camera.Position.getClone(), OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().getClone(),x,Constants.WINDOW_HEIGHT-y));
        //计算 鼠标和xz平面的交接处
        GL_Vector from = GamingState.instance.camera.Position;
        GL_Vector viewDir =  OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().getClone(),x,y);

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
    public void mouseDrag(int x,int y){
        if(Switcher.mouseState==Switcher.faceSelectMode) {
            lastFaceX = curentX;
            lastFaceZ = curentZ;
            lastFaceMaxX = curentX + 1;
            lastFaceMaxZ = curentZ + 1;
        }
    }
    public void mouseUp(int x,int y){
        if(Switcher.mouseState==Switcher.faceSelectMode) {
            lastFaceX=curentX;
            lastFaceZ=curentZ;
        }
    }

    public int getCurrentColorGroupAnimationFrameCount(){
        if(selectBlockList.size()>0 && selectBlockList.get(0) instanceof  ColorGroup){
            return ((ColorGroup) selectBlockList.get(0) ).animations.size();
        }
        return 0;
    }

    public void currentColorAddGroupAnimationFrame(){
        if(selectBlockList.size()>0 && selectBlockList.get(0) instanceof  ColorGroup){
            ColorGroup group = (ColorGroup) selectBlockList.get(0);
            ColorGroup newGroup =group.copy();


            group.animations.add(newGroup);
        }

    }

}
