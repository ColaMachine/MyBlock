package com.dozenx.game.engine.Role.model;

import cola.machine.game.myblocks.engine.modes.GamingState;
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

/**
 * Created by luying on 17/3/5.
 */
public class BaseModel implements Model   {
    public BaseModel(Role role ){
        this.role = role;
    }
    public Component rootComponent = new Component();
    Role role ;

    public void addChild(Component parent,String name,ItemBean itemBean) {
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

                component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
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

                component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                //changeProperty();
            }
        }
    }

    public void addChild(Component parent,Shape shape) {
        String parentName = shape.getParent();
        if(parent==null){
            LogUtil.err("parent node is null");
        }
        Component parentNode = parent.findChild(parentName);
        if (parentNode == null) {
            return;
        } else {


                Component component = new Component(shape);
                // component.setShape(itemCfg.getShape());
               // component.setItem(itemBean);


                component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parentNode.addChild(component);
                //changeProperty();
            }

    }

    @Override
    public Component getRootComponent() {
        return rootComponent;
    }

    @Override
    public void build(ShaderConfig config, GL_Matrix rotateMatrix) {

            //this.role.getPosition().x+=0.01;
            //GamingState.cameraChanged.x+=0.01;

            if(Switcher.SHADER_ENABLE){




                GL_Matrix translateMatrix=GL_Matrix.translateMatrix(role.getX(), role.getY() + 0.75f, role.getZ());//-BODY_THICK/2
                //float angle=GL_Vector.angleXZ(this.WalkDir , new GL_Vector(0,0,-1));
                rotateMatrix = GL_Matrix.rotateMatrix(0,-role.getBodyAngle()+3.14f/2/**3.14f/180*/,0);

                rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);

               // GL_Matrix newtranslateMatrix=GL_Matrix.translateMatrix(-rootComponent.getWidth()/2, rootComponent.getHeight()/2, -rootComponent.getThick()/2);
                //rotateMatrix=GL_Matrix.multiply(rotateMatrix,newtranslateMatrix);
                //.getVao().getVertices()
                //  ShaderManager.livingThingShaderConfig.getVao().getVertices().rewind();
                rootComponent.build(ShaderManager.livingThingShaderConfig,rotateMatrix);
                //渲染头部名字
                if(StringUtil.isNotEmpty(role.getName())){

                    //
                    GL_Matrix translateMatrix1 = GL_Matrix.translateMatrix(role.getX(), role.getY() + 3.5f, role.getZ());
                    float angle = /*(float)(Math.PI)+*/-GamingState.player.getHeadAngle()-3.14f/2;
                    GL_Matrix rotateMatrix1 = GL_Matrix.rotateMatrix(0,angle/**3.14f/180,0*/,0);

                    rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);

                    // rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);
                    rotateMatrix1=GL_Matrix.multiply(rotateMatrix1,GL_Matrix.translateMatrix(-2f, 0,0));

                    ShaderUtils.draw3dText(role.getName(), rotateMatrix1, 24, new Vector4f(1, 1, 1, 1), ShaderManager.livingThingShaderConfig);



                }

            }else{

            }
    }

    @Override
    public void build(ShaderConfig config, Vao vao, int x, int y, int z) {

    }
}
