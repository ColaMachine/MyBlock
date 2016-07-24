package cola.machine.game.myblocks.model.ui;

import cola.machine.game.myblocks.model.ui.Menu.PauseMenu;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatDemo;
import cola.machine.game.myblocks.ui.chat.ChatFrame;
import cola.machine.game.myblocks.ui.inventory.InventoryDemo;
import cola.machine.game.myblocks.ui.inventory.InventoryDialog;
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
    protected boolean closeRequested;
    protected final DisplayMode desktopMode;
    protected VideoSettings.CallbackReason vidDlgCloseReason;
    LWJGLRenderer renderer;
     GuiRootPane root;
    public  NuiManager() {

        cross = new Cross();
        desktopMode = Display.getDisplayMode();

        bag = new Bag();
        PauseMenu = new PauseMenu();
        toolbarcontainer = new ToolBar();
        try{
     /*   Display.setDisplayMode(new DisplayMode(800, 600));//设置高度 宽度
        Display.create();//创建窗口
        Display.setTitle("TWL Login Panel Demo");//设置窗口标题
        Display.setVSyncEnabled(true);//垂直同步

        Mouse.setClipMouseCoordinatesToWindow(false);//修建鼠标位置对应于窗口*/
           root = new GuiRootPane();//创建root pane
        //InventoryDemo demo = new InventoryDemo();//创建包裹实力

         renderer = new LWJGLRenderer();//调用lwjgl能力
        renderer.setUseSWMouseCursors(true);
        gui = new GUI(root, renderer);//创建gui
            //ChatDemo chat = new ChatDemo();

        this.loadTheme();//加载主题
        this.addComponent();
       /* ThemeManager theme = ThemeManager.createThemeManager(
                InventoryDemo.class.getResource("inventory.xml"), renderer);//加载主体
        gui.applyTheme(theme);//应用主体
*/
        //gui.validateLayout();//调整组件位置在窗口中间 验证布局
        //demo.positionFrame();//调整组件 让组件自动调整到自适应大小
         }catch(Exception e) {
            e.printStackTrace();
        }
        CoreRegistry.put(GUI.class,gui);
    }

    private void addComponent(){
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

        InventoryDialog inventoryPanel = new InventoryDialog();//10行5列
        root.desk.add(inventoryPanel);
        inventoryPanel.adjustSize();
        inventoryPanel.center(0.5f, 0.5f);
        inventoryPanel.addCloseCallback();
        inventoryPanel.setVisible(false);
       /* TextAreaDemoDialog2 fTextAreaTest = new TextAreaDemoDialog2();
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

        root.addButton("Exit", new Runnable() {
            public void run() {
                closeRequested = true;
            }
        });
        /*root.addButton("Info", "Shows TWL license", new ToggleFadeFrame(fInfo)).setTooltipContent(makeComplexTooltip());
        */root.addButton("inventory", "inventory", new ToggleFadeFrame(inventoryPanel)).setTooltipContent(makeComplexTooltip());
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
                popup.add(game);
                popup.openPopupCentered();
            }
        });*/

        //fInfo.requestKeyboardFocus();
    }

    private void loadTheme() throws IOException {
        renderer.syncViewportSize();
        System.out.println("width="+renderer.getWidth()+" height="+renderer.getHeight());

        long startTime = System.nanoTime();
        // NOTE: this code destroys the old theme manager (including it's cache context)
        // after loading the new theme with a new cache context.
        // This allows easy reloading of a theme for development.
        // If you want fast theme switching without reloading then use the existing
        // cache context for loading the new theme and don't destroy the old theme.
        ThemeManager newTheme = ThemeManager.createThemeManager(
                SimpleTest.class.getResource("simple_demo.xml"), renderer);
        long duration = System.nanoTime() - startTime;
        System.out.println("Loaded theme in " + (duration/1000) + " us");



        gui.setSize();
        gui.applyTheme(newTheme);
        gui.setBackground(newTheme.getImageNoWarning("gui.background"));
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
       // System.out.println();
        gui.update();//刷新gui
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
