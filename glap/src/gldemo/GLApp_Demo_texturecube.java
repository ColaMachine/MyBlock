package gldemo;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;

import glapp.*;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

/**
 * Run a bare-bones GLApp.  Draws one white triangle centered on screen.
 * <P>
 * GLApp initializes the LWJGL environment for OpenGL rendering,
 * ie. creates a window, sets the display mode, inits mouse and keyboard,
 * then runs a loop that calls draw().
 * <P>
 * napier at potatoland dot org
 */
public class GLApp_Demo_texturecube extends GLApp {
	  float lightPosition[]= { -2f, 2f, 2f, 0f };
	  int sphereTextureHandle = 0;
	    int groundTextureHandle = 0;
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
    	GLApp_Demo_texturecube demo = new GLApp_Demo_texturecube();
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
         
         
    	  GL11.glEnable(GL11.GL_LIGHTING);
          GL11.glEnable(GL11.GL_TEXTURE_2D);

          // Enable alpha transparency (so text will have transparent background)
          GL11.glEnable(GL11.GL_AUTO_NORMAL);
          //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

          // Create texture for spere
          sphereTextureHandle = makeTexture("images/background.png");
          
          groundTextureHandle = makeTexture("images/background.png",true,true);

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

        GL11.glPushMatrix();
        {
            GL11.glTranslatef(0f, -3f, 0f); // down a bit
            GL11.glScalef(15f, .01f, 15f);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, groundTextureHandle);
            renderCube();
        }
        GL11.glPopMatrix();

        // invokes the drawObjects() method to create shadows for objects in the scene

        // draw sphere at center (rotate 10 degrees per second)
        rotation += 10f * getSecondsPerFrame();

        // draw the scene (after we draw the shadows, so everything layers correctly)
        drawObjects();

        // Place the light.  Light will move with the rest of the scene
        setLightPosition(GL11.GL_LIGHT1, lightPosition);

		// render some text using texture-mapped font
	
    }

    public void drawObjects() {
        // draw the airplane
        GL11.glPushMatrix();
        {
        	// place plane at orbit point, and orient it toward origin
        	//billboardPoint(airplanePos, ORIGIN, UP);
        	// turn plane toward direction of motion
            GL11.glRotatef(-90, 0, 1, 0);
            // make it big
            GL11.glScalef(4f, 4f, 4f);
        	// reset material, since model.render() will alter current material settings
            setMaterial( new float[] {.8f, .8f, .7f, 1f}, .4f);
        }
        GL11.glPopMatrix();

    	// draw the earth
        GL11.glPushMatrix();
        {
            GL11.glRotatef(rotation, 0, 1, 0);  // rotate around Y axis
            GL11.glScalef(2f, 2f, 2f);          // scale up
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, sphereTextureHandle);
            callDisplayList(earth);
        }
        GL11.glPopMatrix();
    }
    /**
	 * Given position of object and target, create matrix to
	 * orient object so it faces target.
	 */
	public void billboardPoint(GL_Vector bbPos, GL_Vector targetPos, GL_Vector targetUp)
	{
		// direction the billboard will be facing (looking):
		GL_Vector look = GL_Vector.sub(targetPos,bbPos).normalize();
		
		// billboard Right vector is perpendicular to Look and Up (the targets Up vector)
		GL_Vector right = GL_Vector.crossProduct(targetUp,look).normalize();
		
		// billboard Up vector is perpendicular to Look and Right
		GL_Vector up = GL_Vector.crossProduct(look,right).normalize();
		
		// Create a 4x4 matrix that will orient the object at bbPos to face targetPos
		GL_Matrix.createBillboardMatrix(bbmatrix, right, up, look, bbPos);
		
		// apply the billboard matrix
		GL11.glMultMatrix(bbmatrix);
	}
}
