package cola.machine.game.myblocks.model.ui.html;

import cola.machine.game.myblocks.manager.TextureManager;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.model.ButtonModel;
import de.matthiasmann.twl.model.SimpleButtonModel;

import javax.vecmath.Vector4f;

/**
 * Created by luying on 14-9-16.
 */
public class Button extends HtmlObject implements Cloneable{
    private ButtonModel model;
    private int mouseButton;
    public Button(){
        this.mouseButton = Event.MOUSE_LBUTTON;
        this.canAcceptKeyboardFocus=true;
        model = new SimpleButtonModel();
        this.setColor(new Vector4f(1,1,1,1));
        this.setBackgroundColor(new Vector4f(0,0,0,1));
    }
    public Button(String name){
      this();
        this.innerText = name;
    }
    public void addCallback(Runnable callback) {
        model.addActionCallback(callback);
    }
    public void handle(Event event ){
        System.out.println("进来了");
    }
    protected boolean handleEvent(Event evt) {
       //ss System.out.println("进来了");
        if(evt.getType()==Event.Type.MOUSE_ENTERED){
            //this.setPreBackgroundColor(this.getBackgroundColor());
            setBackgroundImage(new Image(TextureManager.getTextureInfo("button-over")));
            /*this.setPreColor(this.getColor());
            this.setBackgroundColor(new Vector4f(1,1,1,1));
            this.setColor(new Vector4f(0,0,0,1));*/
           // this.innerText="进入了";
            //LogUtil.println(this.id + "进入了");
            Document.needUpdate=true;
            //return true;
        }else if(evt.getType()==Event.Type.MOUSE_EXITED){
            //this.borderColor=new Vector4f(0,0,0,1);
            //this.setColor(this.getPreColor());
            //this.setBackgroundColor(this.getPreBackgroundColor());
            //LogUtil.println(this.id+"离开了了");
            //this.innerText="离开了了";
            Document.needUpdate=true;//return true;
            setBackgroundImage(new Image(TextureManager.getTextureInfo("button")));
        }else if(evt.getType()==Event.Type.MOUSE_MOVED){

        }

        if(evt.isMouseEvent()) {
            boolean hover = (evt.getType() != Event.Type.MOUSE_EXITED) && isMouseInside(evt);
            model.setHover(hover);
            model.setArmed(hover && model.isPressed());
        }
        switch (evt.getType()) {
            case MOUSE_BTNDOWN:

                if(evt.getMouseButton() == mouseButton) {
                    setBackgroundImage(new Image(TextureManager.getTextureInfo("button-down")));  Document.needUpdate=true;//return true;
                    model.setPressed(true);
                    model.setArmed(true);
                }
                break;
            case MOUSE_BTNUP:
                if(evt.getMouseButton() == mouseButton) {
                    setBackgroundImage(new Image(TextureManager.getTextureInfo("button-over")));  Document.needUpdate=true;//return true;
                    model.setPressed(false);
                    model.setArmed(false);
                }
                break;
            case KEY_PRESSED:
                switch (evt.getKeyCode()) {
                    case Event.KEY_RETURN:
                    case Event.KEY_SPACE:
                        if(!evt.isKeyRepeated()) {
                            model.setPressed(true);
                            model.setArmed(true);
                        }
                        return true;
                }
                break;
            case KEY_RELEASED:
                switch (evt.getKeyCode()) {
                    case Event.KEY_RETURN:
                    case Event.KEY_SPACE:
                        model.setPressed(false);
                        model.setArmed(false);
                        return true;
                }
                break;
            case POPUP_OPENED:
                model.setHover(false);
                break;
            case MOUSE_WHEEL:
                // ignore mouse wheel
                return false;
        }
        if(super.handleEvent(evt)) {
            return true;
        }
        // eat all mouse events - except moused wheel which was checked above
        return evt.isMouseEvent();

         //this.borderColor=new Vector4f(1,1,1,1);
       // return true;
    }


}
