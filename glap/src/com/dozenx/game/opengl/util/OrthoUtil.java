package com.dozenx.game.opengl.util;

import org.lwjgl.opengl.GL11;

public class OrthoUtil {
	public static void setOrthoOff() {
		// restore the original positions and views
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		// turn Depth Testing back on
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public static void setOrthoOn() {
		// prepare projection matrix to render in 2D
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix(); // preserve perspective view
		GL11.glLoadIdentity(); // clear the perspective matrix
		GL11.glOrtho(-1, 1, -1, 1, -500, 500); // Zfar, Znear
		// clear the modelview matrix
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix(); // Preserve the Modelview Matrix
		GL11.glLoadIdentity(); // clear the Modelview Matrix
		// disable depth test so further drawing will go over the current scene
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public static void setOrthoTest() {
		// select projection matrix (controls view on screen)
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// set ortho to same size as viewport, positioned at 0,0
		/*
		 * GL11.glOrtho( 0,viewportW, // left,right 0,viewportH, // bottom,top
		 * -500,500); // Zfar, Znear
		 */
		GL11.glOrtho(16.5, 19.5, -2, 2, 16.5, 19.5);

		// return to modelview matrix
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	
}
