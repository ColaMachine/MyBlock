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
public class AnimationBlock2 extends BaseBlock {

    public AnimationBlock2(float x, float y, float z, float width, float height, float thick) {
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
    public List<BaseBlock> colorBlockList = new ArrayList<BaseBlock>();
    public List<BaseBlock> selectBlockList = new ArrayList<BaseBlock>();
    public List<GroupBlock> animations = new ArrayList<GroupBlock>();
    public String state;
    public Map<String, List<GroupBlock>> animationMap = new HashMap<String, List<GroupBlock>>();
    /*
     * public ColorGroup(int x, int y, int z) { super(x, y, z); }
     */

    public void addChild(BaseBlock colorBlock) {
        colorBlockList.add(colorBlock);
    }

    long lastAnimationTime = 0;
    int nowIndex = 0;// 动画运行到的帧数
    // public void update(){
    // //每隔1秒展示下一个动画
    // if(animations.size()>0) {
    // if (play ) {//正常播放
    // if (TimeUtil.getNowMills() - lastAnimationTime > 100) {
    // lastAnimationTime = TimeUtil.getNowMills();
    // nowIndex++;
    // if (nowIndex > animations.size() - 1) {
    // nowIndex = 0;
    // }
    //
    // }
    // // animations.get(nowIndex).update();
    //
    // for(int i=0;i<animations.get(nowIndex).colorBlockList.size();i++){
    // BaseBlock block = animations.get(nowIndex).colorBlockList.get(i);
    // float[] info = getChildBlockPosition(block);
    // block.update(info[0],info[1],info[2],info[3],info[4],info[5]);
    // // ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,
    // ShaderManager.anotherShaderConfig.getVao(),this.x+this.xoffset+colorBlock.x/xzoom,this.y+this.yoffset+colorBlock.y/yzoom,this.z+this.zoffset+colorBlock.z/zzoom,
    // new GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf),
    // colorBlock.width/xzoom, colorBlock.height/yzoom, colorBlock.thick/zzoom,
    // colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);
    //
    // }
    // return;
    // }/* else {*///现实帧
    // //如果停止了播放就显示blockList中的内容
    // /* animations.get(nowIndex).update();
    // return;*/
    // /* }*/
    // }
    // for(int i=0;i<selectBlockList.size();i++){
    // BaseBlock colorBlock = selectBlockList.get(i);
    // float[] info =this.getChildBlockPosition(colorBlock);
    // ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig,ShaderManager.lineShaderConfig.getVao(),info[0],info[1],info[2],info[3],info[4],info[5]);
    // }
    //
    // for(int i=0;i<colorBlockList.size();i++){
    // BaseBlock colorBlock = colorBlockList.get(i);
    // // colorBlock.update();
    // float[] info =this.getChildBlockPosition(colorBlock);
    //
    //
    // colorBlock.update(info[0],info[1],info[2], info[3],info[4],info[5]);
    // //ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,
    // ShaderManager.anotherShaderConfig.getVao(),info[0],info[1],info[2],new
    // GL_Vector(colorBlock.rf, colorBlock.gf, colorBlock.bf),
    // info[3],info[4],info[5],
    // colorBlock.opacity/*selectBlockList.size()>0?0.5f:1*/);
    //
    // }
    // }

    public BaseBlock selectSingle(GL_Vector from, GL_Vector direction) {

        BaseBlock theNearestBlock = getSelectBlock(from, direction);

        if (theNearestBlock != null) {

            selectBlockList.add(theNearestBlock);
            return theNearestBlock;
        }
        return null;
    }

    public BaseBlock getSelectBlock(GL_Vector from, GL_Vector direction) {
        direction = direction.normalize();
        // startPoint =GamingState.instance.player.getPosition().copyClone();

        // LogUtil.println("开始选择");
        Vector3f fromV = new Vector3f(from.x, from.y, from.z);
        Vector3f directionV = new Vector3f(direction.x, direction.y, direction.z);
        float distance = 0;
        BaseBlock theNearestBlock = null;
        float[] xiangjiao = null;
        for (int i = 0; i < colorBlockList.size(); i++) {
            BaseBlock colorBlock = colorBlockList.get(i);
            float[] info = getChildBlockPosition(colorBlock, this.x, this.y, this.z);
            AABB aabb = new AABB(new Vector3f(info[0], info[1], info[2]),
                    new Vector3f(info[0] + info[3], info[1] + info[4], info[2] + info[5]));

            // LogUtil.println(fromV.toString() );
            // LogUtil.println(directionV.toString() );
            if ((xiangjiao = aabb.intersectRectangle2(fromV, directionV)) != null) {// 这里进行了按照list的顺序进行选择
                                                                                    // 其实应该按照距离的最近选择

                // 计算点在了那个面上
                // 有上下左右前后6个面
                // LogUtil.println("选中了");
                float _tempDistance = xiangjiao[0] * xiangjiao[0] + xiangjiao[1] * xiangjiao[1]
                        + xiangjiao[2] * xiangjiao[2];

                if (distance == 0 || _tempDistance < distance) {
                    distance = _tempDistance;
                    theNearestBlock = colorBlock;
                }

            }

        }
        return theNearestBlock;
    }

    @Override
    public float  addWidth(float num) {
        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addWidth(num);// (false);

            }
        } else {
            this.width += num;

        }
        return width;
    }

    @Override
    public float addX(float num) {

        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addX(num);// (false);

            }
        } else {
            this.x += num;

        }
        return this.x;
    }

    @Override
    public float addHeight(float num) {

        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addHeight(num);// (false);

            }
        } else {
            this.height += num;

        }
        return this.height;
    }

    @Override
    public float  addY(float num) {

        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addY(num);// (false);

            }
        } else {
            this.y += num;

        }
        return this.y;
    }

    @Override
    public float addThick(float num) {

        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addThick(num);// (false);

            }
        } else {
            this.thick += num;

        }
        return this.thick;
    }

    @Override
    public float addZ(float num) {

        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addZ(num);// (false);

            }
        } else {
            this.z += num;

        }
        return this.z;
    }

    public AnimationBlock2 copy() {
        // 复制本身

        AnimationBlock2 animationBlock = new AnimationBlock2(this.x, this.y, this.z, this.width, this.height, this.thick);
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
    public AnimationBlock2 deepcopy() {
        // 复制本身

        AnimationBlock2 animationBlock = new AnimationBlock2(this.x, this.y, this.z, this.width, this.height, this.thick);
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

    // 选择区域
    public void selectMany(float minX, float minY, float maxX, float maxY, boolean clearbefore) {

        // 遍历所有的方块查找所有的在方块里的方块
        // 取出
        if (maxX < minX) {
            maxX = minX;
        }
        if (maxY < minY) {
            maxY = minY;
        }
        if (clearbefore)
            selectBlockList.clear();
        for (int i = 0; i < colorBlockList.size(); i++) {
            BaseBlock colorBlock = colorBlockList.get(i);
            /*
             * GL_Vector[] gl_vectors =
             * BoxModel.getSmaillPoint(colorBlock.x,colorBlock.y,colorBlock.z,
             * colorBlock.width, colorBlock.height, colorBlock.thick); for(){
             *
             * }
             */
            float[] info = getChildBlockPosition(colorBlock, this.x, this.y, this.z);
            Vector2f xy = OpenglUtils.wordPositionToXY(ShaderManager.projection,
                    new GL_Vector(info[0], info[1], info[2]), GamingState.instance.camera.Position,
                    GamingState.instance.camera.getViewDir());
            xy.x *= Constants.WINDOW_WIDTH;
            xy.y *= Constants.WINDOW_HEIGHT;
            if (xy.x > minX && xy.x < maxX && xy.y > minY && xy.y < maxY) {
                selectBlockList.add(colorBlock);
                // colorBlock.setSelected(true);
            } else {
                // colorBlock.setSelected(false);
            }
        }

        if (!clearbefore || selectBlockList.size() == 0) {
            selectSingle(GamingState.instance.camera.Position.copyClone(), OpenglUtils.getLookAtDirectionInvert(
                    GamingState.instance.camera.getViewDir().copyClone(), minX, Constants.WINDOW_HEIGHT - minY));
        }
    }

    public float[] getChildBlockRelativePosition(BaseBlock colorBlock, float nowX, float nowY, float nowZ) {
        float[] info = new float[6];
        float x = (colorBlock.x + xoffset) / xzoom;
        float y = (colorBlock.y + yoffset) / yzoom;
        float z = (colorBlock.z + zoffset) / zzoom;
        float width = (colorBlock.width == 0 ? 1 : colorBlock.width) / xzoom;
        float height = (colorBlock.height == 0 ? 1 : colorBlock.height) / yzoom;
        float thick = (colorBlock.thick == 0 ? 1 : colorBlock.thick) / zzoom;
        return new float[] { x, y, z, width, height, thick };
    }

    public float[] getChildBlockPosition(BaseBlock colorBlock, float nowX, float nowY, float nowZ) {
        float[] info = new float[6];
        float x = (colorBlock.x + xoffset) / xzoom + nowX;
        float y = (colorBlock.y + yoffset) / yzoom + nowY;
        float z = (colorBlock.z + zoffset) / zzoom + nowZ;
        float width = colorBlock.width / xzoom;
        float height = colorBlock.height / yzoom;
        float thick = colorBlock.thick / zzoom;
        return new float[] { x, y, z, width, height, thick };
    }

    public void shootBlock(float x, float y) {

        GL_Vector from = GamingState.instance.camera.Position.copyClone();
        GL_Vector direction = OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(),
                x, Constants.WINDOW_HEIGHT - y);
        direction = direction.normalize();

        // LogUtil.println("开始选择");
        Vector3f fromV = new Vector3f(from.x, from.y, from.z);
        Vector3f directionV = new Vector3f(direction.x, direction.y, direction.z);
        float distance = 0;
        BaseBlock theNearestBlock = null;
        float[] xiangjiao = null;
        float[] right = null;
        for (int i = 0; i < colorBlockList.size(); i++) {
            BaseBlock colorBlock = colorBlockList.get(i);
            float[] info = this.getChildBlockPosition(colorBlock, this.x, this.y, this.z);
            AABB aabb = new AABB(new Vector3f(info[0], info[1], info[2]),
                    new Vector3f(info[0] + info[3], info[1] + info[4], info[2] + info[5]));

            // LogUtil.println(fromV.toString() );
            // LogUtil.println(directionV.toString() );
            if ((xiangjiao = aabb.intersectRectangle2(fromV, directionV)) != null) {// 这里进行了按照list的顺序进行选择
                                                                                    // 其实应该按照距离的最近选择

                // 计算点在了那个面上
                // 有上下左右前后6个面
                LogUtil.println("选中了");
                float _tempDistance = xiangjiao[0] * xiangjiao[0] + xiangjiao[1] * xiangjiao[1]
                        + xiangjiao[2] * xiangjiao[2];

                if (distance == 0 || _tempDistance < distance) {
                    distance = _tempDistance;
                    theNearestBlock = colorBlock;
                    right = xiangjiao;
                }

                // addColorBlock.rf = color.red;
                // addColorBlock.gf=color.green;
                // addColorBlock.bf =color.blue;
                // GL_Vector.chuizhijuli(GL_Vector.sub(livingThing.position,from),direction)<3){
                // LogUtil.println("选中了");
                // this.target=livingThing;
                // colorBlock.selected=true;

            }

        }

        int face = -1;
        if (theNearestBlock != null) {
            // 计算是那一面

            float[] ary = AABB.xyFaces[(int) right[3] - 1];
            float[] ary1 = AABB.xzFaces[(int) right[4] - 1];
            float[] ary2 = AABB.yzFaces[(int) right[5] - 1];

            for (int i = 0; i < ary.length; i++) {
                for (int j = 0; j < ary.length; j++) {
                    if (ary[i] == ary1[j]) {// 在xz的集合当中也有

                        for (int k = 0; k < ary.length; k++) {
                            // 在yz的集合当中也有
                            if (ary[i] == ary2[k]) {
                                face = (int) ary[i];
                                try {

                                    float thisX = (from.x + right[0] - this.xoffset - this.x) * this.xzoom;

                                    float thisY = (from.y + right[1] - this.yoffset - this.y) * this.yzoom;
                                    float thisZ = (from.z + right[2] - this.zoffset - this.z) * this.zzoom;

                                    BaseBlock addColorBlock = GamingState.editEngine.readyShootBlock.copy();
                                    GamingState.editEngine.set(addColorBlock, (int) thisX, (int) thisY, (int) thisZ,
                                            addColorBlock.width, addColorBlock.height, addColorBlock.thick,
                                            GamingState.editEngine.red, GamingState.editEngine.green,
                                            GamingState.editEngine.blue, GamingState.editEngine.alpha);

                                    /*
                                     * addColorBlock.rf =
                                     * GamingState.editEngine.red;
                                     * addColorBlock.gf =
                                     * GamingState.editEngine.green;
                                     * addColorBlock.bf =
                                     * GamingState.editEngine.blue;
                                     */
                                    colorBlockList.add(addColorBlock);
                                    if (face == Constants.BACK) {
                                        addColorBlock.z -= 1;
                                    } else if (face == Constants.LEFT) {
                                        addColorBlock.x -= 1;
                                    } else if (face == Constants.BOTTOM) {
                                        addColorBlock.y -= 1;
                                    }

                                    if (face == Constants.BACK || face == Constants.FRONT) {
                                        float fangdabeishu = (addColorBlock.z - from.z) / direction.z;
                                        addColorBlock.x = (int) (from.x + direction.x * fangdabeishu);
                                        addColorBlock.y = (int) (from.y + direction.y * fangdabeishu);
                                    } else if (face == Constants.LEFT || face == Constants.RIGHT) {
                                        float fangdabeishu = (addColorBlock.x - from.x) / direction.x;
                                        addColorBlock.y = (int) (from.y + direction.y * fangdabeishu);
                                        addColorBlock.z = (int) (from.z + direction.z * fangdabeishu);
                                    } else if (face == Constants.BOTTOM || face == Constants.TOP) {
                                        float fangdabeishu = (addColorBlock.y - from.y) / direction.y;
                                        addColorBlock.x = (int) (from.x + direction.x * fangdabeishu);
                                        addColorBlock.z = (int) (from.z + direction.z * fangdabeishu);
                                    }
                                    // 调整
                                    if (face == Constants.BACK) {
                                        // addColorBlock.z=theNearestBlock.z-1;
                                        if (addColorBlock.x > theNearestBlock.x + (int) theNearestBlock.width - 1) {
                                            addColorBlock.x = theNearestBlock.x + (int) theNearestBlock.width - 1;
                                        }
                                        if (addColorBlock.x < theNearestBlock.x) {
                                            addColorBlock.x = theNearestBlock.x;
                                        }

                                        if (addColorBlock.y > theNearestBlock.y + (int) theNearestBlock.height - 1) {
                                            addColorBlock.y = theNearestBlock.y + (int) theNearestBlock.height - 1;
                                        }
                                        if (addColorBlock.y < theNearestBlock.y) {
                                            addColorBlock.y = theNearestBlock.y;
                                        }

                                    } else if (face == Constants.FRONT) {
                                        // addColorBlock.z=theNearestBlock.z+(int)theNearestBlock.thick+1;
                                        if (addColorBlock.x > theNearestBlock.x + (int) theNearestBlock.width - 1) {
                                            addColorBlock.x = theNearestBlock.x + (int) theNearestBlock.width - 1;
                                        }
                                        if (addColorBlock.x < theNearestBlock.x) {
                                            addColorBlock.x = theNearestBlock.x;
                                        }

                                        if (addColorBlock.y > theNearestBlock.y + (int) theNearestBlock.height - 1) {
                                            addColorBlock.y = theNearestBlock.y + (int) theNearestBlock.height - 1;
                                        }
                                        if (addColorBlock.y < theNearestBlock.y) {
                                            addColorBlock.y = theNearestBlock.y;
                                        }

                                    } else if (face == Constants.LEFT) {

                                        if (addColorBlock.y > theNearestBlock.y + (int) theNearestBlock.height - 1) {
                                            addColorBlock.y = theNearestBlock.y + (int) theNearestBlock.height - 1;
                                        }
                                        if (addColorBlock.y < theNearestBlock.y) {
                                            addColorBlock.y = theNearestBlock.y;
                                        }

                                        if (addColorBlock.z > theNearestBlock.z + (int) theNearestBlock.thick - 1) {
                                            addColorBlock.z = theNearestBlock.z + (int) theNearestBlock.thick - 1;
                                        }
                                        if (addColorBlock.z < theNearestBlock.z) {
                                            addColorBlock.z = theNearestBlock.z;
                                        }
                                    } else if (face == Constants.RIGHT) {

                                        if (addColorBlock.y > theNearestBlock.y + (int) theNearestBlock.height - 1) {
                                            addColorBlock.y = theNearestBlock.y + (int) theNearestBlock.height - 1;
                                        }
                                        if (addColorBlock.y < theNearestBlock.y) {
                                            addColorBlock.y = theNearestBlock.y;
                                        }

                                        if (addColorBlock.z > theNearestBlock.z + (int) theNearestBlock.thick - 1) {
                                            addColorBlock.z = theNearestBlock.z + (int) theNearestBlock.thick - 1;
                                        }
                                        if (addColorBlock.z < theNearestBlock.z) {
                                            addColorBlock.z = theNearestBlock.z;
                                        }
                                    } else if (face == Constants.BOTTOM) {
                                        // addColorBlock.y=theNearestBlock.y-1;

                                        if (addColorBlock.x > theNearestBlock.x + (int) theNearestBlock.width - 1) {
                                            addColorBlock.x = theNearestBlock.x + (int) theNearestBlock.width - 1;
                                        }
                                        if (addColorBlock.x < theNearestBlock.x) {
                                            addColorBlock.x = theNearestBlock.x;
                                        }

                                        if (addColorBlock.z > theNearestBlock.z + (int) theNearestBlock.thick - 1) {
                                            addColorBlock.z = theNearestBlock.z + (int) theNearestBlock.thick - 1;
                                        }
                                        if (addColorBlock.z < theNearestBlock.z) {
                                            addColorBlock.z = theNearestBlock.z;
                                        }
                                    } else if (face == Constants.TOP) {
                                        // addColorBlock.y=theNearestBlock.y+(int)theNearestBlock.height+1;

                                        if (addColorBlock.x > theNearestBlock.x + (int) theNearestBlock.width - 1) {
                                            addColorBlock.x = theNearestBlock.x + (int) theNearestBlock.width - 1;
                                        }
                                        if (addColorBlock.x < theNearestBlock.x) {
                                            addColorBlock.x = theNearestBlock.x;
                                        }

                                        if (addColorBlock.z > theNearestBlock.z + (int) theNearestBlock.thick - 1) {
                                            addColorBlock.z = theNearestBlock.z + (int) theNearestBlock.thick - 1;
                                        }
                                        if (addColorBlock.z < theNearestBlock.z) {
                                            addColorBlock.z = theNearestBlock.z;
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }
            }
            LogUtil.println("是那个面:" + face);

        }
    }

    public void deleteSelect() {
        for (int i = selectBlockList.size() - 1; i >= 0; i--) {
            BaseBlock colorBlock = selectBlockList.get(i);

            colorBlockList.remove(colorBlock);

        }
        selectBlockList.clear();
    }

    boolean play = true;// -2 正常播放 -1 停止

    public void showAnimationFrame(int num) {

        play = false;
        nowIndex = num;
        // 把主要的对象关联到

        this.colorBlockList = this.animations.get(nowIndex).colorBlockList;
    }

    public void saveToCurFrame() {
        AnimationBlock2 animationBlock = this.copy();
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

    public void setColor(float red, float green, float blue, float alpha) {

        for (int i = selectBlockList.size() - 1; i >= 0; i--) {
            BaseBlock colorBlock = selectBlockList.get(i);
            if (colorBlock instanceof ColorBlock) {
                ColorBlock colorBlock1 = (ColorBlock) colorBlock;
                colorBlock1.rf = red;
                colorBlock1.gf = green;
                colorBlock1.bf = blue;
                colorBlock1.opacity = alpha;
            }

        }
    }

    public void brushImageOnBlock(float x, float y, float red, float green, float blue) {

        GL_Vector from = GamingState.instance.camera.Position.copyClone();
        GL_Vector direction = OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(),
                x, Constants.WINDOW_HEIGHT - y);
        direction = direction.normalize();

        // LogUtil.println("开始选择");
        Vector3f fromV = new Vector3f(from.x, from.y, from.z);
        Vector3f directionV = new Vector3f(direction.x, direction.y, direction.z);
        float distance = 0;
        BaseBlock theNearestBlock = null;
        float[] xiangjiao = null;
        float[] right = null;
        for (int i = 0; i < colorBlockList.size(); i++) {
            BaseBlock colorBlock = colorBlockList.get(i);

            float[] info = this.getChildBlockPosition(colorBlock, x, y, z);
            AABB aabb = new AABB(new Vector3f(info[0], info[1], info[2]),
                    new Vector3f(info[0] + info[3], info[1] + info[4], info[2] + info[5]));

            if ((xiangjiao = aabb.intersectRectangle2(fromV, directionV)) != null) {// 这里进行了按照list的顺序进行选择
                                                                                    // 其实应该按照距离的最近选择

                // 计算点在了那个面上
                // 有上下左右前后6个面
                LogUtil.println("选中了");
                float _tempDistance = xiangjiao[0] * xiangjiao[0] + xiangjiao[1] * xiangjiao[1]
                        + xiangjiao[2] * xiangjiao[2];

                if (distance == 0 || _tempDistance < distance) {
                    distance = _tempDistance;
                    theNearestBlock = colorBlock;
                    right = xiangjiao;
                }

            }

        }

        int face = -1;
        if (theNearestBlock != null && theNearestBlock instanceof ImageBlock) {
            // 计算是那一面
            ImageBlock imageBlock = (ImageBlock) theNearestBlock;

            float[] ary = AABB.xyFaces[(int) right[3] - 1];
            float[] ary1 = AABB.xzFaces[(int) right[4] - 1];
            float[] ary2 = AABB.yzFaces[(int) right[5] - 1];

            for (int i = 0; i < ary.length; i++) {
                for (int j = 0; j < ary.length; j++) {
                    if (ary[i] == ary1[j]) {// 在xz的集合当中也有

                        for (int k = 0; k < ary.length; k++) {
                            // 在yz的集合当中也有
                            if (ary[i] == ary2[k]) {
                                face = (int) ary[i];
                                try {

                                    // 调整
                                    if (face == Constants.BACK) {

                                        imageBlock.back = GamingState.editEngine.nowTextureInfo;

                                    } else if (face == Constants.FRONT) {
                                        imageBlock.front = GamingState.editEngine.nowTextureInfo;

                                    } else if (face == Constants.LEFT) {

                                        imageBlock.left = GamingState.editEngine.nowTextureInfo;

                                    } else if (face == Constants.RIGHT) {

                                        imageBlock.right = GamingState.editEngine.nowTextureInfo;

                                    } else if (face == Constants.BOTTOM) {
                                        imageBlock.bottom = GamingState.editEngine.nowTextureInfo;
                                    } else if (face == Constants.TOP) {
                                        imageBlock.top = GamingState.editEngine.nowTextureInfo;
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;

                            }
                        }
                    }
                }
            }
            LogUtil.println("是那个面:" + face);

        }
    }

    public void brushBlock(float x, float y, float red, float green, float blue) {

        BaseBlock block = this.getSelectBlock(GamingState.instance.camera.Position.copyClone(),
                OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(), x,
                        Constants.WINDOW_HEIGHT - y));
        if (block == null)
            return;
        if (block instanceof ColorBlock) {
            ((ColorBlock) block).rf = red;
            ((ColorBlock) block).gf = green;
            ((ColorBlock) block).bf = blue;
        }

    }

    @Override
    public IBlock clone() {
        return null;
    }

    public AnimationBlock2() {

    }

    public String toString() {
        //开始清理
        //遍历animationMap下的所有colorgroup 清理他们的animationMap

        StringBuffer buffer = new StringBuffer();
        buffer.append("{").append("name:\"").append(this.name).append("\",").append("blocktype:'animationlock',")
                .append("width:").append(this.width).append(",").append("height:").append(this.height).append(",")
                .append("thick:").append(this.thick).append(",").append("x:").append(this.x).append(",").append("y:")
                .append(this.y).append(",").append("z:").append(this.z).append(",")
                .append("dir:").append(this.dir).append(",")
                .append("xoffset:").append(xoffset).append(",").append("yoffset:").append(yoffset).append(",")
                .append("zoffset:").append(zoffset).append(",").append("xzoom:").append(xzoom).append(",")
                .append("yzoom:").append(yzoom).append(",").append("zzoom:").append(zzoom).append(",").append("live:")
                .append(live).append(",").append("points:").append(JSON.toJSON(this.points)).append(",")
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
                if(block instanceof AnimationBlock2){
                    AnimationBlock2 animationBlock = (AnimationBlock2)block;
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

    public static AnimationBlock2 parse(JSONObject map) {
        AnimationBlock2 group = new AnimationBlock2();
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
        

        return group;

    }

    public float rotateX(float value) {

        return adjustRotatex(value);
    }

    public float rotateY(float value) {
        // adjustRotateY(value);
        return super.rotateY(value);//baseblock的调整方向都是顺时针90度的
    }

    public float rotateZ(float value) {
        return  adjustRotateZ(value);
    }

    public float adjustRotatex(float value) {
        float degree = 0 ;
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock colorBlock = selectBlockList.get(i);
            if (colorBlock instanceof RotateColorBlock2) {
                degree =((RotateColorBlock2) colorBlock).rotateX(value);
            }
            if (colorBlock instanceof AnimationBlock2) {
                degree = ((AnimationBlock2) colorBlock).rotateX(value);
            }
        } return degree;
    }

    public float adjustRotateY(float value) {float degree = 0 ;
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock colorBlock = selectBlockList.get(i);
            if (colorBlock instanceof RotateColorBlock2) {
                degree =((RotateColorBlock2) colorBlock).rotateY(value);
                // colorBlock .rotateY(value);
            }
            if (colorBlock instanceof AnimationBlock2) {
                ((AnimationBlock2) colorBlock).rotateY(value);
            }
        } return degree;
    }

    public float adjustRotateZ(float value) {float degree = 0 ;
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock colorBlock = selectBlockList.get(i);
            if (colorBlock instanceof RotateColorBlock2) {
                degree =((RotateColorBlock2) colorBlock).rotateZ(value);
            }
            if (colorBlock instanceof AnimationBlock2) {
                degree =((AnimationBlock2) colorBlock).rotateZ(value);
            }
        }
        return degree;
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
            ShaderUtils.draw3dColorBoxLine( ShaderManager.lineShaderConfig.getVao(),
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
            ShaderUtils.draw3dColorBoxLine( ShaderManager.lineShaderConfig.getVao(),
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
                block.reComputePoints();
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

    public void setCenter(float x, float y, float z) {
        for (BaseBlock block : selectBlockList) {
            if (block instanceof RotateColorBlock2) {
                RotateColorBlock2 rotateBlock = (RotateColorBlock2) block;
                rotateBlock.centerX = x;// -rotateBlock.x;
                rotateBlock.centerY = y;// -rotateBlock.y;
                rotateBlock.centerZ = z;// -rotateBlock.z;

            }
        }
    }
}
