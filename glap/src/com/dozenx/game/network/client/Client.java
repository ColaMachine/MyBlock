package com.dozenx.game.network.client;

import cola.machine.game.myblocks.engine.Constants;
import com.dozenx.game.network.client.bean.GameCallBackTask;
import core.log.LogUtil;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatFrame;
import com.dozenx.game.engine.command.*;
import com.dozenx.util.ByteUtil;
import sun.rmi.runtime.Log;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luying on 16/10/7.
 */
public class Client extends Thread{
    public static Stack<GameCmd> messages=new Stack<GameCmd>();
    public static Stack<GameCmd> equips=new Stack<GameCmd>();
    public static Stack<GameCmd> movements=new Stack<GameCmd>();
    public static Stack<GameCmd> newborns=new Stack<GameCmd>();
    public static Map<Integer, GameCallBackTask> taskMap= new ConcurrentHashMap<Integer, GameCallBackTask>();
    public static Queue<GameCmd> playerSync=new LinkedList<>();
    ChatFrame chatFrame;
    public Client(){
         chatFrame =  CoreRegistry.get(ChatFrame.class );
    }
    Socket socket = null;
   // BufferedReader br = null;
    //PrintWriter pw = null;
    public  void send(GameCmd cmd ){
    //if(pw!=null){
       /* System.out.println(" send message"+message);
        pw.println(message);
        pw.flush();*/
        try {
            byte[] oldByteAry = cmd.toBytes();

            if(ByteUtil.getInt(ByteUtil.slice(oldByteAry,4,4))>10){
                LogUtil.err("错误");
            }
            if(ByteUtil.getInt(ByteUtil.slice(oldByteAry,4,4)) == 5){
                LogUtil.println("错误");
            }
          /*  byte[] newByteAry =

            ByteUtil.getBytes(oldByteAry,new byte[]{'\n'});*/
            LogUtil.println("client 准备发送数据类型:"+ ByteUtil.getInt(ByteUtil.slice(oldByteAry,4,4))+"长度:"+(oldByteAry.length-4));

            outputStream.write(oldByteAry);
            LogUtil.println("send over");
            //outputStream.write('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
        // }
    }
    public static void main(String args[]){
        //System.out.println(message);
        /*Client client =new Client();
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
        }*/

    }
    InputStream inputSteram;
    OutputStream outputStream;
    public void run(){
        int curColor=0;
        try {
            //客户端socket指定服务器的地址和端口号
            socket = new Socket("127.0.0.1", Constants.serverPort);
            //socket.setTcpNoDelay(true);
            System.out.println("Socket=" + socket);
            //同服务器原理一样
            inputSteram = socket.getInputStream();
           /* br = new BufferedReader(new InputStreamReader(
                   ));*/
             outputStream = socket.getOutputStream();
           /* pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    )));*/
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
            byte[] bytes=new byte[4096];
            int n=0;
            while(true){//不断读取数据 然后压入到messages里 由界面端显示出stack
                //String str = br.readLine();

                inputSteram.read(bytes,0,4);
                int length = ByteUtil.getInt(bytes);
                LogUtil.println("client received data length: "+length);
                if(length<=0){
                  /* n=  inputSteram.read(bytes);
                    if(n==-1){*/
                        LogUtil.err("socket 读取数据有问题 +"+length+"+ 已经自动断开");

                    /*    break;
                    }*/


                }
                if(length>4096){
                    LogUtil.err("err");
                }
                n= inputSteram.read(bytes,0,length);
              byte[] newBytes =  ByteUtil.slice(bytes,0,length);
               /* if(str==null){
                    LogUtil.println("失去连接 正在重新连接");
                    //Thread.sleep(1000);
                    continue;
                }*/
                if(ByteUtil.getInt(newBytes)>10){
                    LogUtil.err("错误");
                }
                LogUtil.println("client 准备接收数据类型:"+ ByteUtil.getInt(newBytes)+"长度:"+(length));
                try {
                    if (n == 0) {
                        LogUtil.err("读取的数据为0");
                        //Thread.sleep(1000);
                        //continue;
                    }
                    GameCmd cmd = CmdUtil.getCmd(newBytes);
                    LogUtil.println("the cmd was : "+cmd.getCmdType());
                    if (cmd.getCmdType()== CmdType.EQUIP) {//equip
                        equips.push(cmd);

                    } else if (cmd.getCmdType()== CmdType.POS) {
                        movements.push(cmd);
                    } else if (cmd.getCmdType()== CmdType.SAY) {
                        messages.push(cmd);
                    }
                    else if (cmd.getCmdType()== CmdType.PLAYERSTATUS) {
                        playerSync.offer(cmd);
                    }
                    else if (cmd.getCmdType()== CmdType.RESULT) {
                         ResultCmd result = (ResultCmd) cmd;

                        if(result.getThreadId()>0){
                            GameCallBackTask task = taskMap.get(result.getThreadId());
                            if(task!=null){
                                task .setResult(result);
                                task.run();
                               // task.notifyAll();
                            }else{
                                LogUtil.err("不能为null");
                            }

                        }

                    }

                    /*else if (cmd.getCmdType()== CmdType.L) {

                        BlockEngine.engine.changeState(new GamingState());
                    }*/


                }catch (Exception e){
                    e.printStackTrace();
                }
               /* if(str.startsWith("say:")) {
                    messages.push(str.substring(4));
                }else if(str.startsWith("move:")) {


                    movements.push(str.substring(5).split(","));
                }
                else if(str.startsWith("newborn:")) {


                    newborns.push(str.substring(8).split(","));
                }*/

                /* curColor = (curColor + 1) % 3;
                chatFrame.appendRow("color"+curColor ,str);*/
               /* if(str.equals("END")){
                    break;
                }*/
                //System.out.println("Client Socket Message:"+str);
                //Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("close......");
               // br.close();
                //pw.close();
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
