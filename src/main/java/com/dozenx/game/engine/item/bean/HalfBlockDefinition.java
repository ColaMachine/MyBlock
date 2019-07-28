package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.block.BlockParseUtil;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.base.BaseBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.edit.view.GroupBlock;
import com.dozenx.game.engine.item.parser.ItemDoorParser;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * item 模板存放在 只要block的engine 是door 类型的 就用这个definition去定义
 */
public class HalfBlockDefinition extends BlockDefinition {
    private static final Logger logger = LoggerFactory.getLogger(HalfBlockDefinition.class);
    public HashMap<Integer, BaseBlock> idShapeMap = new HashMap<>();

    public HalfBlockDefinition() {
        this.itemTypeId = ItemType.wood_door.id;
        this.getItemModel();

    }

    /**
     * 如果有一个配置文件被解析城了map 可以通过map再转成此类bean
     *
     * @param map
     */
    public void receive(Map map) {
        super.receive(map);
        ItemDoorParser.parse(this, map);
        if(GamingState.player!=null) {
            init();
        }
        // this.setShape(TextureManager.getShape("wood_door"));
    }


    public void init() {

        GroupBlock groupBlock = new GroupBlock();


        BaseBlock half = TextureManager.getShape("wood_half").copy();




        this.setShape(half);
        this.stateBlock();
    }


    /**
     * 缓存住所有的状态对应的模型 数字id 对应  baseblock 会存两份  一份在这个的dorrDefinition 一份再这个的 TextureManager的 idShapeMap中  shape 这个词汇感觉已经不适合了,会混淆一个概念 其实就是block
     */
    public void stateBlock() {
        int rot = 0;
        int face = 0;
        int top = 0;
        int open = 0;
        BaseBlock block = this.getShape();

        for (top = 0; top < 2; top++) {


                BaseBlock blockTemp = block.copy();
                if (top == 1) {
                    blockTemp=this.getShape().copy();
                    blockTemp.addY(0.5f);
                }
                blockTemp.reComputePoints();

                int stateId = BlockParseUtil.getValue(face, ItemType.wood_half.id, top, open);
                blockTemp.id = ItemType.wood_half.id;
                blockTemp.stateId = stateId;
                idShapeMap.put(stateId, blockTemp);//id shape Map 进行映射
                TextureManager.putIdShapeMap(stateId, blockTemp);
        }

    }

    /**
     * @param placePoint
     * @param itemType
     * @param viewDir
     */
    public void use(GL_Vector absolutePlacePoint, Integer itemType, GL_Vector viewDir) {
        //检查上方是否有物体
        int chunkX = MathUtil.getBelongChunkInt(absolutePlacePoint.x);
        int chunkZ = MathUtil.getBelongChunkInt(absolutePlacePoint.z);
        LogUtil.println(absolutePlacePoint.y + "");
        ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
        int blockX = MathUtil.floor(absolutePlacePoint.x) - chunkX * 16;
        int blockY = MathUtil.floor(absolutePlacePoint.y);
        int blockZ = MathUtil.floor(absolutePlacePoint.z) - chunkZ * 16;
        int top = 0;
        cmd.cx = blockX;
        cmd.cz = blockZ;
        cmd.cy = blockY;
        float xiaoShuDian = absolutePlacePoint.y % 1;

        if (xiaoShuDian > 0.5) {
            top = 1;
        }
        cmd.type = 1;
        cmd.blockType = itemType;
        //z+ postion
        if (cmd.cy < 0) {
            LogUtil.err("y can't be <0 ");
        }
        //blockType 应该和IteType类型联系起来
        int newId = BlockParseUtil.getValue(0, ItemType.wood_door.id, top, 0);
        cmd.blockType = newId;//获取加工后的方块状态id
        CoreRegistry.get(Client.class).send(cmd);


    }


}
