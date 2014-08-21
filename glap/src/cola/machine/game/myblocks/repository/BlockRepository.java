package cola.machine.game.myblocks.repository;

import java.util.HashMap;

import cola.machine.game.myblocks.model.Block;

import util.MathUtil;

public class BlockRepository {
	HashMap<Integer,Block>  map=new HashMap<Integer,Block> ();
	
	public void put(Block block){
		map.put(block.x*1000000+block.z*1000+block.y,block);
	}
	public boolean haveObject(int x,int y,int z){
		if(y<=0)
			return true;
		return map.get(x*1000000+z*1000+y)!=null;
	}
	
	public boolean haveObject(float x,float y,float z){
		if(y<=0)
			return true;
		
		int _x = MathUtil.getNearOdd(x );
		int _y = MathUtil.getNearOdd(y);
		int _z = MathUtil.getNearOdd(z );
		if(this.haveObject(x, y, z)){
			//System.out.println("�ǵ�����"+y);
			//System.out.println("��ǰ�����y:"+human.Position.y+"��⵽����:"+y);
			return true;
		}
		return false;
	}
}
