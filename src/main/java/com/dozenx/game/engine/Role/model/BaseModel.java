package com.dozenx.game.engine.Role.model;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.BoneRotateImageBlock;
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
    public BoneRotateImageBlock rootComponent = new BoneRotateImageBlock();
    Role role ;


    public void clearAddChild(BoneRotateImageBlock parent,int type ,String name,ItemBean itemBean) {
        if(parent==null){
            LogUtil.err("parent node is null");
        }
       // parent.children.clear();
        //一个很明确的是问题是如果body添加了一个装备 name 是不能清除所有子节点的
        if(parent == null){
            LogUtil.err("parent not found ");
        }
        BoneRotateImageBlock shoe = parent.findChild(name);

        if (shoe == null) {
            if (itemBean == null||itemBean.itemDefinition == null) {
                return;
            } else {
               // parent.children.clear();//这里会把body的子元素都清理掉的
                for(BoneRotateImageBlock block: parent.children){
                    if(block.getName().equals(name)){
                        block.removeChild(block);
                        break;
                    }
                }
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(addItemToComponent(name,type ,itemBean));
                //changeProperty();
            }
        } else {

            if (itemBean == null||itemBean.itemDefinition == null) {
                //删除shoe节点
                for(BoneRotateImageBlock block: parent.children){
                    if(block.getName().equals(name)){
                        block.removeChild(block);
                        break;
                    }
                }

            } else {
                for(BoneRotateImageBlock block: parent.children){
                    if(block.getName().equals(name)){
                        block.removeChild(block);
                        break;
                    }
                }
              //  parent.children.clear();

                parent.addChild(addItemToComponent(name,type,itemBean));
                //changeProperty();
            }
        }
    }
    public void addChild(BoneRotateImageBlock parent,int type ,String name,ItemBean itemBean) {
        if(parent==null){
            LogUtil.err("parent node is null");
        }
        BoneRotateImageBlock shoe = parent.findChild(name);
        if (shoe == null) {
            if (itemBean == null||itemBean.itemDefinition == null) {
                return;
            } else {

                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(addItemToComponent(name,type ,itemBean));
                //changeProperty();
            }
        } else {

            if (itemBean == null||itemBean.itemDefinition == null) {
                //删除shoe节点
                parent.removeChild(shoe);
            } else {
                parent.removeChild(shoe);

                parent.addChild(addItemToComponent(name,type,itemBean));
                //changeProperty();
            }
        }
    }
    public Component addItemToComponent(String name,int type ,ItemBean itemBean){

        BaseBlock baseBlock = itemBean.getItemDefinition().getShape();
        if (baseBlock == null) {
            LogUtil.err("load shape from itemDefinition:" + baseBlock.getName() + "failed");

        }
       // rootComponent.belongTo = type;

        Component component = new WearComponent(baseBlock.getWidth(), baseBlock.getHeight(), baseBlock.getThick());

        if(type==component.hand){
            component.belongTo = component.hand;
        }
        //component.setShape(itemCfg.getShape());
        component.setItem(itemBean);
        component.name =name;// itemBean.getItemDefinition().getName();
        if(baseBlock instanceof  BoneBlock){
            BoneBlock boneBlock = (BoneBlock) baseBlock;
            component.setOffset(boneBlock.parentPosition, boneBlock.childPosition);

        }

        return component;
    }



    public void addChild(BoneRotateImageBlock parent,BoneBlock shape) {
        String parentName = shape.getParent();
        if(parent==null){
            LogUtil.err("parent node is null");
        }
        BoneRotateImageBlock parentNode = parent.findChild(parentName);
        if (parentNode == null) {
            return;
        } else {


                Component component = new Component(shape);
                // component.setShape(itemCfg.getShape());
               // component.setItem(itemBean);

            component.setOffset(shape.parentPosition, shape.childPosition);
                //component.setOffset(new GL_Vector(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new GL_Vector(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parentNode.addChild(component);
                //changeProperty();
            }

    }

    @Override
    public BoneRotateImageBlock getRootComponent() {
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
                rootComponent.renderShader(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),rotateMatrix);
                //渲染头部名字
                if(StringUtil.isNotEmpty(role.getName())){

                    //
                    GL_Matrix translateMatrix1 = GL_Matrix.translateMatrix(role.getX(), role.getY() + 2.1f, role.getZ());

                    //
                    translateMatrix1=translateMatrix1.multiply(translateMatrix1,GL_Matrix.translateMatrix(role.getRightVector().x*-0.5f, role.getRightVector().y*-0.5f, role.getRightVector().z*-0.5f));

                    float angle = /*(float)(Math.PI)+*/-GamingState.player.getHeadAngle()-3.14f/2;
                    GL_Matrix rotateMatrix1 = GL_Matrix.rotateMatrix(0,angle/**3.14f/180,0*/,0);

                    rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);

                    // rotateMatrix1=GL_Matrix.multiply(translateMatrix1,rotateMatrix1);
                    rotateMatrix1=GL_Matrix.multiply(rotateMatrix1,GL_Matrix.translateMatrix(0, 0,0));

                    ShaderUtils.draw3dText(role.getName(), rotateMatrix1, 12, Constants.RGBA_WHITE, ShaderManager.livingThingShaderConfig);
                   // ShaderUtils.draw3dColor(P1,P2,P6,P5,rotateMatrix,new GL_Vector(0,0,1f),color,floatBuffer, config);
                    ShaderUtils.draw3dColorReactWithMatrix(
                            ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),rotateMatrix1,
                             new GL_Vector(0,-0.2f,0), new GL_Vector(this.role.nowHP*3f/300,-0.2f,0),
                            new GL_Vector(this.role.nowHP*3f/300,0,0), new GL_Vector(0,0,0), new GL_Vector(0,0,1),  Constants.RED);


                }

            }else{

            }
    }
    
    

    @Override
    public void build(ShaderConfig config, Vao vao, int x, int y, int z) {

    }
}
