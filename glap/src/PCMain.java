import org.terasology.engine.paths.PathManager;

import cola.machine.game.myblocks.engine.MyBlockEngine;

public class PCMain {
	public static void main(String args[]) {
		PathManager.getInstance().useDefaultHomePath();

		MyBlockEngine engine = new MyBlockEngine();

		engine.initSelf();

		engine.run();
	}
}
