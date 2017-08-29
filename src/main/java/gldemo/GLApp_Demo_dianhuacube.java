package gldemo;

import glapp.GLApp;
import glapp.GLCam;
import glapp.GLCamera;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Run a bare-bones GLApp.  Draws one white triangle centered on screen.
 * <P>
 * GLApp initializes the LWJGL environment for OpenGL rendering,
 * ie. creates a window, sets the display mode, inits mouse and keyboard,
 * then runs a loop that calls draw().
 * <P>
 * napier at potatoland dot org
 */
public class GLApp_Demo_dianhuacube extends GLApp {
	  float lightPosition[]= { -2f, 2f, 2f, 0f };
	    float[] cameraPos = {0f,3f,20f};
	    GLCamera camera1 = new GLCamera();
	    GLCam cam = new GLCam(camera1);
	    int earth;

		FloatBuffer bbmatrix = GLApp.allocFloats(16);
	    // for earth rotation
	    float degrees = 0;
	/**
	 * Start the application.  run() calls setup(), handles mouse and keyboard input,
	 * calls render() in a loop.
	 */
    public static void main(String args[]) {
    	// create the app
    	GLApp_Demo_dianhuacube demo = new GLApp_Demo_dianhuacube();
    	demo.VSyncEnabled = true;
        demo.fullScreen = false;
        demo.displayWidth = 800;
        demo.displayHeight = 600;
        demo.run();  // will call init(), render(), mouse functions
    }
    /**
     * set the field of view and view depth.
     */
    public static void setPerspective()
    {
        // select projection matrix (controls perspective)
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        // fovy, aspect ratio, zNear, zFar
        GLU.gluPerspective(50f,         // zoom in or out of view
                           aspectRatio, // shape of viewport rectangle
                           .1f,         // Min Z: how far from eye position does view start
                          40f);       // max Z: how far from eye position does view extend
        // return to modelview matrix
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    /**
     * Initialize the scene.  Called by GLApp.run().  For now the default
     * settings will be fine, so no code here.
     */
    public void setup()
    {   setPerspective();
    	 setLight( GL11.GL_LIGHT1,
         		new float[] { 1f, 1f, 1f, 1f },
         		new float[] { 0.5f, 0.5f, .53f, 1f },
         		new float[] { 1f, 1f, 1f, 1f },
         		lightPosition );
    	 // Create a directional light (light green, to simulate reflection off grass)
         setLight( GL11.GL_LIGHT2,
         		new float[] { 0.15f, 0.4f, 0.1f, 1.0f },  // diffuse color
         		new float[] { 0.0f, 0.0f, 0.0f, 1.0f },   // ambient
         		new float[] { 0.0f, 0.0f, 0.0f, 1.0f },   // specular
         		new float[] { 0.0f, -10f, 0.0f, 0f } );   // direction (pointing up)
         
         flyBuffer .put(fly, 0, 128);
    	  GL11.glEnable(GL11.GL_LIGHTING);

          // Enable alpha transparency (so text will have transparent background)
          GL11.glEnable(GL11.GL_AUTO_NORMAL);
          //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

          // Create texture for spere

          camera1.setCamera(0,4,15, 0,-.3f,-1, 0,1,0);
          
          // make a sphere display list
          earth = beginDisplayList(); 
          {
          	renderCube();
          }
          endDisplayList();
    }

    /**
     * Render one frame.  Called by GLApp.run().
     */
    
   
    public void draw() {
    	degrees += 30f * GLApp.getSecondsPerFrame();

        // place airplane in orbit around ball, and place camera slightly above airplane

    	// align airplane and camera2 (perpendicular to the radius and up vector)

        // user keystrokes adjust camera position
        cam.handleNavKeys((float)GLApp.getSecondsPerFrame());

        // combine user camera motion with current camera position (so user can look around while on the airplane)

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // select model view for subsequent transforms
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        // do gluLookAt() with camera position, direction, orientation
        cam.render();

        // invokes the drawObjects() method to create shadows for objects in the scene

        // draw sphere at center (rotate 10 degrees per second)
        //rotation += 10f * getSecondsPerFrame();

        // draw the scene (after we draw the shadows, so everything layers correctly)
       drawObjects();

        // Place the light.  Light will move with the rest of the scene
        setLightPosition(GL11.GL_LIGHT1, lightPosition);

		// render some text using texture-mapped font
	
    }
    byte fly[] = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03,
			(byte) 0x80, 0x01, (byte) 0xC0, 0x06, (byte) 0xC0, 0x03, 0x60,
			0x04, 0x60, 0x06, 0x20, 0x04, 0x30, 0x0C, 0x20, 0x04, 0x18, 0x18,
			0x20, 0x04, 0x0C, 0x30, 0x20, 0x04, 0x06, 0x60, 0x20, 0x44, 0x03,
			(byte) 0xC0, 0x22, 0x44, 0x01, (byte) 0x80, 0x22, 0x44, 0x01,
			(byte) 0x80, 0x22, 0x44, 0x01, (byte) 0x80, 0x22, 0x44, 0x01,
			(byte) 0x80, 0x22, 0x44, 0x01, (byte) 0x80, 0x22, 0x44, 0x01,
			(byte) 0x80, 0x22, 0x66, 0x01, (byte) 0x80, 0x66, 0x33, 0x01,
			(byte) 0x80, (byte) 0xCC, 0x19, (byte) 0x81, (byte) 0x81,
			(byte) 0x98, 0x0C, (byte) 0xC1, (byte) 0x83, 0x30, 0x07,
			(byte) 0xe1, (byte) 0x87, (byte) 0xe0, 0x03, 0x3f, (byte) 0xfc,
			(byte) 0xc0, 0x03, 0x31, (byte) 0x8c, (byte) 0xc0, 0x03, 0x33,
			(byte) 0xcc, (byte) 0xc0, 0x06, 0x64, 0x26, 0x60, 0x0c,
			(byte) 0xcc, 0x33, 0x30, 0x18, (byte) 0xcc, 0x33, 0x18, 0x10,
			(byte) 0xc4, 0x23, 0x08, 0x10, 0x63, (byte) 0xC6, 0x08, 0x10, 0x30,
			0x0c, 0x08, 0x10, 0x18, 0x18, 0x08, 0x10, 0x00, 0x00, 0x08 };
    ByteBuffer flyBuffer  = ByteBuffer.allocateDirect(128);
	
    public void drawObjects() {
        // draw the airplane
     
    	flyBuffer .rewind();
    	// draw the earth
        GL11.glPushMatrix();
        {
        	//billboardPoint(airplanePos, ORIGIN, UP);
            GL11.glRotatef(rotation, 0, 1, 0);  // rotate around Y axis
            GL11.glScalef(1f, 1f, 1f);          // scale up
            
            GL11.glEnable(GL11.GL_POLYGON_STIPPLE);
			GL11.glPolygonStipple(flyBuffer );
			GL11.glDisable(GL11.GL_POLYGON_STIPPLE);
            //GL11.glBindTexture(GL11.GL_TEXTURE_2D, sphereTextureHandle);
            callDisplayList(earth);
        }
        GL11.glPopMatrix();
    }
   
}
