package cola.machine.game.myblocks.physic;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;
public class BulletPhysics  {

	//public BlockRepository blockRepository=null;

    public BulletPhysics(/*BlockRepository blockRepository*/) {
    //	this.blockRepository=blockRepository;
    	
    }


	/**
	 * 点击选择探测器
	 * @param from
	 * @param direction
	 * @param distance
	 * @param blockname
	 * @param delete
     * @return 3个Gl_vector 第一个是触碰到的block 位置 第二个是触碰路径上的最后一个空block位置 第三个第一个x分量是block的类型
     */
    //名字要改一下 改成选择方块
    public BulletResultDTO rayTrace(GL_Vector from, GL_Vector direction, float distance,String blockname,boolean delete) {
    	ChunkProvider localChunkProvider= CoreRegistry.get(ChunkProvider.class);
    	GL_Vector to = new GL_Vector(direction);
        
        //������յ�λ��

    	//ÿ���ƶ�0.1 init  variable
    	int preBlockIndex=-1;
        int chunkIndex=-1;
        Integer preChunkIndex=null;  //if preChunkIndex != chunkIndex bianlichunk will to find it's new chunk and also use to find the place block
    	int blockIndex=0;
        Chunk bianliChunk=null;//need to be initialize
        int chunkX,preChunkX,chunkZ,preChunkZ;
        //int chunkY;
        float nowX,nowY,nowZ,preX,preY,preZ;
        nowX=nowY=nowZ=preX=preY=preZ=0;
        int worldX,worldY,worldZ ,preWorldX,preWorldY,preWorldZ;
        int offsetX ,offsetZ ,preOffsetX,preOffsetZ;
       // int preBlockIndex =0; every time push a little
    	for(float x=0.1f;x<distance;x+=0.1){//一点一点推进 看撞到了哪个物体 every time add a little
    		 nowX = from.x+x*to.x;
             nowY = from.y+x*to.y;
             nowZ = from.z+x*to.z;


            worldX = MathUtil.floor(nowX);//x 代表推进的举例 推进后的x
            worldY = MathUtil.floor(nowY);//推进后的y
            if(worldY<0){
                BulletResultDTO arr = new BulletResultDTO();
               // arr.targetBlock= targetBlock;

                arr.placePoint =  new GL_Vector(preX,
                        preY,
                        preZ);//����ײ��ǰ�ķ���λ��
                return arr;//说明已经穿透地板了
            }
            worldZ = MathUtil.floor(nowZ);//推进后的z

            offsetX = MathUtil.getOffesetChunk(worldX);
            offsetZ = MathUtil.getOffesetChunk(worldZ);
            blockIndex=offsetX*10000+offsetZ*100+worldY;//推进后落入的blockINdex
            if(preBlockIndex==-1){
                preBlockIndex = blockIndex;
            }
            chunkX=MathUtil.getBelongChunkInt(nowX);//换算出新的chunkX
            chunkZ=MathUtil.getBelongChunkInt(nowZ);//换算出新的chunkZ
            chunkIndex =  chunkX*10000+chunkZ;
            if(blockIndex == preBlockIndex){
                continue;
            }
    		if(blockIndex!=preBlockIndex)//如果和之前判断的还是同一个的话 略过
            {
                //pre
             /*   preBlockIndex = blockIndex;
                preChunkIndex = chunkIndex;
                preChunkX = chunkX;
                preChunkZ = chunkZ;
                preX=nowX;
                preY=nowY;
                preZ = nowZ;
                preOffsetX = offsetX;
                preOffsetZ = offsetZ;*/
                //int chunkY;


            }
    		//ȥ����Ƿ���һ������֮��
    		//ChunkImpl chunk = localChunkProvider.getChunk(new Vector3i(MathUtil.floor((float)_x/16),0,MathUtil.floor((float)_z/16)));
            if(preChunkIndex==null){
                bianliChunk = localChunkProvider.getChunk(new Vector3i(chunkX,0,chunkZ));//因为拉远距离了之后 会导致相机的位置其实是在很远的位置 改为只其实还没有chunk加载 所以最好是从任务的头顶开始出发
                preChunkIndex = chunkIndex;
            }

            //once enther a new chunk block then make sure if crash a realblock
            if( chunkIndex!=preChunkIndex){//如果进入到新的chunk方格子空间
                bianliChunk = localChunkProvider.getChunk(new Vector3i(chunkX,0,chunkZ));//因为拉远距离了之后 会导致相机的位置其实是在很远的位置 改为只其实还没有chunk加载 所以最好是从任务的头顶开始出发

            }
                // 得到当前chunk 改变的次数有限
    		//
    		
    		//int chunk_pos_x =  MathUtil.floor(((float)_x)/16);
    		//int chunk_pos_z =  MathUtil.floor(((float)_z)/16);
            //System.out.printf(" %d %d %d",_x,_y,_z);
    		//先获取chunk 在获取block
			//blockRepository.haveObject(_x,_y,_z)
			
			//detect if there is block
    		if(bianliChunk !=null){
                //Block targetBlock = bianliChunk.getBlock(offsetX,worldY,offsetZ);
                int blockType = bianliChunk.getBlockData(offsetX,worldY,offsetZ);
    		if(blockType>0){
                BulletResultDTO arr = new BulletResultDTO();
                if(delete){//如果是删除 就删除对应的方块
    				//Block block=new BaseBlock("water",0,false);
                   // bianliChunk.setBlock(MathUtil.getOffesetChunk(_x),MathUtil.floor( _y), MathUtil.getOffesetChunk(_z),block);
                    //((ChunkImpl)bianliChunk).build();
                   // ((ChunkImpl)bianliChunk).buildAlpha();
                    //bianliChunk.build();
    				//return null;
                    if(nowY<0){
                        LogUtil.err("y can't be <0 ");
                    }
                   /* arr[0] = new GL_Vector(MathUtil.floor(from.x+x*to.x),
                            MathUtil.floor(from.y+x*to.y),
                            MathUtil.floor(from.z+x*to.z));*/
                    //return ;
    			}
                IBlock targetBlock = bianliChunk.getBlock(offsetX,worldY,offsetZ);
                arr.targetChunX= chunkX;
                arr.targetChunZ= chunkZ;
                arr.targetPoint = new GL_Vector(offsetX,
                        MathUtil.floor(nowY),
                        offsetZ);
                arr.targetBlock= targetBlock;
    			//退回来 如果是增加放置一个block的话

    			//BlockManager blockManager = CoreRegistry.get(BlockManager.class);//创建新的方块
    			//Block block=new BaseBlock("water",blockManager.getBlock(blockname).getId(),false);
    			
    			//Chunk _chunk = localChunkProvider.getChunk(new Vector3i(chunkX,0,chunkZ));
    			//_chunk.setBlock(MathUtil.getOffesetChunk(_x),MathUtil.floor(_y), MathUtil.getOffesetChunk(_z), block);
               /* _chunk.setBlock(MathUtil.floor(from.x+x*to.x)%16,
    					MathUtil.floor(from.y+x*to.y),
    					MathUtil.floor(from.z+x*to.z)%16, block);*/
                //_chunk.disposeMesh();
                //_chunk.update();
    			//重新更新
    			//_chunk.build();
                //_chunk.buildAlpha();
                if(from.y+x*to.y<0){
                    LogUtil.err("y can't be <0 ");
                }

              //  LogUtil.println("x:"+(from.x+x*to.x)%1 + "y:"+(from.y+x*to.y)%1+"z:"+(from.z+x*to.z)%1);

                arr.placePoint =  new GL_Vector(preX,
                        preY,
                        preZ);//����ײ��ǰ�ķ���λ��
              //  arr[2] =new GL_Vector(blockType,0,0);
                return arr;
        		
    		}// if has no crash a real block then prex prey prez use to fallback
                preBlockIndex = blockIndex;
                preChunkIndex = chunkIndex;
                preChunkX = chunkX;
                preChunkZ = chunkZ;
                preX=nowX;
                preY=nowY;
                preZ = nowZ;
                preOffsetX = offsetX;
                preOffsetZ = offsetZ;
    	}
        }
    	return null;
    	
    }
   
   

}
