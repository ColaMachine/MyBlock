package gldemo;

import glapp.GLApp;
import glapp.GLCam;
import glapp.GLCamera;
import glmodel.GL_Vector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Render a scene with two cameras, one still and one moving.  Hit SPACE to
 * toggle viewpoints between the two cameras.  Uses the GLCamera class to
 * hold camera position and orientation, and the GLCam class to move the
 * current camera in response to arrow key events.
 * <P>
 * @see GLCamera.java
 * @see GLCam.java
 * <P>
 * napier at potatoland dot org
 */
public class GLApp_Demo_Select extends GLApp {
    // Handle for texture
    int sphereTextureHandle = 0;
    // Light position: if last value is 0, then this describes light direction.  If 1, then light position.
    float lightPosition[]= { -2f, 2f, 2f, 0f };
    // Camera position
    float[] cameraPos = {0f,3f,20f};

    // two cameras and a cam to move them around scene
    GLCamera camera1 = new GLCamera();
    GLCam cam = new GLCam(camera1);

    // vectors used to orient airplane motion
    GL_Vector UP = new GL_Vector(0,1,0);
    GL_Vector ORIGIN = new GL_Vector(0,0,0);

    // for earth rotation
    float degrees = 0;

    // model of airplane and sphere displaylist for earth
    int earth;

    // shadow handler will draw a shadow on floor plane

    
	FloatBuffer bbmatrix = GLApp.allocFloats(16);

    /**
     * Initialize application and run main loop.
     */
    public static void main(String args[]) {
    	GLApp_Demo_Select demo = new GLApp_Demo_Select();
        demo.VSyncEnabled = true;
        demo.fullScreen = false;
        demo.displayWidth = 800;
        demo.displayHeight = 600;
        demo.run();  // will call init(), render(), mouse functions
    }

    /**
     * Initialize the scene.  Called by GLApp.run()
     */
    public void setup()
    {
        // setup and enable perspective
        setPerspective();

        // Create a light (diffuse light, ambient light, position)
       
       

        
        // enable lighting and texture rendering
       // GL11.glEnable(GL11.GL_LIGHTING);

        // Enable alpha transparency (so text will have transparent background)
       // GL11.glEnable(GL11.GL_BLEND);
       // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Create texture for spere

        // Create texture for ground plane

        // set camera 1 position
        camera1.setCamera(0,0,15, 0,0f,-1, 0,1,0);

        // load the airplane model and make it a display list

     

        // make a shadow handler
        // params:
        //		the light position,
        //		the plane the shadow will fall on,
        //		the color of the shadow,
        // 		this application,
        // 		the function that draws all objects that cast shadows
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
                           500f);       // max Z: how far from eye position does view extend
        // return to modelview matrix
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    /**
     * Render one frame.  Called by GLApp.run().
     */
    public void draw() {

        // place airplane in orbit around ball, and place camera slightly above airplane

    	// align airplane and camera2 (perpendicular to the radius and up vector)

        // user keystrokes adjust camera position

        // combine user camera motion with current camera position (so user can look around while on the airplane)

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // select model view for subsequent transforms
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        // do gluLookAt() with camera position, direction, orientation
        cam.render();

      
        // draw the scene (after we draw the shadows, so everything layers correctly)
        drawObjects();

        // Place the light.  Light will move with the rest of the scene
        setLightPosition(GL11.GL_LIGHT1, lightPosition);

		// render some text using texture-mapped font
		print( 30, viewportH- 45, "Use arrow keys to navigate:");
        print( 30, viewportH- 80, "Left-Right arrows rotate camera", 1);
        print( 30, viewportH-100, "Up-Down arrows move camera forward and back", 1);
        print( 30, viewportH-120, "PageUp-PageDown move vertically", 1);
        print( 30, viewportH-140, "SPACE key switches cameras", 1);
    }

    public void drawObjects() {
        drawSelect2(false);
    }
    
    public void SelectObject(int x,int y){
  	  IntBuffer selectBuff = ByteBuffer.allocateDirect((Integer.SIZE/8)*16).order(ByteOrder.nativeOrder()).asIntBuffer();
	      int hits;
	      IntBuffer viewport = GLApp.getViewport();
	      //GL11.glGetInteger(GL11.GL_VIEWPORT,viewport);
	      GL11.glSelectBuffer(selectBuff);
	      GL11.glRenderMode(GL11.GL_SELECT);    //����ѡ��ģʽ    
	      GL11.glInitNames();  //��ʼ������ջ    

	      
	      GL11.glPushName(0);  //������ջ�з���һ����ʼ�����֣�����Ϊ��0��    
	      
	      
	      
	      GL11. glMatrixMode(GL11.GL_PROJECTION);    //����ͶӰ�׶�׼��ʰȡ    
	      
	      GL11. glPushMatrix();     //������ǰ��ͶӰ����    
	      
	      GL11. glLoadIdentity();   //���뵥λ����    
	      
	      FloatBuffer m = ByteBuffer.allocateDirect((Float.SIZE/8)*16).order(ByteOrder.nativeOrder()).asFloatBuffer();
	      
	      
	      
	      GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, m);    
	      
	      
	      
	     GLU.gluPickMatrix( x,           // �趨����ѡ���Ĵ�С������ʰȡ���󣬾�������Ĺ�ʽ  viewport[3]-y,    // viewport[3]������Ǵ��ڵĸ߶ȣ���������ת��ΪOpenGL����    
	      y,
	       4,4,              // ѡ���Ĵ�СΪ2��2    
	      
	       viewport          // �ӿ���Ϣ�������ӿڵ���ʼλ�úʹ�С    
	      
	    );        
	      
	                                                            
	      
	     	GL11. glGetFloat(GL11.GL_PROJECTION_MATRIX, m);    
	      
	     	GL11.glOrtho(-10, 10, -10, 10, -10, 10);     //ʰȡ�������ͶӰ���������Ϳ�����ѡ���Ŵ�Ϊ������һ����    
	      
	    	GL11.   glGetFloat( GL11.GL_PROJECTION_MATRIX, m);    
	      
	      
	      
	    	drawSelect2(true);    // �ú�������Ⱦ���壬���Ҹ������趨����    
	      
	            
	      
	        GL11.glMatrixMode( GL11.GL_PROJECTION);    
	      
	        GL11.glPopMatrix();  // ����������ͶӰ�任    
	      
	        GL11. glGetFloat( GL11.GL_PROJECTION_MATRIX, m);    
	      
	        hits =  GL11.glRenderMode( GL11.GL_RENDER); // ��ѡ��ģʽ��������ģʽ,�ú�������ѡ�񵽶���ĸ���    
	        if(hits > 0)    
	        	System.out.println("ѡ�м���"+hits+"����:"+selectBuff.get(1)+"id"+selectBuff.get(3)+"��2������"+selectBuff.get(2)+"��0������"+selectBuff.get(0));
	            processSelect(selectBuff);  //  ѡ��������    
	      
	        }    

    public void drawSelect2(boolean flag){   
        if(flag)    
        {    
            GL11.glColor3f(1.0f,0.0f,0.0f);    
  
           GL11.glLoadName(100);    
            GL11.glPushMatrix();    
  
            GL11.glTranslatef(-5f, 0.0f, 10.0f);    
  
            GL11.glBegin(GL11.GL_QUADS);    
  
            GL11.glVertex3f(-1, -1, 0);    
  
            GL11.glVertex3f( 1, -1, 0);    
  
            GL11.glVertex3f( 1, 1, 0);    
  
            GL11.glVertex3f(-1, 1, 0);    
  
            GL11.glEnd();    
  
            GL11.glPopMatrix();    
    
  
            GL11. glColor3f(0.0f,0.0f,1.0f);    
           GL11.glLoadName(102);    
  
            GL11. glPushMatrix();    
  
            GL11.glTranslatef(5f, 0.0f, -10.0f);    
  
            GL11.glBegin(GL11.GL_QUADS);    
  
            GL11.glVertex3f(-1, -1, 0);    
  
            GL11.glVertex3f( 1, -1, 0);    
  
            GL11.glVertex3f( 1, 1, 0);    
  
            GL11.glVertex3f(-1, 1, 0);    
  
            GL11.glEnd();    
  
            GL11.glPopMatrix();    
        }    
  
        else    
  
    {    
  
        	GL11.glColor3f(1.0f,0.0f,0.0f);    
  
        	GL11.glPushMatrix();    
  
        	GL11.glTranslatef(-5f, 0.0f, -5.0f);    
  
        	GL11.glBegin(GL11.GL_QUADS);    
  
        	GL11.glVertex3f(-1, -1, 0);    
  
        	GL11.glVertex3f( 1, -1, 0);    
  
        	GL11.glVertex3f( 1, 1, 0);    
  
        	GL11.glVertex3f(-1, 1, 0);    
  
        	GL11. glEnd();    
  
        	GL11.glPopMatrix();    
  
        
  
        	GL11.glColor3f(0.0f,0.0f,1.0f);    
  
        	GL11.glPushMatrix();    
  
        	GL11.glTranslatef(-5f, 0.0f, -10.0f);    
  
        	GL11.glBegin(GL11.GL_QUADS);    
  
        	GL11.glVertex3f(-1, -1, 0);    
  
        	GL11.glVertex3f( 1, -1, 0);    
  
        	GL11.glVertex3f( 1, 1, 0);    
  
        	GL11.glVertex3f(-1, 1, 0);    
  
        	GL11.glEnd();    
  
        	GL11.glPopMatrix();    
  
        }   }
    public void processSelect(IntBuffer selectBuff){
     
    }

	
	
    public void mouseMove(int x, int y) {
    }

    public void mouseDown(int x, int y) {//System.out.println("mousedown");
    	SelectObject(x,y);
    }

    public void mouseUp(int x, int y) {
    }

    public void keyDown(int keycode) {
    	if (Keyboard.KEY_SPACE == keycode) {
    		cam.setCamera( camera1);
    	}
    }

    public void keyUp(int keycode) {
    }
}