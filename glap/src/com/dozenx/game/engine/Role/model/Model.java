package com.dozenx.game.engine.Role.model;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.BodyComponent;
import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.model.HandComponent;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.command.EquipCmd;
import com.dozenx.game.engine.command.EquipPartType;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Point3f;
import javax.vecmath.Vector4f;

/**
 * Created by luying on 17/3/5.
 */
public class Model {
    protected  float BODY_HEIGHT=1.5f;
    protected float BODY_WIDTH=1f;
    protected  float BODY_THICK=0.5f;
    Role role ;
   /* protected  float HAND_HEIGHT=1.5f;
    protected float HAND_WIDTH=0.5f;
    protected float HAND_THICK=0.5f;




    protected float LEG_HEIGHT=1.5f;
    protected float LEG_WIDTH=0.5f;
    protected  float LEG_THICK=0.5f;

    protected float HEAD_HEIGHT=1f;
    protected float HEAD_WIDTH=1f;
    protected  float HEAD_THICK=1f;*/

    public Component bodyComponent = new BodyComponent(BODY_WIDTH,BODY_HEIGHT,BODY_THICK);

/*

    public void addBodyEquipStart(ItemDefinition itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(this, EquipPartType.BODY, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }
    public void addLegEquipStart(ItemDefinition itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(this, EquipPartType.LEG, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }

    public void addHandEquipStart(ItemDefinition itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(this, EquipPartType.HAND, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }

    public void addShoeEquipStart(ItemDefinition itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(this, EquipPartType.FOOT, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }
    public void addHeadEquipStart(ItemDefinition itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(this, EquipPartType.HEAD, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }
*/

    public void addHeadEquip(ItemDefinition itemCfg)  {

        Component parent = 	bodyComponent.findChild("human_head");
        addChild(parent, "helmet", itemCfg);
    }


    public void addLRShoeEquip(boolean leftFlag ,ItemDefinition itemCfg)  {
        Component parent ;
        if(leftFlag){
            parent = bodyComponent.findChild(EquipPartType.LLEG.getName());
        }else{
            parent = bodyComponent.findChild(EquipPartType.RLEG.getName());
        }
        //Component shoe =   bodyComponent.findChild("shoe");
        addChild(parent,EquipPartType.SHOOE.getName(),itemCfg);
    }
    public void addShoeEquip(ItemDefinition itemCfg)  {
        addLRShoeEquip(true, itemCfg);
        addLRShoeEquip(false, itemCfg);
    }

    public void addLRegEquip(boolean leftFlag ,ItemDefinition itemCfg)  {
        Component parent ;
        if(leftFlag){
            parent = bodyComponent.findChild(EquipPartType.LLEG.getName());
        }else{
            parent = bodyComponent.findChild(EquipPartType.RLEG.getName());
        }
        //Component shoe =   bodyComponent.findChild("shoe");
        addChild(parent,EquipPartType.PANTS.getName(),itemCfg);
    }

    public void addLegEquip(ItemDefinition itemCfg)  {
        addLRegEquip(true, itemCfg);
        addLRegEquip(false, itemCfg);
    }
    public void addBodyEquip(ItemDefinition itemCfg)  {
        Component parent = 	bodyComponent.findChild(EquipPartType.BODY.getName());
        addChild(parent, EquipPartType.ARMOR.getName(), itemCfg);
    }

    public void addHandEquip(ItemDefinition itemCfg)  {
        //Shape shape = itemCfg.getShape();
        Component parent = 	bodyComponent.findChild(EquipPartType.RARM.getName());
        this.addHandChild(parent, EquipPartType.RHAND.getName(), itemCfg);
    }
    // int vaoId;
    //int trianglesCount =0;
    public void build(){//当发生改变变的时候触发这里



        if(Switcher.SHADER_ENABLE){




            GL_Matrix translateMatrix=GL_Matrix.translateMatrix(role.getX(), role.getY() + 0.75f, role.getZ());
            //float angle=GL_Vector.angleXZ(this.WalkDir , new GL_Vector(0,0,-1));
            GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,-role.getBodyAngle()+3.14f/2/**3.14f/180*/,0);

            rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
            //.getVao().getVertices()
            //  ShaderManager.livingThingShaderConfig.getVao().getVertices().rewind();
            bodyComponent.build(ShaderManager.livingThingShaderConfig,rotateMatrix);

            if(StringUtil.isNotEmpty(role.getName())){

            //
            GL_Matrix translateMatrix1 = GL_Matrix.translateMatrix(role.getX(), role.getY() + 3.5f, role.getZ());
            float angle = /*(float)(Math.PI)+*/-GamingState.player.getHeadAngle()-3.14f/2;
            GL_Matrix rotateMatrix1 = GL_Matrix.rotateMatrix(0,angle/**3.14f/180,0*/,0);

            rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);

           // rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);
                rotateMatrix1=GL_Matrix.multiply(rotateMatrix1,GL_Matrix.translateMatrix(-2f, 0,0));

            ShaderUtils.draw3dText(role.getName(), rotateMatrix1, 24, new Vector4f(1,1,1,1),ShaderManager.livingThingShaderConfig);



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

    public void addChild(Component parent,String name,ItemDefinition itemCfg) {
        if(parent==null){
            LogUtil.err("parent node is null");
        }
        Component shoe = parent.findChild(name);
        if (shoe == null) {
            if (itemCfg == null) {
                return;
            } else {
                Shape shape = itemCfg.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemCfg.getName() + "failed");

                }

                Component component = new BodyComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
                //component.setShape(itemCfg.getShape());
                component.setItem(itemCfg);
                component.name = name;

                component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
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
                Shape shape = itemCfg.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemCfg.getName() + "failed");

                }
                Component component = new BodyComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
               // component.setShape(itemCfg.getShape());
                component.setItem(itemCfg);
                component.name = name;

                component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                //changeProperty();
            }
        }
    }
    public void addHandChild(Component parent,String name,ItemDefinition itemCfg) {
        if(parent==null){
            LogUtil.err("parent node is null");
        }
        Component shoe = parent.findChild(name);
        if (shoe == null) {
            if (itemCfg == null) {
                return;
            } else {
                Shape shape = itemCfg.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemCfg.getName() + "failed");

                }

                Component component = new HandComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
                //component.setShape(itemCfg.getShape());
                component.setItem(itemCfg);
                component.name = name;

                component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
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
                Shape shape = itemCfg.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemCfg.getName() + "failed");

                }
                Component component = new HandComponent(shape.getWidth(), shape.getHeight(), shape.getThick());
                // component.setShape(itemCfg.getShape());
                component.setItem(itemCfg);
                component.name = name;

                component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                //changeProperty();
            }
        }
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
        bodyComponent.render();
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

}
