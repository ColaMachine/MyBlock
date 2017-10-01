package com.dozenx.game.engine.element.bean;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.textture.BoneBlock;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.item.bean.ShapeType;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.util.FloatBufferWrap;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Point3f;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 16/7/23.
 * 2017-04-07 22:59:08移入当前element 目录 是希望她发挥更大左右 她应该是世界一切物体的本源 有点元素的味道
 */
public class Component {
    public static int body =1;
    public static int hand =2;
    public static int drop =3;
    public static int place=4;
    public static int bag =5;
    public BaseBlock block ;
    public int belongTo = 0 ;//private int belongTo = 2; //1 身上 2 手上 3丢弃 4安置 5 背包
    public ItemBean itemBean;
    public  ShapeType shapeType;    //形状类型
    public Point3f parentLocation =new Point3f();   //父亲节点的位置

    public Point3f childLocation =new Point3f();    //子节点对应的位置
     public int id;      //每个元素都有唯一id
     public  String name;    //适用的名称
   /* public TextureInfo front; //如果是盒状的话有四面 其实应该纳入什么模型具体对象中的
    public TextureInfo back;
    public TextureInfo top;
    public TextureInfo left;
    public TextureInfo right;
    public TextureInfo bottom;*/

    public float rotateX;
    public float rotateY;
    public float rotateZ;
    public GL_Vector offsetPosition=new GL_Vector(0,0,0);
    public GL_Vector P1;
    public GL_Vector P2;
    public  GL_Vector P3;
    public  GL_Vector P4;
    public GL_Vector P5;
    public GL_Vector P6;
    public GL_Vector P7;
    public GL_Vector P8;
   // public List<Connector> connectors =new ArrayList<Connector>();
   public List<Component> children =new ArrayList<Component>();
 /*   public void addConnector(Connector connector){
        connectors.add(connector);
    }*/
 public void setOffset(Point3f parentLocation ,Point3f childLocation ){
     this.parentLocation = parentLocation;
     this.childLocation= childLocation;
 }
    public void addChild(Component component){
        children.add(component);
    }
    public void removeChild(Component component){
        for(int i=children.size()-1;i>=0;i--){
            Component child =  children.get(i);
            if(child == component){
                children.remove(i);
            }

        }
    }
    float width;
    float height;


    float thick;
   // int secnum;
   public Component(){

   }


    public void addChild(BoneBlock shape) {
        String parentName = shape.getParent();

        Component parentNode = this.findChild(parentName);
        if (parentNode == null) {
            return;
        } else {


            Component component = new Component(shape);
            // component.setShape(itemCfg.getShape());
            // component.setItem(itemBean);
            component.rotateX=-30;

            component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
            //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
            parentNode.addChild(component);
            //changeProperty();
        }

    }
    public Component(BoneBlock shape ){
        this(shape.getWidth(),shape.getHeight(),shape.getThick());
        this.name = shape.getName();
        this.parentLocation =new Point3f(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z());
        this.childLocation =new Point3f(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z());
    this.block = shape;
      /*  this.front= shape.getFront();
        this.back= shape.getBack();
        this.left=shape.getLeft();
        this.right=shape.getRight();
        this.top= shape.getTop();
        this.bottom= shape.getBottom();*/
        this.shapeType =ShapeType.BOX;

    }
    public Component(float width,float height,float thick){
        this.width=width;
        this.height=height;
        this.thick=thick;
        this.P1= new GL_Vector(0,0,thick);
        this.P2= new GL_Vector(width,0,thick);
        this.P3= new GL_Vector(width,0,0);
        this.P4= new GL_Vector(0,0,0);
        this.P5= new GL_Vector(0,height,thick);
        this.P6= new GL_Vector(width,height,thick);
        this.P7= new GL_Vector(width,height,0);
        this.P8= new GL_Vector(0,height,0);


    }
    public void addFacesToList(List<int[]> faces, int[] arr){
        int[] face = new int[]{arr[0],arr[1],arr[2],arr[3],arr[0],arr[2]};
        faces.add(face);

    }
    public void addVerticesToList(List<float[]> list ,float[][] vertices,GL_Matrix matrix){

        for(int i=0;i<4;i++){
            GL_Vector vector =new GL_Vector(vertices[i][0],vertices[i][1],vertices[i][2]);
            vector = GL_Matrix.multiply(matrix, vector);
            list.add(new float[]{vector.x,vector.y,vector.z});
        }

    }
    public void addNormalsToList(List<float[]> nromals, GL_Vector gl_vector,GL_Matrix matrix){
        gl_vector = GL_Matrix.multiply(matrix, gl_vector);
        float[] normal = new float[]{gl_vector.x,gl_vector.y,gl_vector.z};

        nromals.add(normal);
        nromals.add(normal);
        nromals.add(normal);
        nromals.add(normal);

    }
    public void addTexcoordsToList(List<float[]> list,TextureInfo ti ){
        list.add(new float[]{ti.minX,ti.minY});
        list.add(new float[]{ti.maxX,ti.minY});
        list.add(new float[]{ti.maxX,ti.maxY});
        list.add(new float[]{ti.minX, ti.maxY});

    }

    /**
     *
     * @param index
     * @param matrix
     * @param vertices
     * @param texcoords
     * @param faces
     * @param normals
     */
    public void getVertices(int index,GL_Matrix matrix,List<float[]> vertices,List<float[] > texcoords,List<int[]> faces,List<float[]> normals){

        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(parentLocation.x, parentLocation.y, parentLocation.z);

        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,0,0);
        rotateMatrix= GL_Matrix.multiply(matrix,translateMatrix);

        if(rotateZ!=0){
            //rotateMatrix= GL_Matrix.rotateMatrix( 0, 0, rotateZ);
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( 0, 0, -rotateZ*3.14f/180));
        }
        if(rotateY!=0){
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( 0, -rotateY*3.14f/180, 0));
        }
        if(rotateX!=0){
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( -rotateX*3.14f/180, 0, 0));

        } //GL11.glTranslatef(-childLocation.x, -childLocation.y, -childLocation.z);
        translateMatrix = GL_Matrix.translateMatrix(-childLocation.x, -childLocation.y, -childLocation.z);
        rotateMatrix= GL_Matrix.multiply(rotateMatrix,translateMatrix);



        /*if(this.block.renderShader();front !=null) {
            this.addVerticesToList(vertices, BoxModel.getFrontVertices(0f, 0f, 0f, width, height, thick), rotateMatrix);
            this.addFacesToList(faces, new int[]{index++, index++, index++, index++});
            this.addNormalsToList(normals, BoxModel.FRONT_DIR, rotateMatrix);
            this.addTexcoordsToList(texcoords, front);
        }
        if(back !=null) {
            this.addVerticesToList(vertices, BoxModel.getBackVertices(0f, 0f, 0f, width, height, thick), rotateMatrix);
            this.addFacesToList(faces, new int[]{index++, index++, index++, index++});
            this.addNormalsToList(normals, BoxModel.BACK_DIR, rotateMatrix);
            this.addTexcoordsToList(texcoords, back);
        }
        if(left !=null) {
            this.addVerticesToList(vertices, BoxModel.getLeftVertices(0f, 0f, 0f, width, height, thick), rotateMatrix);
            this.addFacesToList(faces, new int[]{index++, index++, index++, index++});
            this.addNormalsToList(normals, BoxModel.LEFT_DIR, rotateMatrix);
            this.addTexcoordsToList(texcoords, left);
        }
        if(right !=null) {
            this.addVerticesToList(vertices, BoxModel.getRightVertices(0f, 0f, 0f, width, height, thick), rotateMatrix);
            this.addFacesToList(faces, new int[]{index++, index++, index++, index++});
            this.addNormalsToList(normals, BoxModel.RIGHT_DIR, rotateMatrix);
            this.addTexcoordsToList(texcoords, right);
        }
        if(top !=null) {
            this.addVerticesToList(vertices, BoxModel.getTopVertices(0f, 0f, 0f, width, height, thick), rotateMatrix);
            this.addFacesToList(faces, new int[]{index++, index++, index++, index++});
            this.addNormalsToList(normals, BoxModel.TOP_DIR, rotateMatrix);
            this.addTexcoordsToList(texcoords, top);
        }
        if(bottom !=null) {
            this.addVerticesToList(vertices, BoxModel.getBottomVertices(0f, 0f, 0f, width, height, thick), rotateMatrix);
            this.addFacesToList(faces, new int[]{index++, index++, index++, index++});
            this.addNormalsToList(normals, BoxModel.DOWN_DIR, rotateMatrix);
            this.addTexcoordsToList(texcoords, bottom);
        }*/

        for(int i=0;i<children.size();i++){
            children.get(i).getVertices( index,matrix,vertices, texcoords, faces,normals);
        }

    }

    public void build(ShaderConfig config ,  GL_Matrix matrix){
        FloatBufferWrap floatBuffer = config.getVao().getVertices();
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(parentLocation.x, parentLocation.y, parentLocation.z);

        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,0,0);
        rotateMatrix= GL_Matrix.multiply(matrix,translateMatrix);

        if(rotateZ!=0){
            //rotateMatrix= GL_Matrix.rotateMatrix( 0, 0, rotateZ);
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( 0, 0, -rotateZ*3.14f/180));
        }
        if(rotateY!=0){
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( 0, -rotateY*3.14f/180, 0));
        }
        if(rotateX!=0){
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( -rotateX*3.14f/180, 0, 0));

        } //GL11.glTranslatef(-childLocation.x, -childLocation.y, -childLocation.z);
         translateMatrix = GL_Matrix.translateMatrix(-childLocation.x, -childLocation.y, -childLocation.z);
        rotateMatrix= GL_Matrix.multiply(rotateMatrix,translateMatrix);
        /*if(this.shapeType==ShapeType.ICON){
            if(this.itemBean !=null){
                this.itemBean.getItemDefinition().getItemModel().renderShader( config ,   matrix);
                // return;
            }
        }else*/
        //if(this.shapeType==ShapeType.CAKE){
            if(this.itemBean !=null) {//让我们会议下component 是怎么从一个itemdefintion
                if (this.belongTo == 2) {
                    this.itemBean.getItemDefinition().getItemModel().handModel.build(config, matrix);
                } else if (this.belongTo == 1) {
                    if(this.itemBean.getItemDefinition()!=null && this.itemBean.getItemDefinition().getItemModel()!=null && this.itemBean.getItemDefinition().getItemModel().wearModel!=null){
                        this.itemBean.getItemDefinition().getItemModel().wearModel.build(config, matrix);
                    }

                } else if (this.belongTo == 3) {
                    this.itemBean.getItemDefinition().getItemModel().outdoorModel.build(config, matrix);
                }else{
                    this.itemBean.getItemDefinition().getItemModel().renderShader(config, matrix);
                }
               // this.itemBean.getItemDefinition().getItemModel().renderShader(config, matrix);
                // return;
            //}
            //}
        }else if(this.shapeType==ShapeType.BOX){
            block.renderShader(config,config.getVao(),rotateMatrix);
           /* if(front!=null) {
                // ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P1,P2,P6,P5,new GL_Vector(0,0,1f),front);
                ShaderUtils.draw3dImage(P1,P2,P6,P5,rotateMatrix,new GL_Vector(0,0,1f),front,floatBuffer, config);
            }
            if(back!=null) {
                //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P3,P4,P8,P7,new GL_Vector(0,0,-1f),front);
                ShaderUtils.draw3dImage(P3,P4,P8,P7,rotateMatrix,new GL_Vector(0,0,-1),back,floatBuffer, config);
            }
            if(top!=null) {
                //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P5,P6,P7,P8,new GL_Vector(0,1,0f),front);
                ShaderUtils.draw3dImage(P5,P6,P7,P8,rotateMatrix,new GL_Vector(0,1,0),top,floatBuffer, config);
            }

            if(bottom!=null) {
                ShaderUtils.draw3dImage(P4,P3,P2,P1,rotateMatrix,new GL_Vector(0,-1,0),bottom,floatBuffer, config);
            }
            if(left!=null) {
                ShaderUtils.draw3dImage(P2,P3,P7,P6,rotateMatrix,new GL_Vector(-1,0,0f),left,floatBuffer, config);
            }
            if(right!=null) {
                ShaderUtils.draw3dImage(P4,P1,P5,P8,rotateMatrix,new GL_Vector(1,0,0),right,floatBuffer, config);
            }*/
        }


       for(int i=0;i<children.size();i++){
            children.get(i).build(config,rotateMatrix);
        }

    }
/*
    public void render(){*//*try{
        Util.checkGLError();}catch (Exception e ){
        e.printStackTrace();
        LogUtil.println(e.getMessage());
        throw e;
    }
*//*
        //GL11. glEnable(GL11.GL_DEPTH_TEST);
        GL11.glTranslatef(parentLocation.x, parentLocation.y, parentLocation.z);
       *//* try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*//*
        if(rotateZ!=0){
            GL11.glRotatef(rotateZ, 0, 0, 1);
        }
        if(rotateX!=0){
            GL11.glRotatef(rotateX, 1, 0, 0);
        }
        //GL11.glRotatef(child.rotateX, child.rotateY, 90, 0);
        GL11.glTranslatef(-childLocation.x, -childLocation.y, -childLocation.z);
        *//*try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*//*

        if(this.itemBean !=null){
            this.itemBean.getItemDefinition().getItemModel().render();
           // return;
        }

       // GL11.glBindTexture(GL11.GL_TEXTURE_2D,front.textureHandle);
        if(front!=null) {
            front.bind();
        }else if(left!=null){
            left.bind();
        }
        else if(right!=null){
            right.bind();
        }
        else if(back!=null){
            back.bind();
        }
        else if(top!=null){
            top.bind();
        }else if(bottom!=null){
            bottom.bind();
        }
        *//*try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*//*

        *//*if(this.id.equals("fur_helmet")){
            LogUtil.println("iron_helmet");
        }*//*
        GL11.glTranslatef(offsetPosition.x, offsetPosition.y, offsetPosition.z);
//        GL11.glRotatef(rotateX, rotateY, rotateZ, 0);
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face

        *//*try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }
*//*
        if(front!=null) {


            OpenglUtils.glVertex3fv4rect(P1, P2, P6, P5, front, Constants.FRONT);
        }
        // Back Face
        if(back!=null) {
            OpenglUtils.glVertex3fv4rect(P3, P4, P8, P7, back, Constants.BACK);

        }
        // Top Face
        if(top!=null) {
            OpenglUtils.glVertex3fv4rect(P5, P6, P7, P8, top, Constants.TOP);

        }
        if(bottom!=null) {
            OpenglUtils.glVertex3fv4rect(P4, P3, P2, P1, bottom, Constants.BOTTOM);
        }
        // left face
        if(left!=null) {

            OpenglUtils.glVertex3fv4rect(P2, P3, P7, P8, left, Constants.LEFT);
        }
        // right Face
        if(right!=null) {
            OpenglUtils.glVertex3fv4rect(P4, P1, P5, P8, right, Constants.RIGHT);
        }
        *//*try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*//*
        GL11.glEnd();
        *//*try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*//*
        for(int i=0;i<children.size();i++){
            children.get(i).render();
        }
       *//* try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*//*

//        GL11.glRotatef(-rotateX, -rotateY, -rotateZ, 0);
        GL11.glTranslatef(-offsetPosition.x,-offsetPosition.y,-offsetPosition.z);

        GL11.glTranslatef(childLocation.x, childLocation.y, childLocation.z);
        // GL11.glRotatef(-child.rotateX, -child.rotateY, -90, 0);
        if(rotateX!=0){
            GL11.glRotatef(-rotateX, 1, 0, 0);
        }
        if(rotateZ!=0){
            GL11.glRotatef(-rotateZ, 0, 0, 1);
        }
        GL11.glTranslatef(-parentLocation.x, -parentLocation.y, -parentLocation.z);


    }*/


    public void setImage(String textureInfoName){

    }

    /*public void setEightFace(String name,TextureManager textureManager){

       // this.name =name;
        this.front= textureManager.getTextureInfo(name+"_front");
        this.back= textureManager.getTextureInfo(name+"_back");
        this.left= textureManager.getTextureInfo(name+"_left");
        this.right= textureManager.getTextureInfo(name+"_right");
        this.top= textureManager.getTextureInfo(name+"_top");
        this.bottom= textureManager.getTextureInfo(name+"_bottom");
    }*/
    /*public void setEightFace(String name){
       // this.name =name;
        this.front= TextureManager.getTextureInfo(name+"_front");
        this.back= TextureManager.getTextureInfo(name+"_back");
        this.left= TextureManager.getTextureInfo(name+"_left");
        this.right= TextureManager.getTextureInfo(name+"_right");
        this.top= TextureManager.getTextureInfo(name+"_top");
        this.bottom= TextureManager.getTextureInfo(name+"_bottom");
    }*/
    public ItemDefinition itemDefinition;
    public void setItem(ItemDefinition itemDefinition){
        this.itemDefinition = itemDefinition;
        this.name= itemDefinition.getName();
       // this.setShape123(itemDefinition.getShape());
        // this.front= itemCfgBean.getIcon();
        // this.back= itemCfgBean.getIcon();
        //this.left=itemCfgBean.getIcon();
        //this.right=itemCfgBean.getIcon();
        //this.top= itemCfgBean.getIcon();
        // this.bottom= itemCfgBean.getIcon();
    }
    public void setItem(ItemBean itemBean){
    this.itemBean = itemBean;
        this.name= itemBean.getItemDefinition().getName();
        //this.setShape123(itemDefinition.getShape());
       // this.front= itemCfgBean.getIcon();
       // this.back= itemCfgBean.getIcon();
        //this.left=itemCfgBean.getIcon();
        //this.right=itemCfgBean.getIcon();
        //this.top= itemCfgBean.getIcon();
       // this.bottom= itemCfgBean.getIcon();
    }
   /* public void setShape123(Shape shape){//这个逻辑是错误的 shape 是不应该有itemDefinition属性的
        if(shape.getShapeType()==2){
           // this.itemDefinition= ItemManager.getItemDefinition(shape.getName());
            itemDefinition.getItemModel().init();
        }

       // this.name= shape.getName();
        this.front= shape.getFront();
        this.back= shape.getBack();
        this.left=shape.getLeft();
        this.right=shape.getRight();
        this.top= shape.getTop();
        this.bottom= shape.getBottom();
    }*/
    public void glVertex3fv(GL_Vector p){
        GL11.glVertex3f(p.x,p.y,p.z);
    }
    public Component findChild(String nodeName)  {
        if(this.name.equals(nodeName))
            return this;
        if(children.size()>0){
            for(int i=0;i<children.size();i++){
                if(children.get(i).name.equals(nodeName)){
                    return children.get(i);
                }else{
                    Component child = children.get(i).findChild(nodeName);
                    if(child!=null){
                        return child;
                    }

                }


            }
        }

        return null;

    }
    /*public Component findChild(String nodeName)  {
        if(this.id.equals(nodeName))
            return this;
        if(connectors.size()>0){
            for(int i=0;i<connectors.size();i++){
                if(connectors.get(i).child.id.equals(nodeName)){
                    return connectors.get(i).child;
                }else{
                    Component child = connectors.get(i).child.findChild(nodeName);
                    if(child!=null){
                        return child;
                    }

                }


            }
        }

        return null;

    }*/

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getThick() {
        return thick;
    }

    public void setThick(float thick) {
        this.thick = thick;
    }/*
    public TextureInfo getFront(){
        return front;
    }
    public void setFront(TextureInfo texture){
        this.front=texture;
    }
    public void setBack(TextureInfo texture){
        this.back=texture;
    }
    public void setLeft(TextureInfo texture){
        this.left=texture;
    }

    public void setRight(TextureInfo texture){
        this.right=texture;
    }
    public void setTop(TextureInfo texture){
        this.top=texture;
    }
    public void setBottom(TextureInfo texture){
        this.bottom=texture;
    }*/

}
