package com.dozenx.game.network.client.action;

import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.network.client.bean.ClientRequest;
import com.dozenx.game.network.server.service.UserService;

/**
 * Created by dozen.zhang on 2017/2/20.
 */
public class LoginClientAction {

    public ResultDTO login(String  username,String pwd){
        userService=  Proxy.createProxy(UserService).login(userName,pwd);
        ResultDTO result = userService.valid(username,pwd);

        if(result==asdf){
            //登录成功
        }else{
            //登录失败
        }
       // CoreRegistry.get(Client.class).send(new ClientRequest());

    }
}
