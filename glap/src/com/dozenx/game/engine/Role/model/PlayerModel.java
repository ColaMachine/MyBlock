package com.dozenx.game.engine.Role.model;

import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.Role.bean.Role;
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

    public PlayerModel(Role role ){
        this.role =role;
        int id =role.getId();
        bodyComponent.id=id*10+EquipPartType.BODY.ordinal();
        bodyComponent.name = EquipPartType.BODY.getName();
        bodyComponent.setEightFace("human_body");

        //rhand
        Component rArm= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        rArm.id=id*10+EquipPartType.RARM.ordinal();

        rArm.setEightFace("human_hand");
        rArm.name=EquipPartType.RARM.getName();
        rArm.setOffset(new Point3f(BODY_WIDTH,BODY_HEIGHT*3/4,BODY_THICK/2),new Point3f(0,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addChild(rArm);

        //小手

        //lhand
        Component lArm= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        lArm.id=id*10+EquipPartType.LARM.ordinal();
        lArm.setEightFace("human_hand");
        lArm.name=EquipPartType.LARM.getName();
        //rHandComponent.name="rHumanHand";
        lArm.setOffset(new Point3f(0,BODY_HEIGHT*3/4,BODY_THICK/2),new Point3f(HAND_WIDTH,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addChild(lArm);

        //lleg
        Component lleg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        lleg.setEightFace("human_leg");
        lleg.id=id*10+EquipPartType.LLEG.ordinal();
        lleg.name= EquipPartType.LLEG.getName();
        lleg.setOffset(new Point3f(LEG_WIDTH/2,0,BODY_THICK/2),new Point3f(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));
        bodyComponent.addChild(lleg);




        //rleg
        Component rleg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        rleg.setEightFace("human_leg");
        rleg.name= EquipPartType.RLEG.getName();
        rleg.id=id*10 +EquipPartType.RLEG.ordinal();
        rleg.setOffset(new Point3f(BODY_WIDTH-LEG_WIDTH/2,0,BODY_THICK/2),new Point3f(LEG_WIDTH/2,LEG_HEIGHT,LEG_THICK/2));
        bodyComponent.addChild(rleg);

        //head

        Component head= new Component(HEAD_WIDTH,HEAD_HEIGHT,HEAD_THICK);
        head.setEightFace("human_head");
        head.id=id*10+EquipPartType.HEAD.ordinal();
        head.name=EquipPartType.HEAD.getName();
        head.setOffset(new Point3f(BODY_WIDTH/2,BODY_HEIGHT,BODY_THICK/2),new Point3f(HEAD_WIDTH/2,0,HEAD_THICK/2));
        bodyComponent.addChild(head);

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

}
