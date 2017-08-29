package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.BlockUtil;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.item.parser.ItemBoxParser;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.util.Map;

/**
 * item 模板存放在
 */
public class BoxDefinition extends  BlockDefinition{

    public void receive(Map map ){
        super.receive(map);
        ItemBoxParser.parse(this,map);
    }
  public void use(GL_Vector placePoint,Integer itemType,GL_Vector viewDir){
      //检查上方是否有物体
      int chunkX = MathUtil.getBelongChunkInt(placePoint.x);
      int chunkZ = MathUtil.getBelongChunkInt(placePoint.z);
      //   TreeBlock treeBlock =new TreeBlock(hitPoint);
      //treeBlock.startPosition=hitPoint;

      //  treeBlock.generator();
      int blockX = MathUtil.floor(placePoint.x) - chunkX * 16;
      int blockY = MathUtil.floor(placePoint.y);
      int blockZ = MathUtil.floor(placePoint.z) - chunkZ * 16;
      ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
      cmd.cx = blockX;
      cmd.cz = blockZ;
      cmd.cy = blockY;
      cmd.type = 1;
      cmd.blockType = itemType;



      if(cmd.cy<0){
          LogUtil.err("y can't be <0 ");
      }

      //blockType 应该和IteType类型联系起来

   //if(cmd.blockType== ItemType.wood_door.ordinal()){
          int condition = BlockUtil.getIndex(placePoint, viewDir);
          cmd.blockType  = condition<<8|cmd.blockType;

          CoreRegistry.get(Client.class).send(cmd);



   //   }
  }

}
