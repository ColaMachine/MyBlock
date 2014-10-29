package cola.machine.game.myblocks.config;

import cola.machine.game.myblocks.rendering.world.ViewDistance;
import org.lwjgl.opengl.PixelFormat;


public class RenderingConfig {

    private PixelFormat pixelFormat = new PixelFormat().withDepthBits(24);

    private int windowPoxX = -1;
    private int windowPosY=-1;
    private int windowWidth=1152;
    private int windowHeight=720;

    private boolean fullscreen ;

    private ViewDistance viewDistance = ViewDistance.MODERATE;

    private boolean fLickeringLight=true;

    private boolean animateGrass=true;
    private boolean animateWater;
    private float fieldOfView =90;
    private boolean cameraBobbing=true;

    private boolean renderPlacingBox = true;


    private int blurIntensity =2;
    private boolean reflectiveWater;


    private boolean vignette =true;
    private boolean motionBlur = true;



    private boolean ssao;
    private boolean filmGrain=true;
    private boolean outline=true;
    private boolean lightShafts =true;
    private boolean eyeAdaptation =true;

    private boolean bloom =true;

    private boolean dynamicShadows = true;
    private boolean oculusVrSupport;
    private int maxTextureAtlasResolution=4096;
    private int maxChunksUsedForShadowMapping =1024;
    private int shadowMapResolution =1024;
    private boolean normalMapping ;
    private boolean parallaxMapping;
    private boolean dynamicShadowsPcfFiltering;
    private boolean renderNearest =true;
    private int particleEffectLimit=10;
    private int meshLimit=400;
    private boolean inscattering =true;
    private boolean localReflections;
    private boolean vSync;
    private PerspectiveCameraSettings cameraSettings = new PerspectiveCameraSettings(CameraSetting.NORMAL);

	  private ViewDistance viewDistance = ViewDistance.MODERATE;
	  
	  public ViewDistance getViewDistance() {
	        return viewDistance;
	    }
}
