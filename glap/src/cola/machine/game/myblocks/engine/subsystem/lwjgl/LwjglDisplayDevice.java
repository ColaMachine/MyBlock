package cola.machine.game.myblocks.engine.subsystem.lwjgl;

import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.engine.subsystem.DisplayDevice;
import cola.machine.game.myblocks.registry.CoreRegistry;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by luying on 14/10/27.
 */
public class LwjglDisplayDevice implements DisplayDevice {
    private static final Logger logger = LoggerFactory.getLogger(LwjglDisplayDevice.class);

    public LwjglDisplayDevice(){

    }

    @Override
    public boolean isActive() {
        return Display.isActive();
    }

    @Override
    public boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    @Override
    public void setFullscreen(boolean state) {
        setFullscreen(state,true);
    }
    void setFullscreen(boolean state,boolean resize){
        try{
            if(state){
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setFullscreen(true);
            }else{
                Config config = CoreRegistry.get(Config.class);
                Display.setDisplayMode(config.getRendering().getDisplayMode());
            }

        }catch(){

        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processMessages() {

    }

    @Override
    public boolean isHeadless() {
        return false;
    }

    @Override
    public void prepareToRender() {

    }
}
