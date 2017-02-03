package cola.machine.game.myblocks.model.ui.html;

import cola.machine.game.myblocks.log.LogUtil;
import de.matthiasmann.twl.Event;

import javax.vecmath.Vector4f;

/**
 * Created by luying on 14-9-16.
 */
public class Label extends HtmlObject implements Cloneable{
    private int display=HtmlObject.INLINE;
    public  Label(String text){
        this.innerText = text;
    }
}
