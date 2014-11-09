package cola.machine.game.myblocks.input.device;

import java.util.Queue;

import cola.machine.game.myblocks.input.InputAction;

public interface InputDevice {
	Queue<InputAction> getInputQueue();
}
