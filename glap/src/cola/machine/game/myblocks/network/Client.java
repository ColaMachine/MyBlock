package cola.machine.game.myblocks.network;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.logic.characters.MovementMode;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatFrame;
import com.dozenx.game.engine.command.*;
import com.dozenx.util.ByteUtil;

import java.io.*;
import java.net.Socket;
import java.util.Stack;

/**
 * Created by luying on 16/10/7.
 */
public class Client extends Thread{
    public static Stack<GameCmd> messages=new Stack<GameCmd>();
    public static Stack<GameCmd> equips=new Stack<GameCmd>();
    public static Stack<GameCmd> movements=new Stack<GameCmd>();
    public static Stack<GameCmd> newborns=new Stack<GameCmd>();
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
            byte[] newByteAry =
            ByteUtil.getBytes(oldByteAry,new byte[]{'\n'});
            outputStream.write(newByteAry);
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
                n=inputSteram.read(bytes);
               /* if(str==null){
                    LogUtil.println("失去连接 正在重新连接");
                    //Thread.sleep(1000);
                    continue;
                }*/
                if(n==0){
                    LogUtil.err("读取的数据为0");
                    //Thread.sleep(1000);
                    //continue;
                }
                if(bytes[0]== (byte)CmdType.EQUIP.ordinal()){//equip
                    equips.push(new EquipmentCmd(bytes));

                }else if(bytes[0]== (byte)CmdType.POS.ordinal()){
                    movements.push(new PosCmd(bytes));
                }else if(bytes[0]== (byte)CmdType.POS.ordinal()){
                    messages.push(new SayCmd(bytes));
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
