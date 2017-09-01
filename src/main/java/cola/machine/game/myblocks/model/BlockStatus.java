package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.AABB.AABB;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * 方块实体 如果你想找的是方块说明 那么应该找 blockDefinition
 */
public abstract class BlockStatus{
    //是否是透明
    boolean alpha =false;
    //是否可以通过
    public boolean penetration=false;

    public boolean live =false;
    
  
    //
    public int dir;
    public int id=0;
  


}
