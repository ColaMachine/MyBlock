package cola.machine.game.myblocks.logic.location;

import com.google.common.collect.Lists;

import cola.machine.game.myblocks.entitySystem.entity.EntityRef;

public class LocationComponent implements Compoonent,ReplicationCheck {
	public boolean replicateChanges=true;
	EntityRef parent =EntityRef.NULL;
	List<EntityRef> children=Lists.newArrayList();
	
	Vector3f position=new Vector3f();
	
		
}
