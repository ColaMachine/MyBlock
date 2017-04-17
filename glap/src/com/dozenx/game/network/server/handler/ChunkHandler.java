package com.dozenx.game.network.server.handler;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import cola.machine.game.myblocks.world.chunks.ServerChunkProvider;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import core.log.LogUtil;

/**
 * Created by luying on 17/2/18.
 */
public class ChunkHandler extends GameServerHandler {
    private EnemyService enemyService;
    private UserService userService;
    private BagService bagService;
    private ChunkProvider chunkProvider ;

    public ChunkHandler(ServerContext serverContext){
        super(serverContext);
        enemyService = (EnemyService)serverContext.getService(EnemyService.class);
        userService = (UserService)serverContext.getService(UserService.class);
        bagService =(BagService)serverContext.getService(BagService.class);
        chunkProvider = (ServerChunkProvider) serverContext.chunkProvider;
    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){

        ChunkRequestCmd cmd =(ChunkRequestCmd) request.getCmd();

        Chunk chunk =  chunkProvider.getChunk(cmd.getX(), 0, cmd.getZ());
       // TeraArray ary = chunk .getBlockData();

        if(cmd.type==0){
            LogUtil.println("服务器加载chunk:"+cmd.x+","+cmd.z);
            ChunkResponseCmd chunkCmd =new ChunkResponseCmd(chunk);

            // return new ResultCmd( chunkCmd.toBytes());
            return new ResultCmd(0,chunkCmd.toBytes(),0);
        }else if(cmd.type==1||cmd.type==2){
            LogUtil.println("服务器加载chunk:"+cmd.x+","+cmd.z+"");
            chunk.setBlock(cmd.cx,cmd.cy,cmd.cz,cmd.blockType);
            broadCast(cmd);
        }

        return null;
        //更新其他附近人的此人的装备属性
        //return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
