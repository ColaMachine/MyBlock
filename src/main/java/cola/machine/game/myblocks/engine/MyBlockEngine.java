package cola.machine.game.myblocks.engine;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.lifething.manager.BehaviorManager;
import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.magicbean.fireworks.Firework;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.ui.NuiManager;
import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.persistence.impl.StorageManagerInternal;
import cola.machine.game.myblocks.physic.BulletPhysics;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;
import cola.machine.game.myblocks.rendering.world.WorldRendererLwjgl;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;
import cola.machine.game.myblocks.world.Skysphere;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.block.internal.BlockManagerImpl;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.chunks.LocalChunkProvider;
import cola.machine.game.myblocks.world.chunks.RemoteChunkProvider;
import cola.machine.game.myblocks.world.generator.WorldGenerators.PerlinWorldGenerator;
import cola.machine.game.myblocks.world.internal.WorldProviderWrapper;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.network.client.SynchronTask;
import glapp.GLApp;
import glapp.GLCam;
import glapp.GLCamera;
import gldemo.learnOpengl.LearnOpengl5;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Util;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import time.Time;

import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

//import sun.jvm.hotspot.utilities.Assert;

/**
 * Run a bare-bones GLApp. Draws one white triangle centered on screen.
 * <p/>
 * GLApp initializes the LWJGL environment for OpenGL rendering, ie. creates a
 * window, sets the display mode, inits mouse and keyboard, then runs a loop
 * that calls draw().
 * <p/>
 * napier at potatoland dot org
 */
@Deprecated
public class MyBlockEngine extends GLApp {
    private Logger logger = LoggerFactory.getLogger(MyBlockEngine.class);

    // Handle for texture
    //int sphereTextureHandle = 0;
    // int groundTextureHandle = 0;
    // int humanTextureHandle = 0;
    public String currentObject = "water";
    // int skyTextureHandle = 0;
    //  int waterTextureHandle = 0;
    //  int crossTextureHandle = 0;
    // GLImage textureImg;

    //String library_path = System.setProperty("org.lwjgl.librarypath","/home/colamachine/workspace/MyBlock/bin/natives/linux");


    // String library_path = System.setProperty("org.lwjgl.librarypath","/home/colamachine/workspace/MyBlock/bin/natives/linux");

    Time time = new Time();
    MouseControlCenter mouseControlCenter;
    //BagController bagController =new BagController(player);
    // Light position: if last value is 0, then this describes light direction.
    // If 1, then light position.
    //float lightPosition[] = { 5f, 45f, 5f, 0f };
    float lightPosition[] = {-2f, 36f, 2f, 0f};
    float humanLightPosition[] = {-2f, 4f, 2f, 0f};
    // Camera position
    float[] cameraPos = {0f, 3f, 20f};

    // two cameras and a cam to move them around scene
    GLCamera camera1 = new GLCamera();
    // GLCamera camera2 = new GLCamera();
    GLCam cam = new GLCam(camera1);
    DropControlCenter dcc = new DropControlCenter();

    // vectors used to orient airplane motion
    // GL_Vector UP = new GL_Vector(0, 1, 0);
    //  GL_Vector ORIGIN = new GL_Vector(0, 0, 0);

    // for earth rotation
    // float degrees = 0;

    // model of airplane and sphere displaylist for earth
    // GLModel airplane;
    public int earth;
    // public int waterDisplay;
    //public Sword sword;
    // shadow handler will draw a shadow on floor plane
    // GLShadowOnPlane airplaneShadow;

    public GL_Vector airplanePos;

    FloatBuffer bbmatrix = GLApp.allocFloats(16);

    // public BlockRepository blockRepository = new BlockRepository(this);
    BulletPhysics bulletPhysics;
    public Player player;
    // private Human human2;
    private Skysphere skysphere = new Skysphere();
    /**
     * Start the application. run() calls setup(), handles mouse and keyboard
     * input, calls render() in a loop.
     */

    WorldRenderer worldRenderer;
    AnimationManager animationManager;

    /**
     * Initialize the scene. Called by GLApp.run(). For now the default settings
     * will be fine, so no code here.
     */
    int handleId;
    LearnOpengl5 shaderTest;
    // GLShadowOnPlane airplaneShadow;
    public LivingThingManager livingThingManager;

    public static void main(String args[]) {
        try {
            PathManager.getInstance().useDefaultHomePath();


            LWJGLHelper.initNativeLibs();
            //System.out.println(System.getProperty("java.library.path"));
            //Collection<EngineSubsystem> subsystemList;

            //subsystemList = Lists.<EngineSubsystem>newArrayList(new LwjglGraphics());

            // create the app
            MyBlockEngine demo = new MyBlockEngine();
            // System.out.println(System.getProperty("java.library.path"));
            demo.VSyncEnabled = true;
            demo.fullScreen = false;
            demo.displayWidth = 600;
            demo.displayHeight = 600;

            demo.run(); // will call init(), render(), mouse functions
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setup() {

        //开启胜读测试
        /* shaderTest= new LearnOpengl5();
        try {
            shaderTest.initGL();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // makeTexture("assets.images/Particle.bmp", true, true);
        // airplaneShadow = new GLShadowOnPlane(lightPosition, new float[]{0f, 1f, 0f, 3f}, null, this, method(this, "drawObjects"));
        // boat = new GLModel("glap/models/boat/botrbsm1.obj");
        //groundTextureHandle = makeTexture("glap/assets.images/grass_1_512.jpg", true, true);
        //  boat.mesh.regenerateNormals();
        //  boat.makeDisplayList();
        GL11. glEnable(GL11.GL_POINT_SMOOTH);
        GL11. glEnable( GL11. GL_LINE_SMOOTH);
        GL11. glHint( GL11. GL_POINT_SMOOTH_HINT,  GL11. GL_NICEST); // Make round points, not square points
        GL11. glHint( GL11. GL_LINE_SMOOTH_HINT,  GL11. GL_NICEST);  // Antialias the lines
       // GL11.glEnable(GL11.GL_DEPTH_TEST);
       // GL11.glDepthFunc(GL11.GL_LEQUAL);




        // Depth test setup
       // GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
       // GL11.glDepthFunc(GL11.GL_LEQUAL);  // The Type Of Depth Testing To Do



        //sword=new Sword(0,0,0);
        //human2 = new Human(blockRepository);






/*
        Collection<EngineSubsystem> subsystemList;
        subsystemList = Lists.<EngineSubsystem>newArrayList(new LwjglGraphics());
        //, new LwjglTimer(), new LwjglAudio(), new LwjglInput()
        for (EngineSubsystem subsystem : subsystemList) {
            subsystem.preInitialise();
        }*/

       setLight( GL11.GL_LIGHT1,
        		new float[] { 1.0f, 1.0f, 1.0f, 1.0f },   // diffuse color
        		new float[] { 0.2f, 0.2f, 0.2f, 1.0f },   // ambient
        		new float[] { 1.0f, 1.0f, 1.0f, 1.0f },   // specular
        		new float[]{-5,35,1,1} );




        // position
        // Create a light (diffuse light, ambient light, position)
      /*  setLight(GL11.GL_LIGHT1,
                new float[]{0.5f, 0.5f, 0.0f, 1.0f},//diffuseGL_AMBIENT表示各种光线照射到该材质上，经过很多次反射后最终遗留在环境中的光线强度（颜色）。
                new float[]{1.0f, 1.0f, 1.0f, 1.0f},//ambient GL_DIFFUSE表示光线照射到该材质上，经过漫反射后形成的光线强度（颜色）。
                new float[]{1f, 1f, 1f, 1f},//GL_SPECULAR表示光线照射到该材质上，经过镜面反射后形成的光线强度（颜色）
                humanLightPosition);
*///GL_SHININESS属性。该属性只有一个值，称为“镜面指数”，取值范围是0到128。该值越小，表示材质越粗糙，点光源发射的光线照射到上面，也可以产生较大的亮点。该值越大，表示材质越类似于镜面，光源照射到上面后，产生较小的亮点。
        /*setSpotLight(GL11.GL_LIGHT1,
                new float[]{0.5f, 0.5f, 0.0f, 1.0f},//diffuseGL_AMBIENT表示各种光线照射到该材质上，经过很多次反射后最终遗留在环境中的光线强度（颜色）。
                new float[]{1.0f, 1.0f, 1.0f, 1.0f},//ambient GL_DIFFUSE表示光线照射到该材质上，经过漫反射后形成的光线强度（颜色）。
                humanLightPosition,new float[]{0,0,-1,0},30);*/
        //GL_EMISSION属性。该属性由四个值组成，表示一种颜色。OpenGL认为该材质本身就微微的向外发射光线，以至于眼睛感觉到它有这样的颜色，但这光线又比较微弱，以至于不会影响到其它物体的颜色。
//
//        FloatBuffer mat_ambient = ByteBuffer.allocateDirect(4 * GLApp.SIZE_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
//        mat_ambient.put(1.0f).put(1.0f).put(0f).put(1.0f);
//        mat_ambient.flip();
//        GL11.glMaterial(GL11.GL_FRONT,GL11.GL_AMBIENT,mat_ambient);
//        FloatBuffer mat_diffuse = ByteBuffer.allocateDirect(4 * GLApp.SIZE_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
//        mat_diffuse.put(new float[]{0.5f,0.5f,0f,1.0f});mat_diffuse.flip();
//        FloatBuffer mat_specular = ByteBuffer.allocateDirect(4 * GLApp.SIZE_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
//        mat_diffuse.put(new float[]{1.0f,1.0f,0f,1.0f});mat_diffuse.flip();
//
//        FloatBuffer mat_emission = ByteBuffer.allocateDirect(4 * GLApp.SIZE_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
//        mat_emission.put(1.0f).put(1.0f).put(0f).put(1.0f);
//        mat_emission.flip();


        float mat_shininess =128.0f;

        GL11.glEnable(GL11.GL_LIGHTING);

        camera1.setCamera(5, 20, 5, 0, 0f, -1, 0, 1, 0);


        try {
            CoreRegistry.put(MyBlockEngine.class, this);
            Client client =new Client();
            client.start();
            CoreRegistry.put(Client.class,client);
          //  CoreRegistry.put(BagController.class,bagController);
            this.initManagers();
            this.initEntities();
            this.initEvent();

            mouseControlCenter.livingThingManager=this.livingThingManager;
            initSelf();

            thread  =new BehaviorManager();
            thread.start();
        }catch (Exception e){
            e.printStackTrace();
            exit();
        }
        // initDisplayList();
        // initChuck();
    }
    Thread thread;
    /**
     * set the field of view and view depth.
     */


    Firework firework = new Firework();
    public void initEvent(){
        //dcc.blockRepository = blockRepository;
        bulletPhysics = new BulletPhysics(/*blockRepository*/);

       // mouseControlCenter = new MouseControlCenter(player, camera1);
        CoreRegistry.put(MouseControlCenter.class,mouseControlCenter);
        mouseControlCenter.bulletPhysics = bulletPhysics;
    }
    public void draw() {


        if (!Switcher.IS_GOD)
            if (Math.random() > 0.5) {
                //dcc.check(human);
               // livingThingManager.checkAllLivingThingCollising(dcc);

            }

        mouseControlCenter.handleNavKeys((float) GLApp.getSecondsPerFrame());
        // cam.handleNavKeys((float)GLApp.getSecondsPerFrame());
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        Util.checkGLError();
//        if(!camera1.fenli) {



//        }
        // camera1.MoveTo(human.Position.x, human.Position.y + 4,
        // human.Position.z);
        Util.checkGLError();

        if (Switcher.CAMERA_MODEL == 2) {
            // camera1.ViewDir.reverse();
            camera1.ViewDir = new GL_Vector(player.viewDir.x * -1, player.viewDir.y * -1, player.viewDir.z * -1);
        } else {

            camera1.viewDir(player.viewDir);
        }
        Util.checkGLError();
        cam.render();
        Util.checkGLError();



        mainDraw();


    }




    public void initSelf()  {
        // TODO Auto-generated method stub

        // 暂时用默认的参数初始化manager 然后manager 放到corerepgistry里
        //

		/* read config.cfg */
        try {
            initConfig();
//            initFont();

            // initManagers();

            // load assets
            // initAssets();
            //
            // initOPFlow();
            //
            //    initEntities();

            StorageManager storageManager = new StorageManagerInternal();
            PerlinWorldGenerator worldGenerator = new PerlinWorldGenerator();
            worldGenerator.initialize();
            worldGenerator.setWorldSeed("123123123");
            GeneratingChunkProvider chunkProvider = new LocalChunkProvider(storageManager, worldGenerator);


            //chunkProvider.createOrLoadChunk(new Vector3i(1,1,1));
            CoreRegistry.put(ChunkProvider.class, chunkProvider);

            RemoteChunkProvider remoteChunkProvider = new RemoteChunkProvider(storageManager, worldGenerator);


            //chunkProvider.createOrLoadChunk(new Vector3i(1,1,1));
            CoreRegistry.put(RemoteChunkProvider.class, remoteChunkProvider);

            WorldProvider WorldProvider = new WorldProviderWrapper();

            WorldRendererLwjgl worldRenderer = new WorldRendererLwjgl(WorldProvider, chunkProvider, new LocalPlayerSystem(), null, player);

            this.worldRenderer = worldRenderer;
        }catch(Exception e ){
            e.printStackTrace();;
            System.exit(0);
        }

    }
    //Player player;
    private void initEntities(){
        player = new Player(1);
        player.setHuman(1, 4, 5, 0, 0, -1, 0, 1, 0);
        CoreRegistry.put(Player.class, player);

        LivingThing livingThing =new LivingThing(1);
        livingThing.position=new GL_Vector(10,4,0);
        livingThingManager.setPlayer(player);
        livingThingManager.add(livingThing);
        SynchronTask task =new SynchronTask();
        task.start();
     /*    player =new Player(CoreRegistry.get(TextureManager.class));
          human.setPlayer(player);
        CoreRegistry.put(Player.class,player);*/
    }
    private void initManagers() {
        // ResourceManager assetManager=CoreRegistry.putPermanently(ResourceManager.class,new ResourceManager());
        CoreRegistry.put(BlockManager.class,
                new BlockManagerImpl());
        TextureManager textureManager = CoreRegistry.put(TextureManager.class,
                new TextureManager());
        ;
        CoreRegistry.put(BlockManager.class,
                new BlockManagerImpl());
        animationManager=  new AnimationManager();
        CoreRegistry.put(AnimationManager.class,
                animationManager);


        NuiManager nuiManager = CoreRegistry.put(NuiManager.class,
                new NuiManager());

        // AssetManager assetManager =
        // CoreRegistry.putPermanently(AssetManager.class, new
        // AssetManager(moduleManager.getEnvironment()));
        livingThingManager =new LivingThingManager();
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
        print(30, viewportH - 120, "cam:"+ player.viewDir);
        print(30, viewportH - 135, "fps:" + time.tick());
    }

    public GL_Vector lineStart = new GL_Vector(0, 0, 0);

    // һ����¼���߷���
    public GL_Vector mouseDir = new GL_Vector(0, 1, 0);
    // һ����¼���߽����

    public GL_Vector mouseEnd = new GL_Vector(0, 5, 0);



    public void mainDraw() {

        if(Switcher.gameState==0){
            CoreRegistry.get(NuiManager.class).render();//worldRenderer.render();
        }else if(Switcher.gameState==1){

            try {
                animationManager.update();
            } catch (Exception e) {
                e.printStackTrace();
                exit();
            }
           worldRenderer.render();Util.checkGLError();
           livingThingManager.update();Util.checkGLError();
            livingThingManager.render();Util.checkGLError();

            //AttackManager.update();Util.checkGLError();
          //  AttackManager.render();Util.checkGLError();
            //GL11.glScaled(10,10,10);
           /* TextureInfo ti = TextureManager.getTextureInfo("human");
            ti.bind();*/



            //GL11.glPopMatrix();
            CoreRegistry.get(NuiManager.class).render();Util.checkGLError();
        }else if(Switcher.gameState==2){

        }

        if (Switcher.PRINT_SWITCH)
            printText();

        Util.checkGLError();
        GLApp.drawRect(1,1,50,50);Util.checkGLError();
        //

    }



}
