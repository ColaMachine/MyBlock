package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.BlockUtil;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.action.ItemDoorParser;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.util.Map;

/**
 * item 模板存放在
 */
public class DoorDefinition extends  BlockDefinition{

    public void receive(Map map ){
        super.receive(map);
        ItemDoorParser.parse(this,map);
    }
  public void use(GL_Vector placePoint,ItemType itemType,GL_Vector viewDir){
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
      cmd.blockType = itemType.ordinal();



      if(cmd.cy<0){
          LogUtil.err("y can't be <0 ");
      }

      //blockType 应该和IteType类型联系起来

      if(cmd.blockType== ItemType.wood_door.ordinal()){
          int condition = BlockUtil.getIndex(placePoint, viewDir);
          cmd.blockType  = condition<<8|cmd.blockType;
                    /*if(pianyiX<0.1 ){//把一个方块分为 12345678 8个格子 算出它再哪个格子
                        //说明是向左的方向
                        if(block==1 ||  block==4||block==5 ||  block==8){
                            condition=Constants.LEFT;
                        }else{
                            condition=Constants.RIGHT;
                        }
                    }else if(pianyiX>0.9){
                        if(block==1 ||  block==4||block==5 ||  block==8){
                            condition=Constants.LEFT;
                        }else{
                            condition=Constants.RIGHT;
                        }
                        //说明是向右的方向
                    }else if(pianyiY<0.1 ){
                        //说明是向上的方向
                    }else if(pianyiY>0.9){
                        //说明是向下的方向
                    }else if(pianyiZ<0.1 ){
                        //说明是向前的方向
                    }else if(pianyiZ>0.9){
                        //说明是向后的方向
                    }*/
          CoreRegistry.get(Client.class).send(cmd);
          cmd.cy+=1;
          cmd.blockType = ItemType.copy_down.ordinal();
          CoreRegistry.get(Client.class).send(cmd);


      }
  }

}
