package check;

import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import glapp.GLApp;
import util.MathUtil;

import java.util.List;

/**
 * Created by luying on 14-10-2.
 */
public class CrashCheck {
    Human player;
    List<ChunkImpl> chunksInProximity;
    public CrashCheck(Human player,List<ChunkImpl> chunksInProximity){
        this.player=player;
        this.chunksInProximity=chunksInProximity;
    }
    int blockX= 0;
    int blockY= 0;
    int blockZ= 0;
    public boolean check(){

        float plr_pos_x=player.Position.x;
        float plr_pos_y=player.Position.y;
        float plr_pos_z=player.Position.z;
        for (float offset_x= -0.3f; offset_x < 0.4; offset_x+=0.3) {//这个人的碰撞宽度

            for (float offset_z = -0.3f; offset_z < 0.4; offset_z+=0.3) {//这个人的碰撞体积厚度
                ChunkImpl chunk_corner = null;
                int chunk_pos_x_16 = MathUtil.getBelongChunkInt(offset_x + plr_pos_x);
                int chunk_pos_z_16 = MathUtil.getBelongChunkInt(offset_z+plr_pos_z);
                for (int i = 0; i < chunksInProximity.size(); i++) {
                    ChunkImpl chunk_temp = chunksInProximity.get(i);
                    if (chunk_temp.getPos().x == chunk_pos_x_16 &&//0
                            chunk_temp.getPos().z == chunk_pos_z_16) {//0
                        chunk_corner = chunk_temp;
                    } else {
                        continue;
                    }
                }
                if (chunk_corner == null) {
                    GLApp.msg("the chunk_corner can't be null please debug it");
                    System.exit(0);
                }

                for (int offset_y = 0; offset_y <= 2; offset_y += 2) {

                    //get chunk from near
//                    int chunk_x= MathUtil.getNearOdd(x);
                    blockX = MathUtil.getOffesetChunk(offset_x+plr_pos_x);
                    blockY = (int) (offset_y+plr_pos_y);
                    blockZ = MathUtil.getOffesetChunk(offset_z+plr_pos_z);
                    int k = chunk_corner.getBlockData(blockX,
                            blockY, blockZ
                    );
                    if (k > 0) {
                        //means it is crashed
//                        System.out.println("warning crashed");
//                        player.moveOld();

//                            GL_Vector.add(player.Position, GL_Vector.multiply(
//                                    player.Position.sub(
//                                            new GL_Vector(blockX, blockY, blockZ)),
//                                    0.2f))
                        return true;
                        //end all loop
                        //但是不一定是碰撞检测结束了
                        //如果所有的corner都没有碰撞到物体
                        //碰撞检测结束了
                        //it means it's over it's stable
                        //how we be sure it's stable
                        //when all corner is checked not crashed
                        //fan fangxiang yundnog 1 xiaoduan juli

                    }

                }
            }
        }
        //if all block checked is air then needjdegecrash is false
    return false;
    }
    public boolean haveBlock2(){//检测当前人的宽度和厚度 还有高度带入公式用来判断是否有碰撞
        boolean isswim=false;
        float plr_pos_x=player.Position.x;
        float plr_pos_y=player.Position.y;
        float plr_pos_z=player.Position.z;
        for (float offset_x= -0.3f; offset_x < 0.4; offset_x+=0.3) {

            for (float offset_z = -0.3f; offset_z < 0.4; offset_z+=0.3) {//厚度的大小
                ChunkImpl chunk_corner = null;
                int chunk_pos_x_16 = MathUtil.getBelongChunkInt(offset_x + plr_pos_x);
                int chunk_pos_z_16 = MathUtil.getBelongChunkInt(offset_z+plr_pos_z);
                for (int i = 0; i < chunksInProximity.size(); i++) {
                    ChunkImpl chunk_temp = chunksInProximity.get(i);
                    if (chunk_temp.getPos().x == chunk_pos_x_16 &&
                            chunk_temp.getPos().z == chunk_pos_z_16) {
                        chunk_corner = chunk_temp;
                    } else {
                        continue;
                    }
                }
                if (chunk_corner == null) {
                    GLApp.msg("the chunk_corner can't be null please debug it");
                    System.exit(0);
                }

                for (int offset_y = -1; offset_y <0; offset_y += 2) {

                    //get chunk from near
//                    int chunk_x= MathUtil.getNearOdd(x);
                    blockX = MathUtil.getOffesetChunk(offset_x+plr_pos_x);
                    blockY = (int) (offset_y+plr_pos_y);
                    blockZ = MathUtil.getOffesetChunk(offset_z+plr_pos_z);
                    int k = chunk_corner.getBlockData(blockX,
                            blockY, blockZ
                    );


                    if (k==8 && !isswim ){
                      // CoreRegistry.get(Human.class).swim=true;
                      // System.out.println("swim");
                    }else{
                       // CoreRegistry.get(Human.class).swim=false;
                       // System.out.println("unswim");
                    }
                    if (k > 0&& k!=8) {//不为水的话
                        //means it is crashed
//                        System.out.println("warning crashed");
//                        player.moveOld();

//                            GL_Vector.add(player.Position, GL_Vector.multiply(
//                                    player.Position.sub(
//                                            new GL_Vector(blockX, blockY, blockZ)),
//                                    0.2f))
                        return true;
                        //end all loop
                        //但是不一定是碰撞检测结束了
                        //如果所有的corner都没有碰撞到物体
                        //碰撞检测结束了
                        //it means it's over it's stable
                        //how we be sure it's stable
                        //when all corner is checked not crashed
                        //fan fangxiang yundnog 1 xiaoduan juli

                    }

                }
            }
        }
        //if all block checked is air then needjudgecrash is false
        return false;
    }
    public boolean haveBlock(float x,float y,float z){
        if(y<0)
            return true;
        int chunk_pos_x_16 = MathUtil.getBelongChunkInt(x);
        int chunk_pos_z_16 = MathUtil.getBelongChunkInt(z);
        for (int i = 0; i < chunksInProximity.size(); i++) {
            ChunkImpl chunk_temp = chunksInProximity.get(i);
            if (chunk_temp.getPos().x == chunk_pos_x_16 &&
                    chunk_temp.getPos().z == chunk_pos_z_16) {
                blockX = MathUtil.getOffesetChunk(x);
                blockY = (int) (y);
                blockZ = MathUtil.getOffesetChunk(z);
                int k = chunk_temp.getBlockData(blockX,
                        blockY, blockZ
                );
                if (k > 0) {
                    return true;
                }else{
                    return false;
                }
            } else {
                continue;
            }
        }
        return false;
    }
}
