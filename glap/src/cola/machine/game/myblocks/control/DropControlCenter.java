package cola.machine.game.myblocks.control;

import check.CrashCheck;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.registry.CoreRegistry;

public class DropControlCenter {

	//public BlockRepository blockRepository=null;
	/*
	 * don't use at every frame ,it's cost much time
	 */
	public void check(LivingThing human){
		// ȡ�õ�ǰ��human���

		// ȡ���������� ż�����

		//ȡ�����������������

        if(human.position.y<=0){
            human.position.y=1.5f;
        }

		if(!human.stable){
			//System.out.println("��ǰ�����y:"+human.Position.y);
//			int x = MathUtil.getNearOdd(human.Position.x );
//			int y = MathUtil.getNearOdd(human.Position.y);
//			int z = MathUtil.getNearOdd(human.Position.z );

			if(CoreRegistry.get(CrashCheck.class).haveBlock2(human)){
				//System.out.println("�ǵ�����"+y);
				//System.out.println("��ǰ�����y:"+human.Position.y+"��⵽����:"+y);
				human.mark=(int)human.position.y;
				if(human.mark<=0){
					System.out.println("get the underground");
				}
				//System.out.println("mark :"+human.mark);
			}else{
				human.mark=(int)human.position.y-1;
			}

		}else if(human.stable){

            if(CoreRegistry.get(CrashCheck.class).check(human)){
                human.position.y+=2;//找到最近的地方让他安顿;
                return;
            }
//			int x = MathUtil.getNearOdd(human.Position.x );
//			int y = MathUtil.getNearOdd(human.Position.y);
//			int z = MathUtil.getNearOdd(human.Position.z );
			if(!CoreRegistry.get(CrashCheck.class).haveBlock2(human)){
				//System.out.println("check the human hasn't under block  begin to drop");
				//System.out.println("��ǰ�����y:"+human.Position.y+"��⵽����:"+y);
				human.drop();
				human.mark=(int)human.position.y-1;
				//System.out.println("mark :"+human.mark);
			}
		}




	}
}
