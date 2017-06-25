package com.dozenx.game.network.server;

import cola.machine.game.myblocks.world.block.TreeBlock;
import com.dozenx.game.engine.command.ChunkssCmd;
import com.dozenx.game.engine.command.DropCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.command.PlayerSynCmd;
import com.dozenx.game.engine.item.bean.ItemSeed;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.engine.live.state.IdleState;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.TimeUtil;

import javax.vecmath.Point4f;
import javax.vecmath.Point4i;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by luying on 16/10/7.
 */
public class ServerGrowTask extends TimerTask {
  ServerContext serverContext;

  public static List<ItemSeed> seeds =new ArrayList<>();
    public ServerGrowTask(ServerContext serverContext){
        this.serverContext = serverContext;

    }
    //如何把小怪物的动作行动同步到各个客户端呢 一个办法是告诉客户端 怪物的一个5秒动作 即是 要到哪个点 要执行哪个动作 这样能减少客户端的数据交互 所以最终还是得看指令
    public void run(){
        try {
            Long now = TimeUtil.getNowMills();
            //遍历所有种子 以后可以采用priorityList 优先处理快要长成的种子
            for(int i=0,length=seeds.size();i<length;i++){
                ItemSeed seed = seeds.get(i);
                ItemType seedType =seed.getSeedType();
                if(now-seed.getPlantedTime()>10000){//先写死10秒
                    //开始生成树
                    seeds.remove(i);
                      TreeBlock treeBlock =new TreeBlock(serverContext.chunkProvider,seed.getPosition());

                     List<Integer> list =  treeBlock.generator();
                        serverContext.getMessages().offer(new ChunkssCmd(list).toBytes());


                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
