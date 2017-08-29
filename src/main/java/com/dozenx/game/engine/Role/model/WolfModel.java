package com.dozenx.game.engine.Role.model;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.BoneBlock;
import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.command.EquipPartType;
import com.dozenx.game.engine.element.bean.Component;

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
        BoneBlock bodyShape = (BoneBlock)TextureManager.getShape("wolf_body");
        rootComponent =
        new Component(bodyShape);

        int id =role.getId();
        rootComponent.id=id*10+EquipPartType.BODY.ordinal();


        rootComponent.name = bodyShape.getName();
        //rootComponent.setEightFace("wolf_body");
        List<BoneBlock> shapes = TextureManager.shapeGroups.get("wolf");

        //Component root =  null;
       /* for(Shape shape : shapes){
            if(shape.getParent().equals("root")){
                root = new Component(shape);
            }
        }*/
        for(BoneBlock shape : shapes){
            if(shape.getParent().equals("root")){
                continue;
            }

            this.addChild(rootComponent,shape );
        }






    }


}
