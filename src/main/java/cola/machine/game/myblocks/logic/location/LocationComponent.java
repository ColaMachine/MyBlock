package cola.machine.game.myblocks.logic.location;

import cola.machine.game.myblocks.entitySystem.Component;
import cola.machine.game.myblocks.entitySystem.entity.EntityRef;
import cola.machine.game.myblocks.network.ReplicationCheck;
import cola.machine.game.myblocks.reflection.metadata.FieldMetadata;
import com.google.common.collect.Lists;

import javax.vecmath.Vector3f;
import java.util.List;

public class LocationComponent implements Component,ReplicationCheck {
	public boolean replicateChanges=true;
	EntityRef parent =EntityRef.NULL;
	List<EntityRef> children=Lists.newArrayList();
	
	Vector3f position=new Vector3f();


	@Override
	public boolean shouldReplicate(FieldMetadata field, boolean initial, boolean toOwner) {
		return false;
	}
}
