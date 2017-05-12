package cola.machine.game.myblocks.world.block;

import cola.machine.game.myblocks.block.Block;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.world.WorldRendererLwjgl;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/5/6.
 */
public class TreeBlock {
   // Block startBlock;
    public GL_Vector startPosition;
   // Seed seed;
    int rodMaxHeight = 5;
    //WorldRendererLwjgl world;
Client client ;
    public  TreeBlock(GL_Vector startPosition){
        this.startPosition = startPosition;
        this.client =CoreRegistry.get(Client.class);

    }
    public void createBlock(float x,float y,float z,ItemType itemType){
        int chunkX = MathUtil.getBelongChunkInt(x);
        int chunkZ = MathUtil.getBelongChunkInt(z);
        int blockX = MathUtil.floor(x) - chunkX * 16;
        int blockY = MathUtil.floor(y);
        int blockZ = MathUtil.floor(z) - chunkZ * 16;
        ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
        cmd.cx = blockX;
        cmd.cz = blockZ;
        cmd.cy = blockY;
LogUtil.println("tree block :"+cmd.cx+"y:"+cmd.cy+"z:"+cmd.cz);
        if(cmd.cy<0){
            LogUtil.err("y can't be <0 ");
        }
        cmd.type = 1;
        //blockType 应该和IteType类型联系起来
        cmd.blockType = itemType.ordinal();

        client.send(cmd);
    }
    //public void
    public void generator(){


        //row growth
        //先来一颗棒棒糖树 树干是笔直的 树叶是正方体
        for(int i=1;i<rodMaxHeight;i++){
           // GL_Vector newPosition = new GL_Vector(startPosition.x,startPosition.y+i,startPosition.z);
            createBlock(startPosition.x,startPosition.y+i,startPosition.z,ItemType.tree_wood);

        }
        int width =5;
        for(int i=-width/2;i<=width/2;i++){
            for(int j=0;j<width;j++){
                for(int k=-width/2;k<=width/2;k++){
                    //GL_Vector newPosition = new GL_Vector(startBlock.getPosition().x+i,startBlock.getPosition().y+5+j,startBlock.getPosition().z+k);
                   // world.setBlock(newPosition,ItemType.tree_leaf_block);
                    createBlock(startPosition.x+i,startPosition.y+5+j,startPosition.z+k,ItemType.tree_leaf);
                }
            }
        }

//        for(int i=0;i<rodMaxHeight;i++){
//            GL_Vector newPosition = new GL_Vector(startBlock.getPosition().x,startBlock.getPosition().y+1,startBlock.getPosition().z)
//            if(world.hasBlock(newPosition)){
//                break;
//            }
//            if(i>rodMinHeight){
//                //grow branch
//                //随机的 x x>=-1 <=1 z<=1 z>=-1 y>=-1 y<=1
//                //
//
//            }
//        }
        //while(true){
            //获取随机的数目高度
            //每个树的块都有一个父块 就像一个链表一样,
            //我有一个基块, 这个基块一开始是要网上生长的 ,生长到制定高度 就会斜角生长出枝干, 枝干会长出叶子
            //a grow and add one block on top and check the height and grow when arrive the height then grow branch
            //then branch has it's direction and has max length  and main rod grow arrive the max hegith then stop grow
            //then grow leaf
        //}
    }
}
