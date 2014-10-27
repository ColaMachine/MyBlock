package cola.machine.game.myblocks.engine.subsystem.lwjgl;

import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.engine.modes.GameState;
import cola.machine.game.myblocks.registry.CoreRegistry;

/**
 * Created by luying on 14/10/27.
 */
public class LwjglGraphics extends BaseLwjglSubsystem{


    @Override
    public void preInitialise() {
        super.preInitialise();
    }

    @Override
    public void postInitialise(Config config) {
        CoreRegistry.putPermanently(RenderingSubsystemFactory.class, new LwjglRenderingSubsystemFactory(bufferPool));

        LwjglDisplayDevice lwjglDisplay = new LwjglDisplayDevice();
        CoreRegistry.putPermanently(DisplayDevice.class, lwjglDisplay);

        initDisplay(config, lwjglDisplay);
        initOpenGL();

        CoreRegistry.putPermanently(NUIManager.class, new NUIManagerInternal(new LwjglCanvasRenderer()));
    }

    @Override
    public void preUpdate(GameState currentState, float delta) {

    }

    @Override
    public void postUpdate(GameState currentState, float delta) {

    }

    @Override
    public void shutdown(Config config) {

    }

    @Override
    public void dispose() {

    }
}
