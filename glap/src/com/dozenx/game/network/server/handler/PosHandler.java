package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.PosCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.network.server.bean.PlayerStatus;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import core.log.LogUtil;

/**
 * Created by luying on 17/2/18.
 */
public class PosHandler extends GameServerHandler {

    public PosHandler(ServerContext serverContext){
        super(serverContext);

    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){

        PosCmd posCmd =(PosCmd) request.getCmd();
        int userId = posCmd.getUserId();
        PlayerStatus playerStatus =  serverContext.getOnlinePlayerById(userId);
        if(playerStatus==null){
            LogUtil.err("userId:"+userId+" find playerStatus is null");
        }
        playerStatus.setX(posCmd.getX());
        playerStatus.setY(posCmd.getY());
        playerStatus.setZ(posCmd.getZ());

        //TODO
        playerStatus.setBodyAngle(posCmd.getBodyAngle());
        playerStatus.setHeadAngle(posCmd.getBodyAngle());
        playerStatus.setHeadAngle2(posCmd.getBodyAngle());

        broadCast(request.getCmd());
        //更新其他附近人的此人的装备属性
        return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
