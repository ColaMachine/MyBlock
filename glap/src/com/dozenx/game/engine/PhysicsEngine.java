package com.dozenx.game.engine;

import check.CrashCheck;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.MathUtil;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;

/**
 * Created by luying on 17/7/8.
 */
public class PhysicsEngine {
    public PhysicsEngine(){
        CoreRegistry.put(PhysicsEngine.class,this);
        chunkProvider = CoreRegistry.get(ChunkProvider.class);
    }
    private final static float g = 19.6f; //重力加速度
    ChunkProvider chunkProvider = null;
    //when drop state happened
    public void gravitation(LivingThingBean livingThingBean){//每隔200ms 触发一次
        //对所有的生物进行
        if(!livingThingBean .isStable() ){ // when drop happened to the livingthing
            long t = TimeUtil.getNowMills() - livingThingBean.jumpTime;//�˶���ʱ��
            float yDistance = livingThingBean.jumpSpeed * t / 1000 - 0.5f * (this.g) * t * t / 1000000;//�˶��ľ���

            livingThingBean.position.y = livingThingBean.jumpStartY + yDistance;//now position ��Ӧy��䶯

            if (livingThingBean.position.y <= livingThingBean.valleyBottom) {
                //
                //System.out.println("��ǰ��y" + mark);
                livingThingBean.position.y = livingThingBean.valleyBottom;
                livingThingBean.setStable( true);// return to the normal state
                livingThingBean.valleyBottom = 0 ;
                livingThingBean.jumpStartY = 0;
            }

            this.hasSomeThingUnderFoot(livingThingBean);
        }

    }
    //when stable check wether is drop
    public void checkIsDrop(LivingThingBean livingThing){//当从一个格子移动到另外一个格子的时候触发



        // ȡ���������� ż�����

        //ȡ�����������������

        if(livingThing.position.y<=0){//触底了
            livingThing.position.y=0f;
            livingThing.setStable(true);
        }

        if(!livingThing.isStable()){//如果再掉落状态的话 更新valleyBottom
            //System.out.println("��ǰ�����y:"+human.Position.y);
//			int x = MathUtil.getNearOdd(human.Position.x );
//			int y = MathUtil.getNearOdd(human.Position.y);
//			int z = MathUtil.getNearOdd(human.Position.z );

            if(this.hasSomeThingUnderFoot(livingThing)){
                //System.out.println("�ǵ�����"+y);
                //System.out.println("��ǰ�����y:"+human.Position.y+"��⵽����:"+y);
                livingThing.valleyBottom=(int)livingThing.position.y;
                if(livingThing.valleyBottom<=0){
                    System.out.println("get the underground");
                }
                //System.out.println("mark :"+human.mark);
            }else{
                livingThing.valleyBottom=(int)livingThing.position.y-1;
            }

        }else if(livingThing.isStable()){

            if(this.collision(livingThing)){
                livingThing.position.y+=2;//找到最近的地方让他安顿;
                return;
            }
//			int x = MathUtil.getNearOdd(human.Position.x );
//			int y = MathUtil.getNearOdd(human.Position.y);
//			int z = MathUtil.getNearOdd(human.Position.z );
            if(!hasSomeThingUnderFoot(livingThing)){
                //System.out.println("check the human hasn't under block  begin to drop");
                //System.out.println("��ǰ�����y:"+human.Position.y+"��⵽����:"+y);
                livingThing.drop();
                livingThing.valleyBottom=(int)livingThing.position.y-1;
                //System.out.println("mark :"+human.mark);
            }
        }


        if(hasSomeThingUnderFoot(livingThing)){
            livingThing.setStable(true);
        }else{
            livingThing.setStable(false);
        }
    }
    //

    int blockX,blockY,blockZ;
    public boolean hasSomeThingUnderFoot(LivingThingBean livingThing){
        boolean isswim=false;
        float plr_world_pos_x=livingThing.position.x;
        float plr_world_pos_y=livingThing.position.y;
        float plr_world_pos_z=livingThing.position.z;

        for (float offset_x= -0.3f; offset_x < 0.4; offset_x+=0.3) {

            for (float offset_z = -0.3f; offset_z < 0.4; offset_z+=0.3) {//厚度的大小
                Chunk chunk_corner = null;
                int temp_chunk_pos_x_16 = MathUtil.getBelongChunkInt(offset_x + plr_world_pos_x);
                int temp_chunk_pos_z_16 = MathUtil.getBelongChunkInt(offset_z+plr_world_pos_z);

                 chunk_corner = chunkProvider.getChunk(temp_chunk_pos_x_16,0,temp_chunk_pos_z_16);

                if (chunk_corner == null) {

                    return false;
                }

              int offset_y = -1;

                    //get chunk from near
//                    int chunk_x= MathUtil.getNearOdd(x);
                    blockX = MathUtil.getOffesetChunk(offset_x+plr_world_pos_x);
                    blockY = (int) (offset_y+plr_world_pos_y);
                    blockZ = MathUtil.getOffesetChunk(offset_z+plr_world_pos_z);
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
                                livingThing.valleyBottom = blockY+1;
                                return true;
                            }
                        }

//
                    }catch(Exception e){
                        LogUtil.err(e);
                    }



            }
        }
        //if all block checked is air then needjudgecrash is false
        return false;
    }
    public boolean collision(LivingThingBean livingThing){//位置发生改变之后 当要发生位移的时候可以事先调用此方法 用来判断是否可以移动
        float plr_world_pos_x=livingThing.position.x;
        float plr_world_pos_y=livingThing.position.y;
        float plr_world_pos_z=livingThing.position.z;


        for (float offset_x= -0.3f; offset_x < 0.4; offset_x+=0.3) {//这个人的碰撞宽度

            for (float offset_z = -0.3f; offset_z < 0.4; offset_z+=0.3) {//这个人的碰撞体积厚度
                Chunk chunk_corner = null;
                int temp_chunk_pos_x_16 = MathUtil.getBelongChunkInt(offset_x + plr_world_pos_x);
                int temp_chunk_pos_z_16 = MathUtil.getBelongChunkInt(offset_z+plr_world_pos_y);


                chunk_corner = chunkProvider.getChunk(temp_chunk_pos_x_16,0,temp_chunk_pos_z_16);


                if (chunk_corner == null) {
                    // LogUtil.err("may be the chunk_corner haven't been initialized the chunk_corner can't be null please debug it");
                    return false;
                }

                for (int offset_y = 0; offset_y <1.5; offset_y += 1.3) {

                    //get chunk from near
//                    int chunk_x= MathUtil.getNearOdd(x);
                    blockX = MathUtil.getOffesetChunk(offset_x+plr_world_pos_x);
                    blockY = (int) (offset_y+plr_world_pos_y);
                    blockZ = MathUtil.getOffesetChunk(offset_z+plr_world_pos_z);
                    int k = chunk_corner.getBlockData(blockX,
                            blockY, blockZ
                    );

                    if (k > 0) {
                        Block block =chunk_corner.getBlock(blockX,
                                blockY, blockZ);
                        if (!block.isPenetrate()){

                            return true;


                        }
                    }

                }
            }
        }
        //if all block checked is air then needjdegecrash is false
        return false;

    }

}
