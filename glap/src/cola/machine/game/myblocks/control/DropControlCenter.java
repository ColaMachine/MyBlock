package cola.machine.game.myblocks.control;

import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.repository.BlockRepository;
import util.MathUtil;

public class DropControlCenter {
	
	public BlockRepository blockRepository=null;
	/*
	 * don't use at every frame ,it's cost much time
	 */
	public void check(Human human){
		// 取得当前的human坐标

		// 取得离他最近的 偶数坐标
		
		//取得离他最近的奇数坐标
		if(!human.stable){
			//System.out.println("当前人物的y:"+human.Position.y);
			int x = MathUtil.getNearOdd(human.Position.x );
			int y = MathUtil.getNearOdd(human.Position.y);
			int z = MathUtil.getNearOdd(human.Position.z );
			if(blockRepository.haveObject(x, y - 2, z)){
				//System.out.println("是到滴了"+y);
				//System.out.println("当前人物的y:"+human.Position.y+"检测到土壤:"+y);
				human.mark=y-1;
			}
			
		}
		
		
		
		
	}
}
