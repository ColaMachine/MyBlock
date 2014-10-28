package cola.machine.game.myblocks.engine.subsystem;

/**
 * Created by luying on 14/10/27.
 */
public interface DisplayDevice {
    boolean isActive();

    boolean isCloseRequested();

    void setFullscreen(boolean state);

    void processMessages();

    boolean isHeadless();

    void prepareToRender();
}
