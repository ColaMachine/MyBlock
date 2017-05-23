package cola.machine.game.myblocks.physic;

import cola.machine.game.myblocks.world.chunks.Chunk;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;

import cola.machine.game.myblocks.math.Vector3i;
public class BulletPhysics  {

	//public BlockRepository blockRepository=null;

    public BulletPhysics(/*BlockRepository blockRepository*/) {
    //	this.blockRepository=blockRepository;
    	
    }

   

    public GL_Vector[] rayTrace(GL_Vector from, GL_Vector direction, float distance,String blockname,boolean delete) {
    	ChunkProvider localChunkProvider= CoreRegistry.get(ChunkProvider.class);
    	GL_Vector to = new GL_Vector(direction);
        
        //������յ�λ��

    	//ÿ���ƶ�0.1
    	int preBlockIndex=-1;
    	int blockIndex=0;
        Chunk bianliChunk=null;
        int chunkX;
        int chunkY;
        int chunkZ;
       // int preBlockIndex =0;
    	for(float x=0.1f;x<distance;x+=0.1){//一点一点推进 看撞到了哪个物体 every time add a little
    		int _x = MathUtil.floor(from.x+x*to.x);//x 代表推进的举例 推进后的x
    		int _y = MathUtil.floor(from.y+x*to.y);//推进后的y
            if(_y<0){
                return null;//说明已经穿透地板了
            }
    		int _z = MathUtil.floor(from.z+x*to.z);//推进后的z
            blockIndex=_x*10000+_z*100+_y;//推进后落入的blockINdex
    		if(blockIndex==preBlockIndex)//如果和之前判断的还是同一个的话 略过
    			continue;
    		//ȥ����Ƿ���һ������֮��
    		//ChunkImpl chunk = localChunkProvider.getChunk(new Vector3i(MathUtil.floor((float)_x/16),0,MathUtil.floor((float)_z/16)));
    		 chunkX=MathUtil.getBelongChunkInt(from.x+x*to.x);//换算出新的chunkX
    		 chunkZ=MathUtil.getBelongChunkInt(from.z+x*to.z);//换算出新的chunkZ

            int  nowChunkIndex =  chunkX*10000+chunkZ;
            if(nowChunkIndex!=preBlockIndex){//如果进入到新的方格子空间
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
                int blockType = bianliChunk.getBlockData(MathUtil.getOffesetChunk(_x),MathUtil.floor( _y), MathUtil.getOffesetChunk(_z));
    		if(blockType>0){
                GL_Vector[] arr = new GL_Vector[3];
                if(delete){//如果是删除 就删除对应的方块
    				//Block block=new BaseBlock("water",0,false);
                   // bianliChunk.setBlock(MathUtil.getOffesetChunk(_x),MathUtil.floor( _y), MathUtil.getOffesetChunk(_z),block);
                    //((ChunkImpl)bianliChunk).build();
                   // ((ChunkImpl)bianliChunk).buildAlpha();
                    //bianliChunk.build();
    				//return null;
                    if(from.y+x*to.y<0){
                        LogUtil.err("y can't be <0 ");
                    }
                   /* arr[0] = new GL_Vector(MathUtil.floor(from.x+x*to.x),
                            MathUtil.floor(from.y+x*to.y),
                            MathUtil.floor(from.z+x*to.z));*/
                    //return ;
    			}
                arr[0] = new GL_Vector(MathUtil.floor(from.x+x*to.x),
                        MathUtil.floor(from.y+x*to.y),
                        MathUtil.floor(from.z+x*to.z));
    			x-=0.1;//退回来 如果是增加放置一个block的话
    			 _x = MathUtil.floor(from.x+x*to.x);
        		 _y = MathUtil.floor(from.y+x*to.y);
        		 _z = MathUtil.floor(from.z+x*to.z);
        		 chunkX =  MathUtil.floor(((float)_x)/16);
                chunkZ =  MathUtil.floor(((float)_z)/16);
    			BlockManager blockManager = CoreRegistry.get(BlockManager.class);//创建新的方块
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

                LogUtil.println("x:"+(from.x+x*to.x)%1 + "y:"+(from.y+x*to.y)%1+"z:"+(from.z+x*to.z)%1);

                arr[1] =  new GL_Vector(from.x+x*to.x,
                        from.y+x*to.y,
                        from.z+x*to.z);//����ײ��ǰ�ķ���λ��
                arr[2] =new GL_Vector(blockType,0,0);
                return arr;
        		
    		}
                preBlockIndex=blockIndex;
    	}}
    	return null;
    	
    }
   
   

}
