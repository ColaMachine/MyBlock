package cola.machine.game.myblocks.server;

import cola.machine.game.myblocks.log.LogUtil;

import java.net.Socket;
import java.util.*;

/**
 * Created by luying on 16/10/7.
 */
public class AllSender extends Thread{
    Stack<byte[]> messages;
    Map<Integer, Worker> workerMap;
    public AllSender(Stack<byte[]> messages,Map<Integer,Worker> workerMap){
        this.messages =messages;
        this.workerMap=workerMap;
    }
 Queue<Integer> waitDelList =new LinkedList<>();
    public void run(){
        while(true) {
            if (messages.size() > 0) {
                byte[] bytes  = messages.peek();
                if (bytes != null) {
                    messages.pop();
                    Iterator iter = workerMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry<Integer,Worker> entry = (Map.Entry) iter.next();

                        Worker val = (Worker) entry.getValue();
                        if(val.end || !val.isAlive()){
                            LogUtil.println("删除socket成功");
                            val.interrupt();   val.end=true;
                            waitDelList.offer(entry.getKey());





                        }else {
                            val.send(bytes);
                        }
                    }

                    while(waitDelList.size()>0&& waitDelList.peek()!=null){
                        Integer id = waitDelList.poll();
                        workerMap.remove(id);
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
