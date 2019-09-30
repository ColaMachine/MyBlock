package cola.machine.game.myblocks.server;

import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatFrame;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.network.client.bean.GameCallBackTask;
import core.log.LogUtil;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by luying on 16/10/9.
 */
public class NetWorkManager {

    Client client;
    ChatFrame chatFrame;
    LivingThingManager livingThingManager;
    public static Queue<GameCmd> queue = new LinkedList<GameCmd>();

    public static void push(GameCmd cmd ){
        queue.add(cmd);
    }
    public NetWorkManager(){
        this.client= CoreRegistry.get(Client.class);
        livingThingManager= CoreRegistry.get(LivingThingManager.class);
        this.chatFrame= CoreRegistry.get(ChatFrame.class);

        if(client==null || livingThingManager==null || chatFrame==null){

        }
    }

    public void update(){
        while( queue.peek()!=null){
            GameCmd cmd =queue.poll();
            if (cmd.getCmdType()== CmdType.EQUIP) {//equip
                //equips.push((EquipCmd) cmd);
                livingThingManager.addEquipsToSomeOne((EquipCmd) cmd);

            } else if (cmd.getCmdType()== CmdType.POS) {
                //movements.push((PosCmd)cmd);
            } else if (cmd.getCmdType()== CmdType.SAY) {
                //messages.push((SayCmd)cmd);
            }
            else if (cmd.getCmdType()== CmdType.PLAYERSTATUS) {
               // playerSync.offer((PlayerSynCmd)cmd);
            }else if (cmd.getCmdType()== CmdType.ATTACK) {
               // attacks.push((AttackCmd)cmd);
            }else if (cmd.getCmdType()== CmdType.BAG) {
               // bags.push((BagCmd)cmd);
            }else if (cmd.getCmdType()== CmdType.DROP) {
               // drops.push((DropCmd)cmd);
            }else if (cmd.getCmdType()== CmdType.CHUNKREQUEST) {
               // chunks.offer((ChunkRequestCmd)cmd);
            }else if (cmd.getCmdType()== CmdType.CHUNKRESPONSE) {
                //chunkResponses.offer((ChunkResponseCmd)cmd);
            }else if (cmd.getCmdType()== CmdType.CHUNKSS) {
               // chunkAlls.offer((ChunkssCmd)cmd);
            }else if (cmd instanceof  UserBaseCmd ||cmd.getCmdType()== CmdType.PICK || cmd.getCmdType()== CmdType.WALK ||cmd.getCmdType()== CmdType.WALK2 || cmd.getCmdType()== CmdType.DIED|| cmd.getCmdType()== CmdType.REBORN
                    || cmd.getCmdType()== CmdType.JUMP|| cmd.getCmdType()== CmdType.CHASE) {
              //  humanStates.push(cmd);
            }
//            else if (cmd.getCmdType()== CmdType.RESULT) {
//                ResultCmd result = (ResultCmd) cmd;
//
//                if(result.getThreadId()>0){
//                    GameCallBackTask task = SyncTaskMap.get(result.getThreadId());
//                    if(task!=null){
//                        task .setResult(result);
//                        LogUtil.println("任务开跑");
//                        task.run();
//                        // task.notifyAll();
//                    }else{
//                        LogUtil.err(result.getThreadId()+"task 不能为null");
//                    }
//
//                }
//
//            }

        }

    }
}
