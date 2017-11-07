package com.dozenx.game.engine;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.MathUtil;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

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
            GamingState.setCameraChanged(true);
            if (livingThingBean.position.y <= livingThingBean.valleyBottom) {
                //
                //System.out.println("��ǰ��y" + mark);
                livingThingBean.position.y = livingThingBean.valleyBottom;
                //livingThingBean.setStable( true);// return to the normal state
                if(this.hasSomeThingUnderFoot(livingThingBean)){
                    standFirm(livingThingBean);
                }else{
                    livingThingBean.valleyBottom-=1;
                }
                /*
                this.checkIsDrop(livingThingBean);
                if(livingThingBean.isStable()){
                    livingThingBean.valleyBottom = 0 ;
                    livingThingBean.jumpStartY = 0;
                }*/


            }

            this.hasSomeThingUnderFoot(livingThingBean);
        }

    }
    //when stable check wether is drop
    public void checkIsDrop(LivingThingBean livingThing){//当从一个格子移动到另外一个格子的时候触发

        //=======first we check is it's wrong to -1 ===========

        // ȡ���������� ż�����

        //ȡ�����������������

        if(livingThing.position.y<0){//触底了
            livingThing.position.y=0f;
            livingThing.setStable(true);
        }
        //========== when he is dropping we cauculate  where he will stand on======
        if(!livingThing.isStable()){//如果再掉落状态的话 更新valleyBottom
            //System.out.println("��ǰ�����y:"+human.Position.y);
//			int x = MathUtil.getNearOdd(human.Position.x );
//			int y = MathUtil.getNearOdd(human.Position.y);
//			int z = MathUtil.getNearOdd(human.Position.z );
            //========= in  this y he will be stand firm
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
        //======== when he is stable we want to know whether   he will drop or not ========
        }else if(livingThing.isStable()){

            /*if(this.collision(livingThing)){
                livingThing.position.y+=2;//找到最近的地方让他安顿;
                return;
            }*/
//			int x = MathUtil.getNearOdd(human.Position.x );
//			int y = MathUtil.getNearOdd(human.Position.y);
//			int z = MathUtil.getNearOdd(human.Position.z );
            //=============if the y is not a integer  like 1.7 he will drop to 1.0~1.1 =========
            if(livingThing.position.y%1>0.1){
                livingThing.drop();
                livingThing.valleyBottom=(int)Math.floor(livingThing.position.y);
            }else
            if(!hasSomeThingUnderFoot(livingThing)){
                //=========== in the left chance it may be have no block under foot then he drop =========
                //System.out.println("check the human hasn't under block  begin to drop");
                //System.out.println("��ǰ�����y:"+human.Position.y+"��⵽����:"+y);
                livingThing.drop();
                livingThing.valleyBottom=(int)livingThing.position.y-1;
                //System.out.println("mark :"+human.mark);
            }
        }


       /* if(hasSomeThingUnderFoot(livingThing)){

            livingThing.setStable(true);
        }else{
            livingThing.setStable(false);
        }*/
    }
    //

    int blockX,blockY,blockZ;
    public boolean hasSomeThingUnderFoot(LivingThingBean livingThing){
        boolean isswim=false;
        float plr_world_pos_x=livingThing.position.x;
        float plr_world_pos_y=livingThing.position.y;
        float plr_world_pos_z=livingThing.position.z;
        //
        float width = livingThing.getExecutor().getModel().getRootComponent().width;
        float thick=livingThing.getExecutor().getModel().getRootComponent().thick;
        float height = livingThing.getExecutor().getModel().getRootComponent().height;
        for (float offset_x= -width/2f; offset_x <width/2f; offset_x+=width/3f) {

            for (float offset_z = -thick/2f; offset_z <thick/2f; offset_z+=thick/3f) {//厚度的大小
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
                            if(GamingState.player==null){
                                return true;
                            }
                            IBlock block =chunk_corner.getBlock(blockX,
                                    blockY, blockZ);
                            if(block == null ){
                                LogUtil.err("err");
                                 block =chunk_corner.getBlock(blockX,
                                        blockY, blockZ);
                                return false;
                            }
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

    /**
     * 2017年10月16日12:15:43 进行碰撞后的反弹设置
     * @param livingThing
     * @return
     */
    public GL_Vector collision(LivingThingBean livingThing){//位置发生改变之后 当要发生位移的时候可以事先调用此方法 用来判断是否可以移动
        float plr_world_pos_x=livingThing.position.x;
        float plr_world_pos_y=livingThing.position.y;
        float plr_world_pos_z=livingThing.position.z;
        
        if(plr_world_pos_y<0){

            return new GL_Vector(0,1,0);
        }
        if(GamingState.player == null){
            int temp_chunk_pos_x_16 = MathUtil.getBelongChunkInt( plr_world_pos_x);
            int temp_chunk_pos_z_16 = MathUtil.getBelongChunkInt(plr_world_pos_z);
            Chunk chunk_corner  = chunkProvider.getChunk(temp_chunk_pos_x_16,0,temp_chunk_pos_z_16);
            
            int blockX = MathUtil.getOffesetChunk(plr_world_pos_x);
            int blockY = (int) (plr_world_pos_y);
            int blockZ = MathUtil.getOffesetChunk(plr_world_pos_z);
            int k = chunk_corner.getBlockData(blockX,
                    blockY, blockZ
            );
            

            if (k > 0) {
                return new GL_Vector(plr_world_pos_x%1-0.5f,plr_world_pos_y%1-0.5f,plr_world_pos_z%1-0.5f);
               // return true;
            }else{
                return null;
                //return false;
            }
            
        }
        float width = livingThing.getExecutor().getModel().getRootComponent().width;
        float thick=livingThing.getExecutor().getModel().getRootComponent().thick;
        float height = livingThing.getExecutor().getModel().getRootComponent().height;
        for (float offset_x= -width/2; offset_x <width/2; offset_x+=width/3f) {

            for (float offset_z = -thick/2; offset_z <thick/2; offset_z+=thick/3f) {//厚度的大小
                Chunk chunk_corner = null;
                int temp_chunk_pos_x_16 = MathUtil.getBelongChunkInt(offset_x + plr_world_pos_x);
                int temp_chunk_pos_z_16 = MathUtil.getBelongChunkInt(offset_z+plr_world_pos_z);


                chunk_corner = chunkProvider.getChunk(temp_chunk_pos_x_16,0,temp_chunk_pos_z_16);


                if (chunk_corner == null) {
                    // LogUtil.err("may be the chunk_corner haven't been initialized the chunk_corner can't be null please debug it");
                    return null;
                }

                for (float offset_y = 0; offset_y <height; offset_y += height/3f) {

                    //get chunk from near
//                    int chunk_x= MathUtil.getNearOdd(x);
                    blockX = MathUtil.getOffesetChunk(offset_x+plr_world_pos_x);
                    blockY = (int) (offset_y+plr_world_pos_y);
                    blockZ = MathUtil.getOffesetChunk(offset_z+plr_world_pos_z);
                    int k = chunk_corner.getBlockData(blockX,
                            blockY, blockZ
                    );

                    if (k > 0) {
                        if(GamingState.player==null){
                            return new GL_Vector((offset_x+plr_world_pos_x)%1-0.5f,(offset_y+plr_world_pos_y)%1-0.5f,(offset_z+plr_world_pos_z)%1-0.5f);
                        }
                        IBlock block =chunk_corner.getBlock(blockX,
                                blockY, blockZ);
                        if(block == null ){
                            continue;
                        }
                        if (!block.isPenetrate()){
                            return new GL_Vector((offset_x+plr_world_pos_x)%1-0.5f,(offset_y+plr_world_pos_y)%1-0.5f,(offset_z+plr_world_pos_z)%1-0.5f);
                           // return true;


                        }
                    }

                }
            }
        }
        //if all block checked is air then needjdegecrash is false
        return null;

    }


    public void standFirm(LivingThingBean livingThingBean){
        livingThingBean.setStable(true);
        livingThingBean.valleyBottom=0;
        livingThingBean.jumpStartY=0;
        livingThingBean.jumpTime=0;
    }
}
