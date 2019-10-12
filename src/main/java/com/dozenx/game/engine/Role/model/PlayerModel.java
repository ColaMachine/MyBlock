package com.dozenx.game.engine.Role.model;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.base.BaseBlock;
import cola.machine.game.myblocks.model.BodyComponent;
import cola.machine.game.myblocks.model.BoneRotateImageBlock;
import cola.machine.game.myblocks.model.HandComponent;
import cola.machine.game.myblocks.model.textture.BoneBlock;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.command.EquipPartType;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector4f;

/**
 * Created by luying on 17/3/5.
 */
public class PlayerModel extends BaseModel   {


    float bili =1f;

    protected  float HAND_HEIGHT=1.5f*bili;
    protected float HAND_WIDTH=0.5f*bili;
    protected float HAND_THICK=0.5f*bili;

    protected  float BODY_HEIGHT=1.5f*bili;
    protected float BODY_WIDTH=1f*bili;
    protected  float BODY_THICK=0.5f*bili;


    protected float LEG_HEIGHT=1.5f*bili;
    protected float LEG_WIDTH=0.5f*bili;
    protected  float LEG_THICK=0.5f*bili;

    protected float HEAD_HEIGHT=1f*bili;
    protected float HEAD_WIDTH=1f*bili;
    protected  float HEAD_THICK=1f*bili;
    BoneRotateImageBlock boneRotateImageBlock;
    public PlayerModel(Role role ){
        super(role);

        BaseBlock block ;
        block = TextureManager.getShape("human_body");
//        BODY_WIDTH = block.width;
//        BODY_HEIGHT = block.height;
//        BODY_THICK = block.thick;

//        rootComponent= (BoneRotateImageBlock)ItemManager.getItemDefinition("player").getShape();
       // this.rootComponent = (BoneRotateImageBlock)ItemManager.getItemDefinition("bonesteve").getShape();
        //rootComponent=new BodyComponent(BODY_WIDTH,BODY_HEIGHT,BODY_THICK);
//        boneRotateImageBlock = (BoneRotateImageBlock)ItemManager.getItemDefinition("player").getShape();

        int id =role.getId();
        rootComponent.id=id*10+EquipPartType.BODY.ordinal();
        rootComponent.name = EquipPartType.BODY.getName();
        rootComponent.block = block;
        rootComponent.set(block.getWidth(),block.getHeight(),block.getThick());
        //rootComponent=block;
        //rarm
        Component rArm= new BodyComponent(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        rArm.id=id*10+EquipPartType.LARM.ordinal();


        block = TextureManager.getShape("human_hand");
//        HAND_WIDTH = block.width;
//        HAND_HEIGHT = block.height;
//        HAND_THICK = block.thick;

        rArm.block = block;
        rArm.name=EquipPartType.LARM.getName();
        rArm.setOffset(new GL_Vector(BODY_WIDTH,BODY_HEIGHT*3/4,BODY_THICK/2),new GL_Vector(0,HAND_HEIGHT*3/4,HAND_THICK/2));
        rootComponent.addChild(rArm);

        //小手

        //larm
        Component lArm= new BodyComponent(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        lArm.id=id*10+EquipPartType.RARM.ordinal();

        lArm.block = TextureManager.getShape("human_hand");
        lArm.name=EquipPartType.RARM.getName();
        //rHandComponent.name="rHumanHand";
        lArm.setOffset(new GL_Vector(0,BODY_HEIGHT*3/4,BODY_THICK/2),new GL_Vector(HAND_WIDTH,HAND_HEIGHT*3/4,HAND_THICK/2));
        rootComponent.addChild(lArm);

        //lleg


        block = TextureManager.getShape("human_leg");
//        LEG_WIDTH = block.width;
//        LEG_HEIGHT = block.height;
//        LEG_THICK = block.thick;


        Component lleg= new BodyComponent(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);


        lleg.block = block;

        lleg.id=id*10+EquipPartType.RLEG.ordinal();
        lleg.name= EquipPartType.RLEG.getName();
        lleg.setOffset(new GL_Vector(LEG_WIDTH/2,0,BODY_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));
        rootComponent.addChild(lleg);


        //rleg
        Component rleg= new BodyComponent(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        //rleg.setEightFace("human_leg");


        rleg.block = TextureManager.getShape("human_leg");
        rleg.name= EquipPartType.LLEG.getName();
        rleg.id=id*10 +EquipPartType.LLEG.ordinal();
        rleg.setOffset(new GL_Vector(BODY_WIDTH-LEG_WIDTH/2,0,BODY_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,LEG_THICK/2));
        rootComponent.addChild(rleg);

        //head

        block = TextureManager.getShape("human_head");
//        HEAD_WIDTH = block.width;
//        HEAD_HEIGHT = block.height;
//        HEAD_THICK = block.thick;


//        block = block;

        Component head= new BodyComponent(HEAD_WIDTH,HEAD_HEIGHT,HEAD_THICK);
        //head.setEightFace("human_head");
        head.block = block;
        head.id=id*10+EquipPartType.HEAD.ordinal();
        head.name=EquipPartType.HEAD.getName();

        head.setOffset(new GL_Vector(BODY_WIDTH/2,BODY_HEIGHT,BODY_THICK/2),new GL_Vector(HEAD_WIDTH/2,0,HEAD_THICK/2));
        rootComponent.addChild(head);


    }


        public void renderShader(){
        /*if(ShaderManager.livingThingShaderConfig.getVao().getVaoId()==0){
            preRenderShader();

            //LogUtil.err("vao id 不能为空");
        }*/
            // assert ShaderManager.livingThingShaderConfig.getVao().getVaoId()!=0;
            // TextureManager.getTextureInfo("human_head_right").bind();

            //ShaderUtils.finalDraw(GamingState.instance.shaderManager.livingThingShaderConfig);
        /*glUseProgram(GamingState.instance.shaderManager.livingThingShaderConfig.getProgramId());
        Util.checkGLError();

       *//* int transformLoc= glGetUniformLocation(ProgramId,"transform");
        glUniformMatrix4(0,  false,matrixBuffer );
        matrixBuffer.rewind();*//*
        // glBindTexture(GL_TEXTURE_2D, this.textureHandle);
        glBindVertexArray(GamingState.instance.shaderManager.livingThingShaderConfig.getVao().getVaoId());

//        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        //3position 3normal 2texcoord 1 textureindex
        glDrawArrays(GL11.GL_TRIANGLES,0,GamingState.instance.shaderManager.livingThingShaderConfig.getVao().getPoints()/9);

        Util.checkGLError();
        glBindVertexArray(0);

        glUseProgram(0);*/
            // GL11.glPushMatrix();

            // GL11.glTranslatef(position.x, position.y + 0.75f, position.z);
            // float angle=GL_Vector.angleXZ(this.WalkDir , new GL_Vector(0,0,-1));
            //GL11.glRotatef(angle, 0, 1, 0);
            // GL11.glScalef(0.5f,0.5f,0.5f);

            // bodyComponent.renderShader().render();
        /*GL11.glScalef(2,2,2);
        GL11.glRotatef(-angle, 0, 1, 0);
        GL11.glTranslatef(-position.x,-position.y,-position.z);
        GL11.glPopMatrix();*/



            //GLApp.project(this.position.x, this.position.y+2, this.position.z, vector);

            // vector[1]=600-vector[1]-45;

            //TextureManager.getTextureInfo("human_head_front").draw(null,(int)result[0],(int)result[1],headWidth,headHeight);
       /* GLApp.pushAttrib();
       GLApp.setOrthoOn();

        TextureManager.getTextureInfo("human_head_front").draw(null,(int)vector[0],(int)vector[1],40,40);

        GLApp.glFillRect((int)vector[0],(int)vector[1], 150, 20, 4, borderColor, whiteColor);
        GLApp.glFillRect((int)vector[0],(int)vector[1]+4,150*nowBlood/blood,20,lineWdith,borderColor,redColor);

        GLApp.glFillRect((int)vector[0],(int)vector[1]+30,150,20,lineWdith,borderColor,whiteColor);
        GLApp.glFillRect((int)vector[0],(int)vector[1]+30,150*nowEnergy/energy,20,lineWdith,borderColor,blue);

        GLApp.print((int)vector[0],(int)vector[1]+30,"hello");
        GLApp.setOrthoOff();
        GLApp.popAttrib();*/
        }



    public void addHeadEquip(ItemBean itemCfg)  {

        BoneRotateImageBlock parent = 	rootComponent.findChild("human_head");
        LogUtil.println("添加头盔"+(itemCfg==null ?"空":itemCfg.getId()));
        clearAddChild(parent,Component.body, "helmet", itemCfg);
    }


    public void addLRShoeEquip(boolean leftFlag ,ItemBean itemCfg)  {
        BoneRotateImageBlock parent ;
        if(leftFlag){
            parent = rootComponent.findChild(EquipPartType.LLEG.getName());
        }else{
            parent = rootComponent.findChild(EquipPartType.RLEG.getName());
        }
        //Component shoe =   bodyComponent.findChild("shoe");
        clearAddChild(parent,Component.body,EquipPartType.SHOOE.getName(),itemCfg);
    }
    public void addShoeEquip(ItemBean itemCfg)  {
        addLRShoeEquip(true, itemCfg);
        addLRShoeEquip(false, itemCfg);
    }

    public void addLRegEquip(boolean leftFlag ,ItemBean itemCfg)  {
        BoneRotateImageBlock parent ;
        if(leftFlag){
            parent = rootComponent.findChild(EquipPartType.LLEG.getName());
        }else{
            parent = rootComponent.findChild(EquipPartType.RLEG.getName());
        }
        //Component shoe =   bodyComponent.findChild("shoe");
        clearAddChild(parent,Component.body,EquipPartType.PANTS.getName(),itemCfg);
    }

    public void addLegEquip(ItemBean itemCfg)  {
        addLRegEquip(true, itemCfg);
        addLRegEquip(false, itemCfg);
    }
    public void addBodyEquip(ItemBean itemCfg)  {
        BoneRotateImageBlock parent = 	rootComponent.findChild(EquipPartType.BODY.getName());
        clearAddChild(parent,Component.body, EquipPartType.ARMOR.getName(), itemCfg);
    }

    public void addHandEquip(ItemBean itemBean)  {
//        BaseBlock block = ItemManager.getItemDefinition("bonesteve").getShape();
//        if(block instanceof AnimationBlock){
//            AnimationBlock animationBlock = (AnimationBlock)block;
//            this.rootComponent =(BoneRotateImageBlock)animationBlock.colorBlockList.get(0);
//        }
      //this.rootComponent =(BoneRotateImageBlock) ItemManager.getItemDefinition("newplayer").getShape();


        //    this.itemDefinition= ItemManager.getItemDefinition("bonesteve");
        //Shape shape = itemCfg.getShape();
        BoneRotateImageBlock parent = 	rootComponent.findChild(EquipPartType.RARM.getName());
        parent.children.clear();
        this.addHandChild(parent, EquipPartType.RHAND.getName(),  itemBean);
    }


//    protected  float BODY_HEIGHT=1.5f*bili;
//    protected float BODY_WIDTH=1f*bili;
//    protected  float BODY_THICK=0.5f*bili;
   // Role role ;



    public void build(){//当发生改变变的时候触发这里



        if(Switcher.SHADER_ENABLE){




            GL_Matrix translateMatrix=GL_Matrix.translateMatrix(role.getX(), role.getY() + 0.75f, role.getZ());//-BODY_THICK/2
            //float angle=GL_Vector.angleXZ(this.WalkDir , new GL_Vector(0,0,-1));
            GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,-role.getBodyAngle()+ Constants.PI90/**3.14f/180*/,0);

            rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
            rotateMatrix.scaleSelf(0.1f,0.1f,0.1f);
            GL_Matrix newtranslateMatrix=GL_Matrix.translateMatrix(-BODY_WIDTH/2, 0, -BODY_THICK/2);
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,newtranslateMatrix);
            //.getVao().getVertices()
            //  ShaderManager.livingThingShaderConfig.getVao().getVertices().rewind();
            rootComponent.renderShader(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),rotateMatrix);
            //渲染头部名字
            if(StringUtil.isNotEmpty(role.getName())){

                //
                GL_Matrix translateMatrix1 = GL_Matrix.translateMatrix(role.getX(), role.getY() + 3.5f, role.getZ());
                float angle = /*(float)(Math.PI)+*/-GamingState.player.getHeadAngle()- Constants.PI90;
                GL_Matrix rotateMatrix1 = GL_Matrix.rotateMatrix(0,angle/**3.14f/180,0*/,0);

                rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);

                // rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);
                rotateMatrix1=GL_Matrix.multiply(rotateMatrix1,GL_Matrix.translateMatrix(-2f, 0,0));

                ShaderUtils.draw3dText(role.getName(), rotateMatrix1, 24, new Vector4f(1, 1, 1, 1), ShaderManager.livingThingShaderConfig);



            }

        }else{

        }



         /*trianglesCount= floatBuffer.position()/8;
        if(trianglesCount<=0){
            LogUtil.println("trianglesCount can't be 0");
            System.exit(1);
        }*/
        // ShaderManager.livingThingShaderConfig.getVao().setVertices();
        // ShaderUtils.updateLivingVao(ShaderManager.livingThingShaderConfig.getVao());//createVAO(floatBuffer);
        /*if(ShaderManager.livingThingShaderConfig.getVao().getVaoId()<=0){
            LogUtil.println("vaoId can't be 0");
            System.exit(1);
        }*/

        //显示头标


    }

    /*public void addChild(Component parent,String name,ItemBean itemBean) {
        if(parent==null){
            LogUtil.err("parent node is null");
        }
        Component shoe = parent.findChild(name);
        if (shoe == null) {
            if (itemBean == null||itemBean.itemDefinition == null) {
                return;
            } else {
                Shape shape = itemBean.itemDefinition.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemBean.itemDefinition.getName() + "failed");

                }

                Component component = new WearComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
                //component.setShape(itemCfg.getShape());
                component.setItem(itemBean);
                component.name = name;

                component.setOffset(new GL_Vector(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new GL_Vector(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                //changeProperty();
            }
        } else {

            if (itemBean == null||itemBean.itemDefinition == null) {
                //删除shoe节点
                parent.removeChild(shoe);
            } else {
                parent.removeChild(shoe);
                Shape shape = itemBean.itemDefinition.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemBean.itemDefinition.getName() + "failed");

                }
                Component component = new WearComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
                // component.setShape(itemCfg.getShape());
                component.setItem(itemBean);
                component.name = name;

                component.setOffset(new GL_Vector(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new GL_Vector(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                //changeProperty();
            }
        }
    }*/
    public void addHandChild(BoneRotateImageBlock parent,String name,ItemBean itemBean) {

      /*  Component rarm = 	rootComponent.findChild(EquipPartType.RARM.getName());
        rarm.children.clear();;*/

        super.clearAddChild(parent,Component.hand,name,itemBean);
        /*if(parent==null){
            LogUtil.err("parent node is null");
        }
        Component shoe = parent.findChild(name);
        if (shoe == null) {
            if (itemBean == null || itemBean.itemDefinition==null) {
                return;
            } else {
                BaseBlock shape = (BoneBlock)itemBean.itemDefinition.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" +  itemBean.itemDefinition.getName() + "failed");

                }

                Component component = new HandComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
                //component.setShape(itemCfg.getShape());
                component.setItem( itemBean);
                component.name = name;

                component.setOffset(new GL_Vector(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new GL_Vector(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.children.clear();
                parent.addChild(component);
                //changeProperty();
            }
        } else {

            if ( itemBean == null || itemBean.itemDefinition==null ) {
                //删除shoe节点
                parent.removeChild(shoe);
            } else {
                parent.removeChild(shoe);
                BaseBlock shape = itemBean.itemDefinition.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" +  itemBean.itemDefinition.getName() + "failed");

                }
                Component component = new HandComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
                // component.setShape(itemCfg.getShape());
                component.setItem( itemBean);
                component.name = name;
                if(shape instanceof  BoneBlock){
                    BoneBlock boneBlock = (BoneBlock) shape;
                    component.setOffset(new GL_Vector(boneBlock.getP_posi_x(), boneBlock.getP_posi_y(), boneBlock.getP_posi_z()), new GL_Vector(boneBlock.getC_posi_x(), boneBlock.getC_posi_y(), boneBlock.getC_posi_z()));

                }
                 //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                //changeProperty();
            }
        }*/
    }


    public void render(){
        /*GL11.glPushMatrix();try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*/

        GL11.glTranslatef(role.getX(), role.getY() + 0.75f, role.getZ());
        float angle= GL_Vector.angleXZ(role.walkDir, new GL_Vector(0, 0, -1));
        GL11.glRotatef(angle, 0, 1, 0);/*try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*/
        GL11.glScalef(0.5f,0.5f,0.5f);/*try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }
*/
       // rootComponent.render();
        GL11.glScalef(2,2,2);
        GL11.glRotatef(-angle, 0, 1, 0);
        GL11.glTranslatef(-role.getX(), -role.getY() - 0.75f, -role.getZ());
        // GL11.glPopMatrix();



        //GLApp.project(this.position.x, this.position.y+2, this.position.z, vector);

        //vector[1]=600-vector[1]-45;

        //TextureManager.getTextureInfo("human_head_front").draw(null,(int)result[0],(int)result[1],headWidth,headHeight);
       /* GLApp.pushAttrib();
       GLApp.setOrthoOn();

        TextureManager.getTextureInfo("human_head_front").draw(null,(int)vector[0],(int)vector[1],40,40);

        GLApp.glFillRect((int)vector[0],(int)vector[1], 150, 20, 4, borderColor, whiteColor);
        GLApp.glFillRect((int)vector[0],(int)vector[1]+4,150*nowBlood/blood,20,lineWdith,borderColor,redColor);

        GLApp.glFillRect((int)vector[0],(int)vector[1]+30,150,20,lineWdith,borderColor,whiteColor);
        GLApp.glFillRect((int)vector[0],(int)vector[1]+30,150*nowEnergy/energy,20,lineWdith,borderColor,blue);

        GLApp.print((int)vector[0],(int)vector[1]+30,"hello");
        GLApp.setOrthoOff();
        GLApp.popAttrib();*/
       /*try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*/
    }

/*


    public void addHeadEquip(ItemDefinition itemCfg)  {

        Component parent = 	rootComponent.findChild("human_head");
        addChild(parent,Component.body, "helmet", itemCfg);
    }
*/


    public void addLRShoeEquip(boolean leftFlag ,ItemDefinition itemCfg)  {
        BoneRotateImageBlock parent ;
        if(leftFlag){
            parent = rootComponent.findChild(EquipPartType.LLEG.getName());
        }else{
            parent = rootComponent.findChild(EquipPartType.RLEG.getName());
        }
        //Component shoe =   bodyComponent.findChild("shoe");
        addChild(parent,Component.body,EquipPartType.SHOOE.getName(),itemCfg);
    }
    public void addShoeEquip(ItemDefinition itemCfg)  {
        addLRShoeEquip(true, itemCfg);
        addLRShoeEquip(false, itemCfg);
    }

    public void addLRegEquip(boolean leftFlag ,ItemDefinition itemCfg)  {
        BoneRotateImageBlock parent ;
        if(leftFlag){
            parent = rootComponent.findChild(EquipPartType.LLEG.getName());
        }else{
            parent = rootComponent.findChild(EquipPartType.RLEG.getName());
        }
        //Component shoe =   bodyComponent.findChild("shoe");
        addChild(parent,Component.body,EquipPartType.PANTS.getName(),itemCfg);
    }

    public void addLegEquip(ItemDefinition itemCfg)  {
        addLRegEquip(true, itemCfg);
        addLRegEquip(false, itemCfg);
    }
    public void addBodyEquip(ItemDefinition itemCfg)  {
        BoneRotateImageBlock parent = 	rootComponent.findChild(EquipPartType.BODY.getName());
        addChild(parent,Component.body, EquipPartType.ARMOR.getName(), itemCfg);
    }

    public void addHandEquip(ItemDefinition itemCfg)  {
        //Shape shape = itemCfg.getShape();
        BoneRotateImageBlock parent = 	rootComponent.findChild(EquipPartType.RARM.getName());
        this.addHandChild(parent, EquipPartType.RHAND.getName(), itemCfg);
    }

    public void removeChild(BoneRotateImageBlock parent,String name) {
        if(parent==null){
            LogUtil.err("parent node is null");
        }
        BoneRotateImageBlock shoe = parent.findChild(name);
        if (shoe == null) {

            return;

        } else {


            parent.removeChild(shoe);

        }
    }
    public void addChild(BoneRotateImageBlock parent,int type ,String name,ItemDefinition itemCfg) {
        super.clearAddChild(parent,type,name,new ItemBean(itemCfg,1));
       /* if(parent==null){
            LogUtil.err("parent node is null");
        }
        Component shoe = parent.findChild(name);
        if (shoe == null) {
            if (itemCfg == null) {
                return;
            } else {
                BoneBlock shape = (BoneBlock)itemCfg.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemCfg.getName() + "failed");

                }

                Component component = new WearComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
                //component.setShape(itemCfg.getShape());
                component.setItem(itemCfg);
                component.name = name;

                component.setOffset(new GL_Vector(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new GL_Vector(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                //changeProperty();
            }
        } else {

            if (itemCfg == null) {
                //删除shoe节点
                parent.removeChild(shoe);
            } else {
                parent.removeChild(shoe);
                BoneBlock shape = (BoneBlock)itemCfg.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemCfg.getName() + "failed");

                }
                Component component = new WearComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
                // component.setShape(itemCfg.getShape());
                component.setItem(itemCfg);
                component.name = name;

                component.setOffset(new GL_Vector(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new GL_Vector(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                //changeProperty();
            }
        }*/
    }

    public void clearHandChild(Component parent,String name) {
        if(parent==null){
            LogUtil.err("parent node is null");
        }
        Component shoe = parent.findChild(name);
        if (shoe == null) {
            return;

        } else {

            //删除shoe节点
            parent.removeChild(shoe);

        }
    }
    public void addHandChild(BoneRotateImageBlock parent,String name,ItemDefinition itemCfg) {
        if (parent == null) {
            LogUtil.err("parent node is null");
        }
        BoneRotateImageBlock shoe = parent.findChild(name);
        if (shoe == null) {
            if (itemCfg == null) {
                return;
            } else {
                BoneBlock shape = (BoneBlock)itemCfg.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemCfg.getName() + "failed");

                }

                Component component = new HandComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
                //component.setShape(itemCfg.getShape());
                component.setItem(itemCfg);
                component.name = name;
                component.setOffset(shape.parentPosition,component.childPosition);
                //component.setOffset(new GL_Vector(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new GL_Vector(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                //changeProperty();
            }
        } else {

            if (itemCfg == null) {
                //删除shoe节点
                parent.removeChild(shoe);
            } else {
                parent.removeChild(shoe);
                BoneBlock shape = (BoneBlock)itemCfg.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemCfg.getName() + "failed");

                }
                Component component = new HandComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
                // component.setShape(itemCfg.getShape());
                component.setItem(itemCfg);
                component.name = name;
                component.setOffset(shape.parentPosition,component.childPosition);
                //component.setOffset(new GL_Vector(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new GL_Vector(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                //changeProperty();
            }
        }
    }

    @Override
    public BoneRotateImageBlock getRootComponent() {
        return rootComponent;
    }

    /**
     * 这个正式的渲染 现在的层级是 player 具体对象 ==> model 抽象模型层==>component 具体组件层
     * @param config
     * @param rotateMatrix
     */
    @Override
    public void build(ShaderConfig config, GL_Matrix rotateMatrix) {
        //this.role.getPosition().x+=0.01;
        //GamingState.cameraChanged.x+=0.01;
        super.build(config,rotateMatrix);
//        if(Switcher.SHADER_ENABLE){
//
//
//
//            GL_Matrix /*translateMatrix=GL_Matrix.scaleMatrix(1f);*/
//             translateMatrix=GL_Matrix.translateMatrix(role.getX(), role.getY() + 0.35f, role.getZ());//-BODY_THICK/2
//            //float angle=GL_Vector.angleXZ(this.WalkDir , new GL_Vector(0,0,-1));
//             rotateMatrix = GL_Matrix.rotateMatrix(0,-role.getBodyAngle()+3.14f/2/**3.14f/180*/,0);
//      //  LogUtil.println("bodyAngle:"+role.getBodyAngle());
//            rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
//
//            GL_Matrix newtranslateMatrix=GL_Matrix.translateMatrix(-BODY_WIDTH*0.3f/2, 0, -BODY_THICK*0.3f/2);
//            rotateMatrix=GL_Matrix.multiply(rotateMatrix,newtranslateMatrix);
//            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.scaleMatrix(0.3f));
//            //.getVao().getVertices()
//            //  ShaderManager.livingThingShaderConfig.getVao().getVertices().rewind();
//           // GL_Matrix translateMatrix=GL_Matrix.scaleMatrix(0.5f);
//            rootComponent.build(ShaderManager.livingThingShaderConfig,rotateMatrix);
//            //渲染头部名字
//            if(StringUtil.isNotEmpty(role.getName())){
//
//                //
//                GL_Matrix translateMatrix1 = GL_Matrix.translateMatrix(role.getX(), role.getY() + 3.5f, role.getZ());
//                float angle = /*(float)(Math.PI)+*/-GamingState.player.getHeadAngle()-3.14f/2;
//                GL_Matrix rotateMatrix1 = GL_Matrix.rotateMatrix(0,angle/**3.14f/180,0*/,0);
//
//                rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);
//
//                // rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);
//                rotateMatrix1=GL_Matrix.multiply(rotateMatrix1,GL_Matrix.translateMatrix(-2f, 0,0));
//
////                ShaderUtils.draw3dText(role.getName(), rotateMatrix1, 24, new Vector4f(1, 1, 1, 1), ShaderManager.livingThingShaderConfig);
//
//
//
//            }
//
//        }else{
//
//        }
    }

    @Override
    public void build(ShaderConfig config, Vao vao, int x, int y, int z) {

    }
}
