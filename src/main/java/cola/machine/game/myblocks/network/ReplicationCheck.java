package cola.machine.game.myblocks.network;

import cola.machine.game.myblocks.reflection.metadata.FieldMetadata;

public interface ReplicationCheck {
	boolean shouldReplicate(FieldMetadata field,boolean initial,boolean toOwner);
}
