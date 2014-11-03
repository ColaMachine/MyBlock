package cola.machine.game.myblocks.engine;

import cola.machine.game.myblocks.engine.modes.GameState;

public interface GameEngine {
	void init();
	
	void run(GameState initialState);
	
	void shutdown();
	
	void dispose();
	
	boolean isRunning();
	
	boolean isDisposed();
	
	GameState getState();
	
	void changeState(GameState newState);
	
	void submitTask(String name,Runnable task);
	
	boolean isHibernationAllowed();
	
	void setHibernationAlllowed(boolean allowed);
	
	boolean hasFocus();
	
	boolean hasMouseFocus();
	
	void setFocus(boolean focused);
	
	void subscribeToStateChange(StateChangeSubscriber subscriber);
	
	void unsubscribeToStateChange(StateChangeSubscriber subscriber);
	
}
