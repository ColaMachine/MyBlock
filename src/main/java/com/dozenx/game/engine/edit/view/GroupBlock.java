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
public class GroupBlock extends BaseBlock {

    public GroupBlock(float x, float y, float z, float width, float height, float thick) {
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


    public List<BaseBlock> colorBlockList = new ArrayList<BaseBlock>();
    public List<BaseBlock> selectBlockList = new ArrayList<BaseBlock>();

    public String state;

    /*
     * public ColorGroup(int x, int y, int z) { super(x, y, z); }
     */

    public void addChild(BaseBlock colorBlock) {//LogUtil.println("123");
       colorBlockList.add(colorBlock);
        this.reComputePoints();
    }



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
    public float addWidth(float num) {
        float val = 0 ;
        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                val=colorBlock.addWidth(num);// (false);

            }
        } else {
            this.width += num;
            val=this.width;

        }

        this.reComputePoints();
        return val;
    }

    @Override
    public float addX(float num) {
        float val = 0 ;
        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                val=colorBlock.addX(num);// (false);

            }
        } else {
            this.x += num;
            val=this.x;
        }
        this.reComputePoints();
        return val;
    }

    @Override
    public float addHeight(float num) {
        float val = 0 ;
        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                val=colorBlock.addHeight(num);// (false);

            }
        } else {
            this.height += num;
            val=this.height;
        }
        this.reComputePoints();
        return val;
    }

    @Override
    public float addY(float num) {
        float val = 0 ;
        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                val=colorBlock.addY(num);// (false);

            }
        } else {
            this.y += num;
            val=this.y;

        }
        this.reComputePoints();
        return val;
    }

    @Override
    public float addThick(float num) {
        float val = 0 ;
        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                val=colorBlock.addThick(num);// (false);

            }
        } else {
            this.thick += num;
            val=this.thick;

        }
        this.reComputePoints();
        return val;
    }

    @Override
    public float addZ(float num) {
        float val = 0 ;
        if (Switcher.isEditComponent) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                val=colorBlock.addZ(num);// (false);

            }
        } else {
            this.z += num;
            val=this.z;

        }
        this.reComputePoints();
        return val;
    }

    public GroupBlock copy() {
        // 复制本身

        GroupBlock groupBlock = new GroupBlock();

        copyGroupBlock(groupBlock);
        groupBlock.reComputePoints();
        return groupBlock;
    }
    public void copyGroupBlock(GroupBlock groupBlock){
        copyBaseBlock(groupBlock);
     // 复制所有child block
        for (int i = 0; i < colorBlockList.size(); i++) {
            BaseBlock colorBlock = colorBlockList.get(i);
            groupBlock.colorBlockList.add(colorBlock.copy());

        }

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

//    public float[] getChildBlockRelativePosition(BaseBlock colorBlock, float nowX, float nowY, float nowZ) {
//        float[] info = new float[6];
//        float x = (colorBlock.x + xoffset) / xzoom;
//        float y = (colorBlock.y + yoffset) / yzoom;
//        float z = (colorBlock.z + zoffset) / zzoom;
//        float width = (colorBlock.width == 0 ? 1 : colorBlock.width) / xzoom;
//        float height = (colorBlock.height == 0 ? 1 : colorBlock.height) / yzoom;
//        float thick = (colorBlock.thick == 0 ? 1 : colorBlock.thick) / zzoom;
//        return new float[] { x, y, z, width, height, thick };
//    }
//
//    public float[] getChildBlockPosition(BaseBlock colorBlock, float nowX, float nowY, float nowZ) {
//        float[] info = new float[6];
//        float x = (colorBlock.x + xoffset) / xzoom + nowX;
//        float y = (colorBlock.y + yoffset) / yzoom + nowY;
//        float z = (colorBlock.z + zoffset) / zzoom + nowZ;
//        float width = colorBlock.width / xzoom;
//        float height = colorBlock.height / yzoom;
//        float thick = colorBlock.thick / zzoom;
//        return new float[] { x, y, z, width, height, thick };
//    }
    public  Object[] getMouseChoosedBlock(float screenX,float screenY){

        GL_Vector from = GamingState.instance.camera.Position.copyClone();
       GL_Vector direction =OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(),screenX,Constants.WINDOW_HEIGHT-screenY);
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
            
            float[] info = this.getChildBlockPosition(colorBlock, x, y, z);
            AABB aabb = new AABB(new Vector3f(info[0], info[1], info[2]),
                    new Vector3f(info[0] + info[3], info[1] + info[4], info[2] + info[5]));
            // LogUtil.println(fromV.toString() );
            // LogUtil.println(directionV.toString() );
            if( (xiangjiao=aabb.intersectRectangle2(fromV,directionV))!=null){//这里进行了按照list的顺序进行选择 其实应该按照距离的最近选择

                //计算点在了那个面上
                //有上下左右前后6个面
                //LogUtil.println("选中了");
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
                               

                                return new Object[]{theNearestBlock,face,touchPoint,placePoint};

                            }
                        }
                    }
                }
            }
        }
            return new Object[]{theNearestBlock,face,touchPoint,placePoint};
    }
    public void shootBlock(float screenX, float screenY) {  LogUtil.println("是那个面:");
        Object[] results = this.getMouseChoosedBlock(screenX,screenY);
        BaseBlock theNearestBlock = (BaseBlock)results[0];
        GL_Vector touchPoint = (GL_Vector) results[2];
        GL_Vector placePoint = (GL_Vector) results[3];
        GamingState.editEngine.endPoint=touchPoint;
        GamingState.editEngine.startPoint =placePoint;
        int face = (int)results[1];
        if(theNearestBlock!=null){
            BaseBlock addColorBlock =null;

                addColorBlock = GamingState.editEngine.readyShootBlock.copy();//new RotateColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
                GamingState.editEngine.set(addColorBlock,(int) (placePoint.x-x), (int) (placePoint.y -y), (int) (placePoint.z -z),1,1,1,GamingState.editEngine.red,GamingState.editEngine.green,GamingState.editEngine.blue,GamingState.editEngine.alpha);
         /*   }else{
                addColorBlock = new ColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            }*/


            addChild(addColorBlock);


            LogUtil.println("是那个面:"+face);

        }else{//射在地面上
            BaseBlock addColorBlock =null;


            addColorBlock = GamingState.editEngine.readyShootBlock.copy();//new RotateColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            GamingState.editEngine.set(addColorBlock,GamingState.editEngine.curentX, 0, GamingState.editEngine.curentZ,addColorBlock.width,addColorBlock.height,addColorBlock.thick,GamingState.editEngine.red,GamingState.editEngine.green,GamingState.editEngine.blue,GamingState.editEngine.alpha);

            this.colorBlockList.add(addColorBlock);

        }
        
        
    }

    public void deleteSelect() {
        for (int i = selectBlockList.size() - 1; i >= 0; i--) {
            BaseBlock colorBlock = selectBlockList.get(i);

            colorBlockList.remove(colorBlock);

        }
        selectBlockList.clear();
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

    public void brushImageOnBlock(float windowx, float windowy, float red, float green, float blue) {

        GL_Vector from = GamingState.instance.camera.Position.copyClone();
        GL_Vector direction = OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(),
                windowx, Constants.WINDOW_HEIGHT - windowy);
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

    public GroupBlock() {

    }

    public String toString() {
        //开始清理
        //遍历animationMap下的所有colorgroup 清理他们的animationMap

        StringBuffer buffer = new StringBuffer();
        buffer.append("{").append("blocktype:'groupblock',")
                .append(toBaseBlockString())
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



        buffer.append("}");
        return buffer.toString();

    }

    public static GroupBlock parse(JSONObject map) {
        GroupBlock group = new GroupBlock();
        parseGroup(group, map);




        return group;

    }

    public static void parseGroup(GroupBlock group ,JSONObject map){
        parse(group,map);
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
                } else if ("rotateimageblock".equals(blockType)) {
                    RotateImageBlock shape = RotateImageBlock.parse(object);
                    group.addChild(shape);
                } /*else if ("groupblock".equals(blockType)) {
                    GroupBlock shape = GroupBlock.parse(object);
                    group.addChild(shape);
                }*/

            }
        }

    }

    public float rotateX(float value) {

        return adjustRotatex(value);
    }

    public float rotateY(float value) {
        // adjustRotateY(value);
        return super.rotateY(value);//baseblock的调整方向都是顺时针90度的
    }

    public float rotateZ(float value) {
        return adjustRotateZ(value);
    }

    public float adjustRotatex(float value) {
        float degree=0;
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock colorBlock = selectBlockList.get(i);
            if (colorBlock instanceof RotateColorBlock2) {
                degree= ((RotateColorBlock2) colorBlock).rotateX(value);
            }
            if (colorBlock instanceof RotateImageBlock) {
                degree=((RotateImageBlock) colorBlock).rotateX(value);
            }
            if (colorBlock instanceof GroupBlock) {
                degree= ((GroupBlock) colorBlock).rotateX(value);
            }
        }
        this.reComputePoints();
        return degree;
    }

    public float adjustRotateY(float value) {
        float degree= 0 ;
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock colorBlock = selectBlockList.get(i);
            if (colorBlock instanceof RotateColorBlock2) {
                degree= ((RotateColorBlock2) colorBlock).rotateY(value);
                // colorBlock .rotateY(value);
            }
            if (colorBlock instanceof GroupBlock) {
                degree= ((GroupBlock) colorBlock).rotateY(value);
            }
        }
        this.reComputePoints();
        return degree;
    }

    public float adjustRotateZ(float value) {
        float degree= 0 ;
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock colorBlock = selectBlockList.get(i);
            if (colorBlock instanceof RotateColorBlock2) {
                degree=   ((RotateColorBlock2) colorBlock).rotateZ(value);
            }
            if (colorBlock instanceof GroupBlock) {
                degree=  ((GroupBlock) colorBlock).rotateZ(value);
            }
        }
        this.reComputePoints();
        return degree;
    }

    @Override // 具体场景中 生物有旋转身体的 需要用到
    public void renderShader(ShaderConfig config, Vao vao, GL_Matrix matrix) {

        // 每隔1秒展示下一个动画
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
//            for (int k = 0; k < childPoints.length; k++) {
//                // childPoints[i] = GL_Matrix.multiply(childPoints[i],matrix);
//                childPoints[k] = GL_Matrix.multiply(matrix, childPoints[k]);
//            }
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
            //block.reComputePoints();
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
                GL_Matrix.translateMatrix(1f/ 2, 0, 1f/ 2),
                GL_Matrix.rotateMatrix(0, this.dir * 3.14f / 2, 0));
        rotateMatrix = GL_Matrix.multiply(rotateMatrix, GL_Matrix.scaleMatrix(1 , 1 , 1 ));
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
            block.reComputePointsInGroup();//rotateMatrix
        }

        
        

    }

    public void setCenter(float x, float y, float z) {
        for (BaseBlock block : selectBlockList) {
            if (block instanceof RotateBlock) {
                RotateBlock rotateBlock = (RotateBlock) block;
                rotateBlock.setCenterX( x);// -rotateBlock.x;
                rotateBlock.setCenterY( y);// -rotateBlock.y;
                rotateBlock.setCenterZ( z);// -rotateBlock.z;

            }
        }
    }


    public float[] getChildBlockPosition(BaseBlock colorBlock, float nowX, float nowY, float nowZ) {
        float[] info = new float[6];
        float x = colorBlock.x+ nowX;
        float y = colorBlock.y + nowY;
        float z = colorBlock.z + nowZ;
        float width = colorBlock.width ;
        float height = colorBlock.height;
        float thick = colorBlock.thick ;
        return new float[] { x, y, z, width, height, thick };
    }
    public float[] getChildBlockRelativePosition(BaseBlock colorBlock, float nowX, float nowY, float nowZ) {
        float[] info = new float[6];
        float x = colorBlock.x;
        float y = colorBlock.y ;
        float z = colorBlock.z ;
        float width = colorBlock.width ;
        float height = colorBlock.height;
        float thick = colorBlock.thick ;
        return new float[] { x, y, z, width, height, thick };
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
        
        this.reComputePoints();
    }
}
