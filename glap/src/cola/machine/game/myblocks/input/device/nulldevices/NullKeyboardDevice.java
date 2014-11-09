package cola.machine.game.myblocks.input.device.nulldevices;

import java.util.Queue;

import com.google.common.collect.Queues;

import cola.machine.game.myblocks.input.InputAction;
import cola.machine.game.myblocks.input.device.KeyboardDevice;

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
