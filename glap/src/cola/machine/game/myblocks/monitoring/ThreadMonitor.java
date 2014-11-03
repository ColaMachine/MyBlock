package cola.machine.game.myblocks.monitoring;

import java.util.List;
import java.util.Map;



import cola.machine.game.myblocks.monitoring.impl.ThreadActivityInternal;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;

public final class ThreadMonitor {
	/**事件总线**/
    private static final EventBus EVENT_BUS = new EventBus("ThreadMonitor");
    /**一个线程对应一个线程监控器**/
    private static final Map<Thread, SingleThreadMonitor> THREAD_INFO_BY_ID = Maps.newConcurrentMap();
    
    private ThreadMonitor() {
    }
    /**
     * 线程开始 
     * @param activityName
     * @return
     */
    public static ThreadActivity startThreadActivity(String activityName) {
        SingleThreadMonitor monitor = getMonitor();//得到当前线程监控
        monitor.beginTask(activityName);//开始任务
        return new ThreadActivityInternal(monitor);//包裹监控的线程活动

    }
    /** 得到线程监控集合 将指定活动状态的线程返回给 **/
    public static synchronized List<SingleThreadMonitor> getThreadMonitors(List<SingleThreadMonitor> output, boolean aliveThreadsOnly) {
        Preconditions.checkNotNull(output, "The parameter 'output' must not be null");
        output.clear();
        for (SingleThreadMonitor entry : THREAD_INFO_BY_ID.values()) {
            if (!aliveThreadsOnly || entry.isAlive()) {
                output.add(entry);
            }
        }
        return output;
    }
    /**得到只有存活的线程 aliveThreadsOnly为true的话 过滤掉alive为FALSE的线程**/
    public static synchronized List<SingleThreadMonitor> getThreadMonitors(boolean aliveThreadsOnly) {
        return getThreadMonitors(Lists.<SingleThreadMonitor>newArrayList(), aliveThreadsOnly);
    }
    /** 检车是否参数为空如果不为空的话 事件总线注册该参数**/
    public static void registerForEvents(Object object) {
        Preconditions.checkNotNull(object, "The parameter 'object' must not be null");
        EVENT_BUS.register(object);
    }
    /**当前线程监控器增加错误**/
    public static void addError(Throwable e) {
        SingleThreadMonitor monitor = getMonitor();
        monitor.addError(e);
    }
    /**得到当前线程的监控器**/
    private static SingleThreadMonitor getMonitor() {//单线程
        SingleThreadMonitor monitor = THREAD_INFO_BY_ID.get(Thread.currentThread());//得到当前线程的监控
        if (monitor == null) {//如果是空的
            monitor = new SingleThreadMonitorImpl(Thread.currentThread());//新建一个
            THREAD_INFO_BY_ID.put(Thread.currentThread(), monitor);//塞入THREAD_INFO_BY_IDmap
            EVENT_BUS.post(new ThreadMonitorEvent(monitor, ThreadMonitorEvent.Type.MonitorAdded));//事件总线post 当前监控 触发monitoradded事件
        }
        return monitor;
    }
}
