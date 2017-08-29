package time;

import cola.machine.game.myblocks.engine.EngineTime;
import org.lwjgl.Sys;

import java.util.Iterator;

public class Time implements EngineTime {
    boolean stop =false;
    long startTIme = getRawTimeInMs();
	long lastTime=getRawTimeInMs();
    long nowTime=getRawTimeInMs();
	int frames=0;
	public float fps = 0;
	public Iterator<Float> tick(){
		frames++;
        nowTime=getRawTimeInMs();
		if(nowTime-lastTime>1000){
			fps=frames*1000/(nowTime-lastTime);
			
			lastTime=nowTime;
			frames=0;
		}
		
		return null;
		
	}

    @Override
    public void setGameTime(long time) {

    }

    @Override
    public long getRawTimeInMis() {
        return this.getRawTimeInMs();
    }

    @Override
    public void updateTimeFromServer(long targetTime) {

    }

    @Override
    public void setPause(boolean pause) {
        stop=true;
    }

    @Override
    public boolean isPaused() {
        return stop;
    }

    @Override
    public float getDelta() {
        return Sys.getTime()-lastTime;
    }

    @Override
    public long getDeltaInMs() {
        return getRawTimeInMs()-lastTime;
    }

    @Override
    public float getFps() {
        return fps;
    }

    @Override
    public float getGameTime() {
        return 0;
    }

    @Override
    public long getRealTimeInMs() {
        return getRawTimeInMs();
    }

    @Override
    public long getGameTimeInMs() {
        return getRawTimeInMs()-startTIme;
    }

    public long getRawTimeInMs() {
	        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	    }
}
