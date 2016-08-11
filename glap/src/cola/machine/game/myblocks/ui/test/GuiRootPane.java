package cola.machine.game.myblocks.ui.test;

import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatFrame;
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
        //setTheme("");

        desk = new DesktopArea();
        //  desk.setTheme("");
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
        chatFrame = new ChatFrame();
        add(chatFrame);

        chatFrame.setSize(400, 200);
        chatFrame.setPosition(10, 350);
        add(desk);
        add(btnBox);
        add(vsyncBox);


       /* loginPanel = new DialogLayout();
        loginPanel.setTheme("login-panel");

        efName = new EditField();
        efName.setTheme("login-edit");
        efName.addCallback(new EditField.Callback() {
            public void callback(int key) {
                if(key == Event.KEY_RETURN) {
                    efPassword.requestKeyboardFocus();
                }
            }
        });

        efPassword = new EditField();
        efPassword.setPasswordMasking(true);
        efPassword.addCallback(new EditField.Callback() {
            public void callback(int key) {
                if(key == Event.KEY_RETURN) {
                    emulateLogin();
                }
            }
        });

        Label lName = new Label("Name");
        lName.setLabelFor(efName);

        Label lPassword = new Label("Password");
        lPassword.setLabelFor(efPassword);

        btnLogin = new Button("LOGIN");
        btnLogin.addCallback(new Runnable() {
            public void run() {
                emulateLogin();
            }
        });

        DialogLayout.Group hLabels = loginPanel.createParallelGroup(lName, lPassword);
        DialogLayout.Group hFields = loginPanel.createParallelGroup(efName, efPassword);
        DialogLayout.Group hBtn = loginPanel.createSequentialGroup()
                .addGap()   // right align the button by using a variable gap
                .addWidget(btnLogin);

        loginPanel.setHorizontalGroup(loginPanel.createParallelGroup()
                .addGroup(loginPanel.createSequentialGroup(hLabels, hFields))
                .addGroup(hBtn));
        loginPanel.setVerticalGroup(loginPanel.createSequentialGroup()
                .addGroup(loginPanel.createParallelGroup(lName, efName))
                .addGroup(loginPanel.createParallelGroup(lPassword, efPassword))
                .addWidget(btnLogin));


        add(loginPanel);*/
    }

  /*  final DialogLayout loginPanel;
    final EditField efName;
    final EditField efPassword;
    final Button btnLogin;*/


    private final ChatFrame chatFrame;
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
   /* void emulateLogin() {
        GUI gui = getGUI();
        if(gui != null) {
            // step 1: disable all controls
            efName.setEnabled(false);
            efPassword.setEnabled(false);
            btnLogin.setEnabled(false);

            // step 2: get name & password from UI
            String name = efName.getText();
            String pasword = efPassword.getText();
            System.out.println("Name: " + name + " with a " + pasword.length() + " character password");

            // step 3: start a timer to simulate the process of talking to a remote server
            Timer timer = gui.createTimer();
            timer.setCallback(new Runnable() {
                public void run() {
                    // once the timer fired re-enable the controls and clear the password
                    efName.setEnabled(true);
                    efPassword.setEnabled(true);
                    efPassword.setText("");
                    efPassword.requestKeyboardFocus();
                    btnLogin.setEnabled(true);
                }
            });
            timer.setDelay(2500);
            timer.start();

            *//* NOTE: in a real app you would need to keep a reference to the timer object
             * to cancel it if the user closes the dialog which uses the timer.
             * @see Widget#beforeRemoveFromGUI(de.matthiasmann.twl.GUI)
             *//*
        }
    }*/
    @Override
    protected void layout() {
        btnBox.adjustSize();
        btnBox.setPosition(0, getParent().getHeight() - btnBox.getHeight());
        desk.setSize(getParent().getWidth(), getParent().getHeight());
        vsyncBox.adjustSize();
        vsyncBox.setPosition(
                getParent().getWidth() - vsyncBox.getWidth(),
                getParent().getHeight() - vsyncBox.getHeight());
    /*    loginPanel.adjustSize();
        loginPanel.setPosition(
                getInnerX() + (getInnerWidth() - loginPanel.getWidth())/2,
                getInnerY() + (getInnerHeight() - loginPanel.getHeight())/2);*/
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
                        //System.out.println(evt.getMouseButton()+":"+evt.getKeyChar()+":"+evt.getModifiers() );
                        if(evt.getModifiers()==64){
                            mouseControlCenter.mouseLeftDrag(evt.getMouseX(), evt.getMouseY());
                        }else if(evt.getModifiers()==128) {
                            mouseControlCenter.mouseRightDrag(evt.getMouseX(), evt.getMouseY());
                        }
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

    public void addGame(){

    }

}

