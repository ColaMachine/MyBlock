package cola.machine.game.myblocks.engine.subsystem.lwjgl;

import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.engine.EngineTime;
import cola.machine.game.myblocks.engine.Time;
import cola.machine.game.myblocks.engine.internal.TimeLwjgl;
import cola.machine.game.myblocks.engine.modes.GameState;
import cola.machine.game.myblocks.registry.CoreRegistry;

public class LwjglTimer extends BaseLwjglSubsystem {

	@Override
	public void postInitialise(Config config) {
		super.preInitialise();
		initTimer();
	}
	
	@Override
	public void preUpdate(GameState currentState, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postUpdate(GameState currentState, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown(Config config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private void initTimer(){
		EngineTime time =new TimeLwjgl();
		CoreRegistry.putPermanently(Time.class, time);
	}
}
