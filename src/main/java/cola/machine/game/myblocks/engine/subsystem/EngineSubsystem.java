package cola.machine.game.myblocks.engine.subsystem;

import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.engine.modes.GameState;

public interface EngineSubsystem {
	void preInitialise();
	
	void postInitialise(Config config);
	
	void preUpdate(GameState currentState,float delta);
	
	void postUpdate(GameState currentState,float delta);
	
	void shutdown(Config config);
	
	void dispose();
	
	//void registerSystems(ComponentSystemManager componentSystemManager);
}
