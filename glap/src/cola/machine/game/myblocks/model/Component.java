package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 16/7/23.
 */
public class Component {
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
    public List<Connector> connectors =new ArrayList<Connector>();

    public void addConnector(Connector connector){
        connectors.add(connector);
    }

    float width;
    float height;
    float thick;
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

    public void render(){ GL11. glEnable(GL11.GL_DEPTH_TEST);
        GL11.glTranslatef(offsetPosition.x, offsetPosition.y, offsetPosition.z);
//        GL11.glRotatef(rotateX, rotateY, rotateZ, 0);
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face
        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);

        GL11.glTexCoord2f(front.minX, front.minY);glVertex3fv(P1);	// Bottom Left ǰ����
        GL11.glTexCoord2f(front.maxX, front.minY);glVertex3fv(P2);	// Bottom Right ǰ����
        GL11.glTexCoord2f(front.maxX, front.maxY); glVertex3fv(P6);	// Top Right ǰ����
        GL11.glTexCoord2f(front.minX, front.maxY); glVertex3fv(P5);	// Top Left	ǰ����
        // Back Face
        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
        GL11.glTexCoord2f(back.minX, back.minY);  glVertex3fv(P3);	// Bottom Right ������
        GL11.glTexCoord2f(back.maxX, back.minY);glVertex3fv(P4);	// Top Right ������
        GL11.glTexCoord2f(back.maxX, back.maxY); glVertex3fv(P8);	// Top Left ������
        GL11.glTexCoord2f(back.minX, back.maxY); glVertex3fv(P7);	// Bottom Left ������
        // Top Face
        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
        GL11.glTexCoord2f(top.minX, top.minY);  glVertex3fv(P5);	// Top Left
        GL11.glTexCoord2f(top.maxX, top.minY); glVertex3fv(P6);// Bottom Left
        GL11.glTexCoord2f(top.maxX, top.maxY);glVertex3fv(P7);	// Bottom Right
        GL11.glTexCoord2f(top.minX, top.maxY);  glVertex3fv(P8);	// Top Right
        // Bottom Face
        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
        GL11.glTexCoord2f(bottom.minX, bottom.minY);glVertex3fv(P4);	// Top Right ������
        GL11.glTexCoord2f(bottom.maxX, bottom.minY);glVertex3fv(P3);	// Top Left ������
        GL11.glTexCoord2f(bottom.maxX, bottom.maxY);glVertex3fv(P2);	// Bottom Left ǰ����
        GL11.glTexCoord2f(bottom.minX, bottom.maxY); glVertex3fv(P1);// Bottom Right ǰ����
        // left face
        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(left.minX, right.minY);glVertex3fv(P2);	// Bottom Right ������
        GL11.glTexCoord2f(left.maxX, right.minY); glVertex3fv(P3);		// Top Right ������
        GL11.glTexCoord2f(left.maxX, right.maxY);glVertex3fv(P7);		// Top Left ǰ����
        GL11.glTexCoord2f(left.minX, right.maxY);glVertex3fv(P6);	// Bottom Left ǰ����
        // right Face
        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(right.minX, left.minY); glVertex3fv(P4);	// Bottom Left ������
        GL11.glTexCoord2f(right.maxX, left.minY); glVertex3fv(P1);	// Bottom Right ǰ����
        GL11.glTexCoord2f(right.maxX, left.maxY); glVertex3fv(P5);	// Top Right ǰ����
        GL11.glTexCoord2f(right.minX, left.maxY);glVertex3fv(P8);// Top Left
        GL11.glEnd();
        for(int i=0;i<connectors.size();i++){
            connectors.get(i).render();
        }


//        GL11.glRotatef(-rotateX, -rotateY, -rotateZ, 0);
        GL11.glTranslatef(-offsetPosition.x,-offsetPosition.y,-offsetPosition.z);
    }

    public void setOffsetPosition(GL_Vector vector){

        this.offsetPosition.x= vector.x;
        this.offsetPosition.y= vector.y;
        this.offsetPosition.z= vector.z;


    }

    public void setImage(String textureInfoName){

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
        this.front= textureManager.getTextureInfo(name+"-front");
        this.back= textureManager.getTextureInfo(name+"-back");
        this.left= textureManager.getTextureInfo(name+"-left");
        this.right= textureManager.getTextureInfo(name+"-right");
        this.top= textureManager.getTextureInfo(name+"-top");
        this.bottom= textureManager.getTextureInfo(name+"-bottom");
    }
    public void glVertex3fv(GL_Vector p){
        GL11.glVertex3f(p.x,p.y,p.z);
    }
}
