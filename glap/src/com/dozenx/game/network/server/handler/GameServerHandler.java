package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;

/**
 * Created by luying on 17/2/18.
 */
public class GameServerHandler {
    public ServerContext serverContext;
    public GameServerHandler(ServerContext serverContext){
        this.serverContext =serverContext;
    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){
        return null;
    }
}
