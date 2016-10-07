package cola.machine.game.myblocks.network;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.log.LogUtil;

import java.io.*;
import java.net.Socket;
import java.util.Stack;

/**
 * Created by luying on 16/10/7.
 */
public class Client extends Thread{
    public static Stack<String> messages=new Stack<String>();
    public Client(){

    }
    Socket socket = null;
    BufferedReader br = null;
    PrintWriter pw = null;
    public  void send(String message){
    if(pw!=null){
        System.out.println(" send message"+message);
        pw.println(message);
        pw.flush();
    }
    }
    public static void main(String args[]){
        //System.out.println(message);
        Client client =new Client();
       client.start();
        BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));

        String line= null;
        try {
            line = sin.readLine();



        while(!line.equals("bye")){
        client.send(line);

            line=sin.readLine();



        }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void run(){

        try {
            //客户端socket指定服务器的地址和端口号
            socket = new Socket("127.0.0.1", Constants.serverPort);
            socket.setTcpNoDelay(true);
            System.out.println("Socket=" + socket);
            //同服务器原理一样
            br = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())));
            //pw.println("1");pw.flush();
           // pw.println("2");pw.flush();
           /*for(int i=0;i<10;i++){
                //send("hello"+i);
                pw.println(i);pw.flush();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            //pw.flush();
            while(true){//不断读取数据 然后压入到messages里 由界面端显示出stack
                String str = br.readLine();
                if(str==null){
                    LogUtil.println("失去连接 正在重新连接");
                    Thread.sleep(1000);
                    continue;
                }
                messages.push(str);
                if(str.equals("END")){
                    break;
                }
                System.out.println("Client Socket Message:"+str);
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("close......");
                br.close();
                pw.close();
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
