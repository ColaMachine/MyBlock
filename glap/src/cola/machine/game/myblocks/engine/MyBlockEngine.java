package cola.machine.game.myblocks.engine;

import cola.machine.game.myblocks.action.BagController;
import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.engine.subsystem.EngineSubsystem;
import cola.machine.game.myblocks.engine.subsystem.lwjgl.LwjglGraphics;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.human.Player;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.rendering.assets.texture.Texture;
import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import glapp.*;
import glmodel.GL_Vector;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import sun.jvm.hotspot.utilities.Assert;
import time.Time;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.item.weapons.Sword;
import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.magicbean.fireworks.Firework;
import cola.machine.game.myblocks.manager.TextureManager;
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
import cola.machine.game.myblocks.world.Skysphere;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.block.internal.BlockManagerImpl;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.LocalChunkProvider;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.generator.WorldGenerators.PerlinWorldGenerator;
import cola.machine.game.myblocks.world.internal.WorldProviderWrapper;

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
    private Logger logger = LoggerFactory.getLogger(MyBlockEngine.class);

    // Handle for texture
    int sphereTextureHandle = 0;
    int groundTextureHandle = 0;
    int humanTextureHandle = 0;
    public String currentObject = "water";
    int skyTextureHandle = 0;
    int waterTextureHandle = 0;
    int crossTextureHandle = 0;
    GLImage textureImg;

    //String library_path = System.setProperty("org.lwjgl.librarypath","/home/colamachine/workspace/MyBlock/bin/natives/linux");


   // String library_path = System.setProperty("org.lwjgl.librarypath","/home/colamachine/workspace/MyBlock/bin/natives/linux");

    Time time = new Time();
    MouseControlCenter mouseControlCenter;
    BagController bagController =new BagController();
    // Light position: if last value is 0, then this describes light direction.
    // If 1, then light position.
    //float lightPosition[] = { 5f, 45f, 5f, 0f };
    float lightPosition[] = {-2f, 36f, 2f, 0f};
    float humanLightPosition[] = {-2f, 4f, 2f, 0f};
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
   // private Human human2;
    private Skysphere skysphere = new Skysphere();
    /**
     * Start the application. run() calls setup(), handles mouse and keyboard
     * input, calls render() in a loop.
     */

    WorldRenderer worldRenderer;
    AnimationManager animationManager;
    public static void main(String args[]) {
        try {
            PathManager.getInstance().useDefaultHomePath();


            LWJGLHelper.initNativeLibs();
            //System.out.println(System.getProperty("java.library.path"));
            Collection<EngineSubsystem> subsystemList;

            subsystemList = Lists.<EngineSubsystem>newArrayList(new LwjglGraphics());

            // create the app
            MyBlockEngine demo = new MyBlockEngine();
           // System.out.println(System.getProperty("java.library.path"));
            demo.VSyncEnabled = true;
            demo.fullScreen = false;
            demo.displayWidth = 800;
            demo.displayHeight = 600;

            demo.run(); // will call init(), render(), mouse functions
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Initialize the scene. Called by GLApp.run(). For now the default settings
     * will be fine, so no code here.
     */
    int handleId;
   // GLShadowOnPlane airplaneShadow;

    public void setup() {
        //开启胜读测试

       // makeTexture("images/Particle.bmp", true, true);
       // airplaneShadow = new GLShadowOnPlane(lightPosition, new float[]{0f, 1f, 0f, 3f}, null, this, method(this, "drawObjects"));
       // boat = new GLModel("glap/models/boat/botrbsm1.obj");
      groundTextureHandle = makeTexture("glap/images/grass_1_512.jpg", true, true);
      //  boat.mesh.regenerateNormals();
      //  boat.makeDisplayList();
          GL11. glEnable(GL11.GL_POINT_SMOOTH);
          GL11. glEnable( GL11. GL_LINE_SMOOTH);
          GL11. glHint( GL11. GL_POINT_SMOOTH_HINT,  GL11. GL_NICEST); // Make round points, not square points
          GL11. glHint( GL11. GL_LINE_SMOOTH_HINT,  GL11. GL_NICEST);  // Antialias the lines
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        CoreRegistry.put(BagController.class,bagController);
        human = new Human(blockRepository);
        //sword=new Sword(0,0,0);
        //human2 = new Human(blockRepository);
        CoreRegistry.put(MyBlockEngine.class, this);
        CoreRegistry.put(Human.class, human);
        dcc.blockRepository = blockRepository;
        bulletPhysics = new BulletPhysics(blockRepository);

        mouseControlCenter = new MouseControlCenter(human, camera1, this);
        mouseControlCenter.bulletPhysics = bulletPhysics;


        this.initManagers();
        this.initEntities();
        Collection<EngineSubsystem> subsystemList;
        subsystemList = Lists.<EngineSubsystem>newArrayList(new LwjglGraphics());
        //, new LwjglTimer(), new LwjglAudio(), new LwjglInput()
        for (EngineSubsystem subsystem : subsystemList) {
            subsystem.preInitialise();
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
                humanLightPosition);

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


        // Enable alpha transparency (so text will have transparent background)

        // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        // Create texture for spere
        sphereTextureHandle = makeTexture("images/background.png");
        //humanTextureHandle = makeTexture("images/2000.png");
        //skyTextureHandle = makeTexture("images/sky180.png");
        //crossTextureHandle = makeTexture("images/gui.png");
        //waterTextureHandle = TextureManager.getIcon("water").textureHandle;
        //textureImg = loadImage("images/gui.png");
        // set camera 1 position
        camera1.setCamera(5, 20, 5, 0, 0f, -1, 0, 1, 0);
        human.setHuman(1, 4, 5, 0, 0, -1, 0, 1, 0);
        //human.setHuman(-25, 50, 1, 1, 0, 0, 0, 1, 0);

//        human2.setHuman(1, 1, 1, 0, 0, 1, 0, 1, 0);

        human.startWalk();



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
try {
    initSelf();
}catch (Exception e){
    e.printStackTrace();
    exit();
}
       // initDisplayList();
       // initChuck();
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

     //  mouseControlCenter.handleNavKeys((float) GLApp.getSecondsPerFrame());
        // cam.handleNavKeys((float)GLApp.getSecondsPerFrame());
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

//        if(!camera1.fenli) {
            GL_Vector camera_pos = GL_Vector.add(human.Position,
                    GL_Vector.multiply(human.ViewDir, Switcher.CAMERA_MODEL == 2 ? Switcher.CAMERA_2_PLAYER : (-1 * Switcher.CAMERA_2_PLAYER)));
            camera1.MoveTo(camera_pos.x, camera_pos.y + 2, camera_pos.z);
//        }
        // camera1.MoveTo(human.Position.x, human.Position.y + 4,
        // human.Position.z);


        if (Switcher.CAMERA_MODEL == 2) {
            // camera1.ViewDir.reverse();
            camera1.ViewDir = new GL_Vector(human.ViewDir.x * -1, human.ViewDir.y * -1, human.ViewDir.z * -1);
        } else {

            camera1.viewDir(human.ViewDir);
        }
        cam.render();


        //}
       // drawAllBlock();
        //drawColorBlocks();
        try {
          //  Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainDraw();
        //skysphere.render();


        //drawShip();
        //drawLine();
        //sword.y=human.Position.y+4;
        //sword.render();
       // print( 30, viewportH- 45, "position x:"+human.oldPosition.x +" y:"+human.oldPosition.y+" z:"+human.oldPosition.z);
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
                1, 1, 1
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
                -1, -1, -1
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

  //  GLModel boat;

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
            //boat.render();
            // reset material, since model.render() will alter current material settings
            setMaterial(new float[]{.8f, .8f, .7f, 1f}, .4f);
        }
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public void DrawObject() {
        //GL11.glPushMatrix();
        // draw the earth
        { GL11.glColor3f(1.0f, 1.0f, 1.0f);
            // GL11.glTranslated(0,-1.5,0);
            //GL11.glRotatef(rotation, 0, 1, 0);  // rotate around Y axis
            // GL11.glScalef(0.35f, 0.35f, 0.35f);          // scale up
            // GL11. glRotatef(xrot, 1.0f, 0.0f, 0.0f);					// Rotate On The X Axis
            // GL11. glRotatef(yrot, 0.0f, 1.0f, 0.0f);					// Rotate On The Y Axis
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, sphereTextureHandle);
            callDisplayList(earth);
        }
        // GL11.glPopMatrix();
        // draw the earth
        // GL11.glPushMatrix();
        {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
            // GL11.glScalef(0.35f, 0.35f, 0.35f);
            // Rotate On The Y Axis
            // scale up
            GL11. glEnable( GL11.GL_BLEND);						// 启用混合

            GL11. glBlendFunc( GL11.GL_SRC_ALPHA,  GL11.GL_ONE);					// 把原颜色的40%与目标颜色混合

            GL11. glEnable( GL11.GL_TEXTURE_GEN_S);						// 使用球映射

            GL11.glEnable( GL11.GL_TEXTURE_GEN_T);

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.groundTextureHandle);
            callDisplayList(earth);
        } //GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_GEN_S);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_T);
        GL11.glDisable(GL11.GL_BLEND);

    }



    public void initSelf()  {
        // TODO Auto-generated method stub

        // 暂时用默认的参数初始化manager 然后manager 放到corerepgistry里
        //

		/* read config.cfg */
        try {
            initConfig();

            initManagers();

            // load assets
            // initAssets();
            //
            // initOPFlow();
            //
            initEntities();

            StorageManager storageManager = new StorageManagerInternal();
            PerlinWorldGenerator worldGenerator = new PerlinWorldGenerator();
            worldGenerator.initialize();
            worldGenerator.setWorldSeed("123123123");
            GeneratingChunkProvider chunkProvider = new LocalChunkProvider(storageManager, worldGenerator);
            //chunkProvider.createOrLoadChunk(new Vector3i(1,1,1));
            CoreRegistry.put(ChunkProvider.class, chunkProvider);
            WorldProvider WorldProvider = new WorldProviderWrapper();

            WorldRendererLwjgl worldRenderer = new WorldRendererLwjgl(WorldProvider, chunkProvider, new LocalPlayerSystem(), null, human);

            this.worldRenderer = worldRenderer;
        }catch(Exception e ){
            e.printStackTrace();;
            System.exit(0);
        }

    }
    Player player;
    private void initEntities(){
         player =new Player(CoreRegistry.get(TextureManager.class));
          human.setPlayer(player);
        CoreRegistry.put(Player.class,player);
    }
    private void initManagers() {


        CoreRegistry.put(MouseControlCenter.class,mouseControlCenter);

       // ResourceManager assetManager=CoreRegistry.putPermanently(ResourceManager.class,new ResourceManager());
        CoreRegistry.put(BlockManager.class,
                new BlockManagerImpl());
        TextureManager textureManager = CoreRegistry.put(TextureManager.class,
                new TextureManager());
        NuiManager nuiManager = CoreRegistry.put(NuiManager.class,
                new NuiManager());
        CoreRegistry.put(BlockManager.class,
                new BlockManagerImpl());
        animationManager=  new AnimationManager();
        CoreRegistry.put(AnimationManager.class,
                animationManager);
        // AssetManager assetManager =
        // CoreRegistry.putPermanently(AssetManager.class, new
        // AssetManager(moduleManager.getEnvironment()));

    }

    public void initConfig() throws IOException {
        Config config =  Config.load(Config.getConfigFile());
        CoreRegistry.put(Config.class,config );
        //
    }

    public void printText() {
//        print(30, viewportH - 45, "press key 0~9 choose the object");
//        print(30, viewportH - 60, "press key B open package");
//        print(30, viewportH - 75, "press key wasd qe walk and turn direction ");
//        print(30, viewportH - 90, "press space jump ");
//        print(30, viewportH - 105, "press up down look up down ");
        print(30, viewportH - 120, "cam:"+human.ViewDir);
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
    public void testDraw(){
        // draw the ground plane
       /* GL11.glPushMatrix();
        {
            // GL11.glTranslatef(4f, 33f, 1f); // down a bit
            GL11.glScalef(1f, 1f, 1f);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, groundTextureHandle);
            renderCube();

            GL11.glTranslated(2f,2f,2f);
            renderCube();
        }
        GL11.glPopMatrix();*/

        //airplaneShadow.drawShadow();
        //DrawObject();
        //testBeginDisplayList();

        testChunk();

    }
    public void mainDraw() {


      /*  try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/

        if(Switcher.gameState==0){
            CoreRegistry.get(NuiManager.class).render();//worldRenderer.render();
        }else if(Switcher.gameState==1){

            try {
                animationManager.update();
            } catch (Exception e) {
                e.printStackTrace();
                exit();
            }
            //GL11.glScaled(0.1,0.1,0.1);
            worldRenderer.render();
            //GL11.glScaled(10,10,10);
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
            CoreRegistry.get(NuiManager.class).render();
        }else if(Switcher.gameState==2){

        }

        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


       /* GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11. glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11. glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);*/
       // TextureInfo ti1 = TextureManager.getTextureInfo("human");
       // GL11.glBindTexture(GL11.GL_TEXTURE_2D, ti1.textureHandle);
       // GL11.glScaled(0.1,0.1,0.1);


      //  GL11.glScaled(10,10,10);
        //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
       // GL11. glFlush();

      // testDraw();

       // player.render();
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
        //firework.render(human);
       // GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
      // GL11.glDisable(GL11.GL_DEPTH_TEST);


        if (Switcher.PRINT_SWITCH)
            printText();
        //

    }
    ChunkImpl chunk ;
    public void initChuck(){
chunk =new ChunkImpl();
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        //Block block =blockManager .getBlock("soil");
        for (int x = 0; x < chunk.getChunkSizeX(); x+=2) {
            for (int z = 0; z < chunk.getChunkSizeZ(); z+=2) {
                Block block =blockManager .getBlock("soil");
                chunk.setBlock(x, 0, z, block);
            }
        }

       // chunk.setBlock(0,0,-2,block);

        chunk.build();
    }
    public void testChunk(){
        TextureInfo ti = TextureManager.getTextureInfo("soil");
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ti.textureHandle);
        GL11.glBegin(GL11.GL_QUADS);
        chunk.render();
        GL11.glEnd();
    }
    int testDisplayId;
    public void testBeginDisplayList(){

        TextureInfo ti = TextureManager.getTextureInfo("human");
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ti.textureHandle);
        GLApp.callDisplayList(this.testDisplayId);

    }

    public void initDisplayList(){
        testDisplayId = GLApp.beginDisplayList();
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face

        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);//0~4  0~12
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P1);	// Bottom Left ǰ����
        GL11.glTexCoord2f(1/16f, 0f);glVertex3fv(P2);	// Bottom Right ǰ����
        GL11.glTexCoord2f(1/16f, 3/8f);glVertex3fv(P6);	// Top Right ǰ����
        GL11.glTexCoord2f(0f, 3/8f); glVertex3fv(P5);	// Top Left	ǰ����
        // Back Face

        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P3);	// Bottom Left ������
        GL11.glTexCoord2f(1/16f, 0f); glVertex3fv(P4);	// Bottom Right ������
        GL11.glTexCoord2f(1/16f, 3/8f); glVertex3fv(P8);	// Top Right ������
        GL11.glTexCoord2f(0f, 3/8f);glVertex3fv(P7);	// Top Left ������

        // Top Face
        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);

        GL11.glTexCoord2f(1/16f, 3/8f); glVertex3fv(P5);	// Bottom Left ǰ����
        GL11.glTexCoord2f(2/16f, 3/8f); glVertex3fv(P6);	// Bottom Right ǰ����
        GL11.glTexCoord2f(2/16f, 4/8f); glVertex3fv(P7);	// Top Right ������
        GL11.glTexCoord2f(1/16f, 4/8f); glVertex3fv(P8);	// Top Left ������
        // Bottom Face
        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
        GL11.glTexCoord2f(1/16f, 3/8f);glVertex3fv(P4);	// Top Right ������
        GL11.glTexCoord2f(2/16f, 3/8f); glVertex3fv(P3);	// Top Left ������
        GL11.glTexCoord2f(2/16f, 4/8f);glVertex3fv(P2);	// Bottom Left ǰ����
        GL11.glTexCoord2f(1/16f, 4/8f);glVertex3fv(P1);	// Bottom Right ǰ����
        // Right face
        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P2);	// Bottom Left ǰ����
        GL11.glTexCoord2f(1/16f, 0f);glVertex3fv(P3);	// Bottom Right ������
        GL11.glTexCoord2f(1/16f, 3/8f); glVertex3fv(P7);	// Top Right ������
        GL11.glTexCoord2f(0f, 3/8f);glVertex3fv(P6);	// Top Left ǰ����

        // Left Face
        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P4);	// Bottom Left ������
        GL11.glTexCoord2f(1/16f, 0f);glVertex3fv(P1);	// Bottom Right ǰ����
        GL11.glTexCoord2f(1/16f, 3/8f);glVertex3fv(P5);	// Top Right ǰ����
        GL11.glTexCoord2f(0f, 3/8f);glVertex3fv(P8);	// Top Leftǰ����
        GL11.glEnd();

        GL11.glEndList();
    }
    public void glVertex3fv(GL_Vector p){
        GL11.glVertex3f(p.x,p.y,p.z);
    }

    GL_Vector P1=new GL_Vector(-0.25f,-1.5f,0.25f);
    GL_Vector P2=new GL_Vector(0.25f,-1.5f,0.25f);
    GL_Vector P3=new GL_Vector(0.25f,-1.5f,-0.25f);
    GL_Vector P4=new GL_Vector(-0.25f,-1.5f,-0.25f);
    GL_Vector P5=new GL_Vector(-0.25f,0f,0.25f);
    GL_Vector P6=new GL_Vector(0.25f,0f,0.25f);
    GL_Vector P7=new GL_Vector(0.25f,0f,-0.25f);
    GL_Vector P8=new GL_Vector(-0.25f,0f,-0.25f);


    GL_Vector[] vertexs={
            P1,P2,P6,P5,
            P3,P4,P8,P7,
            P5,P6,P7,P8,
            P4,P3,P2,P1,
            P2,P3,P7,P6,
            P4,P1,P5,P8
    };

    public void keyDown(int keycode) {

		/*  if (Keyboard.KEY_SPACE == keycode) { cam.setCamera((cam.camera ==
		 camera1)? camera2 : camera1); }*/

        mouseControlCenter.keyDown(keycode);
    }

    public void keyUp(int keycode) {
       // mouseControlCenter.keyUp(keycode);
    }
/*
    *//**
     * Add last mouse motion to the line, only if left mouse button is down.
     *//*
    public void mouseMove(int x, int y) {
        mouseControlCenter.mouseMove(x, y);
    }

    *//**
     * Add last mouse motion to the line, only if left mouse button is down.
     *//*
    public void mouseUp(int x, int y) {
        mouseControlCenter.mouseUp(x, y);
    }

    *//**
     * Add last mouse motion to the line, only if left mouse button is down.
     *//*
    public void mouseDown(int x, int y) {
        msg("DX=" + x + " DY=" + y);
        if (this.mouseButtonDown(0)) {
            mouseControlCenter.mouseLClick(x, y);
        }
        if (this.mouseButtonDown(1)) {
            mouseControlCenter.mouseRClick(x, y);
        }

    }*/

}
