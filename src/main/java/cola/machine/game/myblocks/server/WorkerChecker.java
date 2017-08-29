package cola.machine.game.myblocks.server;

import com.dozenx.game.network.server.Worker;
import core.log.LogUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by luying on 16/10/14.
 */
public class WorkerChecker extends Thread {
    HashMap<Integer,Worker> workerMap;
    public WorkerChecker(HashMap<Integer,Worker> workerMap){
        this.workerMap=workerMap;
    }
    public void run(){
        try{
            Iterator it = workerMap.keySet().iterator();
            while (it.hasNext()){
                Map.Entry entry = (Map.Entry) it.next();
                Worker worker = (Worker)entry.getValue();

                //检查socket的安全度
               // worker
            }
        }catch (Exception e){
            LogUtil.println("remove  died worker wrong");
            e.printStackTrace();
        }

    }
}
