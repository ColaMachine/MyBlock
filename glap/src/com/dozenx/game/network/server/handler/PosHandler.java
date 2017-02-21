package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.engine.command.SayCmd;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;

/**
 * Created by luying on 17/2/18.
 */
public class PosHandler extends GameServerHandler {

    public PosHandler(ServerContext serverContext){
        super(serverContext);

    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){

        broadCast(request.getCmd());
        //更新其他附近人的此人的装备属性
        return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. messages.offer(cmd.toBytes());
    }
}
