package com.dozenx.game.engine.edit.view;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.model.ColorBlock;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Vector;

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
    int nowIndex =0;
    public void update(){
        //每隔1秒展示下一个动画
        if(animations.size()>0){
            if(TimeUtil.getNowMills()-lastAnimationTime >200){
                lastAnimationTime=TimeUtil.getNowMills();
                nowIndex++;
                if(nowIndex>animations.size()-1){
                    nowIndex=0;
                }

            }
            animations.get(nowIndex).update();
            return ;
        }
        for(int i=0;i<selectBlockList.size();i++){
            ColorBlock colorBlock = selectBlockList.get(i);

            ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig,ShaderManager.lineShaderConfig.getVao(),this.x+colorBlock.x,this.y+colorBlock.y,this.z+colorBlock.z,colorBlock.width,colorBlock.height,colorBlock.thick);
        }

        for(int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock = colorBlockList.get(i);
            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),this.x+this.xoffset+colorBlock.x/xzoom,this.y+this.yoffset+colorBlock.y/yzoom,this.z+this.zoffset+colorBlock.z/zzoom, new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf), colorBlock.width/xzoom, colorBlock.height/yzoom, colorBlock.thick/zzoom, selectBlockList.size()>0?0.5f:1);

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
            AABB aabb = new AABB(new Vector3f(this.x+colorBlock.x,this.y+colorBlock.y,this.z+colorBlock.z),new Vector3f(this.x+colorBlock.x+colorBlock.width,this.y+colorBlock.y+colorBlock.height,this.z+colorBlock.z+colorBlock.thick));

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
        this.width+=num;
    }
    public void addX(int num){
        this.x+=num;
    }

    public void addHeight(int num){
        this.height+=num;
    }
    public void addY(int num){
        this.y+=num;
    }

    public void addThick(int num){
        this.thick+=num;
    }
    public void addZ(int num){
        this.z+=num;
    }

    public ColorGroup copy(){
        //复制本身


        ColorGroup colorGroup =new ColorGroup( this.x,this.y,this.z,this.width,this.height,this.thick);
        colorGroup.xoffset= this.xoffset;
        colorGroup.yoffset =this.yoffset;
        colorGroup.zoffset =this.zoffset;
        //复制所有child block
        for(int i=0;i<colorBlockList.size();i++){
            ColorBlock colorBlock = colorBlockList.get(i);
            colorGroup.colorBlockList.add(colorBlock.copy());
        }


        return colorGroup;
    }
}
