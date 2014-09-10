package cola.machine.game.myblocks.model;

import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.AABB.AABB;
import cola.machine.game.myblocks.model.textture.TextureInfo;

public class BaseBlock extends AABB implements Block{
	public int id=0;
	public int x=0;
	public int y=0;
	public int z=0;
	public int red=0;
	public int blue=0;
	public int green=0;
	public String getName(){
		return name;
	}
	private String name;
	public BaseBlock(String name,int x,int y,int z){
		this.name=name;
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
	public BaseBlock(String name,int x,int y,int z,Color color){
		this.name=name;
		this.x=x;
		this.y=y;
		this.z=z;
		
		this.minX=x-1;
		this.minY=y-1;
		this.minZ=z-1;
		this.maxX=x+1;
		this.maxY=y+1;
		this.maxZ=z+1;
		this.red=color.r();
		this.blue=color.b();
		this.green=color.g();
	}
	public BaseBlock(String name,int id){
		this.name=name;
		this.id=id;
	}
	public BaseBlock(){
		
	}
		
	
	public void renderCube(){
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face
        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
        // Back Face
        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
        // Top Face
        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Left
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Right
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
        // Bottom Face
        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Top Right
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Top Left
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
        // Right face
        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
        // Left Face
        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
        GL11.glEnd();
	}
	
	public void render(){
		 // Front Face
		TextureInfo ti=TextureManager.getIcon(getName());
	    GL11.glBegin(GL11.GL_QUADS);
	        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
	        // Back Face
	        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
	       // Top Face
	        
	        
	     // Top Face
	        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Left
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Right
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	       // Bottom Face
	        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Top Right
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Top Left
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	        // Right face
	        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	        // Left Face
	        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glEnd();
	}
	
	public void renderColor(){
		 // Front Face
		TextureInfo ti=TextureManager.getIcon(getName());
	    GL11.glBegin(GL11.GL_QUADS);
	        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
	        GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	        GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	       GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
	        GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
	        // Back Face
	        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
	       GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
	        GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	       GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
	       // Top Face
	        
	        
	     // Top Face
	        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
	        GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Left
	         GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Right
	        GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	       // Bottom Face
	        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
	       GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Top Right
	        GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Top Left
	        GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	     GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	        // Right face
	        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
	       GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
	        GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	        GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
	         GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	        // Left Face
	        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
	       GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
	       GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	       GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
	         GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
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
	@Override
	public int getX() {
		// VIP Auto-generated method stub
		return x;
	}
	@Override
	public int getY() {
		// VIP Auto-generated method stub
		return y;
	}
	@Override
	public int getZ() {
		// VIP Auto-generated method stub
		return z;
	}
	public int r() {
		return red;
	}
	@Override
	public int b() {
		return blue;
	}
	@Override
	public int g() {
		return green;
	}
	@Override
	public int getId() {
		// VIP Auto-generated method stub
		return id;
	}
}
