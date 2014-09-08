package time;

import org.lwjgl.Sys;

public class Time {
	long lastTime=getRawTimeInMs();
	int frames=0;
	public float fps = 0;
	public float tick(){
		frames++;
		long 
		time=getRawTimeInMs();
		if(time-lastTime>1000){
			fps=frames*1000/(time-lastTime);
			
			lastTime=time;
			frames=0;
		}
		
		return fps;
		
	}
	
	 public long getRawTimeInMs() {
	        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	    }
}
