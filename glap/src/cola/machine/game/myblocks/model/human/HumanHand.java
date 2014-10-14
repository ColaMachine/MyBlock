package cola.machine.game.myblocks.model.human;

import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.item.weapons.Sword;
import glmodel.GL_Vector;

public class HumanHand {
    public Human human;
	private int widht;
	private int height;
	private int thick;
	public float angle;
	//public Sword sword =new Sword(0,0,0);
	public GL_Vector Position ;
    public boolean isMainHand=false;
    public HumanHand(boolean flag){
        isMainHand=flag;
    }
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
	
	GL_Vector P1=new GL_Vector(-0.25f,-1.25f,0.25f);
	GL_Vector P2=new GL_Vector(0.25f,-1.25f,0.25f);
	GL_Vector P3=new GL_Vector(0.25f,-1.25f,-0.25f);
	GL_Vector P4=new GL_Vector(-0.25f,-1.25f,-0.25f);
	GL_Vector P5=new GL_Vector(-0.25f,0.25f,0.25f);
	GL_Vector P6=new GL_Vector(0.25f,0.25f,0.25f);
	GL_Vector P7=new GL_Vector(0.25f,0.25f,-0.25f);
	GL_Vector P8=new GL_Vector(-0.25f,0.25f,-0.25f);
	
	

	GL_Vector[] vertexs={
			P1,P2,P6,P5,
			P3,P4,P8,P7,
			P5,P6,P7,P8,
			P4,P3,P2,P1,
			P2,P3,P7,P6,
			P4,P1,P5,P8
			};
	
	float[] v={
			
	};
	
	public void render(){
		
		GL11.glTranslatef(Position.x,Position.y,Position.z);
		GL11.glRotatef(angle,1, 0, 0);
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face
        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
        GL11.glTexCoord2f(13/16f, 3/8f); glVertex3fv(vertexs[0]);// Bottom Left ǰ����
        GL11.glTexCoord2f(12/16f, 3/8f); glVertex3fv(vertexs[1]);// Bottom Right ǰ����
        GL11.glTexCoord2f(12/16f, 0f); glVertex3fv(vertexs[2]);// Top Right ǰ����
        GL11.glTexCoord2f(13/16f, 0f); glVertex3fv(vertexs[3]);	// Top Left	ǰ����
        // Back Face
        
        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
        GL11.glTexCoord2f(11/16f, 3/8f); glVertex3fv(vertexs[4]);// Bottom Left ������
        GL11.glTexCoord2f(10/16f, 3/8f); glVertex3fv(vertexs[5]);// Bottom Right ������
        GL11.glTexCoord2f(10/16f, 0f); glVertex3fv(vertexs[6]);	// Top Right ������
        GL11.glTexCoord2f(11/16f, 0f); glVertex3fv(vertexs[7]);	// Top Left ������
       
        // Top Face
        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
      
        GL11.glTexCoord2f(11/16f, 3/8f); glVertex3fv(vertexs[8]);	// Bottom Left ǰ����
        GL11.glTexCoord2f(12/16f, 3/8f);glVertex3fv(vertexs[9]);	// Bottom Right ǰ����
        GL11.glTexCoord2f(12/16f, 4/8f); glVertex3fv(vertexs[10]);// Top Right ������
        GL11.glTexCoord2f(11/16f, 4/8f); glVertex3fv(vertexs[11]);// Top Left ������
        // Bottom Face
        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
        GL11.glTexCoord2f(12/16f, 3/8f); glVertex3fv(vertexs[12]);	// Top Right ������
        GL11.glTexCoord2f(13/16f, 3/8f);glVertex3fv(vertexs[13]);	// Top Left ������
        GL11.glTexCoord2f(13/16f, 4/8f); glVertex3fv(vertexs[14]);// Bottom Left ǰ����
        GL11.glTexCoord2f(12/16f, 4/8f); glVertex3fv(vertexs[15]);	// Bottom Right ǰ����
        // Right face
        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(12/16f, 3/8f); glVertex3fv(vertexs[16]);	// Bottom Left ǰ����
        GL11.glTexCoord2f(11/16f, 3/8f); glVertex3fv(vertexs[17]);	// Bottom Right ������
        GL11.glTexCoord2f(11/16f, 0f); glVertex3fv(vertexs[18]);	// Top Right ������
        GL11.glTexCoord2f(12/16f, 0f); glVertex3fv(vertexs[19]);	// Top Left ǰ����
      
        // Left Face
        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(14/16f, 3/8f); glVertex3fv(vertexs[20]);// Bottom Left ������
        GL11.glTexCoord2f(13/16f, 3/8f); glVertex3fv(vertexs[21]);	// Bottom Right ǰ����
        GL11.glTexCoord2f(13/16f,0f); glVertex3fv(vertexs[22]);	// Top Right ǰ����
        GL11.glTexCoord2f(14/16f, 0f); glVertex3fv(vertexs[23]);// Top Leftǰ����
        GL11.glEnd();
        
        
      //转椅到男人手心这个位置
      		// double dy =Math.cos(LHand.angle/Math.PI);
      		 //double dz= Math.sin(LHand.angle/Math.PI);
        if(this.isMainHand && human.item!=null){
            GL11.glTranslated(0,-1,1);

            human.item.render();
            //sword.render();
            GL11.glTranslated(0,1,-1);
        }

      		 
      		 
        GL11.glRotatef(-angle,1, 0, 0);
        GL11.glTranslatef(-Position.x,-Position.y,-Position.z);
      //  GL11.glRotatef(-30,1, 0, 0);
	
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
