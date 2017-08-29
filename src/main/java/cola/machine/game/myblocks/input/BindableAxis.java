package cola.machine.game.myblocks.input;

public interface BindableAxis {
	
	String getId();
	
	void setSendEventMode(SendEventMode mode);
	
	SendEventMode getSendEventMode();
	
	void subscribe(BindAxisSubscriber subscriber);
	
	void unsubsribe(BindAxisSubscriber subscriber);
	
	float getValue();
}
