package cola.machine.game.myblocks.server;

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
                        val.send(message);
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
