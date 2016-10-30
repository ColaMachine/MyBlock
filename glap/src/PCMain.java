//import org.terasology.engine.paths.PathManager;

import cola.machine.game.myblocks.engine.MyBlockEngine;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.engine.subsystem.EngineSubsystem;
import cola.machine.game.myblocks.engine.subsystem.lwjgl.LwjglGraphics;
import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;
import com.google.common.collect.Lists;

import java.util.Collection;

public class PCMain {
	public static void main(String args[]) {



        try {

            PathManager.getInstance().useDefaultHomePath();


            LWJGLHelper.initNativeLibs();
            //System.out.println(System.getProperty("java.library.path"));
            Collection<EngineSubsystem> subsystemList;

            subsystemList = Lists.<EngineSubsystem>newArrayList(new LwjglGraphics());

            // create the app
            MyBlockEngine demo = new MyBlockEngine();
            // System.out.println(System.getProperty("java.library.path"));
            demo.VSyncEnabled = true;
            demo.fullScreen = false;
            demo.displayWidth = 800;
            demo.displayHeight = 600;

            demo.run(); // will call init(), render(), mouse functions
        }catch(Exception e){
            e.printStackTrace();
        }
	}
}
