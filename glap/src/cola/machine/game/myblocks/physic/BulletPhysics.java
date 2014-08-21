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

import com.bulletphysics.collision.dispatch.CollisionWorld;
/**
 * Physics engine implementation using TeraBullet (a customised version of JBullet)
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class BulletPhysics  {

	private BlockRepository blockRepository=null;

    public BulletPhysics(BlockRepository blockRepository) {
    	this.blockRepository=blockRepository;
    	
    }

   

    public GL_Vector rayTrace(GL_Vector from, GL_Vector direction, float distance) {
        
    	GL_Vector to = new GL_Vector(direction);
        
        //计算出终点位置

    	//每次移动0.1
    	for(float x=0.1f;x<distance;x+=0.1){
    		
    		//去获得是否在一个物体之内
    		if(
    				blockRepository.haveObject(from.x+x*to.x,from.y+x*to.y,from.z+x*to.z)
    		){
    			
    			int _x = MathUtil.getNearOdd(from.x+(x-1)*to.x );
    			int _y = MathUtil.getNearOdd(from.y+(x-1)*to.y );
    			int _z = MathUtil.getNearOdd(from.z+(x-1)*to.z );
    			return new GL_Vector(_x,_y,_z);//返回撞击前的方块位置
        		
    		}
    	}
    	return null;
    	
    }
    //得到撞击到的是方块的哪个面
    public void processClosest(CollisionWorld.ClosestRayResultWithUserDataCallback closest ){
    	System.out.println(closest.userData.toString());
    }

}
