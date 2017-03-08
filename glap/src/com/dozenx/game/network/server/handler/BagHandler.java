package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import core.log.LogUtil;

/**
 * Created by luying on 17/2/18.
 */
public class BagHandler extends GameServerHandler {

    public BagHandler(ServerContext serverContext){
        super(serverContext);

    }
    public ResultCmd  handler(GameServerRequest request, GameServerResponse response){

        BagCmd cmd = (BagCmd)request.getCmd();



        cmd.setItemBeanList(serverContext.getItemByUserId(cmd.getUserId()));

        //包裹数量品种进行 校验 比对

        return new ResultCmd(1, cmd.toBytes(),0);

        //更新其他附近人的此人的装备属性

    }

}
