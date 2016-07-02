package cola.machine.game.myblocks.logic.characters;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//import com.bulletphysics.linearmath.QuaternionUtil;

import cola.machine.game.myblocks.entitySystem.Component;
import cola.machine.game.myblocks.entitySystem.entity.EntityRef;
import cola.machine.game.myblocks.math.Direction;
import cola.machine.game.myblocks.math.TeraMath;

public class CharacterComponent implements Component{
	public Vector3f spawnPosition =new Vector3f();
	public float eyeOffset =0.6f;
	public float interactionRange=5f;
	public float pitch;
	public float yaw;
	
	public EntityRef movingItem =EntityRef.NULL;
	
	public EntityRef controller =EntityRef.NULL;
	
	public int seletedItem;
	public float handAnimation;
	
	public Quat4f getLookRotation(){
		Quat4f lookRotation =new Quat4f();
//		QuaternionUtil.setEuler(lookRotation, TeraMath.DEG_TO_RAD*yaw,TeraMath.DEG_TO_RAD*pitch, 0);
		return lookRotation;
	}
	
	public Vector3f getLookDirection(){
		Vector3f result =Direction.FORWARD.getVector3f();
//		QuaternionUtil.quatRotate(getLookRotation(), result, result);
		return result;
	}
	
}
