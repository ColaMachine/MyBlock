package com.dozenx.game.server.user.bean;

import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.server.PlayerStatus;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.LoginCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.util.StringUtil;

/**
 * Created by luying on 17/2/18.
 */
public class LoginHandler extends GameServerHandler{
    private UserService  userService ;
    public GameCmd  handler(GameRequest request,GameResponse response){
        if(userService==null){
            userService = CoreRegistry.get(UserService.class);
        }

        GameCmd cmd =request.getCmd();
        LoginCmd loginCmd = (LoginCmd) cmd;
        String userName = loginCmd.getUserName();
        String pwd = loginCmd.getPwd();
        if(StringUtil.isBlank(userName)){
            return new ResultCmd(1,"密码不正确");
        }else{

        PlayerStatus playerStatus = userService.getUserInfoByUserName(userName);
            if(playerStatus.getPwd().equals(pwd)){

            }
        }
    }
}
