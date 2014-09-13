package cola.machine.game.myblocks.physic;

/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import util.MathUtil;
import glmodel.GL_Vector;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.repository.BlockRepository;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.LocalChunkProvider;
import cola.machine.game.myblocks.world.chunks.Vector3i;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;

/**
 * Physics engine implementation using TeraBullet (a customised version of JBullet)
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class BulletPhysics  {

	public BlockRepository blockRepository=null;

    public BulletPhysics(BlockRepository blockRepository) {
    	this.blockRepository=blockRepository;
    	
    }

   

    public GL_Vector rayTrace(GL_Vector from, GL_Vector direction, float distance,String blockname) {
    	ChunkProvider localChunkProvider= CoreRegistry.get(ChunkProvider.class);
    	GL_Vector to = new GL_Vector(direction);
        
        //������յ�λ��

    	//ÿ���ƶ�0.1
    	int preIndex=-1;
    	int index=0;
    	for(float x=0.1f;x<distance;x+=0.1){
    		int _x = MathUtil.floor(from.x+x*to.x);
    		int _y = MathUtil.floor(from.y+x*to.y);
    		int _z = MathUtil.floor(from.z+x*to.z);
    		index=_x*10000+_z*100+_y;
    		if(index==preIndex)
    			continue;
    		//ȥ����Ƿ���һ������֮��
    		//ChunkImpl chunk = localChunkProvider.getChunk(new Vector3i(MathUtil.floor((float)_x/16),0,MathUtil.floor((float)_z/16)));
    		ChunkImpl chunk = localChunkProvider.getChunk(new Vector3i(MathUtil.getBelongChunkInt(from.x+x*to.x),0,MathUtil.getBelongChunkInt(from.z+x*to.z)));
    		System.out.printf(" %d %d %d",_x,_y,_z);
    		//先获取chunk 在获取block
			//blockRepository.haveObject(_x,_y,_z)
			
			
    		if(chunk.getBlockData(MathUtil.getOffesetChunk(from.x+x*to.x),MathUtil.floor( _y), MathUtil.getOffesetChunk(from.z+x*to.z))>0){
    			x-=0.1;
    			BlockManager blockManager = CoreRegistry.get(BlockManager.class);
    			Block block=new BaseBlock("water",blockManager.getBlock(blockname).getId());
    			
    			
    			chunk.setBlock(MathUtil.getOffesetChunk(from.x+x*to.x),MathUtil.floor(from.y+x*to.y), MathUtil.getOffesetChunk(from.z+x*to.z), block);
//    			chunk.setBlock(MathUtil.floor(from.x+x*to.x)%16,
//    					MathUtil.floor(from.y+x*to.y),
//    					MathUtil.floor(from.z+x*to.z)%16, block);
    			//重新更新
    			chunk.build();
    			return new GL_Vector(MathUtil.floor(from.x+x*to.x),
    					MathUtil.floor(from.y+x*to.y),
    					MathUtil.floor(from.z+x*to.z));//����ײ��ǰ�ķ���λ��
        		
    		}
    		preIndex=index;
    	}
    	return null;
    	
    }
   
   

}
