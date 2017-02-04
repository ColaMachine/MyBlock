package cola.machine.game.myblocks.engine.modes;

import cola.machine.game.myblocks.action.BagController;
import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.GameEngine;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.lifething.manager.BehaviorManager;
import cola.machine.game.myblocks.lifething.manager.LivingThingManager;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.model.ui.NuiManager;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.model.ui.html.InventoryPanel;
import cola.machine.game.myblocks.model.ui.html.SlotPanel;
import cola.machine.game.myblocks.network.Client;
import cola.machine.game.myblocks.network.SynchronTask;
import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.persistence.impl.StorageManagerInternal;
import cola.machine.game.myblocks.physic.BulletPhysics;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;
import cola.machine.game.myblocks.rendering.world.WorldRendererLwjgl;
import cola.machine.game.myblocks.skill.AttackManager;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.ui.test.GuiRootPane;
import cola.machine.game.myblocks.ui.test.SimpleTest;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.block.internal.BlockManagerImpl;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.chunks.LocalChunkProvider;
import cola.machine.game.myblocks.world.generator.WorldGenerators.PerlinWorldGenerator;
import cola.machine.game.myblocks.world.internal.WorldProviderWrapper;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import glapp.GLApp;
import glapp.GLCamera;
import gldemo.learnOpengl.chapt13.LearnOpenglColor;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLU;

import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class GamingState implements GameState {
    public static GamingState instance;
    public static Human human;//= new Human();
    public static String catchThing;
    public static boolean cameraChanged=false;
    public static boolean livingThingChanged=true;
    //LearnOpenglColor learnOpenglColor;
    //GUI startGui;
    WorldRenderer worldRenderer;
    AnimationManager animationManager;
    public LivingThingManager livingThingManager;
    MouseControlCenter mouseControlCenter;
    Thread behaviorManagerThread;
    public BulletPhysics bulletPhysics;
    public GLCamera camera = new GLCamera();

    public GL_Vector lightPos = new GL_Vector(5, 5, 2);
    //shader constants

    public double preKeyTime = 0;

    GUI gameGui;
    Document document ;

    public void init(GameEngine engine) {
        document= Document.getInstance();
        this.instance =this;
        ShaderManager shaderManager =new ShaderManager();
        /*shaderManager.init();*/
        //learnOpenglColor=new LearnOpenglColor();

        try {

            initGL();

            lastTime= System.currentTimeMillis();
            this.initManagers();
            this.initEntities();
            this.initEvent();

            mouseControlCenter.livingThingManager = this.livingThingManager;
            initSelf();

            behaviorManagerThread = new BehaviorManager();
            behaviorManagerThread.start();

            /*LWJGLRenderer renderer = null;//调用lwjgl能力
                renderer = new LWJGLRenderer();

                LoginDemo loginDemo = new LoginDemo();
                startGui = new GUI(loginDemo, renderer);
                ThemeManager theme = ThemeManager.createThemeManager(
                        LoginDemo.class.getResource("login.xml"), renderer);
                startGui.applyTheme(theme);*/



            document= Document.getInstance();
            document .body.removeChild();
            document.body.appendChild(new InventoryPanel(4,5));

            document.needUpdate=true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dispose() {

    }
public ShaderManager shaderManager;
    public void initGL() {
        if (Switcher.SHADER_ENABLE) {
            if(shaderManager==null){
                shaderManager=new ShaderManager();
            }
            shaderManager.init();

        } else {
            OpenglUtils.setGlobalLight();

            OpenglUtils.setPerspective();

        }

    }

    public static void setPerspective() {
        // select projection matrix (controls perspective)
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(40f, 800 / 600, 1f, 1000f);
        // return to modelview matrix
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    int cursorX;
    int cursorY;

    public void handleInput(float delta) {
        cursorX = Mouse.getEventX();
        cursorY = Mouse.getEventY();
        int mouseDW = Mouse.getDWheel();
        if (mouseDW != 0) {
            //mouseControlCenter.mousewmouseWheel(mouseDW);
        }
        GUI gui = CoreRegistry.get(GUI.class);





        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {

                document.handleKey(
                        Keyboard.getEventKey(),
                        Keyboard.getEventCharacter(),
                        Keyboard.getEventKeyState());

                // check for exit key
           /* if (Keyboard.getEventKey() == finishedKey) {
                finished = true;
            }*/

                // pass key event to handler
                //System.out.println("Character"+Keyboard.getEventCharacter());

                // pass key event to handler
                //LogUtil.println("Character"+Keyboard.getEventCharacter());

if(!Switcher.SHADER_ENABLE)
                gui.handleKey(
                        Keyboard.getEventKey(),
                        Keyboard.getEventCharacter(),
                        Keyboard.getEventKeyState());

               /* if (Keyboard.getEventKeyState()) {    // key was just pressed, trigger keyDown()
                    keyDown(Keyboard.getEventKey());
                    // LogUtil.println("key presseds");
                } else {
                    //keyUp(Keyboard.getEventKey());    // key was released
                }*/
            }
        }
        if (Mouse.isCreated()) {
            mouseControlCenter.mouseMove(cursorX, cursorY);
            while (Mouse.next()) {

                document.handleMouse(
                        Mouse.getEventX(), Constants.WINDOW_HEIGHT - Mouse.getEventY() - 1,
                        Mouse.getEventButton(), Mouse.getEventButtonState());
                int wheelDelta = Mouse.getEventDWheel();
                if (wheelDelta != 0) {
                    //gui.handleMouseWheel(wheelDelta / 120);
                }
                //LogUtil.println("Mouse.getEventButton()"+Mouse.getEventButton());


                if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() == true) {
                    mouseControlCenter.mouseLeftDown(cursorX, cursorY);
                }
                if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() == false) {
                    mouseControlCenter.mouseLeftUp(cursorX, cursorY);
                }
                if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState() == true) {
                    mouseControlCenter.mouseRightDown(cursorX, cursorY);
                }
                if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState() == false) {
                    mouseControlCenter.mouseRightUp(cursorX, cursorY);
                }
                //GUI gui = CoreRegistry.get(GUI.class);
                if(!Switcher.SHADER_ENABLE)
                gui.handleMouse(
                        Mouse.getEventX(), gui.getHeight() - Mouse.getEventY() - 1,
                        Mouse.getEventButton(), Mouse.getEventButtonState());

            }
        }

        mouseControlCenter.handleNavKeys(delta);
        /*if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            camera.Position=GL_Vector.add(camera.Position, GL_Vector.multiplyWithoutY(camera.ViewDir,
                    -0.1f));
           camera.changeCallBack();// cameraPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            camera.Position=GL_Vector.add(camera.Position, GL_Vector.multiplyWithoutY(camera.ViewDir,
                    0.1f));
            camera.changeCallBack();// cameraPosChangeListener();

        }




        if (Keyboard.isKeyDown( Keyboard.KEY_A) ){

            GL_Vector right = GL_Vector.crossProduct(camera.ViewDir,camera.UpVector);
            camera.Position=GL_Vector.add(camera.Position, GL_Vector.multiplyWithoutY(right,
                    -0.1f));
            camera.changeCallBack();//cameraPosChangeListener();

        }
        if (Keyboard.isKeyDown(  Keyboard.KEY_D)) {

            GL_Vector right = GL_Vector.crossProduct(camera.ViewDir,camera.UpVector);
            camera.Position=GL_Vector.add(camera.Position, GL_Vector.multiplyWithoutY(right,
                    0.1f));
            camera.changeCallBack();//cameraPosChangeListener();

        }

        if ( Keyboard.isKeyDown( Keyboard.KEY_Q)) {

            GL_Matrix M = GL_Matrix.rotateMatrix(0, (float) Math.toRadians(5)/5,
                    0);
            camera.ViewDir = M.transform(camera.ViewDir);

            camera.changeCallBack();//cameraPosChangeListener();

        }


        if (Keyboard.isKeyDown( Keyboard.KEY_E)) {

            GL_Matrix M = GL_Matrix.rotateMatrix(0, (float) Math.toRadians(-5)/5,
                    0);
            camera.ViewDir = M.transform(camera.ViewDir);


            camera.changeCallBack();// cameraPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            camera.Position.y+=0.1;
            camera.changeCallBack();// cameraPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            camera.Position.y-=0.1;
            camera.changeCallBack();// cameraPosChangeListener();

        }*/


        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {

            lightPos.x += -0.1;

            shaderManager.lightPosChangeListener();

        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            lightPos.x += 0.1;

            shaderManager.lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            lightPos.z -= 0.1;

            shaderManager.lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            lightPos.z += 0.1;

            shaderManager.lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
            lightPos.y += 0.1;

            shaderManager. lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            lightPos.y -= 0.1;

            shaderManager.lightPosChangeListener();

        }

    }

    long nowTime=0;
    int fps=0;
    public void update(float delta) {
        nowTime=System.currentTimeMillis();
        if((nowTime-lastTime)>1000){
            fps =frameCount;
            frameCount=0;
            lastTime=nowTime;
        }else{
            frameCount++;
        }
        AttackManager.update();
        try {
            animationManager.update();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!Switcher.IS_GOD)
            if (/*Math.random()*/ 1> 0.5) {
                //dcc.check(human);
                livingThingManager.CrashCheck(dcc);
                GL_Vector camera_pos = GL_Vector.add(human.position,
                        GL_Vector.multiply(human.ViewDir, Switcher.CAMERA_MODEL == 2 ? Switcher.CAMERA_2_PLAYER : (-1 * Switcher.CAMERA_2_PLAYER)));
                camera.MoveTo(camera_pos.x, camera_pos.y + 2, camera_pos.z);

                if (Switcher.CAMERA_MODEL == 2) {
                    // camera1.ViewDir.reverse();
                    camera.ViewDir = new GL_Vector(human.ViewDir.x * -1, human.ViewDir.y * -1, human.ViewDir.z * -1);
                } else {

                    camera.viewDir(human.ViewDir);
                }
                if(GamingState.cameraChanged){
                    camera.changeCallBack();
                    GamingState.cameraChanged=false;
                }

            }

        if( Document.needUpdate){
            document.resize();
            document.update();
            document.recursivelySetGUI(document);

            //ShaderUtils.twoDColorBuffer.clear();   OpenglUtils.checkGLError();
            //ShaderManager.uiShaderConfig.getVao().getVertices().clear();   OpenglUtils.checkGLError();

            // this.setPerspective();
            document.shaderRender();
            //div.shaderRender();   OpenglUtils.checkGLError();
            // div2.shaderRender();   OpenglUtils.checkGLError();
            //div3.shaderRender();   OpenglUtils.checkGLError();
            //bag.shaderRender();
            ShaderUtils.update2dImageVao(ShaderManager.uiShaderConfig);   OpenglUtils.checkGLError();
            //  ShaderUtils.update2dColorVao();   OpenglUtils.checkGLError();

            Document.needUpdate=false;
        }
    }





    DropControlCenter dcc = new DropControlCenter();
    int frameCount=0;
    long lastTime=0;
    public void render() {


        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        if(!Switcher.SHADER_ENABLE) {
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
        }
//        Util.checkGLError();

        //glTranslatef( 0.0f, 0.0f, -5.0f );

        Long time = System.currentTimeMillis();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       /* float greenValue = (float)(Math.sin(time.doubleValue())/2+0.5);

        glUseProgram(this.ProgramId);
        Util.checkGLError();
        glBindVertexArray(VaoId);
        Util.checkGLError();
        glDrawArrays(GL_TRIANGLES,0,36);

        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();*/
        if (Switcher.SHADER_ENABLE) {

            ShaderUtils.finalDraw(this.shaderManager.lightShaderConfig);
           /* glUseProgram(this.shaderManager.lightShaderConfig.getProgramId());
            Util.checkGLError();
            glBindVertexArray(shaderManager.lightShaderConfig.getVao().getVaoId());
            glDrawArrays(GL_TRIANGLES, 0, 36);
            Util.checkGLError();
            glBindVertexArray(0);*/
            worldRenderer.render();
            OpenglUtils.checkGLError();
            livingThingManager.render();
            OpenglUtils.checkGLError();
            glUseProgram(0);
        } else {
            camera.Render();
            GL11.glEnable(GL11.GL_TEXTURE_2D);   // be sure textures are on
            worldRenderer.render();
            livingThingManager.render();
            gameGui.update();
           // OpenglUtils.renderCubeTest();
        }
        //OpenglUtils.checkGLError();
        // CoreRegistry.get(NuiManager.class).render();

        //GLApp.print(10,10,"fps:"+fps);
       // LogUtil.println("fps:"+fps);
        //printText();
        //OpenglUtil.glFillRect(0,0,1,1,1,new byte[]{(byte)245,(byte)0,(byte)0},new byte[]{(byte)245,(byte)0,(byte)0});
//          GLApp.drawRect(1,1,51,51);
       /* GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        // fovy, aspect ratio, zNear, zFar
        GLU.gluPerspective(50f, // zoom in or out of view
                1, // shape of viewport rectangle
                .1f, // Min Z: how far from eye position does view start
                1024f); // max Z: how far from eye position does view extend
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glLoadIdentity();//.glLoadIdentity();
        try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*/


//        OpenglUtils.checkGLError();

        /*try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        ShaderUtils.finalDraw(ShaderManager.uiShaderConfig);//2DImage

    }

    public boolean isHibernationAllowed() {
        return false;
    }

    private void initManagers() {
        Client client = new Client();
        client.start();
        CoreRegistry.put(Client.class, client);
        BagController bagController = new BagController();
        CoreRegistry.put(BagController.class, bagController);
        // ResourceManager assetManager=CoreRegistry.putPermanently(ResourceManager.class,new ResourceManager());
        CoreRegistry.put(BlockManager.class,
                new BlockManagerImpl());
        TextureManager textureManager = CoreRegistry.put(TextureManager.class,
                new TextureManager());

        CoreRegistry.put(BlockManager.class,
                new BlockManagerImpl());
        animationManager = new AnimationManager();
        CoreRegistry.put(AnimationManager.class,
                animationManager);

        try {

            LWJGLRenderer renderer = new LWJGLRenderer();
            ThemeManager newTheme = null;
            try {
                if(!Switcher.SHADER_ENABLE)
                newTheme = ThemeManager.createThemeManager(
                        SimpleTest.class.getResource("simple_demo.xml"), renderer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Long startTime = System.nanoTime();
            long duration = System.nanoTime() - startTime;
            System.out.println("Loaded theme in " + (duration / 1000) + " us");
            if(!Switcher.SHADER_ENABLE) {
                GuiRootPane root = new GuiRootPane();//创建root pane
                gameGui = new GUI(root, renderer);//创建gui
                //this.root.addGamingComponent();
                gameGui.setSize();
                gameGui.applyTheme(newTheme);
                gameGui.setBackground(newTheme.getImageNoWarning("gui.background"));
                gameGui.validateLayout();
                gameGui.adjustSize();
                CoreRegistry.put(GUI.class, gameGui);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        /*NuiManager nuiManager = CoreRegistry.put(NuiManager.class,
                new NuiManager());*/

        // AssetManager assetManager =
        // CoreRegistry.putPermanently(AssetManager.class, new
        // AssetManager(moduleManager.getEnvironment()));
        livingThingManager = new LivingThingManager();
    }

    private void initEntities() {
        human = new Human();
        human.setHuman(1, 125, 5, 0, 0, -1, 0, 1, 0);
        CoreRegistry.put(Human.class, human);

        LivingThing livingThing = new LivingThing();
        livingThing.position = new GL_Vector(10,125, 0);
        livingThingManager.setPlayer(human);
        //livingThingManager.add(livingThing);
        SynchronTask task = new SynchronTask();
        task.start();
     /*    player =new Player(CoreRegistry.get(TextureManager.class));
          human.setPlayer(player);
        CoreRegistry.put(Player.class,player);*/
    }

    public void initEvent() {
        //dcc.blockRepository = blockRepository;
        bulletPhysics = new BulletPhysics(/*blockRepository*/);

        mouseControlCenter = new MouseControlCenter(human, camera, this);
        CoreRegistry.put(MouseControlCenter.class, mouseControlCenter);
        mouseControlCenter.bulletPhysics = bulletPhysics;
    }

    public void initSelf() {
        // TODO Auto-generated method stub

        // 暂时用默认的参数初始化manager 然后manager 放到corerepgistry里
        //

		/* read config.cfg */
        try {
            initConfig();

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
            WorldProvider WorldProvider = new WorldProviderWrapper();

            WorldRendererLwjgl worldRenderer = new WorldRendererLwjgl(WorldProvider, chunkProvider, new LocalPlayerSystem(), null, human);

            this.worldRenderer = worldRenderer;
        } catch (Exception e) {
            e.printStackTrace();
            ;
            System.exit(0);
        }

    }

    public void initConfig() throws IOException {
        Config config = Config.load(Config.getConfigFile());
        CoreRegistry.put(Config.class, config);
        //
    }


    public void printText() {
//        print(30, viewportH - 45, "press key 0~9 choose the object");
//        print(30, viewportH - 60, "press key B open package");
//        print(30, viewportH - 75, "press key wasd qe walk and turn direction ");
//        print(30, viewportH - 90, "press space jump ");
//        print(30, viewportH - 105, "press up down look up down ");
        GLApp.print(30, 600 - 120, "cam:" + human.ViewDir);
        // GLApp.print(30, 600 - 135, "fps:" + time.tick());
    }

}
