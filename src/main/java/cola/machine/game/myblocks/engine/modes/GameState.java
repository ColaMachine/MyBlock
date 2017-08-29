package cola.machine.game.myblocks.engine.modes;

import cola.machine.game.myblocks.engine.GameEngine;

public interface GameState {
	void init(GameEngine engine);
	
	void dispose();
	
	void handleInput(float delta);
	
	void update(float delta);
	
	void render();
	
	boolean isHibernationAllowed();

	//void cameraPosChangeListener();

}
