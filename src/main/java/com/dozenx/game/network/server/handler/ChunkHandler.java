package com.dozenx.game.network.server.handler;

import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.ServerChunkProvider;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.bean.ItemSeed;
import com.dozenx.game.network.server.ServerGrowTask;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/18.
 */
public class ChunkHandler extends GameServerHandler {
    private EnemyService enemyService;
    private UserService userService;
    private BagService bagService;
    public  ChunkProvider chunkProvider ;

    public ChunkHandler(ServerContext serverContext){
        super(serverContext);
        enemyService = (EnemyService)serverContext.getService(EnemyService.class);
        userService = (UserService)serverContext.getService(UserService.class);
        bagService =(BagService)serverContext.getService(BagService.class);
        chunkProvider = (ServerChunkProvider) serverContext.chunkProvider;
    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){
        if(request.getCmd().getCmdType() == CmdType.CHUNKRESPONSE){//批量同步
            ChunkResponseCmd cmd = (ChunkResponseCmd) request.getCmd();
            Chunk chunk = chunkProvider.getChunk(cmd.chunk.getPos().x,cmd.chunk.getPos().y,cmd.chunk.getPos().z);
            chunk.setBlockData( cmd.chunk.getBlockData());
            //怎么保存呢这是个问题啊
            broadCast(cmd);
        }else {
            ChunkRequestCmd cmd = (ChunkRequestCmd) request.getCmd();
            LogUtil.println("chunkHandler",cmd.cx+":"+cmd.cy+":"+cmd.cz);
            Chunk chunk = chunkProvider.getChunk(cmd.getX(), 0, cmd.getZ());
            // TeraArray ary = chunk .getBlockData();

            if (cmd.type == 0) {
                //LogUtil.println("服务器加载chunk:"+cmd.x+","+cmd.z);
                ChunkResponseCmd chunkCmd = new ChunkResponseCmd(chunk);

                // return new ResultCmd( chunkCmd.toBytes());
                return new ResultCmd(0, chunkCmd.toBytes(), 0);
            } else if (cmd.type == 1 || cmd.type == 2) {// 增加方块 或者 删除方块
                LogUtil.println("chun设置 chunk:" + cmd.x + "," + cmd.z + "" + "cx" + cmd.cx +"cy"+cmd.cy+ "cz" + cmd.cz + "blockid" + cmd.blockType);
                chunk.setBlock(cmd.cx, cmd.cy, cmd.cz, cmd.blockType);
                if(cmd.dir>0){
               /* BaseBlock baseBlock = (BaseBlock)chunk.getBlock(cmd.cx, cmd.cy, cmd.cz);
                baseBlock.dir=cmd.dir;*/
                    chunk.setBlockStatus(cmd.cx, cmd.cy, cmd.cz, cmd.dir);
                }
                if (cmd.blockType == ItemType.tree_seed.id) {
                    ItemSeed itemSeed = new ItemSeed();
                    itemSeed.setPosition(new GL_Vector(cmd.x * 16 + cmd.cx, cmd.cy, cmd.z * 16 + cmd.cz));
                    itemSeed.setPlantedTime(TimeUtil.getNowMills());
                    ServerGrowTask.seeds.add(itemSeed);
                    //单独放一个文件 放置文件对应的xyz对应的status 序列化到文件里 这个文件的  对象是 blockstatus  dir status rotate 以后慢慢扩充吧 但是 加载后放到哪里呢放到chunkimpl里单独一个map么?那游戏里怎么办  也照样是一个
                    //map <Integer , blockstatus> ?? 
                }
                broadCast(cmd);
            }
        }
        return null;
        //更新其他附近人的此人的装备属性
        //return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
