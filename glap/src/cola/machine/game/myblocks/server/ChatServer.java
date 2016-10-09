package cola.machine.game.myblocks.server;

import cola.machine.game.myblocks.engine.Constants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by luying on 16/10/7.
 */
public class ChatServer {

    public static void main(String args[]){
        ChatServer server =new ChatServer();
        server.start();
    }
    //public HashMap<Integer , Socket> socketMap =new HashMap();
    public HashMap<Integer,Worker> workerMap =new HashMap();
    public Stack<String> messages=new Stack<>();
    public Stack<String> livingThings=new Stack<>();
    public  void start(){
        ServerSocket s = null;
        Thread allSender =new AllSender(messages,workerMap);allSender.start();
        try {
            //设定服务端的端口号
            s = new ServerSocket(Constants.serverPort);
            System.out.println("ServerSocket Start:"+s);
            //等待请求,此方法会一直阻塞,直到获得请求才往下走

            while(true){
                Socket socket = s.accept();
                Worker worker =new Worker(socket,messages);
                worker.start();
                workerMap.put(worker.hashCode(),worker);
            }



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            System.out.println("Close.....");
            try {

                s.close();
            } catch (Exception e2) {

            }
        }
    }
}
