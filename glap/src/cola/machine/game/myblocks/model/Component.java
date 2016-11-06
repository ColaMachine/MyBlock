package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.ItemDefinition;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Point3f;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 16/7/23.
 */
public class Component {

    Point3f parentLocation =new Point3f();
    public void setOffset(Point3f parentLocation ,Point3f childLocation ){
        this.parentLocation = parentLocation;
        this.childLocation= childLocation;
    }
    Point3f childLocation =new Point3f();
    public String id;

    TextureInfo front;
    TextureInfo back;
    TextureInfo top;
    TextureInfo left;
    TextureInfo right;
    TextureInfo bottom;

    public float rotateX;
    public float rotateY;
    public float rotateZ;
   GL_Vector offsetPosition=new GL_Vector(0,0,0);
    GL_Vector P1;
    GL_Vector P2;
    GL_Vector P3;
    GL_Vector P4;
    GL_Vector P5;
    GL_Vector P6;
    GL_Vector P7;
    GL_Vector P8;
   // public List<Connector> connectors =new ArrayList<Connector>();
   public List<Component> children =new ArrayList<Component>();
 /*   public void addConnector(Connector connector){
        connectors.add(connector);
    }*/

    public void addChild(Component component){
        children.add(component);
    }
    float width;
    float height;

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
    }

    float thick;
   // int secnum;
    float secdis;
    float angle;
    float banjin;
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
        secdis= height/secdis;
        arr1 = new GL_Vector[secnum];
        arr2 = new GL_Vector[secnum];
        arr3 = new GL_Vector[secnum];
        arr4 = new GL_Vector[secnum];

    }



    GL_Vector[] arr1;
    GL_Vector[] arr2;
    GL_Vector[] arr3;
    GL_Vector[] arr4;

    GL_Vector[] points;
    GL_Vector[] newPoints;
    public void bend(int degree, int secnum){
this.secnum =secnum;
        float degreeSec  = degree/secnum;
        GL_Vector[] axis = new GL_Vector[secnum+1];

        GL_Vector[] axisAfterBend = new GL_Vector[secnum+1];
        for(int i=0;i<secnum+1;i++){
            axis[i]=new GL_Vector();
            axisAfterBend[i]=new GL_Vector();
        }
        axis[0]=new GL_Vector((P1.x+P2.x)/2,P1.y,(P7.z+P5.z)/2);
        axisAfterBend[0].copy(axis[0]);
        axis[secnum]=new GL_Vector((P5.x+P6.x)/2,P1.y,(P7.z+P5.z)/2);
        axisAfterBend[secnum].copy(axis[secnum]);


         points =new GL_Vector[(secnum+1)*4];
         newPoints =new GL_Vector[(secnum+1)*4];
        newPoints[0] = points[0] =P1;
        newPoints[1] =points[1]=P2;
        newPoints[2] =points[2]=P3;
        newPoints[3] =points[3] =P4;

        float secY = (P5.y-P1.y)/secnum;
        for(int i=1;i<=secnum;i++){
            axis[i].set(axis[i-1].x, axis[i-1].y+secY,axis[i-1].z);
            axisAfterBend[i].copy(axis[i]);
        }

        //相对位置 vector

        for(int i=1;i<secnum;i++){

            points[4*i] =new GL_Vector(P1.x,P1.y+secY*i,P1.z);
            points[4*i+1]=new GL_Vector(P2.x,P2.y+secY*i,P2.z);
            points[4*i+2]=new GL_Vector(P3.x,P3.y+secY*i,P3.z);
            points[4*i+3] =new GL_Vector(P4.x,P4.y+secY*i,P4.z);


        }
        points[4*secnum+0]=P5;
        points[4*secnum+1] =P6;
        points[4*secnum+2]=P7;
        points[4*secnum+3] =P8;

        //计算弯曲后的位置
        for(int i=1 ;i<=secnum;i++){// 1 2   如果分成两段那么有 0 1 2
            GL_Vector vector = GL_Vector.sub(axis[i],axis[i-1]);
            GL_Vector rotationVector = GL_Vector.rotateWithZ(vector,degreeSec*i);
            GL_Vector newPoint = GL_Vector.add(axisAfterBend[i-1],rotationVector);
            axisAfterBend [i]= newPoint;



            for(int j=0 ;j<4;j++){//计算四个周点的新位置
                newPoints[4*i+j]=getNewPointAfterBend(points[i*4+j],axis[i],axisAfterBend[i],degreeSec*i);
            }



        }

        LogUtil.println("hello");



    }

    /**
     *
     * @param oldPotin 原来的点
     * @param oldaxis 原来的轴心
     * @param newAxis 新的轴心
     * @param degree 旋转的角度
     * @return
     */
    public GL_Vector getNewPointAfterBend(GL_Vector oldPotin,GL_Vector oldaxis,GL_Vector newAxis,float degree){
        GL_Vector oldVector = GL_Vector.sub(oldPotin,oldaxis);
        GL_Vector newVector = GL_Vector.rotateWithZ(oldVector,degree);
        GL_Vector newPoint = GL_Vector.add(newAxis,newVector);
        return newPoint;
    }
    public void renderShader(FloatBuffer floatBuffer, GL_Matrix matrix){
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(parentLocation.x, parentLocation.y, parentLocation.z);

        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,0,0);
        rotateMatrix= GL_Matrix.multiply(matrix,translateMatrix);

        if(rotateZ!=0){
            //rotateMatrix= GL_Matrix.rotateMatrix( 0, 0, rotateZ);
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( 0, 0, rotateZ));
        }
        if(rotateX!=0){
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( rotateX, 0, 0));

        } //GL11.glTranslatef(-childLocation.x, -childLocation.y, -childLocation.z);
         translateMatrix = GL_Matrix.translateMatrix(-childLocation.x, -childLocation.y, -childLocation.z);
        rotateMatrix= GL_Matrix.multiply(rotateMatrix,translateMatrix);
        if(front!=null) {
            GL_Vector.glVertex3fv4triangle(P1,P2,P6,P5,rotateMatrix,new GL_Vector(0,0,1f),front,floatBuffer);
        }
        if(back!=null) {
            GL_Vector.glVertex3fv4triangle(P3,P4,P8,P7,rotateMatrix,new GL_Vector(0,0,1),back,floatBuffer);
        }
        if(top!=null) {
            GL_Vector.glVertex3fv4triangle(P5,P6,P7,P8,rotateMatrix,new GL_Vector(0,1,0),top,floatBuffer);
        }

        if(bottom!=null) {
            GL_Vector.glVertex3fv4triangle(P4,P3,P2,P1,rotateMatrix,new GL_Vector(0,-1,0),bottom,floatBuffer);
        }
        if(left!=null) {
            GL_Vector.glVertex3fv4triangle(P2,P3,P7,P6,rotateMatrix,new GL_Vector(0,0,1f),left,floatBuffer);
        }
        if(right!=null) {
            GL_Vector.glVertex3fv4triangle(P4,P1,P5,P8,rotateMatrix,new GL_Vector(0,0,-1),right,floatBuffer);
        }
       for(int i=0;i<children.size();i++){
            children.get(i).renderShader(floatBuffer,rotateMatrix);
        }

    }

    public void render(){

        GL11. glEnable(GL11.GL_DEPTH_TEST);
        GL11.glTranslatef(parentLocation.x, parentLocation.y, parentLocation.z);
        if(rotateZ!=0){
            GL11.glRotatef(rotateZ, 0, 0, 1);
        }
        if(rotateX!=0){
            GL11.glRotatef(rotateX, 1, 0, 0);
        }
        //GL11.glRotatef(child.rotateX, child.rotateY, 90, 0);
        GL11.glTranslatef(-childLocation.x, -childLocation.y, -childLocation.z);


        if(this.itemDefinition !=null){
            this.itemDefinition.render();
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


        /*if(this.id.equals("fur_helmet")){
            LogUtil.println("iron_helmet");
        }*/
        GL11.glTranslatef(offsetPosition.x, offsetPosition.y, offsetPosition.z);
//        GL11.glRotatef(rotateX, rotateY, rotateZ, 0);
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face
        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);


        if(front!=null) {
            GL11.glTexCoord2f(front.minX, front.minY);
            glVertex3fv(P1);    // Bottom Left ǰ����
            GL11.glTexCoord2f(front.maxX, front.minY);
            glVertex3fv(P2);    // Bottom Right ǰ����
            GL11.glTexCoord2f(front.maxX, front.maxY);
            glVertex3fv(P6);    // Top Right ǰ����
            GL11.glTexCoord2f(front.minX, front.maxY);
            glVertex3fv(P5);    // Top Left	ǰ����
        }
        // Back Face
        if(back!=null) {
            GL11.glNormal3f(0.0f, 0.0f, -1.0f);
            GL11.glTexCoord2f(back.minX, back.minY);
            glVertex3fv(P3);    // Bottom Right ������
            GL11.glTexCoord2f(back.maxX, back.minY);
            glVertex3fv(P4);    // Top Right ������
            GL11.glTexCoord2f(back.maxX, back.maxY);
            glVertex3fv(P8);    // Top Left ������
            GL11.glTexCoord2f(back.minX, back.maxY);
            glVertex3fv(P7);    // Bottom Left ������
        }
        // Top Face
        if(top!=null) {
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glTexCoord2f(top.minX, top.minY);
            glVertex3fv(P5);    // Top Left
            GL11.glTexCoord2f(top.maxX, top.minY);
            glVertex3fv(P6);// Bottom Left
            GL11.glTexCoord2f(top.maxX, top.maxY);
            glVertex3fv(P7);    // Bottom Right
            GL11.glTexCoord2f(top.minX, top.maxY);
            glVertex3fv(P8);    // Top Right
        }
        if(bottom!=null) {
            // Bottom Face
            GL11.glNormal3f(0.0f, -1.0f, 0.0f);
            GL11.glTexCoord2f(bottom.minX, bottom.minY);
            glVertex3fv(P4);    // Top Right ������
            GL11.glTexCoord2f(bottom.maxX, bottom.minY);
            glVertex3fv(P3);    // Top Left ������
            GL11.glTexCoord2f(bottom.maxX, bottom.maxY);
            glVertex3fv(P2);    // Bottom Left ǰ����
            GL11.glTexCoord2f(bottom.minX, bottom.maxY);
            glVertex3fv(P1);// Bottom Right ǰ����
        }
        // left face
        if(left!=null) {
            GL11.glNormal3f(1.0f, 0.0f, 0.0f);
            GL11.glTexCoord2f(left.minX, right.minY);
            glVertex3fv(P2);    // Bottom Right ������
            GL11.glTexCoord2f(left.maxX, right.minY);
            glVertex3fv(P3);        // Top Right ������
            GL11.glTexCoord2f(left.maxX, right.maxY);
            glVertex3fv(P7);        // Top Left ǰ����
            GL11.glTexCoord2f(left.minX, right.maxY);
            glVertex3fv(P6);    // Bottom Left ǰ����
        }
        // right Face
        if(right!=null) {
            GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
            GL11.glTexCoord2f(right.minX, left.minY);
            glVertex3fv(P4);    // Bottom Left ������
            GL11.glTexCoord2f(right.maxX, left.minY);
            glVertex3fv(P1);    // Bottom Right ǰ����
            GL11.glTexCoord2f(right.maxX, left.maxY);
            glVertex3fv(P5);    // Top Right ǰ����
            GL11.glTexCoord2f(right.minX, left.maxY);
            glVertex3fv(P8);// Top Left
        }
        GL11.glEnd();
        for(int i=0;i<children.size();i++){
            children.get(i).render();
        }


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


    }
    int secnum;
    public void renderBend(){

//        GL11.glRotatef(rotateX, rotateY, rotateZ, 0);
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face





            for(int i=0;i<secnum;i++){
                GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
                GL11.glTexCoord2f(front.minX, front.minY);
                glVertex3fv(newPoints[0+4*i]);    // Bottom Left ǰ����
                GL11.glTexCoord2f(front.maxX, front.minY);
                glVertex3fv(newPoints[1+4*i]);    // Bottom Right ǰ����
                GL11.glTexCoord2f(front.maxX, front.maxY);
                glVertex3fv(newPoints[5+4*i]);    // Top Right ǰ����
                GL11.glTexCoord2f(front.minX, front.maxY);
                glVertex3fv(newPoints[4+4*i]);
            }


        // Back Face

            for(int i=0;i<secnum;i++){
                GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
                GL11.glTexCoord2f(front.minX, front.minY);
                glVertex3fv(newPoints[2+4*i]);    // Bottom Left ǰ����
                GL11.glTexCoord2f(front.maxX, front.minY);
                glVertex3fv(newPoints[3+4*i]);    // Bottom Right ǰ����
                GL11.glTexCoord2f(front.maxX, front.maxY);
                glVertex3fv(newPoints[7+4*i]);    // Top Right ǰ����
                GL11.glTexCoord2f(front.minX, front.maxY);
                glVertex3fv(newPoints[6+4*i]);
            }


        // left face


            for(int i=0;i<secnum;i++){
                GL11.glNormal3f(1.0f, 0.0f, 0.0f);
                GL11.glTexCoord2f(front.minX, front.minY);
                glVertex3fv(newPoints[1+4*i]);    // Bottom Left ǰ����
                GL11.glTexCoord2f(front.maxX, front.minY);
                glVertex3fv(newPoints[2+4*i]);    // Bottom Right ǰ����
                GL11.glTexCoord2f(front.maxX, front.maxY);
                glVertex3fv(newPoints[6+4*i]);    // Top Right ǰ����
                GL11.glTexCoord2f(front.minX, front.maxY);
                glVertex3fv(newPoints[5+4*i]);
            }

        // right Face

            for(int i=0;i<secnum;i++){
                GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
                GL11.glTexCoord2f(front.minX, front.minY);
                glVertex3fv(newPoints[3+4*i]);    // Bottom Left ǰ����
                GL11.glTexCoord2f(front.maxX, front.minY);
                glVertex3fv(newPoints[0+4*i]);    // Bottom Right ǰ����
                GL11.glTexCoord2f(front.maxX, front.maxY);
                glVertex3fv(newPoints[4+4*i]);    // Top Right ǰ����
                GL11.glTexCoord2f(front.minX, front.maxY);
                glVertex3fv(newPoints[7+4*i]);
            }
        GL11.glEnd();



    }
    public void setOffsetPosition(GL_Vector vector){

        this.offsetPosition.x= vector.x;
        this.offsetPosition.y= vector.y;
        this.offsetPosition.z= vector.z;


    }

    public void setImage(String textureInfoName){

    }
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
    }
    public void setEightFace(String name,TextureManager textureManager){
        this.id= name;
        this.front= textureManager.getTextureInfo(name+"_front");
        this.back= textureManager.getTextureInfo(name+"_back");
        this.left= textureManager.getTextureInfo(name+"_left");
        this.right= textureManager.getTextureInfo(name+"_right");
        this.top= textureManager.getTextureInfo(name+"_top");
        this.bottom= textureManager.getTextureInfo(name+"_bottom");
    }
    public void setEightFace(String name){
        this.id= name;
        this.front= TextureManager.getTextureInfo(name+"_front");
        this.back= TextureManager.getTextureInfo(name+"_back");
        this.left= TextureManager.getTextureInfo(name+"_left");
        this.right= TextureManager.getTextureInfo(name+"_right");
        this.top= TextureManager.getTextureInfo(name+"_top");
        this.bottom= TextureManager.getTextureInfo(name+"_bottom");
    }
    public ItemDefinition itemDefinition;
    public void setItem(ItemDefinition itemDefinition){
    this.itemDefinition = itemDefinition;
        this.id= itemDefinition.getName();
       // this.front= itemCfgBean.getIcon();
       // this.back= itemCfgBean.getIcon();
        //this.left=itemCfgBean.getIcon();
        //this.right=itemCfgBean.getIcon();
        //this.top= itemCfgBean.getIcon();
       // this.bottom= itemCfgBean.getIcon();
    }
    public void setShape(Shape shape){

        this.id= shape.getName();
        this.front= shape.getFront();
        this.back= shape.getBack();
        this.left=shape.getLeft();
        this.right=shape.getRight();
        this.top= shape.getTop();
        this.bottom= shape.getBottom();
    }
    public void glVertex3fv(GL_Vector p){
        GL11.glVertex3f(p.x,p.y,p.z);
    }
    public Component findChild(String nodeName)  {
        if(this.id.equals(nodeName))
            return this;
        if(children.size()>0){
            for(int i=0;i<children.size();i++){
                if(children.get(i).id.equals(nodeName)){
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

}
