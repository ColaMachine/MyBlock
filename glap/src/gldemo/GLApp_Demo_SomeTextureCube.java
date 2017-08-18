package gldemo;

import java.nio.FloatBuffer;

import cola.machine.game.myblocks.model.ImageBlock;
import com.dozenx.game.engine.Role.bean.Player;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;

import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.repository.BlockRepository;
import glapp.*;
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
public class GLApp_Demo_SomeTextureCube extends GLApp {
	// Handle for texture
	int sphereTextureHandle = 0;
	int groundTextureHandle = 0;
	int humanTextureHandle = 0;
	MouseControlCenter mouseControlCenter;
	// Light position: if last value is 0, then this describes light direction.
	// If 1, then light position.
	float lightPosition[] = { -2f, 2f, 2f, 0f };
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
	int earth;

	// shadow handler will draw a shadow on floor plane
	// GLShadowOnPlane airplaneShadow;

	public GL_Vector airplanePos;

	FloatBuffer bbmatrix = GLApp.allocFloats(16);

	private BlockRepository blockRepository = new BlockRepository(null);

	private Player player = new Player(2);
	private Player player2 = new Player(1);

	/**
	 * Start the application. run() calls setup(), handles mouse and keyboard
	 * input, calls render() in a loop.
	 */
	public static void main(String args[]) {
		// create the app
		GLApp_Demo_SomeTextureCube demo = new GLApp_Demo_SomeTextureCube();
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
		
		
		setPerspective();
		setLight(GL11.GL_LIGHT1, new float[] { 1f, 1f, 1f, 1f }, new float[] {
				0.5f, 0.5f, .53f, 1f }, new float[] { 1f, 1f, 1f, 1f },
				lightPosition);
		// Create a directional light (light green, to simulate reflection off
		// grass)
		setLight(GL11.GL_LIGHT2, new float[] { 0.15f, 0.4f, 0.1f, 1.0f }, // diffuse
																			// color
				new float[] { 0.0f, 0.0f, 0.0f, 1.0f }, // ambient
				new float[] { 0.0f, 0.0f, 0.0f, 1.0f }, // specular
				new float[] { 0.0f, -10f, 0.0f, 0f }); // direction (pointing
														// up)
		//dcc.blockRepository = blockRepository;
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		// Enable alpha transparency (so text will have transparent background)
		GL11.glEnable(GL11.GL_AUTO_NORMAL);
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// Create texture for spere
		sphereTextureHandle = makeTexture("assets/images/background.png");
		humanTextureHandle = makeTexture("assets/images/2000.png");
		groundTextureHandle = makeTexture("assets/images/background.png", true, true);

		// set camera 1 position
		camera1.setCamera(0, 4, 15, 0, 0f, -1, 0, 1, 0);
		player.setHuman(0, 2, 0, 0, 0, 1, 0, 1, 0);

		player2.setHuman(20, 2, 20, 0, 0, 1, 0, 1, 0);

		/*human.startWalk();*/
		
		//mouseControlCenter= new MouseControlCenter(player,cam.camera);
		mouseControlCenter.centerX=this.displayWidth/2;
		mouseControlCenter.centerY=this.displayHeight/2;
		mouseControlCenter.player = player;
		mouseControlCenter.camera= camera1;
		// load the airplane model and make it a display list
		// = new GLModel("models/JetFire/JetFire.obj");
		// airplane.mesh.regenerateNormals();
		// airplane.makeDisplayList();

		// human.move(2, 12, 2);
		// make a sphere display list
		earth = beginDisplayList();
		// ѭ������
		IBlock block = new ImageBlock("water",1,false);
		for (int j = 1; j < 20; j += 2)
			for (int i = 1; i < 20; i += 2) {
				block.setCenter(i, 1, j);
				blockRepository.put(block);
				block.renderCube();
			}
		{
			// renderCube();
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

	}

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
		
		//dcc.check(player);
		
		//mouseControlCenter.handleNavKeys((float) GLApp.getSecondsPerFrame());

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

	
		// ������ﱳ��ĵ�
		GL_Vector camera_pos = GL_Vector.add(player.position,
				GL_Vector.multiply(player.viewDir, -10));
		camera1.MoveTo(camera_pos.x, camera_pos.y + 4, camera_pos.z);
		camera1.viewDir(player.viewDir);
		cam.render();

		GL11.glPushMatrix();
		{
			GL11.glTranslatef(0f, -3f, 0f); // down a bit
			GL11.glScalef(15f, .01f, 15f);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, groundTextureHandle);
			renderCube();
		}
		GL11.glPopMatrix();

		

		
		drawObjects();

		// Place the light. Light will move with the rest of the scene
		setLightPosition(GL11.GL_LIGHT1, lightPosition);

	
//		print(30, viewportH - 140, "SPACE key switches cameras", 1);

		//this.drawLine();
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
			//player.render();

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
			//player2.render();
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
		//mouseControlCenter.mouseUp(x, y);
	}

	/**
	 * Add last mouse motion to the line, only if left mouse button is down.
	 */
	public void mouseDown(int x, int y) {
		//mouseControlCenter.mouseDown(x, y);

	}

	/*
	

	

	// һ�����¼���߷���
	GL_Vector lineStart = new GL_Vector(0, 0, 0);

	// һ�����¼���߷���
	GL_Vector mouseDir = new GL_Vector(0, 1, 0);
	// һ�����¼���߽����

	GL_Vector mouseEnd = new GL_Vector(0, 5, 0);

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
		
	}*/
}
