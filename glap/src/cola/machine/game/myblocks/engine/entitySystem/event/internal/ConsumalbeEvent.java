package cola.machine.game.myblocks.engine.entitySystem.event.internal;

import cola.machine.game.myblocks.engine.entitySystem.event.Event;

/**
 * Created by luying on 14/10/27.
 */
public interface ConsumalbeEvent extends Event {
    boolean isConsumed();
    boolean  consume();
}
