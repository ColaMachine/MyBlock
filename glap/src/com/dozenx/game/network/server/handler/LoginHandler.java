package com.dozenx.game.network.server.handler;

import cola.machine.game.myblocks.registry.CoreRegistry;
import com.alibaba.fastjson.JSON;
import com.dozenx.game.engine.command.PlayerSynCmd;
import com.dozenx.game.network.server.PlayerStatus;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.LoginCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by luying on 17/2/18.
 */
public class LoginHandler extends GameServerHandler {
    private UserService userService ;
    public LoginHandler(ServerContext serverContext){
        super(serverContext);
        userService = CoreRegistry.get(UserService.class);
    }
    public ResultCmd  handler(GameServerRequest request, GameServerResponse response){
        LogUtil.println("server接收到登录请求了");
        GameCmd cmd =request.getCmd();
        LoginCmd loginCmd = (LoginCmd) cmd;
        String userName = loginCmd.getUserName();
        String pwd = loginCmd.getPwd();
        if(StringUtil.isBlank(userName)){
            return new ResultCmd(1,"密码不能为空",loginCmd.getThreadId());
        }else{

            PlayerStatus playerStatus = userService.getUserInfoByUserName(userName);
            if(playerStatus==null){
                playerStatus =new PlayerStatus();
                playerStatus.setId((int)(Math.random()*10000));
                playerStatus.setName(userName);
                playerStatus.setPwd(pwd);
                userService.save(playerStatus);
            }
            if(playerStatus.getPwd().equals(pwd)){
                serverContext.messages.offer(new PlayerSynCmd(playerStatus).toBytes());
                //把所有人的信息都同步给他
                serverContext.addLivingThing(playerStatus);
               Iterator<Map.Entry<Integer , PlayerStatus>> it = serverContext.id2PlayerMap.entrySet().iterator();
                for(Map.Entry<Integer, PlayerStatus> entry :serverContext.id2PlayerMap.entrySet()){
                    request.getWorker().send(new PlayerSynCmd(entry.getValue()).toBytes());
                }
                return new ResultCmd(0, JSON.toJSONString(playerStatus),loginCmd.getThreadId());

                //把所有在线玩家的状态同步给他



            }else{

                return new ResultCmd(1,"密码错误",loginCmd.getThreadId());
            }
        }

    }
}
