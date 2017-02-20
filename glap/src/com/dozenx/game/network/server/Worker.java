package com.dozenx.game.network.server;

import core.log.LogUtil;
import com.dozenx.game.engine.command.CmdUtil;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.handler.GameServerHandler;

import java.io.*;
import java.net.Socket;
import java.util.Stack;

/**
 * Created by luying on 16/10/7.
 */
public class Worker extends Thread {

    private Socket socket;
    public boolean end=false;
    public String ip;

    public void close(){
        LogUtil.println("失去连接"+this.ip);
        this.end=true;

    }
    Stack<byte[]> messages;
    public Worker(Socket socket,Stack<byte[]> messages){

        this.ip=socket.getInetAddress().getHostAddress();
        LogUtil.println("连接服务器"+this.ip);
        this.socket =socket;
        this.messages=messages;
    }
    InputStream br = null;
    OutputStream pw = null;
    public void send(byte[] bytes){
        if(pw!=null)
            try {
                pw.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        //pw.println(message);
    }
    public void run(){
        byte[] bytes =new byte[100];
        try {
            br =  socket.getInputStream();
            //用于发送返回信息,可以不需要装饰这么多io流使用缓冲流时发送数据要注意调用.flush()方法
            pw = socket.getOutputStream();
            while (true) {
               int n = br.read(bytes);
                if(bytes[n-1]!='\n'){
                    LogUtil.err("socket read end is not \n:"+n);
                }
                if(n==0){
                    LogUtil.println("isOutputShutdown"+socket.isOutputShutdown());
                    LogUtil.println("isClosed"+socket.isClosed());
                    LogUtil.println("isConnected"+socket.isConnected());
                    LogUtil.println("isInputShutdown"+socket.isInputShutdown());
                    System.out.println("socket null so end");
                    this.end=true;
                    break;


                }
               /* if("END".equals(str)){
                    break;
                }*/
               /* while(){

                }*/

                GameCmd cmd = CmdUtil.getCmd(bytes);
                GameServerHandler handler = ChatServer.allHandlerMap.get(cmd.getCmdType());
                if(handler!= null ){
                    handler.handler(new GameServerRequest(cmd,this),new GameServerResponse());
                }
                //messages.push(ByteUtil.copy(bytes,0,n));
               // System.out.println("Client Socket Message:"+str);
                //Thread.sleep(1000);
                //pw.println("Message Received");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
        }finally{
            System.out.println("Close.....");
            try {
                br.close();
                pw.close();
                socket.close();
                this.close();

            } catch (Exception e2) {

            }
        }
    }
}
