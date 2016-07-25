package cola.machine.game.myblocks.ui.test;

import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.registry.CoreRegistry;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.model.SimpleBooleanModel;
import org.lwjgl.opengl.Display;

/**
 * Created by luying on 16/7/3.
 */
public class GuiRootPane extends Widget {
    MouseControlCenter mouseControlCenter;
       public  final DesktopArea desk;
    private boolean dragActive;
        final BoxLayout btnBox;
        final BoxLayout vsyncBox;
        boolean reduceLag = true;

        public GuiRootPane() {
        setTheme("");

        desk = new DesktopArea();
        desk.setTheme("");
            mouseControlCenter=   CoreRegistry.get(MouseControlCenter.class);
        btnBox = new BoxLayout(BoxLayout.Direction.HORIZONTAL);
        btnBox.setTheme("buttonBox");

        vsyncBox = new BoxLayout(BoxLayout.Direction.HORIZONTAL);
        vsyncBox.setTheme("buttonBox");

        final SimpleBooleanModel vsyncModel = new SimpleBooleanModel(true);
        vsyncModel.addCallback(new Runnable() {
            public void run() {
                Display.setVSyncEnabled(vsyncModel.getValue());
            }
        });

        ToggleButton vsyncBtn = new ToggleButton(vsyncModel);
        vsyncBtn.setTheme("checkbox");
        Label l = new Label("VSync");
        l.setLabelFor(vsyncBtn);

        vsyncBox.add(l);
        vsyncBox.add(vsyncBtn);

        add(desk);
        add(btnBox);
        add(vsyncBox);
    }

    public Button addButton(String text, Runnable cb) {
        Button btn = new Button(text);
        btn.addCallback(cb);
        btnBox.add(btn);
        invalidateLayout();
        return btn;
    }

    public Button addButton(String text, String ttolTip, Runnable cb) {
        Button btn = addButton(text, cb);
        btn.setTooltipContent(ttolTip);
        return btn;
    }

    @Override
    protected void layout() {
        btnBox.adjustSize();
        btnBox.setPosition(0, getParent().getHeight() - btnBox.getHeight());
        desk.setSize(getParent().getWidth(), getParent().getHeight());
        vsyncBox.adjustSize();
        vsyncBox.setPosition(
                getParent().getWidth() - vsyncBox.getWidth(),
                getParent().getHeight() - vsyncBox.getHeight());
    }

    @Override
    protected void afterAddToGUI(GUI gui) {
        super.afterAddToGUI(gui);
        validateLayout();
    }

    @Override
    protected boolean handleEvent(Event evt) {
        if(evt.getType() == Event.Type.KEY_PRESSED &&
                evt.getKeyCode() == Event.KEY_L &&
                (evt.getModifiers() & Event.MODIFIER_CTRL) != 0 &&
                (evt.getModifiers() & Event.MODIFIER_SHIFT) != 0) {
            reduceLag ^= true;
            System.out.println("reduceLag = " + reduceLag);
        }


        boolean handled =super.handleEvent(evt);
        if(!handled)
            if(evt.isMouseEventNoWheel()) {
                //mouseControlCenter.mouseMove(evt.getMouseX(),evt.getMouseY());
                if(dragActive) {
                    if(evt.isMouseDragEnd()) {
                        //System.out.println("拖动结束");
                        dragActive = false;

                    }else{
                        mouseControlCenter.mouseRightDrag(evt.getMouseX(),evt.getMouseY());
                    }

                } else if(evt.isMouseDragEvent()) {
                    dragActive = true;
                    mouseControlCenter.prevMouseX=evt.getMouseX();
                    mouseControlCenter.prevMouseY=evt.getMouseY();
                    //System.out.println("开始拖动");
                }else if(evt.getType()== Event.Type.MOUSE_CLICKED){

                   // System.out.println("ctrlframe:"+evt.getKeyCode());
                }
                return true;
            }else if(evt.isKeyPressedEvent()){
                //System.out.println("key pressed "+evt.getType());
                mouseControlCenter.keyDown(evt.getKeyCode());
            }else{
               // System.out.println("nothing event type"+evt.getType());
            }
        return false;

        //return super.handleEvent(evt);
    }

}

