package cola.machine.game.myblocks.persistence;

/**
 * Created by luying on 14/11/5.
 */
public class ModuleContext {
/*

    private static ThreadLocal<Module> context = new ThreadLocal<>();

    private ModuleContext() {
    }

    public static Module getContext() {
        return context.get();
    }

    public static ContextSpan setContext(Module module) {
        return new ContextSpan(module);
    }

    public static ContextSpan setContext(Name module) {
        return new ContextSpan(CoreRegistry.get(ModuleManager.class).getEnvironment().get(module));
    }

    public static final class ContextSpan implements AutoCloseable {

        private Module lastContext;

        private ContextSpan(Module newContext) {
            lastContext = getContext();
            context.set(newContext);
        }

        public void close() throws Exception {
            context.set(lastContext);
        }
    }*/
}
