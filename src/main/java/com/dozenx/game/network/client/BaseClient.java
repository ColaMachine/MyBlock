package com.dozenx.game.network.client;

import cola.machine.game.myblocks.engine.BlockEngine;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.LoginState;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatFrame;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.client.bean.GameCallBackTask;
import com.dozenx.util.ByteUtil;
import com.dozenx.util.FileUtil;
import com.dozenx.util.SocketUtil;
import core.log.LogUtil;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luying on 16/10/7.
 */
public class BaseClient extends Thread{

    public static Stack<GameCmd> sendOver=new Stack<>();
    public static Map<Integer, GameCallBackTask> SyncTaskMap= new ConcurrentHashMap<Integer, GameCallBackTask>();

    public BaseClient(){
    }
    Socket socket = null;

    private final Object lock = new Object();
   // BufferedReader br = null;
    //PrintWriter pw = null;

    public  void send(GameCmd cmd ){
    //if(pw!=null){
       /* System.out.println(" send message"+message);
        pw.println(message);
        pw.flush();*/
        try {
            byte[] oldByteAry = cmd.toBytes();

            /*if(ByteUtil.getInt(ByteUtil.slice(oldByteAry,4,4))>10){
                LogUtil.err("错误");
            }
            if(ByteUtil.getInt(ByteUtil.slice(oldByteAry,4,4)) == 5){
                LogUtil.println("错误");
            }*/
            if(cmd.getCmdType() == CmdType.POS){
                //LogUtil.println(" is moving");
            }
          /*  byte[] newByteAry =

            ByteUtil.getBytes(oldByteAry,new byte[]{'\n'});*/

           // CmdType.printSend(cmd);
            //LogUtil.println("client 准备发送数据类型:"+ cmd.getCmdType()+"长度:"+(oldByteAry.length-4));
            synchronized (this) {
                outputStream.write(ByteUtil.getBytes(oldByteAry.length));
                outputStream.write(oldByteAry);//需要加锁
                outputStream.write(Constants.end);
            }
            //LogUtil.println("send over");
            //outputStream.write('\n');
        } catch (IOException e) {
            this.end=true;
            e.printStackTrace();
        }
        // }
    }

    public  ResultCmd syncSend(GameCmd cmd ){
        try {
            byte[] oldByteAry = cmd.toBytes();
            //synchronized (this) {//这里需要加锁吗

                GameCallBackTask task = new GameCallBackTask(){
                    @Override
                    public void run(){
                        /*if(getResult().getResult()==0){
                            if(getResult().getMsg()!=null){
                                PlayerSynCmd cmd =new  PlayerSynCmd(getResult().getMsg());
                                //PlayerStatus status = JSON.parseObject(getResult().getMsg(),PlayerStatus.class);
                            }
                        }else{
                            return;
                        }*/
                        LogUtil.println("进入恢复任务");
                        synchronized (lock) {LogUtil.println("通知线程恢复");
                            lock.notifyAll(); // 收到响应，唤醒线程
                        }
                    }
                };
                int threadId = (int)(Math.random()*100000);
                BaseClient.SyncTaskMap.put(threadId, task);

                //CoreRegistry.get(Client.class).send(new LoginCmd(userName.getText(),pwd.getText(),threadId));
                GetCmd sendCmd = new GetCmd(cmd,threadId);
                byte[] data = sendCmd.toBytes();
                outputStream.write(ByteUtil.getBytes(data.length));

                outputStream.write(data);//需要加锁
                outputStream.write(Constants.end);

                synchronized (lock) {
                    LogUtil.println("挂起线程 threadid:"+threadId);
                    lock.wait(5000); // 未收到响应，使线程等待  最多等待多少秒
                }
                LogUtil.println("线程恢复");
                //清除task
                SyncTaskMap.remove(threadId);
                return task.getResult();
            //}
        } catch (Exception e) {
            this.end=true;
            e.printStackTrace();
        }
        return null;
    }
    boolean end =false;
    public static void main(String args[]){
        BaseClient client =new BaseClient();
        //new Thread();
        client.start();

       // Path path = Paths.get("G:/test/from")

       // Path path = Paths.get("/Users/luying/Documents/workspace/MyBlock/src/main/java/com/dozenx/game/network/server/handler")
;
Path path =Paths.get("/Users/luying/Pictures");
//  List<File>  files = FileUtil.listFile(path.toFile());
        int i=0;



        client.iteratorDir(path,path.toFile());
        System.out.println("发送完成了");
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
    public  void iteratorDir(Path rootPath,File file ){

        File[] fileAry = file.listFiles();
        for(File childFile : fileAry){
            if(childFile.isDirectory()){
                iteratorDir(rootPath,childFile);
            }else{
                //触发事件
                sendThisFile(rootPath,childFile);
                // fileList.add(childFile);
            }
        }
        // return fileList;
    }
    public static int i=0;

    public void sendThisFile(Path rootPath,File file ){

        //将文件的crc filesize 发送的进度 等信息缓存起来 以备后面要检查
        //int i=0;
        byte[] bts = new byte[1024];
        FileStartCmd fileStartCmd =new FileStartCmd();
        Path relative =  rootPath.relativize(Paths.get(file.getPath()));
        fileStartCmd.setFileName(relative.toString());
        fileStartCmd.setTaskId(i); //i++;
        fileStartCmd.setFileSize((int) file.getFreeSpace());
        this.send(fileStartCmd);
        FileEndCmd fileEndCmd =new FileEndCmd();
        Long left=fileStartCmd.getFileSize();
        fileEndCmd.setTaskId(i);
        FileDataCmd fileDataCmd = new FileDataCmd();
        fileDataCmd.setTaskId(i);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int readed = 0;
            int readCount = 0;
            while((readCount = fileInputStream.read(bts))>0){
                left-=readCount;
                if(readCount<1024){

                    byte[] data = ByteUtil.slice(bts,0,readCount);
                    fileDataCmd.setData(data);
                    fileDataCmd.startPos=readed;

                    this.send(fileDataCmd);
                }else{
                    fileDataCmd.setData(bts);
                    this.send(fileDataCmd);
                }
            }
            if(readCount<=0){
                this.send(new FileEndCmd());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //等待收到确认消息

        System.out.println("发送完成一个文件"+file.getName());


        while(sendOver.size()>0  ){
            ResultCmd resultCmd = (ResultCmd)sendOver.pop();
            LogUtil.println("接受到了结果"+new String(resultCmd.getMsg()));

        }

        i++;

    }
    public void beginRepair(InputStream inputStream) throws IOException {
        while( inputStream.read() != Constants.end){

        }
    }
    public void run(){
        int curColor=0;
        try {
            //客户端socket指定服务器的地址和端口号
            //socket = new Socket("127.0.0.1", Constants.serverPort);
            socket = new Socket("192.168.1.105", Constants.serverPort);
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
            int n1=0;
            while(true){//不断读取数据 然后压入到messages里 由界面端显示出stack
                //String str = br.readLine();

              /*  inputSteram.read(bytes,0,4);
                int length = ByteUtil.getInt(bytes);ByteUtil.clear(bytes);
              //  LogUtil.println("client received data length: "+length);
                if(length<=0){
                  *//* n=  inputSteram.read(bytes);
                    if(n==-1){*//*
                        LogUtil.err("socket 读取数据有问题 +"+length+"+ 已经自动断开");
                        beginRepair(inputSteram);
                    *//*    break;
                    }*//*


                }
                if(length>4096){
                    LogUtil.err("err");

                }

                byte[] newBytes;
                //int n=0;
                if(length<4096){

                    n = inputSteram.read(bytes, 0, length);

                    newBytes = ByteUtil.slice(bytes, 0, length);

                }else{
                    newBytes = new byte[length];
                    n=inputSteram.read(newBytes, 0, length);
                    if(n<length){

                    }
                }
                int end = inputSteram.read();
                if (n != length || end != Constants.end) {
                    LogUtil.err(" read error ");
                    beginRepair(inputSteram);
                }
                ByteUtil.clear(bytes);*/
                //////////////////////////
               /* n= inputSteram.read(bytes,0,length);
                int end = inputSteram.read();
                if(  n!=length || end!=Constants.end){
                    LogUtil.err(" read error ");
                    beginRepair(inputSteram);
                   // continue;
                }
                byte[] newBytes =  ByteUtil.slice(bytes,0,length);
                ByteUtil.clear(bytes);*/
               /* if(str==null){
                    LogUtil.println("失去连接 正在重新连接");
                    //Thread.sleep(1000);
                    continue;
                }*/

            /*    if(newBytes .length <4 *//*||ByteUtil.getInt(newBytes)>10*//* ){
                    LogUtil.err("错误");
                }*/
                byte[] newBytes = SocketUtil.read(inputSteram);
                CmdType.printReceive(newBytes);
               // LogUtil.println("client 准备接收数据类型:"+ CmdType.values()[ByteUtil.getInt(newBytes)]+"长度:"+(length));
                try {
                   // if (n == 0) {
                     //   LogUtil.err("读取的数据为0");
                        //Thread.sleep(1000);
                        //continue;
                   // }
                    GameCmd cmd = CmdUtil.getCmd(newBytes);
                   // LogUtil.println("the received cmd was : "+cmd.getCmdType());
                    if (cmd.getCmdType()== CmdType.RESULT) {
                         ResultCmd result = (ResultCmd) cmd;

                        if(result.getThreadId()>0){
                            GameCallBackTask task = SyncTaskMap.get(result.getThreadId());
                            if(task!=null){
                                task .setResult(result);
                                LogUtil.println("任务开跑");
                                task.run();
                               // task.notifyAll();
                            }else{
                                LogUtil.err(result.getThreadId()+"task 不能为null");
                            }

                        }else{

                            sendOver.push(result);
                            //LogUtil.println(new String(result.getMsg()));
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
