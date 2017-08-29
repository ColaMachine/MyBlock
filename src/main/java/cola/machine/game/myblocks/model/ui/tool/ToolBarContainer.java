package cola.machine.game.myblocks.model.ui.tool;

import cola.machine.game.myblocks.container.Slot;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.region.RegionArea;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.model.ui.bag.Bag;
import cola.machine.game.myblocks.registry.CoreRegistry;
import glapp.GLApp;
import org.lwjgl.opengl.GL11;

public class ToolBarContainer extends RegionArea{
    public TextureInfo textureInfo;
	public ToolBarSlop[] toolBarSlops = new ToolBarSlop[10];
	public Slot[] slots ;
	public ToolBarContainer() {
		Bag bag=CoreRegistry.get(Bag.class);
		slots=bag.slots;
        this.withWH(200,0,362,43);
        this.textureInfo= TextureManager.getTextureInfo("toolbar");

        /*
		float step = (maxX - minX) / 10f;
		for (int i = 0; i < 10; i++) {
			ToolBarSlop slop = new ToolBarSlop(i, minX + i * step, 0, minX
					+ (i + 1) * step, maxY);

			toolBarSlops[i] = slop;

		}*/

	}

	public void render() {

       /* GLApp. pushAttribOrtho();
        // switch to 2D projection
        GLApp. setOrthoOn();
        // tweak settings
        GL11.glEnable(GL11.GL_TEXTURE_2D);   // be sure textures are on
        GL11.glColor4f(1,1,1,1);             // no color
        GL11.glDisable(GL11.GL_LIGHTING);    // no lighting
        GL11.glDisable(GL11.GL_DEPTH_TEST);  // no depth test
        GL11.glEnable(GL11.GL_BLEND);        // enable transparency
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        // activate the image texture
        // draw a textured quad

        GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureInfo.textureHandle);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glNormal3f(0.0f, 0.0f, 1.0f); // normal faces positive Z
            GL11.glTexCoord2f(textureInfo.minX, textureInfo.minY);
            GL11.glVertex3f(0, 0, (float) 0);
            GL11.glTexCoord2f(textureInfo.maxX, textureInfo.minY);
            GL11.glVertex3f(600, 0, (float) 0);
            GL11.glTexCoord2f(textureInfo.maxX, textureInfo.maxY);
            GL11.glVertex3f(600, 51, (float) 0);
            GL11.glTexCoord2f(textureInfo.minX, textureInfo.maxY);
            GL11.glVertex3f(0, 51, (float) 0);
        }


        GL11.glEnd();
        GLApp.setOrthoOff();
        // return to previous settings
        GLApp.popAttrib();*/

       //GLApp.pushAttribOrtho();
        // switch to 2D projection
       // GLApp. setOrthoOn();
        // tweak settings
        GL11.glEnable(GL11.GL_TEXTURE_2D);   // be sure textures are on
        GL11.glColor4f(1,1,1,1);             // no color
        GL11.glDisable(GL11.GL_LIGHTING);    // no lighting
        GL11.glDisable(GL11.GL_DEPTH_TEST);  // no depth test
        GL11.glEnable(GL11.GL_BLEND);        // enable transparency
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        // activate the image texture

        // draw a textured quad

		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureInfo.textureHandle);
        textureInfo.bind();

		GL11.glBegin(GL11.GL_QUADS);

        GL11.glNormal3f(0.0f, 0.0f, 1.0f); // normal faces positive Z
        GL11.glTexCoord2f(textureInfo.minX, textureInfo.minY);
        GL11.glVertex3f(minX, minY, (float) 0);
        GL11.glTexCoord2f(textureInfo.maxX, textureInfo.minY);
        GL11.glVertex3f(maxX, minY, (float) 0);
        GL11.glTexCoord2f(textureInfo.maxX, textureInfo.maxY);
        GL11.glVertex3f(maxX, maxY, (float) 0);
        GL11.glTexCoord2f(textureInfo.minX, textureInfo.maxY);
        GL11.glVertex3f(minX, maxY, (float) 0);
        //GL11.glVertex3f(minX, maxY, (float) 0);
        GL11.glEnd();
			for (int i = 0; i < 9; i++) {
				//slots[27+i].render();
				
				  

				        if(slots[27+i].item!=null){

				        	GL11.glPushMatrix();

				           // GL11.glEnable(GL11.GL_TEXTURE_2D);
				            TextureInfo ti = TextureManager.getTextureInfo(slots[27+i].item.name);
				           // GL11.glBindTexture(GL11.GL_TEXTURE_2D,ti.textureHandle);
                            ti.bind();

				            GL11.glBegin(GL11.GL_QUADS);
				            {
				                GL11.glNormal3f(0.0f, 0.0f, 1.0f); // normal faces positive Z

				                GL11.glTexCoord2f(ti.minX, ti.minY);
				                GL11.glVertex3f( minX+i*this.getWidth()/9, minY, 0);

				                GL11.glTexCoord2f(ti.maxX,ti. minY);
				                GL11.glVertex3f( minX+(i+1)*this.getWidth()/9, minY, 0);

				                GL11.glTexCoord2f(ti.maxX, ti.maxY);
				                GL11.glVertex3f( minX+(i+1)*this.getWidth()/9, maxY,0);

				                GL11.glTexCoord2f(ti.minX, ti.maxY);
				                GL11.glVertex3f(minX+i*this.getWidth()/9,maxY, 0);

				            }
				                GL11.glEnd();
				          GL11.glBindTexture(GL11.GL_TEXTURE_2D, GLApp.fontTextureHandle);
				            //GLApp.print((int)minX,(int) minY,"11");
				            GL11.glDisable(GL11.GL_LIGHTING);
				            // enable alpha blending, so character background is transparent
				            GL11.glEnable(GL11.GL_BLEND);
				            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				            GL11.glTranslatef(minX+(i+1)*this.getWidth()/9-20,0 , 0);        // Position The Text (in pixel coords)
				            for(int j=0; j<String.valueOf(slots[27+i].item.count).length(); j++) {
				                GL11.glCallList(GLApp.fontListBase - 32 + String.valueOf(slots[27+i].item.count).charAt(j));
				            }
				            GL11.glPopMatrix();
				           // GL11.glTranslatef(-maxX,-minY , 0);

				          
				        }
			}
		
       // GLApp.setOrthoOff();
        // return to previous settings
        //GLApp. popAttrib();
	}
}
