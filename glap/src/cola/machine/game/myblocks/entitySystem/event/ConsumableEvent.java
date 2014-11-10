package cola.machine.game.myblocks.entitySystem.event;

import cola.machine.game.myblocks.engine.entitySystem.event.Event;

public interface ConsumableEvent extends Event{
	
	boolean isConsumed();
	
	void consume();
}
