package cola.machine.game.myblocks.engine;

import cola.machine.game.myblocks.model.textture.TextureInfo;
import glapp.*;
import glmodel.GLModel;
import glmodel.GL_Vector;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.terasology.engine.subsystem.EngineSubsystem;
import org.terasology.engine.subsystem.lwjgl.LwjglAudio;
import org.terasology.engine.subsystem.lwjgl.LwjglGraphics;
import org.terasology.engine.subsystem.lwjgl.LwjglInput;
import org.terasology.engine.subsystem.lwjgl.LwjglTimer;

import com.google.common.collect.Lists;

import time.Time;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.item.weapons.Sword;
import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.magicbean.fireworks.Firework;
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
import cola.machine.game.myblocks.resource.ResourceManager;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.Skysphere;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.block.internal.BlockManagerImpl;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.LocalChunkProvider;
import cola.machine.game.myblocks.world.chunks.Vector3i;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.generator.WorldGenerator;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.HeightMapWorldGenerator;
import cola.machine.game.myblocks.world.generator.WorldGenerators.PerlinWorldGenerator;
import cola.machine.game.myblocks.world.internal.WorldProviderWrapper;
import util.MathUtil;

/**
 * Run a bare-bones GLApp. Draws one white triangle centered on screen.
 * <p/>
 * GLApp initializes the LWJGL environment for OpenGL rendering, ie. creates a
 * window, sets the display mode, inits mouse and keyboard, then runs a loop
 * that calls draw().
 * <p/>
 * napier at potatoland dot org
 */
public class MyBlockEngine extends GLApp {
    // Handle for texture
    int sphereTextureHandle = 0;
    int groundTextureHandle = 0;
    int humanTextureHandle = 0;
    public String currentObject = "water";
    int skyTextureHandle = 0;
    int waterTextureHandle = 0;
    int crossTextureHandle = 0;
    GLImage textureImg;
    Time time = new Time();
    MouseControlCenter mouseControlCenter;
    // Light position: if last value is 0, then this describes light direction.
    // If 1, then light position.
    //float lightPosition[] = { 5f, 45f, 5f, 0f };
    float lightPosition[] = {-2f, 36f, 2f, 0f};
    // Camera position
    float[] cameraPos = {0f, 3f, 20f};

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
    private Skysphere skysphere = new Skysphere();
    /**
     * Start the application. run() calls setup(), handles mouse and keyboard
     * input, calls render() in a loop.
     */

    WorldRenderer worldRenderer;

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
    int handleId;
    GLShadowOnPlane airplaneShadow;

    public void setup() {
        makeTexture("images/Particle.bmp", true, true);
        airplaneShadow = new GLShadowOnPlane(lightPosition, new float[]{0f, 1f, 0f, 3f}, null, this, method(this, "drawObjects"));
        boat = new GLModel("glap/models/boat/botrbsm1.obj");
        groundTextureHandle = makeTexture("glap/images/grass_1_512.jpg", true, true);
        boat.mesh.regenerateNormals();
        boat.makeDisplayList();
        //  GL11. glEnable(GL11.GL_POINT_SMOOTH);
        //  GL11. glEnable( GL11. GL_LINE_SMOOTH);
        //  GL11. glHint( GL11. GL_POINT_SMOOTH_HINT,  GL11. GL_NICEST); // Make round points, not square points
        //  GL11. glHint( GL11. GL_LINE_SMOOTH_HINT,  GL11. GL_NICEST);  // Antialias the lines
        //GL11.glEnable(GL11.GL_DEPTH_TEST);
        // GL11.glDepthFunc(GL11.GL_LEQUAL);
        human = new Human(blockRepository);
        //sword=new Sword(0,0,0);
        human2 = new Human(blockRepository);
        CoreRegistry.put(MyBlockEngine.class, this);
        CoreRegistry.put(Human.class, human);
        this.initManagers();
        Collection<EngineSubsystem> subsystemList;
        subsystemList = Lists.<EngineSubsystem>newArrayList(new LwjglGraphics(), new LwjglTimer(), new LwjglAudio(), new LwjglInput());
        
        for (EngineSubsystem subsystem : getSubsystems()) {
            subsystem.postInitialise(config);
        }
        setPerspective();
        /*
		 * setLight(GL11.GL_LIGHT1, new float[] { 100f, 100f, 100f, 1.0f}, new
		 * float[] { 1f, 1f, 1f, 1f }, new float[] { 1f,1f, 1f, 1f },
		 * lightPosition);
		 */
        // Create a directional light (light green, to simulate reflection off
        // grass)
        //setFog(true);
        //setFog(new float[]{1f,1f,1f,0.2f},0.008f);
       /* setLight( GL11.GL_LIGHT1,
        		new float[] { 1.0f, 1.0f, 1.0f, 1.0f },   // diffuse color
        		new float[] { 0.2f, 0.2f, 0.2f, 1.0f },   // ambient
        		new float[] { 1.0f, 1.0f, 1.0f, 1.0f },   // specular
        		new float[]{-5,35,1,1} );

*/
        // position
        // Create a light (diffuse light, ambient light, position)
        setLight(GL11.GL_LIGHT1,
                new float[]{1f, 1f, 1f, 1f},
                new float[]{0.5f, 0.5f, .53f, 1f},
                new float[]{1f, 1f, 1f, 1f},
                lightPosition);

        // Create a directional light (light green, to simulate reflection off grass)
//        setLight( GL11.GL_LIGHT2,
//                new float[] { 0.15f, 0.4f, 0.1f, 1.0f },  // diffuse color
//                new float[] { 0.0f, 0.0f, 0.0f, 1.0f },   // ambient
//                new float[] { 0.0f, 0.0f, 0.0f, 1.0f },   // specular
//                new float[] { 0.0f, -10f, 0.0f, 0f } );   // direction (pointing up)

        // enable lighting and texture rendering
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // Enable alpha transparency (so text will have transparent background)
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
      /*  setLight( GL11.GL_LIGHT2,
        		new float[] { 1.0f, 1.0f, 1.0f, 1.0f },   // diffuse color
        		new float[] { 0.2f, 0.2f, 0.2f, 1.0f },   // ambient
        		new float[] { 1.0f, 1.0f, 1.0f, 1.0f },   // specular
        		new float[]{-25,32,10,1} );                         // position
*/
//		 setLight(GL11.GL_LIGHT2, new float[] { 100f, 100f, 100f, 1.0f}, // diffuse // color
//		  new float[] { 1f, 1f, 1f, 1f }, // ambient
//		  new  float[] { 1f, 1f, 1f, 1f }, // specular
//		  new float[] { 1,0,0, 1f
//		  }); // direction (pointing
//		 setLightPosition( GL11.GL_LIGHT2, new float[]{-22,50,1,1} );

        // up)

        // set global light
        //FloatBuffer ltAmbient = allocFloats(new float[] { 3.0f, 3.0f, 3.0f,
        //		1.0f });
        // ltAmbient.flip();
        //GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, ltAmbient);
        //GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, GL11.GL_FALSE);
//		
        //GL11.glEnable(GL11.GL_AUTO_NORMAL);
        dcc.blockRepository = blockRepository;
        bulletPhysics = new BulletPhysics(blockRepository);


        // Enable alpha transparency (so text will have transparent background)

        // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        // Create texture for spere
        //sphereTextureHandle = makeTexture("images/background.png");
        //humanTextureHandle = makeTexture("images/2000.png");
        //skyTextureHandle = makeTexture("images/sky180.png");
        //crossTextureHandle = makeTexture("images/gui.png");
        //waterTextureHandle = TextureManager.getIcon("water").textureHandle;
        //textureImg = loadImage("images/gui.png");
        // set camera 1 position
        camera1.setCamera(5, 20, 5, 0, 0f, -1, 0, 1, 0);
        human.setHuman(1, 34, 5, 0, 0, -1, 0, 1, 0);
        //human.setHuman(-25, 50, 1, 1, 0, 0, 0, 1, 0);

        human2.setHuman(1, 1, 1, 0, 0, 1, 0, 1, 0);

        human.startWalk();

        mouseControlCenter = new MouseControlCenter(human, camera1, this);
        mouseControlCenter.bulletPhysics = bulletPhysics;


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

        StorageManager storageManager = new StorageManagerInternal();
        PerlinWorldGenerator worldGenerator = new PerlinWorldGenerator();
        worldGenerator.initialize();
        worldGenerator.setWorldSeed("123123123");
        GeneratingChunkProvider chunkProvider = new LocalChunkProvider(storageManager, worldGenerator);
        //chunkProvider.createOrLoadChunk(new Vector3i(1,1,1));
        CoreRegistry.put(ChunkProvider.class, chunkProvider);
        WorldProvider WorldProvider = new WorldProviderWrapper();

        worldRenderer = new WorldRendererLwjgl(WorldProvider, chunkProvider, new LocalPlayerSystem(), camera1, human);
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
                1024f); // max Z: how far from eye position does view extend
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    Firework firework = new Firework();

    public void draw() {
        if (!Switcher.IS_GOD)
            if (Math.random() > 0.5) {
                dcc.check(human);
            }

        mouseControlCenter.handleNavKeys((float) GLApp.getSecondsPerFrame());
        // cam.handleNavKeys((float)GLApp.getSecondsPerFrame());
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();


        GL_Vector camera_pos = GL_Vector.add(human.Position,
                GL_Vector.multiply(human.ViewDir, Switcher.CAMERA_MODEL == 2 ? Switcher.CAMERA_2_PLAYER : (-1 * Switcher.CAMERA_2_PLAYER)));
        camera1.MoveTo(camera_pos.x, camera_pos.y + 2, camera_pos.z);
        // camera1.MoveTo(human.Position.x, human.Position.y + 4,
        // human.Position.z);


        if (Switcher.CAMERA_MODEL == 2) {
            // camera1.ViewDir.reverse();
            camera1.ViewDir = new GL_Vector(human.ViewDir.x * -1, human.ViewDir.y * -1, human.ViewDir.z * -1);
        } else {

            camera1.viewDir(human.ViewDir);
        }
        cam.render();

        if (Switcher.PRINT_SWITCH)
            printText();
        //}
        //drawAllBlock();
        //drawColorBlocks();
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
       // mainDraw();
        //skysphere.render();

        // draw the ground plane
       /* GL11.glPushMatrix();
        {
           // GL11.glTranslatef(4f, 33f, 1f); // down a bit
            GL11.glScalef(15f, .01f, 15f);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, groundTextureHandle);
            renderCube();
        }
        GL11.glPopMatrix();*/

        // airplaneShadow.drawShadow();
        //drawObjects();
        //drawShip();
        //drawLine();
        //sword.y=human.Position.y+4;
        //sword.render();

      /*  print( 30, viewportH- 45, "Use arrow keys to navigate:");
        print( 30, viewportH- 80, "Left-Right arrows rotate camera", 1);
        print( 30, viewportH-100, "Up-Down arrows move camera forward and back", 1);
        print( 30, viewportH-120, "PageUp-PageDown move vertically", 1);
        print( 30, viewportH-140, "SPACE key switches cameras", 1);*/


        //CoreRegistry.get(NuiManager.class).render();

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
				GL11.glTranslated(0,30,0);
				
				int texturehandleid=1;// TextureManager.getImage((String) entry.getKey()).textureHandle;
				GL11.glBindTexture(
						GL11.GL_TEXTURE_2D,
						texturehandleid);
				//GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
				//		GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
				callDisplayList(displayandleId);
                GL11.glTranslated(0,-30,0);
			}GL11.glPopMatrix();
			}
			
		}*/

    }


    public void drawColorBlocks() {
//先缩小

        GL11.glPushMatrix();

        // GL11.glTranslatef(1,30,1);
        GL11.glTranslatef(
                1, 35, 1
        );
        //GL11.glScalef(0.1f, 0.1f, 0.1f);
        //GL11.glRotated(90, 1, 0, 0);


        //GL11.glDisable(GL11.GL_TEXTURE_2D);
        //GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 1);
        GL11.glPointSize(4);
        GL11.glColor4f(1f, 0f, 0f, 1f);
        //GL11.glCullFace(GL11.GL_FRONT);
        callDisplayList(handleId);
        // GL11.glEnable(GL11.GL_LIGHTING);
        //	GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glTranslatef(1,-30,1);

        GL11.glTranslatef(
                -1, -35, -1
        );
        GL11.glPopMatrix();
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

    GLModel boat;

    public void drawShip() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPushMatrix();
        {
            // place plane at orbit point, and orient it toward origin
            //billboardPoint(airplanePos, ORIGIN, UP);
            // turn plane toward direction of motion
            //  GL11.glRotatef(-90, 0, 1, 0);
            // make it big
            GL11.glTranslatef(-10, 35, 5);
            GL11.glScalef(0.01f, 0.01f, 0.01f);
            // GL11.glBindTexture(GL11.GL_TEXTURE_2D, sphereTextureHandle);
            boat.render();
            // reset material, since model.render() will alter current material settings
            setMaterial(new float[]{.8f, .8f, .7f, 1f}, .4f);
        }
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public void drawObjects() {


        // draw the earth
        GL11.glPushMatrix();
        {//GL11.glTranslated(4,36,1);
            GL11.glScalef(2f, 2f, 2f);          // scale up
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, sphereTextureHandle);
            callDisplayList(earth);
        }
        GL11.glPopMatrix();
        /*TextureInfo ti = TextureManager.getTextureInfo("background");
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ti.textureHandle);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR); //GL11.GL_NEAREST);


        // Front Face
        GL11.glTranslated(1,10,1);
        GL11.glScaled(1,1,1);
        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
        int _x=8;
        int _y=8;
        int _z=1;
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(0-_x, 0-_y,  _z);	// Bottom Left
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( _x, 0-_y,  _z);	// Bottom Right
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( _x,  _y,  _z);	// Top Right
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(0-_x,  _y,  _z);	// Top Left
        GL11.glEnd();*/
        TextureInfo ti = TextureManager.getTextureInfo("human");
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ti.textureHandle);
        GL11.glPushMatrix();
        {
            if (Switcher.CAMERA_2_PLAYER < -2 || Switcher.CAMERA_2_PLAYER > 2) {
                human.render();
            } else
                human.renderPart();

        }
        GL11.glPopMatrix();
/*
         ti = TextureManager.getTextureInfo("gold_armor");
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ti.textureHandle);
        GL11.glPushMatrix();
        {
            if(Switcher.CAMERA_2_PLAYER<-2){
                human.render();
            }else
                human.renderPart();

        }
        GL11.glPopMatrix();*/
/*GL11.glTranslated(1,-5,1);
        GL11.glRotated(30,1,0,0);
        GL11.glScaled(1,1,1);*/

       /* GL11.glPushMatrix();
        {

            human2.render();
        }
        GL11.glPopMatrix();*/
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
        msg("DX=" + x + " DY=" + y);
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
        ResourceManager assetManager=CoreRegistry.putPermanently(ResourceManager.class,new ResourceManager());
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

    public void printText() {
        print(30, viewportH - 45, "press key 0~9 choose the object");
        print(30, viewportH - 60, "press key B open package");
        print(30, viewportH - 75, "press key wasd qe walk and turn direction ");
        print(30, viewportH - 90, "press space jump ");
        print(30, viewportH - 105, "press up down look up down ");
        print(30, viewportH - 120, "mouse click create block");
        print(30, viewportH - 135, "fps:" + time.tick());
    }

    public GL_Vector lineStart = new GL_Vector(0, 0, 0);

    // һ����¼���߷���
    public GL_Vector mouseDir = new GL_Vector(0, 1, 0);
    // һ����¼���߽����

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

    public void mainDraw() {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        worldRenderer.render();
        TextureInfo ti = TextureManager.getTextureInfo("human");
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ti.textureHandle);
        GL11.glPushMatrix();
        {
            if (Switcher.CAMERA_2_PLAYER < -2 || Switcher.CAMERA_2_PLAYER > 2) {
                human.render();
            } else
                human.renderPart();

        }
        GL11.glPopMatrix();
		/*
		 * ti = TextureManager.getTextureInfo("gold_armor");
		 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, ti.textureHandle);
		 * GL11.glPushMatrix(); { if(Switcher.CAMERA_2_PLAYER<-2){
		 * human.render(); }else human.renderPart();
		 * 
		 * } GL11.glPopMatrix();
		 */
		/*
		 * GL11.glTranslated(1,-5,1); GL11.glRotated(30,1,0,0);
		 * GL11.glScaled(1,1,1);
		 */

		/*
		 * GL11.glPushMatrix(); {
		 * 
		 * human2.render(); } GL11.glPopMatrix();
		 */
       // firework.render();
        //GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        CoreRegistry.get(NuiManager.class).render();

        //

    }

}
