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
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glapp.GLCam;
import glapp.GLCamera;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import sun.rmi.runtime.Log;

import javax.vecmath.Point3f;
import javax.vecmath.Vector2f;
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



                GL_Matrix translateMatrix=GL_Matrix.translateMatrix(role.getX(), role.getY() , role.getZ());//-BODY_THICK/2
                //GL_Matrix translateMatrix=GL_Matrix.translateMatrix(role.getX()-role.getExecutor().getModel().getRootComponent().width/2, role.getY() + 0.75f, role.getZ()-role.getExecutor().getModel().getRootComponent().thick/2);//-BODY_THICK/2
                //float angle=GL_Vector.angleXZ(this.WalkDir , new GL_Vector(0,0,-1));
                rotateMatrix = GL_Matrix.rotateMatrix(0,-role.getBodyAngle()+3.14f/2/**3.14f/180*/,0);

                rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);

                //rotateMatrix=rotateMatrixGL_Matrix.translateMatrix(-rootComponent.getWidth()/2, rootComponent.getHeight()/2, -rootComponent.getThick()/2);
                //rotateMatrix=GL_Matrix.multiply(rotateMatrix,newtranslateMatrix);
                //.getVao().getVertices()
                //  ShaderManager.livingThingShaderConfig.getVao().getVertices().rewind();
                rotateMatrix=rotateMatrix.multiply(rotateMatrix,GL_Matrix.translateMatrix(-rootComponent.getWidth()/2, 0, -rootComponent.getThick()/2));

                if(this.itemDefinition!=null){
                   this.itemDefinition.getShape().renderShader(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),rotateMatrix);

                 /*   this.itemDefinition.getShape().render(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),
                            role.getX(),role.getY(),role.getZ(),true,true,true,true,true,true);*/
                }else
                rootComponent.renderShader(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),rotateMatrix);


                //渲染头部血条
                Vector2f screenXY= OpenglUtils.wordPositionToXY(ShaderManager.projection,role.getPosition().copyClone().add(new GL_Vector(0,rootComponent.height+1.f,0)),GamingState.getInstance().camera.Position,GamingState.getInstance().camera.ViewDir);
                screenXY.x *= Constants.WINDOW_WIDTH;
                screenXY.y *= Constants.WINDOW_HEIGHT;
                ShaderUtils.draw2dColor(ShaderManager.uifloatShaderConfig.getVao(),ShaderUtils.RGBA_RED,(int)screenXY.x-50,(int)screenXY.y,0,111,11);
                ShaderUtils.printText(role.getName(),(int)screenXY.x,(int)screenXY.y-25,0,22,ShaderUtils.RGBA_WHITE,ShaderManager.uifloatShaderConfig);
                //渲染头部名字
                if(StringUtil.isNotEmpty(role.getName())){

                    //

                    GL_Matrix translateMatrix1 = GL_Matrix.translateMatrix(role.getX(), role.getY() + 2.1f, role.getZ());

                    //
                    translateMatrix1=translateMatrix1.multiply(translateMatrix1,GL_Matrix.translateMatrix(role.getRightVector().x*-0.5f, role.getRightVector().y*-0.5f, role.getRightVector().z*-0.5f));


                    //LogUtil.println(screenXY.toString());
                    GLCamera  cam = GamingState.getInstance().camera;
//                    if(screenXY.x<299 || screenXY.x>310){
//
//
//                        LogUtil.println(cam.getViewDir().toString() + cam.Position.toString()+screenXY.toString());
//                    }


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

       // <vector x=0.91580325 y=-0.052335985 z=0.3982027><vector x=2.8404973 y=2.209344 z=17.636257>(281.3048, 478.55573)



    }

    public static void main(String[] args){
        //首先从人物的位置推算出镜头的位置
        GL_Vector playerPos = new GL_Vector(6.54415f,1,19.13263f);
        GL_Vector cameraPosition =new GL_Vector();
        GL_Vector viewDir = new GL_Vector(0.41993842f,-0.112420924f,0.9005629f);
        Switcher.CAMERA_2_PLAYER=5;
        GL_Vector camera_pos = GL_Vector.add(playerPos,
                GL_Vector.multiply(viewDir, (-1 * Switcher.CAMERA_2_PLAYER)));
        camera_pos.y+=1;

        //ViewDir = {glmodel.GL_Vector@4412}"<vector x=0.1894041 y=-0.12108784 z=0.9744043>"
         GL_Matrix projection = GL_Matrix.perspective3(45, (Constants.WINDOW_WIDTH) / (Constants.WINDOW_HEIGHT), 1f, 1000.0f);

        Vector2f screenXY= OpenglUtils.wordPositionToXY(projection,playerPos,camera_pos,viewDir);
        screenXY.x*=600;
        screenXY.y*=600;
System.out.print(screenXY);
    }
}
