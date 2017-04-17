package cola.machine.game.myblocks.engine.modes;

import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.model.ui.html.Div;
import cola.machine.game.myblocks.model.ui.html.Image;
import cola.machine.game.myblocks.rendering.assets.texture.Texture;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.ui.chat.view.ChatPanel;
import com.dozenx.game.engine.ui.head.view.HeadPanel;
import com.dozenx.game.engine.ui.inventory.control.BagController;
import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.GameEngine;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.ui.html.Document;

import com.dozenx.game.engine.ui.inventory.view.InventoryPanel;
import com.dozenx.game.network.client.SynchronTask;
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
import com.dozenx.game.engine.ui.inventory.view.PersonPanel;
import com.dozenx.game.engine.ui.toolbar.view.ToolBarView;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import glapp.GLApp;
import glapp.GLCamera;
import glmodel.GL_Vector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

import javax.vecmath.Vector4f;
import java.io.IOException;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class GamingState implements GameState {
    public static GamingState instance;
    public static  Player player;//= new Human();
    public static String catchThing;
    public static boolean cameraChanged=false;
    public static boolean livingThingChanged=true;
    public AttackManager attackManager;

    public ItemManager itemManager;
    public static boolean lightPosChanged =true;
    //LearnOpenglColor learnOpenglColor;
    //GUI startGui;
    WorldRenderer worldRenderer;
    AnimationManager animationManager;
    public LivingThingManager livingThingManager;
    MouseControlCenter mouseControlCenter;
    Thread behaviorManagerThread;
    public BulletPhysics bulletPhysics;
    public GLCamera camera = new GLCamera();

    public GL_Vector lightPos = new GL_Vector(5, 65, 2);
    //shader constants

    public double preKeyTime = 0;

    GUI gameGui;
    Document document ;



    public void init(GameEngine engine) {

        document= Document.getInstance();
        this.instance =this;
        ShaderManager shaderManager = ShaderManager.getInstance();
        /*shaderManager.init();*/
        //learnOpenglColor=new LearnOpenglColor();

        try {

            initGL();

            lastTime= TimeUtil.getNowMills();
            this.initManagers();
            this.initEntities();
            this.initEvent();

            mouseControlCenter.livingThingManager = this.livingThingManager;
            initSelf();

            /*behaviorManagerThread = new BehaviorManager();
            behaviorManagerThread.start();*/

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
            document.body.appendChild(new PersonPanel(1,5));
            document.body.appendChild(new ToolBarView(10,1));

            document.body.appendChild(new ChatPanel());


            HeadPanel playerHeadPanel =new HeadPanel();
            document.body.appendChild(playerHeadPanel);
            playerHeadPanel.bind(player);
            HeadPanel enemyHeadPanel =new HeadPanel();

            enemyHeadPanel.setVisible(false);
            CoreRegistry.put(HeadPanel.class,enemyHeadPanel);
            document.body.appendChild(enemyHeadPanel);

            document.needUpdate=true;
             shadowDiv =new Div();
            shadowDiv.setBorderWidth(2);
            shadowDiv.setBorderColor(new Vector4f(1f, 1f, 1f, 1f));
            shadowDiv.setWidth(200);
            shadowDiv.setHeight(200);
            shadowDiv.setBackgroundImage(new Image(TextureManager.getTextureInfo("items")));
            document.body.appendChild(shadowDiv);

            SynchronTask task = new SynchronTask();
            task.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }Div shadowDiv;

    public void dispose() {

    }
public ShaderManager shaderManager;
    public void initGL() {
        if (Switcher.SHADER_ENABLE) {
            if(shaderManager==null){
                shaderManager= ShaderManager.getInstance();
            }

            //shaderManager.init();

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
boolean handled;
    public void handleInput(float delta) {
        cursorX = Mouse.getEventX();
        cursorY = Mouse.getEventY();
        int mouseDW = Mouse.getDWheel();
        if (mouseDW != 0) {
            //mouseControlCenter.mousewmouseWheel(mouseDW);
        }
        GUI gui = CoreRegistry.get(GUI.class);



       // handled=false;

        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {

               if(document.handleKey(
                        Keyboard.getEventKey(),
                        Keyboard.getEventCharacter(),
                        Keyboard.getEventKeyState())){
                   //handled=true;
                   continue;
               }

                keyDown(Keyboard.getEventKey());


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

        //if(!handled){
       // if(/*!document.hasFocusChild()*/document.getFocusKeyWidget()==null)//如果没有键盘粘纸的控件那么就放过
            mouseControlCenter.handleNavKeys(delta);
        //}
        if (Mouse.isCreated()) {
            mouseControlCenter.mouseMove(cursorX, cursorY);
            while (Mouse.next()) {

                if(document.handleMouse(
                        Mouse.getEventX(), Constants.WINDOW_HEIGHT - Mouse.getEventY() - 1,
                        Mouse.getEventButton(), Mouse.getEventButtonState())){
                    if( Mouse.getEventButtonState() ==false){

                    }else{
                        continue;
                    }

                }
                int wheelDelta = Mouse.getEventDWheel();
                if (wheelDelta != 0) {
                    mouseControlCenter.handleMouseWheel(-wheelDelta / 40);
                }



                //LogUtil.println("Mouse.getEventButton()"+Mouse.getEventButton());


                if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() == true) {
                    mouseControlCenter.mouseLeftDown(cursorX, cursorY);
                    TextureInfo info =new TextureInfo();
                    info.textureHandle=shaderManager.hdrTextureHandler;
                    info.minX=0;
                    info.minY=0;
                    info.maxX=1;
                    info.maxY=1;
                    shadowDiv.setBackgroundImage(new Image(info));
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

            lightPos.x += -1;

            shaderManager.lightPosChangeListener();

        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            lightPos.x += 1.1;

            shaderManager.lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            lightPos.z -= 1.1;

            shaderManager.lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            lightPos.z += 1.1;

            shaderManager.lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
            lightPos.y += 1.1;

            shaderManager. lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
            lightPos.y -= 1.1;

            shaderManager.lightPosChangeListener();

        }

    }

    long nowTime=0;
    int fps=0;
    public void update(float delta) {
        TimeUtil.update();
        nowTime= TimeUtil.getNowMills();
        if((nowTime-lastTime)>60000){
            fps =frameCount;
            frameCount=0;
            lastTime=nowTime;
        }else{
            frameCount++;

      }
//        LogUtil.println("帧率"+frameCount*1000/(nowTime-lastTime));
        attackManager.update();OpenglUtils.checkGLError();
        try {
            animationManager.update();
        } catch (Exception e) {
            e.printStackTrace();
        }OpenglUtils.checkGLError();
       // livingThingManager.netWorkUpdate();;
        livingThingManager.update();
            if (Math.random() > 0.5) {
            if (!Switcher.IS_GOD) {
                //dcc.check(human);
                livingThingManager.CrashCheck(dcc);
                GL_Vector camera_pos = GL_Vector.add(player.position,
                        GL_Vector.multiply(player.viewDir, Switcher.CAMERA_MODEL == 2 ? Switcher.CAMERA_2_PLAYER : (-1 * Switcher.CAMERA_2_PLAYER)));
                camera.MoveTo(camera_pos.x, camera_pos.y + 2, camera_pos.z);

                if (Switcher.CAMERA_MODEL == 2) {
                    // camera1.ViewDir.reverse();
                    camera.ViewDir = new GL_Vector(player.viewDir.x * -1, player.viewDir.y * -1, player.viewDir.z * -1);
                } else {

                    camera.viewDir(player.viewDir);
                }
            }
                if(GamingState.cameraChanged){
                    camera.changeCallBack();
                    GamingState.cameraChanged=false;
                }
                if(GamingState.lightPosChanged){
                    //camera.changeCallBack();

                    shaderManager.lightPosChangeListener();
                    GamingState.cameraChanged=false;
                }


            }
        document.update();OpenglUtils.checkGLError();
        itemManager.update();
    }





    DropControlCenter dcc = new DropControlCenter();
    int frameCount=0;
    long lastTime=0;
    public void preUpdate(){

    }

    public void  keyDown(int key){
        mouseControlCenter.keyDown(key);
    }

    boolean shadow =false;

    /**
     * 主要绘制流程
     */
    public void render() {

        //不应该每次都绘制 二应该放入
        if(Constants.SHADOW_ENABLE &&  Math.random()>0.1) {

            GL20.glUseProgram(shaderManager.shadowShaderConfig.getProgramId());
           // glEnable(GL_DEPTH_TEST);
            //glUniformMatrix4fv(lightSpaceMatrixLocation, 1, GL_FALSE, glm::value_ptr(lightSpaceMatrix));
            glViewport(0, 0, 1024, 1024);
            //绑定使用帧缓冲 fbo
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, shaderManager.depthMapFBO);
            glClear(GL_DEPTH_BUFFER_BIT);
            //GL30.RenderScene(simpleDepthShader);
            worldRenderer.render(shaderManager.shadowShaderConfig);
            //去掉fbo
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
            glViewport(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
           // glBindTexture(GL_TEXTURE_2D, shaderManager.depthMap);
        }




        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_BLEND);
        //亮度按照人眼做调整 2.2次方
       // glEnable(GL30.GL_FRAMEBUFFER_SRGB);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
       // OpenglUtils.checkGLError();
        if(!Switcher.SHADER_ENABLE) {
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
            OpenglUtils.checkGLError();
        }

//        Util.checkGLError();

        //glTranslatef( 0.0f, 0.0f, -5.0f );

        Long time = TimeUtil.getNowMills();
       /* try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
       /* float greenValue = (float)(Math.sin(time.doubleValue())/2+0.5);

        glUseProgram(this.ProgramId);
        Util.checkGLError();
        glBindVertexArray(VaoId);
        Util.checkGLError();
        glDrawArrays(GL_TRIANGLES,0,36);

        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();*/

       // if(Constants.HDR_ENABLE &&  Math.random()>0.1) {

            //绑定使用帧缓冲 fbo
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, shaderManager.hdrFBO);
            // ShaderUtils.finalDraw(ShaderManager.hdrShaderConfig,ShaderManager.lightShaderConfig.getVao());
            glClear(GL_DEPTH_BUFFER_BIT);
            worldRenderer.render();
            livingThingManager.render();
            //去掉fbo
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
            //把上一帧的画面留给缓冲帧对应的纹理 保存下来
//            GL20.glUseProgram(shaderManager.hdrShaderConfig.getProgramId());
//
//            // glActiveTexture(GL_TEXTURE0);
//            shaderManager.hdrShaderConfig.getVao().getVertices().rewind();
//            ShaderUtils.draw2dImg(shaderManager.hdrShaderConfig,shaderManager.hdrShaderConfig.getVao(),TextureManager.getTextureInfo("items").textureHandle/*shaderManager.hdrTextureHandler*/);
//            ShaderUtils.createVao(shaderManager.hdrShaderConfig,shaderManager.hdrShaderConfig.getVao(),new int[]{2,2});
//            ShaderUtils.finalDraw(shaderManager.hdrShaderConfig,shaderManager.hdrShaderConfig.getVao());
            //用制定的hdr进行重新绘制
        //}
        if (Switcher.SHADER_ENABLE) {


           /* glUseProgram(this.shaderManager.lightShaderConfig.getProgramId());
            Util.checkGLError();
            glBindVertexArray(shaderManager.lightShaderConfig.getVao().getVaoId());
            glDrawArrays(GL_TRIANGLES, 0, 36);
            Util.checkGLError();
            glBindVertexArray(0);*/

            ShaderUtils.finalDraw(ShaderManager.lightShaderConfig,ShaderManager.lightShaderConfig.getVao());

//
            OpenglUtils.checkGLError();
            worldRenderer.render();
            OpenglUtils.checkGLError();
            livingThingManager.render();
            OpenglUtils.checkGLError();


        } else {
            camera.Render();
            GL11.glEnable(GL11.GL_TEXTURE_2D);   // be sure textures are on
            worldRenderer.render();
            livingThingManager.render();
            gameGui.update();

           // OpenglUtils.renderCubeTest();
        }
        OpenglUtils.checkGLError();
        document.render();
        itemManager.render();
        attackManager.render();
        OpenglUtils.checkGLError();
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



    }

    public boolean isHibernationAllowed() {
        return false;
    }

    private void initManagers() {

        BagController bagController = new BagController(player);
        CoreRegistry.put(BagController.class, bagController);
        //CoreRegistry.put(InventoryController.class, new InventoryController());
        // ResourceManager assetManager=CoreRegistry.putPermanently(ResourceManager.class,new ResourceManager());
        CoreRegistry.put(BlockManager.class,
                new BlockManagerImpl());

        TextureManager textureManager = CoreRegistry.get(TextureManager.class);

        if(textureManager==null){//再开始菜单的时候已经初始化过了
            textureManager= CoreRegistry.put(TextureManager.class,
                    new TextureManager());
        }

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
        //livingThingManager = new LivingThingManager();
        livingThingManager= CoreRegistry.get(LivingThingManager.class);


         itemManager =new ItemManager();
        attackManager =new AttackManager(livingThingManager);

        CoreRegistry.put(ItemManager.class,itemManager);
        try {
            itemManager.loadItem();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.err(e);
        }
    }

    private void initEntities() {
        /*human = new Human();
        human.setHuman(1, 53,-25, 0, 0, -1, 0, 1, 0);
        CoreRegistry.put(Human.class, human);
        human.id= Constants.USER_ID;
       // LivingThing livingThing = new LivingThing();
       // livingThing.position = new GL_Vector(10,125, 0);
        livingThingManager.setPlayer(human);*/
        //livingThingManager.add(livingThing);

     /*    player =new Player(CoreRegistry.get(TextureManager.class));
          human.setPlayer(player);
        CoreRegistry.put(Player.class,player);*/
    }

    public void initEvent() {
        //dcc.blockRepository = blockRepository;
        bulletPhysics = new BulletPhysics(/*blockRepository*/);

        mouseControlCenter = new MouseControlCenter(player, camera, this);
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

            WorldRendererLwjgl worldRenderer = new WorldRendererLwjgl(WorldProvider, chunkProvider, new LocalPlayerSystem(), null, player);

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
        GLApp.print(30, 600 - 120, "cam:" + player.viewDir);
        // GLApp.print(30, 600 - 135, "fps:" + time.tick());
    }

}
