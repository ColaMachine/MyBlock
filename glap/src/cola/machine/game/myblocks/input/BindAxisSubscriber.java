package cola.machine.game.myblocks.input;

import cola.machine.game.myblocks.entitySystem.entity.EntityRef;

public interface BindAxisSubscriber {
	void update(float value,float delta,EntityRef target);
}
