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

import cola.machine.game.myblocks.repository.BlockRepository;

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

   

    public GL_Vector rayTrace(GL_Vector from, GL_Vector direction, float distance) {
        
    	GL_Vector to = new GL_Vector(direction);
        
        //计算出终点位置

    	//每次移动0.1
    	int preIndex=-1;
    	int index=0;
    	for(float x=0.1f;x<distance;x+=0.1){
    		int _x = MathUtil.getNearOdd(from.x+x*to.x);
    		int _y = MathUtil.getNearOdd(from.y+x*to.y);
    		int _z = MathUtil.getNearOdd(from.z+x*to.z);
    		index=_x*10000+_z*100+_y;
    		if(index==preIndex)
    			continue;
    		//去获得是否在一个物体之内
    		if(
    				blockRepository.haveObject(_x,_y,_z)
    		){
    			x-=0.1;
    			return new GL_Vector(MathUtil.getNearOdd(from.x+x*to.x),
    					MathUtil.getNearOdd(from.y+x*to.y),
    					 MathUtil.getNearOdd(from.z+x*to.z));//返回撞击前的方块位置
        		
    		}
    		preIndex=index;
    	}
    	return null;
    	
    }
   
   

}
