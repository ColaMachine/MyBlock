package cola.machine.game.myblocks.engine.entitySystem.event.internal;

import cola.machine.game.myblocks.engine.entitySystem.event.Event;
import cola.machine.game.myblocks.engine.entitySystem.event.Event2;

/**
 * Created by luying on 14/10/27.
 */
public interface ConsumalbeEvent extends Event2 {
    boolean isConsumed();
    boolean  consume();
}
