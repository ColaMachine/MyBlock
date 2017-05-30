package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.AABB.AABB;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.ByteUtil;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector2f;

public class DoorBlock extends BaseBlock{
    int dir=0;
    //int open=0;
    ItemDefinition itemDefinition;
    public DoorBlock(String name ,int id,boolean isAlpha){
        super(name,id,isAlpha);
    }

    @Override
    public void setValue(int value) {
        int state = ByteUtil.get16_8Value(value);
        //获取condition
        dir = ByteUtil.get4_0Value(state);

       int open= ByteUtil.get8_4Value(state);

        if(open == 1){
            penetration =true;
        }else{
            penetration=false;
        }

    }
    //画出四个面
    public void render(Vao vao,ShapeFace shapeFace,TextureInfo ti ){
        //获取condition
        itemDefinition.getShape().getTopFace();

        int degree = 0;
        if (dir == Constants.BACK) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao, shapeFace.getVertices(), shapeFace.getTexcoords(), shapeFace.getNormals(),
                    shapeFace.getFaces()[0], ti, x, y, z);
        } else {

            if (dir == Constants.FRONT) {

                degree = 180;

            } else if (dir == Constants.LEFT) {
                degree = -90;
            } else if (dir == Constants.RIGHT) {
                degree = 90;
            }
            if (this.isPenetrate()) {
                degree += 90;
            }
            GL_Matrix translateMatrix = GL_Matrix.translateMatrix(0.5f, 0, 0.5f);
            translateMatrix = GL_Matrix.multiply(translateMatrix, GL_Matrix.rotateMatrix(0, -degree * 3.14f / 180, 0));


            translateMatrix = GL_Matrix.multiply(translateMatrix, GL_Matrix.translateMatrix(-0.5f, 0, -0.5f));

            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao, shapeFace.getVertices(), shapeFace.getTexcoords(), shapeFace.getNormals(),
                    shapeFace.getFaces()[0], ti, x, y, z, translateMatrix);
        }
    }
    public boolean use(GL_Vector placePoint){
        return false;
    }
    public boolean beuse(){

        //通过一个通用的方式获得点击的面在哪里
      //  int chunkX = MathUtil.getBelongChunkInt(targetPoint.x);
       // int chunkZ = MathUtil.getBelongChunkInt(targetPoint.z);
        //   TreeBlock treeBlock =new TreeBlock(hitPoint);
        //treeBlock.startPosition=hitPoint;
        //  treeBlock.generator();
      //  int blockX = MathUtil.floor(targetPoint.x) - chunkX * 16;
      //  int blockY = MathUtil.floor(targetPoint.y);
      //  int blockZ = MathUtil.floor(targetPoint.z) - chunkZ * 16;

        int chunkX =chunk.chunkPos.x;
        int chunkZ=chunk.chunkPos.z;
        ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
        cmd.cx = this.getX();
        cmd.cz = this.getZ();
        cmd.cy = this.getY();
        cmd.type = 1;
        this.penetration =! penetration;
        chunk.getBlock(x,y+1,z).setPenetrate(this.penetration);
        /*if(open==0){
            open=1;
        }else{
            open=0;
        }*/

       int newId=  ((penetration?1:0)<<12| this.dir<<8 )| this.id;
        cmd.blockType= newId;
       /* int realBlockType = ByteUtil.get8_0Value(blockType);

        if(realBlockType==ItemType.wood_door.ordinal()){
            //判断当前是开还是关
            int state = ByteUtil.get16_12Value(blockType);
            if(state == 0 ){
                //是关
                blockType = 1<<12| blockType;
            }else{
                blockType = ByteUtil.HEX_0_1_1_1 & blockType;
            }*/
           // cmd.blockType= blockType;
            CoreRegistry.get(Client.class).send(cmd);
            return true;
       // }
    }
    @Override
    public Block clone(){
        DoorBlock block =  new DoorBlock(this.getName(),this.getId(),this.getAlpha());
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
        cmd.cy+=1;
        CoreRegistry.get(Client.class).send(cmd);

    }
}
