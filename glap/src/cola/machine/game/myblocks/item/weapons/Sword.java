package cola.machine.game.myblocks.item.weapons;

import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;

public class Sword {
	public int x=0;
	public int y=0;
	public int z=0;
	public String name="sword";
	public void render(){
		
		TextureInfo ti=TextureManager.getIcon("gold_sword");
		GL11.glLineWidth(3);
		GL11.glBindTexture(
				GL11.GL_TEXTURE_2D,
				ti.textureHandle);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
				GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glEnable(GL11.GL_TEXTURE_GEN_S); 
		GL11.glEnable(GL11.GL_TEXTURE_GEN_T);


		 GL11.glBegin(GL11.GL_QUADS);
	  //  // Top Face
	        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Left
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Right
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	       // Bottom Face
	        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x, 1.0f+y-0.5f, -1.0f+z);	// Top Right
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f( 1.0f+x, 1.0f+y-0.5f, -1.0f+z);	// Top Left
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x, 1.0f+y-0.5f,  1.0f+z);	// Bottom Left
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f(-1.0f+x, 1.0f+y-0.5f,  1.0f+z);	// Bottom Right
	       
	       GL11.glEnd();
		
		
		
	}
	public Sword(String name,int x,int y,int z){
		this.name=name;
		this.x=x;
		this.y=y;
		this.z=z;
		
	
	}
	
	
}
