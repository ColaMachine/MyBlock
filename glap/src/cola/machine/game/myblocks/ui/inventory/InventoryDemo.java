/*
 * Copyright (c) 2008-2012, Matthias Mann
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Matthias Mann nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package cola.machine.game.myblocks.ui.inventory;

import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatFrame;
import cola.machine.game.myblocks.ui.chat.CtrlFrame;
import cola.machine.game.myblocks.ui.test.FadeFrame;
import cola.machine.game.myblocks.ui.test.SimpleTest;
import cola.machine.game.myblocks.ui.test.ToggleFadeFrame;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.textarea.HTMLTextAreaModel;
import de.matthiasmann.twl.theme.ThemeManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * A simple login panel
 * 
 * @author Matthias Mann
 */
public class InventoryDemo extends DesktopArea {
    
    public static void main(String[] args) {
        try {
            String library_path = System.setProperty("org.lwjgl.librarypath","/home/colamachine/workspace/MyBlock/bin/natives/linux");

            Display.setDisplayMode(new DisplayMode(800, 600));//设置高度 宽度
            Display.create();//创建窗口
            Display.setTitle("TWL Login Panel Demo");//设置窗口标题
            Display.setVSyncEnabled(true);//垂直同步

            Mouse.setClipMouseCoordinatesToWindow(false);//修建鼠标位置对应于窗口
            
            InventoryDemo demo = new InventoryDemo();//创建包裹实力
            
            LWJGLRenderer renderer = new LWJGLRenderer();//调用lwjgl能力
            GUI gui = new GUI(demo, renderer);//创建gui

            ThemeManager theme = ThemeManager.createThemeManager(
                    InventoryDemo.class.getResource("inventory.xml"), renderer);//加载主体
            gui.applyTheme(theme);//应用主体

            gui.validateLayout();//调整组件位置在窗口中间 验证布局
            demo.positionFrame();//调整组件 让组件自动调整到自适应大小
            
            while(!Display.isCloseRequested() && !demo.quit) {//主要loop
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

                gui.update();//刷新gui
                Display.update();
            }

            gui.destroy();//组件销毁
            theme.destroy();//主体销毁
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        Display.destroy();
    }

    final FPSCounter fpsCounter;
    final FadeFrame frame;
    final InventoryPanel inventoryPanel;
    
    boolean quit;

    private final ChatFrame chatFrame;
    MouseControlCenter mouseControlCenter;
    public InventoryDemo() {
        fpsCounter = new FPSCounter();//fps显示组件
        this.setCanAcceptKeyboardFocus(true);
        inventoryPanel = new InventoryPanel(10, 5);//10行5列
        CtrlFrame ctrlFrame =new CtrlFrame();
        frame = new FadeFrame();//创建包裹框
        frame.setTitle("Inventory");//整个包裹的标题
        frame.setResizableAxis(ResizableFrame.ResizableAxis.BOTH);//是否可以调整大小
        frame.add(inventoryPanel);//添加panel
        frame.addCloseCallback(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(false);
            }
        });
        add(fpsCounter);//添加fps
       // ctrlFrame.setTheme("ctrlframe");
       // add(ctrlFrame);
        add(frame);//添加包裹
        chatFrame = new ChatFrame();
        chatFrame.setTheme("chatframe");
        add(chatFrame);

        chatFrame.setSize(400, 200);
        chatFrame.setPosition(10, 350);
        mouseControlCenter=   CoreRegistry.get(MouseControlCenter.class);

        final SimpleTest.RootPane root = new SimpleTest.RootPane();
        //增加底部按钮
        root.addButton("包裹", "包裹", new ToggleFadeFrame(frame)).setTooltipContent(makeComplexTooltip());
        add(root);

    }
    private Object makeComplexTooltip() {
        HTMLTextAreaModel tam = new HTMLTextAreaModel();
        tam.setHtml("Hello <img src=\"twl-logo\" alt=\"logo\"/> World");
        TextArea ta = new TextArea(tam);
        ta.setTheme("/htmlTooltip");
        return ta;
    }
    public void positionFrame() {
        frame.adjustSize();//调整包裹大小 刚好包裹格子
        frame.setPosition(
                getInnerX() + (getInnerWidth() - frame.getWidth())/2,
                getInnerY() + (getInnerHeight() - frame.getHeight())/2);//调整位置
    }
    
    @Override
    protected void layout() {
        super.layout();
        
        // fpsCounter is bottom right
        fpsCounter.adjustSize();//fps窗体调整大小
        fpsCounter.setPosition(
                getInnerRight() - fpsCounter.getWidth(),
                getInnerBottom() - fpsCounter.getHeight());//右下角
    } private boolean dragActive;
    @Override
    protected boolean handleEvent(Event evt) {

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

                System.out.println("ctrlframe:"+evt.getKeyCode());
            }
            return true;
        }else if(evt.isKeyPressedEvent()){
            System.out.println("key pressed "+evt.getType());
            mouseControlCenter.keyDown(evt.getKeyCode());
        }else{
            System.out.println("nothing event type"+evt.getType());
        }
        return false;


    }
}
