package cola.machine.game.myblocks.engine.subsystem.lwjgl;

import cola.machine.game.myblocks.registry.CoreRegistry;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by luying on 14-10-4.
 */
public class LwjglInput {
    private static final Logger logger = LoggerFactory.getLogger(LwjglInput.class);

    public void initControl(){
        try{
            Keyboard.create();
            Keyboard.enableRepeatEvents(true);
            Mouse.create();
           // s InputSystem inputSystem= CoreRegistry.putPermanently(InputSystem.class, new InputSystem());
            //inputSystem
        }catch (Exception e){
            throw new RuntimeException("could not init input control",e);
        }
    }
}
