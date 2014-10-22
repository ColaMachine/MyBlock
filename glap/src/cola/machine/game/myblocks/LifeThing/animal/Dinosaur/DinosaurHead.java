package cola.machine.game.myblocks.LifeThing.animal.Dinosaur;

import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

public class DinosaurHead {
	private int widht;
	private int height;
	private int thick;
    private float scale=1/2;
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
	GL_Vector P1=new GL_Vector(-0.5f,0f,0.5f);
	GL_Vector P2=new GL_Vector(0.5f,0f,0.5f);
	GL_Vector P3=new GL_Vector(0.5f,0f,-0.5f);
	GL_Vector P4=new GL_Vector(-0.5f,0f,-0.5f);
	GL_Vector P5=new GL_Vector(-0.5f,1f,0.5f);
	GL_Vector P6=new GL_Vector(0.5f,1f,0.5f);
	GL_Vector P7=new GL_Vector(0.5f,1f,-0.5f);
	GL_Vector P8=new GL_Vector(-0.5f,1f,-0.5f);
	
	GL_Vector[] vertexs={
			P1,P2,P6,P5,
			P3,P4,P8,P7,
			P5,P6,P7,P8,
			P4,P3,P2,P1,
			P2,P3,P7,P6,
			P4,P1,P5,P8
			};
	float[] v={1/8f, 4/8f,
			2/8f, 4/8f,
			2/8f, 6/8f,
			1/8f, 6/8f,
			
			3/8f, 4/8f,
			4/8f, 4/8f,
			4/8f, 6/8f,
			3/8f, 6/8f,
			
			
			1/8f, 3/4f,
			2/8f, 3/4f,
			2/8f, 1f,
			1/8f, 1f,
			
			2/8f, 3/4f,
			3/8f, 3/4f,
			3/8f, 1f,
			2/8f, 1f,
			
			2/8f, 2/4f,
			3/8f, 2/4f,
			3/8f,3/4f,
			2/8f, 3/4f,
			
			
			0f, 2/4f,
			1/8f, 2/4f,
			1/8f, 3/4f,
			0f, 3/4f
	}
	;
	float[] normal_v={ 0.0f, 0.0f, 0.5f,
			 0.0f, 0.0f, -0.5f,
			 0.0f, 0.0f, -0.5f,
			 0.0f, 0.5f, 0.0f,
			 0.0f, -0.5f, 0.0f,
			 0.5f, 0.0f, 0.0f,
			 -0.5f, 0.0f, 0.0f
	};
	public void render(){
		
		GL11.glTranslatef(Position.x,Position.y,Position.z);
    GL11.glBegin(GL11.GL_QUADS);
    int index=0;
    for(int i=0;i<24;i++){
    	if(i%4==0){//System.out.println(i/4*3);
    		  GL11.glNormal3f( normal_v[i/4*3], normal_v[i/4*3+1],  normal_v[i/4*3+2]);
    		  
    		  //System.out.printf(" GL11.glNormal3f( %f, %f,%f ) ", normal_v[i/4*3], normal_v[i/4*3+1],  normal_v[i/4*3+2]);
    	}
    	//System.out.printf(" GL11.glTexCoord2f(%f,%f);", normal_v[i/4*3], normal_v[i/4*3+1],  normal_v[i/4*3+2]);
    	 GL11.glTexCoord2f(v[i*2], v[i*2+1]);
 		glVertex3fv(vertexs[i]);
    }
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
