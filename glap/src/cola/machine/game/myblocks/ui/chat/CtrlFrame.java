package cola.machine.game.myblocks.ui.chat;

/**
 * Created by colamachine on 16-6-24.
 */

import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.registry.CoreRegistry;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.textarea.HTMLTextAreaModel;
import org.lwjgl.Sys;

public class CtrlFrame extends Widget {
    MouseControlCenter mouseControlCenter;

    public CtrlFrame() {
        this.setSize(800,600);
       //this.setFocusKeyEnabled(true);
        this.setCanAcceptKeyboardFocus(true);
        mouseControlCenter=   CoreRegistry.get(MouseControlCenter.class);
    }

    private boolean dragActive;
    @Override
    protected boolean handleEvent(Event evt) {
        if(evt.isMouseEventNoWheel()) {
            //mouseControlCenter.mouseMove(evt.getMouseX(),evt.getMouseY());
            if(dragActive) {
                if(evt.isMouseDragEnd()) {
                    //System.out.println("拖动结束");
                    dragActive = false;

                }else{
                   // mouseControlCenter.mouseRightDrag(evt.getMouseX(),evt.getMouseY());
                }

            } else if(evt.isMouseDragEvent()) {
                dragActive = true;
                mouseControlCenter.prevMouseX=evt.getMouseX();
                mouseControlCenter.prevMouseY=evt.getMouseY();
                //System.out.println("开始拖动");
            }else if(evt.getType()== Event.Type.MOUSE_CLICKED){

                System.out.println("ctrlframe:"+evt.getKeyCode());
            }
            return true;
        }else if(evt.isKeyPressedEvent()){
            System.out.println("key pressed "+evt.getType());
            mouseControlCenter.keyDown(evt.getKeyCode());
        }else{
            System.out.println("nothing event type"+evt.getType());
        }
        return super.handleEvent(evt);
    }

}
