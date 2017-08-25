package com.dozenx.game.engine.edit.view;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.model.*;
//import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.switcher.Switcher;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.edit.EditEngine;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.util.TimeUtil;
import com.google.gson.JsonArray;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/8/9.
 */
public class ColorGroup extends BaseBlock {


    public ColorGroup(float x,float y,float z,float width,float height,float thick){
        super(x,y,z,width,height,thick);
        /*this.x =x;
        this.y=y;
        this.z=z;
        this.width =width;
        this.height =height;
        this.thick =thick;*/
    }
  /*  @Override
    public void update(float x, float y, float z, float width, float height, float thick) {
        this.update();
    }*/

    public float xoffset=0;
    public float yoffset=0;
    public float zoffset=0;
    public float xzoom=1;
    public float yzoom=1;
    public float zzoom=1;
   public List<BaseBlock> colorBlockList =new ArrayList<BaseBlock>();
    public List<BaseBlock> selectBlockList =new ArrayList<BaseBlock>();
    public List<ColorGroup> animations =new ArrayList<ColorGroup>();
 /*   public ColorGroup(int x, int y, int z) {
        super(x, y, z);
    }*/


    public void  addChild(BaseBlock colorBlock){
        colorBlockList.add(colorBlock);
    }
    long lastAnimationTime=0;
    int nowIndex =0;//动画运行到的帧数
//    public void update(){
//        //每隔1秒展示下一个动画
//        if(animations.size()>0) {
//            if (play  ) {//正常播放
//                if (TimeUtil.getNowMills() - lastAnimationTime > 100) {
//                    lastAnimationTime = TimeUtil.getNowMills();
//                    nowIndex++;
//                    if (nowIndex > animations.size() - 1) {
//                        nowIndex = 0;
//                    }
//
//                }
//              //  animations.get(nowIndex).update();
//
//                for(int i=0;i<animations.get(nowIndex).colorBlockList.size();i++){
//                    BaseBlock  block = animations.get(nowIndex).colorBlockList.get(i);
//                    float[] info  = getChildBlockPosition(block);
//                    block.update(info[0],info[1],info[2],info[3],info[4],info[5]);
//                 //   ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),this.x+this.xoffset+colorBlock.x/xzoom,this.y+this.yoffset+colorBlock.y/yzoom,this.z+this.zoffset+colorBlock.z/zzoom, new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), colorBlock.width/xzoom, colorBlock.height/yzoom, colorBlock.thick/zzoom, colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);
//
//                }
//                return;
//            }/* else {*///现实帧
//                //如果停止了播放就显示blockList中的内容
//               /* animations.get(nowIndex).update();
//                return;*/
//           /* }*/
//        }
//        for(int i=0;i<selectBlockList.size();i++){
//            BaseBlock colorBlock = selectBlockList.get(i);
//            float[] info  =this.getChildBlockPosition(colorBlock);
//            ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig,ShaderManager.lineShaderConfig.getVao(),info[0],info[1],info[2],info[3],info[4],info[5]);
//        }
//
//        for(int i=0;i<colorBlockList.size();i++){
//            BaseBlock colorBlock = colorBlockList.get(i);
//           // colorBlock.update();
//            float[] info  =this.getChildBlockPosition(colorBlock);
//
//
//            colorBlock.update(info[0],info[1],info[2], info[3],info[4],info[5]);
//            //ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),info[0],info[1],info[2],new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), info[3],info[4],info[5],  colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);
//
//        }
//    }


    public BaseBlock  selectSingle(GL_Vector from, GL_Vector direction){

        BaseBlock theNearestBlock = getSelectBlock(from,direction);

        if(theNearestBlock!=null){

            selectBlockList.add(theNearestBlock);
            return theNearestBlock;
        }
        return null;
    }
    public BaseBlock getSelectBlock(GL_Vector from, GL_Vector direction ){
        direction=direction.normalize();
        //  startPoint =GamingState.instance.player.getPosition().getClone();

        // LogUtil.println("开始选择");
        Vector3f fromV= new Vector3f(from.x,from.y,from.z);
        Vector3f directionV= new Vector3f(direction.x,direction.y,direction.z);
        float distance =0;
        BaseBlock theNearestBlock = null;
        float[] xiangjiao=null;
        for(int i=0;i<colorBlockList.size();i++){
            BaseBlock colorBlock =  colorBlockList.get(i);
            float[] info = getChildBlockPosition(colorBlock,this.x,this.y,this.z);
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


    @Override
    public void addWidth(float num){
        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addWidth(num);//(false);

            }
        }else{ this.width += num;

        }

    }
    @Override
    public void addX(float num){


        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addX(num);//(false);

            }
        }else{  this.x+=num;

        }
    }
    @Override
    public void addHeight(float num){


        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addHeight(num);//(false);

            }
        }else{  this.height+=num;

        }
    }
    @Override
    public void addY(float num){



        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addY(num);//(false);

            }
        }else{  this.y+=num;

        }
    }
@Override
    public void addThick(float num){




        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addThick(num);//(false);

            }
        }else{  this.thick+=num;

        }
    }@Override
    public void addZ(float num){


        if(Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addZ(num);//(false);

            }
        }else{  this.z+=num;

        }
    }


    public ColorGroup copy(){
        //复制本身


        ColorGroup colorGroup =new ColorGroup( this.x,this.y,this.z,this.width,this.height,this.thick);
        colorGroup.id =id;
        colorGroup.xoffset= this.xoffset;
        colorGroup.yoffset =this.yoffset;
        colorGroup.zoffset =this.zoffset;
        colorGroup.live = this.live;
        colorGroup.xzoom= this.xzoom;
        colorGroup.yzoom= this.yzoom;
        colorGroup.zzoom= this.zzoom;
        //复制所有child block
        for(int i=0;i<colorBlockList.size();i++){
            BaseBlock colorBlock = colorBlockList.get(i);
            colorGroup.colorBlockList.add(colorBlock.copy());

        }
        for(int i=0;i<this.animations.size();i++){
            animations.get(i).animations.clear();
        }
        colorGroup.animations=this.animations;//没有深层次拷贝 担心引起无限循环 但问题也就出在这里 animations的对象里 colorgroup还有animations 需要斩草除根


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
            BaseBlock colorBlock  =  colorBlockList.get(i);
          /*  GL_Vector[] gl_vectors = BoxModel.getSmaillPoint(colorBlock.x,colorBlock.y,colorBlock.z,colorBlock.width, colorBlock.height, colorBlock.thick);
           for(){

           }*/
float[] info =getChildBlockPosition(colorBlock,this.x,this.y,this.z);
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

    public float[] getChildBlockRelativePosition(BaseBlock colorBlock,float nowX,float nowY,float nowZ){
        float[] info =new float[6];
        float x = (colorBlock.x+xoffset)/xzoom ;
        float y = (colorBlock.y+yoffset)/yzoom ;
        float z = (colorBlock.z+zoffset)/zzoom;
        float width = (colorBlock.width==0?1:colorBlock.width)/xzoom;
        float height =(colorBlock.height==0?1:colorBlock.height)/yzoom;
        float thick =(colorBlock.thick==0?1:colorBlock.thick) / zzoom;
        return new float[]{x,y,z,width,height,thick};
    }
    public float[] getChildBlockPosition(BaseBlock colorBlock,float nowX,float nowY,float nowZ){
        float[] info =new float[6];
        float x = (colorBlock.x+xoffset)/xzoom + nowX;
        float y = (colorBlock.y+yoffset)/yzoom + nowY;
        float z = (colorBlock.z+zoffset)/zzoom + nowZ;
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
        BaseBlock theNearestBlock = null;
        float[] xiangjiao=null;
        float[] right=null;
        for(int i=0;i<colorBlockList.size();i++){
            BaseBlock colorBlock =  colorBlockList.get(i);
           float[] info = this.getChildBlockPosition(colorBlock,this.x,this.y,this.z);
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

                                    BaseBlock addColorBlock =GamingState.editEngine.readyShootBlock.copy();
                                    GamingState.editEngine.set(addColorBlock,(int)thisX,(int)thisY,(int)thisZ,addColorBlock.width,addColorBlock.height,addColorBlock.thick,GamingState.editEngine.red,
                                            GamingState.editEngine.green,
                                            GamingState.editEngine.blue,
                                            GamingState.editEngine.alpha );


                                 /*   addColorBlock.rf =  GamingState.editEngine.red;
                                    addColorBlock.gf = GamingState.editEngine.green;
                                    addColorBlock.bf =  GamingState.editEngine.blue;*/
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
            BaseBlock colorBlock = selectBlockList.get(i);

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
        ColorGroup colorGroup = this.copy();
        colorGroup.animations .clear();//防止无限循环
       this.animations.set(nowIndex,colorGroup);
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
            BaseBlock colorBlock  =  selectBlockList.get(i);
        if(colorBlock instanceof ColorBlock){
            ColorBlock colorBlock1 =(ColorBlock) colorBlock;
            colorBlock1.rf = red;
            colorBlock1.gf =green;
            colorBlock1.bf = blue;
            colorBlock1.opacity=alpha;
        }


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
        BaseBlock theNearestBlock = null;
        float[] xiangjiao=null;
        float[] right=null;
        for(int i=0;i<colorBlockList.size();i++){
            BaseBlock colorBlock =  colorBlockList.get(i);

            float[] info  =this.getChildBlockPosition(colorBlock,x,y,z);
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

        BaseBlock block = this.getSelectBlock(GamingState.instance.camera.Position.getClone(), OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().getClone(),x,Constants.WINDOW_HEIGHT-y));
        if(block == null)
            return;
        if(block instanceof  ColorBlock){
            ((ColorBlock) block).rf = red;
            ((ColorBlock) block).gf = green;
            ((ColorBlock) block).bf = blue;
        }

    }

    @Override
    public IBlock clone() {
        return null;
    }

    public ColorGroup(){

    }
    public String toString(){


        StringBuffer buffer =new StringBuffer();
        buffer.append("{").append("name:'").append(this.name).append("',")
                .append("blocktype:'groupblock',")
                .append("width:").append(this.width).append(",")
                .append("height:").append(this.height).append(",")
                .append("thick:").append(this.thick).append(",")
                .append("x:").append(this.x).append(",")
                .append("y:").append(this.y).append(",")
                .append("z:").append(this.z).append(",")


                .append("xoffset:").append(xoffset).append(",")
                .append("yoffset:").append(yoffset).append(",")
                .append("zoffset:").append(zoffset).append(",")
                .append("xzoom:").append(xzoom).append(",")
                .append("yzoom:").append(yzoom).append(",")
                .append("zzoom:").append(zzoom).append(",")
                .append("live:").append(live).append(",")
                .append("children:[");


           for(BaseBlock block : colorBlockList){
               buffer.append(block.toString()).append(",");
           }
        buffer.append("],");
        buffer.append("animation:[");
        if(animations!=null){
            for(BaseBlock block : animations){
                buffer.append(block.toString()).append(",");
            }
        }

        buffer .append("]}");





        return buffer.toString();


    }
    public static ColorGroup  parse(JSONObject map){
        ColorGroup group =new ColorGroup();
        parse(group,map);
        group.xoffset=MapUtil.getFloatValue(map,"xoffset");
        group.yoffset=MapUtil.getFloatValue(map,"yoffset");
        group.zoffset=MapUtil.getFloatValue(map,"zoffset");

        group.xzoom=MapUtil.getFloatValue(map,"xzoom");
        group.yzoom=MapUtil.getFloatValue(map,"yzoom");
        group.zzoom=MapUtil.getFloatValue(map,"zzoom");
        group.live = MapUtil.getBooleanValue(map,"live",false);
        JSONArray ary = (JSONArray)map.get("children");
        if(ary!=null) {
            for (int i = 0; i < ary.size(); i++) {
                JSONObject object = (JSONObject) ary.get(i);

                String blockType = (String) object.get("blocktype");
                if ("imageblock".equals(blockType)) {
                    ImageBlock imageBlock = ImageBlock.parse(object);
                    group.addChild(imageBlock);
                } else if ("colorblock".equals(blockType)) {
                    ColorBlock colorBlock = ColorBlock.parse(object);
                    group.addChild(colorBlock);
                }else if("rotatecolorblock".equals(blockType)){
                    RotateColorBlock2 shape = RotateColorBlock2.parse(object);
                    group.addChild(shape);
                }else if("groupblock".equals(blockType)){
                    ColorGroup shape = ColorGroup.parse(object);
                    group.addChild(shape);
                }


            }
        }

        JSONArray animation = (JSONArray)map.get("animation");

        for(int i=0;i<animation.size();i++){
            JSONObject object = (JSONObject)animation.get(i);

            String blockType = (String)object.get("blocktype");
            if("groupblock".equals(blockType)){
                ColorGroup colorGroup = ColorGroup.parse(object);
                group.animations.add(colorGroup);
            }/*else
            if("imageblock".equals(blockType)){
                ImageBlock imageBlock = ImageBlock.parse(object);
                group.animations.add(imageBlock);
            }else if("colorblock".equals(blockType)){
                ColorBlock colorBlock = ColorBlock.parse(object);
                group.animations.add(colorBlock);
            }*/


        }


        return group;

    }
    public void rotateX(float value){


        adjustRotatex(value);
    }


    public void rotateY(float value){
      //  adjustRotateY(value);
        super.rotateY(value);
    }
    public void rotateZ(float value){
        adjustRotateZ(value);
    }


    public void adjustRotatex(float value){
        for(int i=0;i<selectBlockList.size();i++){
            BaseBlock colorBlock  = selectBlockList.get(i);
            if(colorBlock instanceof RotateColorBlock2){
                ((RotateColorBlock2) colorBlock) .rotateX(value);
            }
            if(colorBlock instanceof ColorGroup){
                ((ColorGroup) colorBlock) .rotateX(value);
            }
        }
    }

    public void adjustRotateY(float value){
        for(int i=0;i<selectBlockList.size();i++){
            BaseBlock colorBlock  = selectBlockList.get(i);
           if(colorBlock instanceof RotateColorBlock2){
            ((RotateColorBlock2) colorBlock) .rotateY(value);
           // colorBlock .rotateY(value);
            }
            if(colorBlock instanceof ColorGroup){
                ((ColorGroup) colorBlock) .rotateY(value);
            }
        }
    }
    public void adjustRotateZ(float value){
        for(int i=0;i<selectBlockList.size();i++){
            BaseBlock colorBlock  = selectBlockList.get(i);
            if(colorBlock instanceof RotateColorBlock2){
                ((RotateColorBlock2) colorBlock) .rotateZ(value);
            }
            if(colorBlock instanceof ColorGroup){
                ((ColorGroup) colorBlock) .rotateZ(value);
            }
        }
    }


    @Override
    public void renderShader(ShaderConfig config, Vao vao, GL_Matrix matrix) {


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

                for(int i=0;i<animations.get(nowIndex).colorBlockList.size();i++){
                    BaseBlock  block = animations.get(nowIndex).colorBlockList.get(i);
                    float[] info  = getChildBlockRelativePosition(block,x,y,z);
                    //GL
                    GL_Vector[] childPoints = BoxModel.getSmallPoint(info[0],info[1],info[2],info[3],info[4],info[5]);
                    for(int k =0 ;k<childPoints.length;k++){
                        childPoints[k] = GL_Matrix.multiply(matrix,childPoints[k]);
                    }
                    block.renderShaderInGivexyzwht(config,vao, matrix,childPoints);
                    //   ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),this.x+this.xoffset+colorBlock.x/xzoom,this.y+this.yoffset+colorBlock.y/yzoom,this.z+this.zoffset+colorBlock.z/zzoom, new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), colorBlock.width/xzoom, colorBlock.height/yzoom, colorBlock.thick/zzoom, colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);

                }
                return;
            }/* else {*///现实帧
            //如果停止了播放就显示blockList中的内容
               /* animations.get(nowIndex).update();
                return;*/
           /* }*/
        }
        for(int i=0;i<selectBlockList.size();i++){
            BaseBlock block = selectBlockList.get(i);
            float[] info  =this.getChildBlockRelativePosition(block,x,y,z);


            GL_Vector[] childPoints = BoxModel.getSmallPoint(info[0],info[1],info[2],info[3],info[4],info[5]);
            for(int k =0 ;k<childPoints.length;k++){
                //childPoints[i] = GL_Matrix.multiply(childPoints[i],matrix);

                childPoints[k] = GL_Matrix.multiply(matrix,childPoints[k]);
            }
            // block.renderShaderInGivexyzwht(config,vao, matrix,childPoints);
        }

        for(int i=0;i<colorBlockList.size();i++){
            BaseBlock block = colorBlockList.get(i);
            // colorBlock.update();
            float[] info  =this.getChildBlockRelativePosition(block,x,y,z);


            GL_Vector[] childPoints = BoxModel.getSmallPoint(info[0],info[1],info[2],info[3],info[4],info[5]);
            for(int k =0 ;k<childPoints.length;k++){
                // childPoints[i] = GL_Matrix.multiply(childPoints[i],matrix);
                childPoints[k] = GL_Matrix.multiply(matrix,childPoints[k]);
            }
            block.renderShaderInGivexyzwht(config,vao, matrix,childPoints);

            //ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),info[0],info[1],info[2],new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), info[3],info[4],info[5],  colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);

        }


//        for(int i=0;i<colorBlockList.size();i++){
//            BaseBlock block = colorBlockList.get(i);
//            // colorBlock.update();
//            float[] info  =this.getChildBlockPosition(block,x,y,z);
//
//            GL_Vector[] childPoints = BoxModel.getSmallPoint(info[0],info[1],info[2],info[3],info[4],info[5]);
//            for(int k =0 ;k<childPoints.length;k++){
//                childPoints[i] = GL_Matrix.multiply(childPoints[i],matrix);
//            }
//            block.renderShaderInGivexyzwht(config,vao, matrix,childPoints);
//
//            //colorBlock.update(info[0],info[1],info[2], info[3],info[4],info[5]);
//
//            //ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),info[0],info[1],info[2],new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), info[3],info[4],info[5],  colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);
//
//        }
    }



    @Override
    public void render(ShaderConfig config, Vao vao, float x, float y, float z, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {



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

                for(int i=0;i<animations.get(nowIndex).colorBlockList.size();i++){
                    BaseBlock  block = animations.get(nowIndex).colorBlockList.get(i);
                    float[] info  = getChildBlockPosition(block,x,y,z);
                    block.renderShaderInGivexyzwht(config,vao,info[0],info[1],info[2],info[3],info[4],info[5],top,bottom,left,right,front,back );
                    //   ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),this.x+this.xoffset+colorBlock.x/xzoom,this.y+this.yoffset+colorBlock.y/yzoom,this.z+this.zoffset+colorBlock.z/zzoom, new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), colorBlock.width/xzoom, colorBlock.height/yzoom, colorBlock.thick/zzoom, colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);

                }
                return;
            }/* else {*///现实帧
            //如果停止了播放就显示blockList中的内容
               /* animations.get(nowIndex).update();
                return;*/
           /* }*/
        }
        for(int i=0;i<selectBlockList.size();i++){
            BaseBlock colorBlock = selectBlockList.get(i);
            float[] info  =this.getChildBlockPosition(colorBlock,x,y,z);
            ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig,ShaderManager.lineShaderConfig.getVao(),info[0],info[1],info[2],info[3],info[4],info[5]);
        }

        for(int i=0;i<colorBlockList.size();i++){
            BaseBlock block = colorBlockList.get(i);
            // colorBlock.update();
            float[] info  =this.getChildBlockPosition(block,x,y,z);


            block.renderShaderInGivexyzwht(config,vao,info[0],info[1],info[2],info[3],info[4],info[5],top,bottom,left,right,front,back );

            //ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),info[0],info[1],info[2],new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), info[3],info[4],info[5],  colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);

        }
//
//
//        for(int i=0;i<colorBlockList.size();i++){
//            BaseBlock block = colorBlockList.get(i);
//            // colorBlock.update();
//            float[] info  =this.getChildBlockPosition(block,x,y,z);
//
//            block.renderShaderInGivexyzwht(config,vao,info[0],info[1],info[2],info[3],info[4],info[5],top,bottom,left,right,front,back );
//
//            //colorBlock.update(info[0],info[1],info[2], info[3],info[4],info[5]);
//
//            //ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),info[0],info[1],info[2],new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), info[3],info[4],info[5],  colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);
//
//        }
    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, float x, float y, float z, float width, float height, float thick, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, GL_Matrix matrix, GL_Vector[] childPoints) {

    }


    public  void reComputePoints(){
        this.points = BoxModel.getSmallPoint(0,0 ,0,width,height,thick);

        GL_Matrix rotateMatrix = GL_Matrix.multiply(GL_Matrix.translateMatrix(width/2,0,thick/2),GL_Matrix.rotateMatrix(0,this.dir*3.14f/2,0));
        for(int i=0;i<points.length;i++){
            points[i] = rotateMatrix.multiply(rotateMatrix,points[i]);
           /* points[i].x+=width/2;
            points[i].z+=thick/2;*/
        }

        for(BaseBlock block:colorBlockList){
          //float[] info =  this.getChildBlockRelativePosition(block,x,y,z);
            block.reComputePoints(rotateMatrix);
        }
    }
}
