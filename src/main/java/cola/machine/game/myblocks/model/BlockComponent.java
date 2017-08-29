package cola.machine.game.myblocks.model;

import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.item.bean.ShapeType;

/**
 * Created by luying on 16/7/23.
 */
public class BlockComponent extends Component {
    private int belongTo = 4; //1 身上 2 手上 3丢弃 4安置 5 背包
    public BlockComponent(float width, float height, float thick){
        super(width,height,thick);

        this.shapeType = ShapeType.BOX;

    }

}
