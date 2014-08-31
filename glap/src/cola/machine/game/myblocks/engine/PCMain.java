package cola.machine.game.myblocks.engine;

import org.terasology.engine.paths.PathManager;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by luying on 14-8-31.
 */
public class PCMain {
    public static void main(String args[]){
        try {
            PathManager.getInstance().useDefaultHomePath();

          //  Collection<EngineSubsystem> subsystemList;


            MyBlockEngine engine =new MyBlockEngine();

            try {
                engine.initSelf();
                    engine.run();
            } finally {
                try {
                   // engine.dispose();
                } catch (Exception e) {
                    // Just log this one to System.err because we don't want it
                    // to replace the one that came first (thrown above).
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
