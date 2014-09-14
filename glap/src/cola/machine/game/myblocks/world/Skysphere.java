package cola.machine.game.myblocks.world;

import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

import cola.machine.game.myblocks.rendering.world.WorldRenderer;

public class Skysphere {
	public Skysphere() {
	}
	private static int displayListSphere = -1;
	private  WorldRenderer parentWorldRenderer;

	public Skysphere(WorldRenderer parent) {
		parentWorldRenderer = parent;
	}
	   public void render() {
	        glDepthMask(false);

	       /* if (false) {
	            glCullFace(GL_BACK);
	        } else {
	            glCullFace(GL_FRONT);
	        }*/

//	        Material shader = Assets.getMaterial("engine:prog.sky");
//	        shader.enable();

	        // Draw the skysphere
	       // drawSkysphere();
	        drawSkyCube();
	     /*   if (false) {
	            glCullFace(GL_FRONT);
	        } else {
	            glCullFace(GL_BACK);
	        }*/

	        glDepthMask(true);
	    }
	
	public  void drawSkysphere() {
	GL11.glDisable(GL11.GL_TEXTURE_2D);
	
		if (displayListSphere == -1) {
			displayListSphere = glGenLists(1);

			Sphere sphere = new Sphere();
			//sphere.setTextureFlag(true);

			glNewList(displayListSphere, GL11.GL_COMPILE);

			sphere.draw(100, 50, 100);

			glEndList();
		}
		
		glCallList(displayListSphere);
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
