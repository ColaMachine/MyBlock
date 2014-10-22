package cola.machine.game.myblocks.LifeThing.animal.Dinosaur;

import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

public class DinosaurBody {
	private int widht;
	private int height;
	private int thick;
	public GL_Vector Position ;
	//���������λ��Ӧ����ƨ������ ����
	GL_Vector P1=new GL_Vector(-0.5f,0f,0.25f);
	GL_Vector P2=new GL_Vector(0.5f,0f,0.25f);
	GL_Vector P3=new GL_Vector(0.5f,0f,-0.25f);
	GL_Vector P4=new GL_Vector(-0.5f,0f,-0.25f);
	GL_Vector P5=new GL_Vector(-0.5f,1.5f,0.25f);
	GL_Vector P6=new GL_Vector(0.5f,1.5f,0.25f);
	GL_Vector P7=new GL_Vector(0.5f,1.5f,-0.25f);
	GL_Vector P8=new GL_Vector(-0.5f,1.5f,-0.25f);
	
	public void setHead(float posx, float posy, float posz,
			float dirx, float diry, float dirz,
			float upx, float upy, float upz)
	{
		if (upx == 0 && upy == 0 && upz == 0) {
			System.out.println("GLCamera.setCamera(): Up vector needs to be defined");
			upx=0; upy=1; upz=0;
		}
		if (dirx == 0 && diry == 0 && dirz == 0) {
			System.out.println("GLCamera.setCamera(): ViewDirection vector needs to be defined");
			dirx=0; diry=0; dirz=-1;
		}
		Position 	= new GL_Vector(posx, posy, posz);
	}
	public void render(){
		GL11.glTranslatef(Position.x,Position.y,Position.z);
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face
        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
        GL11.glTexCoord2f(5/16f, 0f);glVertex3fv(P1);	// Bottom Left ǰ����
        GL11.glTexCoord2f(7/16f, 0f);glVertex3fv(P2);	// Bottom Right ǰ����
        GL11.glTexCoord2f(7/16f, 3/8f); glVertex3fv(P6);	// Top Right ǰ����
        GL11.glTexCoord2f(5/16f, 3/8f); glVertex3fv(P5);	// Top Left	ǰ����
        // Back Face
        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
        GL11.glTexCoord2f(4/8f,0f);  glVertex3fv(P3);	// Bottom Right ������
        GL11.glTexCoord2f(5/8f, 0f);glVertex3fv(P4);	// Top Right ������
        GL11.glTexCoord2f(5/8f, 3/8f); glVertex3fv(P8);	// Top Left ������
        GL11.glTexCoord2f(4/8f,3/8f); glVertex3fv(P7);	// Bottom Left ������
        // Top Face
        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
        GL11.glTexCoord2f(1/8f, 3/4f);  glVertex3fv(P5);	// Top Left 
        GL11.glTexCoord2f(2/8f, 3/4f); glVertex3fv(P6);// Bottom Left
        GL11.glTexCoord2f(2/8f, 1f);glVertex3fv(P7);	// Bottom Right
        GL11.glTexCoord2f(1/8f, 1f);  glVertex3fv(P8);	// Top Right
        // Bottom Face
        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
        GL11.glTexCoord2f(2/8f, 3/4f);glVertex3fv(P4);	// Top Right ������
        GL11.glTexCoord2f(3/8f, 3/4f);glVertex3fv(P3);	// Top Left ������
        GL11.glTexCoord2f(3/8f, 1f);glVertex3fv(P2);	// Bottom Left ǰ����
        GL11.glTexCoord2f(2/8f, 1f); glVertex3fv(P1);// Bottom Right ǰ����
        // Right face
        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(7/16f, 0f);glVertex3fv(P2);	// Bottom Right ������
        GL11.glTexCoord2f(8/16f,0f); glVertex3fv(P3);		// Top Right ������
        GL11.glTexCoord2f(8/16f, 3/8f);glVertex3fv(P7);		// Top Left ǰ����
        GL11.glTexCoord2f(7/16f, 2/8f);glVertex3fv(P6);	// Bottom Left ǰ����
        // Left Face
        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(4/16f, 0f); glVertex3fv(P4);	// Bottom Left ������
        GL11.glTexCoord2f(5/16f,0f); glVertex3fv(P1);	// Bottom Right ǰ����
        GL11.glTexCoord2f(5/16f, 3/8f); glVertex3fv(P5);	// Top Right ǰ����
        GL11.glTexCoord2f(4/16f, 2/8f);glVertex3fv(P8);// Top Left
        GL11.glEnd();
        GL11.glTranslatef(-Position.x,-Position.y,-Position.z);
	
	}
	

	public void adjust(float posx, float f, float posz) {
		this.Position.x=posx;
		this.Position.y=f;
		this.Position.z=posz;
		
	}
	public void glVertex3fv(GL_Vector p){
		GL11.glVertex3f(p.x,p.y,p.z);
	}
}
