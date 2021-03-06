package cola.machine.game.myblocks.input.device.nulldevices;

import cola.machine.game.myblocks.input.InputAction;
import cola.machine.game.myblocks.input.device.MouseDevice;
import cola.machine.game.myblocks.math.Vector2i;
import com.google.common.collect.Queues;

import java.util.Queue;

public class NullMouseDevice implements MouseDevice{

	@Override
	public Queue<InputAction> getInputQueue() {
		
		return Queues.newArrayDeque();
	}

	@Override
	public Vector2i getPosition() {
	
		return new Vector2i();
	}

	@Override
	public Vector2i getDelta() {
		
		return null;
	}

	@Override
	public boolean isButtonDown(int button) {
		
		return false;
	}

	@Override
	public boolean isVisible() {
		
		return false;
	}

}
