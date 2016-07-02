package cola.machine.game.myblocks.model.ui;

import cola.machine.game.myblocks.model.ui.Menu.PauseMenu;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatDemo;
import cola.machine.game.myblocks.ui.chat.ChatFrame;
import cola.machine.game.myblocks.ui.inventory.InventoryDemo;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import glapp.GLApp;
import cola.machine.game.myblocks.model.ui.bag.Bag;
import cola.machine.game.myblocks.model.ui.tool.ToolBar;
import org.lwjgl.opengl.GL11;

public class NuiManager {
    ToolBar toolbarcontainer;
    Cross cross;
    Bag bag;
    PauseMenu PauseMenu;
    public  NuiManager() {

        cross = new Cross();

        bag = new Bag();
        PauseMenu = new PauseMenu();
        toolbarcontainer = new ToolBar();
        try{
     /*   Display.setDisplayMode(new DisplayMode(800, 600));//设置高度 宽度
        Display.create();//创建窗口
        Display.setTitle("TWL Login Panel Demo");//设置窗口标题
        Display.setVSyncEnabled(true);//垂直同步

        Mouse.setClipMouseCoordinatesToWindow(false);//修建鼠标位置对应于窗口*/

        InventoryDemo demo = new InventoryDemo();//创建包裹实力

        LWJGLRenderer renderer = new LWJGLRenderer();//调用lwjgl能力
         gui = new GUI(demo, renderer);//创建gui
            ChatDemo chat = new ChatDemo();



        ThemeManager theme = ThemeManager.createThemeManager(
                InventoryDemo.class.getResource("inventory.xml"), renderer);//加载主体
        gui.applyTheme(theme);//应用主体

        gui.validateLayout();//调整组件位置在窗口中间 验证布局
        demo.positionFrame();//调整组件 让组件自动调整到自适应大小
         }catch(Exception e) {
            e.printStackTrace();
        }
        CoreRegistry.put(GUI.class,gui);
    }
    GUI gui;
    public void render(){
       // GLApp.setFog(false);
    	   GLApp.pushAttribOrtho();
           // switch to 2D projection
           GLApp. setOrthoOn();
        cross.render();
        toolbarcontainer.render();
        bag.render();

        PauseMenu.render();

          GLApp.setOrthoOff();
        // return to previous settings
       GLApp. popAttrib();
       //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        gui.update();//刷新gui
       // GLApp.setFog(true);
    }
}
