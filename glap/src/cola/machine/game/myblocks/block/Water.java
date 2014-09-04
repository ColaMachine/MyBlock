package cola.machine.game.myblocks.block;

import org.lwjgl.opengl.GL11;
import org.terasology.registry.CoreRegistry;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.textture.TextureInfo;
/**
 * Created by luying on 14-8-30.
 */
public class Water extends BaseBlock{
	public String name ="water";
	public Water(int x,int y,int z){
		this.x=x;
		this.y=y;
		this.z=z;
		
		this.minX=x-1;
		this.minY=y-1;
		this.minZ=z-1;
		this.maxX=x+1;
		this.maxY=y+1;
		this.maxZ=z+1;
	}
	/*public Water(){
		ti=TextureManager.getIcon("water");
	}*/
	public int x=0;
	public int y=0;
	public int z=0;
	public void renderCube(){
		
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face
     // Top Face
        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
//        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
//        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Left
//        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Right
//        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
       GL11.glEnd();
	}
	public void setCenter(int x,int y,int z){
		this.x=x;
		this.y=y;
		this.z=z;
		
		this.minX=x-1;
		this.minY=y-1;
		this.maxX=x+1;
		this.maxY=y+1;
	}


}
