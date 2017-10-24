package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

/**
 * Created by luying on 16/9/25.
 */
public class AttackBall extends  Ball{
    GL_Vector direction;
    float sumDistance=0;
    int distance=16;
    boolean died=false;
    int width;
    float speed;
    int height;

    LivingThingBean from ;


    public AttackBall(int id, GL_Vector position, GL_Vector direction, float speed, Integer itemType, LivingThingBean from) {
        super(id, position, direction, speed, itemType, from);
    }

    public void update(){

    }

    public void render(){

    }


}
