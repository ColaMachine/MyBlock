package cola.machine.game.myblocks.entitySystem.system;

public interface ComponentSystem {
	/**
     * Called to initialise the system. This occurs after injection, but before other systems are necessarily initialised, so they should not be interacted with
     */
    void initialise();

    /**
     * Called after all systems are initialised, but before the game is loaded
     */
    void preBegin();

    /**
     * Called after the game is loaded, right before first frame
     */
    void postBegin();

    /**
     * Called before the game is saved (this may be after shutdown)
     */
    void preSave();

    /**
     * Called after the game is saved
     */
    void postSave();

    /**
     * Called right before the game is shut down
     */
    void shutdown();
}
