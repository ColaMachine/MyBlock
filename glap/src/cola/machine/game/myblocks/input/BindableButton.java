package cola.machine.game.myblocks.input;

import cola.machine.game.myblocks.engine.SimpleUri;

public interface BindableButton {
	SimpleUri gtId() ;
	String getDisplayName();
	void setMode(ActivateMode mode);
	ActivateMode getMode();
	
	void setRepeating(boolean repeating);
	boolean isRepeating();
	ButtonState getState();
	void subscribe(BindButtonSubscriber subscriber);
}
