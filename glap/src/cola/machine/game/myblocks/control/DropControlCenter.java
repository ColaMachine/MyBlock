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
		// ȡ�õ�ǰ��human����

		// ȡ����������� ż������
		
		//ȡ�������������������
		if(!human.stable){
			//System.out.println("��ǰ�����y:"+human.Position.y);
			int x = MathUtil.getNearOdd(human.Position.x );
			int y = MathUtil.getNearOdd(human.Position.y);
			int z = MathUtil.getNearOdd(human.Position.z );
			if(blockRepository.haveObject(x, y - 2, z)){
				//System.out.println("�ǵ�����"+y);
				//System.out.println("��ǰ�����y:"+human.Position.y+"��⵽����:"+y);
				human.mark=y-1;
			}
			
		}
		
		
		
		
	}
}
