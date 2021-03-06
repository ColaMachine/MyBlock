package cola.machine.game.myblocks.rendering.world;

import cola.machine.game.myblocks.rendering.cameras.Camera;
import com.dozenx.game.opengl.util.ShaderConfig;


public interface WorldRenderer {
	
	   Camera getActiveCamera();

	void render();
    void render(ShaderConfig config );
	
}
