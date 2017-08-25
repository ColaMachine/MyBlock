package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.ui.inventory.control.BoxController;
import com.dozenx.game.engine.ui.inventory.view.BoxPanel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

public class BoxBlock extends DirectionBlock {

    public BoxBlock(String name , int id, boolean isAlpha){
        super(name,id,isAlpha);
    }
    public int open=0;
    @Override
    public void setValue(int value) {

        super.setValue(value);



         open= value16_12;



    }
    //画出四个面
    //@Override
    public void renderShader(Vao vao,ShapeFace shapeFace,TextureInfo ti,int x,int y,int z ){
        //获取condition
       // itemDefinition.getShape().getTopFace();
//dir 其实实现已经算好了的


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
            if(open==1) {
                //ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao, ItemManager.getItemDefinition(ItemType.box).getShape().shapeFaceMap.get("open"), ti, x, y, z,translateMatrix);
            }else
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao, shapeFace, ti, x, y, z,translateMatrix);

          /*  ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao, shapeFace.getVertices(), shapeFace.getTexcoords(), shapeFace.getNormals(),
                    shapeFace.getFaces()[0], ti, x, y, z, translateMatrix);*/
        }
    }
    public boolean use(GL_Vector placePoint){
        return false;
    }
    public boolean beuse(){
        CoreRegistry.get(BoxPanel.class).setVisible(true);
        CoreRegistry.get(BoxPanel.class).requestKeyboardFocus();
        Document.needUpdate=true;
        Switcher.isChat=true;
         this.open=1;

        //修改方块的状态为开并拿会物品列表
        ItemBean[] list = CoreRegistry.get(BoxController.class).openBox(this);

        CoreRegistry.get(BoxPanel.class).reload(list);
        Document.needUpdate =true;
        //更新到boxpanel
      //  Document.getInstance().setFocusKeyWidget(CoreRegistry.get(BoxPanel.class));





        //打开状态
        int chunkX =chunk.chunkPos.x;
        int chunkZ=chunk.chunkPos.z;
        ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
        cmd.cx = this.getX();
        cmd.cz = this.getZ();
        cmd.cy = this.getY();
        cmd.type = 1;




        int newId= (open<<12| this.dir<<8 )| this.id;
        cmd.blockType= newId;

        CoreRegistry.get(Client.class).send(cmd);
        return true;
    }
/*

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

    }*/
@Override
public IBlock clone(){
   BoxBlock block =  new BoxBlock(this.getName(),this.getId(),this.getAlpha());
    block.itemDefinition =itemDefinition;
    return block;
}

    @Override
    public void render(ShaderConfig config, Vao vao, float x, float y, float z, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, float x, float y, float z, float width, float height, float thick, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }


}
