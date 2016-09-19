package cola.machine.game.myblocks.model.ui;

import cola.machine.game.myblocks.model.ui.Menu.PauseMenu;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.ui.chat.ChatDemo;
import cola.machine.game.myblocks.ui.chat.ChatFrame;
import cola.machine.game.myblocks.ui.gameui.GameUIDemo;
import cola.machine.game.myblocks.ui.inventory.InventoryDemo;
import cola.machine.game.myblocks.ui.inventory.InventoryDialog;
import cola.machine.game.myblocks.ui.inventory.InventoryPanel;
import cola.machine.game.myblocks.ui.inventory.PersonPanel;
import cola.machine.game.myblocks.ui.login.LoginDemo;
import cola.machine.game.myblocks.ui.test.*;
import de.matthiasmann.twl.CallbackWithReason;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.PopupWindow;
import de.matthiasmann.twl.TextArea;
import de.matthiasmann.twl.model.PersistentIntegerModel;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.textarea.HTMLTextAreaModel;
import de.matthiasmann.twl.theme.ThemeManager;
import glapp.GLApp;
import cola.machine.game.myblocks.model.ui.bag.Bag;
import cola.machine.game.myblocks.model.ui.tool.ToolBar;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class NuiManager {
    ToolBar toolbarcontainer;
    Cross cross;
    Bag bag;
    PauseMenu PauseMenu;

    //protected final DisplayMode desktopMode;
    protected VideoSettings.CallbackReason vidDlgCloseReason;
    LWJGLRenderer renderer;
    GuiRootPane root;
    GUI gameGui;
    GUI startGui;
    public  NuiManager() {

      /*  cross = new Cross();
        desktopMode = Display.getDisplayMode();

        bag = new Bag();
        PauseMenu = new PauseMenu();
        toolbarcontainer = new ToolBar();*/
        try{
     /*   Display.setDisplayMode(new DisplayMode(800, 600));//设置高度 宽度
        Display.create();//创建窗口
        Display.setTitle("TWL Login Panel Demo");//设置窗口标题
        Display.setVSyncEnabled(true);//垂直同步

        Mouse.setClipMouseCoordinatesToWindow(false);//修建鼠标位置对应于窗口*/

            //InventoryDemo demo = new InventoryDemo();//创建包裹实力

            renderer = new LWJGLRenderer();//调用lwjgl能力
            renderer.setUseSWMouseCursors(true);

            //ChatDemo chat = new ChatDemo();
            //GameUIDemo gameUI = new GameUIDemo();
           LoginDemo loginDemo = new LoginDemo();
             startGui = new GUI(loginDemo, renderer);


            this.loadTheme();//加载主题

       /* ThemeManager theme = ThemeManager.createThemeManager(
                InventoryDemo.class.getResource("inventory.xml"), renderer);//加载主体
        gui.applyTheme(theme);//应用主体
*/
            //gui.validateLayout();//调整组件位置在窗口中间 验证布局
            //demo.positionFrame();//调整组件 让组件自动调整到自适应大小
        }catch(Exception e) {
            e.printStackTrace();
        }
         CoreRegistry.put(GUI.class,startGui);
    }



    private void loadTheme() throws IOException {
        renderer.syncViewportSize();
        //System.out.println("width="+renderer.getWidth()+" height="+renderer.getHeight());

        long startTime = System.nanoTime();
        // NOTE: this code destroys the old theme manager (including it's cache context)
        // after loading the new theme with a new cache context.
        // This allows easy reloading of a theme for development.
        // If you want fast theme switching without reloading then use the existing
        // cache context for loading the new theme and don't destroy the old theme.





        ThemeManager theme = ThemeManager.createThemeManager(
                LoginDemo.class.getResource("login.xml"), renderer);
        startGui.applyTheme(theme);
    }
    public void startGame()  {
        long startTime = System.nanoTime();
        ThemeManager newTheme = null;
        try {
            newTheme = ThemeManager.createThemeManager(
                    SimpleTest.class.getResource("simple_demo.xml"), renderer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long duration = System.nanoTime() - startTime;
        System.out.println("Loaded theme in " + (duration/1000) + " us");
        root = new GuiRootPane();//创建root pane
        gameGui = new GUI(root, renderer);//创建gui
        //this.root.addGamingComponent();
        gameGui.setSize();
        gameGui.applyTheme(newTheme);
        gameGui.setBackground(newTheme.getImageNoWarning("gui.background"));
        gameGui.validateLayout();
        gameGui.adjustSize();
        CoreRegistry.put(GUI.class,gameGui);
        Switcher.gameState=1;
        //root.reAdjust();
        //root.personPanel.adjustSize();
    }

    public void render(){
        // GLApp.setFog(false);
        //GLApp.pushAttribOrtho();
        // switch to 2D projection
        //GLApp. setOrthoOn();
      //  cross.render();
       // toolbarcontainer.render();
        //bag.render();

       // PauseMenu.render();

       // GLApp.setOrthoOff();
        // return to previous settings
      //  GLApp. popAttrib();
        //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        // System.out.println();
        if(Switcher.gameState==0){
            startGui.update();
        }else if(Switcher.gameState==1) {
            gameGui.update();//刷新gui
        }


        // GLApp.setFog(true);
    }

    private Object makeComplexTooltip() {
        HTMLTextAreaModel tam = new HTMLTextAreaModel();
        tam.setHtml("Hello <img src=\"twl-logo\" alt=\"logo\"/> World");
        TextArea ta = new TextArea(tam);
        ta.setTheme("/htmlTooltip");
        return ta;
    }
}
