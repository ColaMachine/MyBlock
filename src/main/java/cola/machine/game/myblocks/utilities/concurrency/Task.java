package cola.machine.game.myblocks.utilities.concurrency;

public interface Task {
	
	String getName();
	
	void run();
	
	boolean isTerminateSignal();
	
}
