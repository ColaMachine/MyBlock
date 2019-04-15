package cola.machine.game.myblocks.model.ui.html;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.StringUtil;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import de.matthiasmann.twl.Event;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.vecmath.Vector4f;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Document extends HtmlObject {
    public HtmlObject body = new HtmlObject();

    public void setHeight(int height) {
        this.height = height;
    }

    private  static Document document =null;
    public static boolean needUpdate = true;

    private Document() {

    }

    public void parseIndexHtml() {
        body.getElementById("root").removeAllChild();
        File file = PathManager.getInstance().getHomePath().resolve("html/index.html").toFile();
        if (file.exists()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //创建一个DocumentBuilder的对象
            try {
                //创建DocumentBuilder对象
                DocumentBuilder db = dbf.newDocumentBuilder();
                //通过DocumentBuilder对象的parser方法加载books.xml文件到当前项目下
                org.w3c.dom.Document document = db.parse(file);
                //获取所有book节点的集合
                NodeList bodyList = document.getElementsByTagName("body");
                NodeList bookList=bodyList.item(0).getChildNodes();
                for (int i = 0; i < bookList.getLength(); i++) {
                    Node node = bookList.item(i);
                    HtmlObject childNode = parseNode(node);

                    if(childNode!=null) {
                        body.getElementById("root").appendChild(childNode);
                    }
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        document.needUpdate=true;
    }

    public HtmlObject parseNode(Node node) {
        HtmlObject htmlObject = new HtmlObject();
        if (node.getNodeName().equals("#Text") || node instanceof com.sun.org.apache.xerces.internal.dom.DeferredTextImpl) {

            HtmlObject textNode =new HtmlObject();
            textNode.innerText=node.getTextContent();
            textNode.setColor(Constants.RGBA_GRAY);
            textNode.setBorderColor(Constants.RGBA_RED);
            textNode.setWidth(50);
            textNode.setHeight(50);
            return textNode;
        }
        NamedNodeMap attrs = node.getAttributes();
//        System.out.println("第 " + (i + 1) + "本书共有" + attrs.getLength() + "个属性");
        //遍历book的属性

        if (attrs != null)
            for (int j = 0; j < attrs.getLength(); j++) {
                //通过item(index)方法获取book节点的某一个属性
                Node attr = attrs.item(j);
                //获取属性名
                System.out.print("属性名：" + attr.getNodeName());
                //获取属性值
                System.out.println("--属性值" + attr.getNodeValue());

                if (attr.getNodeName().equals("style")) {
                    String styleValue = attr.getNodeValue();
                    String styleValueAry[] = styleValue.split(";");
                    for (int styleIndex = 0; styleIndex < styleValueAry.length; styleIndex++) {
                        String keyValueAry[] = styleValueAry[styleIndex].split(":");
                        parseNodeKeyValue(htmlObject, keyValueAry[0], keyValueAry[1]);

                    }
                    //String attrVal = attr.getNodeValue();
                    //htmlObject.setWidth(Integer.valueOf(attrVal));
                }

            }

        //查找子节点
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeName().equals("#Text")) {
                htmlObject.innerText  = nodeList.item(i).getTextContent();
            }
            HtmlObject childNode = parseNode(nodeList.item(i));

            htmlObject.appendChild(childNode);
        }

        return htmlObject;
//
//        if(node.getNodeName().equals("div")){
////            Div div =new Div();
//            //width
//            String style = node.getAttributes("style");
//            Node attr = attrs.item(j);
//            //获取属性名
//            System.out.print("属性名：" + attr.getNodeName());
//            //获取属性值
//            System.out.println("--属性值" + attr.getNodeValue());
//            //height
//            //
//        }
    }

    /**
     * 设置节点属性
     *
     * @param htmlObject
     * @param key
     * @param value
     */
    public void parseNodeKeyValue(HtmlObject htmlObject, String key, String value) {
        if (key.equals("width")) {
            htmlObject.setWidth(Integer.valueOf(value));
        } else if (key.equals("height")) {
            htmlObject.setHeight(Integer.valueOf(value));
        } else if (key.equals("margin-top")) {
            htmlObject.setMarginTop(Short.valueOf(value));
        } else if (key.equals("margin-left")) {
            htmlObject.setMarginLeft(Short.valueOf(value));
        } else if (key.equals("margin-bottom")) {
            htmlObject.setMarginBottom(Short.valueOf(value));
        } else if (key.equals("margin-right")) {
            htmlObject.setMarginRight(Short.valueOf(value));
        } else if (key.equals("padding-top")) {
            htmlObject.setPaddingTop(Short.valueOf(value));
        } else if (key.equals("padding-left")) {
            htmlObject.setPaddingLeft(Short.valueOf(value));
        } else if (key.equals("padding-bottom")) {
            htmlObject.setPaddingBottom(Short.valueOf(value));
        } else if (key.equals("padding-right")) {
            htmlObject.setPaddingRight(Short.valueOf(value));
        } else if (key.equals("background-image")) {

            TextureManager textureManager = CoreRegistry.get(TextureManager.class);

            textureManager.putImage(value, value);
            htmlObject.setBackgroundImage(new Image(new TextureInfo(value)));

        }else if (key.equals("background-color")) {

            if(value.equals("red")){
                htmlObject.setBackgroundColor(Constants.RGBA_RED);
            }/*else if(value.equals("blue")){
                htmlObject.setBorderColor(Constants.RGBA_BLUE);
            }*/else if(value.startsWith("rgba")){
                int start = value.indexOf("(");
                int end =value.indexOf(")");
                String rgabValue = value.substring(start+1,end);
                String rgbaAry[]= rgabValue.split(",");
                htmlObject.setBackgroundColor(new Vector4f(Float.valueOf(rgbaAry[0]) / 256, Float.valueOf(rgbaAry[1]) / 256, Float.valueOf(rgbaAry[2]) / 256, Float.valueOf(rgbaAry[3])));

            }



        }else if (key.equals("color")) {

            if(value.equals("red")){
                htmlObject.setColor(Constants.RGBA_RED);
            }/*else if(value.equals("blue")){
                htmlObject.setBorderColor(Constants.RGBA_BLUE);
            }*/else if(value.startsWith("rgba")){
                int start = value.indexOf("(");
                int end =value.indexOf(")");
                String rgabValue = value.substring(start+1,end);
                String rgbaAry[]= rgabValue.split(",");
                htmlObject.setColor(new Vector4f(Float.valueOf(rgbaAry[0]) / 256, Float.valueOf(rgbaAry[1]) / 256, Float.valueOf(rgbaAry[2]) / 256, Float.valueOf(rgbaAry[3])));

            }



        }else if (key.equals("border-color")) {
            if(value.equals("red")){
                htmlObject.setBorderColor(Constants.RGBA_RED);
            }/*else if(value.equals("blue")){
                htmlObject.setBorderColor(Constants.RGBA_BLUE);
            }*/else if(value.startsWith("rgba")){
                int start = value.indexOf("(");
                int end =value.indexOf(")");
                String rgabValue = value.substring(start+1,end);
                String rgbaAry[]= rgabValue.split(",");
                htmlObject.setBorderColor(new Vector4f(Float.valueOf(rgbaAry[0])/256,Float.valueOf(rgbaAry[1])/256,Float.valueOf(rgbaAry[2])/256,Float.valueOf(rgbaAry[3])));

            }


        }else if (key.equals("border-width")) {
            value = value.replace("px","");
            htmlObject.setBorderWidth(Integer.valueOf(value));

        }
    }

    public static Document getInstance() {

        if(document == null){
            document = new Document();
            document.init();
        }
        return document;
    }

    public void init(){
        this.setId("document");

        this.body =new HtmlObject();
        body.setId("body");
        body.canAcceptKeyboardFocus = true;


        Div div =new Div();
        div.setId("root");
        div.setWidth(500);
        div.setHeight(500);
        body.appendChild(div);

        this.appendChild(body);

        Button button =new Button();
        button .setColor(new Vector4f(0,0,0,1));
        button.innerText="刷新";
        button.setFontSize(34);
        button.textAlign="center";
        //button.setTextAlign("center");
        button.setWidth(100);
        button.setPaddingTop((short)15);
        button.setHeight(50);
        //  button.setBackgroundImage(new Image(TextureManager.getTextureInfo("gridimage")));
        button.setTop(25);
        button.setBorderWidth(2);
        //button.setBorderColor(new Vector4f(1,0,0,1));
        button.setBackgroundImage(new Image(TextureManager.getTextureInfo("button")));
        button.addCallback(new Runnable() {
            @Override
            public void run() {

                document.parseIndexHtml();
                // LogUtil.println("nihao");
            }
        });
        body.appendChild(button);


        this.canAcceptKeyboardFocus = true;

    }

    public static HashMap variables = new HashMap();
    private long mouseEventTime;//鼠标事件时间
    public boolean hasInvalidLayouts = false;

    /*public static List<HtmlObject> elements =new ArrayList<HtmlObject>();
    //public static void appendChild(HtmlObject htmlObject){
        elements.add(htmlObject);
    }*/
    /*public void removeChild(HtmlObject htmlObject){
        elements.remove(htmlObject);
	}*/
    /*public static HtmlObject getElementById(String id){


		for(HtmlObject htmlObject : elements){


            *//*if(id.equals(htmlObject.id)){
				return htmlObject;
			}else {*//*
                HtmlObject childObject =htmlObject.getElementById(id);
                if(childObject!=null){
                    return childObject;
               }
           *//* }*//*

		}
		return null;
	}*/
    public static Object var(String name) {
        return variables.get(name);
    }

    public static void var(String name, Object value) {
        variables.put(name, value);
    }

    private Event event = new Event();
    /* public final boolean handleKey(int keyCode, char keyChar, boolean pressed) {
         event.keyCode = keyCode;
         event.keyChar = keyChar;
         event.keyRepeated = false;

         keyEventTime = curTime;
         if(event.keyCode != Event.KEY_NONE || event.keyChar != Event.CHAR_NONE) {
             event.setModifiers(pressed);

             if(pressed) {
                 keyRepeatDelay = KEYREPEAT_INITIAL_DELAY;
                 return sendKeyEvent(Event.Type.KEY_PRESSED);
             } else {
                 keyRepeatDelay = NO_REPEAT;
                 return sendKeyEvent(Event.Type.KEY_RELEASED);
             }
         } else {
             keyRepeatDelay = NO_REPEAT;
         }

         return false;
     }*/
    private long tooltipEventTime;
    private long curTime;
    private int mouseDownX;
    private int mouseDownY;

    private boolean wasInside;
    private boolean dragActive;
    private int mouseClickCount;
    private int dragButton = -1;
    private int mouseLastX;
    private int mouseLastY;
    private int mouseClickedX;
    private int mouseClickedY;

    public final boolean handleMouse(int mouseX, int mouseY, int button, boolean pressed) {
        curTime = TimeUtil.getNowMills();
        mouseEventTime = curTime;
        tooltipEventTime = curTime;
        event.mouseButton = button;


        // only the previously pressed mouse button
        int prevButtonState = event.getModifiers() & Event.MODIFIER_BUTTON;//l m r 哪个键按下了
        //根据鼠标的按键构造事件
        int buttonMask = 0;
        switch (button) {
            case Event.MOUSE_LBUTTON:
                buttonMask = Event.MODIFIER_LBUTTON;
                break;
            case Event.MOUSE_RBUTTON:
                buttonMask = Event.MODIFIER_RBUTTON;
                break;
            case Event.MOUSE_MBUTTON:
                buttonMask = Event.MODIFIER_MBUTTON;
                break;
        }

        //event 有一个modifer 标识了这个事件是否有按键 pressed代表了是按下还是抬起
        event.setModifier(buttonMask, pressed);
        boolean wasPressed = (prevButtonState & buttonMask) != 0;  //如果一直按着的话 是大于0的

        if (buttonMask != 0) {
            //  renderer.setMouseButton(button, pressed);
        }

        // don't send new mouse coords when still in drag area
        if (dragActive || prevButtonState == 0) {
            event.mouseX = mouseX;//记录 实时的鼠标位置
            event.mouseY = mouseY;
        } else {
            event.mouseX = mouseDownX;//记录鼠标按下的那一刻
            event.mouseY = mouseDownY;//记录鼠标按下的那一刻
        }
        event.mouseX = mouseX;//记录鼠标按下的那一刻
        event.mouseY = mouseY;//记录鼠标按下的那一刻
        boolean handled = dragActive;
       /* for(HtmlObject child:childNodes){
            if(child.isInside(event.mouseX,event.mouseY)){
                if(this.lastChildMouseOver == child){

                }else if(this.lastChildMouseOver != child) {
                    child.mouseOver
                }
                if(this.lastChildMouseOver != child){
                    lastChildMouseOver.routeMouseEvent(event.createSubEvent(Event.Type.MOUSE_EXITED));
                }
                lastChildMouseOver=child;
                return true;
            }
        }*/

        if (!dragActive) {//没有拖动 响应自身的
            //LogUtil.println("" + mouseX +":"+ mouseY);
            if (!isInside(mouseX, mouseY)) {//如果不在范围内
                pressed = false;
                mouseClickCount = 0;
                if (wasInside) {//如果之前是在范围内的 那么就是离开的动作
                    sendMouseEvent(Event.Type.MOUSE_EXITED, null);//发送鼠标事件离开
                    wasInside = false;
                }
            } else if (!wasInside) {//如果现在在范围内 而且之前不在范围内
                wasInside = true;
                if (sendMouseEvent(Event.Type.MOUSE_ENTERED, null) != null) {//进入事件发送
                    handled = true;
                }
            }

        }
        //如果有鼠标移动===========================
        if (mouseX != mouseLastX || mouseY != mouseLastY) {//鼠标有移动
            mouseLastX = mouseX;
            mouseLastY = mouseY;

            if (prevButtonState != 0 && !dragActive) {
                if (Math.abs(mouseX - mouseDownX) > DRAG_DIST ||
                        Math.abs(mouseY - mouseDownY) > DRAG_DIST) {
                    dragActive = true;
                    mouseClickCount = 0;
                    // close the tooltip - it may interface with dragging
                    //hideTooltip();
                    //hadOpenTooltip = false;
                    // grab the tooltip to prevent it from poping up while dragging
                    // the widget can still request a tooltip update
                    //tooltipOwner = lastMouseDownWidget;
                }
            }

            if (dragActive) {
                /*if(boundDragPopup != null) {
                    // a bound drag is converted to a mouse move
                    assert getTopPane() == boundDragPopup;
                    sendMouseEvent(Event.Type.MOUSE_MOVED, null);
                } else */
                if (lastMouseDownWidget != null) {
                    // send MOUSE_DRAGGED only to the widget which received the MOUSE_BTNDOWN
                    sendMouseEvent(Event.Type.MOUSE_DRAGGED, lastMouseDownWidget);
                }
            } else if (prevButtonState == 0) {
                if (sendMouseEvent(Event.Type.MOUSE_MOVED, null) != null) {
                    handled = true;
                    return handled;
                }
            }
        }
        //有按键发生=========================================================
        if (buttonMask != 0 && pressed != wasPressed) {//有按键发生
            if (pressed) {
                if (dragButton < 0) {
                    mouseDownX = mouseX;
                    mouseDownY = mouseY;
                    dragButton = button;
                    lastMouseDownWidget = sendMouseEvent(Event.Type.MOUSE_BTNDOWN, null);
                } else if (lastMouseDownWidget != null /*&& boundDragPopup == null*/) {
                    // if another button is pressed while one button is already
                    // pressed then route the second button to the widget which
                    // received the first press
                    // but only when no bound drag is active
                    sendMouseEvent(Event.Type.MOUSE_BTNDOWN, lastMouseDownWidget);
                }
            } else if (dragButton >= 0 && (/*boundDragPopup == null ||*/ event.isMouseDragEnd())) {
                // only send the last MOUSE_BTNUP event when a bound drag is active
               /* if(boundDragPopup != null) {
                    if(button == dragButton) {
                        // for bound drag the MOUSE_BTNUP is first send to the current widget under the mouse
                        sendMouseEvent(Event.Type.MOUSE_BTNUP, getWidgetUnderMouse());
                    }
                }*/
                if (lastMouseDownWidget != null) {
                    // send MOUSE_BTNUP only to the widget which received the MOUSE_BTNDOWN
                    sendMouseEvent(Event.Type.MOUSE_BTNUP, lastMouseDownWidget);
                }
            }

            if (lastMouseDownWidget != null) {
                handled = true;
            }

            if (button == Event.MOUSE_LBUTTON && !popupEventOccured) {
                if (!pressed && !dragActive) {
                    if (mouseClickCount == 0 ||
                            curTime - mouseClickedTime > DBLCLICK_TIME ||
                            lastMouseClickWidget != lastMouseDownWidget) {
                        mouseClickedX = mouseX;
                        mouseClickedY = mouseY;
                        lastMouseClickWidget = lastMouseDownWidget;
                        mouseClickCount = 0;
                        mouseClickedTime = curTime;
                    }
                    if (Math.abs(mouseX - mouseClickedX) < DRAG_DIST &&
                            Math.abs(mouseY - mouseClickedY) < DRAG_DIST) {
                        // ensure same click target as first
                        event.mouseX = mouseClickedX;
                        event.mouseY = mouseClickedY;
                        event.mouseClickCount = ++mouseClickCount;
                        mouseClickedTime = curTime;
                        if (lastMouseClickWidget != null) {
                            sendMouseEvent(Event.Type.MOUSE_CLICKED, lastMouseClickWidget);
                        }
                    } else {
                        lastMouseClickWidget = null;
                    }
                }
            }
        }
        //如果有拖动===========================================
        if (event.isMouseDragEnd()) {
            if (dragActive) {
                dragActive = false;
                sendMouseEvent(Event.Type.MOUSE_MOVED, null);
            }
            dragButton = -1;
            if (boundDragCallback != null) {
                try {
                    boundDragCallback.run();
                } catch (Exception ex) {
                    Logger.getLogger(Document.class.getName()).log(Level.SEVERE,
                            "Exception in bound drag callback", ex);
                } finally {
                    boundDragCallback = null;
                    // boundDragPopup = null;
                }
            }
        }

        return handled;
    }

    private Runnable boundDragCallback;
    private boolean popupEventOccured;

    //private InfoWindow activeInfoWindow;
    private HtmlObject sendMouseEvent(Event.Type type, HtmlObject target) {
        assert type.isMouseEvent;
        popupEventOccured = false;
        event.type = type;
        event.dragEvent = dragActive;// && (boundDragPopup == null);

        // renderer.setMousePosition(event.mouseX, event.mouseY);

        if (target != null) {//TODO什么时候是不为空的edit field
            if (!target.isDisabled() || !isMouseAction(event)) {
                target.handleEvent(event);
            }
            return target;
        } else {
            assert !dragActive;//|| boundDragPopup != null;
            HtmlObject widget = null;
           /* if(activeInfoWindow != null) {//找激活窗口
                if(activeInfoWindow.isMouseInside(event) && setMouseOverChild(activeInfoWindow, event)) {
                    widget = activeInfoWindow;
                }
            }*/
            if (widget == null) {//如国没有toppane的话 就是找根节点
                widget = this.childNodes.get(this.childNodes.size() - 1);//getTopPane();
                setMouseOverChild(widget, event);//确定哪个元素是mouseoverchild 响应一下entered 和exied事件
            }
            return widget.routeMouseEvent(event);
        }
    }

    private HtmlObject focusKeyWidget;

    public HtmlObject getFocusKeyWidget() {
        return focusKeyWidget;
    }

    public void setFocusKeyWidget(HtmlObject widget) {
        if (focusKeyWidget == null && isFocusKey()) {
            focusKeyWidget = widget;
        }
    }

    private static final int FOCUS_KEY = Event.KEY_TAB;

    boolean isFocusKey() {
        return event.keyCode == FOCUS_KEY &&
                ((event.modifier & (Event.MODIFIER_CTRL | Event.MODIFIER_META | Event.MODIFIER_ALT)) == 0);
    }

    private long mouseClickedTime;
    private HtmlObject lastMouseDownWidget;
    private HtmlObject lastMouseClickWidget;
    private static final int DBLCLICK_TIME = 500;   // ms
    private static final int DRAG_DIST = 3;
    private static final int KEYREPEAT_INITIAL_DELAY = 250; // ms
    private static final int NO_REPEAT = 0;
    private int keyRepeatDelay;

    public final boolean handleKey(int keyCode, char keyChar, boolean pressed) {
        event.keyCode = keyCode;
        event.keyChar = keyChar;
        event.keyRepeated = false;

        keyEventTime = curTime;
        if (event.keyCode != Event.KEY_NONE || event.keyChar != Event.CHAR_NONE) {
            event.setModifiers(pressed);

            if (pressed) {
                keyRepeatDelay = KEYREPEAT_INITIAL_DELAY;
                return sendKeyEvent(Event.Type.KEY_PRESSED);
            } else {
                keyRepeatDelay = NO_REPEAT;
                return sendKeyEvent(Event.Type.KEY_RELEASED);
            }
        } else {
            keyRepeatDelay = NO_REPEAT;
        }

        return false;
    }

    @Override
    public void render() {
        if (Switcher.SHADER_ENABLE) {
            ShaderUtils.finalDraw(ShaderManager.uiShaderConfig, ShaderManager.uiShaderConfig.getVao());//2DImage

        } else {
            super.render();
        }
    }

    @Override
    public void update() {
        super.check();
        if (Document.needUpdate) {

            ShaderManager.uiShaderConfig.getVao().getVertices().clear();//每次重新刷新都要把之前的数据清空

            this.setWidth(Constants.WINDOW_WIDTH);
            this.setHeight(Constants.WINDOW_HEIGHT);
            this.body.setWidth(Constants.WINDOW_WIDTH);
            this.body.setHeight(Constants.WINDOW_HEIGHT);
            OpenglUtils.checkGLError();
            super.resize();
            super.update();
            super.recursivelySetGUI(this);
            if (Switcher.SHADER_ENABLE) {
                ShaderManager.uiShaderConfig.getVao().getVertices().rewind();
                super.buildVao();
                // ShaderUtils.update2dImageVao(ShaderManager.uiShaderConfig);
                ShaderUtils.freshVao(ShaderManager.uiShaderConfig, ShaderManager.uiShaderConfig.getVao());
                OpenglUtils.checkGLError();
            }

            //ShaderUtils.twoDColorBuffer.clear();   OpenglUtils.checkGLError();
            //ShaderManager.uiShaderConfig.getVao().getVertices().clear();   OpenglUtils.checkGLError();

            // this.setPerspective();
            this.render();
            OpenglUtils.checkGLError();
            //div.shaderRender();   OpenglUtils.checkGLError();
            // div2.shaderRender();   OpenglUtils.checkGLError();
            //div3.shaderRender();   OpenglUtils.checkGLError();
            //bag.shaderRender();

            //  ShaderUtils.update2dColorVao();   OpenglUtils.checkGLError();

            Document.needUpdate = false;
        }

    }

    public boolean hasFocusChild() {
        if (this.body.focusChild != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean sendKeyEvent(Event.Type type) {
        assert type.isKeyEvent;
        popupEventOccured = false;
        focusKeyWidget = null;
        event.type = type;
        event.dragEvent = false;
        boolean handled = getTopPane().handleEvent(event);
        if (!handled && focusKeyWidget != null) {
            focusKeyWidget.handleFocusKeyEvent(event);
            handled = true;
        }
        focusKeyWidget = null;  // allow GC
        return handled;
    }


    private HtmlObject getTopPane() {//获取这一层 子节点中位于最上面的那一个widget 一般是倒数第三个
        // don't use potential overwritten methods
        return super.getChildNodes().get(0);//因为最后一个是tooltip 而倒数第二个是一个widget
    }

    private long keyEventTime;

    @Override
    public boolean requestKeyboardFocus() {
        // GUI always has the keyboard focus
        return true;
    }

    @Override
    protected boolean requestKeyboardFocus(HtmlObject child) {
        if (child != null) {
            if (child != getTopPane()) {
                return false;
            }
        }
        return super.requestKeyboardFocus(child);
    }
}
