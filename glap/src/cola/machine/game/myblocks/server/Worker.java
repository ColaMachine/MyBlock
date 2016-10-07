package cola.machine.game.myblocks.server;

import cola.machine.game.myblocks.log.LogUtil;

import java.io.*;
import java.net.Socket;
import java.util.Stack;

/**
 * Created by luying on 16/10/7.
 */
public class Worker extends Thread {
    private Socket socket;
    public boolean end=false;
    Stack<String> messages;
    public Worker(Socket socket,Stack<String> messages){
        this.socket =socket;
        this.messages=messages;
    }
    BufferedReader br = null;
    PrintWriter pw = null;
    public void send(String message){
        if(pw!=null)
        pw.println(message);
    }
    public void run(){

        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //用于发送返回信息,可以不需要装饰这么多io流使用缓冲流时发送数据要注意调用.flush()方法
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            while (true) {
                String str = br.readLine();
                if(str==null){
                    LogUtil.println("isOutputShutdown"+socket.isOutputShutdown());
                    LogUtil.println("isClosed"+socket.isClosed());
                    LogUtil.println("isConnected"+socket.isConnected());
                    LogUtil.println("isInputShutdown"+socket.isInputShutdown());
                    System.out.println("socket null so end");
                    this.end=true;
                    break;


                }
                if("END".equals(str)){
                    break;
                }

                messages.push(str);
                System.out.println("Client Socket Message:"+str);
                Thread.sleep(1000);
                //pw.println("Message Received");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            System.out.println("Close.....");
            try {
                br.close();
                pw.close();
                socket.close();

            } catch (Exception e2) {

            }
        }
    }
}
