package cola.machine.game.myblocks.engine.subsystem.lwjgl;

import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.engine.modes.GameState;
import cola.machine.game.myblocks.engine.subsystem.EngineSubsystem;
import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;

import com.google.common.base.Charsets;

import org.lwjgl.LWJGLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by luying on 14-10-5.
 */
public abstract class BaseLwjglSubsystem implements EngineSubsystem {
    private static final Logger logger = LoggerFactory.getLogger(BaseLwjglSubsystem.class);
    private static boolean initialised;


    @Override
    public synchronized void preInitialise() {
        if (!initialised) {
            initLogger();
            LWJGLHelper.initNativeLibs();
            initialised = true;
        }
    }

    private void initLogger(){
        if(LWJGLUtil.DEBUG){
            try {
                System.setOut(new PrintStream(System.out, false, Charsets.UTF_8.name()) {
                    private  Logger logger = LoggerFactory.getLogger("org.lwjgl");


                    public void print(final String msg){
                        logger.info(msg);
                    }
                });

                System.setErr(new PrintStream(System.err, false, Charsets.UTF_8.name()) {
                    private Logger logger = LoggerFactory.getLogger("org.lwjgl");

                    @Override
                    public void print(final String message) {
                        logger.error(message);
                    }
                });
            }catch(UnsupportedEncodingException e){
                logger.error("Failed to map lwjgl logging", e);
            }
        }
    }



}
