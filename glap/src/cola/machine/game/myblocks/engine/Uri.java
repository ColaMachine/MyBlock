package cola.machine.game.myblocks.engine;
import cola.machine.game.myblocks.naming.Name;

/**
 * Created by luying on 14/11/5.
 */
    public interface Uri {
        /**
         * The character(s) use to separate the module name from other parts of the Uri
         */
        String MODULE_SEPARATOR = ":";

        /**
         * @return The name of the module the resource in question resides in.
         */
        Name getModuleName();

        /**
         * @return Whether this uri represents a valid, well formed uri.
         */
        boolean isValid();
    }