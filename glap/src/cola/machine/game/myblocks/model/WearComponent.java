package cola.machine.game.myblocks.model;

import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.item.bean.ShapeType;

/**
 * Created by luying on 16/7/23.
 */
public class WearComponent extends Component {
    //private int belongTo = 2; //1 身上 2 手上 3丢弃 4安置 5 背包
    public WearComponent(float width, float height, float thick){
        super(width,height,thick);
        belongTo = 1; //1 身上 2 手上 3丢弃 4安置 5 背包
        this.shapeType = ShapeType.CAKE;

    }

}
