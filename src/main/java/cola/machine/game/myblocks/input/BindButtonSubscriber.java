package cola.machine.game.myblocks.input;

import cola.machine.game.myblocks.entitySystem.entity.EntityRef;

public interface BindButtonSubscriber {
	
	boolean onPress(float delta,EntityRef target);
	boolean onRepeat(float delta ,EntityRef target);
	boolean onRelease(float delta,EntityRef target);
}
