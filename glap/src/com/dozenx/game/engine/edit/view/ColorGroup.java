package com.dozenx.game.engine.edit.view;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.model.ImageBlock;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.edit.EditEngine;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/8/9.
 */
public class ColorGroup extends  ColorBlock{
    public ColorGroup(int x,int y,int z,float width,float height,float thick){
        super(x,y,z,width,height,thick);
        /*this.x =x;
        this.y=y;
        this.z=z;
        this.width =width;
        this.height =height;
        this.thick =thick;*/
    }
    public float xoffset=0;
    public float yoffset=0;
    public float zoffset=0;
    public float xzoom=1;
    public float yzoom=1;
    public float zzoom=1;
   public List<ColorBlock> colorBlockList =new ArrayList<ColorBlock>();
    public List<ColorBlock> selectBlockList =new ArrayList<ColorBlock>();
    public List<ColorGroup> animations =new ArrayList<ColorGroup>();
    public ColorGroup(int x, int y, int z) {
        super(x, y, z);
    }


    public void  addChild(ColorBlock colorBlock){
        colorBlockList.add(colorBlock);
    }
    long lastAnimationTime=0;
    int nowIndex =0;//动画运行到的帧数
    public void update(){
        //每隔1秒展示下一个动画
        if(animations.size()>0) {
            if (play  ) {//正常播放
                if (TimeUtil.getNowMills() - lastAnimationTime > 100) {
                    lastAnimationTime = TimeUtil.getNowMills();
                    nowIndex++;
                    if (nowIndex > animations.size() - 1) {
                        nowIndex = 0;
                    }

                }
              //  animations.get(nowIndex).update();

                for(int i=0;i<colorBlockList.size();i++){
                    ColorBlock colorBlock = animations.get(nowIndex).colorBlockList.get(i);
                    ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),this.x+this.xoffset+colorBlock.x/xzoom,this.y+this.yoffset+colorBlock.y/yzoom,this.z+this.zoffset+colorBlock.z/zzoom, new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), colorBlock.width/xzoom, colorBlock.height/yzoom, colorBlock.thick/zzoom, colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);

                }
                return;
            }/* else {*///现实帧
                //如果停止了播放就显示blockList中的内容
               /* animations.get(nowIndex).update();
                return;*/
           /* }*/
        }
        for(int i=0;i<selectBlockList.size();i++){
            ColorBlock colorBlock = selectBlockList.get(i);
            float[] info  =this.getChildBlockPosition(colorBlock);
            ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig,ShaderManager.lineShaderConfig.getVao(),info[0],info[1],info[2],info[3],info[4],info[5]);
        }

        for(int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock = colorBlockList.get(i);
           // colorBlock.update();
            float[] info  =this.getChildBlockPosition(colorBlock);


            colorBlock.update(info[0],info[1],info[2], info[3],info[4],info[5]);
            //ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),info[0],info[1],info[2],new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), info[3],info[4],info[5],  colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);

        }
    }


    public ColorBlock  selectSingle(GL_Vector from, GL_Vector direction){

        ColorBlock theNearestBlock = getSelectBlock(from,direction);

        if(theNearestBlock!=null){

            selectBlockList.add(theNearestBlock);
            return theNearestBlock;
        }
        return null;
    }
    public ColorBlock getSelectBlock(GL_Vector from, GL_Vector direction ){
        direction=direction.normalize();
        //  startPoint =GamingState.instance.player.getPosition().getClone();

        // LogUtil.println("开始选择");
        Vector3f fromV= new Vector3f(from.x,from.y,from.z);
        Vector3f directionV= new Vector3f(direction.x,direction.y,direction.z);
        float distance =0;
        ColorBlock theNearestBlock = null;
        float[] xiangjiao=null;
        for(int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock =  colorBlockList.get(i);
            float[] info = getChildBlockPosition(colorBlock);
            AABB aabb = new AABB(new Vector3f(info[0],info[1],info[2]),new Vector3f(info[0]+info[3],info[1]+info[4],info[2]+info[5]));

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



            }


        }
        return theNearestBlock;
    }



    public void addWidth(int num){
        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                ColorBlock colorBlock = selectBlockList.get(i);

                colorBlock.addWidth(num);//(false);

            }
        }else{ this.width += num;

        }

    }
    public void addX(int num){


        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                ColorBlock colorBlock = selectBlockList.get(i);

                colorBlock.addX(num);//(false);

            }
        }else{  this.x+=num;

        }
    }

    public void addHeight(int num){


        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                ColorBlock colorBlock = selectBlockList.get(i);

                colorBlock.addHeight(num);//(false);

            }
        }else{  this.height+=num;

        }
    }
    public void addY(int num){



        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                ColorBlock colorBlock = selectBlockList.get(i);

                colorBlock.addY(num);//(false);

            }
        }else{  this.y+=num;

        }
    }

    public void addThick(int num){




        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                ColorBlock colorBlock = selectBlockList.get(i);

                colorBlock.addThick(num);//(false);

            }
        }else{  this.thick+=num;

        }
    }
    public void addZ(int num){


        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                ColorBlock colorBlock = selectBlockList.get(i);

                colorBlock.addZ(num);//(false);

            }
        }else{  this.z+=num;

        }
    }

    public ColorGroup copy(){
        //复制本身


        ColorGroup colorGroup =new ColorGroup( this.x,this.y,this.z,this.width,this.height,this.thick);
        colorGroup.xoffset= this.xoffset;
        colorGroup.yoffset =this.yoffset;
        colorGroup.zoffset =this.zoffset;

        colorGroup.xzoom= this.xzoom;
        colorGroup.yzoom= this.yzoom;
        colorGroup.zzoom= this.zzoom;
        //复制所有child block
        for(int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock = colorBlockList.get(i);
            colorGroup.colorBlockList.add(colorBlock.copy());

        }
        colorGroup.animations=this.animations;

        return colorGroup;
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
float[] info =getChildBlockPosition(colorBlock);
            Vector2f xy = OpenglUtils.wordPositionToXY(ShaderManager.projection,new GL_Vector(info[0],info[1],info[2]), GamingState.instance.camera.Position, GamingState.instance.camera.getViewDir());
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

    public float[] getChildBlockPosition(ColorBlock colorBlock){
        float[] info =new float[6];
        float x = colorBlock.x/xzoom+xoffset + this.x;
        float y = colorBlock.y/yzoom+yoffset + this.y;
        float z = colorBlock.z/zzoom+xoffset + this.z;
        float width = colorBlock.width/xzoom;
        float height =colorBlock.height /yzoom;
        float thick =colorBlock.thick / zzoom;
        return new float[]{x,y,z,width,height,thick};
    }

    public void shootBlock(float x,float y){

        GL_Vector from = GamingState.instance.camera.Position.getClone();
        GL_Vector direction =OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().getClone(),x,Constants.WINDOW_HEIGHT-y);
        direction=direction.normalize();

        // LogUtil.println("开始选择");
        Vector3f fromV= new Vector3f(from.x,from.y,from.z);
        Vector3f directionV= new Vector3f(direction.x,direction.y,direction.z);
        float distance =0;
        ColorBlock theNearestBlock = null;
        float[] xiangjiao=null;
        float[] right=null;
        for(int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock =  colorBlockList.get(i);
           float[] info = this.getChildBlockPosition(colorBlock);
            AABB aabb = new AABB(new Vector3f(info[0],info[1],info[2]),new Vector3f(info[0]+info[3],info[1]+info[4],info[2]+info[5]));

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


                                    float thisX = (from.x + right[0]-this.xoffset-this.x )*this.xzoom;

                                    float thisY = (from.y + right[1]-this.yoffset-this.y )*this.yzoom;
                                    float thisZ = (from.z + right[2]-this.zoffset-this.z )*this.zzoom;

                                    ColorBlock addColorBlock = new ColorBlock((int) thisX, (int) thisY, (int) thisZ);

                                    addColorBlock.rf =  GamingState.editEngine.red;
                                    addColorBlock.gf = GamingState.editEngine.green;
                                    addColorBlock.bf =  GamingState.editEngine.blue;
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
    public void deleteSelect(){
        for (int i = selectBlockList.size() - 1; i >= 0; i--) {
            ColorBlock colorBlock = selectBlockList.get(i);

            colorBlockList.remove(colorBlock);

        }
        selectBlockList.clear();
    }
    boolean play=true;// -2 正常播放 -1 停止

    public void showAnimationFrame(int num){

        play=false;
        nowIndex= num;
        //把主要的对象关联到

        this.colorBlockList = this.animations.get(nowIndex).colorBlockList;
    }

    public void saveToCurFrame(){
       this.animations.set(nowIndex,this.copy());
    }
    public boolean play(){


        play=!play;
        return play;

    }
    public void stop(){
        this.play=false;
    }

    /**
     * 删除当前帧
     */
    public void deleteCurFrame(){
        if(nowIndex< animations.size()){
            this.animations.remove(nowIndex);
        }

    }

    public void setColor(float red, float green, float blue,float alpha) {

        for( int i=selectBlockList.size()-1;i>=0;i--){
            ColorBlock colorBlock  =  selectBlockList.get(i);

            colorBlock.rf = red;
            colorBlock.gf =green;
            colorBlock.bf = blue;
            colorBlock.opacity=alpha;

        }
    }
    public void brushImageOnBlock(float x, float y, float red, float green, float blue) {


        GL_Vector from = GamingState.instance.camera.Position.getClone();
        GL_Vector direction =OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().getClone(),x,Constants.WINDOW_HEIGHT-y);
        direction=direction.normalize();

        // LogUtil.println("开始选择");
        Vector3f fromV= new Vector3f(from.x,from.y,from.z);
        Vector3f directionV= new Vector3f(direction.x,direction.y,direction.z);
        float distance =0;
        ColorBlock theNearestBlock = null;
        float[] xiangjiao=null;
        float[] right=null;
        for(int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock =  colorBlockList.get(i);

            float[] info  =this.getChildBlockPosition(colorBlock);
            AABB aabb = new AABB(new Vector3f(info[0],info[1],info[2]),new Vector3f(info[0]+info[3],info[1]+info[4],info[2]+info[5]));


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
        if(theNearestBlock!=null && theNearestBlock instanceof ImageBlock){
            //计算是那一面
            ImageBlock imageBlock = (ImageBlock)theNearestBlock;


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


                                    //调整
                                    if (face == Constants.BACK) {

                                        imageBlock.back = GamingState.editEngine.nowTextureInfo;

                                    } else  if (face == Constants.FRONT) {
                                        imageBlock.front = GamingState.editEngine.nowTextureInfo;

                                    } else if (face == Constants.LEFT) {

                                        imageBlock.left = GamingState.editEngine.nowTextureInfo;

                                    } else if (face == Constants.RIGHT) {

                                        imageBlock.right = GamingState.editEngine.nowTextureInfo;

                                    } else if (face == Constants.BOTTOM) {
                                        imageBlock.bottom = GamingState.editEngine.nowTextureInfo;
                                    }else if (face == Constants.TOP) {
                                        imageBlock.top = GamingState.editEngine.nowTextureInfo;
                                    }

                                }catch ( Exception e){
                                    e.printStackTrace();
                                }
                                break;


                            }
                        }
                    }
                }
            }
            LogUtil.println("是那个面:"+face);

        }
    }
    public void brushBlock(float x, float y, float red, float green, float blue) {

        ColorBlock block = this.getSelectBlock(GamingState.instance.camera.Position.getClone(), OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().getClone(),x,Constants.WINDOW_HEIGHT-y));
        if(block == null)
            return;
        block.rf = red;
        block.gf = green;
        block.bf = blue;
    }
}
