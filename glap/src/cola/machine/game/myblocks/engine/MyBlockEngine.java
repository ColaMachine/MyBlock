package cola.machine.game.myblocks.engine;

import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import cola.machine.game.myblocks.model.liquid.Water;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Dimension;
import org.lwjgl.util.glu.*;

import util.ImageUtil;
import util.OpenglUtil;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.physic.BulletPhysics;
import cola.machine.game.myblocks.repository.BlockRepository;
import glapp.*;
import glmodel.GLModel;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

/**
 * Run a bare-bones GLApp. Draws one white triangle centered on screen.
 * <P>
 * GLApp initializes the LWJGL environment for OpenGL rendering, ie. creates a
 * window, sets the display mode, inits mouse and keyboard, then runs a loop
 * that calls draw().
 * <P>
 * napier at potatoland dot org
 */
public class MyBlockEngine extends GLApp {
	// Handle for texture
	int sphereTextureHandle = 0;
	int humanTextureHandle = 0;
	int skyTextureHandle = 0;
	MouseControlCenter mouseControlCenter;
	// Light position: if last value is 0, then this describes light direction.
	// If 1, then light position.
	float lightPosition[] = { 5f, 10f, 5f, 0f };
	// Camera position
	float[] cameraPos = { 0f, 3f, 20f };

	// two cameras and a cam to move them around scene
	GLCamera camera1 = new GLCamera();
	GLCamera camera2 = new GLCamera();
	GLCam cam = new GLCam(camera1);
	DropControlCenter dcc = new DropControlCenter();

	// vectors used to orient airplane motion
	GL_Vector UP = new GL_Vector(0, 1, 0);
	GL_Vector ORIGIN = new GL_Vector(0, 0, 0);

	// for earth rotation
	float degrees = 0;

	// model of airplane and sphere displaylist for earth
	// GLModel airplane;
	public int earth;
	public int sky;

	// shadow handler will draw a shadow on floor plane
	// GLShadowOnPlane airplaneShadow;

	public GL_Vector airplanePos;

	FloatBuffer bbmatrix = GLApp.allocFloats(16);

	public BlockRepository blockRepository = new BlockRepository(this);
	BulletPhysics bulletPhysics;
	private Human human ;
	private Human human2 ;

	/**
	 * Start the application. run() calls setup(), handles mouse and keyboard
	 * input, calls render() in a loop.
	 */
	public static void main(String args[]) {

		// create the app
		MyBlockEngine demo = new MyBlockEngine();
		demo.VSyncEnabled = true;
		demo.fullScreen = false;
		demo.displayWidth = 800;
		demo.displayHeight = 600;

		demo.run(); // will call init(), render(), mouse functions
	}

	/**
	 * Initialize the scene. Called by GLApp.run(). For now the default settings
	 * will be fine, so no code here.
	 */
	public void setup() {
		human= new Human(blockRepository);
		human2= new Human(blockRepository);
		setPerspective();
		/*setLight(GL11.GL_LIGHT1, new float[] { 100f, 100f, 100f, 1.0f}, new float[] {
				1f, 1f, 1f, 1f }, new float[] { 1f,1f, 1f, 1f },
				lightPosition);*/
		// Create a directional light (light green, to simulate reflection off
		// grass)
		/*setLight(GL11.GL_LIGHT2, new float[] { 100f, 100f, 100f, 1.0f}, // diffuse
																			// color
				new float[] { 1f, 1f, 1f, 1f }, // ambient
				new float[] { 1f, 1f, 1f, 1f }, // specular
				new float[] { 5f, 10f, 5f, 0f }); // direction (pointing*/
														// up)

        //set global light
        FloatBuffer ltAmbient = allocFloats(new float[]{11.0f,11.0f,11.0f,1.0f});
       // ltAmbient.flip();
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT,ltAmbient);
        GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE,GL11.GL_FALSE);
		dcc.blockRepository = blockRepository;
		bulletPhysics= new BulletPhysics(blockRepository);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		// Enable alpha transparency (so text will have transparent background)
		GL11.glEnable(GL11.GL_AUTO_NORMAL);
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// Create texture for spere
		sphereTextureHandle = makeTexture("images/background.png");
		humanTextureHandle = makeTexture("images/2000.png");
		skyTextureHandle= makeTexture("images/sky180.png");
		// set camera 1 position
		camera1.setCamera(5, 20, 5, 0, 0f, -1, 0, 1, 0);
		human.setHuman(5, 4, 5, 0, 0, -1, 0, 1, 0);

		human2.setHuman(4, 2, 20, 0, 0, 1, 0, 1, 0);

		human.startWalk();
		
		mouseControlCenter= new MouseControlCenter(human,camera1,this);
		mouseControlCenter.bulletPhysics=bulletPhysics;
		// load the airplane model and make it a display list
		// = new GLModel("models/JetFire/JetFire.obj");
		// airplane.mesh.regenerateNormals();
		// airplane.makeDisplayList();

		// human.move(2, 12, 2);
		// make a sphere display list
		
		/*try {
			int[][] heights =ImageUtil.getGrayPicture("d:/graymap.png");
			
			earth = beginDisplayList();
			// ѭ������
			for (int i = 0; i < 50; i ++)
				for (int j = 0; j < 50; j ++) {
					int height=heights[i][j];
for(int y=0;y<=height/10;y++){
	Block block = new Block();
	block.setCenter(2*i+1,y, 2*j+1);
	blockRepository.put(block);
	block.renderCube();	
					}
				
				}
			{
				// renderCube();
			}
			endDisplayList();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		earth = beginDisplayList();
		// ѭ������
		
		for (int j = 1; j < 20; j += 2)
			for (int i = 1; i < 20; i += 2) {
				Block block = new Block();
				block.setCenter(i, 1, j);
				blockRepository.put(block);
				block.renderCube();
			}
		{
			// renderCube();
		}
		endDisplayList();
		
		sky = beginDisplayList(); {
        	renderSphere();
        }
        endDisplayList();
		// make a shadow handler
		// params:
		// the light position,
		// the plane the shadow will fall on,
		// the color of the shadow,
		// this application,
		// the function that draws all objects that cast shadows
		// airplaneShadow = new GLShadowOnPlane(lightPosition, new float[]
		// {0f,1f,0f,3f}, null, this, method(this,"drawObjects"));
        water=new Water();
        water.setCenter(2, 2, 2);
	}
    Water water ;
	/**
	 * set the field of view and view depth.
	 */
	public static void setPerspective() {
		// select projection matrix (controls perspective)
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// fovy, aspect ratio, zNear, zFar
		GLU.gluPerspective(50f, // zoom in or out of view
				aspectRatio, // shape of viewport rectangle
				.1f, // Min Z: how far from eye position does view start
				100f); // max Z: how far from eye position does view extend
		// return to modelview matrix

		// GL11.glOrtho(15, 32, 0, 20, 15, 32);
		// GL11.glOrtho(16.5, 19.5, -2, 2, 16.5, 19.5);
		// GL11.glOrtho(-10, 10, -10, 10, -10, 10);

		// GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	

	public void draw() {
		
		dcc.check(human);
		
		mouseControlCenter.handleNavKeys((float) GLApp.getSecondsPerFrame());
		//  cam.handleNavKeys((float)GLApp.getSecondsPerFrame());
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

	
		// ������ﱳ��ĵ�
		GL_Vector camera_pos = GL_Vector.add(human.Position,
			GL_Vector.multiply(human.ViewDir, -10));
		camera1.MoveTo(camera_pos.x, camera_pos.y + 4, camera_pos.z);
		//camera1.MoveTo(human.Position.x, human.Position.y + 4, human.Position.z);
		camera1.viewDir(human.ViewDir);
		cam.render();

		
		

		
		drawObjects();

		// Place the light. Light will move with the rest of the scene
		//setLightPosition(GL11.GL_LIGHT1, lightPosition);

	
//		print(30, viewportH - 140, "SPACE key switches cameras", 1);

		this.drawLine();
        this.drawWater();
	}

	public void drawObjects() {
		// draw the earth
		GL11.glPushMatrix();
		{// setOrthoOn();
			// GL11.glRotatef(rotation, 0, 1, 0); // rotate around Y axis
			// GL11.glScalef(1/4f, 1/4f, 1/4f); // scale up
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, sphereTextureHandle);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			callDisplayList(earth);
			// setOrthoOff();
		}
		GL11.glPopMatrix();
		
		//����һ����
		GL11.glPushMatrix();
		{
			// GL11.glRotatef(rotation, 0, 1, 0); // rotate around Y axis
			// GL11.glScalef(1/2f, 1/2f, 1/2f); // scale up
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, humanTextureHandle);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			human.render();

		}
		GL11.glPopMatrix();
		
		
		// draw the earth
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(this.cam.camera.Position.x, 4, this.cam.camera.Position.z);
           // GL11.glScalef(5f, 5f,5f);
            GL11.glScalef(40f, 15f,40f);          // scale up
          // GL11.glBindTexture(GL11.GL_TEXTURE_2D, skyTextureHandle);

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glFrontFace(GL11.GL_CW);
           //GL11.glDisable(GL11.GL_LIGHT_MODEL_AMBIENT);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11. glColor4f(0.5f, 0.5f, 1.0f, 0.4f);
           // GL11.glCullFace(GL11.GL_BACK);
           // GL11.glColor3f(0.835f,0.78125f, 0.94921875f);
            callDisplayList(sky);
            GL11.glFrontFace(GL11.GL_CCW);
           // GL11.glEnable(GL11.GL_LIGHT1);
            GL11.glEnable(GL11.GL_LIGHTING);
           // GL11.glEnable(GL11.GL_LIGHT_MODEL_AMBIENT);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        GL11.glPopMatrix();
        
		//���ڶ�����
		GL11.glPushMatrix();
		{
			// GL11.glRotatef(rotation, 0, 1, 0); // rotate around Y axis
			// GL11.glScalef(1f, 1f, 1f); // scale up
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, humanTextureHandle);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			//human2.render();
		}
		GL11.glPopMatrix();
	}

	

	public void keyDown(int keycode) {
		/*
		 * if (Keyboard.KEY_SPACE == keycode) { cam.setCamera((cam.camera ==
		 * camera1)? camera2 : camera1); }
		 */
		mouseControlCenter.keyDown( keycode);
	}

	public void keyUp(int keycode) {
		mouseControlCenter.keyUp( keycode);
	}

	/**
	 * Add last mouse motion to the line, only if left mouse button is down.
	 */
	public void mouseMove(int x, int y) {
		mouseControlCenter.mouseMove(x, y);
	}

	/**
	 * Add last mouse motion to the line, only if left mouse button is down.
	 */
	public void mouseUp(int x, int y) {
		mouseControlCenter.mouseUp(x, y);
	}

	/**
	 * Add last mouse motion to the line, only if left mouse button is down.
	 */
	public void mouseDown(int x, int y) {
		if(this.mouseButtonDown(0)){
			mouseControlCenter.mouseLClick(x, y);
		}
		if(this.mouseButtonDown(1)){
			mouseControlCenter.mouseRClick(x, y);
		}

	}

	
	
	

	// һ�����¼���߷���
	public GL_Vector lineStart = new GL_Vector(0, 0, 0);

	// һ�����¼���߷���
	GL_Vector mouseDir = new GL_Vector(0, 1, 0);
	// һ�����¼���߽����

	public GL_Vector mouseEnd = new GL_Vector(0, 5, 0);

	public void drawLine() {
		
	
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(12f);
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glBegin(GL11.GL_LINES); // draw triangles
		GL11.glVertex3f(lineStart.x, lineStart.y,
				lineStart.z); // A1-A2
		GL11.glVertex3f(mouseEnd.x, mouseEnd.y, mouseEnd.z);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
	}

    public void drawWater(){
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glTranslated(5, 2, 5);
        GL11. glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11. GL_ONE_MINUS_SRC_ALPHA);
        GL11. glColor4f(0.5f, 0.5f, 1.0f, 0.4f);
        GL11. glColor4f(0.5f, 0.5f, 1.0f, 0.4f);
        water.renderCube();
        GL11.glDisable(GL11.GL_BLEND);
        GL11. glColor4f(1.0f, 1.0f, 1.0f,1f);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    public void drawSky(){
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glTranslated(5, 2, 5);
        GL11. glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11. GL_ONE_MINUS_SRC_ALPHA);
        GL11. glColor4f(0.5f, 0.5f, 1.0f, 0.4f);
        GL11. glColor4f(0.5f, 0.5f, 1.0f, 0.4f);
        water.renderCube();
        GL11.glDisable(GL11.GL_BLEND);
        GL11. glColor4f(1.0f, 1.0f, 1.0f,1f);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
    }
}
