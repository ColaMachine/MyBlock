package cola.machine.game.myblocks.item;

import cola.machine.game.myblocks.icon.Icon;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import org.lwjgl.opengl.GL11;

/**
 * Created by luying on 14-8-29.
 */
public class Item {
    public String name;
    public  String id;
    public String type;
    public Icon icon;
    String render;
    public  int count;
    public ItemInfo itemInfo;
    public TextureInfo textureInfo;
    int iconTextureHandle=0;
    int entityTextureHandle=0;
    int upsideTextureHandle=0;
    int sideTextureHandle=0;

    public Item(String name ,int count){
        this.name=name;
        this.textureInfo= TextureManager.getTextureInfo(name);
        this.count=count;
    }
    public void render(){

        if(this.name.equals("sword")){

        }else{
            {
                // Front Face
                TextureInfo ti=TextureManager.getTextureInfo(this.name);
                //GL11.glBindTexture(GL11.GL_TEXTURE_2D,ti.textureHandle);
                ti.bind();
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glNormal3f( 0.0f, 0.0f, 0.6f);
                GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-0.6f, -0.6f,  0.6f);	// Bottom Left
                GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f( 0.6f, -0.6f,  0.6f);	// Bottom Right
                GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 0.6f,  0.6f,  0.6f);	// Top Right
                GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f(-0.6f,  0.6f,  0.6f);	// Top Left
                // Back Face
                GL11.glNormal3f( 0.0f, 0.0f, -0.6f);
                GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-0.6f, -0.6f, -0.6f);	// Bottom Right
                GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f(-0.6f,  0.6f, -0.6f);	// Top Right
                GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 0.6f,  0.6f, -0.6f);	// Top Left
                GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f( 0.6f, -0.6f, -0.6f);	// Bottom Left
                // Top Face


                // Top Face
                GL11.glNormal3f( 0.0f, 0.6f, 0.0f);
                GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-0.6f,  0.6f, -0.6f);	// Top Left
                GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f(-0.6f,  0.6f,  0.6f);	// Bottom Left
                GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 0.6f,  0.6f,  0.6f);	// Bottom Right
                GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f( 0.6f,  0.6f, -0.6f);	// Top Right
                // Bottom Face
                GL11.glNormal3f( 0.0f, -1f, 0.0f);
                GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-0.6f, -0.6f, -0.6f);	// Top Right
                GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f( 0.6f, -0.6f, -0.6f);	// Top Left
                GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 0.6f, -0.6f,  0.6f);	// Bottom Left
                GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f(-0.6f, -0.6f,  0.6f);	// Bottom Right
                // Right face
                GL11.glNormal3f( 1f, 0.0f, 0.0f);
                GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f( 0.6f, -0.6f, -0.6f);	// Bottom Right
                GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f( 0.6f,  0.6f, -0.6f);	// Top Right
                GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 0.6f,  0.6f,  0.6f);	// Top Left
                GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f( 0.6f, -0.6f,  0.6f);	// Bottom Left
                // Left Face
                GL11.glNormal3f( -1f, 0.0f, 0.0f);
                GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-0.6f, -0.6f, -0.6f);	// Bottom Left
                GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f(-0.6f, -0.6f,  0.6f);	// Bottom Right
                GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f(-0.6f,  0.6f,  0.6f);	// Top Right
                GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f(-0.6f,  0.6f, -0.6f);	// Top Left
                GL11.glEnd();
            }
        }
    }
}
