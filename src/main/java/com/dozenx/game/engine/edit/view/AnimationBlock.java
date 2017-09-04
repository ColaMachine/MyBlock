package com.dozenx.game.engine.edit.view;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.model.*;
import cola.machine.game.myblocks.switcher.Switcher;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import cola.machine.game.myblocks.model.ColorBlock;

/**
 * Created by dozen.zhang on 2017/8/9.
 */
public class AnimationBlock extends GroupBlock {

    public AnimationBlock(float x, float y, float z, float width, float height, float thick) {
        super(x, y, z, width, height, thick);
        /*
         * this.x =x; this.y=y; this.z=z; this.width =width; this.height
         * =height; this.thick =thick;
         */
    }
    /*
     * @Override public void update(float x, float y, float z, float width,
     * float height, float thick) { this.update(); }
     */

    public float xoffset = 0;
    public float yoffset = 0;
    public float zoffset = 0;
    public float xzoom = 1;
    public float yzoom = 1;
    public float zzoom = 1;

    public List<GroupBlock> animations = new ArrayList<GroupBlock>();
    public String state;
    public Map<String, List<GroupBlock>> animationMap = new HashMap<String, List<GroupBlock>>();
    /*
     * public ColorGroup(int x, int y, int z) { super(x, y, z); }
     */


    long lastAnimationTime = 0;
    int nowIndex = 0;// 动画运行到的帧数


    public AnimationBlock copy() {
        // 复制本身

        AnimationBlock animationBlock = new AnimationBlock(this.x, this.y, this.z, this.width, this.height, this.thick);
        animationBlock.id = id;
        animationBlock.xoffset = this.xoffset;
        animationBlock.yoffset = this.yoffset;
        animationBlock.zoffset = this.zoffset;
        animationBlock.live = this.live;
        animationBlock.xzoom = this.xzoom;
        animationBlock.yzoom = this.yzoom;
        animationBlock.zzoom = this.zzoom;
        // 复制所有child block
        for (int i = 0; i < colorBlockList.size(); i++) {
            BaseBlock colorBlock = colorBlockList.get(i);
            animationBlock.colorBlockList.add(colorBlock.copy());

        }
    /*    if (animations != null)
            for (int i = 0; i < this.animations.size(); i++) {
                if (animations.get(i) != null && animations.get(i).animations != null) {
                    animations.get(i).animations.clear();
                }
            }*/

        
        if (animationMap != null)
            animationBlock.animationMap = this.animationMap;
        
        animationBlock.animations = this.animations;// 没有深层次拷贝 担心引起无限循环 但问题也就出在这里
                                                // animations的对象里
                                                // colorgroup还有animations 需要斩草除根

        animationBlock.reComputePoints();
        return animationBlock;
    }

    /**
     * 深处拷贝 拷贝animation
     * 
     * @return
     * @author 张智威
     * @date 2017年8月29日 下午4:17:21
     */
    public AnimationBlock deepcopy() {
        // 复制本身

        AnimationBlock animationBlock = new AnimationBlock(this.x, this.y, this.z, this.width, this.height, this.thick);
        animationBlock.id = id;
        animationBlock.xoffset = this.xoffset;
        animationBlock.yoffset = this.yoffset;
        animationBlock.zoffset = this.zoffset;
        animationBlock.live = this.live;
        animationBlock.xzoom = this.xzoom;
        animationBlock.yzoom = this.yzoom;
        animationBlock.zzoom = this.zzoom;
        // 复制所有child block
        for (int i = 0; i < colorBlockList.size(); i++) {
            BaseBlock block = colorBlockList.get(i);
            animationBlock.colorBlockList.add(block.copy());

        }
        /*for (int i = 0; i < this.animations.size(); i++) {
            if (animations.get(i).animations != null) {
                animations.get(i).animations.clear();
            }
        }*/
        animationBlock.animations = this.animations;// 没有深层次拷贝 担心引起无限循环 但问题也就出在这里
                                                // animations的对象里
                                                // colorgroup还有animations 需要斩草除根

        animationBlock.reComputePoints();
        return animationBlock;
    }

    boolean play = true;// -2 正常播放 -1 停止

    public void showAnimationFrame(int num) {

        play = false;
        nowIndex = num;
        // 把主要的对象关联到
        this.colorBlockList=new ArrayList();
        for(BaseBlock block :this.animations.get(nowIndex).colorBlockList){
            BaseBlock copy =block.copy();
            copy.reComputePointsInGroup();
            this.colorBlockList.add(copy);//解决copy后points不对的问题
        }
        //this.colorBlockList = this.animations.get(nowIndex).colorBlockList;
    }

    public void saveToCurFrame() {
        AnimationBlock animationBlock = this.copy();
        GroupBlock groupBlock =new GroupBlock();
        groupBlock.colorBlockList = animationBlock.colorBlockList;
       // animationBlock.animations = null;// 防止无限循环 不做这一步 在下一次saveToCurFrame的时候
                                     // 会把子节点的animation.clear 由于
                                     // 父子animiations都是指向同一个
                                     // name必然会导致animations瞬间空了 下面的代码就会爆数组越界错误
        this.animations.set(nowIndex, groupBlock);
    }

    public boolean play() {
        nowIndex=0;
        play = !play;
        return play;

    }

    public void stop() {
        this.play = false;
    }

    /**
     * 删除当前帧
     */
    public void deleteCurFrame() {
        if (nowIndex < animations.size()) {
            this.animations.remove(nowIndex);
        }

    }


    @Override
    public IBlock clone() {
        return null;
    }

    public AnimationBlock() {

    }

    public String toString() {
        //开始清理
        //遍历animationMap下的所有colorgroup 清理他们的animationMap

        StringBuffer buffer = new StringBuffer();
        buffer.append("{").append("blocktype:'animationblock',")
                .append(toBaseBlockString())
                .append("xoffset:").append(xoffset).append(",").append("yoffset:").append(yoffset).append(",")
                .append("zoffset:").append(zoffset).append(",").append("xzoom:").append(xzoom).append(",")
                .append("yzoom:").append(yzoom).append(",").append("zzoom:").append(zzoom).append(",")

                .append("children:[");
        int index = 0 ;
        for (BaseBlock block : colorBlockList) {
            if(index>0){
                buffer.append(",");
            }
            buffer.append(block.toString());
            
            index++;
        }
        buffer.append("],");
        
        //=======child end ===========
        //========animation start==============
        buffer.append("animation:[");
        index = 0;
        if (animations != null) {
            for (BaseBlock block : animations) {
                if(block instanceof AnimationBlock){
                    AnimationBlock animationBlock = (AnimationBlock)block;
                    animationBlock.animations=null;
                    animationBlock.animationMap = null;
                    
                }
                if (index >= 1) {
                    buffer.append(",");
                }
                buffer.append(block.toString());
                index++;
            }
        }

        buffer.append("],");

        if (animationMap != null) {
            buffer.append("animationMap:{");
            Map<String, List<GroupBlock>> map = animationMap;

             index = 0;
            for (Map.Entry<String, List<GroupBlock>> entry : map.entrySet()) {
                if (index >= 1) {
                    buffer.append(",");
                }
                
                List<GroupBlock> list = entry.getValue();
                for(GroupBlock animationBlock : list){
                   
                        //ColorGroup colorGroup = (ColorGroup)block;
//                        animationBlock.animations=null;
//                        animationBlock.animationMap = null;
                        
                   
                }
                String name = entry.getKey();
                buffer.append(name + ":[");
                if (list != null) {
                    for (BaseBlock block : list) {
                        buffer.append(block.toString()).append(",");
                    }
                }

                buffer.append("]");
                index++;

            }

            buffer.append("}");
        }
        buffer.append("}");
        return buffer.toString();

    }

    public static AnimationBlock parse(JSONObject map) {
        AnimationBlock group = new AnimationBlock();
        parseAnimationBlock(group, map);


        return group;

    }

    public static void parseAnimationBlock (AnimationBlock group ,JSONObject map ){
        parse(group, map);
        group.xoffset = MapUtil.getFloatValue(map, "xoffset");
        group.yoffset = MapUtil.getFloatValue(map, "yoffset");
        group.zoffset = MapUtil.getFloatValue(map, "zoffset");

        group.xzoom = MapUtil.getFloatValue(map, "xzoom");
        group.yzoom = MapUtil.getFloatValue(map, "yzoom");
        group.zzoom = MapUtil.getFloatValue(map, "zzoom");
        group.live = MapUtil.getBooleanValue(map, "live", false);
        JSONArray ary = (JSONArray) map.get("children");
        if (ary != null) {
            for (int i = 0; i < ary.size(); i++) {
                JSONObject object = (JSONObject) ary.get(i);

                String blockType = (String) object.get("blocktype");
                if ("imageblock".equals(blockType)) {
                    ImageBlock imageBlock = ImageBlock.parse(object);
                    group.addChild(imageBlock);
                } else if ("colorblock".equals(blockType)) {
                    ColorBlock colorBlock = ColorBlock.parse(object);
                    group.addChild(colorBlock);
                } else if ("rotatecolorblock".equals(blockType)) {
                    RotateColorBlock2 shape = RotateColorBlock2.parse(object);
                    group.addChild(shape);
                } else if ("groupblock".equals(blockType)) {
                    GroupBlock shape = GroupBlock.parse(object);
                    group.addChild(shape);
                }

            }
        }

        JSONArray animation = (JSONArray) map.get("animation");

        for (int i = 0; i < animation.size(); i++) {
            JSONObject object = (JSONObject) animation.get(i);

            String blockType = (String) object.get("blocktype");
            if ("groupblock".equals(blockType)) {
                GroupBlock animationBlock = GroupBlock.parse(object);
                group.animations.add(animationBlock);
            } /*
               * else if("imageblock".equals(blockType)){ ImageBlock imageBlock
               * = ImageBlock.parse(object); group.animations.add(imageBlock);
               * }else if("colorblock".equals(blockType)){ ColorBlock colorBlock
               * = ColorBlock.parse(object); group.animations.add(colorBlock); }
               */

        }

        JSONObject animationMap = (JSONObject) map.get("animationMap");
        if(animationMap!= null ){
            for (Map.Entry<String, Object> entry : animationMap.entrySet()) {
                String name = entry.getKey();
                JSONArray animationAry = (JSONArray)entry.getValue();

                List<GroupBlock> list =new ArrayList<>();
                for (int i = 0; i < animationAry.size(); i++) {
                    JSONObject object = (JSONObject) animationAry.get(i);

                    String blockType = (String) object.get("blocktype");
                    if ("groupblock".equals(blockType)) {
                        GroupBlock animationBlock = GroupBlock.parse(object);
                        list.add(animationBlock);
                    }

                }
                group.animationMap.put(name,list);

            }
        }

    }
    @Override // 具体场景中 生物有旋转身体的 需要用到
    public void renderShader(ShaderConfig config, Vao vao, GL_Matrix matrix) {

        // 每隔1秒展示下一个动画
        if (animations.size() > 0) {
            if (play) {// 正常播放
                if (TimeUtil.getNowMills() - lastAnimationTime > 100) {
                    lastAnimationTime = TimeUtil.getNowMills();
                    nowIndex++;
                    if (nowIndex > animations.size() - 1) {
                        nowIndex = 0;
                    }

                }
                // animations.get(nowIndex).update();

                for (int i = 0; i < animations.get(nowIndex).colorBlockList.size(); i++) {
                    BaseBlock block = animations.get(nowIndex).colorBlockList.get(i);
                    float[] info = getChildBlockRelativePosition(block, x, y, z);
                    // GL
                   /* GL_Vector[] childPoints = BoxModel.getSmallPoint(info[0], info[1], info[2], info[3], info[4],
                            info[5]);
                    for (int k = 0; k < childPoints.length; k++) {
                        childPoints[k] = GL_Matrix.multiply(matrix, childPoints[k]);
                    }*/
                    //block.reComputePoints();
                    //block.reComputePoints(matrix);
                  // this.reComputePoints();
                    block.renderShaderInGivexyzwht(config, vao, matrix, block.points);
                    // ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,
                    // ShaderManager.anotherShaderConfig.getVao(),this.x+this.xoffset+colorBlock.x/xzoom,this.y+this.yoffset+colorBlock.y/yzoom,this.z+this.zoffset+colorBlock.z/zzoom,
                    // new GL_Vector(colorBlock.rf, colorBlock.gf,
                    // colorBlock.bf), colorBlock.width/xzoom,
                    // colorBlock.height/yzoom, colorBlock.thick/zzoom,
                    // colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);

                }
                return;
            } /* else { */// 现实帧
            // 如果停止了播放就显示blockList中的内容
            /*
             * animations.get(nowIndex).update(); return;
             */
            /* } */
        }
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock block = selectBlockList.get(i);
            float[] info = this.getChildBlockRelativePosition(block, x, y, z);

            GL_Vector[] childPoints = BoxModel.getSmallPoint(info[0], info[1], info[2], info[3], info[4], info[5]);
            for (int k = 0; k < childPoints.length; k++) {
                // childPoints[i] = GL_Matrix.multiply(childPoints[i],matrix);

                childPoints[k] = GL_Matrix.multiply(matrix, childPoints[k]);
            }
            // block.renderShaderInGivexyzwht(config,vao, matrix,childPoints);
        }

        for (int i = 0; i < colorBlockList.size(); i++) {
            BaseBlock block = colorBlockList.get(i);
            // colorBlock.update();
            float[] info = this.getChildBlockRelativePosition(block, x, y, z);

            GL_Vector[] childPoints = BoxModel.getSmallPoint(info[0], info[1], info[2], info[3], info[4], info[5]);
            for (int k = 0; k < childPoints.length; k++) {
                // childPoints[i] = GL_Matrix.multiply(childPoints[i],matrix);
                childPoints[k] = GL_Matrix.multiply(matrix, childPoints[k]);
            }
            block.renderShaderInGivexyzwht(config, vao, matrix, childPoints);

            // ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,
            // ShaderManager.anotherShaderConfig.getVao(),info[0],info[1],info[2],new
            // GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf),
            // info[3],info[4],info[5],
            // colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);

        }

        // for(int i=0;i<colorBlockList.size();i++){
        // BaseBlock block = colorBlockList.get(i);
        // // colorBlock.update();
        // float[] info =this.getChildBlockPosition(block,x,y,z);
        //
        // GL_Vector[] childPoints =
        // BoxModel.getSmallPoint(info[0],info[1],info[2],info[3],info[4],info[5]);
        // for(int k =0 ;k<childPoints.length;k++){
        // childPoints[i] = GL_Matrix.multiply(childPoints[i],matrix);
        // }
        // block.renderShaderInGivexyzwht(config,vao, matrix,childPoints);
        //
        // //colorBlock.update(info[0],info[1],info[2],
        // info[3],info[4],info[5]);
        //
        // //ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,
        // ShaderManager.anotherShaderConfig.getVao(),info[0],info[1],info[2],new
        // GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf),
        // info[3],info[4],info[5],
        // colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);
        //
        // }
    }
    //recompute 传入transformmatrix 计算points
    //渲染的时候根据points 渲染

    @Override // 再editegine中绘制 或者真实环境中需要用到
    public void render(ShaderConfig config, Vao vao, float x, float y, float z, boolean top, boolean bottom,
            boolean left, boolean right, boolean front, boolean back) {
//GL_Matrix matrix =GL_Matrix.translateMatrix(x, y, z);

GL_Matrix matrix = GL_Matrix.multiply(GL_Matrix.multiply(GL_Matrix.translateMatrix(x+width/2,y,z+thick/2),GL_Matrix.rotateMatrix(0,this.dir*3.14f/2,0)),GL_Matrix.translateMatrix(-width/2,0,-thick/2) );
        if (animations.size() > 0) {
            if (play) {// 正常播放
                if (TimeUtil.getNowMills() - lastAnimationTime > 100) {//这里的需求是动画按照正常流转 运行到最后一帧的时候保持最后一帧的状态 并且不动了 等待下一次动画的唤起
                    lastAnimationTime = TimeUtil.getNowMills();
                    nowIndex++;
                    if (nowIndex > animations.size() - 1) {
                        nowIndex = 0;
                        play=false;
                        this.colorBlockList =  animations.get(animations.size() - 1).colorBlockList;
                        return ;
                       
                    }

                }
                // animations.get(nowIndex).update();

                for (int i = 0; i < animations.get(nowIndex).colorBlockList.size(); i++) {
                    BaseBlock block = animations.get(nowIndex).colorBlockList.get(i);
                    //System.out.println(nowIndex);
                    float[] info = getChildBlockPosition(block, x, y, z);
                    block.renderShaderInGivexyzwht(config, vao,  matrix,block.points);
                   

                }
                return;
            } /* else { */// 现实帧
            // 如果停止了播放就显示blockList中的内容
            /*
             * animations.get(nowIndex).update(); return;
             */
            /* } */
        }
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock colorBlock = selectBlockList.get(i);
            float[] info = this.getChildBlockPosition(colorBlock, x, y, z);
            ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig, ShaderManager.lineShaderConfig.getVao(),
                    info[0], info[1], info[2], info[3], info[4], info[5]);
        }

        for (int i = 0; i < colorBlockList.size(); i++) {
            BaseBlock block = colorBlockList.get(i);
            // colorBlock.update();
            float[] info = this.getChildBlockPosition(block, x, y, z);
            block.reComputePoints();
            block.renderShaderInGivexyzwht(config, vao,matrix, block.points);


        }
    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, float parentX, float parentY, float parentZ,
            float x, float y, float z, float width, float height, float thick, boolean top, boolean bottom,
            boolean left, boolean right, boolean front, boolean back) {

    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, GL_Matrix matrix, GL_Vector[] childPoints) {
        if (animations.size() > 0) {
            if (play) {// 正常播放
                if (TimeUtil.getNowMills() - lastAnimationTime > 100) {//这里的需求是动画按照正常流转 运行到最后一帧的时候保持最后一帧的状态 并且不动了 等待下一次动画的唤起
                    lastAnimationTime = TimeUtil.getNowMills();
                    nowIndex++;
                    if (nowIndex > animations.size() - 1) {
                        nowIndex = 0;
                        play=false;
                        this.colorBlockList =  animations.get(animations.size() - 1).colorBlockList;
                        return ;
                       
                    }

                }
                // animations.get(nowIndex).update();

                for (int i = 0; i < animations.get(nowIndex).colorBlockList.size(); i++) {
                    BaseBlock block = animations.get(nowIndex).colorBlockList.get(i);
                    //System.out.println(nowIndex);
                    float[] info = getChildBlockPosition(block, x, y, z);
                    block.renderShaderInGivexyzwht(config, vao,  matrix,block.points);
                   

                }
                return;
            } /* else { */// 现实帧
            // 如果停止了播放就显示blockList中的内容
            /*
             * animations.get(nowIndex).update(); return;
             */
            /* } */
        }
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock colorBlock = selectBlockList.get(i);
            float[] info = this.getChildBlockPosition(colorBlock, x, y, z);
            ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig, ShaderManager.lineShaderConfig.getVao(),
                    info[0], info[1], info[2], info[3], info[4], info[5]);
        }

        for (int i = 0; i < colorBlockList.size(); i++) {
            BaseBlock block = colorBlockList.get(i);
            // colorBlock.update();
            float[] info = this.getChildBlockPosition(block, x, y, z);
            
            block.renderShaderInGivexyzwht(config, vao,matrix, block.points);


        }
    }

    public void reComputePoints() {
        this.points = BoxModel.getSmallPoint(0, 0, 0, width, height, thick);

        GL_Matrix rotateMatrix = GL_Matrix.multiply(
                GL_Matrix.translateMatrix(xoffset + 1f/ 2, yoffset, zoffset + 1f/ 2),
                GL_Matrix.rotateMatrix(0, this.dir * 3.14f / 2, 0));
        rotateMatrix = GL_Matrix.multiply(rotateMatrix, GL_Matrix.scaleMatrix(1 / xzoom, 1 / yzoom, 1 / zzoom));
        rotateMatrix = GL_Matrix.multiply(rotateMatrix, GL_Matrix.translateMatrix(-1f/ 2, 0, -1f/ 2));

        /*for (int i = 0; i < points.length; i++) {
            points[i] = GL_Matrix.multiply(rotateMatrix, points[i]);
            
            
             * points[i].x+=width/2; points[i].z+=thick/2;
             
        }*/
        /*float tempthick =thick;
        thick=width;
        width = tempthick;*/
        for (BaseBlock block : colorBlockList) {
            // float[] info = this.getChildBlockRelativePosition(block,x,y,z);
            block.reComputePoints(rotateMatrix);
        }
        if (animations != null) {
            for (BaseBlock block : animations) {
                // float[] info =
                // this.getChildBlockRelativePosition(block,x,y,z);
                block.reComputePoints(rotateMatrix);
                block.dir = this.dir;
            }
        }
        
        
        if (animationMap != null) {
            for (Map.Entry<String, List<GroupBlock>> entry : animationMap.entrySet()) {
             
                List<GroupBlock> list = entry.getValue();
                for(GroupBlock animationBlock : list){
                        animationBlock.dir = this.dir;
                        animationBlock.reComputePoints();

                }
            }
        }
    }
    
    public void scale(float xzoom,float yzoom,float zzoom){
        this.x=this.x*xzoom;
        this.y= this.y*yzoom;
        this.z=this.z*zzoom;
        this.width = this.width * xzoom;
        this.height =this.height*yzoom;
        this.thick =this.thick*zzoom;
        
        for (BaseBlock block : colorBlockList) {
            // float[] info = this.getChildBlockRelativePosition(block,x,y,z);
            block.scale(xzoom,yzoom,zzoom);
        }
        if (animations != null) {
            for (BaseBlock block : animations) {
                // float[] info =
                // this.getChildBlockRelativePosition(block,x,y,z);
                block.scale(xzoom,yzoom,zzoom);
            }
        }
        
        
        if (animationMap != null) {
            for (Map.Entry<String, List<GroupBlock>> entry : animationMap.entrySet()) {
             
                List<GroupBlock> list = entry.getValue();
                for(GroupBlock animationBlock : list){
                        
                        animationBlock.scale(xzoom,yzoom,zzoom);
                      

                }
            }
        }
        this.reComputePoints();
    }
   


}
