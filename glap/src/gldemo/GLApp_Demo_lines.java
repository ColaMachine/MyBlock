package gldemo;

import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;

import glapp.*;

/**
 * Run a bare-bones GLApp.  Draws one white triangle centered on screen.
 * <P>
 * GLApp initializes the LWJGL environment for OpenGL rendering,
 * ie. creates a window, sets the display mode, inits mouse and keyboard,
 * then runs a loop that calls draw().
 * <P>
 * napier at potatoland dot org
 */
public class GLApp_Demo_lines extends GLApp {
	
	/**
	 * Start the application.  run() calls setup(), handles mouse and keyboard input,
	 * calls render() in a loop.
	 */
    public static void main(String args[]) {
    	// create the app
    	GLApp_Demo_lines demo = new GLApp_Demo_lines();

    	// set title, window size
    	demo.window_title = "Hello World";
    	demo.displayWidth = 640;
    	demo.displayHeight = 480;

    	// start running: will call init(), setup(), draw(), mouse functions
    	demo.run();
    }

    /**
     * Initialize the scene.  Called by GLApp.run().  For now the default
     * settings will be fine, so no code here.
     */
    public void setup()
    {	
int i;
// Clear screen and depth buffer


    }

    /**
     * Render one frame.  Called by GLApp.run().
     */
    public void drawOneLine(float x1, float y1,float x2,float y2){
    	GL11.glBegin(GL11.GL_LINES);
    	GL11.glVertex2f(x1,y1);
    	GL11.glVertex2f(x2,y2);
    	GL11.glEnd();
    }

	public void draw() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		// Select The Modelview Matrix (controls model orientation)
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		// Reset the coordinate system to center of screen
		GL11.glLoadIdentity();
		GLU.gluLookAt(50f,125f, 120f, // eye position (10 units in front of the
				// origin)
	50f, 125f, 0f, // target to look at (the origin)
	0f, 1f, 0f); // which way is up (Y axis)//哪个方向槽上
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_LINE_STIPPLE);
		
		GL11.glLineStipple(1, (short) 0x0101);

		drawOneLine(50.0f, 125.0f, 150.0f, 125.0f);
		
		GL11.glLineStipple(1, (short)0x00FF);
		drawOneLine(50.0f, 130.0f,150.0f, 130.0f);
		
		GL11.glLineStipple(1, (short)0x1C47);
		drawOneLine(50.0f, 133.0f,150.0f, 133.0f);
		GL11.glDisable(GL11.GL_LINE_STIPPLE);
		GL11.glFlush();
	}
}
