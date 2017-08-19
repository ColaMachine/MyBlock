package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.parser.ItemBlockParser;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.MathUtil;
import glmodel.GL_Vector;

import java.util.Map;

/**
 * item 模板存放在
 */
public class BlockDefinition extends  ItemDefinition {
   boolean haveAnotherBlock;
    float zuni;
    boolean water;
    boolean trough;
    boolean isDoor;

    public void receive(Map map ){
        super.receive(map);
        ItemBlockParser.parse(this, map);
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
        CoreRegistry.get(Client.class).send(cmd);

    }

}
