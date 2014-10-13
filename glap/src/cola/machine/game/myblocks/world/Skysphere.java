package cola.machine.game.myblocks.world;

import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import glapp.GLApp;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;

public class Skysphere {
	public Skysphere() {
	}

	private static int displayListSphere = -1;
	private WorldRenderer parentWorldRenderer;

	public Skysphere(WorldRenderer parent) {
		parentWorldRenderer = parent;
		double _y= Math.sin(30*Math.PI/180)*50+32;
		double  _x= Math.cos(30*Math.PI/180)*50;
		
		//GLApp.setLightPosition(GL11.GL_LIGHT1, new float[]{(float)_x,(float)_y,0,1});
	}

	public void render() {
		glDepthMask(false);

		/*
		 * if (false) { glCullFace(GL_BACK); } else { glCullFace(GL_FRONT); }
		 */

		// Material shader = Assets.getMaterial("engine:prog.sky");
		// shader.enable();

		// Draw the skysphere

        drawSkysphereTexture();

		//drawSkysphere();
		drawSun();
		// drawSkyCube();
		/*
		 * if (false) { glCullFace(GL_FRONT); } else { glCullFace(GL_BACK); }
		 */

		glDepthMask(true);
	}

	public void drawSkysphere() {
		if (displayListSphere == -1) {
			displayListSphere = glGenLists(1);

			Sphere sphere = new Sphere();
			// sphere.setTextureFlag(true);

			glNewList(displayListSphere, GL11.GL_COMPILE);

			sphere.draw(100, 150, 100);

			glEndList();
		}

		GL11.glCullFace(GL11.GL_FRONT);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor4f(0.63f, 0.80f, 0.91f, 1.0f);
		glCallList(displayListSphere);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glCullFace(GL11.GL_BACK);
	}

    public void drawSkysphereTexture() {
        if (displayListSphere == -1) {
            displayListSphere = glGenLists(1);

            //Sphere sphere = new Sphere();
            // sphere.setTextureFlag(true);

            glNewList(displayListSphere, GL11.GL_COMPILE);

            //sphere.draw(2, 2, 2);

            GLApp.renderSphere();

            glEndList();
        }



        GL11.glPushMatrix();
        { GL11.glCullFace(GL11.GL_FRONT);
            GL11.glTranslated(1,32,1);
            GL11.glScalef(100f, 50f, 100f);          // scale up
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, TextureManager.getTextureInfo("night").textureHandle);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
                    GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            glCallList(displayListSphere);

            GL11.glTranslated(-1,-32,-1);
            GL11.glCullFace(GL11.GL_BACK);
        }
        GL11.glPopMatrix();

       // GL11.glCullFace(GL11.GL_BACK);
    }

    public void drawSun() {
		//计算半径
		double y= Math.sin(30*Math.PI/180)*50+32;
		double  x= Math.cos(30*Math.PI/180)*50;
		

	// GL11.glTranslated(1, 50, 0);
	
		GL11.glTranslated(x, y, 0);
		 GL11.glRotated(30, 0, 0, 1);
		 GL11.glColor4f(1.0f, 0.5f, 0.5f, 0.3f);
		
		 GL11.glEnable(GL11.GL_TEXTURE_2D);
		 GL11.glBindTexture(GL11.GL_TEXTURE_2D,
					TextureManager.getTextureInfo("sun").textureHandle);
		 GL11.glEnable(GL11.GL_BLEND);		
		 GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	        // Front Face
			GL11.glBegin(GL11.GL_QUADS);
			// Front Face
			GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(0f, -10.0f, -10.0f); // Bottom Left
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(0.0f, -10.0f, 10.0f); // Bottom Right
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(0.0f, 10.0f, 10.0f); // Top Right
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(0.0f, 10.0f, -10.0f); // Top Left
			GL11.glEnd();
	        GL11.glRotated(-30, 0, 0, 1);
	        GL11.glTranslated(-x, -y, 0);
	        //  GL11.glTranslated(-1, -50, 0);
	        
	        GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			
			GL11.glColor4f(1.0f, 1f, 1f, 1f);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void drawSunbak() {

		// 计算半径
		double x = Math.sin(30 * Math.PI / 180) * 100;
		double y = Math.cos(30 * Math.PI / 180) * 50;
		// GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslated(1, 50, 1);
		GL11.glColor4f(1.0f, 0.5f, 0.5f, 0.3f);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D,
				TextureManager.getTextureInfo("sun").textureHandle);
		
		GL11.glBegin(GL11.GL_QUADS);
		// Front Face
		GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(0f, -10.0f, -10.0f); // Bottom Left
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(0.0f, -10.0f, 10.0f); // Bottom Right
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(0.0f, 10.0f, 10.0f); // Top Right
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(0.0f, 10.0f, -10.0f); // Top Left
		GL11.glEnd();
		
		
		GL11.glTranslated(-1, -50, 1);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glColor4f(1.0f, 1f, 1f, 1f);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
public void drawSunTest() {
		
		//计算半径
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA,  GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
	 GL11.glTranslated(1, 50,1);
	 GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.3f);  



	        // Front Face
	        
		 GLApp.drawRect(-10, -10, 10, 10);
	      /*  GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
	        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(0f, -10.0f, -10.0f);	// Bottom Left
	        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f(0.0f, -10.0f,  10.0f);	// Bottom Right
	        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f(0.0f,  10.0f,  10.0f);	// Top Right
	        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(0.0f,  10.0f, -10.0f);	// Top Left
	        */
	       GL11.glTranslated(-1, -50, 1);
	}
	public void drawSkyCube() {
		// draw the sky
		if (displayListSphere == -1) {
			displayListSphere = glGenLists(1);
			glNewList(displayListSphere, GL11.GL_COMPILE);
			initDisplayList();
			glEndList();
		}
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor4f(0.63f, 0.80f, 0.91f, 1.0f);
		glCallList(displayListSphere);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

	}

	public void initDisplayList() {

		int sky_x = 1024;
		int sky_y = 1024;
		int sky_z = 256;
		{
			// renderSphere();

			GL11.glBegin(GL11.GL_QUADS);
			// Front Face
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);

			GL11.glVertex3f(sky_x, -sky_y, sky_z); // Bottom Right
			GL11.glVertex3f(-sky_x, -sky_y, sky_z); // Bottom Left
			GL11.glVertex3f(-sky_x, sky_y, sky_z); // Top Left
			GL11.glVertex3f(sky_x, sky_y, sky_z); // Top Right

			// Back Face
			GL11.glNormal3f(0.0f, 0.0f, -1.0f);

			GL11.glVertex3f(-sky_x, sky_y, -sky_z); // Top Right
			GL11.glVertex3f(-sky_x, -sky_y, -sky_z); // Bottom Right
			GL11.glVertex3f(sky_x, -sky_y, -sky_z); // Bottom Left
			GL11.glVertex3f(sky_x, sky_y, -sky_z); // Top Left

			// Top Face
			GL11.glNormal3f(0.0f, 1.0f, 0.0f);

			GL11.glVertex3f(-sky_x, sky_y, sky_z); // Bottom Left
			GL11.glVertex3f(-sky_x, sky_y, -sky_z); // Top Left
			GL11.glVertex3f(sky_x, sky_y, -sky_z); // Top Right
			GL11.glVertex3f(sky_x, sky_y, sky_z); // Bottom Right

			// Bottom Face
			GL11.glNormal3f(0.0f, -1.0f, 0.0f);

			GL11.glVertex3f(sky_x, -sky_y, -sky_z); // Top Left
			GL11.glVertex3f(-sky_x, -sky_y, -sky_z); // Top Right
			GL11.glVertex3f(-sky_x, -sky_y, sky_z); // Bottom Right
			GL11.glVertex3f(sky_x, -sky_y, sky_z); // Bottom Left

			// Right face
			GL11.glNormal3f(1.0f, 0.0f, 0.0f);

			GL11.glVertex3f(sky_x, sky_y, -sky_z); // Top Right
			GL11.glVertex3f(sky_x, -sky_y, -sky_z); // Bottom Right
			GL11.glVertex3f(sky_x, -sky_y, sky_z); // Bottom Left
			GL11.glVertex3f(sky_x, sky_y, sky_z); // Top Left

			// Left Face
			GL11.glNormal3f(-1.0f, 0.0f, 0.0f);

			GL11.glVertex3f(-sky_x, -sky_y, sky_z); // Bottom Right
			GL11.glVertex3f(-sky_x, -sky_y, -sky_z); // Bottom Left
			GL11.glVertex3f(-sky_x, sky_y, -sky_z); // Top Left
			GL11.glVertex3f(-sky_x, sky_y, sky_z); // Top Right

			GL11.glEnd();
		}
	}
}
