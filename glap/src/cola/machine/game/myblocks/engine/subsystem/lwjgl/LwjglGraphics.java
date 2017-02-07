package cola.machine.game.myblocks.engine.subsystem.lwjgl;

import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.config.RenderingConfig;
import cola.machine.game.myblocks.engine.ComponentSystemManager;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GameState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.engine.subsystem.DisplayDevice;
import cola.machine.game.myblocks.engine.subsystem.RenderingSubsystemFactory;
import cola.machine.game.myblocks.registry.CoreRegistry;

import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.ui.login.LoginDemo;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by luying on 14/10/27.
 */
public class LwjglGraphics extends BaseLwjglSubsystem{

    private static final Logger logger = LoggerFactory.getLogger(LwjglGraphics.class);//日志

    private GLBufferPool bufferPool = new GLBufferPool(false);//一个有关vbo的buffer

    @Override
    public void preInitialise() {
        super.preInitialise();
    }//初始化日志和类路径 还有高级类路径

    @Override
    public void postInitialise(Config config) {
        CoreRegistry.putPermanently(RenderingSubsystemFactory.class, new LwjglRenderingSubsystemFactory(bufferPool));//用来生成worldrenderlwjgl的工厂

        LwjglDisplayDevice lwjglDisplay = new LwjglDisplayDevice();//显示器设备
        CoreRegistry.putPermanently(DisplayDevice.class, lwjglDisplay);

        initDisplay(config, lwjglDisplay);//根据配置初始显示设备
        initOpenGL();//初始化opengl

       // CoreRegistry.putPermanently(NUIManager.class, new NUIManagerInternal(new LwjglCanvasRenderer()));//初始化界面系统
    }

    @Override
    public void preUpdate(GameState currentState, float delta) {
    }

    @Override
    public void postUpdate(GameState currentState, float delta) {
        Display.update();
        Display.sync(Constants.FPS);//定义了每秒的帧率
        currentState.render();

        if (Display.wasResized()) {
            glViewport(0, 0, Display.getWidth(), Display.getHeight());
        }
    }

    @Override
    public void shutdown(Config config) {
        if (!Display.isFullscreen() && Display.isVisible()) {
            //config.getRendering().setWindowPosX(Display.getX());
           // config.getRendering().setWindowPosY(Display.getY());
        }
    }

    @Override
    public void dispose() {
        Display.destroy();
    }

    private void initDisplay(Config config, LwjglDisplayDevice lwjglDisplay) {
        try {
            lwjglDisplay.setFullscreen(false, false);// 不能调整大小

            RenderingConfig rc = config.getRendering();
            Display.setLocation(rc.getWindowPosX(),rc.getWindowPosY());
            Display.setTitle("myblock" + " | " + "alpha");
            try {

                String root = "icons/";
                ClassLoader classLoader = getClass().getClassLoader();
//logger.debug("current class loader:"+classLoader.toString());
                //Path path = PathManager.getInstance().getHomePath().resolve("icons");
                //Files.createi
                BufferedImage icon16 = ImageIO.read(classLoader.getResourceAsStream(root + "gooey_sweet_16.png"));
                BufferedImage icon32 = ImageIO.read(classLoader.getResourceAsStream(root + "gooey_sweet_32.png"));
                BufferedImage icon64 = ImageIO.read(classLoader.getResourceAsStream(root + "gooey_sweet_64.png"));
                BufferedImage icon128 = ImageIO.read(classLoader.getResourceAsStream(root + "gooey_sweet_128.png"));

               /* Display.setIcon(new ByteBuffer[]{
                        ImageIO.
                        new ImageIOImageData().imageToByteBuffer(icon16, false, false, null),
                        new ImageIOImageData().imageToByteBuffer(icon32, false, false, null),
                        new ImageIOImageData().imageToByteBuffer(icon64, false, false, null),
                        new ImageIOImageData().imageToByteBuffer(icon128, false, false, null)
                });*/
            } catch (IOException | IllegalArgumentException e) {
                logger.warn("Could not set icon", e);
            }

            if(Switcher.SHADER_ENABLE){
                ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                        .withForwardCompatible(true)
                        .withProfileCore(true);
                Display.create( rc.getPixelFormat(),contextAtrributes);
            }else {
                Display.create(rc.getPixelFormat()/*,contextAtrributes*/);
            }
            Display.setVSyncEnabled(rc.isVSync());//确定是否垂直同步
        } catch (LWJGLException e) {
            throw new RuntimeException("Can not initialize graphics device.", e);
        }
    }

    private void initOpenGL() {
        checkOpenGL();
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        initOpenGLParams();
        /*AssetManager assetManager = CoreRegistry.get(AssetManager.class);
        assetManager.setAssetFactory(AssetType.FONT, new AssetFactory<FontData, Font>() {
            @Override
            public Font buildAsset(AssetUri uri, FontData data) {
                return new FontImpl(uri, data);
            }
        });
        assetManager.setAssetFactory(AssetType.TEXTURE, new AssetFactory<TextureData, Texture>() {
            @Override
            public Texture buildAsset(AssetUri uri, TextureData data) {
                return new OpenGLTexture(uri, data);
            }
        });
        assetManager.setAssetFactory(AssetType.SHADER, new AssetFactory<ShaderData, Shader>() {
            @Override
            public Shader buildAsset(AssetUri uri, ShaderData data) {
                return new GLSLShader(uri, data);
            }
        });
        assetManager.setAssetFactory(AssetType.MATERIAL, new AssetFactory<MaterialData, Material>() {
            @Override
            public Material buildAsset(AssetUri uri, MaterialData data) {
                return new GLSLMaterial(uri, data);
            }
        });
        assetManager.setAssetFactory(AssetType.MESH, new AssetFactory<MeshData, Mesh>() {
            @Override
            public Mesh buildAsset(AssetUri uri, MeshData data) {
                return new OpenGLMesh(uri, data, bufferPool);
            }
        });
        assetManager.setAssetFactory(AssetType.SKELETON_MESH, new AssetFactory<SkeletalMeshData, SkeletalMesh>() {
            @Override
            public SkeletalMesh buildAsset(AssetUri uri, SkeletalMeshData data) {
                return new OpenGLSkeletalMesh(uri, data, bufferPool);
            }
        });
        assetManager.setAssetFactory(AssetType.ANIMATION, new AssetFactory<MeshAnimationData, MeshAnimation>() {
            @Override
            public MeshAnimation buildAsset(AssetUri uri, MeshAnimationData data) {
                return new MeshAnimationImpl(uri, data);
            }
        });
        assetManager.setAssetFactory(AssetType.ATLAS, new AssetFactory<AtlasData, Atlas>() {
            @Override
            public Atlas buildAsset(AssetUri uri, AtlasData data) {
                return new Atlas(uri, data);
            }
        });
        assetManager.setAssetFactory(AssetType.SUBTEXTURE, new AssetFactory<SubtextureData, Subtexture>() {
            @Override
            public Subtexture buildAsset(AssetUri uri, SubtextureData data) {
                return new Subtexture(uri, data);
            }
        });
        assetManager.addResolver(AssetType.SUBTEXTURE, new SubtextureFromAtlasResolver());
        assetManager.addResolver(AssetType.TEXTURE, new ColorTextureAssetResolver());
        assetManager.addResolver(AssetType.MESH, new IconMeshResolver());
        CoreRegistry.putPermanently(ShaderManager.class, new ShaderManagerLwjgl());
        CoreRegistry.get(ShaderManager.class).initShaders();*/

    }


    private void checkOpenGL() {//校验是否支持
        boolean canRunGame = GLContext.getCapabilities().OpenGL11
                & GLContext.getCapabilities().OpenGL12
                & GLContext.getCapabilities().OpenGL14
                & GLContext.getCapabilities().OpenGL15
                & GLContext.getCapabilities().GL_ARB_framebuffer_object
                & GLContext.getCapabilities().GL_ARB_texture_float
                & GLContext.getCapabilities().GL_ARB_half_float_pixel
                & GLContext.getCapabilities().GL_ARB_shader_objects;

        if (!canRunGame) {
            String message = "Your GPU driver is not supporting the mandatory versions or extensions of OpenGL. Considered updating your GPU drivers? Exiting...";
            logger.error(message);
            JOptionPane.showMessageDialog(null, message, "Mandatory OpenGL version(s) or extension(s) not supported", JOptionPane.ERROR_MESSAGE);
            //throw new RuntimeException(message);
        }

    }

    public void initOpenGLParams() {
      glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
      //  glEnable(GL_NORMALIZE);
      //  glDepthFunc(GL_LEQUAL);
    }

    public void registerSystems(ComponentSystemManager componentSystemManager) {
    }

}
