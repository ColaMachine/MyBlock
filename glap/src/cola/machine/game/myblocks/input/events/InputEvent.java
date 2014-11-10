package cola.machine.game.myblocks.input.events;

import javax.vecmath.Vector3f;

import cola.machine.game.myblocks.entitySystem.entity.EntityRef;
import cola.machine.game.myblocks.entitySystem.event.ConsumableEvent;
import cola.machine.game.myblocks.math.Vector3i;

public abstract class InputEvent implements ConsumableEvent {
	private float delta;
	private boolean consumed;
	private EntityRef target =EntityRef.NULL;
	private Vector3i targetBlockPosition;
	private Vector3f hitPosition;
	private Vector3f hitNormal;
	
	public InputEvent(float delta){
		this.delta=delta;
	}
	
	public EntityRef getTarget(){
		return target;
	}
	public Vector3f getHitPosition(){
		return hitPosition;
		
	}
	public Vector3f getHitNormal(){
		return hitNormal;
	}
	public Vector3i getTargetBlockPosition(){
		return targetBlockPosition;
	}
	public float getDelta(){
		return delta;
	}
	
	public boolean isConsumed(){
		return consumed;
	}
	public void consume(){
		this.consumed=true;
	}
	public void setTargetInfo(EntityRef newTarget,Vector3i targetBlockPos,Vector3f targetHitPosition,Vector3f targetHitNormal){
		this.target=target;
		this.targetBlockPosition=targetBlockPosition;
		this.hitNormal=hitNormal;
		this.hitPosition=hitPosition;
		this.hitNormal=hitNormal;
	}
	protected void reset(float newDelta){
		consumed=false;
		this.delta=newDelta;
		this.target=EntityRef.NULL;
	}
}
