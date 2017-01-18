package cola.machine.game.myblocks.model.ui.html;

import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.InfoWindow;
import de.matthiasmann.twl.Widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Document extends HtmlObject {
    public HtmlObject body = new HtmlObject();
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    private static Document document= new Document();
    public static boolean needUpdate=false;
    public Document(){
        this.setId("document");
        body.setId("body");
        this.appendChild(body);
    }
    public static Document getInstance(){

        return document;
    }
	public static HashMap variables=new HashMap();
    private long mouseEventTime;//鼠标事件时间
    public  boolean hasInvalidLayouts=false;
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
	public static  Object var(String name){
		return variables.get(name);
	}
	public static void var(String name,Object value){
		 variables.put(name, value);
	}
    private Event event=new Event();
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
        curTime=System.currentTimeMillis();
        mouseEventTime = curTime;
        tooltipEventTime = curTime;
        event.mouseButton = button;



        // only the previously pressed mouse button
        int prevButtonState = event.getModifiers() & Event.MODIFIER_BUTTON;

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
        event.setModifier(buttonMask, pressed);
        boolean wasPressed = (prevButtonState & buttonMask) != 0;

        if(buttonMask != 0) {
          //  renderer.setMouseButton(button, pressed);
        }

        // don't send new mouse coords when still in drag area
        if(dragActive || prevButtonState == 0) {
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
        if(!dragActive) {//没有拖动
            if(!isInside(mouseX, mouseY)) {//如果不在范围内
                pressed = false;
                mouseClickCount = 0;
                if(wasInside) {//如果之前是在范围内的 那么就是离开的动作
                    sendMouseEvent(Event.Type.MOUSE_EXITED, null);//发送鼠标事件离开
                    wasInside = false;
                }
            } else if(!wasInside) {//如果现在在范围内 而且之前不在范围内
                wasInside = true;
                if(sendMouseEvent(Event.Type.MOUSE_ENTERED, null) != null) {//进入事件发送
                    handled = true;
                }
            }

        }

        if(mouseX != mouseLastX || mouseY != mouseLastY) {//鼠标有移动
            mouseLastX = mouseX;
            mouseLastY = mouseY;

            if(prevButtonState != 0 && !dragActive) {
                if(Math.abs(mouseX - mouseDownX) > DRAG_DIST ||
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

            if(dragActive) {
                /*if(boundDragPopup != null) {
                    // a bound drag is converted to a mouse move
                    assert getTopPane() == boundDragPopup;
                    sendMouseEvent(Event.Type.MOUSE_MOVED, null);
                } else */if(lastMouseDownWidget != null) {
                    // send MOUSE_DRAGGED only to the widget which received the MOUSE_BTNDOWN
                    sendMouseEvent(Event.Type.MOUSE_DRAGGED, lastMouseDownWidget);
                }
            } else if(prevButtonState == 0) {
                if(sendMouseEvent(Event.Type.MOUSE_MOVED, null) != null) {
                    handled = true;
                    return handled;
                }
            }
        }

        if(buttonMask != 0 && pressed != wasPressed) {
            if(pressed) {
                if(dragButton < 0) {
                    mouseDownX = mouseX;
                    mouseDownY = mouseY;
                    dragButton = button;
                    lastMouseDownWidget = sendMouseEvent(Event.Type.MOUSE_BTNDOWN, null);
                } else if(lastMouseDownWidget != null /*&& boundDragPopup == null*/) {
                    // if another button is pressed while one button is already
                    // pressed then route the second button to the widget which
                    // received the first press
                    // but only when no bound drag is active
                    sendMouseEvent(Event.Type.MOUSE_BTNDOWN, lastMouseDownWidget);
                }
            } else if(dragButton >= 0 && (/*boundDragPopup == null ||*/ event.isMouseDragEnd())) {
                // only send the last MOUSE_BTNUP event when a bound drag is active
               /* if(boundDragPopup != null) {
                    if(button == dragButton) {
                        // for bound drag the MOUSE_BTNUP is first send to the current widget under the mouse
                        sendMouseEvent(Event.Type.MOUSE_BTNUP, getWidgetUnderMouse());
                    }
                }*/
                if(lastMouseDownWidget != null) {
                    // send MOUSE_BTNUP only to the widget which received the MOUSE_BTNDOWN
                    sendMouseEvent(Event.Type.MOUSE_BTNUP, lastMouseDownWidget);
                }
            }

            if(lastMouseDownWidget != null) {
                handled = true;
            }

            if(button == Event.MOUSE_LBUTTON && !popupEventOccured) {
                if(!pressed && !dragActive) {
                    if(mouseClickCount == 0 ||
                            curTime - mouseClickedTime > DBLCLICK_TIME ||
                            lastMouseClickWidget != lastMouseDownWidget) {
                        mouseClickedX = mouseX;
                        mouseClickedY = mouseY;
                        lastMouseClickWidget = lastMouseDownWidget;
                        mouseClickCount = 0;
                        mouseClickedTime = curTime;
                    }
                    if(Math.abs(mouseX - mouseClickedX) < DRAG_DIST &&
                            Math.abs(mouseY - mouseClickedY) < DRAG_DIST) {
                        // ensure same click target as first
                        event.mouseX = mouseClickedX;
                        event.mouseY = mouseClickedY;
                        event.mouseClickCount = ++mouseClickCount;
                        mouseClickedTime = curTime;
                        if(lastMouseClickWidget != null) {
                            sendMouseEvent(Event.Type.MOUSE_CLICKED, lastMouseClickWidget);
                        }
                    } else {
                        lastMouseClickWidget = null;
                    }
                }
            }
        }

        if(event.isMouseDragEnd()) {
            if(dragActive) {
                dragActive = false;
                sendMouseEvent(Event.Type.MOUSE_MOVED, null);
            }
            dragButton = -1;
            if(boundDragCallback != null) {
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

        if(target != null) {
            if(!target.isDisabled() || !isMouseAction(event)) {
               // target.handleEvent(target.translateMouseEvent(event));
            }
            return target;
        } else {
            assert !dragActive ;//|| boundDragPopup != null;
            HtmlObject widget = null;
           /* if(activeInfoWindow != null) {//找激活窗口
                if(activeInfoWindow.isMouseInside(event) && setMouseOverChild(activeInfoWindow, event)) {
                    widget = activeInfoWindow;
                }
            }*/
            if(widget == null) {//如国没有toppane的话 就是找根节点
                widget = this.childNodes.get(this.childNodes.size()-1);//getTopPane();
                setMouseOverChild(widget, event);//确定哪个元素是mouseoverchild 响应一下entered 和exied事件
            }
            return widget.routeMouseEvent(event);
        }
    }
    private HtmlObject focusKeyWidget;
    void setFocusKeyWidget(HtmlObject widget) {
        if(focusKeyWidget == null && isFocusKey()) {
            focusKeyWidget = widget;
        }
    }
    private static final int FOCUS_KEY = Event.KEY_TAB;
    boolean isFocusKey() {
        return event.keyCode == FOCUS_KEY &&
                ((event.modifier & (Event.MODIFIER_CTRL|Event.MODIFIER_META|Event.MODIFIER_ALT)) == 0);
    }
    private static final int DRAG_DIST = 3;
    private long mouseClickedTime;
    private HtmlObject lastMouseDownWidget;
    private HtmlObject lastMouseClickWidget;
    private static final int DBLCLICK_TIME = 500;   // ms
}
