    package cola.machine.game.myblocks.ui.test;

    import cola.machine.game.myblocks.control.MouseControlCenter;
    import cola.machine.game.myblocks.registry.CoreRegistry;
    import cola.machine.game.myblocks.ui.chat.ChatFrame;
    import cola.machine.game.myblocks.ui.inventory.InventoryDemo;
    import cola.machine.game.myblocks.ui.inventory.InventoryDialog;
    import cola.machine.game.myblocks.ui.inventory.PersonDialog;
    import cola.machine.game.myblocks.ui.inventory.PersonPanel;
    import de.matthiasmann.twl.*;
    import de.matthiasmann.twl.model.SimpleBooleanModel;
    import org.lwjgl.opengl.Display;

    /**
     * Created by luying on 16/7/3.
     */
    public class GuiRootPane extends Widget {
        protected boolean closeRequested;
        MouseControlCenter mouseControlCenter;
        public  final DesktopArea desk;
        private boolean dragActive;
        final BoxLayout btnBox;
        final BoxLayout vsyncBox;
        boolean reduceLag = true;
        final InventoryDialog inventoryDialog;
        public PersonDialog personDialog ;
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

             personDialog = new PersonDialog();
            add(personDialog);
            personDialog.setPosition(100,200);

             inventoryDialog = new InventoryDialog();//10行5列
           // desk.add(inventoryPanel);
            add(inventoryDialog);
            //inventoryPanel.adjustSize();
            inventoryDialog.center(0.5f, 0.5f);
            inventoryDialog.addCloseCallback();



            // personPanel.validateLayout();
           // personPanel.setMinSize(personPanel.getPreferredInnerWidth(),personPanel.getPreferredInnerHeight());
           // personPanel.setMinSize(200,500);


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


        public void reAdjust(){
            this.chatFrame.adjustSize();
            this.inventoryDialog.adjustSize();
            this.personDialog.adjustSize();
        }
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

                    if(evt.getType()==Event.Type.KEY_PRESSED){
                        if(evt.getKeyCode() == Event.KEY_B){

                           // InventoryDialog inventoryDialog= CoreRegistry.get(InventoryDialog.class);
                            inventoryDialog.setVisible(!inventoryDialog.isVisible());
                            inventoryDialog.validateLayout();
                            inventoryDialog.adjustSize();
                        }
                    }
                    //System.out.println("key pressed "+evt.getType());
                   // mouseControlCenter.keyDown(evt.getKeyCode());
                    if(evt.getKeyCode()==Event.KEY_RETURN){

                        CoreRegistry.get(ChatFrame.class).showEdit();
                        CoreRegistry.get(ChatFrame.class).setFocus();
                    }
                    return true;
                }else if(evt.isKeyRepeated()){
                   // mouseControlCenter.keyDown(evt.getKeyCode());
                    // System.out.println("nothing event type"+evt.getType());
                }
            return false;

            //return super.handleEvent(evt);
        }

        public void addGame(){

        }

        public void addGamingComponent(){
        /*WidgetsDemoDialog1 dlg1 = new WidgetsDemoDialog1();
        root.desk.add(dlg1);
        dlg1.adjustSize();
        dlg1.center(0.35f, 0.5f);

        GraphDemoDialog1 fMS = new GraphDemoDialog1();
        root.desk.add(fMS);
        fMS.adjustSize();
        fMS.center(1f, 0.8f);

        ScrollPaneDemoDialog1 fScroll = new ScrollPaneDemoDialog1();
        root.desk.add(fScroll);
        fScroll.adjustSize();
        fScroll.center(0f, 0f);
        fScroll.addCloseCallback();
        fScroll.centerScrollPane();

        TableDemoDialog1 fTable = new TableDemoDialog1();
        root.desk.add(fTable);
        fTable.adjustSize();
        fTable.center(0f, 0.5f);
        //fTable.addCloseCallback();

        PropertySheetDemoDialog fPropertySheet = new PropertySheetDemoDialog();
        fPropertySheet.setHardVisible(false);
        root.desk.add(fPropertySheet);
        fPropertySheet.setSize(400, 400);
        fPropertySheet.center(0f, 0.25f);
        fPropertySheet.addCloseCallback();

        TextAreaDemoDialog1 fInfo = new TextAreaDemoDialog1();
        root.desk.add(fInfo);
        fInfo.setSize(gui.getWidth()*2/3, gui.getHeight()*2/3);
        fInfo.center(0.5f, 0.5f);
        fInfo.addCloseCallback();*/

       /* InventoryDialog inventoryPanel = new InventoryDialog();//10行5列
        root.desk.add(inventoryPanel);


        inventoryPanel.center(0.23f, 0.36f);
        //inventoryPanel.addCloseCallback();
        inventoryPanel.adjustSize();
        inventoryPanel.setVisible(false);
        CoreRegistry.put(InventoryDialog.class,inventoryPanel);*/


           // TextAreaDemoDialog2 fTextAreaTest = new TextAreaDemoDialog2();




       /* TextAreaDemoDialog2 fTextAreaTest = new TextAreaDemoDialog2();
>>>>>>> 0ee979ac5e533853303ebca53ea6238e474ff593
        fTextAreaTest.setHardVisible(false);
        root.desk.add(fTextAreaTest);
        fTextAreaTest.setSize(gui.getWidth()*2/3, gui.getHeight()*2/3);
        fTextAreaTest.center(0.5f, 0.5f);
        fTextAreaTest.addCloseCallback();

        ColorSelectorDemoDialog1 fCS = new ColorSelectorDemoDialog1();
        fCS.setHardVisible(false);
        root.desk.add(fCS);
        fCS.adjustSize();
        fCS.center(0.5f, 0.5f);
        fCS.addCloseCallback();

        final PopupWindow settingsDlg = new PopupWindow(root);
        final VideoSettings settings = new VideoSettings(
                AppletPreferences.userNodeForPackage(VideoSettings.class),
                desktopMode);
        settingsDlg.setTheme("settingdialog");
        settingsDlg.add(settings);
        settingsDlg.setCloseOnClickedOutside(false);
        settings.setTheme("settings");
        settings.addCallback(new CallbackWithReason<VideoSettings.CallbackReason>() {
            public void callback(VideoSettings.CallbackReason reason) {
                vidDlgCloseReason = reason;
                settingsDlg.closePopup();
            }
        });*/

        /*    addButton("Exit", new Runnable() {
                public void run() {
                    //closeRequested = true;
                }
            });*/



        /*root.addButton("Info", "Shows TWL license", new ToggleFadeFrame(fInfo)).setTooltipContent(makeComplexTooltip());
        *///root.addButton("inventory", "inventory", new ToggleFadeFrame(inventoryPanel)).setTooltipContent(makeComplexTooltip());
        /*root.addButton("TA", "Shows a text area test", new ToggleFadeFrame(fTextAreaTest));


            root.addButton("Settings", "Opens a dialog which might be used to change video settings", new Runnable() {
                public void run() {
                    settings.readSettings();
                    settingsDlg.openPopupCentered();
                }
            });
*/

      /*  root.addButton("ScrollPane", new ToggleFadeFrame(fScroll));
        root.addButton("Properties", new ToggleFadeFrame(fPropertySheet));
        root.addButton("Color", new ToggleFadeFrame(fCS));

        root.addButton("Game", new Runnable() {
            public void run() {
                BlockGame game = new BlockGame();
                game.setTheme("/blockgame");
                PopupWindow popup = new PopupWindow(root);
                popup.setTheme("settingdialog");
                popup.add(game);ç∂   x'x
                popup.openPopupCentered();
            }
        });*/

            //fInfo.requestKeyboardFocus();
        }

    }

