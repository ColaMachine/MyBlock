package cola.machine.game.myblocks.input.events;

import cola.machine.game.myblocks.input.ButtonState;

public abstract class ButtonEvent extends InputEvent{
	public ButtonEvent(float delta){
		super(delta);
	}
	public abstract ButtonState getState();
	public boolean isDown(){
		return getState()!=ButtonState.UP;
	}
}
