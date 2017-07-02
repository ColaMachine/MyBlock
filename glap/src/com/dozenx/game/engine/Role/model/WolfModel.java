package com.dozenx.game.engine.Role.model;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.BodyComponent;
import cola.machine.game.myblocks.model.HandComponent;
import cola.machine.game.myblocks.model.WearComponent;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.command.EquipPartType;
import com.dozenx.game.engine.element.bean.Component;
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
import java.util.List;

/**
 * Created by luying on 17/3/5.
 */
public class WolfModel extends BaseModel{
    //狼有好几种动作 摇尾巴

    //伸懒腰

    //嗅探

    //奔跑

    //撕咬

    //拖拽

    public WolfModel(Role role){
        super(role);
        Shape bodyShape = TextureManager.getShape("wolf_body");
        rootComponent =
        new Component(bodyShape);

        int id =role.getId();
        rootComponent.id=id*10+EquipPartType.BODY.ordinal();


        rootComponent.name = bodyShape.getName();
        //rootComponent.setEightFace("wolf_body");
        List<Shape > shapes = TextureManager.shapeGroups.get("wolf");

        //Component root =  null;
       /* for(Shape shape : shapes){
            if(shape.getParent().equals("root")){
                root = new Component(shape);
            }
        }*/
        for(Shape shape : shapes){
            if(shape.getParent().equals("root")){
                continue;
            }

            this.addChild(rootComponent,shape );
        }






    }


}
