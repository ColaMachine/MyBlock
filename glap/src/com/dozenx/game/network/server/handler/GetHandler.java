package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.*;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by luying on 17/2/18.
 */
public class GetHandler extends GameServerHandler {

    public GetHandler(ServerContext serverContext){
        super(serverContext);

    }
    public ResultCmd  handler(GameServerRequest request, GameServerResponse response){

        GetCmd cmd = (GetCmd)request.getCmd();

        LogUtil.println("接收到线程id:"+cmd.getThreadId());
        byte[] data = cmd.getData();
        GameCmd childCmd = CmdUtil.getCmd(data);


        GameServerHandler handler = serverContext.getHandler(childCmd.getCmdType());
        if(handler!= null ){
            ResultCmd resultCmd =handler.handler(new GameServerRequest(childCmd,request.getWorker()),new GameServerResponse());
            if(resultCmd!=null){
                resultCmd.setThreadId(cmd.getThreadId());
                return   resultCmd;
            }else{
                LogUtil.err("resultCmd is null");
            }
        }else{
            LogUtil.err("handler is null");
        }

        return new ResultCmd(1, "失败",cmd.getThreadId());

        //更新其他附近人的此人的装备属性

    }

}
