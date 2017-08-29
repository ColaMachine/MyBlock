package cola.machine.game.myblocks.input;

import cola.machine.game.myblocks.math.Vector2i;

public final class InputAction {

	private final Input input;
	private final ButtonState state;
	private final char inputChar;
	private final Vector2i mousePosition;
	private final int delta;
	
	public InputAction(Input input,ButtonState state,Vector2i mousePosition){
		this.input = input;
		this.state=state;
		this.mousePosition=mousePosition;
		this.delta=0;
		this.inputChar='\0';
	}

	public InputAction(Input input, ButtonState state, char inputChar) {
		this.input = input;
		this.state=state;
		this.mousePosition=null;
		this.delta=0;
		this.inputChar=inputChar;
	}
	
	//mousewheel
	public InputAction(Input input, int delta, Vector2i position) {
		this.input = input;
		this.state=null;
		this.mousePosition=null;
		this.delta=delta;
		this.inputChar='\0';
	}

	public Input getInput() {
		return input;
	}

	public ButtonState getState() {
		return state;
	}

	public char getInputChar() {
		return inputChar;
	}

	public Vector2i getMousePosition() {
		return mousePosition;
	}

	public int getDelta() {
		return delta;
	}
}
