package cola.machine.game.myblocks.model;

import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.item.bean.ShapeType;

/**
 * Created by luying on 16/7/23.
 */
public class BodyComponent extends Component {

    public BodyComponent(float width, float height, float thick){
       super(width,height,thick);

        this.shapeType = ShapeType.BOX;

    }


}
