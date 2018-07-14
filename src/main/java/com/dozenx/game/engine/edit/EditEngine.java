package com.dozenx.game.engine.edit;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.animation.Animation;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.*;
import cola.machine.game.myblocks.model.textture.BoneBlock;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkConstants;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ChunkResponseCmd;
import com.dozenx.game.engine.command.SayCmd;
import com.dozenx.game.engine.edit.view.AnimationBlock;
import com.dozenx.game.engine.edit.view.GroupBlock;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.element.model.CakeModel;
import com.dozenx.game.engine.item.action.ItemFactory;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.FileUtil;
import com.dozenx.util.MapUtil;
import com.dozenx.util.MathUtil;

import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/8/5.
 */
public class EditEngine {
    //Vao lineVao = new Vao(ShaderManager.anotherShaderConfig);
    Vao blockVao = null;
    public static BaseBlock nowMoveScaleRotateBlock=null;
    public HashMap<GL_Vector, WeakReference<ColorBlock>> lightBlockHashMap = new HashMap<>();
    public BaseBlock[] blockAry = new BaseBlock[140 * 140 * 140];
    public List<Triangle> triangleList =new ArrayList<>();
    public List<BaseBlock> colorBlockList = new ArrayList<>();
    public List<BaseBlock> selectBlockList = new ArrayList<>();

    public List<BaseBlock> appendingBlockList = new ArrayList<>();
    public float prevX;
    public float prevY;
    public float nowX;
    public float nowY;


    ColorBlock mouseOvercolorBlock = null;
    public int curentX = 0;
    public int curentZ = 0;

    public int prevFaceX = 0;
    public int prevFaceZ = 0;
    public int prevMaxFaceX = 0;
    public int prevMaxFaceZ = 0;


    public int lastFaceX = 0;
    public int lastFaceZ = 0;
    public int lastFaceMaxX = 0;
    public int lastFaceMaxZ = 0;

    public int nowFace = 0;

    public GL_Vector xPoint = new GL_Vector(0, 0, 0);
    public GL_Vector yPoint = new GL_Vector(0, 0, 0);
    public GL_Vector zPoint = new GL_Vector(0, 0, 0);

    /**
     * 当前编辑的组件库
     */
    public GroupBlock currentChoosedGroupForEdit = null;
    public float red, green, blue, alpha = 1;

    /**
     * 在组件库里选中的对象
     */
    //public ColorGroup chooseColorGroup = null;
    HashMap<String, BaseBlock> colorGroupHashMap = new HashMap<>();

    int minX, minZ;
    public GL_Vector startPoint = new GL_Vector();//用来强调记录placePoint  在鼠标按下的一瞬间产生变化
    public GL_Vector endPoint = new GL_Vector();//用来记录touchPoint

    public GL_Vector lastTouchPoint = new GL_Vector();//用来记录touchPoint

    public GL_Vector touchStartPoint = new GL_Vector();//用来记录选择的时候开始的位置
    public GL_Vector touchEndPoint = new GL_Vector();//用来记录选择结束的点


    public GL_Vector placeStartPoint = new GL_Vector();//用来记录选择的时候开始的位置
    public GL_Vector placeEndPoint = new GL_Vector();//用来记录选择结束的点
    public boolean needUpdate = false;

    public boolean lineNeedUpdate = false;

    public EditEngine(){
        blockVao = new Vao(ShaderManager.anotherShaderConfig);
    }
    public void render(){

        ShaderUtils.finalDraw(ShaderManager.terrainShaderConfig, blockVao);


    }
    public void update() {
       /* for(int i =0;i<groups.size();i++){
            groups.get(i).update();
        }*/
        //绘制当前的画布顶平面的网格
        if (lineNeedUpdate) {


            for (int i = -15; i < 15; i++) {

                ShaderUtils.drawLine(ShaderManager.lineShaderConfig.getVao(),new GL_Vector(GamingState.instance.player.getX() + i, 0, GamingState.instance.player.getZ() - 15), new GL_Vector(GamingState.instance.player.getX() + i, 0, GamingState.instance.player.getZ() + 15));
                ShaderUtils.drawLine(ShaderManager.lineShaderConfig.getVao(),new GL_Vector(GamingState.instance.player.getX() - 15, 0, GamingState.instance.player.getZ() + i), new GL_Vector(GamingState.instance.player.getX() + 15, 0, GamingState.instance.player.getZ() + i));

            }
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);
                //批量删除的时候这里会报错 空指针
                ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig.getVao(), colorBlock.x, colorBlock.y, colorBlock.z, colorBlock.width, colorBlock.height, colorBlock.thick);

            }

        }


        if (needUpdate) {
            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, blockVao, 0, 0, 0, new GL_Vector(0.3f, 0.3f, 0.3f), 100, 0, 100, 0.2f);


            synchronized (colorBlockList) {
                for (int i = 0; i < colorBlockList.size(); i++) {
                    BaseBlock colorBlock = colorBlockList.get(i);
                    GL_Matrix matrx = GL_Matrix.translateMatrix(colorBlock.x, colorBlock.y, colorBlock.z);
                colorBlock.renderShader(ShaderManager.terrainShaderConfig, blockVao, matrx);
                    // colorBlock.render(ShaderManager.terrainShaderConfig,blockVao,colorBlock.x,colorBlock.y,colorBlock.z,true,true,true,true,true,true);
                }

                for (int i = 0; i < triangleList.size(); i++) {
                    Triangle triangle = triangleList.get(i);

                    triangle.renderShader(ShaderManager.terrainShaderConfig, blockVao);
                    // colorBlock.render(ShaderManager.terrainShaderConfig,blockVao,colorBlock.x,colorBlock.y,colorBlock.z,true,true,true,true,true,true);
                }
            }
            for (int i = 0; i < appendingBlockList.size(); i++) {
                BaseBlock colorBlock = appendingBlockList.get(i);
                GL_Matrix matrx = GL_Matrix.translateMatrix(colorBlock.x, colorBlock.y, colorBlock.z);
                colorBlock.renderShader(ShaderManager.terrainShaderConfig, blockVao, matrx);
                // colorBlock.render(ShaderManager.terrainShaderConfig,blockVao,colorBlock.x,colorBlock.y,colorBlock.z,true,true,true,true,true,true);
            }
            needUpdate = false;

            if (startPoint != null) {

                ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, blockVao, startPoint.x, startPoint.y, startPoint.z, new GL_Vector(1, 0, 0), 0.3f, 0.3f, 0.3f, 1);

                ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, blockVao, endPoint.x, endPoint.y, endPoint.z, new GL_Vector(0, 0, 1), 0.3f, 0.3f, 0.3f, 1);


                //ShaderUtils.drawLine( startPoint,endPoint);
                //  ShaderUtils.drawLine( startPoint,endPoint);
            }

            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, blockVao, curentX, 0, curentZ, new GL_Vector(1, 1, 1), 1, 0.2f, 1, 1f);


            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, blockVao, GamingState.instance.lightPos.x,
                    GamingState.instance.lightPos.y, GamingState.instance.lightPos.z, new GL_Vector(1, 1, 1), 1, 1f, 1, 1f);


            int i = 0;
            ShaderManager.shaderLightingPass.use();
            for (Map.Entry<GL_Vector, WeakReference<ColorBlock>> entry : lightBlockHashMap.entrySet()) {
                WeakReference<ColorBlock> ref = entry.getValue();

                ColorBlock block = ref.get();
                if (block == null) {
                    lightBlockHashMap.remove(entry.getKey());
                }


//                            GL_Matrix vieDirw =
//                        GL_Matrix.LookAt(new GL_Vector(0, 0, 0), GamingState.player.getViewDir());
//                GL_Vector lightPositionView = GL_Matrix.multiply(vieDirw, new GL_Vector(0.1f, 0.5f, 1));


                GL_Vector lightPos = entry.getKey();

                GL_Matrix vieDirw =
                        GL_Matrix.LookAt(GamingState.getInstance().camera.Position, GamingState.player.getViewDir());
                GL_Vector lightPositionView = GL_Matrix.multiply(vieDirw, new GL_Vector(lightPos.x + 0.5f, lightPos.y + 0.5f, lightPos.z + 0.5f));

                // lightPositionView.z*=-1;
                ShaderManager.shaderLightingPass.setVec3("lights[" + i + "].Position", lightPositionView);

                ShaderManager.shaderLightingPass.setVec3("lights[" + i + "].Color", new GL_Vector(block.rf, block.gf, block.bf));

                // update attenuation parameters and calculate radius
                float constant = 0.1f; // note that we don't send this to the shader, we assume it is always 1.0 (in our case)
                float linear = 0.7f;
                float quadratic = 0.2f;


                ShaderManager.shaderLightingPass.setFloat("lights[" + i + "].Linear", linear);

                ShaderManager.shaderLightingPass.setFloat("lights[" + i + "].Quadratic", quadratic);
                i++;


            }
            renderZuobiao();



        }


        if (lineNeedUpdate) {

           // ShaderUtils.freshVao(ShaderManager.lineShaderConfig, ShaderManager.lineShaderConfig.getVao());

            lineNeedUpdate = false;//原来这个freshvao 是和 上面的绘制写在一起的 后来发现 在绘制groupblock 和animationblock的时候 也产生线段 就把freshvao放在这里了
        }
       /* if(Switcher.mouseState==Switcher.faceSelectMode) {
            minX = Math.min(prevFaceX, lastFaceX);
            minZ = Math.min(prevFaceZ, lastFaceZ);
            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, blockVao, Math.min(prevFaceX, lastFaceX), 0, Math.min(prevFaceZ, lastFaceZ), new GL_Vector(1, 1, 1), Math.max(lastFaceMaxX, prevMaxFaceX) - minX, 0.2f, Math.max(lastFaceMaxZ, prevMaxFaceZ) - minZ, 1f);
        }*/
        //ShaderManager.lineShaderConfig.getVao().getVertices().rewind();
        //ShaderUtils.freshVao(ShaderManager.lineShaderConfig, ShaderManager.lineShaderConfig.getVao());


/*
        ShaderUtils.finalDraw(ShaderManager.terrainShaderConfig,blockVao);

        ShaderUtils.finalDrawLine(ShaderManager.lineShaderConfig, ShaderManager.lineShaderConfig.getVao());*/


        // ShaderUtils.finalDraw(ShaderManager.shaderLightingPass, blockVao);
        //绘制

        if(blockVao.changed){
            ShaderUtils.freshVao(ShaderManager.anotherShaderConfig, blockVao);
            blockVao.changed=false;
        }
        ShaderUtils.finalDraw(ShaderManager.anotherShaderConfig, blockVao);


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
     * 调用getSelectBlock 来选择方块
     *
     * @param x
     * @param y
     * @return
     */

    public BaseBlock selectSingle(float x, float y) {
        Object[] results = this.getMouseChoosedBlock(x, y);
        BaseBlock theNearestBlock = (BaseBlock) results[0];

        if (theNearestBlock != null) {

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


    public BaseBlock getSelectFirstBlock() {
        if (selectBlockList.size() > 0) {
            return selectBlockList.get(0);
        }
        return null;
    }

    public void enterComponentEdit() {


        BaseBlock colorBlock = getSelectFirstBlock();

        if (colorBlock != null && (colorBlock instanceof AnimationBlock || colorBlock instanceof GroupBlock
        )) {
            Switcher.isEditComponent = true;
            currentChoosedGroupForEdit = (GroupBlock) colorBlock;
            currentChoosedGroupForEdit.x=0;
            currentChoosedGroupForEdit.y=0;
            currentChoosedGroupForEdit.z=0;
        } else {
            Switcher.isEditComponent = false;
            currentChoosedGroupForEdit = null;
        }
    }

    //选择区域
    public void selectMany(float minX, float minY, float maxX, float maxY, boolean clearbefore) {

        if (Switcher.isEditComponent) {
            currentChoosedGroupForEdit.selectMany(minX, minY, maxX, maxY, clearbefore);
            return;
            //只在当前组件内编辑
        }
        //遍历所有的方块查找所有的在方块里的方块
        //取出
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
          /*  GL_Vector[] gl_vectors = BoxModel.getSmaillPoint(colorBlock.x,colorBlock.y,colorBlock.z,colorBlock.width, colorBlock.height, colorBlock.thick);
           for(){

           }*/

            Vector2f xy = OpenglUtils.wordPositionToXY(ShaderManager.projection, new GL_Vector(colorBlock.x, colorBlock.y, colorBlock.z), GamingState.instance.camera.Position, GamingState.instance.camera.getViewDir());
            xy.x *= Constants.WINDOW_WIDTH;
            xy.y *= Constants.WINDOW_HEIGHT;
            if (xy.x > minX && xy.x < maxX &&
                    xy.y > minY && xy.y < maxY
                    ) {
                selectBlockList.add(colorBlock);
                //   colorBlock.setSelected(true);
            } else {
                //  colorBlock.setSelected(false);
            }
        }

        if (!clearbefore || selectBlockList.size() == 0) {
            selectSingle(minX, minY);
        }
        lineNeedUpdate = true;
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

    public void deleteSelect() {
       /* for( int i=0;i<selectBlockList.size();i++){
            ColorBlock colorBlock  =  selectBlockList.get(i);
            colorBlockList.remove(colorBlock);
        }*/
        if (Switcher.isEditComponent) {
            currentChoosedGroupForEdit.deleteSelect();
        } else {
            for (int i = selectBlockList.size() - 1; i >= 0; i--) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlockList.remove(colorBlock);

                blockAry[this.getIndex((int) colorBlock.x, (int) colorBlock.y, (int) colorBlock.z)] = null;
            }
            selectBlockList.clear();
        }
        needUpdate = true;
        lineNeedUpdate = true;
    }

    public void addBlock() {

        // Color commonColor =new Color( color.getRed() ,color.getGreen(),color.getBlue());


        minX = Math.min(prevFaceX, lastFaceX);
        minZ = Math.min(prevFaceZ, lastFaceZ);
        int width = Math.max(lastFaceMaxX, prevMaxFaceX) - minX;
        int thick = Math.max(lastFaceMaxZ, prevMaxFaceZ) - minZ;

        BaseBlock colorBlock = null;
        colorBlock = readyShootBlock.copy();
        for (int x = minX; x < width + minX; x++) {
            for (int z = minZ; z < thick + minZ; z++) {
                colorBlock = readyShootBlock.copy();
                //set(colorBlock,x,0,z,Math.max(width,1),1,Math.max(thick,1),red,green,blue,alpha);
                set(colorBlock, x, 0, z, 1, 1, 1, red, green, blue, alpha);
                addColorBlock(colorBlock);
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

        needUpdate = true;
        GamingState.editEngine.select(colorBlock);
    }

    public void set(BaseBlock block, int x, int y, int z, float width, float height, float thick, float red, float green, float blue, float alpha) {
        block.x = x;
        block.y = y;
        block.z = z;

        block.width = Math.max(width, 1);
        block.height = height;
        block.thick = Math.max(thick, 1);
        if (block instanceof ColorBlock) {
            ColorBlock colorBlock = (ColorBlock) block;
            colorBlock.rf = red;
            colorBlock.gf = green;
            colorBlock.bf = blue;

            colorBlock.opacity = alpha;
        }

    }

    public void select(BaseBlock block) {

        selectBlockList.clear();
        selectBlockList.add(block);
        lineNeedUpdate = true;
        needUpdate = true;
    }

    public float adjustWidth(float num, boolean position) {
        if (Switcher.size && position) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addWidth(num);//(false);

            }
        } else {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addX(num);//(false);

            }
        }
        lineNeedUpdate = true;
        needUpdate = true;
        return 0;
    }

    public float adjustHeight(float num, boolean position) {
        lineNeedUpdate = true;
        needUpdate = true;float value =0;
        if (Switcher.size && position) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                value = colorBlock.addHeight(num);//(false);

            }
            return 0;
        } else {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                value = colorBlock.addY(num);//(false);

            }
            return value;
        }

        // return 0;
    }

    public float adjustThick(float num, boolean position) {
        lineNeedUpdate = true;
        needUpdate = true;
        if (Switcher.size && position) {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addThick(num);//(false);

            }
        } else {
            for (int i = 0; i < selectBlockList.size(); i++) {
                BaseBlock colorBlock = selectBlockList.get(i);

                colorBlock.addZ(num);//(false);

            }
        }
        return 0;

    }

    public void copySelect() {
        for (int i = selectBlockList.size() - 1; i >= 0; i--) {
            BaseBlock colorBlock = selectBlockList.get(i).copy();
         /*   ColorBlock copyBlock =new ColorBlock(colorBlock.x,colorBlock.y,colorBlock.z);
            copyBlock.rf = colorBlock.rf;
            copyBlock.gf =colorBlock.gf;
            copyBlock.bf = colorBlock.bf;
            copyBlock.width = colorBlock.width;
            copyBlock.height =colorBlock.height;
            copyBlock.thick =colorBlock.thick;*/
            //colorBlock.selected=false;
            addColorBlock(colorBlock);
            selectBlockList.set(i, colorBlock);
            //  copyBlock.selected=true;

        }
    }
    //用指定颜色填充选择
    public void setColor(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        if (currentChoosedGroupForEdit != null) {
            currentChoosedGroupForEdit.setColor(red, green, blue, alpha);
            return;
        }

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
        lineNeedUpdate = true;
        needUpdate = true;
    }

    public void selectTheSame(){

        BaseBlock block = getSelectFirstBlock();
        int fromX=(int)block.x;
        int fromY=(int)block.y;
        int fromZ=(int)block.z;
        boolean hasBeside=true;
       // nowdir=0;
        List<BaseBlock > checkedBlockList =new ArrayList<>();
        List<BaseBlock > notCheckedBlockList =new ArrayList<>();
        checkedBlockList.add(block);
        BaseBlock nowBlock = block;
        while (hasBeside){
            for(int i=0;i<BoxModel.dirAry.length;i++){
                int nowX = (int)nowBlock.x;

               /* if(){

                }*/
            }

            //上下左右遍历
        }
        //blockAry
    }


    //用当前选择的方块填充选定对象
    public void setBlock() {

       /* if (currentChoosedGroupForEdit != null) {
            currentChoosedGroupForEdit.setColor(red, green, blue, alpha);
            return;
        }*/

        for (int i = selectBlockList.size() - 1; i >= 0; i--) {
            BaseBlock colorBlock = selectBlockList.get(i);
           colorBlockList.remove(colorBlock);
            BaseBlock block = readyShootBlock.copy();
            readyShootBlock.set(colorBlock.getX(),readyShootBlock.getY(),readyShootBlock.getZ());
            colorBlockList.add(readyShootBlock);

        }
        lineNeedUpdate = true;
        needUpdate = true;
    }


    public void saveWork() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
            for (BaseBlock colorBlock : colorBlockList) {
                // outputStream .write();
                stringBuffer.append(colorBlock.toString()).append("\n");
            }
            FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile(), stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reloadWork() {
        try {
            List<String> list = FileUtil.readFile2List(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
            for (String s : list) {
                JSONObject shapeObj = JSON.parseObject(s);
                String blockType = (String) ((JSONObject) shapeObj).get("blocktype");
                BaseBlock block = EditEngine.parse((JSONObject) shapeObj);
//                if ("imageblock".equals(blockType)) {
//                    block = ImageBlock.parse((JSONObject) shapeObj);
//
//
//                } else if ("colorblock".equals(blockType)) {
//                    block = ColorBlock.parse((JSONObject) shapeObj);
//                } else if ("groupblock".equals(blockType)) {
//                    block = GroupBlock.parse((JSONObject) shapeObj);
//                } else if ("animationblock".equals(blockType)) {
//                    block = AnimationBlock.parse((JSONObject) shapeObj);
//                }
                if (shapeObj.get("id") != null) {
                    block.id = shapeObj.getInteger("id");
                }
                addColorBlock(block);
            }
            needUpdate = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void seperateSelect() {
        if (selectBlockList.size() == 0) {
            LogUtil.println("未选中任何东西");
            return;
        }

        if(getSelectFirstBlock() instanceof  BoneRotateImageBlock){
            BoneRotateImageBlock boneRotateImageBlock =(BoneRotateImageBlock) getSelectFirstBlock();
            for(BaseBlock childBlock:boneRotateImageBlock.children){
                colorBlockList.add(childBlock);

            }
            return;
        }
        BaseBlock colorBlock = selectBlockList.get(0);

        for (int x = 0; x < colorBlock.width; x++) {
            for (int y = 0; y < colorBlock.height; y++) {
                for (int z = 0; z < colorBlock.thick; z++) {

                    BaseBlock childBlock = colorBlock.copy();
                    childBlock.x = colorBlock.x + x;
                    childBlock.y = colorBlock.y + y;
                    childBlock.z = colorBlock.z + z;

                    childBlock.width = 1;
                    childBlock.height = 1;
                    childBlock.thick = 1;


                    addColorBlock(childBlock);
                }
            }
        }
        selectBlockList.clear();
        colorBlockList.remove(colorBlock);

    }

    public void brushBlock(float x, float y) {
        if (currentChoosedGroupForEdit != null) {//内部绘制
            currentChoosedGroupForEdit.brushBlock(x, y, red, green, blue);
            return;
        }
        Object[] result = this.getMouseChoosedBlock(x, y);

        ColorBlock block = (ColorBlock) result[0];
        if (block == null)
            return;
        block.rf = red;
        block.gf = green;
        block.bf = blue;
        lineNeedUpdate = true;
        needUpdate = true;
    }

    //,float red,float green ,float blue
    public void shootBlock(float x, float y) {
        LogUtil.println("shoot outer");
        if (Switcher.mouseState != Switcher.shootMode) {
            return;
        }
        if (Switcher.isEditComponent) {
            currentChoosedGroupForEdit.shootBlock(x, y);
            return;


        }
        Object[] results = this.getMouseChoosedBlock(x, y);
        BaseBlock theNearestBlock = (BaseBlock) results[0];
        GL_Vector touchPoint = (GL_Vector) results[2];

        GL_Vector placePoint = (GL_Vector) results[3];
        /*endPoint=touchPoint;
        startPoint =placePoint;*/
        int face = (int) results[1];
        if (theNearestBlock != null) {
            BaseBlock addColorBlock = null;

            addColorBlock = readyShootBlock.copy();//new RotateColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            set(addColorBlock, (int) (placePoint.x), (int) (placePoint.y), (int) (placePoint.z), 1, 1, 1, red, green, blue, alpha);
         /*   }else{
                addColorBlock = new ColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            }*/


            addColorBlock(addColorBlock);
            if (addColorBlock instanceof ColorBlock && ((ColorBlock) addColorBlock).isLight) {
                lightBlockHashMap.put(new GL_Vector((int) addColorBlock.x, (int) addColorBlock.y, (int) addColorBlock.z), new WeakReference<ColorBlock>((ColorBlock) addColorBlock));

            }

            LogUtil.println("是那个面:" + face);
            needUpdate = true;

        } else {//射在地面上
            BaseBlock addColorBlock = null;


            addColorBlock = readyShootBlock.copy();//new RotateColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            set(addColorBlock, curentX, 0, curentZ, addColorBlock.width, addColorBlock.height, addColorBlock.thick, red, green, blue, alpha);

            this.addColorBlock(addColorBlock);//假如到这里
            if (addColorBlock instanceof ColorBlock && ((ColorBlock) addColorBlock).isLight) {
                lightBlockHashMap.put(new GL_Vector((int) addColorBlock.x, (int) addColorBlock.y, (int) addColorBlock.z), new WeakReference<ColorBlock>((ColorBlock) addColorBlock));

            }
            needUpdate = true;
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

    public Object[] getMouseChoosedBlock(float x, float y) {

        GL_Vector from = GamingState.instance.camera.Position.copyClone();
        direction = OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(), x, Constants.WINDOW_HEIGHT - y);
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
            AABB aabb = new AABB(new Vector3f(colorBlock.x, colorBlock.y, colorBlock.z), new Vector3f(colorBlock.x + colorBlock.width, colorBlock.y + colorBlock.height, colorBlock.z + colorBlock.thick));

            // LogUtil.println(fromV.toString() );
            // LogUtil.println(directionV.toString() );
            if ((xiangjiao = aabb.intersectRectangle2(fromV, directionV)) != null) {//这里进行了按照list的顺序进行选择 其实应该按照距离的最近选择

                //计算点在了那个面上
                //有上下左右前后6个面
                //LogUtil.println("选中了");
                float _tempDistance = xiangjiao[0] * xiangjiao[0] + xiangjiao[1] * xiangjiao[1] + xiangjiao[2] * xiangjiao[2];

                if (distance == 0 || _tempDistance < distance) {
                    distance = _tempDistance;
                    theNearestBlock = colorBlock;
                    right = xiangjiao;
                }


            }


        }

        int face = -1;
        GL_Vector touchPoint = new GL_Vector();
        GL_Vector placePoint = new GL_Vector();
        if (theNearestBlock != null) {
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

                                placePoint.x = touchPoint.x = (from.x + right[0]);
                                placePoint.y = touchPoint.y = (from.y + right[1]);

                                placePoint.z = touchPoint.z = (from.z + right[2]);

                           /*     if (face == Constants.BACK ) {
                                    placePoint.z -=1;
                                } else if (face == Constants.LEFT) {
                                    placePoint.x -= 1;
                                } else if (face == Constants.BOTTOM) {
                                    placePoint.y -= 1;
                                }*/

                                if (face == Constants.BACK || face == Constants.FRONT) {
                                    float fangdabeishu = (touchPoint.z - from.z) / direction.z;
                                    touchPoint.x = (from.x + direction.x * fangdabeishu);
                                    touchPoint.y = (from.y + direction.y * fangdabeishu);
                                } else if (face == Constants.LEFT || face == Constants.RIGHT) {
                                    float fangdabeishu = (touchPoint.x - from.x) / direction.x;
                                    touchPoint.y = (from.y + direction.y * fangdabeishu);
                                    touchPoint.z = (from.z + direction.z * fangdabeishu);
                                } else if (face == Constants.BOTTOM || face == Constants.TOP) {
                                    float fangdabeishu = (touchPoint.y - from.y) / direction.y;
                                    touchPoint.x = (from.x + direction.x * fangdabeishu);
                                    touchPoint.z = (from.z + direction.z * fangdabeishu);
                                }
                                placePoint.x = touchPoint.x;
                                placePoint.y = touchPoint.y;

                                placePoint.z = touchPoint.z;
                                if (face == Constants.BACK) {
                                    placePoint.z -= 1;
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

                                return new Object[]{theNearestBlock, face, touchPoint, placePoint};

                            }
                        }
                    }
                }
            }
        }
        return new Object[]{theNearestBlock, face, touchPoint, placePoint};
    }

    public void brushImageOnBlock(float x, float y) {


        if (currentChoosedGroupForEdit != null) {//内部绘制
            currentChoosedGroupForEdit.brushImageOnBlock(x, y, red, green, blue);
            return;
        }

        Object[] result = this.getMouseChoosedBlock(x, y);
        BaseBlock block = (BaseBlock) result[0];
        int face = (int) result[1];

        if (block != null && block instanceof ImageBlock) {
            ImageBlock imageBlock = (ImageBlock) block;
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
        LogUtil.println("是那个面:" + face);

        lineNeedUpdate = true;
        needUpdate = true;
    }

    /**
     * 当前选中的纹理
     */
    public TextureInfo nowTextureInfo;

    public void saveSelectAsComponent(String name) {


        StringBuffer stringBuffer = new StringBuffer();
        try {
            //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
            for (BaseBlock block : selectBlockList) {
                // outputStream .write();

                stringBuffer.append(block.toString()).append("\n");
            }
            FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("save").resolve(name + ".block").toFile(), stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveSelectAsItem(String name) {

//        {
//            name:"grass",
//                    icon:"grass_top",
//                type:"block",
//                remark:"草方块",
//                shape:"grass",
//                baseon:"mantle"
//        },
        StringBuffer stringBuffer = new StringBuffer();
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

            FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("config/item").resolve(name + ".block").toFile(), stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载组件
     * @param file
     */

    public void readComponentFromFile(File file) {
        try {
            List<String> list = FileUtil.readFile2List(file);
            for (String s : list) {
                JSONObject shapeObj = JSON.parseObject(s);
                String blockType = (String) ((JSONObject) shapeObj).get("blocktype");
                BaseBlock block = EditEngine.parse((JSONObject) shapeObj);
//                if ("imageblock".equals(blockType)) {
//                    block = ImageBlock.parse((JSONObject) shapeObj);
//                } else if ("colorblock".equals(blockType)) {
//                    block = ColorBlock.parse((JSONObject) shapeObj);
//                } else if ("groupblock".equals(blockType)) {
//                    block = GroupBlock.parse((JSONObject) shapeObj);
//                } else if ("animationblock".equals(blockType)) {
//                    block = AnimationBlock.parse((JSONObject) shapeObj);
//                } else if ("rotatecolorblock".equals(blockType)) {
//                    block = RotateColorBlock2.parse((JSONObject) shapeObj);
//                }else if ("rotateimageblock".equals(blockType)) {
//                    block = RotateImageBlock.parse((JSONObject) shapeObj);
//                }else if ("bonerotateimageblock".equals(blockType)) {
//                    block = BoneRotateImageBlock.parse((JSONObject) shapeObj);
//                }
                if (shapeObj.get("id") != null) {
                    block.id = shapeObj.getInteger("id");
                }
//


                addColorBlock(block);
            }
            needUpdate = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void buildAnimationComponent() {
        AnimationBlock group = new AnimationBlock(1, 1, 1, 1, 1, 1);
        Integer minx = null, miny = null, minz = null;
        Float maxx = null, maxy = null, maxz = null;

        //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
        for (int i = selectBlockList.size() - 1; i >= 0; i--) {
            BaseBlock colorBlock = selectBlockList.get(i);
            group.addChild(selectBlockList.get(i));
            selectBlockList.remove(i);
            colorBlockList.remove(colorBlock);
            if (minx == null) {
                minx = (int) colorBlock.x;
                miny = (int) colorBlock.y;
                minz = (int) colorBlock.z;
                maxx = (int) colorBlock.x + colorBlock.width;
                maxy = (int) colorBlock.y + colorBlock.height;
                maxz = (int) colorBlock.z + colorBlock.thick;
            } else {
                if (colorBlock.x < minx) {
                    minx = (int) colorBlock.x;
                }
                if (colorBlock.y < miny) {
                    miny = (int) colorBlock.y;
                }
                if (colorBlock.z < minz) {
                    minz = (int) colorBlock.z;
                }
                if (colorBlock.x + colorBlock.width > maxx) {
                    maxx = (int) colorBlock.x + colorBlock.width;
                }
                if (colorBlock.y + colorBlock.height > maxy) {
                    maxy = (int) colorBlock.y + colorBlock.height;
                }
                if (colorBlock.z + colorBlock.thick > maxz) {
                    maxz = (int) colorBlock.z + colorBlock.thick;
                }
            }
        }
        group.x = minx;
        group.y = miny;
        group.z = minz;
        group.width = maxx - minx;
        group.height = maxy - miny;
        group.thick = maxz - minz;
        //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
        for (int i = group.colorBlockList.size() - 1; i >= 0; i--) {
            BaseBlock colorBlock = group.colorBlockList.get(i);
            colorBlock.x -= group.x;
            colorBlock.y -= group.y;
            colorBlock.z -= group.z;
        }
        group.reComputePoints();

       /* for(BaseBlock colorBlock : group.colorBlockList){
            colorBlock.x-= group.x;
            colorBlock.y-=group.y;
            colorBlock.z-=group.z;
        }*/
        this.addColorBlock(group);

    }


    public void buildComponent() {
        GroupBlock group = new GroupBlock(1, 1, 1, 1, 1, 1);
        Integer minx = null, miny = null, minz = null;
        Float maxx = null, maxy = null, maxz = null;

        //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
        for (int i = selectBlockList.size() - 1; i >= 0; i--) {
            BaseBlock colorBlock = selectBlockList.get(i);
            group.addChild(selectBlockList.get(i));
            selectBlockList.remove(i);
            colorBlockList.remove(colorBlock);
            if (minx == null) {
                minx = (int) colorBlock.x;
                miny = (int) colorBlock.y;
                minz = (int) colorBlock.z;
                maxx = (int) colorBlock.x + colorBlock.width;
                maxy = (int) colorBlock.y + colorBlock.height;
                maxz = (int) colorBlock.z + colorBlock.thick;
            } else {
                if (colorBlock.x < minx) {
                    minx = (int) colorBlock.x;
                }
                if (colorBlock.y < miny) {
                    miny = (int) colorBlock.y;
                }
                if (colorBlock.z < minz) {
                    minz = (int) colorBlock.z;
                }
                if (colorBlock.x + colorBlock.width > maxx) {
                    maxx = (int) colorBlock.x + colorBlock.width;
                }
                if (colorBlock.y + colorBlock.height > maxy) {
                    maxy = (int) colorBlock.y + colorBlock.height;
                }
                if (colorBlock.z + colorBlock.thick > maxz) {
                    maxz = (int) colorBlock.z + colorBlock.thick;
                }
            }
        }
        group.x = minx;
        group.y = miny;
        group.z = minz;
        group.width = maxx - minx;
        group.height = maxy - miny;
        group.thick = maxz - minz;
        //FileOutputStream outputStream =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("save").resolve("save1.block").toFile());
        for (int i = group.colorBlockList.size() - 1; i >= 0; i--) {
            BaseBlock colorBlock = group.colorBlockList.get(i);
            colorBlock.x -= group.x;
            colorBlock.y -= group.y;
            colorBlock.z -= group.z;
        }
        group.reComputePoints();

       /* for(BaseBlock colorBlock : group.colorBlockList){
            colorBlock.x-= group.x;
            colorBlock.y-=group.y;
            colorBlock.z-=group.z;
        }*/
        this.addColorBlock(group);

    }

   /* List<ColorGroup> groups =new ArrayList<>();*/

    public void adjustComponent(float xzoom, float yzoom, float zzoom, float xoffset, float yoffset, float zoffset) {
        if (selectBlockList.size() > 0) {
            if (selectBlockList.get(0) instanceof AnimationBlock) {
                AnimationBlock animationBlock = (AnimationBlock) selectBlockList.get(0);
                animationBlock.xzoom = xzoom;
                animationBlock.yzoom = yzoom;
                animationBlock.zzoom = zzoom;

                animationBlock.xoffset = xoffset;
                animationBlock.yoffset = yoffset;
                animationBlock.zoffset = zoffset;
                animationBlock.scale(xzoom, yzoom, zzoom);
            }else
            if (selectBlockList.get(0) instanceof GroupBlock) {
                GroupBlock animationBlock = (GroupBlock) selectBlockList.get(0);

                animationBlock.scale(xzoom, yzoom, zzoom);
            }else{
        for(BaseBlock block : selectBlockList){
            block.x=block.x*xzoom;
            block.y=block.y*yzoom;
            block.z=block.z*zzoom;
            block.width=block.width*xzoom;
            block.height=block.height*yzoom;
            block.thick=block.thick*zzoom;
            if(block instanceof  RotateBlock){
                RotateBlock rotateBlock  = (RotateBlock)block;
                rotateBlock.setCenterX(rotateBlock.getCenterX()*xzoom);
                rotateBlock.setCenterY(rotateBlock.getCenterY()*yzoom);
                rotateBlock.setCenterZ(rotateBlock.getCenterZ()*zzoom);
            }
        }


            }
        }
        lineNeedUpdate = true;
        needUpdate = true;
    }


    public void mouseMove(int x, int y) {
        y = Constants.WINDOW_HEIGHT - y;
        // mouseOvercolorBlock = selectSingle(GamingState.instance.camera.Position.copyClone(), OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(),x,Constants.WINDOW_HEIGHT-y));
        //计算 鼠标和xz平面的交接处

        if (currentChoosedGroupForEdit != null) {//内部绘制
            //((GroupBlock )currentChoosedGroupForEdit).getMouseChoosedBlock(x,y);
            Object[] results = currentChoosedGroupForEdit.getMouseChoosedBlock(x, y);
            BaseBlock theNearestBlock = (BaseBlock) results[0];
            GL_Vector touchPoint = (GL_Vector) results[2];
            GL_Vector placePoint = (GL_Vector) results[3];
           /* endPoint=touchPoint;
            startPoint =placePoint;*/
            int face = (int) results[1];
            if (theNearestBlock != null) {

                return;
            }
        }


    }


    public int nowAxis;
    public int rotateScreenX, rotateScreenY;

    public void mouseClick(int x, int y) {


        //和3个坐标旋转方块进行检测
        if (Switcher.mouseState == Switcher.rotateMode || Switcher.mouseState == Switcher.moveMode || Switcher.mouseState == Switcher.resizeMode  || Switcher.mouseState == Switcher.moveAxisMode) {
            rotateScreenX = x;
            rotateScreenY = y;
            GL_Vector from = GamingState.instance.camera.Position.copyClone();
            direction = OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(), x, y);
            direction = direction.normalize();

            // LogUtil.println("开始选择");
            Vector3f fromV = new Vector3f(from.x, from.y, from.z);
            Vector3f directionV = new Vector3f(direction.x, direction.y, direction.z);


            AABB aabb = new AABB(new Vector3f(xPoint.x, xPoint.y, xPoint.z), new Vector3f(xPoint.x + 0.2f, xPoint.y + 0.2f, xPoint.z + 0.2f));

            // LogUtil.println(fromV.toString() );
            // LogUtil.println(directionV.toString() );
            if ((aabb.intersectRectangle2(fromV, directionV)) != null) {//这里进行了按照list的顺序进行选择 其实应该按照距离的最近选择

                LogUtil.println("x axsis");

                nowAxis = 1;
            }


            aabb = new AABB(new Vector3f(yPoint.x, yPoint.y, yPoint.z), new Vector3f(yPoint.x + 0.2f, yPoint.y + 0.2f, yPoint.z + 0.2f));

            // LogUtil.println(fromV.toString() );
            // LogUtil.println(directionV.toString() );
            if ((aabb.intersectRectangle2(fromV, directionV)) != null) {//这里进行了按照list的顺序进行选择 其实应该按照距离的最近选择

                LogUtil.println("y axsis");
                nowAxis = 2;
            }


            aabb = new AABB(new Vector3f(zPoint.x, zPoint.y, zPoint.z), new Vector3f(zPoint.x + 0.2f, zPoint.y + 0.2f, zPoint.z + 0.2f));

            // LogUtil.println(fromV.toString() );
            // LogUtil.println(directionV.toString() );
            if ((aabb.intersectRectangle2(fromV, directionV)) != null) {//这里进行了按照list的顺序进行选择 其实应该按照距离的最近选择

                LogUtil.println("z axsis");
                nowAxis = 3;
            }
            return;
        }

        Object[] results = this.getMouseChoosedBlock(x, Constants.WINDOW_HEIGHT - y);
        BaseBlock theNearestBlock = (BaseBlock) results[0];
        GL_Vector touchPoint = (GL_Vector) results[2];
        GL_Vector placePoint = (GL_Vector) results[3];


        int face = (int) results[1];
        if (theNearestBlock != null) {
            LogUtil.println("碰到的面是"+touchPoint);
            LogUtil.println("name:"+theNearestBlock.name);
            LogUtil.println("碰到的面是"+touchPoint.copyClone().sub(new GL_Vector(theNearestBlock.x,theNearestBlock.y,theNearestBlock.z)));
            lastTouchPoint = touchPoint.copyClone();
            endPoint = touchPoint;
            if (face == Constants.TOP) {
                endPoint.y -= 1;
            }
            if (face == Constants.RIGHT) {
                endPoint.x -= 1;
            }
            if (face == Constants.FRONT) {
                endPoint.z -= 1;
            }
            startPoint = placePoint;
            nowFace = face;
           /* BaseBlock addColorBlock =null;

                addColorBlock = readyShootBlock.copy();//new RotateColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            set(addColorBlock,(int) (placePoint.x), (int) (placePoint.y ), (int) (placePoint.z ),1,1,1,red,green,blue,alpha);
            }else{
                addColorBlock = new ColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            }


            addColorBlock(addColorBlock);


            LogUtil.println("是那个面:"+face);*/

        } else {//射在地面上

            // y=Constants.WINDOW_HEIGHT-y;
            GL_Vector from = GamingState.instance.camera.Position;
            GL_Vector viewDir = OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(), x, y);

            float weizhi = -from.y / viewDir.y;
            curentX = (int) (from.x + weizhi * viewDir.x);
            curentZ = (int) (from.z + weizhi * viewDir.z);
            endPoint.x = startPoint.x = curentX;
            endPoint.z = startPoint.z = curentZ;
            endPoint.y = startPoint.y = 0;
            nowFace = Constants.TOP;
            lastFace = Constants.TOP;


        }


       /* if(Switcher.mouseState==Switcher.faceSelectMode) {
            prevFaceX = curentX;
            prevMaxFaceX = curentX + 1;
            prevFaceZ = curentZ;
            prevMaxFaceZ = curentZ + 1;
            lastFaceX = curentX + 1;
            lastFaceZ = curentZ + 1;

        }else */
        if (Switcher.mouseState == Switcher.shootMode || Switcher.mouseState == Switcher.faceSelectMode) {//选择的开始也需要确定起点

            touchStartPoint.x = endPoint.x;
            placeStartPoint.x = startPoint.x;

            touchStartPoint.y = endPoint.y;
            placeStartPoint.y = startPoint.y;

            touchStartPoint.z = endPoint.z;
            placeStartPoint.z = startPoint.z;

            if (placeStartPoint.x > 100) {
                LogUtil.println("123");
            }
            lastFace = nowFace;

        } else if (Switcher.mouseState == Switcher.pullMode) {
            pullSelect();
        } else if (Switcher.mouseState == Switcher.pushMode) {
            pushSelect();
        }
        lineNeedUpdate = true;
        needUpdate = true;
    }

    public int lastFace = 0;
    int lastPaintX = 0;
    int lastPaintZ = 0;

    public void mouseDrag(int x, int y) {


        if (Switcher.mouseState == Switcher.rotateMode) {
            for(BaseBlock block : selectBlockList){
                if(block instanceof  RotateBlock ){
                    RotateBlock rotateBlock= (RotateBlock)block;
                    if (nowAxis == 1) {
                        //x
                        rotateBlock.rotateX((y - (Constants.WINDOW_HEIGHT - rotateScreenY)) * 0.01f);
                        needUpdate = true;
                    } else if (nowAxis == 2) {
                        needUpdate = true;
                        //y
                        rotateBlock.rotateY((x - rotateScreenX) * 0.01f);
                    } else if (nowAxis == 3) {
                        needUpdate = true;
                        //z
                        rotateBlock.rotateZ((y - (Constants.WINDOW_HEIGHT - rotateScreenY)) * 0.01f);
                    }
                }else if(block instanceof  GroupBlock){


                    GroupBlock rotateBlock = (GroupBlock) block;
                    //groupBlock.rotateX((y - (Constants.WINDOW_HEIGHT - rotateScreenY)) * 0.01f);
                    if (nowAxis == 1) {
                        //x
                        rotateBlock.rotateX((y - (Constants.WINDOW_HEIGHT - rotateScreenY)) * 0.01f);
                        needUpdate = true;
                    } else if (nowAxis == 2) {
                        needUpdate = true;
                        //y
                        rotateBlock.rotateY((x - rotateScreenX) * 0.01f);
                    } else if (nowAxis == 3) {
                        needUpdate = true;
                        //z
                        rotateBlock.rotateZ((y - (Constants.WINDOW_HEIGHT - rotateScreenY)) * 0.01f);
                    }
                    needUpdate = true;
                }
            }
          /*  if (nowMoveScaleRotateBlock!=null) {
                BaseBlock block = nowMoveScaleRotateBlock;
                if (block instanceof RotateBlock) {
                    RotateBlock rotateBlock = (RotateBlock) block;
                    if (nowAxis == 1) {
                        //x
                        rotateBlock.rotateX((y - (Constants.WINDOW_HEIGHT - rotateScreenY)) * 0.01f);
                        needUpdate = true;
                    } else if (nowAxis == 2) {
                        needUpdate = true;
                        //y
                        rotateBlock.rotateY((x - rotateScreenX) * 0.01f);
                    } else if (nowAxis == 3) {
                        needUpdate = true;
                        //z
                        rotateBlock.rotateZ((y - (Constants.WINDOW_HEIGHT - rotateScreenY)) * 0.01f);
                    }
                }

            }*/
            return;
        }

        if (Switcher.mouseState == Switcher.moveMode) {

            if (nowMoveScaleRotateBlock!=null) {
                BaseBlock block = nowMoveScaleRotateBlock;
                GL_Vector from = GamingState.instance.camera.Position;
                GL_Vector viewDir = OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(), x, Constants.WINDOW_HEIGHT-y).normalize();

//                LogUtil.println( "y:"+y);
//                LogUtil.println("viewDir:"+viewDir);
                //计算出现在的xyz
                if (nowAxis == 1) {
                    //x轴线移动 所以和z=原来的平面有交际 计算出这个焦点位置
                    //x
                    float[] position = caculateWithY(from, viewDir,block.y);
//                    LogUtil.println("viewDir.x:"+viewDir.x);
                    block.x = position[0] - jianTouSize-2.7f;
                   // LogUtil.println("移动后的x:"+block.x);
                    needUpdate = true;    lineNeedUpdate=true;
                } else if (nowAxis == 2) {
                    needUpdate = true;lineNeedUpdate=true;
                    //y
                    //LogUtil.println("viewDir:"+viewDir);
                    float[] position = caculateWithZ(from, viewDir,block.z);
                    block.y = position[1] - jianTouSize-2.7f;

                } else if (nowAxis == 3) {
                    needUpdate = true;
                    lineNeedUpdate=true;
                    //z
                    float[] position = caculateWithY(from, viewDir,block.y);
                    block.z = position[2] - jianTouSize-2.7f;
                }
            }


            return;
        }

        if (Switcher.mouseState == Switcher.resizeMode) {

            if (nowMoveScaleRotateBlock!=null) {
                BaseBlock block = nowMoveScaleRotateBlock;
                GL_Vector from = GamingState.instance.camera.Position;
                GL_Vector viewDir = OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(), x, y);

                //计算出现在的xyz
                if (nowAxis == 1) {
                    //x轴线移动 所以和z=原来的平面有交际 计算出这个焦点位置
                    //x
                    float[] position = caculateWithZ(from, viewDir,block.z);
                    block.width=position[0]-jianTouSize;
                    needUpdate = true;    lineNeedUpdate=true;
                } else if (nowAxis == 2) {
                    needUpdate = true;    lineNeedUpdate=true;
                    //y
                    float[] position = caculateWithX(from, viewDir,block.x);
                    block.height=position[1]-jianTouSize;

                } else if (nowAxis == 3) {
                    needUpdate = true;    lineNeedUpdate=true;
                    //z
                    float[] position = caculateWithY(from, viewDir,block.y);
                    block.thick=position[2]-jianTouSize;
                }
            }


            return;
        }


        if (Switcher.mouseState == Switcher.moveAxisMode) {

            if (nowMoveScaleRotateBlock!=null) {
               RotateBlock block = (RotateBlock)nowMoveScaleRotateBlock;
                GL_Vector from = GamingState.instance.camera.Position;
                GL_Vector viewDir = OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(), x, Constants.WINDOW_HEIGHT-y).normalize();



               // LogUtil.println( "y:"+y);
                //LogUtil.println("viewDir:"+viewDir);
               // 计算出现在的xyz
                if (nowAxis == 1) {
                    //x轴线移动 所以和z=原来的平面有交际 计算出这个焦点位置
                    //x
                    float[] position = caculateWithY(from, viewDir,nowMoveScaleRotateBlock.y);
//                    LogUtil.println("viewDir.x:"+viewDir.x);
                    float newX= position[0] - jianTouSize;
                    block.setCenterX(newX - nowMoveScaleRotateBlock.x-2.7f);
                    // LogUtil.println("移动后的x:"+block.x);
                    needUpdate = true;    lineNeedUpdate=true;
                } else if (nowAxis == 2) {
                    needUpdate = true;lineNeedUpdate=true;
                    //y
                    //LogUtil.println("viewDir:"+viewDir);
                    float[] position = caculateWithZ(from, viewDir,nowMoveScaleRotateBlock.z-2.7f);


                    float newY= position[1] - jianTouSize;
                    block.setCenterY(newY - nowMoveScaleRotateBlock.y-2.7f);


                } else if (nowAxis == 3) {
                    needUpdate = true;
                    lineNeedUpdate=true;
                    //z
                    float[] position = caculateWithY(from, viewDir,nowMoveScaleRotateBlock.y);


                    float newZ= position[2] - jianTouSize;
                    block.setCenterZ(newZ - nowMoveScaleRotateBlock.z);

                }



                if (nowAxis == 1 ||nowAxis == 2 || nowAxis == 3 ) {
                    for (BaseBlock blockOther : selectBlockList) {
                        if(block==blockOther)continue;
                        if (blockOther instanceof RotateBlock) {
                            RotateBlock rotateBlock = (RotateBlock) blockOther;

                            rotateBlock.setCenterX(nowMoveScaleRotateBlock.x - ((BaseBlock) rotateBlock).getX() + block.getCenterX());

                            rotateBlock.setCenterY(nowMoveScaleRotateBlock.y - ((BaseBlock) rotateBlock).getY() + block.getCenterY());


                            rotateBlock.setCenterZ(nowMoveScaleRotateBlock.z - ((BaseBlock) rotateBlock).getZ() + block.getCenterZ());


                        }
                    }
                }
            }


            return;
        }

        Object[] results = this.getMouseChoosedBlock(x, y);
        BaseBlock theNearestBlock = (BaseBlock) results[0];
        GL_Vector touchPoint = (GL_Vector) results[2];
        GL_Vector placePoint = (GL_Vector) results[3];

        int face = (int) results[1];
        if (theNearestBlock != null) {

            endPoint = touchPoint;
            if (face == Constants.TOP) {
                endPoint.y -= 1;
            }
            if (face == Constants.RIGHT) {
                endPoint.x -= 1;
            }
            if (face == Constants.FRONT) {
                endPoint.z -= 1;
            }
            startPoint = placePoint;
            nowFace = face;
           /* BaseBlock addColorBlock =null;

                addColorBlock = readyShootBlock.copy();//new RotateColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            set(addColorBlock,(int) (placePoint.x), (int) (placePoint.y ), (int) (placePoint.z ),1,1,1,red,green,blue,alpha);
            }else{
                addColorBlock = new ColorBlock((int) (from.x + right[0]), (int) (from.y + right[1]), (int) (from.z + right[2]));
            }


            addColorBlock(addColorBlock);


            LogUtil.println("是那个面:"+face);*/

        } else {//射在地面上

            y = Constants.WINDOW_HEIGHT - y;
            GL_Vector from = GamingState.instance.camera.Position;
            GL_Vector viewDir = OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(), x, y);

            float weizhi = -from.y / viewDir.y;
            curentX = (int) (from.x + weizhi * viewDir.x);
            curentZ = (int) (from.z + weizhi * viewDir.z);
            endPoint.x = startPoint.x = curentX;
            endPoint.z = startPoint.z = curentZ;
            nowFace = Constants.TOP;
            endPoint.y = startPoint.y = 0;


        }

      /*  if(Switcher.mouseState==Switcher.faceSelectMode) {
            lastFaceX = curentX;
            lastFaceZ = curentZ;
            lastFaceMaxX = curentX + 1;
            lastFaceMaxZ = curentZ + 1;
        }else */
        if (Switcher.mouseState == Switcher.faceSelectMode) {
            //开始选择了哈哈哈
            if (placeEndPoint.x != endPoint.x || placeEndPoint.z != endPoint.z || placeEndPoint.y != endPoint.y) {
                // shootBlock(x,y);
                lastPaintX = curentX;
                lastPaintZ = curentZ;
                touchEndPoint.x = endPoint.x;
                placeEndPoint.x = startPoint.x;

                touchEndPoint.y = endPoint.y;
                placeEndPoint.y = startPoint.y;

                touchEndPoint.z = endPoint.z;
                placeEndPoint.z = startPoint.z;

                reComputeSelect();//重新设置选择的对象
                lineNeedUpdate = true;
                needUpdate = true;
            }
        } else if (Switcher.mouseState == Switcher.shootMode) {


            if (placeEndPoint.x != endPoint.x || placeEndPoint.z != endPoint.z || placeEndPoint.y != endPoint.y) {
                // shootBlock(x,y);
                lastPaintX = curentX;
                lastPaintZ = curentZ;
                touchEndPoint.x = endPoint.x;
                placeEndPoint.x = startPoint.x;

                touchEndPoint.y = endPoint.y;
                placeEndPoint.y = startPoint.y;

                touchEndPoint.z = endPoint.z;
                placeEndPoint.z = startPoint.z;

                reComputeAppend();
                lineNeedUpdate = true;
                needUpdate = true;
            }
        } else if (Switcher.mouseState == Switcher.shootComponentMode) {
            if (lastPaintX != curentX || lastPaintZ != curentZ) {
                shootBlock(x, y);
                lastPaintX = curentX;
                lastPaintZ = curentZ;
                lineNeedUpdate = true;
                needUpdate = true;
            }

        }

    }

    public void mouseUp(int x, int y) {
        /*if(Switcher.mouseState==Switcher.faceSelectMode) {
            lastFaceX=curentX;
            lastFaceZ=curentZ;
        }else */
        if (Switcher.mouseState == Switcher.shootMode) {
            addColorBlockAll(appendingBlockList);

            for (BaseBlock block : appendingBlockList) {

                this.addToAry(block);

            }
            appendingBlockList.clear();


        }else
         if (Switcher.mouseState == Switcher.rotateMode || Switcher.mouseState == Switcher.moveMode || Switcher.mouseState == Switcher.resizeMode  || Switcher.mouseState == Switcher.moveAxisMode) {
            nowAxis = 0;
            needUpdate = true;
            lineNeedUpdate = true;
        }
        needUpdate = true;
        lineNeedUpdate = true;
    }

    /**
     * 获得当前动画有多少针
     *
     * @return
     */
    public int getCurrentColorGroupAnimationFrameCount() {
        if (selectBlockList.size() > 0 && selectBlockList.get(0) instanceof AnimationBlock) {
            return ((AnimationBlock) selectBlockList.get(0)).animations.size();
        }
        return 0;
    }

    public void saveToCurFrame() {
        AnimationBlock animationBlock = (AnimationBlock) currentChoosedGroupForEdit;
        if (animationBlock != null) {
            animationBlock.saveToCurFrame();
        }
        // currentChoosedGroupForEdit.animations(currentChoosedGroupForEdit.)
    }

    public void deleteCurFrame() {
        AnimationBlock animationBlock = (AnimationBlock) currentChoosedGroupForEdit;
        if (animationBlock != null) {
            animationBlock.deleteCurFrame();
        }
    }


    /**
     * colorgroup如何显示成一个动画  动画是由帧组成的 每个帧都可以是完全不同的东西 但是 所有的帧都挂靠在 一个父级的 ColorGroup里面
     * 把当前的colorgroup 复制并新增一个动画帧
     */
    public void currentColorAddGroupAnimationFrame() {
        if (selectBlockList.size() > 0 && selectBlockList.get(0) instanceof AnimationBlock) {
            AnimationBlock group = (AnimationBlock) selectBlockList.get(0);
            AnimationBlock newGroup = group.copy();
            newGroup.animations = null;
            GroupBlock groupBlock = new GroupBlock();
            groupBlock.colorBlockList = newGroup.colorBlockList;
            group.animations.add(groupBlock);
        }

    }


    /**
     * 改变当前选中的组件 由鼠表选中组件 触发
     *
     * @param componentName
     */
    public void changeCurrentComponent(String componentName) {
        // chooseColorGroup = colorGroupHashMap.get(componentName);
        readyShootBlock = colorGroupHashMap.get(componentName);
    }

    /*public void loadColorGroup(String componentName,List<String > contents) {
        chooseColorGroup = colorGroupHashMap.get(componentName);
    }*/
    public int readColorGroupFromList(List<String> contents, int nowIndex, AnimationBlock animationBlock) {

        String groupInfo = contents.get(nowIndex);
        String infoAry[] = groupInfo.split(",");
        animationBlock.width = Float.valueOf(infoAry[0]);
        animationBlock.height = Float.valueOf(infoAry[1]);
        animationBlock.thick = Float.valueOf(infoAry[2]);

        animationBlock.xoffset = Float.valueOf(infoAry[3]);
        animationBlock.yoffset = Float.valueOf(infoAry[4]);
        animationBlock.zoffset = Float.valueOf(infoAry[5]);

        animationBlock.xzoom = Float.valueOf(infoAry[6]);
        animationBlock.yzoom = Float.valueOf(infoAry[7]);
        animationBlock.zzoom = Float.valueOf(infoAry[8]);
        int lastIndex = 0;
        for (int i = nowIndex + 1, size = contents.size(); i < size; i++) {
            String s = contents.get(i);
            if (s.equalsIgnoreCase("animation")) {
                return i + 1;
            }
            String[] ary = s.split(",");
            if (ary.length > 10) {
                ImageBlock block = new ImageBlock();
                set(block, Math.round(Float.valueOf(ary[0])), Math.round(Float.valueOf(ary[1])), Math.round(Float.valueOf(ary[2])), Float.valueOf(ary[3]), Float.valueOf(ary[4]), Float.valueOf(ary[5]),
                        Float.valueOf(ary[6]), Float.valueOf(ary[7]), Float.valueOf(ary[8]), Float.valueOf(ary[9]));
                block.top = TextureManager.getTextureInfo(String.valueOf(ary[10]));
                block.bottom = TextureManager.getTextureInfo(String.valueOf(ary[11]));
                block.front = TextureManager.getTextureInfo(String.valueOf(ary[12]));
                block.back = TextureManager.getTextureInfo(String.valueOf(ary[13]));
                block.left = TextureManager.getTextureInfo(String.valueOf(ary[14]));
                block.right = TextureManager.getTextureInfo(String.valueOf(ary[15]));
                animationBlock.colorBlockList.add(block);
            } else {
                ColorBlock colorBlock = new ColorBlock(Math.round(Float.valueOf(ary[0])), Math.round(Float.valueOf(ary[1])), Math.round(Float.valueOf(ary[2])));
                colorBlock.width = Float.valueOf(ary[3]);
                colorBlock.height = Float.valueOf(ary[4]);
                colorBlock.thick = Float.valueOf(ary[5]);
                colorBlock.rf = Float.valueOf(ary[6]);
                colorBlock.gf = Float.valueOf(ary[7]);
                colorBlock.bf = Float.valueOf(ary[8]);
                if (ary.length >= 10) {
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
     *
     * @param name
     */
    public void saveSelectAsColorGroup(int id, String name, int firstType,int secondType, String script, boolean isPenetrate, boolean isLight,HashMap param,String icon) {

        //这里还要增加imageblock colorblock 的支持 然后原生的支持

        BaseBlock selectBlock = this.getSelectFirstBlock();
        if(firstType==1) {
            selectBlock.live = true;
        }
        if (selectBlock == null) {
            return;
        }

        if (isLight && selectBlock instanceof ColorBlock) {
            ((ColorBlock) selectBlock).isLight = true;
        }
        //获取现在设定的id
        StringBuffer sb = new StringBuffer();
        selectBlock.setName(name);
        //  TextureManager.putShape(selectBlock);

        ItemFactory itemFactory = new ItemFactory();
        selectBlock.penetration = isPenetrate;

        sb.append("{id:").append(id).append(",")
                .append("name:'").append(name).append("',")

                .append("icon:'").append(icon).append("',")
                .append("remark:'").append(name).append("',")
                .append("script:'").append(script.replaceAll("\"", "\\\\\"").replaceAll("'", "\\\\\'").replaceAll("\r\n", "")).append("',");
        if(  firstType==1){
            sb  .append("live:").append("true,");
            sb .append("type:'").append("block").append("',");
            sb  .append("baseon:'mantle',");
        }else

            sb.append("stack:'").append(MapUtil.getIntValue(param,"stack",1)).append("',");

        if(  firstType==2){//是装备

            sb  .append("type:'").append("wear").append("',");
            sb  .append("spirit:").append(MapUtil.getIntValue(param,"spirit",0)).append(",");
            sb  .append("agile:").append(MapUtil.getIntValue(param,"agile",0)).append(",");
            sb  .append("intelli:").append(MapUtil.getIntValue(param,"intelli",0)).append(",");
            sb  .append("strenth:").append(MapUtil.getIntValue(param,"strenth",0)).append(",");
            sb  .append("tili:").append(MapUtil.getIntValue(param,"tili",0)).append(",");
            if(secondType==Constants.WEAR_POSI_HEAD){
                sb  .append("position:'").append("head").append("',");
                sb  .append("baseon:'fur_helmet',");
            }else if(secondType==Constants.WEAR_POSI_BODY){
                sb  .append("position:'").append("body").append("',");
                sb  .append("baseon:'fur_armor',");
            }else if(secondType==Constants.WEAR_POSI_LEG){
                sb  .append("position:'").append("leg").append("',");
                sb  .append("baseon:'fur_pants',");
            }else if(secondType==Constants.WEAR_POSI_HAND){
                sb  .append("position:'").append("hand").append("',");
                sb  .append("baseon:'wood_sword',");

                //如果是远程武器的画//如果是武器的画
                boolean isFar = MapUtil.getBooleanValue(param,"isFar",false);
                sb  .append("isFar:"+isFar+",");
                String ballId= MapUtil.getStringValue(param,"shootBallId");
                sb  .append("shootBallId:"+ballId+",");
            }
        }else{
            sb .append("type:'").append("block").append("',");
            sb  .append("baseon:'mantle',");
        }

        sb.append("remark:'").append(name).append("',")
                .append("shape:").append(selectBlock.toString()).append(",")
                .append("}");


        try {

            //保存之后存入到内存当中
            LogUtil.println(sb.toString());
            ItemDefinition itemDef = itemFactory.parse(JSON.parseObject(sb.toString(), HashMap.class));

            ItemManager.putItemDefinition(itemDef.getName(), itemDef);

            FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("config/item/newItem").resolve(id + "_" + name + ".block").toFile(), sb.toString());
            colorGroupHashMap.put(name, selectBlock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animationFrameShowNum(int num) {
        ((AnimationBlock) currentChoosedGroupForEdit).showAnimationFrame(num);
        needUpdate=true;
        lineNeedUpdate=true;
    }

    public boolean playAnimation() {
        if (currentChoosedGroupForEdit != null) {
            return ((AnimationBlock) currentChoosedGroupForEdit).play();
        }
        return true;
    }

    public float adjustRotatex(float value) {
        float rotateX = 0;
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock colorBlock = selectBlockList.get(i);
            if (colorBlock instanceof RotateColorBlock2) {
                rotateX = ((RotateColorBlock2) colorBlock).rotateX(value);
            }
            if (colorBlock instanceof RotateImageBlock) {
                rotateX = ((RotateImageBlock) colorBlock).rotateX(value);
            }
            if (colorBlock instanceof AnimationBlock) {
                rotateX = ((AnimationBlock) colorBlock).rotateX(value);
            }
            if (colorBlock instanceof GroupBlock) {
                rotateX = ((GroupBlock) colorBlock).rotateX(value);
            }
        }
        return rotateX;
    }

    public float adjustRotateY(float value) {
        float rotate = 0;
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock colorBlock = selectBlockList.get(i);
            //if(colorBlock instanceof RotateColorBlock2){
            // ((RotateColorBlock2) colorBlock) .rotateY(value);
            rotate = colorBlock.rotateY(value);
            // }
        }
        return rotate;
    }

    public float adjustRotateZ(float value) {
        float rotate = 0;
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock colorBlock = selectBlockList.get(i);
            if (colorBlock instanceof RotateColorBlock2) {
                rotate = ((RotateColorBlock2) colorBlock).rotateZ(value);
            }
            if (colorBlock instanceof RotateImageBlock) {
                rotate = ((RotateImageBlock) colorBlock).rotateZ(value);
            }
            if (colorBlock instanceof AnimationBlock) {
                rotate = ((AnimationBlock) colorBlock).rotateZ(value);
            }
        }
        return rotate;
    }

    public BaseBlock readyShootBlock = new ColorBlock();

    public void saveToGame() {


        int preBlockIndex = -1;
        int chunkIndex = -1;
        Integer preChunkIndex = null;  //if preChunkIndex != chunkIndex bianlichunk will to find it's new chunk and also use to find the place block
        int blockIndex = 0;
        Chunk bianliChunk = null;//need to be initialize
        int chunkX, preChunkX, chunkZ, preChunkZ;
        //int chunkY;
        float nowX, nowY, nowZ, preX, preY, preZ;
        nowX = nowY = nowZ = preX = preY = preZ = 0;
        int worldX, worldY, worldZ, preWorldX, preWorldY, preWorldZ;
        int offsetX, offsetZ, preOffsetX, preOffsetZ;
        ChunkProvider localChunkProvider = CoreRegistry.get(ChunkProvider.class);
        Client client = CoreRegistry.get(Client.class);
        for (int i = colorBlockList.size() - 1; i >= 0; i--) {
            BaseBlock colorBlock = colorBlockList.get(i);
            int id = colorBlock.id;

            boolean live = colorBlock.live;
            worldX = colorBlock.getX();
            worldY = colorBlock.getY();
            worldZ = colorBlock.getZ();

            if (live) {
                SayCmd sayCmd = new SayCmd(0, "", "/create monster " + worldX + " " + worldY + " " + worldZ + " " + colorBlock.getId());
                client.send(sayCmd);
                continue;


            }

            offsetX = MathUtil.getOffesetChunk(worldX);
            offsetZ = MathUtil.getOffesetChunk(worldZ);
            blockIndex = offsetX * 10000 + offsetZ * 100 + worldY;//推进后落入的blockINdex
            if (preBlockIndex == -1) {
                preBlockIndex = blockIndex;
            }
            chunkX = MathUtil.getBelongChunkInt(worldX);//换算出新的chunkX
            chunkZ = MathUtil.getBelongChunkInt(worldZ);//换算出新的chunkZ
            chunkIndex = chunkX * 10000 + chunkZ;
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
            cmd.dir = colorBlock.dir;
            cmd.blockType = id;
            client.send(cmd);//一个个发比较效率低

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


    public void saveToGame2() {


        int preBlockIndex = -1;
        int chunkIndex = -1;
        Integer preChunkIndex = null;  //if preChunkIndex != chunkIndex bianlichunk will to find it's new chunk and also use to find the place block
        int blockIndex = 0;
        Chunk bianliChunk = null;//need to be initialize
        int chunkX, preChunkX, chunkZ, preChunkZ;
        //int chunkY;
        float nowX, nowY, nowZ, preX, preY, preZ;
        nowX = nowY = nowZ = preX = preY = preZ = 0;
        int worldX, worldY, worldZ, preWorldX, preWorldY, preWorldZ;
        int offsetX, offsetZ, preOffsetX, preOffsetZ;
        ChunkProvider localChunkProvider = CoreRegistry.get(ChunkProvider.class);

        for (int i = colorBlockList.size() - 1; i >= 0; i--) {
            BaseBlock colorBlock = colorBlockList.get(i);
            int id = colorBlock.id;

            boolean live = colorBlock.live;
            worldX = colorBlock.getX();
            worldY = colorBlock.getY();
            worldZ = colorBlock.getZ();


            offsetX = MathUtil.getOffesetChunk(worldX);
            offsetZ = MathUtil.getOffesetChunk(worldZ);
            blockIndex = offsetX * 10000 + offsetZ * 100 + worldY;//推进后落入的blockINdex
            if (preBlockIndex == -1) {
                preBlockIndex = blockIndex;
            }
            chunkX = MathUtil.getBelongChunkInt(worldX);//换算出新的chunkX
            chunkZ = MathUtil.getBelongChunkInt(worldZ);//换算出新的chunkZ
            chunkIndex = chunkX * 10000 + chunkZ;
            if (preChunkIndex == null) {
                bianliChunk = localChunkProvider.getChunk(new Vector3i(chunkX, 0, chunkZ));//因为拉远距离了之后 会导致相机的位置其实是在很远的位置 改为只其实还没有chunk加载 所以最好是从任务的头顶开始出发
                preChunkIndex = chunkIndex;
            }

            if (chunkIndex != preChunkIndex) {//如果进入到新的chunk方格子空间
                bianliChunk = localChunkProvider.getChunk(new Vector3i(chunkX, 0, chunkZ));//因为拉远距离了之后 会导致相机的位置其实是在很远的位置 改为只其实还没有chunk加载 所以最好是从任务的头顶开始出发

            }


            bianliChunk.setBlock(offsetX, worldY, offsetZ, colorBlock.id);
            bianliChunk.setNeedUpdate(true);

            colorBlockList.remove(i);


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

        ChunkProvider localChunkProvider = CoreRegistry.get(ChunkProvider.class);


        int chunkX = MathUtil.getBelongChunkInt(GamingState.instance.player.getX());//换算出新的chunkX
        int chunkZ = MathUtil.getBelongChunkInt(GamingState.instance.player.getZ());//换算出新的chunkZ

        Chunk chunk = localChunkProvider.getChunk(chunkX, 0, chunkZ);

        TeraArray data = chunk.getBlockData();
        List<Integer> arr = new ArrayList<>();
        for (int x = 0; x < ChunkConstants.SIZE_X; x++) {

            for (int y = 0; y < ChunkConstants.SIZE_Y; y++) {

                for (int z = 0; z < ChunkConstants.SIZE_Z; z++) {
                    int value = chunk.getBlockData(x, y, z);
                    //x<<12 && y<<8&& z <<4 && value
                    if (value > 0) {
                        if (value != 40) {
                            BaseBlock block = ItemManager.getItemDefinition(value).getShape().copy();
                            block.x = chunkX * 16 + x;
                            block.y = y;
                            block.z = chunkZ * 16 + z;
                            this.addColorBlock(block);
                        }

                        chunk.setBlock(x, y, z, 0);

                    }

                }
            }
        }

        chunk.setNeedUpdate(true);
    }

    public void synChunkFromServer() {

        ChunkProvider localChunkProvider = CoreRegistry.get(ChunkProvider.class);


        int chunkX = MathUtil.getBelongChunkInt(GamingState.instance.player.getX());//换算出新的chunkX
        int chunkZ = MathUtil.getBelongChunkInt(GamingState.instance.player.getZ());//换算出新的chunkZ


        Chunk chunk = localChunkProvider.getChunk(chunkX, 0, chunkZ);

        localChunkProvider.reload(chunk);

    }

    public void synChunkFromClient() {
        ChunkProvider localChunkProvider = CoreRegistry.get(ChunkProvider.class);


        int chunkX = MathUtil.getBelongChunkInt(GamingState.instance.player.getX());//换算出新的chunkX
        int chunkZ = MathUtil.getBelongChunkInt(GamingState.instance.player.getZ());//换算出新的chunkZ


        Chunk chunk = localChunkProvider.getChunk(chunkX, 0, chunkZ);

        ChunkResponseCmd cmd = new ChunkResponseCmd(chunk);
        CoreRegistry.get(Client.class).send(cmd);
    }

    public void synChunkFromEditToClient() {
        ChunkProvider localChunkProvider = CoreRegistry.get(ChunkProvider.class);


        int chunkX = MathUtil.getBelongChunkInt(GamingState.instance.player.getX());//换算出新的chunkX
        int chunkZ = MathUtil.getBelongChunkInt(GamingState.instance.player.getZ());//换算出新的chunkZ


        Chunk chunk = localChunkProvider.getChunk(chunkX, 0, chunkZ);

        ChunkResponseCmd cmd = new ChunkResponseCmd(chunk);
        CoreRegistry.get(Client.class).send(cmd);
    }

    public void setCenter(float x, float y, float z) {
        for (BaseBlock block : selectBlockList) {
            if (block instanceof RotateBlock) {
                RotateBlock rotateBlock = (RotateBlock) block;
                rotateBlock.setCenterX(x);
                rotateBlock.setCenterY(y);
                rotateBlock.setCenterZ(z);

            } else {
                if (block instanceof AnimationBlock) {
                    AnimationBlock group = (AnimationBlock) block;
                    group.setCenter(x, y, z);
                }
            }
        }
    }


    public void addAnimationMap(String name) {
        // TODO Auto-generated method stub

        BaseBlock block = this.getSelectFirstBlock();
        if (block instanceof AnimationBlock) {
            AnimationBlock group = (AnimationBlock) block;
            List<GroupBlock> copyAnimationList = new ArrayList<>();

            for (GroupBlock animationBlock : group.animations) {
                copyAnimationList.add(animationBlock.copy());
            }
            group.animationMap.put(name, copyAnimationList);
        }

    }


    public void animationNameShow(String selectedItem) {
        BaseBlock block = this.getSelectFirstBlock();
        if (block instanceof AnimationBlock) {
            AnimationBlock group = (AnimationBlock) block;
            group.animations.clear();//不合适吧
            List<GroupBlock> copyAnimationList = group.animationMap.get(selectedItem);

            for (GroupBlock animationBlock : copyAnimationList) {
                group.animations.add(animationBlock.copy());


            }

        }

    }


    public List<String> getAnimationName() {
        List nameList = new ArrayList<String>();
        BaseBlock block = this.getSelectFirstBlock();
        if (block instanceof AnimationBlock) {
            AnimationBlock group = (AnimationBlock) block;
            Map<String, List<GroupBlock>> map = group.animationMap;


            for (Map.Entry<String, List<GroupBlock>> entry : map.entrySet()) {

                nameList.add(entry.getKey());

            }

        }
        return nameList;
    }

    GL_Vector direction = null;

    public void reComputeAppend() {
        appendingBlockList.clear();
        int minx = (int) (Math.min(placeStartPoint.x, placeEndPoint.x));
        int maxx = (int) (Math.max(placeStartPoint.x, placeEndPoint.x));

        int miny = (int) (Math.min(placeStartPoint.y, placeEndPoint.y));
        int maxy = (int) (Math.max(placeStartPoint.y, placeEndPoint.y));

        int minz = (int) (Math.min(placeStartPoint.z, placeEndPoint.z));
        int maxz = (int) (Math.max(placeStartPoint.z, placeEndPoint.z));
        if (Switcher.shapeMode == Switcher.shapeBlockMode) {
            for (int x = minx; x <= maxx; x++) {
                for (int y = miny; y <= maxy; y++) {
                    for (int z = minz; z <= maxz; z++) {
                        BaseBlock colorBlock = this.readyShootBlock.copy();
                        this.set(colorBlock, x, y, z, 1, 1, 1, red, green, blue, alpha);
                        //colorBlock.reComputePoints();
                        //LogUtil.println("widht:"+minx+":"+maxx);
                        this.appendingBlockList.add(colorBlock);
                    }

                }

            }
        } else if (Switcher.shapeMode == Switcher.shapeLineMode) {

            minx = (int) placeStartPoint.x;
            maxx = (int) placeEndPoint.x;

            miny = (int) placeStartPoint.y;
            maxy = (int) placeEndPoint.y;


            minz = (int) placeStartPoint.z;
            maxz = (int) placeEndPoint.z;
            if (maxx - minx == 0 && maxy - miny == 0 && maxz - minz == 0) {
                ColorBlock colorBlock = new ColorBlock(minx, miny, minz, 1, 1, 1, red, green, blue, alpha);

                this.appendingBlockList.add(colorBlock);
            } else {
                int maxv = 0, minv = 0;
                if (Math.abs(maxx - minx) >= Math.abs(maxy - miny) && Math.abs(maxx - minx) >= Math.abs(maxz - minz)) {
                    maxv = maxx;
                    minv = minx;

                } else if (Math.abs(maxy - miny) >= Math.abs(maxx - minx) && Math.abs(maxy - miny) >= Math.abs(maxz - minz)) {
                    maxv = maxy;
                    minv = miny;
                } else if (Math.abs(maxz - minz) >= Math.abs(maxx - minx) && Math.abs(maxz - minz) >= Math.abs(maxy - miny)) {

                    maxv = maxz;
                    minv = minz;

                }

                int _minv = Math.min(minv, maxv);
                int _maxv = Math.max(minv, maxv);
//                LogUtil.println("_minv:"+minv+"_maxv:"+maxv);
                for (int v = _minv; v <= _maxv; v++) {
                    if (maxv - minv == 0) {
                        LogUtil.println("hello");
                    }

                    int x = (int) ((v - _minv) * 1f / (_maxv - _minv) * (maxx - minx) + minx);
                    int y = (int) ((v - _minv) * 1f / (_maxv - _minv) * (maxy - miny) + miny);
                    int z = (int) ((v - _minv) * 1f / (_maxv - _minv) * (maxz - minz) + minz);
                    ColorBlock colorBlock = new ColorBlock(x, y, z, 1, 1, 1, red, green, blue, alpha);

                    this.appendingBlockList.add(colorBlock);
                }
            }
        } else if (Switcher.shapeMode == Switcher.shapeCircleMode) {
            //获取当前是哪个面 上下 就取xz 然后对比距离
            int startX = (int) placeStartPoint.x;
            int startY = (int) placeStartPoint.y;
            int startZ = (int) placeStartPoint.z;

            int endX = (int) placeEndPoint.x;
            int endY = (int) placeEndPoint.y;
            int endZ = (int) placeEndPoint.z;
            GL_Vector from = GamingState.instance.camera.Position;
            if (lastFace == Constants.TOP || lastFace == Constants.BOTTOM) {
                //获取当前射线


                float weizhi = (startY - from.y) / direction.y;
                endX = (int) (from.x + weizhi * direction.x);
                endZ = (int) (from.z + weizhi * direction.z);
                endY = startY;

                float lengthSqr = (startZ - endZ) * (startZ - endZ) +
                        (startX - endX) * (startX - endX);

                if (lengthSqr == 0) {
                    return;
                }
                float length = (float) Math.sqrt(lengthSqr);
                if (length > 30) return;
                for (int x = 0; x < length; x++) {
                    for (int z = 0; z < length; z++) {
                        if (x == 0 && z == 0) continue;
                        int nowLength = x * x + z * z;
                        if (nowLength < lengthSqr) {
                            BaseBlock colorBlock = getNewBlock(startX + x, startY, startZ + z);//,1,1,1,red,green,blue,alpha);
                            BaseBlock colorBlock2 = getNewBlock(startX + x, startY, startZ - z);//,1,1,1,red,green,blue,alpha);

                            BaseBlock colorBlock3 = getNewBlock(startX - x, startY, startZ - z);//,1,1,1,red,green,blue,alpha);

                            BaseBlock colorBlock4 = getNewBlock(startX - x, startY, startZ + z);//,1,1,1,red,green,blue,alpha);

                            this.appendingBlockList.add(colorBlock);
                            this.appendingBlockList.add(colorBlock2);
                            this.appendingBlockList.add(colorBlock3);
                            this.appendingBlockList.add(colorBlock4);
                        }
                    }
                }
            } else if (lastFace == Constants.LEFT || lastFace == Constants.RIGHT) {
                //y z的差别


                float weizhi = (startX - from.x) / direction.x;
                endX = startX;
                endY = (int) (from.y + weizhi * direction.y);

                endZ = (int) (from.z + weizhi * direction.z);


                float lengthSqr = (startZ - endZ) * (startZ - endZ) +
                        (startY - endY) * (startY - endY);

                if (lengthSqr == 0) {
                    return;
                }
                float length = (float) Math.sqrt(lengthSqr);
                if (length > 30) return;
                for (int y = 0; y < length; y++) {
                    for (int z = 0; z < length; z++) {
                        if (y == 0 && z == 0) continue;
                        int nowLength = y * y + z * z;
                        if (nowLength < lengthSqr) {

                            BaseBlock colorBlock = getNewBlock(startX, startY + y, startZ + z);//1,1,1,red,green,blue,alpha);
                            BaseBlock colorBlock2 = getNewBlock(startX, startY + y, startZ - z);//,1,1,1,red,green,blue,alpha);

                            BaseBlock colorBlock3 = getNewBlock(startX, startY - y, startZ - z);//,1,1,1,red,green,blue,alpha);

                            BaseBlock colorBlock4 = getNewBlock(startX, startY - y, startZ + z);//,1,1,1,red,green,blue,alpha);

                            this.appendingBlockList.add(colorBlock);
                            this.appendingBlockList.add(colorBlock2);
                            this.appendingBlockList.add(colorBlock3);
                            this.appendingBlockList.add(colorBlock4);
                        }
                    }
                }


            } else if (lastFace == Constants.FRONT || lastFace == Constants.BACK) {
                //y z的差别

                float weizhi = (startZ - from.z) / direction.z;
                endX = (int) (from.x + weizhi * direction.x);
                endY = (int) (from.y + weizhi * direction.y);
                endZ = startZ;


                float lengthSqr = (startX - endX) * (startX - endX) +
                        (startY - endY) * (startY - endY);

                if (lengthSqr == 0) {
                    return;
                }
                float length = (float) Math.sqrt(lengthSqr);
                if (length > 30)
                    return;
                for (int x = 0; x < length; x++) {
                    for (int y = 0; y < length; y++) {
                        if (y == 0 && x == 0) continue;
                        int nowLength = y * y + x * x;
                        if (nowLength < lengthSqr) {
                            BaseBlock colorBlock = getNewBlock(startX + x, startY + y, startZ);//, 1, 1, 1, red, green, blue, alpha);
                            BaseBlock colorBlock2 = getNewBlock(startX - x, startY + y, startZ);//, 1, 1, 1, red, green, blue, alpha);

                            BaseBlock colorBlock3 = getNewBlock(startX + x, startY - y, startZ);//, 1, 1, 1, red, green, blue, alpha);

                            BaseBlock colorBlock4 = getNewBlock(startX - x, startY - y, startZ);//, 1, 1, 1, red, green, blue, alpha);

                            this.appendingBlockList.add(colorBlock);
                            this.appendingBlockList.add(colorBlock2);
                            this.appendingBlockList.add(colorBlock3);
                            this.appendingBlockList.add(colorBlock4);
                        }
                    }
                }

                //把所有的东西都同步到制定的blokAry里


            }


        }
        if (maxx - minx > 100) {
            LogUtil.println("is not block ");
        }


    }

    public BaseBlock getNewBlock(int x, int y, int z) {
        BaseBlock colorBlock = this.readyShootBlock.copy();
        this.set(colorBlock, x, y, z, 1, 1, 1, red, green, blue, alpha);
        return colorBlock;
    }


    public void reComputeSelect() {//把原来的删除? 把现在替换上?
        selectBlockList.clear();
        int minx = (int) (Math.min(touchStartPoint.x, touchEndPoint.x));
        int maxx = (int) (Math.max(touchStartPoint.x, touchEndPoint.x));

        int miny = (int) (Math.min(touchStartPoint.y, touchEndPoint.y));
        int maxy = (int) (Math.max(touchStartPoint.y, touchEndPoint.y));

        int minz = (int) (Math.min(touchStartPoint.z, touchEndPoint.z));
        int maxz = (int) (Math.max(touchStartPoint.z, touchEndPoint.z));
        if (Switcher.shapeMode == Switcher.shapeBlockMode) {
            for (int x = minx; x <= maxx; x++) {
                for (int y = miny; y <= maxy; y++) {
                    for (int z = minz; z <= maxz; z++) {
                        if (x >= 0 && x <= 140 && y >= 0 && y <= 140 && z >= 0 && z <= 140)
                            if (blockAry[this.getIndex(x, y, z)] != null) {
                                this.selectBlockList.add(blockAry[this.getIndex(x, y, z)]);
                            }
                        //colorBlock.reComputePoints();
                        //LogUtil.println("widht:"+minx+":"+maxx);

                    }

                }

            }
        } else if (Switcher.shapeMode == Switcher.shapeLineMode) {

            minx = (int) touchStartPoint.x;
            maxx = (int) touchEndPoint.x;

            miny = (int) touchStartPoint.y;
            maxy = (int) touchEndPoint.y;


            minz = (int) touchStartPoint.z;
            maxz = (int) touchEndPoint.z;
            if (maxx - minx == 0 && maxy - miny == 0 && maxz - minz == 0) {
                // ColorBlock colorBlock = new ColorBlock(minx,miny,minz,1,1,1,red,green,blue,alpha);
                if (blockAry[this.getIndex(minX, miny, minz)] != null) {
                    this.selectBlockList.add(blockAry[this.getIndex(minX, miny, minz)]);
                }
                // this.appendingBlockList.add(colorBlock);
            } else {
                int maxv = 0, minv = 0;
                if (Math.abs(maxx - minx) >= Math.abs(maxy - miny) && Math.abs(maxx - minx) >= Math.abs(maxz - minz)) {
                    maxv = maxx;
                    minv = minx;

                } else if (Math.abs(maxy - miny) >= Math.abs(maxx - minx) && Math.abs(maxy - miny) >= Math.abs(maxz - minz)) {
                    maxv = maxy;
                    minv = miny;
                } else if (Math.abs(maxz - minz) >= Math.abs(maxx - minx) && Math.abs(maxz - minz) >= Math.abs(maxy - miny)) {

                    maxv = maxz;
                    minv = minz;

                }

                int _minv = Math.min(minv, maxv);
                int _maxv = Math.max(minv, maxv);
                // LogUtil.println("_minv:"+minv+"_maxv:"+maxv);
                for (int v = _minv; v <= _maxv; v++) {
                    if (maxv - minv == 0) {
                        LogUtil.println("hello");
                    }

                    int x = (int) ((v - _minv) * 1f / (_maxv - _minv) * (maxx - minx) + minx);
                    int y = (int) ((v - _minv) * 1f / (_maxv - _minv) * (maxy - miny) + miny);
                    int z = (int) ((v - _minv) * 1f / (_maxv - _minv) * (maxz - minz) + minz);

                    if (blockAry[this.getIndex(x, y, z)] != null) {
                        this.selectBlockList.add(blockAry[this.getIndex(x, y, z)]);
                    }
                    // ColorBlock colorBlock = new ColorBlock(x, y, z, 1, 1, 1, red, green, blue, alpha);

                    // this.appendingBlockList.add(colorBlock);
                }
            }
        } else if (Switcher.shapeMode == Switcher.shapeCircleMode) {
            //获取当前是哪个面 上下 就取xz 然后对比距离
            int startX = (int) touchStartPoint.x;
            int startY = (int) touchStartPoint.y;
            int startZ = (int) touchStartPoint.z;

            int endX = (int) placeEndPoint.x;
            int endY = (int) placeEndPoint.y;
            int endZ = (int) placeEndPoint.z;
            GL_Vector from = GamingState.instance.camera.Position;
            if (lastFace == Constants.TOP || lastFace == Constants.BOTTOM) {
                //获取当前射线


                float weizhi = (startY - from.y) / direction.y;
                endX = (int) (from.x + weizhi * direction.x);
                endZ = (int) (from.z + weizhi * direction.z);
                endY = startY;

                float lengthSqr = (startZ - endZ) * (startZ - endZ) +
                        (startX - endX) * (startX - endX);

                if (lengthSqr == 0) {
                    return;
                }
                float length = (float) Math.sqrt(lengthSqr);
                for (int x = -(int) length; x < length; x++) {
                    for (int z = -(int) length; z < length; z++) {
                        if (x == 0 && z == 0) continue;
                        int nowLength = x * x + z * z;
                        if (nowLength < lengthSqr) {


                            if (blockAry[getIndex(startX + x, startY, startZ + z)] != null) {

                                this.selectBlockList.add(blockAry[getIndex(startX + x, startY, startZ + z)]);
                            }
                           /* if(z!=0)
                            if(startZ-z>=0 && blockAry[getIndex(startX+x,startY,startZ-z)]!=null){
                                this.selectBlockList.add(blockAry[getIndex(startX+x,startY,startZ-z)]);
                            }
                            if(z!=0&&x!=0)
                            if(startX-x>=0 &&startZ-z>=0 && blockAry[getIndex(startX-x,startY,startZ-z)]!=null){
                                this.selectBlockList.add(blockAry[getIndex(startX-x,startY,startZ-z)]);
                            }
                            if(z!=0&&x!=0)
                            if(startX-x>=0 && blockAry[getIndex(startX-x,startY,startZ+z)]!=null){

                                this.selectBlockList.add(blockAry[getIndex(startX-x,startY,startZ+z)]);
                            }*/


                        }
                    }
                }
            } else if (lastFace == Constants.LEFT || lastFace == Constants.RIGHT) {
                //y z的差别


                float weizhi = (startX - from.x) / direction.x;
                endX = startX;
                endY = (int) (from.y + weizhi * direction.y);

                endZ = (int) (from.z + weizhi * direction.z);


                float lengthSqr = (startZ - endZ) * (startZ - endZ) +
                        (startY - endY) * (startY - endY);

                if (lengthSqr == 0) {
                    return;
                }
                float length = (float) Math.sqrt(lengthSqr);
                for (int y = -(int) length; y < length; y++) {
                    for (int z = -(int) length; z < length; z++) {
                        if (y == 0 && z == 0) continue;
                        int nowLength = y * y + z * z;
                        if (nowLength < lengthSqr) {


                            if (blockAry[getIndex(startX, startY + y, startZ + z)] != null) {
                                this.selectBlockList.add(blockAry[getIndex(startX, startY + y, startZ + z)]);
                            }
                         /*   if(startZ-z>=0 && blockAry[getIndex(startX,startY+y,startZ-z)]!=null){
                                this.selectBlockList.add(blockAry[getIndex(startX,startY+y,startZ-z)]);
                            }
                            if(y!=0 && z!=0)
                            if(startY-y>=0&&startZ-z>=0&& blockAry[getIndex(startX,startY-y,startZ-z)]!=null){
                                this.selectBlockList.add(blockAry[getIndex(startX,startY-y,startZ-z)]);
                            }
                            if(y!=0 && z!=0)
                            if(startY-y>=0 &&blockAry[getIndex(startX,startY-y,startZ+z)]!=null){
                                this.selectBlockList.add(blockAry[getIndex(startX,startY-y,startZ+z)]);
                            }*/


                        }
                    }
                }


            } else if (lastFace == Constants.FRONT || lastFace == Constants.BACK) {
                //y z的差别

                float weizhi = (startZ - from.z) / direction.z;
                endX = (int) (from.x + weizhi * direction.x);
                endY = (int) (from.y + weizhi * direction.y);
                endZ = startZ;


                float lengthSqr = (startX - endX) * (startX - endX) +
                        (startY - endY) * (startY - endY);

                if (lengthSqr == 0) {
                    return;
                }
                float length = (float) Math.sqrt(lengthSqr);
                for (int x = -(int) length; x < length; x++) {
                    for (int y = -(int) length; y < length; y++) {
                        if (y == 0 && x == 0) continue;
                        int nowLength = y * y + x * x;
                        if (nowLength < lengthSqr) {


                            if (blockAry[getIndex(startX + x, startY + y, startZ)] != null) {
                                this.selectBlockList.add(blockAry[getIndex(startX + x, startY + y, startZ)]);
                            }
                         /*   if(startX-x>=0 && blockAry[getIndex(startX-x, startY + y, startZ)]!=null){
                                this.selectBlockList.add(blockAry[getIndex(startX-x, startY + y, startZ)]);
                            }
                            if(y!=0 && x!=0)
                            if(startY - y>=0 && blockAry[getIndex(startX+x, startY - y, startZ)]!=null){
                                this.selectBlockList.add(blockAry[getIndex(startX+x, startY - y, startZ)]);
                            }
                            if(y!=0 && x!=0)
                            if(startX-x>=0 &&startY - y>=0&& blockAry[getIndex(startX-x, startY - y, startZ)]!=null){
                                this.selectBlockList.add(blockAry[getIndex(startX-x, startY - y, startZ)]);
                            }*/
                        }
                    }
                }

                //把所有的东西都同步到制定的blokAry里


            }


        }
        if (maxx - minx > 100) {
            LogUtil.println("is not block ");
        }


    }

    public int getIndex(int x, int y, int z) {
        if (in(x, 0, 140) && in(y, 0, 140) && in(z, 0, 140)) {
            return y * 40 * 40 + x + 40 * z;
        } else {
            return 0;
        }


    }

    public void readFromChunk() {//这个相当于在另外一个线程里操作了 需要做同步锁
        synchronized (colorBlockList) {
            for (int i = 0, length = blockAry.length; i < length; i++) {
                blockAry[i] = null;
            }
            for (BaseBlock block : colorBlockList) {
                this.addToAry(block);
                // blockAry[getIndex((int) block.x, (int) block.y, (int) block.z)] = block;
            }
            colorBlockList.clear();
            for (int i = 0, length = blockAry.length; i < length; i++) {
                if (blockAry[i] != null) {
                    addColorBlock(blockAry[i]);
                }
            }
        }
    }

    public void addColorBlock(BaseBlock block) {
        if (block != null) {
            colorBlockList.add(block);
            //加载的时候也会调用 但是下面的语句会把颜色搞砸
            if (block.id==0 && block instanceof ColorBlock && ((ColorBlock) block).isLight) {
                lightBlockHashMap.put(new GL_Vector((int) block.x, (int) block.y, (int) block.z), new WeakReference<ColorBlock>((ColorBlock) block));

            }


            needUpdate = true;
        } else {
            LogUtil.println("block is null");
        }

    }

    public void addColorBlockAll(List<BaseBlock> list) {
        for (BaseBlock block : list) {
            addColorBlock(block);
            needUpdate = true;
        }
    }

    public void clearAll() {
        colorBlockList.clear();
        for (int i = 0; i < blockAry.length; i++) {
            blockAry[i] = null;
        }
        selectBlockList.clear();
        needUpdate = true;

    }

    /**
     *
     */
    public void pullSelect() {
        //判断是否有选取的block
        LogUtil.println("拉了一次");
        if (selectBlockList.size() == 0) {
            return;
        }


        //判断是选取的是哪一个面
        int face = nowFace;
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock block = selectBlockList.get(i);
            BaseBlock copy = block.copy();
            addColorBlock(copy);

            addToAry(copy);

            if (face == Constants.TOP) {
                block.y++;

            } else if (face == Constants.BOTTOM) {
                block.y--;
            } else if (face == Constants.BACK) {
                block.z--;
            } else if (face == Constants.FRONT) {
                block.z++;
            } else if (face == Constants.LEFT) {
                block.x--;
            } else if (face == Constants.RIGHT) {
                block.x++;
            }
            addToAry(block);

        }


    }

    public float jianTouSize = 0.3f;
    public float axisLen = 3f;

    /**
     *
     */
    public void pushSelect() {
        //判断是否有选取的block
        LogUtil.println("推一次");
        if (selectBlockList.size() == 0) {
            return;
        }


        //判断是选取的是哪一个面
        int face = nowFace;
        List<BaseBlock> list = new ArrayList<>();
        for (int i = 0; i < selectBlockList.size(); i++) {
            BaseBlock block = selectBlockList.get(i);
            int index = getIndex((int) block.x, (int) block.y, (int) block.z);
            blockAry[index] = null;


            colorBlockList.remove(block);
            if (face == Constants.TOP) {
                block.y--;
            } else if (face == Constants.BOTTOM) {
                block.y++;
            } else if (face == Constants.BACK) {
                block.z++;
            } else if (face == Constants.FRONT) {
                block.z--;
            } else if (face == Constants.LEFT) {
                block.x++;
            } else if (face == Constants.RIGHT) {
                block.x--;
            }
            index = getIndex((int) block.x, (int) block.y, (int) block.z);
            BaseBlock indexBlock = blockAry[index];
            if (indexBlock != null) {
                list.add(indexBlock);
            }

        }
        selectBlockList.clear();
        selectBlockList.addAll(list);

        needUpdate = true;
        lineNeedUpdate = true;
    }

    public boolean in(float nowvalue, float minValue, float maxValue) {
        if (nowvalue >= minValue && nowvalue <= maxValue) {
            return true;
        }
        return false;
    }

    public void addToAry(BaseBlock block) {
        if (in(block.x, 0, 140) && in(block.y, 0, 140) && in(block.z, 0, 140)) {
            blockAry[getIndex((int) block.x, (int) block.y, (int) block.z)] = block;
        }

    }

    public void fresh() {
        this.needUpdate = true;
        this.lineNeedUpdate = true;
        for (BaseBlock block : selectBlockList) {
            block.reComputePoints();
        }
    }

    public void renderZuobiao() {
        //x
        BaseBlock block = null;
        GL_Vector zuobiaoPoint =null;
        if(Switcher.isEditComponent){
            if(currentChoosedGroupForEdit!=null){
               if(currentChoosedGroupForEdit.selectBlockList.size()>0){

                    block = currentChoosedGroupForEdit.selectBlockList.get(0);
                   zuobiaoPoint = new GL_Vector(block.x, block.y, block.z);
                   zuobiaoPoint.x += currentChoosedGroupForEdit.x;
                   zuobiaoPoint.y += currentChoosedGroupForEdit.y;
                   zuobiaoPoint.z += currentChoosedGroupForEdit.z;

                }else{
                   return;
               }
            }
        }else

        if (selectBlockList.size() > 0) {
             block = selectBlockList.get(0);
            zuobiaoPoint = new GL_Vector(block.x, block.y, block.z);
        }else{
            return;
        }

            if (block instanceof RotateBlock) {
                zuobiaoPoint.x += ((RotateBlock) block).getCenterX();
                zuobiaoPoint.y += ((RotateBlock) block).getCenterY();
                zuobiaoPoint.z += ((RotateBlock) block).getCenterZ();
            }
            ShaderUtils.drawLine(ShaderManager.lineShaderConfig.getVao(),zuobiaoPoint, new GL_Vector(zuobiaoPoint.x + 3, zuobiaoPoint.y, zuobiaoPoint.z));
            ShaderUtils.drawLine(ShaderManager.lineShaderConfig.getVao(),zuobiaoPoint, new GL_Vector(zuobiaoPoint.x, zuobiaoPoint.y + 3, zuobiaoPoint.z));
            ShaderUtils.drawLine(ShaderManager.lineShaderConfig.getVao(),zuobiaoPoint, new GL_Vector(zuobiaoPoint.x, zuobiaoPoint.y, zuobiaoPoint.z + 3));

            xPoint.x = zuobiaoPoint.x + 3;
            xPoint.y = zuobiaoPoint.y;
            xPoint.z = zuobiaoPoint.z;

            yPoint.x = zuobiaoPoint.x;
            yPoint.y = zuobiaoPoint.y + 3;
            yPoint.z = zuobiaoPoint.z;


            zPoint.x = zuobiaoPoint.x;
            zPoint.y = zuobiaoPoint.y;
            zPoint.z = zuobiaoPoint.z + 3;
            //绘制 x 箭头 y箭头 z箭头

            //x 轴 红色可拖动方块
            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, blockVao,
                    xPoint.x, xPoint.y, xPoint.z, ShaderUtils.RED, 0.2f, 0.2f, 0.2f, 1
            );
            ShaderUtils.draw3dColorTriangle(ShaderManager.anotherShaderConfig, blockVao,
                    new GL_Vector(zuobiaoPoint.x + axisLen, zuobiaoPoint.y, zuobiaoPoint.z),
                    new GL_Vector(zuobiaoPoint.x + axisLen - jianTouSize, zuobiaoPoint.y + jianTouSize, zuobiaoPoint.z),
                    new GL_Vector(zuobiaoPoint.x + axisLen - jianTouSize, zuobiaoPoint.y - jianTouSize, zuobiaoPoint.z),
                    BoxModel.FRONT_DIR, ShaderUtils.RED);
            ShaderUtils.draw3dColorTriangle(ShaderManager.anotherShaderConfig, blockVao,
                    new GL_Vector(zuobiaoPoint.x + axisLen, zuobiaoPoint.y, zuobiaoPoint.z),

                    new GL_Vector(zuobiaoPoint.x + axisLen - jianTouSize, zuobiaoPoint.y - jianTouSize, zuobiaoPoint.z),
                    new GL_Vector(zuobiaoPoint.x + axisLen - jianTouSize, zuobiaoPoint.y + jianTouSize, zuobiaoPoint.z),
                    BoxModel.BACK_DIR, ShaderUtils.RED);
            //y 轴 绿色可拖动方块
            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, blockVao,
                    yPoint.x, yPoint.y, yPoint.z, ShaderUtils.GREEN, 0.2f, 0.2f, 0.2f, 1
            );


            ShaderUtils.draw3dColorTriangle(ShaderManager.anotherShaderConfig, blockVao,
                    new GL_Vector(zuobiaoPoint.x, zuobiaoPoint.y + axisLen, zuobiaoPoint.z),
                    new GL_Vector(zuobiaoPoint.x - 0.3f, zuobiaoPoint.y + axisLen - jianTouSize, zuobiaoPoint.z),
                    new GL_Vector(zuobiaoPoint.x + 0.3f, zuobiaoPoint.y + axisLen - jianTouSize, zuobiaoPoint.z),
                    BoxModel.FRONT_DIR, ShaderUtils.GREEN);
            ShaderUtils.draw3dColorTriangle(ShaderManager.anotherShaderConfig, blockVao,
                    new GL_Vector(zuobiaoPoint.x, zuobiaoPoint.y + axisLen, zuobiaoPoint.z),
                    new GL_Vector(zuobiaoPoint.x + 0.3f, zuobiaoPoint.y + axisLen - jianTouSize, zuobiaoPoint.z),
                    new GL_Vector(zuobiaoPoint.x - 0.3f, zuobiaoPoint.y + axisLen - jianTouSize, zuobiaoPoint.z),

                    BoxModel.BACK_DIR, ShaderUtils.GREEN);

            //z 轴 蓝色可拖动方块
            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, blockVao,
                    zPoint.x, zPoint.y, zPoint.z, ShaderUtils.BLUE, 0.2f, 0.2f, 0.2f, 1
            );


            ShaderUtils.draw3dColorTriangle(ShaderManager.anotherShaderConfig, blockVao,
                    new GL_Vector(zuobiaoPoint.x, zuobiaoPoint.y, zuobiaoPoint.z + axisLen),
                    new GL_Vector(zuobiaoPoint.x + jianTouSize, zuobiaoPoint.y, zuobiaoPoint.z + axisLen - jianTouSize),
                    new GL_Vector(zuobiaoPoint.x - jianTouSize, zuobiaoPoint.y, zuobiaoPoint.z + axisLen - jianTouSize),
                    BoxModel.TOP_DIR, ShaderUtils.BLUE);


            ShaderUtils.draw3dColorTriangle(ShaderManager.anotherShaderConfig, blockVao,
                    new GL_Vector(zuobiaoPoint.x, zuobiaoPoint.y, zuobiaoPoint.z + axisLen),

                    new GL_Vector(zuobiaoPoint.x - jianTouSize, zuobiaoPoint.y, zuobiaoPoint.z + axisLen - jianTouSize),
                    new GL_Vector(zuobiaoPoint.x + jianTouSize, zuobiaoPoint.y, zuobiaoPoint.z + axisLen - jianTouSize),
                    BoxModel.DOWN_DIR, ShaderUtils.BLUE);




    }

    RotateBlock nowRotateBlock = null;

    public void beginRotate() {
        if (selectBlockList.size() > 0) {


            BaseBlock block = selectBlockList.get(0);
            GL_Vector startPoint = new GL_Vector(block.x, block.y, block.z);

            if(Switcher.isEditComponent && currentChoosedGroupForEdit.selectBlockList.size()>0){
                nowMoveScaleRotateBlock = currentChoosedGroupForEdit.selectBlockList.get(0) ;
                if(nowMoveScaleRotateBlock instanceof RotateBlock){
                    nowRotateBlock = (RotateBlock) nowMoveScaleRotateBlock;
                }
            }else
            if (block instanceof RotateBlock) {

                Switcher.mouseState = Switcher.rotateMode;
                nowRotateBlock = (RotateBlock) block;
                nowMoveScaleRotateBlock=block;
            } else {

            }

        }
    }
//BaseBlock nowBlock
    public void beginMove() {
        if (selectBlockList.size() > 0) {


            BaseBlock block = selectBlockList.get(0);
            nowMoveScaleRotateBlock=block;
            if(Switcher.isEditComponent && currentChoosedGroupForEdit.selectBlockList.size()>0){
                nowMoveScaleRotateBlock = currentChoosedGroupForEdit.selectBlockList.get(0) ;

            }
            Switcher.mouseState = Switcher.moveMode;
        }
    }

    public void beginResize() {
        if (selectBlockList.size() > 0) {


            BaseBlock block = selectBlockList.get(0);
            nowMoveScaleRotateBlock=block;
            if(Switcher.isEditComponent && currentChoosedGroupForEdit.selectBlockList.size()>0){
                nowMoveScaleRotateBlock = currentChoosedGroupForEdit.selectBlockList.get(0) ;

            }
            Switcher.mouseState = Switcher.resizeMode;

        }
    }
    public void beginMoveAxis(){
        if (selectBlockList.size() > 0) {


            BaseBlock block = selectBlockList.get(0);

            nowMoveScaleRotateBlock=block;

            if(Switcher.isEditComponent && currentChoosedGroupForEdit.selectBlockList.size()>0){
                nowMoveScaleRotateBlock = currentChoosedGroupForEdit.selectBlockList.get(0) ;

            }
            Switcher.mouseState = Switcher.moveAxisMode;

        }
    }
    public float[] caculateWithZ(GL_Vector from ,GL_Vector viewDir,float z){



        //获取当前射线


        float weizhi = (z - from.z) / viewDir.z;
        float endX =  (from.x + weizhi * viewDir.x);
       // LogUtil.println("endX:"+endX);
     // LogUtil.println("direction:"+direction);
        float  endY =  (from.y + weizhi * viewDir.y);
        float  endZ = z;
        return new float[]{endX,endY,endZ};
    }

    public float[] caculateWithX(GL_Vector from ,GL_Vector viewDir,float x){

        //获取当前射线


        float weizhi = (x - from.x) / viewDir.x;
        float endZ =  (from.z + weizhi * viewDir.z);
        float  endY =  (from.y + weizhi * viewDir.y);
        float  endX = x;
        return new float[]{endX,endY,endZ};
    }

    public float[] caculateWithY(GL_Vector from ,GL_Vector viewDir,float y){


        //获取当前射线


        float weizhi = (y - from.y) / viewDir.y;
        float endX =  (from.x + weizhi * viewDir.x);
        float endZ =  (from.z + weizhi * viewDir.z);
        float  endY = y;
        return new float[]{endX,endY,endZ};
    }


    public void reset() {
        if(Switcher.isEditComponent){
            for(BaseBlock block : selectBlockList){
                block.points = BoxModel.getSmallPoint(block.x,block.y,block.z,
                        block.width,block.height,block.thick);

                if(block instanceof  GroupBlock){
                    for(BaseBlock childBlock : ((GroupBlock)block).selectBlockList){

//                        childBlock.points = BoxModel.getSmallPoint(childBlock.x,childBlock.y,childBlock.z,
//                                childBlock.width,childBlock.height,childBlock.thick);

                        if(childBlock instanceof  RotateBlock){
                            RotateBlock rotateBlock = (RotateBlock) childBlock;
                            rotateBlock.setCenterX(childBlock.width/2);;
                            rotateBlock.setCenterY(childBlock.height/2);
                            rotateBlock.setCenterZ(childBlock.thick/2);
                            rotateBlock.setRotateX(0);
                            rotateBlock.setRotateY(0);
                            rotateBlock.setRotateZ(0);

                        }
                    }
                }
            }
        }else{
            for(BaseBlock childBlock : selectBlockList){

                childBlock.points = BoxModel.getSmallPoint(childBlock.x,childBlock.y,childBlock.z,
                        childBlock.width,childBlock.height,childBlock.thick);

                if(childBlock instanceof  RotateBlock){
                    RotateBlock rotateBlock = (RotateBlock) childBlock;
                    rotateBlock.setCenterX(childBlock.width/2);;
                    rotateBlock.setCenterY(childBlock.height/2);
                    rotateBlock.setCenterZ(childBlock.thick/2);
                    rotateBlock.setRotateX(0);
                    rotateBlock.setRotateY(0);
                    rotateBlock.setRotateZ(0);

                }
            }
        }
    }

    public void generatorTriangle(){
        if(selectBlockList.size()==4){
            Triangle triangle =new Triangle();
            triangle.block1=(ColorBlock)selectBlockList.get(0);
            triangle.block2=(ColorBlock)selectBlockList.get(1);
            triangle.block3=(ColorBlock)selectBlockList.get(2);
            triangle.rf= red;
            triangle.gf=green;
            triangle.bf =blue;
            triangleList.add(triangle);
            needUpdate=true;
            Triangle triangle2 =new Triangle();
            triangle2.block1=(ColorBlock)selectBlockList.get(2);
            triangle2.block2=(ColorBlock)selectBlockList.get(3);
            triangle2.block3=(ColorBlock)selectBlockList.get(0);
            triangle2.rf= red;
            triangle2.gf=green;
            triangle2.bf =blue;
            triangleList.add(triangle2);

        }
        if(selectBlockList.size()==3){
            Triangle triangle =new Triangle();
            triangle.block1=(ColorBlock)selectBlockList.get(0);
            triangle.block2=(ColorBlock)selectBlockList.get(1);
            triangle.block3=(ColorBlock)selectBlockList.get(2);
            triangle.rf= red;
            triangle.gf=green;
            triangle.bf =blue;
            triangleList.add(triangle);
            needUpdate=true;
        }
    }
    /**
     * 事先选中 3个block 然后在里面进行 trianglelist 进行匹配 如果3个block都匹配上了
     */
    public Triangle getSelectedTriangle(){
        if(selectBlockList.size()>=3){
            for(int i=triangleList.size()-1;i>=0;i--){
                Triangle triangle =triangleList.get(i);
                if(triangle.hashBlock(selectBlockList.get(0),selectBlockList.get(1),selectBlockList.get(2))){
                    return  triangle;
                }

            }


        }
        return null;
    }
    /**
     * 事先选中 3个block 然后在里面进行 trianglelist 进行匹配 如果3个block都匹配上了就进行删除
     */
    public void deleteTriangle(){
        Triangle triangle = getSelectedTriangle();
        if(triangle != null) {
            triangleList.remove(triangle);
            needUpdate=true;
        }

    }

    public void setX(Float val) {

        for(BaseBlock block : selectBlockList){
            block.x=val;
        }
    }

    public void setY(Float val) {

        for(BaseBlock block : selectBlockList){
            block.y=val;
        }
    }

    public void setZ(Float val) {

        for(BaseBlock block : selectBlockList){
            block.z=val;
        }
    }

    public void setWidth(Float val) {

        for(BaseBlock block : selectBlockList){
            block.width=val;
        }
    }
    public void setHeight(Float val) {

        for(BaseBlock block : selectBlockList){
            block.height=val;
        }
    }
    public void setThick(Float val) {

        for(BaseBlock block : selectBlockList){
            block.thick=val;
        }
    }

    public void setName(String text) {
        getSelectFirstBlock().setName(text);
    }

    public void getAniationCfgFromAnimationBlock(){
        AnimationBlock animationBlock = (AnimationBlock)getSelectFirstBlock();
//        for(BaseBlock block:animationBlock.colorBlockList){
//
//        }
        //遍历不同的动画 key 是动画名称
        for(Map.Entry entry:animationBlock.animationMap.entrySet()){

            StringBuffer sb =new StringBuffer();
            HashMap<String,StringBuffer> animationStrMap= new HashMap<>();


            String animationName = (String)entry.getKey();//拿到了key之后 取出每一帧
            List<BaseBlock> animationGroupBlockList = (List<BaseBlock> )entry.getValue();//里面每一个list的项目都是帧 groupblock
            LogUtil.println("开始解析动画:%s",animationName);
            LogUtil.println("一共有:%d帧数",animationGroupBlockList.size());
            int frameCount = animationGroupBlockList.size();//总的帧数
            int nowFrame =0;//当前遍历到的帧数
            for(BaseBlock block:animationGroupBlockList){

                GroupBlock groupBlock = (GroupBlock)block;
                List<BaseBlock> colorBlockList = new ArrayList<>();
                colorBlockList.addAll(groupBlock.colorBlockList);
                if(groupBlock.colorBlockList.get(0) instanceof  BoneRotateImageBlock){
                    List<BaseBlock> boneList = new ArrayList<>();
                    BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock)groupBlock.colorBlockList.get(0);
                    fillBoneList(boneRotateImageBlock,boneList);
                    colorBlockList.addAll(boneList);
                }

                for(BaseBlock frameBlock: colorBlockList){

                    String blockName = frameBlock.getName();
                    if(StringUtil.isNotEmpty(blockName)){
                        StringBuffer animationStr = animationStrMap.get(blockName);
                        if(animationStr!=null){

                        }else{
                            animationStr=new StringBuffer(String.format("\n@keyframes %s-%s {\n",blockName,animationName));
                            animationStrMap.put(blockName,animationStr);
                        }
                        if(frameBlock instanceof  RotateBlock){
                            RotateBlock rotateBlock = (RotateBlock)frameBlock;
                            animationStr.append(String.format("%d%% { transform:rotateX(%ddeg),rotateY(%ddeg),rotateZ(%ddeg);} \n",
                                    (int)(nowFrame*100.0/(frameCount-1)),

                                    (int)(rotateBlock.getRotateX()/Constants.PI1),
                                    (int)(rotateBlock.getRotateY()/Constants.PI1),
                                    (int)(rotateBlock.getRotateZ()/Constants.PI1)));
                        }

//                    @keyframes handkan {
//                        0% { transform:rotateX(0deg),rotateY(0deg);}
//                        25% { transform:rotateX(-155deg),rotateY(-90deg); }
//                        50% { transform:rotateX(95deg),rotateY(0deg); }
//                        75% { transform:rotateX(-155deg),rotateY(-90deg);}
//                        100% { transform:rotateX(0deg),rotateY(0deg);}
//                    }
                    }
                }
                nowFrame++;
            }

            //把帧答应出来

            for(Map.Entry<String,StringBuffer> sbEntry:animationStrMap.entrySet()){
                String blockName = sbEntry.getKey();
                LogUtil.println("%s动画",blockName);
                StringBuffer blockAniStr = sbEntry.getValue();
                LogUtil.println(blockAniStr.toString()+"}");
            }
        }

    }

    public void connect(){
        if(selectBlockList.size()>=2){
            BoneRotateImageBlock firstBlock =(BoneRotateImageBlock)selectBlockList.get(0);
            BaseBlock secondBlock =selectBlockList.get(1);
            if(secondBlock instanceof  BoneRotateImageBlock){
                firstBlock.addChild((BoneRotateImageBlock)secondBlock);
            }else{
                firstBlock.block = secondBlock;
            }
           // firstBlock.addChild(secondBlock);
            //selectBlockList.remove(secondBlock);
            //colorBlockList.remove(secondBlock);
        }
    }

    public void setParentX(float val){
        BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock)this.getSelectFirstBlock();
        boneRotateImageBlock.parentPosition.x=val;
    }
    public void setParentY(float val){
        BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock)this.getSelectFirstBlock();
        boneRotateImageBlock.parentPosition.y=val;
    }
    public void setParentZ(float val){
        BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock)this.getSelectFirstBlock();
        boneRotateImageBlock.parentPosition.z=val;
    }

    public void setChildX(float val){
        BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock)this.getSelectFirstBlock();
        boneRotateImageBlock.childPosition.x=val;
    }
    public void setChildY(float val){
        BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock)this.getSelectFirstBlock();
        boneRotateImageBlock.childPosition.y=val;
    }
    public void setChildZ(float val){
        BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock)this.getSelectFirstBlock();
        boneRotateImageBlock.childPosition.z=val;
    }

    public List<BaseBlock> getBoneBlockList(){
        List<BaseBlock> boneList = new ArrayList<>();
       BaseBlock selectBlock =  getSelectFirstBlock();
        if(selectBlock instanceof GroupBlock){
            GroupBlock groupBlock = (GroupBlock) selectBlock;
            if(groupBlock.selectBlockList.size()>0 && groupBlock.selectBlockList.get(0) instanceof BoneRotateImageBlock ){
                BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock)groupBlock.selectBlockList.get(0);
                fillBoneList(boneRotateImageBlock,boneList);
            }


            return boneList;
        }
        BoneRotateImageBlock boneRotateImageBlock = (BoneRotateImageBlock)getSelectFirstBlock();
        fillBoneList(boneRotateImageBlock,boneList);

        return boneList;
    }

    public void fillBoneList(BoneRotateImageBlock boneRotateImageBlock,List<BaseBlock> boneList){
        if(boneRotateImageBlock.children.size()>0){
           for(BaseBlock block:boneRotateImageBlock.children){
               boneList.add(block);
                if(block instanceof  BoneRotateImageBlock){//如果是boneblock 的话就继续往里面找
                    fillBoneList((BoneRotateImageBlock)block,boneList);
                }
           }
        }

    }

    public void selectBoneByName(String name) {


    }

    public void selectBone(BaseBlock baseBlock) {
        if(Switcher.isEditComponent){
            currentChoosedGroupForEdit.selectBlockList.clear();
            currentChoosedGroupForEdit.selectBlockList.add(baseBlock);
            return;
        }
        selectBlockList.clear();
        selectBlockList.add(baseBlock);
    }

    public void changeRotateImageBlockAsBoneRotateImageBlock(){
        for(BaseBlock block: selectBlockList){
            if(block instanceof  RotateImageBlock){
                RotateImageBlock rotateImageBlock = (RotateImageBlock)block;
                BoneRotateImageBlock boneRotateImageBlock =new BoneRotateImageBlock();
                boneRotateImageBlock.x=rotateImageBlock.x;
                boneRotateImageBlock.y=rotateImageBlock.y;
                boneRotateImageBlock.z=rotateImageBlock.z;
                boneRotateImageBlock.width=rotateImageBlock.width;
                boneRotateImageBlock.height=rotateImageBlock.height;
                boneRotateImageBlock.thick=rotateImageBlock.thick;
                boneRotateImageBlock.rotateX=rotateImageBlock.rotateX;
                boneRotateImageBlock.rotateY=rotateImageBlock.rotateY;
                boneRotateImageBlock.rotateZ=rotateImageBlock.rotateZ;
                boneRotateImageBlock.front=rotateImageBlock.front;
                boneRotateImageBlock.back=rotateImageBlock.back;
                boneRotateImageBlock.left=rotateImageBlock.left;
                boneRotateImageBlock.right=rotateImageBlock.right;
                boneRotateImageBlock.top=rotateImageBlock.top;
                boneRotateImageBlock.bottom=rotateImageBlock.bottom;
                boneRotateImageBlock.centerX=rotateImageBlock.centerX;
                boneRotateImageBlock.centerY=rotateImageBlock.centerY;
                boneRotateImageBlock.name = rotateImageBlock.name;
                boneRotateImageBlock.centerZ=rotateImageBlock.centerZ;
                colorBlockList.add(boneRotateImageBlock);
            }
        }
    }

    public static BaseBlock parse(  JSONObject object ){
        String blockType = (String) object.get("blocktype");
        if ("imageblock".equals(blockType)) {
            ImageBlock imageBlock = ImageBlock.parse(object);
            return imageBlock;
        } else if ("colorblock".equals(blockType)) {
            ColorBlock colorBlock = ColorBlock.parse(object);
            return colorBlock;
        } else if ("rotatecolorblock".equals(blockType)) {
            RotateColorBlock2 shape = RotateColorBlock2.parse(object);
            return shape;
        } else if ("rotateimageblock".equals(blockType)) {
            RotateImageBlock shape = RotateImageBlock.parse(object);
            return shape;
        } else if ("bonerotateimageblock".equals(blockType)) {
            BoneRotateImageBlock shape = BoneRotateImageBlock.parse(object);
            return shape;
        }else if ("groupblock".equals(blockType)) {
            GroupBlock shape = GroupBlock.parse(object);
            return shape;
        }else if ("animationblock".equals(blockType)) {
            AnimationBlock shape = AnimationBlock.parse(object);
            return shape;
        }else if ("boneblock ".equals(blockType)) {
            BoneBlock shape = BoneBlock.parse(object);
            return shape;
        }else {
            BoneBlock shape = BoneBlock.parse(object);
            return shape;
        }


    }

    public void genFromTexBtn() {

        //获得选择的纹理
        if( this.nowTextureInfo!=null){
            CakeModel cakeModel =new CakeModel(this.nowTextureInfo);
            for(int i=0;i<cakeModel.blocks.length;i++){
                IBlock block = cakeModel.blocks[i];
                this.colorBlockList.add((ColorBlock)block);
                this.needUpdate=true;
            }
            cakeModel.blocks=null;
        }

    }

    public void changColorBlockToRotateBlock(){
        for(int i=0;i<selectBlockList.size();i++){

            BaseBlock block = selectBlockList.get(i);
            if(block instanceof  ColorBlock){
               ColorBlock  colorBlock =(ColorBlock ) block;
                RotateColorBlock2 rotateColorBlock2  = new RotateColorBlock2();
                rotateColorBlock2.x=colorBlock.x;
                rotateColorBlock2.y=colorBlock.y;
                rotateColorBlock2.z=colorBlock.z;

                rotateColorBlock2.width=colorBlock.width;
                rotateColorBlock2.height=colorBlock.height;
                rotateColorBlock2.thick=colorBlock.thick;

                rotateColorBlock2.rf=colorBlock.rf;
                rotateColorBlock2.bf=colorBlock.bf;
                rotateColorBlock2.gf=colorBlock.gf;
                rotateColorBlock2.opacity=colorBlock.opacity;
                colorBlockList.remove(colorBlock);
                colorBlockList.add(rotateColorBlock2);

            }
            needUpdate=true;
        }
    }
}
