package com.dozenx.game.engine.Role.model;

import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.command.EquipCmd;
import com.dozenx.game.engine.command.EquipPartType;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import core.log.LogUtil;
import glmodel.GL_Matrix;

import javax.vecmath.Point3f;

/**
 * Created by luying on 17/3/5.
 */
public class PlayerModel extends  Model{
    protected  float HAND_HEIGHT=1.5f;
    protected float HAND_WIDTH=0.5f;
    protected float HAND_THICK=0.5f;

    protected  float BODY_HEIGHT=1.5f;
    protected float BODY_WIDTH=1f;
    protected  float BODY_THICK=0.5f;


    protected float LEG_HEIGHT=1.5f;
    protected float LEG_WIDTH=0.5f;
    protected  float LEG_THICK=0.5f;

    protected float HEAD_HEIGHT=1f;
    protected float HEAD_WIDTH=1f;
    protected  float HEAD_THICK=1f;

    public PlayerModel(){
        int id =role.getId();
        bodyComponent.id=id*10+EquipPartType.BODY.ordinal();
        bodyComponent.name = "human_body";
        bodyComponent.setEightFace("human_body");

        //rhand
        Component rHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        rHandComponent.id=id*10+EquipPartType.RHAND.ordinal();

        rHandComponent.setEightFace("human_hand");
        rHandComponent.name="rhuman_hand";
        rHandComponent.setOffset(new Point3f(BODY_WIDTH,BODY_HEIGHT*3/4,BODY_THICK/2),new Point3f(0,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addChild(rHandComponent);

        //小手

        //lhand
        Component lHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        lHandComponent.id=id*10+EquipPartType.LHAND.ordinal();
        lHandComponent.setEightFace("human_hand");
        lHandComponent.name="lhuman_hand";
        //rHandComponent.name="rHumanHand";
        lHandComponent.setOffset(new Point3f(0,BODY_HEIGHT*3/4,BODY_THICK/2),new Point3f(HAND_WIDTH,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addChild(lHandComponent);

        //lleg
        Component human_l_b_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_l_b_leg.setEightFace("human_leg");
        human_l_b_leg.id=id*10+EquipPartType.LLEG.ordinal();
        human_l_b_leg.name= "human_l_b_leg";
        human_l_b_leg.setOffset(new Point3f(LEG_WIDTH/2,0,BODY_THICK/2),new Point3f(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));
        bodyComponent.addChild(human_l_b_leg);




        //rleg
        Component human_r_b_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_r_b_leg.setEightFace("human_leg");
        human_r_b_leg.name= "human_r_b_leg";
        human_r_b_leg.id=id*10 +EquipPartType.RLEG.ordinal();
        human_r_b_leg.setOffset(new Point3f(BODY_WIDTH-LEG_WIDTH/2,0,BODY_THICK/2),new Point3f(LEG_WIDTH/2,LEG_HEIGHT,LEG_THICK/2));
        bodyComponent.addChild(human_r_b_leg);

        //head

        Component head= new Component(HEAD_WIDTH,HEAD_HEIGHT,HEAD_THICK);
        head.setEightFace("human_head");
        head.id=id*10+EquipPartType.HEAD.ordinal();
        head.setOffset(new Point3f(BODY_WIDTH/2,BODY_HEIGHT,BODY_THICK/2),new Point3f(HEAD_WIDTH/2,0,HEAD_THICK/2));
        bodyComponent.addChild(head);

    }

    public Component bodyComponent = new Component(BODY_WIDTH,BODY_HEIGHT,BODY_THICK);

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

    public void addHeadEquip(ItemDefinition itemCfg)  {

        Component parent = 	bodyComponent.findChild("human_head");
        addChild(parent, "cap", itemCfg);
    }





    */
/*

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
                Component component = new Component(shape.getWidth(), shape.getHeight(), shape.getThick());
                component.setShape(itemCfg.getShape());
                component.name = name;

                component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                changeProperty();
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
                Component component = new Component(shape.getWidth(), shape.getHeight(), shape.getThick());
                component.setShape(itemCfg.getShape());
                component.name = name;

                component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                changeProperty();
            }
        }
    }*/
    public void addShoeEquip(boolean leftFlag ,ItemDefinition itemCfg)  {
        Component parent ;
        if(leftFlag){
            parent = bodyComponent.findChild("human_l_b_leg");
        }else{
            parent = bodyComponent.findChild("human_r_b_leg");
        }
        //Component shoe =   bodyComponent.findChild("shoe");
        addChild(parent,"shoe",itemCfg);
    }
    public void addShoeEquip(ItemDefinition itemCfg)  {
        addShoeEquip(true, itemCfg);
        addShoeEquip(false, itemCfg);
    }
    public void addLegEquip(ItemDefinition itemCfg)  {
        Component parent = 	bodyComponent.findChild("human_l_b_leg");
        addChild(parent, "pants", itemCfg);
    }
    public void addBodyEquip(ItemDefinition itemCfg)  {
        Component parent = 	bodyComponent.findChild("human_body");
        addChild(parent, "armor", itemCfg);
    }

    public void addHandEquip(ItemDefinition itemCfg)  {
        //Shape shape = itemCfg.getShape();
        Component parent = 	bodyComponent.findChild("rhuman_hand");
        addChild(parent, "weapon", itemCfg);
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

}
