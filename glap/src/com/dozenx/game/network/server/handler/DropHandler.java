package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.AttackCmd;
import com.dozenx.game.engine.command.DropCmd;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Vector;

import java.util.List;

/**
 * Created by luying on 17/2/18.
 */
public class DropHandler extends GameServerHandler {
    private EnemyService enemyService;
    private UserService userService;
    private BagService bagService;
    public DropHandler(ServerContext serverContext){
        super(serverContext);
        enemyService = (EnemyService)serverContext.getService(EnemyService.class);
        userService = (UserService)serverContext.getService(UserService.class);
    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){

        DropCmd cmd =(DropCmd) request.getCmd();
        LivingThingBean from = userService.getOnlinePlayerById(cmd.getUserId());

        ItemServerBean[] itemAry = bagService.getItemAryUserId(from.getId());

        itemAry[42]=null;

        broadCast(cmd);
        //更新其他附近人的此人的装备属性
        return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
