package gldemo;

import glapp.GLApp;
import org.lwjgl.opengl.GL11;

/**
 * Run a bare-bones GLApp.  Draws one white triangle centered on screen.
 * <P>
 * GLApp initializes the LWJGL environment for OpenGL rendering,
 * ie. creates a window, sets the display mode, inits mouse and keyboard,
 * then runs a loop that calls draw().
 * <P>
 * napier at potatoland dot org
 */
public class GLApp_Demo_glclear extends GLApp {
	
	/**
	 * Start the application.  run() calls setup(), handles mouse and keyboard input,
	 * calls render() in a loop.
	 */
    public static void main(String args[]) {
    	// create the app
    	GLApp_Demo_glclear demo = new GLApp_Demo_glclear();

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
    }

    /**
     * Render one frame.  Called by GLApp.run().
     */
    public void draw() {
    	
    	GL11.glClearColor(1, 1, 1, 0);
    	
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    	                      
    }
}
