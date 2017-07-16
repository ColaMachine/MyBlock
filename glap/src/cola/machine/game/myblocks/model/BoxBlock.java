package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.engine.ui.inventory.control.BoxController;
import com.dozenx.game.engine.ui.inventory.view.BoxPanel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import java.util.List;

public class BoxBlock extends BaseBlock{
   public int dir=0;
    //int open=0;
    ItemDefinition itemDefinition;
    public BoxBlock(String name , int id, boolean isAlpha){
        super(name,id,isAlpha);
    }
    public int open=0;
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
        CoreRegistry.get(BoxPanel.class).setVisible(true);
        CoreRegistry.get(BoxPanel.class).requestKeyboardFocus();
        Document.needUpdate=true;
        Switcher.isChat=true;

        //修改方块的状态为开并拿会物品列表
        ItemBean[] list = CoreRegistry.get(BoxController.class).openBox(this);

        CoreRegistry.get(BoxPanel.class).reload(list);
        Document.needUpdate =true;
        //更新到boxpanel
      //  Document.getInstance().setFocusKeyWidget(CoreRegistry.get(BoxPanel.class));
        return true;
    }
    @Override
    public Block clone(){
        BoxBlock block =  new BoxBlock(this.getName(),this.getId(),this.getAlpha());
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
