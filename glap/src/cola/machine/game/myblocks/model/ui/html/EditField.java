package cola.machine.game.myblocks.model.ui.html;

/**
 * Created by luying on 17/1/12.
 */
public class EditField extends TextField {

    //onMouseMove  鼠标变成竖杠
    //onMouseMoveOut 鼠标变成正常的
    //onkeyDown
    //onclick
    //onblur
    //onfocus

    public void handle(){

    }
    public void onMouseMoveOver(){
        Window.cursor= Window.SELECT;
    }
    public void onMouseMoveOut(){
        Window.cursor= Window.POINT;
    }
    public void onFocus(){
        Window.cursor= Window.SELECT;
    }
    public void onClick(){
        Window.cursor= Window.SELECT;
    }

}
