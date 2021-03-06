package check;

import cola.machine.game.myblocks.world.chunks.Chunk;

import java.util.List;

/**
 * Created by luying on 14-10-2.
 */
public class CrashCheck {
    //Human player;
    List<Chunk> chunksInProximity;
    public CrashCheck(/*Human player,*/List<Chunk> chunksInProximity){
       // this.player=player;
        this.chunksInProximity=chunksInProximity;
    }
    int blockX= 0;
    int blockY= 0;
    int blockZ= 0;

    /**
     * 返回true 表示有碰撞
     * @param livingThing
     * @return
     */
   /* public boolean  check(LivingThingBean livingThing){

        float plr_pos_x=livingThing.position.x;
        float plr_pos_y=livingThing.position.y;
        float plr_pos_z=livingThing.position.z;
        for (float offset_x= -0.3f; offset_x < 0.4; offset_x+=0.3) {//这个人的碰撞宽度

            for (float offset_z = -0.3f; offset_z < 0.4; offset_z+=0.3) {//这个人的碰撞体积厚度
                Chunk chunk_corner = null;
                int chunk_pos_x_16 = MathUtil.getBelongChunkInt(offset_x + plr_pos_x);
                int chunk_pos_z_16 = MathUtil.getBelongChunkInt(offset_z+plr_pos_z);
                for (int i = 0; i < chunksInProximity.size(); i++) {
                    Chunk chunk_temp = chunksInProximity.get(i);
                    if (chunk_temp.getPos().x == chunk_pos_x_16 &&//0
                            chunk_temp.getPos().z == chunk_pos_z_16) {//0
                        chunk_corner = chunk_temp;break;
                    } else {
                        continue;
                    }
                }
                if (chunk_corner == null) {
                   // LogUtil.err("may be the chunk_corner haven't been initialized the chunk_corner can't be null please debug it");
                    return false;
                }

                for (int offset_y = 0; offset_y <1.5; offset_y += 2) {

                    //get chunk from near
//                    int chunk_x= MathUtil.getNearOdd(x);
                    blockX = MathUtil.getOffesetChunk(offset_x+plr_pos_x);
                    blockY = (int) (offset_y+plr_pos_y);
                    blockZ = MathUtil.getOffesetChunk(offset_z+plr_pos_z);
                    int k = chunk_corner.getBlockData(blockX,
                            blockY, blockZ
                    );

                    if (k > 0) {
                        Block block =chunk_corner.getBlock(blockX,
                                blockY, blockZ);
                        if (!block.isPenetrate()){
                            //是否碰撞是不是应该交给对应的block 去判断
                            *//*if (BlockUtil.isDoor(k) && BlockUtil.isDoorOpen(k)) {

                            } else {
                                return true;
                            }*//*
                            return true;
                            //means it is crashed
//                        System.out.println("warning crashed");
//                        player.moveOld();

//                            GL_Vector.add(player.Position, GL_Vector.multiply(
//                                    player.Position.sub(
//                                            new GL_Vector(blockX, blockY, blockZ)),
//                                    0.2f))

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
        }
        //if all block checked is air then needjdegecrash is false
    return false;
    }*/

    /**
     *
     * @param position
     * @return true表示有碰撞
     */
   /* public boolean  check(GL_Vector position){

        float plr_pos_x=position.x;
        float plr_pos_y=position.y;
        float plr_pos_z=position.z;
        for (float offset_x= -0.3f; offset_x < 0.4; offset_x+=0.3) {//这个人的碰撞宽度

            for (float offset_z = -0.3f; offset_z < 0.4; offset_z+=0.3) {//这个人的碰撞体积厚度
                Chunk chunk_corner = null;
                int chunk_pos_x_16 = MathUtil.getBelongChunkInt(offset_x + plr_pos_x);
                int chunk_pos_z_16 = MathUtil.getBelongChunkInt(offset_z+plr_pos_z);
                for (int i = 0; i < chunksInProximity.size(); i++) {
                    Chunk chunk_temp = chunksInProximity.get(i);
                    if (chunk_temp.getPos().x == chunk_pos_x_16 &&//0
                            chunk_temp.getPos().z == chunk_pos_z_16) {//0
                        chunk_corner = chunk_temp;break;
                    } else {
                        continue;
                    }
                }
                if (chunk_corner == null) {
                    // LogUtil.err("may be the chunk_corner haven't been initialized the chunk_corner can't be null please debug it");
                    return false;
                }

                for (int offset_y = 0; offset_y <1.5; offset_y += 2) {

                    //get chunk from near
//                    int chunk_x= MathUtil.getNearOdd(x);
                    blockX = MathUtil.getOffesetChunk(offset_x+plr_pos_x);
                    blockY = (int) (offset_y+plr_pos_y);
                    blockZ = MathUtil.getOffesetChunk(offset_z+plr_pos_z);
                    int k = chunk_corner.getBlockData(blockX,
                            blockY, blockZ
                    );

                    if (k > 0) {
                        Block block =chunk_corner.getBlock(blockX,
                                blockY, blockZ);
                        if (!block.isPenetrate()){
                            //是否碰撞是不是应该交给对应的block 去判断
                            *//*if (BlockUtil.isDoor(k) && BlockUtil.isDoorOpen(k)) {

                            } else {
                                return true;
                            }*//*
                            return true;
                            //means it is crashed
//                        System.out.println("warning crashed");
//                        player.moveOld();

//                            GL_Vector.add(player.Position, GL_Vector.multiply(
//                                    player.Position.sub(
//                                            new GL_Vector(blockX, blockY, blockZ)),
//                                    0.2f))

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
        }
        //if all block checked is air then needjdegecrash is false
        return false;
    }*/
    /*public boolean haveBlock2(LivingThingBean livingThing){//检测当前人的宽度和厚度 还有高度带入公式用来判断是否有碰撞
        boolean isswim=false;
        float plr_pos_x=livingThing.position.x;
        float plr_pos_y=livingThing.position.y;
        float plr_pos_z=livingThing.position.z;
        for (float offset_x= -0.3f; offset_x < 0.4; offset_x+=0.3) {

            for (float offset_z = -0.3f; offset_z < 0.4; offset_z+=0.3) {//厚度的大小
                Chunk chunk_corner = null;
                int chunk_pos_x_16 = MathUtil.getBelongChunkInt(offset_x + plr_pos_x);
                int chunk_pos_z_16 = MathUtil.getBelongChunkInt(offset_z+plr_pos_z);
                for (int i = 0; i < chunksInProximity.size(); i++) {
                    Chunk chunk_temp = chunksInProximity.get(i);
                    if (chunk_temp.getPos().x == chunk_pos_x_16 &&
                            chunk_temp.getPos().z == chunk_pos_z_16) {
                        chunk_corner = chunk_temp;break;
                    } else {
                        continue;
                    }
                }
                if (chunk_corner == null) {
                    //livingThing.disapper();
                  //  GLApp.msg("the chunk_corner can't be null please debug it");
                    //System.exit(0);
                    return false;
                }

                for (int offset_y = -1; offset_y <0; offset_y += 2) {

                    //get chunk from near
//                    int chunk_x= MathUtil.getNearOdd(x);
                    blockX = MathUtil.getOffesetChunk(offset_x+plr_pos_x);
                    blockY = (int) (offset_y+plr_pos_y);
                    blockZ = MathUtil.getOffesetChunk(offset_z+plr_pos_z);
                    try {
                        if(blockY<0){
                            return true;
                        }
                        int k = chunk_corner.getBlockData(blockX,
                                blockY, blockZ
                        );

                        if(k>0){

                            Block block =chunk_corner.getBlock(blockX,
                                    blockY, blockZ);
                            if (!block.isPenetrate()){
                                return true;
                            }
                        }

//                        if (k==8 && !isswim ){
//                            // CoreRegistry.get(Human.class).swim=true;
//                            // System.out.println("swim");
//                        }else{
//                            // CoreRegistry.get(Human.class).swim=false;
//                            // System.out.println("unswim");
//                        }
//                        if (k > 0&& k!=8) {//不为水的话
//
//                            if(BlockUtil.isDoor(k) &&BlockUtil.isDoorOpen(k)){
//
//                            }else{
//                                return true;
//                            }
//                            //means it is crashed
////                        System.out.println("warning crashed");
////                        player.moveOld();
//
////                            GL_Vector.add(player.Position, GL_Vector.multiply(
////                                    player.Position.sub(
////                                            new GL_Vector(blockX, blockY, blockZ)),
////                                    0.2f))
//
//                            //end all loop
//                            //但是不一定是碰撞检测结束了
//                            //如果所有的corner都没有碰撞到物体
//                            //碰撞检测结束了
//                            //it means it's over it's stable
//                            //how we be sure it's stable
//                            //when all corner is checked not crashed
//                            //fan fangxiang yundnog 1 xiaoduan juli
//
//                        }
                    }catch(Exception e){
                        LogUtil.err(e);
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
            Chunk chunk_temp = chunksInProximity.get(i);
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
    }*/
}
