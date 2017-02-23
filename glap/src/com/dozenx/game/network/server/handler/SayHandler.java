package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.PlayerStatus;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import core.log.LogUtil;

/**
 * Created by luying on 17/2/18.
 */
public class SayHandler extends GameServerHandler {

    public SayHandler(ServerContext serverContext){
        super(serverContext);

    }
    public ResultCmd  handler(GameServerRequest request, GameServerResponse response){
        LogUtil.println("server接收到说话了 "+((SayCmd)request.getCmd()).getMsg());
        broadCast(request.getCmd());
        //更新其他附近人的此人的装备属性
        return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. messages.offer(cmd.toBytes());
    }
}
