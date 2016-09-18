    package cola.machine.game.myblocks.ui.test;

    import cola.machine.game.myblocks.control.MouseControlCenter;
    import cola.machine.game.myblocks.manager.TextureManager;
    import cola.machine.game.myblocks.registry.CoreRegistry;
    import cola.machine.game.myblocks.ui.chat.ChatFrame;
    import cola.machine.game.myblocks.ui.inventory.*;
    import de.matthiasmann.twl.*;
    import de.matthiasmann.twl.model.SimpleBooleanModel;
    import de.matthiasmann.twl.renderer.Texture;
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
       // final BoxLayout vsyncBox;
        /**技能条**/
        final ToolbarDialog toolbar;
        boolean reduceLag = true;
        /**包裹**/
        final InventoryDialog inventoryDialog;
        /**聊天框**/
        private final ChatFrame chatFrame;
        /**任务面板**/
        public PersonDialog personDialog ;
        public GuiRootPane() {
            //setTheme("");
            mouseControlCenter=   CoreRegistry.get(MouseControlCenter.class);
            /**桌面**/
            desk = new DesktopArea();
            add(desk);
            //  desk.setTheme("");
            /**按钮框**/
            btnBox = new BoxLayout(BoxLayout.Direction.HORIZONTAL);
            btnBox.setTheme("buttonBox");
            add(btnBox);


            /** 聊天框**/
            chatFrame = new ChatFrame();
            add(chatFrame);

            chatFrame.setSize(400, 200);
            chatFrame.setPosition(10, 350);


            //add(vsyncBox);
            /**人物框**/
             personDialog = new PersonDialog();
            add(personDialog);
            //personDialog.setPosition(100,200);

            /**包裹框体**/
             inventoryDialog = new InventoryDialog();//10行5列
           // desk.add(inventoryPanel);
            add(inventoryDialog);
            //inventoryPanel.adjustSize();
            //inventoryDialog.center(0.5f, 0.5f);
            inventoryDialog.addCloseCallback();
            /**快捷访问框**/
             toolbar = new ToolbarDialog();



            add(toolbar);

            addButton("toolbar", "toolbar", new ToggleFadeFrame(toolbar));
            addButton("person","person", new ToggleFadeFrame(personDialog));
            addButton("inventory","inventory", new ToggleFadeFrame(inventoryDialog));
        }




       /* public void reAdjust(){

        }*/

        public Button addButton(String text, Runnable cb) {
            Button btn = new Button(text);
            btn.addCallback(cb);
            btnBox.add(btn);
            invalidateLayout();
            return btn;
        }

        public Button addButton(String text, String ttolTip, Runnable cb) {
            Button btn = addButton(text, cb);
           // btn.setBackground(TextureManager.getTextureInfo("fur_helmet").
            //        texture.getImage(0, 0, 16, 16, new Color((byte)23,(byte) 23, (byte)23,(byte)23),false,Texture.Rotation.NONE));
            btn.setTooltipContent(ttolTip);
            return btn;
        }

        @Override
        protected void layout() {
            btnBox.adjustSize();
            btnBox.setPosition(200, getParent().getHeight() - btnBox.getHeight());
            desk.setSize(getParent().getWidth(), getParent().getHeight());

           // this.adjustSize();
            this.chatFrame.adjustSize();
            this.inventoryDialog.adjustSize();
            this.personDialog.adjustSize();
            this.toolbar.adjustSize();

            //vsyncBox.adjustSize();
            /*vsyncBox.setPosition(
                    getParent().getWidth() - vsyncBox.getWidth(),
                    getParent().getHeight() - vsyncBox.getHeight());*/
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
                        mouseControlCenter.mouseLClick(evt.getMouseX(), evt.getMouseY());
                      /*  if(evt.getModifiers()==64){
                            mouseControlCenter.mouseLClick(evt.getMouseX(), evt.getMouseY());
                        }else if(evt.getModifiers()==128) {
                            mouseControlCenter.mouseRClick(evt.getMouseX(), evt.getMouseY());
                        }*/
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


    }

