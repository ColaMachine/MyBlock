package cola.machine.game.myblocks.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.game.myblocks.model.ui.NuiManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;

public class StateInGame {
	  private static final Logger logger = LoggerFactory.getLogger(StateInGame.class);
	   private NuiManager nuiManager;
	    private WorldRenderer worldRenderer;
	    
	    public void init(){
	    	   nuiManager = CoreRegistry.get(NuiManager.class);
	           worldRenderer = CoreRegistry.get(WorldRenderer.class);
	    }
	   
	    @Override
	    public void update(float delta) {
	        eventSystem.process();

	        for (UpdateSubscriberSystem system : componentSystemManager.iterateUpdateSubscribers()) {
	            PerformanceMonitor.startActivity(system.getClass().getSimpleName());
	            system.update(delta);
	            PerformanceMonitor.endActivity();
	        }

	        if (worldRenderer != null && shouldUpdateWorld()) {
	            worldRenderer.update(delta);
	        }

	        updateUserInterface(delta);
	    }
	    
	    @Override
	    public void handleInput(float delta) {
	        cameraTargetSystem.update(delta);
	        inputSystem.update(delta);
	    }

	    public void render() {
	        DisplayDevice displayDevice = CoreRegistry.get(DisplayDevice.class);
	        displayDevice.prepareToRender();

	      //game pic
            worldRenderer.render();
	       

	        /* UI */
	        renderUserInterface();
	    }
}
