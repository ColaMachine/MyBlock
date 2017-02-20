package cola.machine.game.myblocks.model.ui.html;

/**
 * Created by luying on 14-9-16.
 */
public class Label extends HtmlObject implements Cloneable{
    private int display=HtmlObject.INLINE;
    public  Label(String text){
        this.innerText = text;
    }
}
