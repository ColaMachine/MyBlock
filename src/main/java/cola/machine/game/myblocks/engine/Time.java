package cola.machine.game.myblocks.engine;

public interface Time {
	
	//秒差
	float getDelta();
	
	//和上一帧的毫秒差
	long getDeltaInMs();
	
	//fps
	float getFps();
	
	//秒
	float getGameTime();
	
	//毫秒
	long getRealTimeInMs();

	long getGameTimeInMs();
}
