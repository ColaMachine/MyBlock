package com.dozenx.game.network.server;

import cola.machine.game.myblocks.engine.Constants;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.handler.GameServerHandler;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by luying on 16/10/7.
 */
public class Worker extends Thread {

    private Socket socket;

    public boolean end=false;

    public String ip;

   // Queue<byte[]> messages;

    InputStream inputSteram = null;

    OutputStream outputStream = null;

    ServerContext serverContext ;
    public void close(){
        LogUtil.println("失去连接"+this.ip);
        this.end=true;

    }

    public Worker(Socket socket,ServerContext serverContext){

        this.ip=socket.getInetAddress().getHostAddress();
        LogUtil.println("连接服务器"+this.ip);
        this.socket =socket;
       this.serverContext = serverContext;

    }

    public void send(byte[] bytes){
        if(outputStream!=null)
            try {
                if(ByteUtil.getInt(ByteUtil.slice(bytes,4,4))>10){
                    LogUtil.err("错误");
                }
                //
                CmdType.printSend(ByteUtil.slice(bytes,4,4));
                //LogUtil.println("server 准备发送数据类型:"+ CmdType.values()[(ByteUtil.getInt(ByteUtil.slice(bytes,4,4)))]+"长度:"+(bytes.length-4));

                outputStream.write(bytes);
                outputStream.write(Constants.end);
            } catch (IOException e) {
                e.printStackTrace();
            }
        //pw.println(message);
    }
    public void run(){
        byte[] bytes =new byte[100];
        try {
            inputSteram =  socket.getInputStream();
            //用于发送返回信息,可以不需要装饰这么多io流使用缓冲流时发送数据要注意调用.flush()方法
            outputStream = socket.getOutputStream();
            while (!this.end && this.isAlive()) {
              // int n = br.read(bytes);


                inputSteram.read(bytes,0,4);
                int length = ByteUtil.getInt(bytes);ByteUtil.clear(bytes);
                if(length<=0){
                  /* n=  inputSteram.read(bytes);
                    if(n==-1){*/
                    LogUtil.err("socket 读取数据有问题 +"+length+"+ 已经自动断开");
                    beginRepair(inputSteram);
                    /*    break;
                    }*/


                }

                if(length>4096){
                    LogUtil.err("err");
                }
                int n= inputSteram.read(bytes,0,length);
                int end = inputSteram.read();
                if(  n!=length || end!=Constants.end){
                    LogUtil.err(" read error ");
                    beginRepair(inputSteram);
                }

                byte[] newBytes =  ByteUtil.slice(bytes,0,length);ByteUtil.clear(bytes);


               /* if(bytes[n-1]!='\n'){
                    LogUtil.err("socket read end is not \n:"+n);
                }*/
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
                CmdType.printReceive(newBytes);
            //LogUtil.println("server 接收到数据类型:"+CmdType.values()[(ByteUtil.getInt(newBytes))] +"长度:"+length);
                GameCmd cmd = CmdUtil.getCmd(newBytes);
                GameServerHandler handler = serverContext.allHandlerMap.get(cmd.getCmdType());
                if(handler!= null ){
                    ResultCmd resultCmd =handler.handler(new GameServerRequest(cmd,this),new GameServerResponse());
                    if(resultCmd!=null){
                        send(resultCmd.toBytes());
                    }
                }
                //messages.push(ByteUtil.copy(bytes,0,n));
               // System.out.println("Client Socket Message:"+str);
                //Thread.sleep(1000);
                //pw.println("Message Received");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
           e.printStackTrace();
            LogUtil.println(e.getMessage());
        }finally{
            System.out.println("Close.....");
            try {
                inputSteram.close();
                this.outputStream.close();
                socket.close();
                this.close();
                this.end=true;

            } catch (Exception e2) {

            }
        }
    }
    public void beginRepair(InputStream inputStream) throws IOException {
        while( inputStream.read() != Constants.end){

        }
    }

    public static void main(String args[]){
        HashMap<CmdType, String > map =new HashMap();
        map.put(CmdType.POS,"POS");
        map.put(CmdType.LOGIN,"LOGIN");
        map.put(CmdType.ATTACK,"ATTACK");
        map.put(CmdType.PLAYERSTATUS,"PLAYERSTATUS");

        GameCmd  cmd = new LoginCmd("1","1",1);
        CmdType type = cmd.getCmdType();
        LogUtil.println(map.get(CmdType.LOGIN));
    }
}
