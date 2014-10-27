package cola.machine.game.myblocks.engine.internal;

import org.lwjgl.Sys;

public class TimeLwjgl extends TimeBase{
	public long getRawTimeInMs(){
		return (Sys.getTime()*1000)/Sys.getTimerResolution();
	}
	
	
}
