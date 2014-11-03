package cola.machine.game.myblocks.monitoring.impl;

import cola.machine.game.myblocks.monitoring.SingleThreadMonitor;
import cola.machine.game.myblocks.monitoring.ThreadActivity;


public class ThreadActivityInternal implements ThreadActivity{

    private SingleThreadMonitor monitor;//单线程监控器

    public ThreadActivityInternal(SingleThreadMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void close() {
        monitor.endTask();//监控停止任务
    }
}
