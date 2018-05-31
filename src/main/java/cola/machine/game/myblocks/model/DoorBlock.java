package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

public class DoorBlock extends DirectionFourBlock {

    public DoorBlock(String name ,int id,boolean isAlpha){
        super(name,id,isAlpha);
    }
    int isTop;
    int open;
    int face;
    public void setValue(int face,int open,int isTop){
        //super();
        this.isTop =isTop;
        this.open=open;
        this.face = face;
    }
    @Override
    public void setValue(int value) {
        super.setValue(value);


          isTop = value>>10 &1;
         open= value >> 9&1;



    }

    public int getValue(){

          return   isTop<<11| open <<10 |dir<<8| ItemType.wood_door.id;



    }
    //画出四个面
    //@Override
    public void renderShader(Vao vao,ShapeFace shapeFace,TextureInfo ti,int x,int y,int z){
        //获取condition
        //itemDefinition.getShape().getTopFace();
//1111 1111 1111 1111  1111 1 物品开关 1上下方向 11 物品4个方向  1111 1111 物品id
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
            if (this.open==1) {
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
        cmd.cx = (int)this.getX();
        cmd.cz = (int)this.getZ();
        cmd.cy = (int)this.getY();
        cmd.type = 1;
        this.open =Math.abs( this.open -1)  ;
        this.penetration =! penetration;
        chunk.getBlock(cmd.cx,cmd.cy+1,cmd.cz).setPenetrate(this.penetration);
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
    public IBlock clone(){
        DoorBlock block =  new DoorBlock(this.getName(),this.getId(),this.getAlpha());
        block.itemDefinition =itemDefinition;
        return block;
    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, float parentX,float parentY,float parentZ, float childX,float childY,float childZ, float width, float height, float thick, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }

}
