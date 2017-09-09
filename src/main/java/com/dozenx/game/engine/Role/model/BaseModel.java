package com.dozenx.game.engine.Role.model;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.WearComponent;
import cola.machine.game.myblocks.model.textture.BoneBlock;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.bean.Role;
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

import javax.vecmath.Point3f;
import javax.vecmath.Vector4f;

/**
 * Created by luying on 17/3/5.
 */
public class BaseModel implements Model   {
    public BaseModel(Role role ){
        this.role = role;
    }
    public ItemDefinition itemDefinition;
    public Component rootComponent = new Component();
    Role role ;


    public void clearAddChild(Component parent,int type ,String name,ItemBean itemBean) {
        if(parent==null){
            LogUtil.err("parent node is null");
        }
        Component shoe = parent.findChild(name);
        if (shoe == null) {
            if (itemBean == null||itemBean.itemDefinition == null) {
                return;
            } else {
                parent.children.clear();
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(addItemToComponent(type ,itemBean));
                //changeProperty();
            }
        } else {

            if (itemBean == null||itemBean.itemDefinition == null) {
                //删除shoe节点
                parent.children.clear();
            } else {
                parent.children.clear();

                parent.addChild(addItemToComponent(type,itemBean));
                //changeProperty();
            }
        }
    }
    public void addChild(Component parent,int type ,String name,ItemBean itemBean) {
        if(parent==null){
            LogUtil.err("parent node is null");
        }
        Component shoe = parent.findChild(name);
        if (shoe == null) {
            if (itemBean == null||itemBean.itemDefinition == null) {
                return;
            } else {

                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(addItemToComponent(type ,itemBean));
                //changeProperty();
            }
        } else {

            if (itemBean == null||itemBean.itemDefinition == null) {
                //删除shoe节点
                parent.removeChild(shoe);
            } else {
                parent.removeChild(shoe);

                parent.addChild(addItemToComponent(type,itemBean));
                //changeProperty();
            }
        }
    }
    public Component addItemToComponent(int type ,ItemBean itemBean){

        BaseBlock baseBlock = itemBean.getItemDefinition().getShape();
        if (baseBlock == null) {
            LogUtil.err("load shape from itemDefinition:" + baseBlock.getName() + "failed");

        }
        rootComponent.belongTo = type;

        Component component = new WearComponent(baseBlock.getWidth(), baseBlock.getHeight(), baseBlock.getThick());

        if(type==component.hand){
            component.belongTo = component.hand;
        }
        //component.setShape(itemCfg.getShape());
        component.setItem(itemBean);
        component.name = itemBean.getItemDefinition().getName();
        if(baseBlock instanceof  BoneBlock){
            BoneBlock boneBlock = (BoneBlock) baseBlock;
            component.setOffset(new Point3f(boneBlock.getP_posi_x(), boneBlock.getP_posi_y(), boneBlock.getP_posi_z()), new Point3f(boneBlock.getC_posi_x(), boneBlock.getC_posi_y(), boneBlock.getC_posi_z()));

        }

        return component;
    }



    public void addChild(Component parent,BoneBlock shape) {
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
                if(this.itemDefinition!=null){
                   this.itemDefinition.getShape().renderShader(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),rotateMatrix);

                 /*   this.itemDefinition.getShape().render(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),
                            role.getX(),role.getY(),role.getZ(),true,true,true,true,true,true);*/
                }else
                rootComponent.build(ShaderManager.livingThingShaderConfig,rotateMatrix);
                //渲染头部名字
                if(StringUtil.isNotEmpty(role.getName())){

                    //
                    GL_Matrix translateMatrix1 = GL_Matrix.translateMatrix(role.getX(), role.getY() + 3.5f, role.getZ());
                    float angle = /*(float)(Math.PI)+*/-GamingState.player.getHeadAngle()-3.14f/2;
                    GL_Matrix rotateMatrix1 = GL_Matrix.rotateMatrix(0,angle/**3.14f/180,0*/,0);

                    rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);

                    // rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);
                    rotateMatrix1=GL_Matrix.multiply(rotateMatrix1,GL_Matrix.translateMatrix(0, 0,0));

                    ShaderUtils.draw3dText(role.getName(), rotateMatrix1, 12, new Vector4f(1, 1, 1, 1), ShaderManager.livingThingShaderConfig);
                   // ShaderUtils.draw3dColor(P1,P2,P6,P5,rotateMatrix,new GL_Vector(0,0,1f),color,floatBuffer, config);
                    ShaderUtils.draw3dColor(
                            
                             new GL_Vector(0,-0.25f,0), new GL_Vector(this.role.nowHP*3f/300,-0.25f,0),
                            new GL_Vector(this.role.nowHP*3f/300,0,0), new GL_Vector(0,0,0),rotateMatrix1, new GL_Vector(0,0,1),  new GL_Vector(1,0,0),ShaderManager.livingThingShaderConfig.getVao().getVertices(),ShaderManager.livingThingShaderConfig);


                }

            }else{

            }
    }
    
    

    @Override
    public void build(ShaderConfig config, Vao vao, int x, int y, int z) {

    }
}
