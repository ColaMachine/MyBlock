package cola.machine.game.myblocks.model.human;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import glmodel.GL_Vector;

public class HumanLeg {
	/*
	 * width:1
	 * (-0.5 ,0,0.5) (0.5,0)
	 * 	P8	P7
	 * P5 P6
	 *  ______
	 * |	|
	 * |	|
	 * |	|Height:4	
	 * |	|
	 * |	|
	 * |	|
	 * ------
	 * 	P4	P3
	 * P1 P2
	 * (-0.25,-4,0.25) (0.25,-4,0.25)
	 */

	GL_Vector P1=new GL_Vector(-0.25f,-1.5f,0.25f);
	GL_Vector P2=new GL_Vector(0.25f,-1.5f,0.25f);
	GL_Vector P3=new GL_Vector(0.25f,-1.5f,-0.25f);
	GL_Vector P4=new GL_Vector(-0.25f,-1.5f,-0.25f);
	GL_Vector P5=new GL_Vector(-0.25f,0f,0.25f);
	GL_Vector P6=new GL_Vector(0.25f,0f,0.25f);
	GL_Vector P7=new GL_Vector(0.25f,0f,-0.25f);
	GL_Vector P8=new GL_Vector(-0.25f,0f,-0.25f);
	

	GL_Vector[] vertexs={
			P1,P2,P6,P5,
			P3,P4,P8,P7,
			P5,P6,P7,P8,
			P4,P3,P2,P1,
			P2,P3,P7,P6,
			P4,P1,P5,P8
			};
	private int widht;
	private int height;
	private int thick;
	float angle=0;
	public GL_Vector Position ;
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
	public void render(){ GL11. glEnable(GL11.GL_DEPTH_TEST);
		 //GL11.glTranslatef(Position.x,Position.y,Position.z); // down a bit
		
		//λ��������ϵͳ
		//1�������������������
		//
		/*
		 * width:2
		 * ---------
		 * |	   |height:1
		 * -------
		 * 
		 */
		//����ת
		//��λ��
		GL11.glTranslatef(Position.x,Position.y,Position.z);
		 GL11.glRotatef(angle,1, 0, 0);
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face
        
        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);//0~4  0~12
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P1);	// Bottom Left ǰ����
        GL11.glTexCoord2f(1/16f, 0f);glVertex3fv(P2);	// Bottom Right ǰ����
        GL11.glTexCoord2f(1/16f, 3/8f);glVertex3fv(P6);	// Top Right ǰ����
        GL11.glTexCoord2f(0f, 3/8f); glVertex3fv(P5);	// Top Left	ǰ����
        // Back Face
        
        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P3);	// Bottom Left ������
        GL11.glTexCoord2f(1/16f, 0f); glVertex3fv(P4);	// Bottom Right ������
        GL11.glTexCoord2f(1/16f, 3/8f); glVertex3fv(P8);	// Top Right ������
        GL11.glTexCoord2f(0f, 3/8f);glVertex3fv(P7);	// Top Left ������
       
        // Top Face
        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
      
        GL11.glTexCoord2f(1/16f, 3/8f); glVertex3fv(P5);	// Bottom Left ǰ����
        GL11.glTexCoord2f(2/16f, 3/8f); glVertex3fv(P6);	// Bottom Right ǰ����
        GL11.glTexCoord2f(2/16f, 4/8f); glVertex3fv(P7);	// Top Right ������
        GL11.glTexCoord2f(1/16f, 4/8f); glVertex3fv(P8);	// Top Left ������
        // Bottom Face
        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
        GL11.glTexCoord2f(1/16f, 3/8f);glVertex3fv(P4);	// Top Right ������
        GL11.glTexCoord2f(2/16f, 3/8f); glVertex3fv(P3);	// Top Left ������
        GL11.glTexCoord2f(2/16f, 4/8f);glVertex3fv(P2);	// Bottom Left ǰ����
        GL11.glTexCoord2f(1/16f, 4/8f);glVertex3fv(P1);	// Bottom Right ǰ����
        // Right face
        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P2);	// Bottom Left ǰ����
        GL11.glTexCoord2f(1/16f, 0f);glVertex3fv(P3);	// Bottom Right ������
        GL11.glTexCoord2f(1/16f, 3/8f); glVertex3fv(P7);	// Top Right ������
        GL11.glTexCoord2f(0f, 3/8f);glVertex3fv(P6);	// Top Left ǰ����
      
        // Left Face
        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P4);	// Bottom Left ������
        GL11.glTexCoord2f(1/16f, 0f);glVertex3fv(P1);	// Bottom Right ǰ����
        GL11.glTexCoord2f(1/16f, 3/8f);glVertex3fv(P5);	// Top Right ǰ����
        GL11.glTexCoord2f(0f, 3/8f);glVertex3fv(P8);	// Top Leftǰ����
        GL11.glEnd();
        
    	
        
    	GL11.glTranslatef(-Position.x,-Position.y,-Position.z);
	}
	
	public void glVertex3fv(GL_Vector p){
		GL11.glVertex3f(p.x,p.y,p.z);
	}

	public void adjust(float posx, float f, float posz) {
		this.Position.x=posx;
		this.Position.y=f;
		this.Position.z=posz;
		
	}
}
