package cola.machine.game.myblocks.server;

import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.CmdType;
import com.dozenx.game.engine.command.CmdUtil;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.LoginCmd;
import com.dozenx.game.server.user.bean.GameRequest;
import com.dozenx.game.server.user.bean.GameServerHandler;
import com.dozenx.game.server.user.bean.UserService;
import com.dozenx.util.StringUtil;

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

    public void  syncStatus(byte[] bytes){
        /*GameCmd cmd = CmdUtil.getCmd(bytes);
        GameServerHandler handler = ChatServer.allHandlerMap.get(cmd.getCmdType());
        if(handler!= null ){
            handler.handler(new GameRequest(cmd,));
        }
        if (cmd.getCmdType()== CmdType.EQUIP) {//equip


        } else if (cmd.getCmdType()== CmdType.POS) {

        } else if (cmd.getCmdType()== CmdType.SAY) {

        }else if (cmd.getCmdType()== CmdType.LOGIN) {
            LoginCmd loginCmd = (LoginCmd) cmd;
            String userName = loginCmd.getUserName();
            String pwd = loginCmd.getPwd();
            if(StringUtil.isBlank(userName)){

            }
           *//* CoreRegistry.get(UserService.class).getUserInfoByUserName(userName);
            //vertify the pwd
            loginCmd.pwd;
*//*
            //notice other player
        }*/


    }
    public void getCmd(byte[] bytes){

    }
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
