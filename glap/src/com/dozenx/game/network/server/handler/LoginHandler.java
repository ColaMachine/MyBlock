package com.dozenx.game.network.server.handler;

import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.network.server.PlayerStatus;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.LoginCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.service.UserService;
import com.dozenx.util.StringUtil;

/**
 * Created by luying on 17/2/18.
 */
public class LoginHandler extends GameServerHandler {
    private UserService userService ;
    public GameCmd  handler(GameServerRequest request, GameServerResponse response){
        if(userService==null){
            userService = CoreRegistry.get(UserService.class);
        }

        GameCmd cmd =request.getCmd();
        LoginCmd loginCmd = (LoginCmd) cmd;
        String userName = loginCmd.getUserName();
        String pwd = loginCmd.getPwd();
        if(StringUtil.isBlank(userName)){
            return new ResultCmd(1,"密码不能为空");
        }else{

            PlayerStatus playerStatus = userService.getUserInfoByUserName(userName);
            if(playerStatus.getPwd().equals(pwd)){
                return new ResultCmd(0,"登录成功");
            }else{
                return new ResultCmd(1,"密码错误");
            }
        }
    }
}
