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
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.model.ui.NuiManager;
import cola.machine.game.myblocks.model.ui.bag.Bag;
import cola.machine.game.myblocks.model.ui.html.Div;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.model.ui.html.Image;
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
import cola.machine.game.myblocks.ui.login.LoginDemo;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.block.internal.BlockManagerImpl;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.chunks.LocalChunkProvider;
import cola.machine.game.myblocks.world.generator.WorldGenerators.PerlinWorldGenerator;
import cola.machine.game.myblocks.world.internal.WorldProviderWrapper;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import glapp.GLApp;
import glapp.GLCamera;
import glapp.GLImage;
import gldemo.learnOpengl.chapt13.LearnOpenglColor;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLU;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class StartMenuState implements GameState {
   //Bag bag;
    public static String catchThing;
    //LearnOpenglColor learnOpenglColor;
    GUI gui;
Document document =new Document();
    public void init(GameEngine engine) {
        document.setWidth(Constants.WINDOW_WIDTH);
        document.setHeight(Constants.WINDOW_HEIGHT);
        document.body.setWidth(Constants.WINDOW_WIDTH);
        document.body.setHeight(Constants.WINDOW_HEIGHT);
        TextureManager textureManager =new TextureManager();

        Div div3 =new Div();div3.setId("3");
        Div div2 =new Div();div2.setId("2");
        Div div =new Div();div.setId("1");
       // div=new Div();
       // bag =new Bag();

        //div.setId("bag");
        //document.body.appendChild(div3);
        //document.body.appendChild(div2);
        document.body.appendChild(div);
        div.appendChild(div3);
        div.appendChild(div2);
        //div.margin="0 auto";
        div.setWidth(100);
        div.setHeight(100);
        div.setLeft(110);
        div.setTop(110);
        div.setFontSize(12);
        //div.bottom=100;
        div.setBorderWidth(10);
       div.setInnerText("一行白鹭路上青天");
        //div.background_image="soil";
        //TextureInfo batTexture= TextureManager.getTextureInfo("soil");
       // div.set(new Vector4f(1,0.5f,0.5f,1));
       // div.setBackgroundImage(new Image(TextureManager.getTextureInfo("fur_pants")));
        div.setBorderColor(new Vector4f(1,0.5f,0.5f,1));
        //div.setBackgroundColor(new Vector4f(1,1f,1f,1));
        //div.update();



        //div.margin="0 auto";
        div2.setWidth(100);
        div2.setHeight(100);
        div2.setLeft(210);
        div2.setTop(110);
        div2.setInnerText("两只黄鹂鸣翠柳");
        //div.bottom=100;
        div2.setBorderWidth(10);
        //div.background_image="soil";
        //TextureInfo batTexture= TextureManager.getTextureInfo("soil");
        // div.set(new Vector4f(1,0.5f,0.5f,1));
       // div2.setBackgroundImage(new Image(TextureManager.getTextureInfo("iron_helmet_front")));
        div2.setBorderColor(new Vector4f(1,0.5f,0.5f,1));
        //div.setBackgroundColor(new Vector4f(1,1f,1f,1));
       // div2.update();
        OpenglUtils.checkGLError();


        div3.setWidth(100);
        div3.setHeight(100);
        div3.setLeft(310);
        div3.setLeft(310);
        div3.setInnerText("三个铜板买来的");
        //div.bottom=100;
        div3.setBorderWidth(10);
        //div.background_image="soil";
        //TextureInfo batTexture= TextureManager.getTextureInfo("soil");
        // div.set(new Vector4f(1,0.5f,0.5f,1));
       // div3.setBackgroundImage(new Image(TextureManager.getTextureInfo("zhongwen")));
        div3.setBorderColor(new Vector4f(1,0.5f,0.5f,1));
        //div.setBackgroundColor(new Vector4f(1,1f,1f,1));
        document.update();


        try {


            LWJGLRenderer renderer = new LWJGLRenderer();//调用lwjgl能力
            renderer.setUseSWMouseCursors(true);   OpenglUtils.checkGLError();

            //ChatDemo chat = new ChatDemo();
            //GameUIDemo gameUI = new GameUIDemo();
            LoginDemo loginDemo = new LoginDemo();
            gui = new GUI(loginDemo, renderer);
if(!Switcher.SHADER_ENABLE) {
    ThemeManager theme = ThemeManager.createThemeManager(
            LoginDemo.class.getResource("login.xml"), renderer);
    gui.applyTheme(theme);
}
        } catch (Exception e) {
            e.printStackTrace();
        }   OpenglUtils.checkGLError();

        ShaderUtils.twoDColorBuffer.rewind();   OpenglUtils.checkGLError();
        ShaderUtils.twoDImgBuffer.rewind();   OpenglUtils.checkGLError();
        // this.setPerspective();
        document.shaderRender();
        //div.shaderRender();   OpenglUtils.checkGLError();
       // div2.shaderRender();   OpenglUtils.checkGLError();
        //div3.shaderRender();   OpenglUtils.checkGLError();
        //bag.shaderRender();
        ShaderUtils.update2dColorVao();   OpenglUtils.checkGLError();
        ShaderUtils.update2dImageVao();   OpenglUtils.checkGLError();
        /*ShaderManager shaderManager =new ShaderManager();
        shaderManager.init();*/
        //learnOpenglColor=new LearnOpenglColor();

       /* GLImage image;
        image= OpenglUtils.makeTexture("assets/images/items.png");
        glBindTexture(GL_TEXTURE_2D, image.textureHandle);*/

    }

    public void dispose() {

    }


    public void handleInput(float delta) {


       /* if (Keyboard.isCreated()) {
            while (Keyboard.next()) {

                gui.handleKey(
                        Keyboard.getEventKey(),
                        Keyboard.getEventCharacter(),
                        Keyboard.getEventKeyState());


            }
        }*/
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                document.handleMouse(
                        Mouse.getEventX(), gui.getHeight() - Mouse.getEventY() - 1,
                        Mouse.getEventButton(), Mouse.getEventButtonState());

            }
        }


    }


    public void update(float delta) {
        if( Document.needUpdate){
            ShaderUtils.twoDColorBuffer.rewind();   OpenglUtils.checkGLError();
            ShaderUtils.twoDImgBuffer.rewind();   OpenglUtils.checkGLError();
            // this.setPerspective();
            document.shaderRender();
            //div.shaderRender();   OpenglUtils.checkGLError();
            // div2.shaderRender();   OpenglUtils.checkGLError();
            //div3.shaderRender();   OpenglUtils.checkGLError();
            //bag.shaderRender();
            ShaderUtils.update2dColorVao();   OpenglUtils.checkGLError();
            ShaderUtils.update2dImageVao();   OpenglUtils.checkGLError();
            Document.needUpdate=false;
        }
    }

    public float rotation = 0f;

    @Override
    public void render() {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //bag.shaderRender();
        //glUseProgram(0);

      // gui.update();
        //OpenglUtils.drawRect();
        //print(30, viewportH - 135, "fps:" );
        //  GLApp.drawRect(1,1,50,50);


        ShaderUtils.finalDraw2DColor();

        ShaderUtils.finalDraw2DImage();
    }

    @Override
    public boolean isHibernationAllowed() {
        return false;
    }
}
