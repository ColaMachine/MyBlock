package cola.machine.game.myblocks.repository;

import glmodel.GL_Vector;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.engine.MyBlockEngine;
import cola.machine.game.myblocks.model.Block;
import util.MathUtil;

public class BlockRepository {
	public MyBlockEngine engine;
	HashMap<Integer,Block>  map=new HashMap<Integer,Block> ();
	public BlockRepository(MyBlockEngine engine){
		this .engine=engine;
	}
	public void put(Block block){//System.out.println("the existing block nums"+map.size());
		if(!haveObject(block.x,block.y,block.z))
		map.put(block.x*10000+block.z*100+block.y,block);
	}
	public boolean haveObject(int x,int y,int z){
		if(y<=0)
			return true;
		return map.get(x*10000+z*100+y)!=null;
	}
	
	public Block getObject(float x,float y,float z){
		int _x = MathUtil.getNearOdd(x );
		int _y = MathUtil.getNearOdd(y);
		int _z = MathUtil.getNearOdd(z );
		return map.get(_x*10000+_z*100+_y);
	}
	/*public GL_Vector getObject(float x,float y,float z){
		int _x = MathUtil.getNearOdd(x );
		int _y = MathUtil.getNearOdd(y);
		int _z = MathUtil.getNearOdd(z );
		if( map.get(_x*10000+_z*100+_y)!=null){
			return new GL_Vector(_x,_y,_z);
		}
	}*/
	public Block getObject(int x,int y,int z){
		return map.get(x*10000+z*100+y);
	}
	/*public Block haveObject(float x,float y,float z){
		int _x = MathUtil.getNearOdd(x );
		int _y = MathUtil.getNearOdd(y);
		int _z = MathUtil.getNearOdd(z );
		 return map.get(x*10000+z*100+y);
	}*/
	public void reBuild(){
		
		
		int i=0;

		//GL11.glDeleteLists(engine.earth, GL11.GL_COMPILE); // Start Building A List
		//engine.displayLists.clear();
	
		
		java.util.Iterator it = map.entrySet().iterator();
		engine.earth =engine. beginDisplayList();
		while(it.hasNext()){
			i++;
		java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
		// entry.getKey() 返回与此项对应的键
		// entry.getValue() 返回与此项对应的值
		Block block = (Block) entry.getValue();
		block.renderCube();
		//System.out.println("the existing block nums"+map.size());
		} 
		engine. endDisplayList();
		System.out.println("redisplaylist :"+i);
	}
}
