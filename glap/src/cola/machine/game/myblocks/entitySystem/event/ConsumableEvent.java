package cola.machine.game.myblocks.entitySystem.event;

import cola.machine.game.myblocks.engine.entitySystem.event.Event2;

public interface ConsumableEvent extends Event2 {
	
	boolean isConsumed();
	
	void consume();
}
