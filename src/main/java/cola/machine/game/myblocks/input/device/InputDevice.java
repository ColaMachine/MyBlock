package cola.machine.game.myblocks.input.device;

import cola.machine.game.myblocks.input.InputAction;

import java.util.Queue;

public interface InputDevice {
	Queue<InputAction> getInputQueue();
}
