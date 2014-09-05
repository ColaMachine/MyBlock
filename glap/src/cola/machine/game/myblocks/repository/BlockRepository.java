package cola.machine.game.myblocks.repository;

import glapp.GLApp;
import glmodel.GL_Vector;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.block.Water;
import cola.machine.game.myblocks.engine.MyBlockEngine;
import cola.machine.game.myblocks.model.Block;
import util.MathUtil;

public class BlockRepository {
	public MyBlockEngine engine;
	public HashMap<Integer,Block>  map=new HashMap<Integer,Block> ();
	public HashMap<String,Integer> handleMap=new HashMap();
	public HashMap<Integer,Block> woodmap=new HashMap<Integer,Block> ();
	public 	HashMap<String,HashMap<Integer,Block> > kindBlockMap=new HashMap<String,HashMap<Integer,Block> > ();
	public BlockRepository(MyBlockEngine engine){
		this .engine=engine; 
	}
	public void put(Block block){//System.out.println("the existing block nums"+map.size());
		if(!haveObject(block.getX(),block.getY(),block.getZ()))
		map.put(block.getX()*10000+block.getZ()*100+block.getY(),block);
		if(kindBlockMap.get(block.getName())==null){
			kindBlockMap.put(block.getName(), new HashMap<Integer,Block>() );
			
		}
		kindBlockMap.get(block.getName()).put(block.getX()*10000+block.getZ()*100+block.getY(),block);
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
		// entry.getKey() ����������Ӧ�ļ�
		// entry.getValue() ����������Ӧ��ֵ
		Block block = (Block) entry.getValue();
		block.render();
		//System.out.println("the existing block nums"+map.size());
		} 
		engine. endDisplayList();
		System.out.println("redisplaylist :"+i);
	}
	
	public void reBuild(String type){
		if(this.handleMap.get(type)!=null){
			
			int oldHandleId=handleMap.get(type);
			GLApp.destroyDisplayList(oldHandleId);
		}
		/*int oldHandleId=;
		if(oldHandleId<1){
			
		}else{
			
		}*/
		int i=0;
		HashMap<Integer,Block> map =this.kindBlockMap.get(type);
		java.util.Iterator it = map.entrySet().iterator();
		int handleId =engine. beginDisplayList();
		while(it.hasNext()){
			i++;
		java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
		// entry.getKey() ����������Ӧ�ļ�
		// entry.getValue() ����������Ӧ��ֵ
		Block block = (Block) entry.getValue();
		block.render();
		//System.out.println("the existing block nums"+map.size());
		} 
		System.out.println("rebuild block : "+type+" count:"+i+"handle id:"+handleId);
		engine. endDisplayList();
		this.handleMap.put(type,handleId);
		//System.out.println("redisplaylist :"+i);
	}
}
