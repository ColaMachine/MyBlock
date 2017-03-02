package com.dozenx.game.network.server.handler;

import cola.machine.game.myblocks.registry.CoreRegistry;
import com.alibaba.fastjson.JSON;
import com.dozenx.game.engine.command.PlayerSynCmd;
import com.dozenx.game.network.server.bean.*;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.LoginCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;

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

            LivingThingBean playerBean = userService.getUserInfoByUserName(userName);

            if(playerBean==null){
                PlayerStatus playerInfo = new PlayerStatus();


                playerInfo.setId((int)(Math.random()*10000));
                playerInfo.setName(userName);
                playerInfo.setPwd(pwd);
                userService.addNew(playerInfo);
                playerBean=new LivingThingBean(playerInfo);
            }
            if(playerBean.getPwd().equals(pwd)){
                serverContext.broadCast(new PlayerSynCmd(playerBean).toBytes());
                //把所有人的信息都同步给他
                serverContext.addOnlinePlayer(playerBean);
              /* Iterator<Map.Entry<Integer , PlayerStatus>> it = serverContext.id2PlayerMap.entrySet().iterator();
                for(Map.Entry<Integer, PlayerStatus> entry :serverContext.id2PlayerMap.entrySet()){
                    request.getWorker().send(new PlayerSynCmd(entry.getValue()).toBytes());
                }*/
                for(LivingThingBean player: serverContext.getAllOnlinePlayer()){
                    request.getWorker().send(new PlayerSynCmd(player).toBytes());
                }
                for(LivingThingBean player: serverContext.getAllEnemies()){
                    request.getWorker().send(new PlayerSynCmd(player).toBytes());
                }

                return new ResultCmd(0, JSON.toJSONString(playerBean),loginCmd.getThreadId());

                //把所有在线玩家的状态同步给他



            }else{

                return new ResultCmd(1,"密码错误",loginCmd.getThreadId());
            }
        }

    }
}
