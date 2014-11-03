package cola.machine.game.myblocks.utilities.concurrency;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.game.myblocks.monitoring.ThreadActivity;

public class TaskProcessor <T extends Task> implements Runnable{
	
	private static final Logger logger =LoggerFactory.getLogger(TaskProcessor.class);
	private String name;
	private BlockingQueue<T> queue;
	
	
	public TaskProcessor(String name ,BlockingQueue<T> taskQueue){
		this.queue=taskQueue;
		this.name =name;
	}
	
	public void run(){
		boolean running =true;
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		Thread.currentThread().setName(name);
		while(running){
			try{
				T task=queue.take();
				try(ThreadActivity ignored=ThreadMonitor.startThreadActivity(task.getName())){
					task.run();
				}
				if(task.isTerminateSignal()){
					running=false;
				}
			}catch(InterruptedException e){
				ThreadMonitor.addError(e);
				logger.error("Thread interrupted",e);
			}catch(Throwable e){
				ThreadMonitor.addError(e);
				logger.error("Error in thread{}",Thread.currentThread().getName(),e);
			}
			logger.debug("Thread shutdown safely");
		}
	}
}
