package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.Vao;
import core.log.LogUtil;
import glmodel.GL_Vector;

public class CopyDownBlock extends BaseBlock{
    int dir=0;
    int open=0;
    ItemDefinition itemDefinition;
    public CopyDownBlock(String name, int id, boolean isAlpha){
        super(name,id,isAlpha);
    }

    @Override
    public void setValue(int value) {


    }
    //画出四个面
    public void render(Vao vao,ShapeFace shapeFace,TextureInfo ti ){

    }
    public boolean use(GL_Vector placePoint){
        return false;
    }
    public boolean beuse(){
        //调用真正的block
        chunk.getBlock(this.x,this.y-1,this.z).beuse();
        //那开关状态也要和block 同步的
        return true;
    }
    @Override
    public IBlock clone(){
        CopyDownBlock block =  new CopyDownBlock(this.getName(),this.getId(),this.getAlpha());
        block.itemDefinition =itemDefinition;
        return block;
    }

    public void beAttack(){
        int chunkX = chunk.chunkPos.x;
        int chunkZ = chunk.chunkPos.z;

        ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
        cmd.cx = x;//this.getX();
        cmd.cy =y;//this.getX();
        cmd.cz =z;// this.getZ();

        if(cmd.cy<0){
            LogUtil.err("y can't be <0 ");
        }
        cmd.type = 2;
        //blockType 应该和IteType类型联系起来
        cmd.blockType = 0;

        CoreRegistry.get(Client.class).send(cmd);
        cmd.cy-=1;
        CoreRegistry.get(Client.class).send(cmd);

    }

    @Override
    public void update(float x, float y, float z, float width, float height, float thick) {

    }

    @Override
    public void update() {

    }

    @Override
    public BaseBlock copy() {
        return null;
    }

    @Override
    public void render(ShaderConfig config, Vao vao, int x, int y, int z, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }
}
