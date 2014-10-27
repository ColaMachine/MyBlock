package cola.machine.game.myblocks.engine;

import java.util.Iterator;

public interface EngineTime extends Time {
	Iterator<Float> tick();
	
	void setGameTime(long time);
	
	long getRawTimeInMis();
	
	void updateTimeFromServer(long targetTime);
	
	void setPause(boolean pause);
	
	boolean isPaused();
	
	  /**
     * @return Game time in milliseconds. This is synched with the server.
     */
    long getGameTimeInMs();

}
