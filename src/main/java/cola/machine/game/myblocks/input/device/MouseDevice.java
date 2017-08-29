package cola.machine.game.myblocks.input.device;

import cola.machine.game.myblocks.math.Vector2i;

public interface MouseDevice extends InputDevice {
	
	Vector2i getPosition();
	
	Vector2i getDelta();
	
	boolean isButtonDown(int button);
	
	boolean isVisible();
}
