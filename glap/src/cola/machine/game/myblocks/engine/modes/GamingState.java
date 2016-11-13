package cola.machine.game.myblocks.engine.modes;

import cola.machine.game.myblocks.action.BagController;
import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.engine.GameEngine;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.lifething.manager.BehaviorManager;
import cola.machine.game.myblocks.lifething.manager.LivingThingManager;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.model.ui.NuiManager;
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
import com.dozenx.game.opengl.util.OpenglUtils;
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

import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class GamingState implements GameState{
    public static  Human human;//= new Human();
    public static String catchThing;
    //LearnOpenglColor learnOpenglColor;
    //GUI startGui;
    WorldRenderer worldRenderer;
    AnimationManager animationManager;
    public LivingThingManager livingThingManager;
    MouseControlCenter mouseControlCenter;
    Thread behaviorManagerThread;
    public BulletPhysics bulletPhysics;
    GLCamera camera = new GLCamera();

    GL_Vector lightPos = new GL_Vector(5,5,2);
    //shader constants
    public static int terrainProgramId;
    public static int VaoId;
    public static int VboId;
    public static int viewPosLoc;
    public static int LightProgramId;
    public static int viewLoc;//glGetUniformLocation(ProgramId,"view");
    public static int lightViewLoc;// glGetUniformLocation(LightProgramId,"view");
    public double preKeyTime = 0;

GUI gameGui;
    public void init(GameEngine engine){
        /*ShaderManager shaderManager =new ShaderManager();
        shaderManager.init();*/
        //learnOpenglColor=new LearnOpenglColor();

        try {

                initGL();


            this.initManagers();
            this.initEntities();
            this.initEvent();

            mouseControlCenter.livingThingManager=this.livingThingManager;
            initSelf();

            behaviorManagerThread  =new BehaviorManager();
            behaviorManagerThread.start();

            /*LWJGLRenderer renderer = null;//调用lwjgl能力
                renderer = new LWJGLRenderer();

                LoginDemo loginDemo = new LoginDemo();
                startGui = new GUI(loginDemo, renderer);
                ThemeManager theme = ThemeManager.createThemeManager(
                        LoginDemo.class.getResource("login.xml"), renderer);
                startGui.applyTheme(theme);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dispose(){

    }

    public void initGL(){
        if(Switcher.SHADER_ENABLE) {
            
            this.CreateTerrainProgram();
            this.CreateTerrainVAO();
            this.uniformTerrian();

            this.CreateLightProgram();
            this.CreateLightVAO();
            this.uniformLight();
        }else{

            try {
                // Depth test setup
                GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
                GL11.glDepthFunc(GL11.GL_LEQUAL);  // The Type Of Depth Testing To Do

                // Some basic settings
                GL11.glClearColor(0f, 0f, 0f, 1f); // Black Background
                GL11.glEnable(GL11.GL_NORMALIZE);  // force normal lengths to 1
                GL11.glEnable(GL11.GL_CULL_FACE);  // don't render hidden faces
                GL11.glEnable(GL11.GL_TEXTURE_2D); // use textures
                GL11.glEnable(GL11.GL_BLEND);      // enable transparency

                // How to handle transparency: average colors together
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                // Enable alpha test so the transparent backgrounds in texture assets.images don't
                // draw.
                // This prevents transparent areas from affecting the depth or stencil buffer.
                // alpha func will accept only fragments with alpha greater than 0
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0f);

                // Draw specular highlghts on top of textures
                GL11.glLightModeli(GL12.GL_LIGHT_MODEL_COLOR_CONTROL, GL12.GL_SEPARATE_SPECULAR_COLOR );

                // Perspective quality
                GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

                // Set the size and shape of the screen area
                GL11.glViewport(viewportX, viewportY, viewportW, viewportH);

                // setup perspective (see setOrtho() for 2D)
                setPerspective();

                // select model view for subsequent transformations
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glLoadIdentity();
            }
            catch (Exception e) {
                err("GLApp.initOpenGL(): " + e);
            }

        }

    }
    int cursorX;
    int cursorY;
    public void handleInput(float delta){
        cursorX=Mouse.getEventX();
        cursorY=Mouse.getEventY();
        int mouseDW = Mouse.getDWheel();
        if (mouseDW != 0) {
            //mouseControlCenter.mousewmouseWheel(mouseDW);
        }
        GUI gui = CoreRegistry.get(GUI.class);

        if(Keyboard.isCreated()) {
            while (Keyboard.next()) {
                // check for exit key
           /* if (Keyboard.getEventKey() == finishedKey) {
                finished = true;
            }*/

                // pass key event to handler
                //System.out.println("Character"+Keyboard.getEventCharacter());

                // pass key event to handler
                //LogUtil.println("Character"+Keyboard.getEventCharacter());


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
        if(Mouse.isCreated())
        {
            mouseControlCenter.mouseMove(cursorX,cursorY);
            while (Mouse.next()) {



               int wheelDelta = Mouse.getEventDWheel();
                if (wheelDelta != 0) {
                    //gui.handleMouseWheel(wheelDelta / 120);
                }
                //LogUtil.println("Mouse.getEventButton()"+Mouse.getEventButton());


                if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() == true) {
                    mouseControlCenter.mouseLeftDown(cursorX, cursorY);
                }
                if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() == false) {
                    mouseControlCenter. mouseLeftUp(cursorX, cursorY);
                }
                if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState() == true) {
                    mouseControlCenter. mouseRightDown(cursorX, cursorY);
                }
                if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState() == false) {
                    mouseControlCenter. mouseRightUp(cursorX, cursorY);
                }
                //GUI gui = CoreRegistry.get(GUI.class);
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

            lightPos.x+=-0.1;

            lightPosChangeListener();

        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            lightPos.x+=0.1;

            lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            lightPos.z-=0.1;

            lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            lightPos.z+=0.1;

            lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
            lightPos.y+=0.1;

            lightPosChangeListener();

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            lightPos.y-=0.1;

            lightPosChangeListener();

        }

    }


    public void update(float delta){
        AttackManager.update();
        try {
            animationManager.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void CreateTerrainVAO(){


        VaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(VaoId);
        Util.checkGLError();
        this.CreateTerrainVBO();

        glBindVertexArray(0);
        Util.checkGLError();
    }
    public void CreateTerrainVBO(){
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        float VerticesArray[]= { -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                -0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,

                -0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                -0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,

                -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,

                0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
                0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
                0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,

                -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
                0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
                0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,

                -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
                0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                -0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f};
        FloatBuffer Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);


        Vertices.put(VerticesArray);
        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty
        VboId=glGenBuffers();//create vbo


        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data

        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);
        Util.checkGLError();

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3*4);

        Util.checkGLError();
        glEnableVertexAttribArray(1);
        Util.checkGLError();




    }
    GL_Matrix projection= GL_Matrix.perspective3(45,600/600,1f,1000.0f);
    FloatBuffer cameraViewBuffer = BufferUtils.createFloatBuffer(16);
    public void lightPosChangeListener(){

        glUseProgram(terrainProgramId);

        glUniform3f(lightPosInTerrainLoc,lightPos.x,lightPos.y,lightPos.z);
        Util.checkGLError();

        glUseProgram(LightProgramId);

        GL_Matrix model = GL_Matrix.translateMatrix(lightPos.x,lightPos.y,lightPos.z);
        glUniformMatrix4(lightModelLoc, false, model.toFloatBuffer());
        Util.checkGLError();


    }
    public void cameraPosChangeListener1(){
        GL_Matrix view=
                GL_Matrix.LookAt(camera.Position,camera.ViewDir);
        view.fillFloatBuffer(cameraViewBuffer);

        glUseProgram(terrainProgramId);



        glUniformMatrix4(viewLoc,  false,cameraViewBuffer);
        Util.checkGLError();
        //viewPosLoc= glGetUniformLocation(ProgramId,"viewPos");


        glUniform3f(viewPosLoc,  camera.Position.x,camera.Position.y,camera.Position.z);
        Util.checkGLError();


        glUseProgram(LightProgramId);
        Util.checkGLError();
        //lightViewLoc= glGetUniformLocation(LightProgramId,"view");


        glUniformMatrix4(lightViewLoc,  false,view.toFloatBuffer() );

        Util.checkGLError();

    }
    public void uniformTerrian(){
        /*
        uniform mat4 projection;
        uniform mat4 view;
        uniform mat4 model;
        uniform vec3 objectColor;物体的颜色
        uniform vec3 lightColor;灯光的颜色
        uniform vec3 lightPos;灯光的位置
        uniform vec3 viewPos;相机的位置*/


        //GL_Matrix view= GL_Matrix.translateMatrix(0,0,-3);




        //glUseProgram(ProgramId);
        glUseProgram(terrainProgramId);
        //unifrom赋值===========================================================
        //投影矩阵
        int projectionLoc= glGetUniformLocation(terrainProgramId, "projection");
        Util.checkGLError();
        glUniformMatrix4(projectionLoc,  false,projection.toFloatBuffer() );
        Util.checkGLError();

        //相机位置
        GL_Matrix view=
                GL_Matrix.LookAt(camera.Position,camera.ViewDir);
        view.fillFloatBuffer(cameraViewBuffer);
        viewLoc = glGetUniformLocation(terrainProgramId,"view");

        Util.checkGLError();
        glUniformMatrix4(viewLoc,  false,view.toFloatBuffer() );
        Util.checkGLError();


        GL_Matrix model= GL_Matrix.rotateMatrix((float)(0*3.14/180.0),0,0);
        int modelLoc= glGetUniformLocation(terrainProgramId, "model");
        Util.checkGLError();
        glUniformMatrix4(modelLoc, false, model.toFloatBuffer());
        Util.checkGLError();


        viewLoc= glGetUniformLocation(terrainProgramId, "view");
        Util.checkGLError();



        //物体颜色
        int objectColorLoc= glGetUniformLocation(terrainProgramId, "objectColor");
        glUniform3f(objectColorLoc,1.0f,0.5f,0.31f);
        Util.checkGLError();

        //环境光颜色

       /*  lightColorLoc= glGetUniformLocation(ProgramId, "lightColor");
        glUniform3f(lightColorLoc,1.0f,1f,1f);
        Util.checkGLError();*/

        lightPosInTerrainLoc= glGetUniformLocation(terrainProgramId, "light.position");
        if(lightPosInTerrainLoc==-1){
            LogUtil.println("light.position not found ");
            System.exit(1);
        }
        glUniform3f(lightPosInTerrainLoc,lightPos.x,lightPos.y,lightPos.z);
        Util.checkGLError();

        viewPosLoc= glGetUniformLocation(terrainProgramId,"viewPos");
        if(viewPosLoc==-1){
            LogUtil.println("viewPos not found ");
            System.exit(1);
        }
        glUniform3f(viewPosLoc,lightPos.x,lightPos.y,lightPos.z);
        Util.checkGLError();


        //int matAmbientLoc = glGetUniformLocation(ProgramId, "material.ambient");
        //int matDiffuseLoc = glGetUniformLocation(ProgramId, "material.diffuse");
        int matSpecularLoc = glGetUniformLocation(terrainProgramId, "material.specular");
        int matShineLoc = glGetUniformLocation(terrainProgramId, "material.shininess");

        //glUniform3f(matAmbientLoc, 1.0f, 0.5f, 0.31f);
        //glUniform3f(matDiffuseLoc, 1.0f, 0.5f, 0.31f);
        glUniform3f(matSpecularLoc, 0.5f, 0.5f, 0.5f);
        glUniform1f(matShineLoc, 32.0f);

        int lightMatAmbientLoc = glGetUniformLocation(terrainProgramId, "light.ambient");
        int lightMatDiffuseLoc = glGetUniformLocation(terrainProgramId, "light.diffuse");
        int lightMatSpecularLoc = glGetUniformLocation(terrainProgramId, "light.specular");
        int lightMatShineLoc = glGetUniformLocation(terrainProgramId, "light.shininess");

        glUniform3f(lightMatAmbientLoc, 0.5f, 0.5f, 0.5f);
        glUniform3f(lightMatDiffuseLoc, 0.5f, 0.5f, 0.5f);
        glUniform3f(lightMatSpecularLoc, 0.5f, 0.5f, 0.5f);
        glUniform1f(lightMatShineLoc, 32.0f);

        glUniform1f(glGetUniformLocation(terrainProgramId, "light.constant"), 1.0f);
        glUniform1f(glGetUniformLocation(terrainProgramId, "light.linear"), 0.07f);
        glUniform1f(glGetUniformLocation(terrainProgramId, "light.quadratic"), 0.017f);

    }
    int lightColorLoc;
    int lightPosInTerrainLoc;
    int lightPosLoc;

    int lightVaoId;
    public void CreateTerrainProgram(){
        try {
            terrainProgramId = OpenglUtils.CreateProgram("chapt16/box.vert", "chapt16/box.frag");
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    public void CreateLightProgram(){
        try {
            LightProgramId = OpenglUtils.CreateProgram("chapt13/light.vert", "chapt13/light.frag");
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    public void CreateLightVAO(){
        lightVaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(lightVaoId);
        Util.checkGLError();

        this.CreateLightVBO();
        glBindVertexArray(0);
        Util.checkGLError();

    }
    public void CreateLightVBO(){

        //创建vao2=========================================================
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects

        // model = GL_Matrix.multiply(view2,model);
        // VboId=glGenBuffers();//create vbo
        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
       // glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data
        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);
        Util.checkGLError();
        glEnableVertexAttribArray(0);
        Util.checkGLError();
    }
    int lightModelLoc;
    public void uniformLight(){
        GL_Matrix model= GL_Matrix.translateMatrix(lightPos.x,lightPos.y,lightPos.z);
        GL_Matrix view= GL_Matrix.translateMatrix(0,0,0);
        glUseProgram(LightProgramId);
        int lightProjectionLoc= glGetUniformLocation(LightProgramId, "projection");
        glUniformMatrix4(lightProjectionLoc,  false,projection.toFloatBuffer() );
        Util.checkGLError();
        lightModelLoc= glGetUniformLocation(LightProgramId, "model");
        glUniformMatrix4(lightModelLoc, false, model.toFloatBuffer());
        Util.checkGLError();

        lightViewLoc= glGetUniformLocation(LightProgramId, "view");
        glUniformMatrix4(lightViewLoc,  false,view.toFloatBuffer() );
        Util.checkGLError();

    }
    DropControlCenter dcc = new DropControlCenter();
    public void render(){


        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        if (!Switcher.IS_GOD)
            if (Math.random() > 0.5) {
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
                camera.changeCallBack();
            }
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
//        Util.checkGLError();

        //glTranslatef( 0.0f, 0.0f, -5.0f );

        Long time =System.currentTimeMillis();
       /* float greenValue = (float)(Math.sin(time.doubleValue())/2+0.5);

        glUseProgram(this.ProgramId);
        Util.checkGLError();
        glBindVertexArray(VaoId);
        Util.checkGLError();
        glDrawArrays(GL_TRIANGLES,0,36);

        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();*/
    if(Switcher.SHADER_ENABLE){
        glUseProgram(this.LightProgramId);
        Util.checkGLError();
        glBindVertexArray(lightVaoId);
        glDrawArrays(GL_TRIANGLES, 0, 36);
        Util.checkGLError();
        glBindVertexArray(0);
        worldRenderer.render();
        try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }
        livingThingManager.render();
        try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }
        glUseProgram(0);
    }else{
        worldRenderer.render();
        livingThingManager.render();
        camera.Render();
    }
       // CoreRegistry.get(NuiManager.class).render();
        gameGui.update();
        printText();
        //OpenglUtil.glFillRect(0,0,1,1,1,new byte[]{(byte)245,(byte)0,(byte)0},new byte[]{(byte)245,(byte)0,(byte)0});
          GLApp.drawRect(1,1,1,1);
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



    }

    public boolean isHibernationAllowed(){
        return false;
    }

    private void initManagers() {
        Client client =new Client();
        client.start();
        CoreRegistry.put(Client.class,client);
        BagController bagController =new BagController();
        CoreRegistry.put(BagController.class,bagController);
        // ResourceManager assetManager=CoreRegistry.putPermanently(ResourceManager.class,new ResourceManager());
        CoreRegistry.put(BlockManager.class,
                new BlockManagerImpl());
        TextureManager textureManager = CoreRegistry.put(TextureManager.class,
                new TextureManager());

        CoreRegistry.put(BlockManager.class,
                new BlockManagerImpl());
        animationManager=  new AnimationManager();
        CoreRegistry.put(AnimationManager.class,
                animationManager);

try {

    LWJGLRenderer renderer = new LWJGLRenderer();
    ThemeManager newTheme = null;
    try {
        newTheme = ThemeManager.createThemeManager(
                SimpleTest.class.getResource("simple_demo.xml"), renderer);
    } catch (IOException e) {
        e.printStackTrace();
    }

    Long startTime = System.nanoTime();
    long duration = System.nanoTime() - startTime;
    System.out.println("Loaded theme in " + (duration / 1000) + " us");
    GuiRootPane root = new GuiRootPane();//创建root pane
    gameGui = new GUI(root, renderer);//创建gui
    //this.root.addGamingComponent();
    gameGui.setSize();
    gameGui.applyTheme(newTheme);
    gameGui.setBackground(newTheme.getImageNoWarning("gui.background"));
    gameGui.validateLayout();
    gameGui.adjustSize();
    CoreRegistry.put(GUI.class, gameGui);

}catch (Exception e ){
    e.printStackTrace();
    System.exit(1);
}
        /*NuiManager nuiManager = CoreRegistry.put(NuiManager.class,
                new NuiManager());*/

        // AssetManager assetManager =
        // CoreRegistry.putPermanently(AssetManager.class, new
        // AssetManager(moduleManager.getEnvironment()));
        livingThingManager =new LivingThingManager();
    }
    private void initEntities(){
        human = new Human();
        human.setHuman(1, 4, 5, 0, 0, -1, 0, 1, 0);
        CoreRegistry.put(Human.class, human);

        LivingThing livingThing =new LivingThing();
        livingThing.position=new GL_Vector(10,4,0);
        livingThingManager.setPlayer(human);
        //livingThingManager.add(livingThing);
        SynchronTask task =new SynchronTask();
        task.start();
     /*    player =new Player(CoreRegistry.get(TextureManager.class));
          human.setPlayer(player);
        CoreRegistry.put(Player.class,player);*/
    }
    public void initEvent(){
        //dcc.blockRepository = blockRepository;
        bulletPhysics = new BulletPhysics(/*blockRepository*/);

        mouseControlCenter = new MouseControlCenter(human, camera,this);
        CoreRegistry.put(MouseControlCenter.class,mouseControlCenter);
        mouseControlCenter.bulletPhysics = bulletPhysics;
    }

    public void initSelf()  {
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
        }catch(Exception e ){
            e.printStackTrace();;
            System.exit(0);
        }

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
        GLApp.print(30, 600 - 120, "cam:" + human.ViewDir);
       // GLApp.print(30, 600 - 135, "fps:" + time.tick());
    }

}
