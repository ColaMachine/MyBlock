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
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import glapp.GLApp;
import glapp.GLCamera;
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

import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class StartMenuState implements GameState {

    public static String catchThing;
    //LearnOpenglColor learnOpenglColor;
    GUI gui;

    public void init(GameEngine engine) {

        try {


            LWJGLRenderer renderer = new LWJGLRenderer();//调用lwjgl能力
            renderer.setUseSWMouseCursors(true);

            //ChatDemo chat = new ChatDemo();
            //GameUIDemo gameUI = new GameUIDemo();
            LoginDemo loginDemo = new LoginDemo();
            gui = new GUI(loginDemo, renderer);

                    ThemeManager theme = ThemeManager.createThemeManager(
                    LoginDemo.class.getResource("login.xml"), renderer);
            gui.applyTheme(theme);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // this.setPerspective();

        /*ShaderManager shaderManager =new ShaderManager();
        shaderManager.init();*/
        //learnOpenglColor=new LearnOpenglColor();

    }

    public void dispose() {

    }


    public void handleInput(float delta) {


        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {

                gui.handleKey(
                        Keyboard.getEventKey(),
                        Keyboard.getEventCharacter(),
                        Keyboard.getEventKeyState());


            }
        }
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                gui.handleMouse(
                        Mouse.getEventX(), gui.getHeight() - Mouse.getEventY() - 1,
                        Mouse.getEventButton(), Mouse.getEventButtonState());

            }
        }


    }


    public void update(float delta) {

    }

    public float rotation = 0f;

    @Override
    public void render() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        glUseProgram(0);
        gui.update();
        //print(30, viewportH - 135, "fps:" );
        //  GLApp.drawRect(1,1,50,50);
    }

    @Override
    public boolean isHibernationAllowed() {
        return false;
    }
}
