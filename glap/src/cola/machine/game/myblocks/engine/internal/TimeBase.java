package cola.machine.game.myblocks.engine.internal;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.game.myblocks.engine.EngineTime;

public abstract class TimeBase implements EngineTime{
	private static final Logger logger =LoggerFactory.getLogger(TimeBase.class);
	
	private static float DECAY_RATE=0.95F;
	
	private static final float ONE_MINUS_DECAY_RATE=1.0f-DECAY_RATE;
	
	private static final float RESYNC_TIME_RATE =0.1f;
	
	private static final int MAX_UPDATE_CYCLE_LENGTH=1000;
	
	private static final int UPDATE_CAP=1000;
	
	private AtomicLong last =new AtomicLong(0);
	
	private AtomicLong delta =new AtomicLong(0);
	
	private float avgDelta;
	
	private long desynch;
	
	private boolean paused;
	
	private AtomicLong gameTime =new AtomicLong(0);
	
	public abstract long getRawTimeInMs();
	
	public Iterator<Float > tick(){
		long now =getRawTimeInMs();//当前时间
		long newDelta=now-last.get();//本次时间减去上次时间  =  间隔时间
		if(0== newDelta){
			try{
				Thread.sleep(0,1000);//停止1秒 原因过快
			}catch(InterruptedException e){
				
			}
			now =getRawTimeInMs();
			newDelta =now -last.get();
		}
		if(newDelta>UPDATE_CAP){//如果大于1秒
			logger.warn("Delta too great({}, capping to {})",newDelta,UPDATE_CAP);
			newDelta =UPDATE_CAP;
		}
		int updateCycles =(int)((newDelta-1)/MAX_UPDATE_CYCLE_LENGTH)+1;
		last.set(now);
		avgDelta =avgDelta * DECAY_RATE+newDelta * ONE_MINUS_DECAY_RATE;//追上的速度
		//avgDelta 表示的是两真之间的间隔时间 如果一次的时间特别长 就会增加avgDelta的值
		if(desynch !=0){
			long diff=(long)Math.ceil(desynch * RESYNC_TIME_RATE);
			if(diff==0){
				diff =(long)Math.signum(desynch);
			}
			gameTime.getAndAdd(diff);
			desynch -=diff;
		}
		
		if(paused){
			delta.set(0);
			return new TimeStepper(1,0);
			
		}else {
			if(updateCycles>0){
				delta.set(newDelta/updateCycles);
			}
			//LogUtil.println("fps:"+this.getFps());
			return new TimeStepper(updateCycles,newDelta/updateCycles);
		}
	}
	
;	@Override
	public float getDelta() {
		
		return delta.get()/1000;
	}

	@Override
	public long getDeltaInMs() {
		
		return delta.get();
	}

	@Override
	public float getFps() {
		return 1000.0f/avgDelta;
	}

	@Override
	public float getGameTime() {
		return gameTime.get()/1000f;
	}

	@Override
	public long getRealTimeInMs() {
		return gameTime.get();
	}



	@Override
	public void setGameTime(long time) {
		delta.set(0);
		gameTime.set(time);
		
	}

	@Override
	public long getRawTimeInMis() {
		return getRawTimeInMs();
	}

	@Override
	public void updateTimeFromServer(long targetTime) {
		desynch = targetTime - getGameTimeInMs();
		
	}
	public long getGameTimeInMs(){
		return gameTime.get();
	}
	@Override
	public void setPause(boolean pause) {
		 this.paused = paused;
		
	}

	@Override
	public boolean isPaused() {
		return paused;
	}
	
	private class TimeStepper implements Iterator<Float>{
		private int cycles;
		private long deltaPerCycle;
		private int currentCycle;
		
		public TimeStepper(int cycles,long deltaPerCycle){
			this.cycles=cycles;
			this.deltaPerCycle=deltaPerCycle;
		}

		@Override
		public boolean hasNext() {
			
			return currentCycle<cycles;
		}

		@Override
		public Float next() {
			currentCycle++;//初始值为0 累加到16 如果fps要求是60的话
			gameTime.addAndGet(deltaPerCycle);
			return deltaPerCycle/1000f;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	 
	}

}
