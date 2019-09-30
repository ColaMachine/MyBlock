package gldemo;

/**
 * Created by colamachine on 19-9-3.
 */


import java.awt.Font;
import java.io.InputStream;

import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class GLApp_Font {

    /** The fonts to draw to the screen */
    private TrueTypeFont font;
    private TrueTypeFont font2;

    /** Boolean flag on whether AntiAliasing is enabled or not */
    private boolean antiAlias = true;

    /**
     * Start the test
     */
    public void start() {
        initGL(800,600);
        init();

        while (true) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            render();

            Display.update();
            Display.sync(100);

            if (Display.isCloseRequested()) {
                Display.destroy();
                System.exit(0);
            }
        }
    }

    /**
     * Initialise the GL display
     *
     * @param width The width of the display
     * @param height The height of the display
     */
    private void initGL(int width, int height) {
        try {
            Display.setDisplayMode(new DisplayMode(width,height));
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glViewport(0,0,width,height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    /**
     * Initialise resources
     */
    public void init() {
        // load a default java font
        Font awtFont = new Font("Times New Roman", Font.PLAIN, 24);
        font = new TrueTypeFont(awtFont, antiAlias,new char[]{'我','们','是'});

        // load font from file
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("myfont.ttf");

            Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);

            awtFont2 = awtFont2.deriveFont(24f); // set font size
            font2 = new TrueTypeFont(awtFont2, antiAlias);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Game loop render
     */
    public void render() {
        Color.white.bind();

        font.drawString(100, 50, "我们是THE LIGHTWEIGHT JAVA GAMES LIBRARY", Color.yellow);
        font2.drawString(100, 100, "THE LIGHTWEIGHT JAVA GAMES LIBRARY", Color.green);
    }

    /**
     * Main method
     */
    public static void main(String[] argv) {
        LWJGLHelper.initNativeLibs();

        GLApp_Font fontExample = new GLApp_Font();
        fontExample.start();
    }
}