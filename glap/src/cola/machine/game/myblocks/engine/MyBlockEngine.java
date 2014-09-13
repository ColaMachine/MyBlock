package cola.machine.game.myblocks.engine;

import glapp.GLApp;
import glapp.GLCam;
import glapp.GLCamera;
import glapp.GLImage;
import glmodel.GL_Vector;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import time.Time;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.item.weapons.Sword;
import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.model.ui.NuiManager;
import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.persistence.impl.StorageManagerInternal;
import cola.machine.game.myblocks.physic.BulletPhysics;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;
import cola.machine.game.myblocks.rendering.world.WorldRendererLwjgl;
import cola.machine.game.myblocks.repository.BlockRepository;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.block.internal.BlockManagerImpl;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.LocalChunkProvider;
import cola.machine.game.myblocks.world.chunks.Vector3i;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.generator.WorldGenerator;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.HeightMapWorldGenerator;
import cola.machine.game.myblocks.world.internal.WorldProviderWrapper;

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
	public String currentObject = "water";
	int skyTextureHandle = 0;
	int waterTextureHandle = 0;
	int crossTextureHandle = 0;
	GLImage textureImg;
	Time time =new Time();
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
	public int waterDisplay;
	public Sword sword;
	// shadow handler will draw a shadow on floor plane
	// GLShadowOnPlane airplaneShadow;

	public GL_Vector airplanePos;

	FloatBuffer bbmatrix = GLApp.allocFloats(16);

	public BlockRepository blockRepository = new BlockRepository(this);
	BulletPhysics bulletPhysics;
	public Human human;
	private Human human2;

	/**
	 * Start the application. run() calls setup(), handles mouse and keyboard
	 * input, calls render() in a loop.
	 */
	
	WorldRenderer worldRenderer ;
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
		
	
		human = new Human(blockRepository);
		//sword=new Sword(0,0,0);
		human2 = new Human(blockRepository);
		CoreRegistry.put(MyBlockEngine.class, this);
		CoreRegistry.put(Human.class, human);
		this.initManagers();
		setPerspective();
		/*
		 * setLight(GL11.GL_LIGHT1, new float[] { 100f, 100f, 100f, 1.0f}, new
		 * float[] { 1f, 1f, 1f, 1f }, new float[] { 1f,1f, 1f, 1f },
		 * lightPosition);
		 */
		// Create a directional light (light green, to simulate reflection off
		// grass)
		/*
		 * setLight(GL11.GL_LIGHT2, new float[] { 100f, 100f, 100f, 1.0f}, //
		 * diffuse // color new float[] { 1f, 1f, 1f, 1f }, // ambient new
		 * float[] { 1f, 1f, 1f, 1f }, // specular new float[] { 5f, 10f, 5f, 0f
		 * }); // direction (pointing
		 */
		// up)

		// set global light
		FloatBuffer ltAmbient = allocFloats(new float[] { 11.0f, 11.0f, 11.0f,
				1.0f });
		// ltAmbient.flip();
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, ltAmbient);
		GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, GL11.GL_FALSE);
		dcc.blockRepository = blockRepository;
		bulletPhysics = new BulletPhysics(blockRepository);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		// Enable alpha transparency (so text will have transparent background)
		GL11.glEnable(GL11.GL_AUTO_NORMAL);
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// Create texture for spere
		sphereTextureHandle = makeTexture("images/background.png");
		humanTextureHandle = makeTexture("images/2000.png");
		skyTextureHandle = makeTexture("images/sky180.png");
		crossTextureHandle = makeTexture("images/gui.png");
		waterTextureHandle = TextureManager.getIcon("water").textureHandle;
		textureImg = loadImage("images/gui.png");
		// set camera 1 position
		camera1.setCamera(5, 20, 5, 0, 0f, -1, 0, 1, 0);
		human.setHuman(1, 12, 1, 0, 0, -1, 0, 1, 0);

		human2.setHuman(10, 3, 10, 0, 0, 1, 0, 1, 0);

		human.startWalk();

		mouseControlCenter = new MouseControlCenter(human, camera1, this);
		mouseControlCenter.bulletPhysics = bulletPhysics;
		
		/*for (int i = 0; i < 50; i++)
			for (int j = 0; j < 50; j++) {
					Block soil = new BaseBlock("soil",2 * i + 1, 2 * 0 + 1, 2 * j + 1);
					blockRepository.put(soil);
			}
		blockRepository.reBuild("soil");*/
		/*try {
			
			//blockRepository.reBuild("color");
			int[][] heights = ImageUtil.getGrayPicture("images/gray.png");

			// earth = beginDisplayList();
			for (int i = 0; i < 50; i++)
				for (int j = 0; j < 50; j++) {
					int height = heights[i][j];
					for (int y = 0; y <= height / 20; y++) {
						Block soil = new BaseBlock("soil",2 * i + 1, 2 * y + 1, 2 * j + 1);
						blockRepository.put(soil);
					}
				}
			for (int i = 0; i < 50; i++)
				for (int j = 0; j < 50; j++) {
					int height = heights[i][j];
					for (int y = height / 20; y <= 5; y++) {
						Block block = new BaseBlock("water",2 * i + 1, 2 * y + 1, 2 * j + 1);
					 blockRepository.put(block);
					}
				}
			blockRepository.reBuild("soil");
			blockRepository.reBuild("water");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
*/
		int sky_x = 50;
		int sky_y = 50;
		int sky_z = 50;
		sky = beginDisplayList();
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
		// water=new Water();
		// water.setCenter(2, 2, 2);
		// org.lwjgl.input.Keyboard.enableRepeatEvents(true);
		
		StorageManager storageManager =new StorageManagerInternal();
		WorldGenerator worldGenerator =new HeightMapWorldGenerator();
		 GeneratingChunkProvider chunkProvider =new LocalChunkProvider(storageManager,worldGenerator);
		 //chunkProvider.createOrLoadChunk(new Vector3i(1,1,1));
		 CoreRegistry.put(ChunkProvider.class, chunkProvider);
		WorldProvider WorldProvider =new WorldProviderWrapper();
	
		worldRenderer=new WorldRendererLwjgl(WorldProvider,chunkProvider, new LocalPlayerSystem(),camera1,human);
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
				500f); // max Z: how far from eye position does view extend
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void draw() {
		
		dcc.check(human);

		mouseControlCenter.handleNavKeys((float) GLApp.getSecondsPerFrame());
		// cam.handleNavKeys((float)GLApp.getSecondsPerFrame());
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		GL_Vector camera_pos = GL_Vector.add(human.Position,
				GL_Vector.multiply(human.ViewDir, -10));
		camera1.MoveTo(camera_pos.x, camera_pos.y + 4, camera_pos.z);
		// camera1.MoveTo(human.Position.x, human.Position.y + 4,
		// human.Position.z);
		camera1.viewDir(human.ViewDir);
		cam.render();
		drawAllBlock();
		//drawColorBlocks();
		drawObjects();
		drawSky();
		drawLine();
		//sword.y=human.Position.y+4;
		//sword.render();
		CoreRegistry.get(NuiManager.class).render();
		if(Switcher.PRINT_SWITCH)
			printText();
		
	}

	public void drawAllBlock() {
		worldRenderer.render();
		/*java.util.Iterator it = blockRepository.handleMap.entrySet().iterator();
		while (it.hasNext()) {
			
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String name = (String) entry.getKey();
			if(!name.equals("name"))
			{
			int displayandleId = (Integer) entry.getValue();
			GL11.glPushMatrix();
			{	
				
				
				int texturehandleid= TextureManager.getIcon((String) entry.getKey()).textureHandle;
				GL11.glBindTexture(
						GL11.GL_TEXTURE_2D,
						texturehandleid);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
						GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
				callDisplayList(displayandleId);
			}GL11.glPopMatrix();
			}
			
		}
*/
	}
	/*
	public void drawColorBlocks(){
//先缩小
	
		GL11.glPushMatrix();	GL11.glTranslatef(human.Position.x,human.Position. y+4
				, human.Position.z);
		GL11.glScalef(0.1f, 0.1f, 0.1f);
		GL11.glRotated(90, 1, 0, 0);
		
		HashMap<Integer,Block> map =blockRepository.kindBlockMap.get("color");
		java.util.Iterator it1 = map.entrySet().iterator();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		while(it1.hasNext()){
			
		java.util.Map.Entry entry1 = (java.util.Map.Entry)it1.next();
		Block block = (Block) entry1.getValue();
		
		GL11.glColor3f(block.r()/256f, block.b()/256f,block. g()/256f);
		block.renderColor();
		} GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}*/
	
	public void drawSky(){
		// draw the sky
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(this.cam.camera.Position.x, 4,
					this.cam.camera.Position.z);

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glColor4f(0.5f, 0.5f, 1.0f, 0.4f);
			callDisplayList(sky);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		GL11.glPopMatrix();
	}
	public void drawObjects() {
		GL11.glPushMatrix();
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, humanTextureHandle);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			human.render();

		}
		GL11.glPopMatrix();

	

		GL11.glPushMatrix();
		{
			// GL11.glRotatef(rotation, 0, 1, 0); // rotate around Y axis
			// GL11.glScalef(1f, 1f, 1f); // scale up
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, humanTextureHandle);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			 human2.render();
		}
		GL11.glPopMatrix();
	}

	public void keyDown(int keycode) {
		/*
		 * if (Keyboard.KEY_SPACE == keycode) { cam.setCamera((cam.camera ==
		 * camera1)? camera2 : camera1); }
		 */
		mouseControlCenter.keyDown(keycode);
	}

	public void keyUp(int keycode) {
		mouseControlCenter.keyUp(keycode);
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
		if (this.mouseButtonDown(0)) {
			mouseControlCenter.mouseLClick(x, y);
		}
		if (this.mouseButtonDown(1)) {
			mouseControlCenter.mouseRClick(x, y);
		}

	}

	



	public void initSelf() {
		// TODO Auto-generated method stub

		// 暂时用默认的参数初始化manager 然后manager 放到corerepgistry里
		//

		/* read config.cfg */
		initConfig();

		initManagers();

		// load assets
		// initAssets();
		//
		// initOPFlow();
		//

	}

	private void initManagers() {
		CoreRegistry.put(BlockManager.class,
				new BlockManagerImpl());
		TextureManager textureManager = CoreRegistry.put(TextureManager.class,
				new TextureManager());
		NuiManager nuiManager = CoreRegistry.put(NuiManager.class,
				new NuiManager());
		CoreRegistry.put(BlockManager.class,
				new BlockManagerImpl());
		// AssetManager assetManager =
		// CoreRegistry.putPermanently(AssetManager.class, new
		// AssetManager(moduleManager.getEnvironment()));

	}

	public void initConfig() {
		//
	}
	public void printText(){
			print(30, viewportH - 45, "press key 0~9 choose the object");
		print(30, viewportH - 60, "press key B open package");
		print(30, viewportH - 75, "press key wasd qe walk and turn direction ");
		print(30, viewportH - 90, "press space jump ");
		print(30, viewportH - 105, "press up down look up down ");
		print(30, viewportH - 120, "mouse click create block");
		print(30,viewportH - 135, "fps:"+time.tick());
	}
		public GL_Vector lineStart = new GL_Vector(0, 0, 0);
	
		// һ����¼���߷���
		public GL_Vector mouseDir = new GL_Vector(0, 1, 0);
		// һ����¼���߽����
	
		public 	GL_Vector mouseEnd = new GL_Vector(0, 5, 0);
	
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
}
