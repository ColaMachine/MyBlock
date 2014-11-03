package cola.machine.game.myblocks.monitoring;

import java.util.List;

public interface SingleThreadMonitor  extends Comparable<SingleThreadMonitor>{

    boolean isAlive();//是否还存活

    boolean isActive();//是否活跃

    String getName();//取得名字

    long getThreadId();//得到线程id

    boolean hasErrors();//是否有错误

    int getNumErrors();//得到错误数

    Throwable getLastError();//得到最后条错误

    List<Throwable> getErrors();//得到错误list

    void addError(Throwable error);//增加错误

    Iterable<String> getTasks();//得到tasks

    long getCounter(String task);//得到计数

    void beginTask(String task);//开始任务

    void endTask();//结束任务

    String getLastTask();//得到最近的任务
}
