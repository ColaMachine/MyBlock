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

import org.lwjgl.input.Keyboard;

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

   

    public GL_Vector rayTrace(GL_Vector from, GL_Vector direction, float distance,String blockname,boolean delete) {
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
    		int newX=MathUtil.getBelongChunkInt(from.x+x*to.x);
    		int newZ=MathUtil.getBelongChunkInt(from.z+x*to.z);
    		//every time add a little
    		
    		int chunk_pos_x =  MathUtil.floor(((float)_x)/16);
    		int chunk_pos_z =  MathUtil.floor(((float)_z)/16);
    		ChunkImpl chunk = localChunkProvider.getChunk(new Vector3i(chunk_pos_x,0,chunk_pos_z));
    		//System.out.printf(" %d %d %d",_x,_y,_z);
    		//先获取chunk 在获取block
			//blockRepository.haveObject(_x,_y,_z)
			
			//detect if there is block
    		
    		if(chunk.getBlockData(MathUtil.getOffesetChunk(_x),MathUtil.floor( _y), MathUtil.getOffesetChunk(_z))>0){
    			if(delete){
    				Block block=new BaseBlock("water",0);
    				chunk.setBlock(MathUtil.getOffesetChunk(_x),MathUtil.floor( _y), MathUtil.getOffesetChunk(_z),block);
    				chunk.build();
    				return null;
    			}
    			x-=0.1;
    			 _x = MathUtil.floor(from.x+x*to.x);
        		 _y = MathUtil.floor(from.y+x*to.y);
        		 _z = MathUtil.floor(from.z+x*to.z);
        		 chunk_pos_x =  MathUtil.floor(((float)_x)/16);
         		 chunk_pos_z =  MathUtil.floor(((float)_z)/16);
    			BlockManager blockManager = CoreRegistry.get(BlockManager.class);
    			Block block=new BaseBlock("water",blockManager.getBlock(blockname).getId());
    			
    			ChunkImpl _chunk = localChunkProvider.getChunk(new Vector3i(chunk_pos_x,0,chunk_pos_z));
    			_chunk.setBlock(MathUtil.getOffesetChunk(_x),MathUtil.floor(_y), MathUtil.getOffesetChunk(_z), block);
//    			chunk.setBlock(MathUtil.floor(from.x+x*to.x)%16,
//    					MathUtil.floor(from.y+x*to.y),
//    					MathUtil.floor(from.z+x*to.z)%16, block);
    			//重新更新
    			_chunk.build();
    			return new GL_Vector(MathUtil.floor(from.x+x*to.x),
    					MathUtil.floor(from.y+x*to.y),
    					MathUtil.floor(from.z+x*to.z));//����ײ��ǰ�ķ���λ��
        		
    		}
    		preIndex=index;
    	}
    	return null;
    	
    }
   
   

}
