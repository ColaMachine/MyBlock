package com.dozenx.game.engine.Role.model;

import cola.machine.game.myblocks.engine.modes.GamingState;
import com.dozenx.game.engine.element.bean.Component;
import cola.machine.game.myblocks.model.HandComponent;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.command.EquipPartType;
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

import javax.vecmath.Point3f;
import javax.vecmath.Vector4f;

/**
 * Created by luying on 17/3/5.
 */
public interface  Model {


   /* protected  float HAND_HEIGHT=1.5f;
    protected float HAND_WIDTH=0.5f;
    protected float HAND_THICK=0.5f;




    protected float LEG_HEIGHT=1.5f;
    protected float LEG_WIDTH=0.5f;
    protected  float LEG_THICK=0.5f;

    protected float HEAD_HEIGHT=1f;
    protected float HEAD_WIDTH=1f;
    protected  float HEAD_THICK=1f;*/

    //public Component bodyComponent = new BodyComponent(BODY_WIDTH,BODY_HEIGHT,BODY_THICK);


    public Component getRootComponent();

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

    // int vaoId;
    //int trianglesCount =0;


    public void build(ShaderConfig config ,GL_Matrix rotateMatrix);

    public void build(ShaderConfig config, Vao vao , int x, int y, int z);

}
