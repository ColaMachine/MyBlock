package cola.machine.game.myblocks.rendering.world;

import javax.vecmath.Vector3f;

import cola.machine.game.myblocks.rendering.cameras.Camera;




public interface WorldRenderer {
	
	   Camera getActiveCamera();

	void render();
	
}
