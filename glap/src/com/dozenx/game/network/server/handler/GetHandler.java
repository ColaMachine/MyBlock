package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.GetCmd;
import com.dozenx.game.engine.command.PlayerSynCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.network.server.bean.*;
import core.log.LogUtil;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by luying on 17/2/18.
 */
public class GetHandler extends GameServerHandler {

    public GetHandler(ServerContext serverContext){
        super(serverContext);

    }
    public ResultCmd  handler(GameServerRequest request, GameServerResponse response){
        LogUtil.println("server接收到说话了");
        GetCmd cmd = (GetCmd)request.getCmd();
        //Iterator<Map.Entry<Integer , PlayerStatus>> it = serverContext.id2PlayerMap.entrySet().iterator();
        for(LivingThingBean player: serverContext.getAllOnlinePlayer()){
            request.getWorker().send(new PlayerSynCmd(player.getInfo()).toBytes());
        }
        for(LivingThingBean player: serverContext.getAllEnemies()){
            request.getWorker().send(new PlayerSynCmd(player.getInfo()).toBytes());
        }
        /*
        for(Map.Entry<Integer, PlayerStatus> entry :serverContext.id2PlayerMap.entrySet()){
            request.getWorker().send(new PlayerSynCmd(entry.getValue()).toBytes());
        }*/
        return new ResultCmd(0, "成功",cmd.getThreadId());

        //更新其他附近人的此人的装备属性

    }

}
