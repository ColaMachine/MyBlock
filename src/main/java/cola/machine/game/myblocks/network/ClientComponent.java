package cola.machine.game.myblocks.network;

import cola.machine.game.myblocks.entitySystem.Component;
import cola.machine.game.myblocks.entitySystem.entity.EntityRef;

public class ClientComponent implements Component {
 public boolean local;
 public EntityRef clientInfo =EntityRef.NULL;
 public EntityRef character =EntityRef .NULL;
}
