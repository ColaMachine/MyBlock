package cola.machine.game.myblocks.server;

import cola.machine.game.myblocks.log.LogUtil;

import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

/**
 * Created by luying on 16/10/7.
 */
public class AllSender extends Thread{
    Stack<String> messages;
    HashMap<Integer, Worker> workerMap;
    public AllSender(Stack<String> messages,HashMap<Integer,Worker> workerMap){
        this.messages =messages;
        this.workerMap=workerMap;
    }
    public void run(){
        while(true) {
            if (messages.size() > 0) {
                String message = messages.peek();
                if (message != null) {
                    messages.pop();
                    Iterator iter = workerMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();

                        Worker val = (Worker) entry.getValue();
                        if(val.end || !val.isAlive()){
                            LogUtil.println("删除socket成功");
                            workerMap.remove(entry.getKey());
                        }else {
                            val.send(message);
                        }
                    }
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
    }

}
