package cola.machine.game.myblocks.engine.subsystem.lwjgl;

import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.engine.ComponentSystemManager;
import cola.machine.game.myblocks.engine.modes.GameState;
import cola.machine.game.myblocks.registry.CoreRegistry;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by luying on 14-10-4.
 */
public class LwjglInput extends BaseLwjglSubsystem{

    private static final Logger logger = LoggerFactory.getLogger(LwjglInput.class);
    private boolean mouseGrabbed;

    @Override
    public void preInitialise() {
        super.preInitialise();
    }

    @Override
    public void postInitialise(Config config) {
        initControls();
        updateInputConfig(config);
        Mouse.setGrabbed(false);
    }

    @Override
    public void preUpdate(GameState currentState, float delta) {

    }

    @Override
    public void postUpdate(GameState currentState, float delta) {
        currentState.handleInput(delta);
    }

    @Override
    public void shutdown(Config config) {
    }

    @Override
    public void dispose() {
        Mouse.destroy();
        Keyboard.destroy();
    }

    private void initControls() {
//        try {
//            Keyboard.create();
//            Keyboard.enableRepeatEvents(true);
//            Mouse.create();
//            InputSystem inputSystem = CoreRegistry.putPermanently(InputSystem.class, new InputSystem());
//            inputSystem.setMouseDevice(new LwjglMouseDevice());
//            inputSystem.setKeyboardDevice(new LwjglKeyboardDevice());
//        } catch (LWJGLException e) {
//            throw new RuntimeException("Could not initialize controls.", e);
//        }
    }

    private void updateInputConfig(Config config) {
//        config.getInput().getBinds().updateForChangedMods();
//        config.save();
    }

    public void registerSystems(ComponentSystemManager componentSystemManager) {
    }
}
