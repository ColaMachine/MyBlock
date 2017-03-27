package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.item.bean.ShapeType;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Point3f;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 16/7/23.
 */
public class HandComponent extends Component{
    public HandComponent(float width, float height, float thick){
        super(width,height,thick);

        this.shapeType = ShapeType.CAKE;

    }

}
