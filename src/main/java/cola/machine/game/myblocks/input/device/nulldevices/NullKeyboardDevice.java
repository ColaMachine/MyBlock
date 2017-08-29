package cola.machine.game.myblocks.input.device.nulldevices;

import cola.machine.game.myblocks.input.InputAction;
import cola.machine.game.myblocks.input.device.KeyboardDevice;
import com.google.common.collect.Queues;

import java.util.Queue;

public class NullKeyboardDevice implements KeyboardDevice{

	@Override
	public Queue<InputAction> getInputQueue() {
		return Queues.newArrayDeque();
	}

	@Override
	public boolean isKeyDown(int key) {
		return false;
	}

}
