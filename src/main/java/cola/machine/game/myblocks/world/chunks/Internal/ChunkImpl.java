package cola.machine.game.myblocks.world.chunks.Internal;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.block.BlockDefManager;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.Region3i;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.model.CopyBlock;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkBlockIterator;
import cola.machine.game.myblocks.world.chunks.ChunkConstants;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraDenseArray16Bit;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.edit.EditEngine;
import com.dozenx.game.engine.edit.view.AnimationBlock;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.item.BlockUtil;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.ByteUtil;
import com.dozenx.util.FileUtil;
import core.log.LogUtil;
import glapp.GLApp;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLU;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkImpl implements Chunk {
    int currentBlockType = 0;
    int faceIndex = 0;
    public int displayId = 0;
    public int alphaDisplayId = 0;
    public IntBuffer vetices = BufferUtils.createIntBuffer(14);
    public int count = 0;
    public Vao vao = null;
    public Vao alphaVao = null;
    private TeraArray blockData;
    public IntBuffer normalizes = BufferUtils.createIntBuffer(4);
    List<BaseBlock> animationBlock = new ArrayList<>();
    HashMap<Integer, IBlock> blockMap = new HashMap<>();// this map store the
                                                        // block has state

    // HashMap<Integer,BlockStatus> blockStatusMap =new HashMap<>();//this map
    // store the block has state

    // public FloatBuffer veticesBuffer = BufferUtils.createFloatBuffer(196608);
    public ChunkImpl(Vector3i chunkPos) {
        this(chunkPos.x, chunkPos.y, chunkPos.z);
    }

    public final Vector3i chunkPos = new Vector3i();
    private BlockManager blockManager;
    private Region3i region;

    public ChunkImpl() {
        blockData = new TeraDenseArray16Bit(getChunkSizeX(), getChunkSizeY(), getChunkSizeZ());
        // sunlightData =
        // c.getSunlightDataEntry().factory.create(getChunkSizeX(),
        // getChunkSizeY(), getChunkSizeZ());
        // lightData = c.getLightDataEntry().factory.create(getChunkSizeX(),
        // getChunkSizeY(), getChunkSizeZ());
        // extraData = c.getExtraDataEntry().factory.create(getChunkSizeX(),
        // getChunkSizeY(), getChunkSizeZ());
        blockManager = CoreRegistry.get(BlockManager.class);
        /*
         * if(GamingState.player!=null){ vao = new
         * Vao(102400,ShaderManager.terrainShaderConfig); alphaVao = new
         * Vao(102400,ShaderManager.terrainShaderConfig); }else{ vao = new
         * Vao(102400,null); alphaVao = new Vao(102400,null); }
         */
    }

    public ChunkImpl(int x, int y, int z) {
        this();

        chunkPos.x = x;
        chunkPos.y = y;
        chunkPos.z = z;
        region = Region3i.createFromMinAndSize(
                new Vector3i(x * ChunkConstants.SIZE_X, y * ChunkConstants.SIZE_Y, z * ChunkConstants.SIZE_Z),
                ChunkConstants.CHUNK_SIZE);
    }

    @Override
    public Vector3i getPos() {
        // VIP Auto-generated method stub
        return this.chunkPos;
    }

    @Override
    public IBlock getBlock(Vector3i pos) {
        // VIP Auto-generated method stub
        return null;
    }

    /**
     * 获取指定位置的
     * @param x
     * @param y
     * @param z
     * @return
     */
    @Override
    public IBlock getBlock(int x, int y, int z) {
        // VIP Auto-generated method stub
        // return null;
       
        int blockValue = BlockUtil.getRealBlockId(blockData.get(x, y, z));


        if(GamingState.player==null){
            
        }
        if(blockValue == 0 ){
            return null;
        }
        IBlock block = blockMap.get(blockData.getIndex(x, y, z));// .getBlock((short)
                                                                 // blockValue);
        if (block == null) {
            block = blockManager.getBlock(blockValue);
            if(block == null){
                LogUtil.err("the block is not in blockManager 这个方块没有在blockmanaager中登记过:"+blockValue);
            }
        }
        if (block != null && blockValue == ItemType.copy_down.id) {
            block = blockMap.get(blockData.getIndex(x, y - 1, z));
        }//().id=blockValue;//TODO 暂时修正在服务端取出来的block id 都是0 应该是item load 的时候没处理好
        return block;
        // return new BaseBlock();
    }

    public TeraArray getBlockData() {
        return blockData;
    }

    @Override
    public void setBlockData(TeraArray blockData) {
        this.blockData = blockData;
    }

    @Override // 返回原来的block 类型id 设置当前的block进入数据数组
    public IBlock setBlock(int x, int y, int z, IBlock block) {

        try {

            /*
             * BaseBlock baseBlock=(BaseBlock) block;
             * 
             * 
             * if(baseBlock .width>1|| baseBlock .thick>1 || baseBlock
             * .height>1){ BaseBlock copyBlock = new CopyBlock(baseBlock);
             * for(int _x=0;_x<baseBlock.width;_x++){ for(int
             * _z=0;_z<baseBlock.height;_z++){ for(int
             * _y=0;_y<baseBlock.height;_y++){ setBlock( x+_x,y+_y,z+_z,
             * copyBlock);
             * 
             * } }
             * 
             * 
             * } }
             * 
             * setBlock(x,y,z,block);
             */
            // int realId = ByteUtil.get8_0Value(blockId);//获得真实的blockid
            blockData.set(x, y, z, block.getId());// 设置或者
            blockMap.put(blockData.getIndex(x, y, z), block);
            if (block instanceof AnimationBlock) {
                AnimationBlock group = ((AnimationBlock) block).copy();
                group.x = x;
                group.y = y;
                group.z = z;
                if (group.animations != null && group.animations.size() > 0) {

                    animationBlock.add(((AnimationBlock) block));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * @Override//返回原来的block 类型id 设置当前的block进入数据数组 public void setBlock(int x,
     * int y, int z, Integer blockid) {
     */
    /*
     * //int oldValue = blockData.set(x, y, z, blockid);
     * 
     * IBlock block = blockManager.getBlock(blockid); if(block instanceof
     * ColorGroup){ ColorGroup group = (ColorGroup) block;
     * if(group.animations!=null && group.animations.size() > 0){
     * animationBlock.add(group); } }
     */
    // this.setBlock(x, y, z, blockId);
    /*
     * if (oldValue != block.id) {
     *//*
       * if (!block.isLiquid()) { setLiquid(x, y, z, new LiquidData()); }
       *//*
         * } return blockManager.getBlock((short) oldValue);
         */
    // }
    @Override
    public void setBlockStatus(int x, int y, int z, int dir) {
        BaseBlock block = (BaseBlock) blockMap.get(blockData.getIndex(x, y, z));
        if (block == null) {
            if (GamingState.player== null) {
                // 创建block
                block = new ColorBlock();
                block.dir = dir;
                blockMap.put(blockData.getIndex(x, y, z), block);

            } else {
                
            }
        } else {

            // if (block != null && block instanceof ColorGroup) {
            block.dir = dir;
            block.reComputePoints();
            if (GamingState.player!= null) {
            
            if (block.width > 1 || block.thick > 1 || block.height > 1) {
                BaseBlock copyBlock = new CopyBlock(block);
                for (int _x = 0; _x < block.width; _x++) {
                    for (int _z = 0; _z < block.thick; _z++) {
                        for (int _y = 0; _y < block.height; _y++) {
                            if (_x == 0 && _y == 0 && _z == 0) {
                                continue;
                            }
                            if(block.dir==0){
                                blockMap.put(blockData.getIndex(x + _x, y + _y, z + _z), copyBlock);
                                blockData.set(x + _x, y + _y, z + _z, copyBlock.id);// 设置或者
                                
                                LogUtil.println("add"+(x + _x)+","+(y + _y )+ ","+(z + _z));
                            }else if(block.dir==1){

                                //清理原来的copydown
                                blockData.set(x +  _x, y + _y, z +_z, 0);// 设置或者
                                LogUtil.println("clear"+(x + _x)+","+(y + _y )+ ","+(z + _z));
                                blockMap.put(blockData.getIndex(x +  _z, y + _y, z +_x), copyBlock);
                                
                                LogUtil.println("addnow "+(x +  _z)+","+ (y + _y)+ ","+ (z +_x));
                                blockData.set(x +  _z, y + _y, z +_x, copyBlock.id);// 设置或者
                            }
                            
                           
                        }
                    }

                }
            }
            }
            // }
        }
    }

    @Override
    public void setBlock(int x, int y, int z, int blockId) {
        try {

            // int realId = ByteUtil.get8_0Value(blockId);//获得真实的blockid
            blockData.set(x, y, z, blockId);// 设置或者

            if (GamingState.player != null) {
                if (blockId == 0) {
                    BaseBlock block = (BaseBlock) blockMap.get(blockData.getIndex(x, y, z));
                    if (block != null && block instanceof AnimationBlock) {
                        this.animationBlock.remove(block);
                        blockMap.remove(blockData.getIndex(x, y, z));
                    }

                } else {
                    // 得到对象
                    IBlock block = blockManager.getBlock(blockId);
                    // 复制对象
                   BaseBlock baseBlock = ((BaseBlock) block);//.copy();
                  //  baseBlock.reComputePoints();容易发生堆溢出
                    // 判断对象
                    if (baseBlock instanceof AnimationBlock) {
                        AnimationBlock group = ((AnimationBlock) baseBlock);
                        group.x = x;
                        group.y = y;
                        group.z = z;
                        if (group.animations != null && group.animations.size() > 0) {
                            // BaseBlock baseblock = group.copy();
                            animationBlock.add(group);
                            blockMap.put(blockData.getIndex(x, y, z), group);
                        } else {
                            blockMap.put(blockData.getIndex(x, y, z), baseBlock);
                        }

                        // 如果大于0的话 还有延展  这里有一个问题是 一旦物体发生了旋转之后他的占用位置
                        if (baseBlock.width > 1 || baseBlock.thick > 1 || baseBlock.height > 1) {
                            BaseBlock copyBlock = new CopyBlock(baseBlock);
                            for (int _x = 0; _x < baseBlock.width; _x++) {
                                for (int _z = 0; _z < baseBlock.thick; _z++) {
                                    for (int _y = 0; _y < baseBlock.height; _y++) {
                                        if (_x == 0 && _y == 0 && _z == 0) {
                                            continue;
                                        }
                                        //if(baseBlock.dir==1){
                                            blockMap.put(blockData.getIndex(x + _x, y + _y, z + _z), copyBlock);
                                        //}
                                        LogUtil.println("add"+(x + _x)+","+(y + _y) + ","+(z + _z));
                                        blockData.set(x + _x, y + _y, z + _z, copyBlock.id);// 设置或者
                                    }
                                }

                            }
                        }

                        // setBlock(x,y,z,block);

                    }else if(baseBlock instanceof  ColorBlock){
                        if(((ColorBlock) baseBlock).isLight){//如果是灯光就加入到灯光秀里
                            GamingState.editEngine.lightBlockHashMap.put(new GL_Vector(this.chunkPos.x*ChunkConstants.SIZE_X+x,y,this.chunkPos.z*ChunkConstants.SIZE_Z+z),new WeakReference<ColorBlock>((ColorBlock)baseBlock));
                        }
                       // blockMap.put(blockData.getIndex(x, y, z), baseBlock);//容易发生堆栈溢出
                    } else{
                       // blockMap.put(blockData.getIndex(x, y, z), baseBlock);//容易发生堆栈溢出
                    }
                }
                // if(blockId > 0)
                /*
                 * if(realId==ItemType.wood_door.id || realId ==
                 * ItemType.box.id){ LogUtil.println("is ad special block");
                 * 
                 * //if (realId ==ItemType.wood_door.id ) { //IBlock block =
                 * blockManager.getBlock(realId).clone();
                 * block.setValue(blockId); block.set(x,y,z);
                 * block.setChunk(this); blockMap.put(blockData.getIndex(x, y,
                 * z), block);
                 * 
                 * //} }
                 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果是特殊block的话 放入特殊map中 下次取或者修改都修改里面的值

    }

    TextureInfo ti = null;// TextureManager.getTextureInfo("mantle");

    /**
     * 判断对方和自己 如果一个是alpha 一个是非透明的 就需要
     *
     * @param selfType
     * @param blockType
     * @return
     */
    public boolean needToPaint(int selfType, int blockType) {
        if (blockType > 256) {
            blockType = ByteUtil.get8_0Value(blockType);
        }
        if (blockType == 0 || selfType == 0) {
            return true;
        }
        if (blockType == ItemType.wood_door.id || selfType == ItemType.wood_door.id) {
            return true;
        }
        // 如果一个是透明的 另一个是不透明的 就需要绘制
        // 如果两个都是不透明的就不需要绘制
        IBlock selfBlock = blockManager.getBlock(selfType);
        IBlock otherBlock = blockManager.getBlock(blockType);
        if (selfBlock == null || otherBlock == null) {
            LogUtil.err("block is null blockType:" + selfType);
            return false;
        }
        if ((!selfBlock.getAlpha() && !otherBlock.getAlpha())) {
            return false;
        } else if (selfBlock.getAlpha() && otherBlock.getAlpha()) {
            return false;
        } else if (selfBlock.getAlpha() && !otherBlock.getAlpha()) {// 如果自身是Alpha
                                                                    // 对方不是alpha
                                                                    // 也不需要绘制
            return false;
        } else {
            return true;
        }
        /*
         * if((selfType==6||selfType==3) && blockType==0){ return true; }
         * if(selfType!=6 && (blockType==0|| blockType==6|| blockType==3)){
         * return true; }
         */

        // return false;
    }

    public boolean judgeAlpha(int type) {
        if (3 == type || 0 == type || 6 == type) {
            return true;
        }
        return false;
    }

    int state = 0;

    public void buildAlpha() {

        alphaDisplayId = GLApp.beginDisplayList();

        // block.render();
        // GL11.glBegin(GL11.GL_QUADS);
        for (int x = 0; x < this.getChunkSizeX(); x++) {
            for (int z = 0; z < this.getChunkSizeZ(); z++) {
                for (int y = 0; y < this.getChunkSizeY(); y++) {
                    int i = 0;
                    try {
                        i = blockData.get(x, y, z);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (i != 6 && i != 3)
                        continue;
                    // 这一步把i转变成256格式数据

                    if (currentBlockType > 256) {

                        state = ByteUtil.get16_8Value(i);
                        i = currentBlockType = ByteUtil.get8_0Value(i);
                    }
                    // 获得当前方框方向

                    if (i > 0) {// System.out.printf("%d %d %d /n\n",x,y,z);
                        // 判断上面
                        if (y < this.getChunkSizeY() - 1) {
                            if (needToPaint(i, blockData.get(x, y + 1, z))) {
                                addThisTop(x, y, z);
                            } else {

                            }
                        } else {
                            addThisTop(x, y, z);
                        }

                        // 判断下面
                        if (y > 0) {
                            if (needToPaint(i, blockData.get(x, y - 1, z))) {
                                addThisBottom(x, y, z);
                            }
                        } else {
                            addThisBottom(x, y, z);
                        }

                        // 判断左面
                        if (x > 0) {
                            if (needToPaint(i, blockData.get(x - 1, y, z))) {
                                addThisLeft(x, y, z);
                            }
                        } else {
                            // TODO
                            addThisLeft(x, y, z);
                        }

                        // 判断右面

                        if (x < this.getChunkSizeX() - 1) {
                            if (needToPaint(i, blockData.get(x + 1, y, z))) {
                                addThisRight(x, y, z);
                            }
                        } else {// TODO
                            addThisRight(x, y, z);
                        }

                        // 前面

                        if (z < this.getChunkSizeZ() - 1) {
                            if (needToPaint(i, blockData.get(x, y, z + 1))) {
                                addThisFront(x, y, z);
                            }
                        } else {// TODO
                            addThisFront(x, y, z);
                        }

                        // 后面

                        if (z > 0) {
                            if (needToPaint(i, blockData.get(x, y, z - 1))) {
                                addThisBack(x, y, z);
                            }
                        } else {// TODO
                            addThisBack(x, y, z);
                        }

                    }

                }
            }
        } // GL11.glEnd();
          // System.out.println(this.count);
        GLApp.endDisplayList();
    }

    @Override
    public void build() {
        if (Switcher.SHADER_ENABLE) {
            this.buildVao();
            return;
        }

        displayId = GLApp.beginDisplayList();
        ChunkProvider chunkProvider = CoreRegistry.get(ChunkProvider.class);
        ChunkImpl leftChunk = (ChunkImpl) chunkProvider.getChunk(this.chunkPos.x - 1, this.chunkPos.y, this.chunkPos.z);
        ChunkImpl rightChunk = (ChunkImpl) chunkProvider.getChunk(this.chunkPos.x + 1, this.chunkPos.y,
                this.chunkPos.z);
        ChunkImpl frontChunk = (ChunkImpl) chunkProvider.getChunk(this.chunkPos.x, this.chunkPos.y,
                this.chunkPos.z + 1);
        ChunkImpl backChunk = (ChunkImpl) chunkProvider.getChunk(this.chunkPos.x, this.chunkPos.y, this.chunkPos.z - 1);

        // block.render();
        GL11.glBegin(GL11.GL_QUADS);

        for (int x = 0; x < this.getChunkSizeX(); x++) {
            for (int z = 0; z < this.getChunkSizeZ(); z++) {
                for (int y = 0; y < this.getChunkSizeY(); y++) {
                    int i = 0;
                    try {
                        i = blockData.get(x, y, z);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (i == 6 || i == 3)
                        continue;
                    currentBlockType = i;
                    // addThisTop(x, y, z);

                    if (i > 0) {// System.out.printf("%d %d %d /n\n",x,y,z);
                        // 判断上面
                        if (y < this.getChunkSizeY() - 1) {
                            if (needToPaint(i, blockData.get(x, y + 1, z))) {
                                addThisTop(x, y, z);
                                // System.out.println("top");
                            } else {

                            }
                        } else {
                            addThisTop(x, y, z);// System.out.println("top");
                        }

                        // 判断下面
                        if (y > 0) {
                            if (needToPaint(i, blockData.get(x, y - 1, z))) {
                                addThisBottom(x, y, z);// System.out.println("Bottom");
                            }
                        } else {
                            // addThisBottom(x, y,
                            // z);//System.out.println("Bottom");
                        }

                        // 判断左面
                        if (x > 0) {
                            if (needToPaint(i, blockData.get(x - 1, y, z))) {
                                addThisLeft(x, y, z);// System.out.println("left");
                            }
                        } else {
                            // TODO//x
                            if (leftChunk != null)
                                if (needToPaint(i, leftChunk.blockData.get(leftChunk.getChunkSizeX() - 1, y, z))) {
                                    addThisLeft(x, y, z);// System.out.println("left");
                                } else {
                                    // LogUtil.println("bu xu yao hui zhi");
                                }

                        }

                        // 判断右面

                        if (x < this.getChunkSizeX() - 1) {
                            if (needToPaint(i, blockData.get(x + 1, y, z))) {
                                addThisRight(x, y, z);// System.out.println("Right");
                            }
                        } else {// TODO

                            if (rightChunk != null)
                                if (needToPaint(i, rightChunk.blockData.get(0, y, z))) {
                                    addThisRight(x, y, z);// System.out.println("left");
                                }
                            // addThisRight(x, y,
                            // z);//System.out.println("Right");
                        }

                        // 前面

                        if (z < this.getChunkSizeZ() - 1) {
                            if (needToPaint(i, blockData.get(x, y, z + 1))) {
                                addThisFront(x, y, z);// System.out.println("Front");
                            }
                        } else {// TODO
                            if (frontChunk != null)
                                if (needToPaint(i, frontChunk.blockData.get(x, y, 0))) {
                                    addThisFront(x, y, z);// System.out.println("left");
                                }
                            // addThisFront(x, y,
                            // z);//System.out.println("Front");
                        }

                        // 后面

                        if (z > 0) {
                            if (needToPaint(i, blockData.get(x, y, z - 1))) {
                                addThisBack(x, y, z);// System.out.println("back");
                            }
                        } else {// TODO z==0
                            if (backChunk != null)
                                if (needToPaint(i, backChunk.blockData.get(x, y, this.getChunkSizeZ() - 1))) {
                                    addThisBack(x, y, z);// System.out.println("left");
                                }
                            // addThisBack(x, y,
                            // z);//System.out.println("back");
                        }

                    }

                }
            }
        }
        GL11.glEnd();
        // System.out.println(this.count);
        GLApp.endDisplayList();
        GLApp.callDisplayList(this.displayId);
        Util.checkGLError();
    }

    public boolean needupdate = true;

    @Override
    public void setNeedUpdate(boolean b) {
        needupdate = b;
    }

    @Override
    public boolean isNeedUpdate() {
        return false;
    }

    int VaoId;
    int VboId;

    /*
     * public void CreateTerrainVBO() { //顶点 vbo //create vbo 创建vbo vertex
     * buffer objects
     * 
     * 
     * veticesBuffer.rewind(); // rewind, otherwise LWJGL thinks our buffer is
     * empty VboId = glGenBuffers();//create vbo
     * 
     * 
     * glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
     * glBufferData(GL_ARRAY_BUFFER, veticesBuffer, GL_STATIC_DRAW);//put data
     * 
     * // System.out.println("float.size:" + FlFLOAToat.SIZE);
     * glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0);
     * 
     * Util.checkGLError(); glEnableVertexAttribArray(0); Util.checkGLError();
     * 
     * glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * 4, 3 * 4);
     * 
     * Util.checkGLError(); glEnableVertexAttribArray(1); Util.checkGLError();
     * glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * 4, 6 * 4);
     * 
     * Util.checkGLError(); glEnableVertexAttribArray(2); Util.checkGLError();
     * 
     * 
     * }
     */

    /*
     * public void CreateTerrainVAO() {
     * 
     * 
     * VaoId = glGenVertexArrays(); Util.checkGLError();
     * 
     * glBindVertexArray(VaoId); Util.checkGLError(); this.CreateTerrainVBO();
     * 
     * glBindVertexArray(0); Util.checkGLError(); }
     */
    public String toString() {
        return this.getChunkWorldPosX() + "-" + this.getChunkSizeY() + "-" + this.getChunkWorldPosZ();
    }

    boolean top = true;
    boolean bottom = true;
    boolean left = true;
    boolean right = true;
    boolean front = true;
    boolean back = true;

    public void buildVao() {
        needupdate = false;

        vao.getVertices().rewind();

        // glUseProgram(ShaderManager.terrainShaderConfig.getProgramId());
        ChunkProvider chunkProvider = CoreRegistry.get(ChunkProvider.class);
        ChunkImpl leftChunk = (ChunkImpl) chunkProvider.getChunk(this.chunkPos.x - 1, this.chunkPos.y, this.chunkPos.z);
        ChunkImpl rightChunk = (ChunkImpl) chunkProvider.getChunk(this.chunkPos.x + 1, this.chunkPos.y,
                this.chunkPos.z);
        ChunkImpl frontChunk = (ChunkImpl) chunkProvider.getChunk(this.chunkPos.x, this.chunkPos.y,
                this.chunkPos.z + 1);
        ChunkImpl backChunk = (ChunkImpl) chunkProvider.getChunk(this.chunkPos.x, this.chunkPos.y, this.chunkPos.z - 1);

        // block.render();
        // GL11.glBegin(GL11.GL_QUADS);
        // ShaderManager.terrainShaderConfig.addVao(new Vao(this.toString()));
        BaseBlock nowBlock;
        for (int x = 0; x < this.getChunkSizeX(); x++) {
            for (int z = 0; z < this.getChunkSizeZ(); z++) {
                for (int y = 0; y < this.getChunkSizeY(); y++) {
                    top = bottom = left = right = back = front = false;
                    int i = 0;
                    try {
                        i = blockData.get(x, y, z);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /*
                     * if(i==6||i==3) continue;
                     */
                    currentBlockType = i;
                    if (currentBlockType == ItemType.CopyBlock.id) {
                        LogUtil.println("render:"+x+","+y+","+z);
                        ShaderUtils.draw3dColorBox(ShaderManager.terrainShaderConfig,vao,x,y,z,new GL_Vector(1,1,1),0.2f,0.5f);
                        continue;
                    }
                    if (currentBlockType > 256) {

                        // state = ByteUtil.get16_8Value(i);
                        i = currentBlockType = ByteUtil.get8_0Value(i);
                    }
                    if (i > 0) {// System.out.printf("%d %d %d /n\n",x,y,z);
                        // 如果不是box 类型的

                        ItemDefinition itemDefinition = ItemManager.getItemDefinition(currentBlockType);
                        if (itemDefinition == null) {
                            continue;
                        }
                        if (!(itemDefinition.getItemModel().placeModel instanceof BoxModel)) {

                            itemDefinition.getItemModel().placeModel.build(ShaderManager.terrainShaderConfig, vao,
                                    this.chunkPos.x * getChunkSizeX() + x, y, this.chunkPos.z * getChunkSizeZ() + z);// .renderShader(ShaderManager.terrainShaderConfig);
                            continue;
                        }
                        // 判断上面
                        if (y < this.getChunkSizeY() - 1) {
                            if (needToPaint(i, blockData.get(x, y + 1, z))) {
                                // addThisTop4shader(x, y, z);
                                // addShader(x, y, z, Constants.TOP);
                                top = true;
                                // System.out.println("top");
                            } else {
                                top = false;
                            }
                        } else {
                            // addShader(x, y, z, Constants.TOP);
                            top = true;
                            // addThisTop4shader(x, y,
                            // z);//System.out.println("top");
                        }

                        // 判断下面
                        if (y > 0) {
                            if (needToPaint(i, blockData.get(x, y - 1, z))) {
                                // addThisBottom4shader(x, y,
                                // z);//System.out.println("Bottom");
                                // addShader(x, y, z, Constants.BOTTOM);
                                bottom = true;
                            }
                        } else {
                            bottom = false;
                            // addThisBottom(x, y,
                            // z);//System.out.println("Bottom");
                        }

                        // 判断左面
                        if (x > 0) {
                            if (needToPaint(i, blockData.get(x - 1, y, z))) {
                                // addThisLeft4shader(x, y,
                                // z);//System.out.println("left");
                                // addShader(x, y, z, Constants.LEFT);
                                left = true;

                            }
                        } else {
                            // TODO//x
                            if (leftChunk != null)
                                if (needToPaint(i, leftChunk.blockData.get(leftChunk.getChunkSizeX() - 1, y, z))) {
                                    // addThisLeft4shader(x, y,
                                    // z);//System.out.println("left");
                                    // addShader(x, y, z, Constants.LEFT);
                                    left = true;
                                } else {
                                    left = false;
                                    // LogUtil.println("bu xu yao hui zhi");
                                }

                        }

                        // 判断右面

                        if (x < this.getChunkSizeX() - 1) {
                            if (needToPaint(i, blockData.get(x + 1, y, z))) {
                                // addThisRight4shader(x, y,
                                // z);//System.out.println("Right");
                                // addShader(x, y, z, Constants.RIGHT);
                                right = true;
                            }
                        } else {// TODO

                            if (rightChunk != null)
                                if (needToPaint(i, rightChunk.blockData.get(0, y, z))) {
                                    // addThisRight4shader(x, y,
                                    // z);//System.out.println("left");
                                    // addShader(x, y, z, Constants.RIGHT);
                                    right = true;
                                }
                            // addThisRight(x, y,
                            // z);//System.out.println("Right");
                        }

                        // 前面

                        if (z < this.getChunkSizeZ() - 1) {
                            if (needToPaint(i, blockData.get(x, y, z + 1))) {
                                // addThisFront4shader(x, y,
                                // z);//System.out.println("Front");
                                // addShader(x, y, z, Constants.FRONT);
                                front = true;
                            }
                        } else {// TODO
                            if (frontChunk != null)
                                if (needToPaint(i, frontChunk.blockData.get(x, y, 0))) {
                                    // addThisFront4shader(x, y,
                                    // z);//System.out.println("left");
                                    // addShader(x, y, z, Constants.FRONT);
                                    front = true;
                                }
                            // addThisFront(x, y,
                            // z);//System.out.println("Front");
                        }

                        // 后面

                        if (z > 0) {
                            if (needToPaint(i, blockData.get(x, y, z - 1))) {
                                // addThisBack4shader(x, y,
                                // z);//System.out.println("back");
                                // addShader(x, y, z, Constants.BACK);
                                back = true;

                            }
                        } else {// TODO z==0
                            if (backChunk != null)
                                if (needToPaint(i, backChunk.blockData.get(x, y, this.getChunkSizeZ() - 1))) {
                                    // addThisBack4shader(x, y,
                                    // z);//System.out.println("left");
                                    // addShader(x, y, z, Constants.BACK);
                                    back = true;
                                }
                            // addThisBack(x, y,
                            // z);//System.out.println("back");
                        }

                    }
                    if (currentBlockType > 0 && (top || bottom || left || right || front || back)) {
                        this.addShader(x, y, z, top, bottom, left, right, front, back);
                    }

                }

            }
        }
        // CreateTerrainVAO();
        ShaderUtils.freshVao(ShaderManager.terrainShaderConfig, vao);
        ShaderUtils.freshVao(ShaderManager.terrainShaderConfig, alphaVao);
        // GL11.glEnd();
        // System.out.println(this.count);
    }

    public void addThisTop(int x, int y, int z) {

        this.faceIndex = 1;
        count++;
        vetices.put(x);
        vetices.put(y + 1);
        vetices.put(z + 1);

        vetices.put(x + 1);
        vetices.put(y + 1);
        vetices.put(z + 1);

        vetices.put(x + 1);
        vetices.put(y + 1);
        vetices.put(z);

        vetices.put(x);
        vetices.put(y + 1);
        vetices.put(z);

        normalizes.put(0);
        normalizes.put(1);
        normalizes.put(0);
        Draw();
        normalizes.position(0);
        vetices.position(0);
    }

    BlockDefManager blockDefManager;

    public void getTexutreInfo() {// up down left right front back

        /*
         * boolean flat = true; //blockDefManager.getBlockById() ItemDefinition
         * itemDefinition =
         * ItemManager.getItemDefinition(ItemType.values()[this.currentBlockType
         * ]);
         * 
         * if (
         *//* ti==null|| *//*
                           * itemDefinition.getShape() == null ||
                           * itemDefinition.getShape().getTop() == null) {
                           * LogUtil.err( "'shape  is null "); }
                           */
        /*
         * BaseBlock block = itemDefinition.getShape(); if (faceIndex ==
         * Constants.TOP) { ti = block.getTop(); // ti =
         * TextureManager.getTextureInfo("grass_top");
         * 
         * } else if (faceIndex == Constants.BACK) { ti = block.getBack(); //ti
         * = TextureManager.getTextureInfo("soil");
         * 
         * } else if (faceIndex == Constants.LEFT) { ti = block.getLeft();
         * 
         * } else if (faceIndex == Constants.RIGHT) { ti = block.getRight();
         * 
         * } else if (faceIndex == Constants.FRONT) { ti = block.getFront();
         * 
         * } else if (faceIndex == Constants.BOTTOM) { ti = block.getBottom();
         * 
         * }
         */

        /*
         * if(this.currentBlockType== ItemType.stone_block.id){ ti =
         * TextureManager.getTextureInfo("stone"); }else
         * if(this.currentBlockType== ItemType.soil_block.id){ ti =
         * TextureManager.getTextureInfo("soil"); }else
         * if(this.currentBlockType== ItemType.mantle_block.id){ ti =
         * TextureManager.getTextureInfo("mantle"); }else
         * if(this.currentBlockType== ItemType.wood_block.id){ ti =
         * TextureManager.getTextureInfo("wood"); }else { ti =
         * TextureManager.getTextureInfo("wood"); }
         *//*
           * switch (this.currentBlockType) { case ItemType.stone_block.id: ti =
           * TextureManager.getTextureInfo("stone");
           * 
           * break; case 3: if (faceIndex == 1) { ti =
           * TextureManager.getTextureInfo("grass_top");
           * 
           * } else if (faceIndex == 2) { ti =
           * TextureManager.getTextureInfo("soil");
           * 
           * } else { ti = TextureManager.getTextureInfo("grass_side");
           * 
           * } break; case 20: ti = TextureManager.getTextureInfo("glass");
           * 
           * break; case 8: ti = TextureManager.getTextureInfo("water"); if
           * (faceIndex == 1) {
           * 
           * } break; case 12: ti = TextureManager.getTextureInfo("sand");
           * 
           * break; case 7: ti = TextureManager.getTextureInfo("mantle");
           * 
           * break;
           * 
           * case 5: ti = TextureManager.getTextureInfo("wood");
           * 
           * break; default: System.out.println("添纹理的时候 什么都没对应上"); }
           */
        // }
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
        // GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        // GL11.glBegin(GL11.GL_QUADS);

        // GL11.glEnd();
        // normalizes.position(0);
        // vetices.position(0);
    }

    public void Draw() {// up down left right front back

        boolean flat = true;
        // blockDefManager.getBlockById()
        switch (this.currentBlockType) {
        case 1:
            ti = TextureManager.getTextureInfo("stone");

            // ti.bind();
            DrawVetext();
            break;
        case 3:
            if (faceIndex == 1) {
                ti = TextureManager.getTextureInfo("grass_top");
                // ti.bind();
            } else if (faceIndex == 2) {
                ti = TextureManager.getTextureInfo("soil");
                // ti.bind();
            } else {
                ti = TextureManager.getTextureInfo("grass_side");
                // ti.bind();
            }
            DrawVetext();
            break;
        case 20:
            ti = TextureManager.getTextureInfo("glass");
            // ti.bind();
            DrawVetext();
            break;
        case 8:
            ti = TextureManager.getTextureInfo("water");
            if (faceIndex == 1) {
                // ti.bind();
                DrawVetext();
            }
            break;
        case 12:
            ti = TextureManager.getTextureInfo("sand");
            // ti.bind();
            DrawVetext();
            break;
        case 7:
            ti = TextureManager.getTextureInfo("mantle");
            // ti.bind();
            DrawVetext();
            break;

        case 5:
            ti = TextureManager.getTextureInfo("wood");
            // ti.bind();
            DrawVetext();
            break;
        default:
            LogUtil.println("添纹理的时候 什么都没对应上 currentBlockType" + this.currentBlockType);
        }
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
        // GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        // GL11.glBegin(GL11.GL_QUADS);

        // GL11.glEnd();

    }

    public void DrawVetext() {
        vetices.position(vetices.position() - 12);
        normalizes.position(normalizes.position() - 3);

        GL11.glNormal3f(normalizes.get(), normalizes.get(), normalizes.get());

        GL11.glTexCoord2f(ti.minX, ti.minY);
        GL11.glVertex3f(vetices.get(), vetices.get(), vetices.get());
        GL11.glTexCoord2f(ti.maxX, ti.minY);
        GL11.glVertex3f(vetices.get(), vetices.get(), vetices.get());
        GL11.glTexCoord2f(ti.maxX, ti.maxY);
        GL11.glVertex3f(vetices.get(), vetices.get(), vetices.get());
        GL11.glTexCoord2f(ti.minX, ti.maxY);
        GL11.glVertex3f(vetices.get(), vetices.get(), vetices.get());

        /*
         * GL11.glNormal3f( 0.0f, 0.0f, 1.0f); GL11.glVertex3f(-1.0f, -1.0f,
         * 1.0f); // Bottom Left GL11.glVertex3f( 1.0f, -1.0f, 1.0f); // Bottom
         * Right GL11.glVertex3f( 1.0f, 1.0f, 1.0f); // Top Right
         * GL11.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left
         */

        // vetices.flip();

    }

    public void addToVao(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4, GL_Vector normal) {
        if (ti == null) {
            LogUtil.err("in chunkimpl ti shoud not be null !");
        }
        // 根据blocktype 获取对应的shape

        if (currentBlockType != 3 && currentBlockType != 6) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao, p1, p2, p3, p4, normal, ti);
        } else {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, alphaVao, p1, p2, p3, p4, normal, ti);
        }
    }

    public void addThisTop4shader(int x, int y, int z) {

        x += this.chunkPos.x * getChunkSizeX();
        z += this.chunkPos.z * getChunkSizeZ();
        this.faceIndex = Constants.TOP;
        getTexutreInfo();
        if (ti == null)
            return;
        count++;
        // getTexutreInfo();
        // ShaderUtils.drawCube(x,y,z);
        GL_Vector normal = new GL_Vector(0, 1, 0);
        GL_Vector p1 = new GL_Vector(x, y + 1, z + 1);
        GL_Vector p2 = new GL_Vector(x + 1, y + 1, z + 1);
        GL_Vector p3 = new GL_Vector(x + 1, y + 1, z);
        GL_Vector p4 = new GL_Vector(x, y + 1, z);
        addToVao(p1, p2, p3, p4, normal);

        /*
         * veticesBuffer.put(x).put(y+1).put(z+1
         * ).put(0).put(1).put(0).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x+1 ).put(y+1).put(z+1
         * ).put(0).put(1).put(0).put(ti.maxX).put(ti.minY);//p2
         * veticesBuffer.put(x+1 ).put(y+1
         * ).put(z).put(0).put(1).put(0).put(ti.maxX).put(ti.maxY);//p3
         * veticesBuffer.put(x).put(y+1 ).put(z
         * ).put(0).put(1).put(0).put(ti.minX).put(ti.maxY);//p4
         * veticesBuffer.put(x).put(y+1).put(z+1
         * ).put(0).put(1).put(0).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x+1 ).put(y+1
         * ).put(z).put(0).put(1).put(0).put(ti.maxX).put(ti.maxY);//p3
         */
        count++;// left front top

    }

    public void addThisBottom4shader(int x, int y, int z) {
        x += this.chunkPos.x * getChunkSizeX();
        z += this.chunkPos.z * getChunkSizeZ();
        this.faceIndex = Constants.BOTTOM;
        getTexutreInfo();
        if (ti == null)
            return;
        count++;

        GL_Vector normal = new GL_Vector(0, -1, 0);
        GL_Vector p1 = new GL_Vector(x, y, z);
        GL_Vector p2 = new GL_Vector(x + 1, y, z);
        GL_Vector p3 = new GL_Vector(x + 1, y, z + 1);
        GL_Vector p4 = new GL_Vector(x, y, z + 1);
        addToVao(p1, p2, p3, p4, normal);

        /*
         * veticesBuffer.put(x).put(y).put(z
         * ).put(0).put(-1).put(0).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x+1 ).put(y).put(z
         * ).put(0).put(-1).put(0).put(ti.maxX).put(ti.minY);//p2
         * veticesBuffer.put(x+1 ).put(y
         * ).put(z+1).put(0).put(-1).put(0).put(ti.maxX).put(ti.maxY);//p3
         * veticesBuffer.put(x).put(y ).put(z+1
         * ).put(0).put(-1).put(0).put(ti.minX).put(ti.maxY);//p4
         * veticesBuffer.put(x).put(y).put(z
         * ).put(0).put(-1).put(0).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x+1 ).put(y
         * ).put(z+1).put(0).put(-1).put(0).put(ti.maxX).put(ti.maxY);//p3
         */

    }

    public void addThisBottom(int x, int y, int z) {
        this.faceIndex = 2;
        count++;
        vetices.put(x);
        vetices.put(y);
        vetices.put(z);

        vetices.put(x + 1);
        vetices.put(y);
        vetices.put(z);

        vetices.put(x + 1);
        vetices.put(y);
        vetices.put(z + 1);

        vetices.put(x);
        vetices.put(y);
        vetices.put(z + 1);
        normalizes.put(0);
        normalizes.put(-1);
        normalizes.put(0);

        Draw();

    }

    public void addThisFront(int x, int y, int z) {
        this.faceIndex = 5;
        vetices.put(x);
        count++;
        vetices.put(y);
        vetices.put(z + 1);

        vetices.put(x + 1);
        vetices.put(y);
        vetices.put(z + 1);

        vetices.put(x + 1);
        vetices.put(y + 1);
        vetices.put(z + 1);

        vetices.put(x);
        vetices.put(y + 1);
        vetices.put(z + 1);

        normalizes.put(0);
        normalizes.put(0);
        normalizes.put(1);
        Draw();
    }

    public void addThisFront4shader(int x, int y, int z) {
        x += this.chunkPos.x * getChunkSizeX();
        z += this.chunkPos.z * getChunkSizeZ();
        this.faceIndex = Constants.FRONT;
        getTexutreInfo();
        if (ti == null)
            return;
        count++;
        GL_Vector normal = new GL_Vector(0, 0, 1);
        GL_Vector p1 = new GL_Vector(x, y, z + 1);
        GL_Vector p2 = new GL_Vector(x + 1, y, z + 1);
        GL_Vector p3 = new GL_Vector(x + 1, y + 1, z + 1);
        GL_Vector p4 = new GL_Vector(x, y + 1, z + 1);
        addToVao(p1, p2, p3, p4, normal);

        /*
         * veticesBuffer.put(x).put(y).put(z +
         * 1).put(0).put(0).put(1).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x + 1).put(y).put(z +
         * 1).put(0).put(0).put(1).put(ti.maxX).put(ti.minY);//p2
         * veticesBuffer.put(x + 1).put(y + 1).put(z +
         * 1).put(0).put(0).put(1).put(ti.maxX).put(ti.maxY);//p3
         * veticesBuffer.put(x).put(y + 1).put(z +
         * 1).put(0).put(0).put(1).put(ti.minX).put(ti.maxY);//p4
         * veticesBuffer.put(x).put(y).put(z +
         * 1).put(0).put(0).put(1).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x + 1).put(y + 1).put(z +
         * 1).put(0).put(0).put(1).put(ti.maxX).put(ti.maxY);//p3
         */

        // Draw();
    }

    public void addThisBack(int x, int y, int z) {
        this.faceIndex = 6;
        count++;

        vetices.put(x + 1);
        vetices.put(y);
        vetices.put(z);

        vetices.put(x);
        vetices.put(y);
        vetices.put(z);

        vetices.put(x);
        vetices.put(y + 1);
        vetices.put(z);

        vetices.put(x + 1);
        vetices.put(y + 1);
        vetices.put(z);

        normalizes.put(0);
        normalizes.put(0);
        normalizes.put(-1);
        Draw();
    }

    public void addShader(int x, int y, int z, boolean top, boolean bottom, boolean left, boolean right, boolean front,
            boolean back) {

        int worldx = x + this.chunkPos.x * getChunkSizeX();
        int worldz = z + this.chunkPos.z * getChunkSizeZ();
        y = y;
        // GL_Vector[] vertices = BoxModel.getPoint(worldx, y, worldz);
        // 查询出当前是哪个面

        // int[] faces = BoxModel.facesAry[dir];

        // this.faceIndex = dir;
        // getTexutreInfo();
        // if(ti==null)return;
        count++;

        /*
         * GL_Vector normal= new GL_Vector(-1,0,0); GL_Vector p1= new
         * GL_Vector(x,y,z); GL_Vector p2= new GL_Vector(x,y,z+1); GL_Vector p3=
         * new GL_Vector(x,y+1,z+1); GL_Vector p4= new GL_Vector(x,y+1,z);
         */
        // addToVao(vertices[faces[0]], vertices[faces[1]],
        // vertices[faces[2]],vertices[faces[3]], BoxModel.FRONT_DIR);

        // boolean flat = true;
        // blockDefManager.getBlockById()+

        BaseBlock nowBlock =(BaseBlock)this.getBlock(x, y, z);//= (BaseBlock) blockMap.get(blockData.getIndex(x, y, z));
        if(nowBlock == null ){
            LogUtil.err("this block is null ");
        }
        // ItemDefinition itemDefinition =
        // ItemManager.getItemDefinition(this.currentBlockType);
        // if (/*ti==null||*/ itemDefinition.getShape() == null ||
        // itemDefinition.getShape().etTop() == null) {
        // LogUtil.err(ti.name + "'shape is null ");
        // }
        // BaseBlock shape = itemDefinition.getShape();
        // ShapeFace shapeFace = null;
        if (nowBlock instanceof AnimationBlock) {
            AnimationBlock group = (AnimationBlock) nowBlock;
            if (group.animations != null && group.animations.size() > 0) {
                return;
            }
        }

        nowBlock.render(ShaderManager.terrainShaderConfig, vao, worldx, y, worldz, top, bottom, left, right, front,
                back);



        /*
         * if (faceIndex == Constants.TOP) { ti = shape.getTop(); // ti =
         * TextureManager.getTextureInfo("grass_top"); if (shape.getTopFace() !=
         * null) { shapeFace = shape.getTopFace();
         * //ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,p1,
         * p2, p3,p4, normal, ti); }
         * 
         * } else if (faceIndex == Constants.BACK) { ti = shape.getBack();
         * 
         * if (shape.getBackFace() != null) { shapeFace = shape.getBackFace();
         * //ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,p1,
         * p2, p3,p4, normal, ti); } //ti =
         * TextureManager.getTextureInfo("soil");
         * 
         * } else if (faceIndex == Constants.LEFT) { ti = shape.getLeft(); if
         * (shape.getLeftFace() != null) { shapeFace = shape.getLeftFace();
         * //ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,p1,
         * p2, p3,p4, normal, ti); } } else if (faceIndex == Constants.RIGHT) {
         * ti = itemDefinition.getShape().getRight();
         * 
         * if (shape.getRightFace() != null) { shapeFace = shape.getRightFace();
         * //ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,p1,
         * p2, p3,p4, normal, ti); }
         * 
         * } else if (faceIndex == Constants.FRONT) { ti = shape.getFront();
         * 
         * if (shape.getFrontFace() != null) { shapeFace = shape.getFrontFace();
         * //ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,p1,
         * p2, p3,p4, normal, ti); }
         * 
         * } else if (faceIndex == Constants.BOTTOM) { ti = shape.getBottom();
         * if (shape.getBottomFace() != null) { shapeFace =
         * shape.getBottomFace();
         * //ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,p1,
         * p2, p3,p4, normal, ti); }
         * 
         * }
         * 
         * if(currentBlockType== ItemType.box.id) { if(
         * faceIndex==Constants.FRONT){ IBlock block = this.getBlock(x, y,
         * z);//this.blockManager.getBlock(currentBlockType);
         * block.renderShader(vao, shapeFace, ti,worldx,y,worldz); }
         * 
         * }else if (currentBlockType== ItemType.wood_door.id) {
         * //===========判断当前方块是不是有状态的方块 如果是有状态的方块调
         * //=========判断当前方块是简单方块还是特殊方块如果是特殊方块调用他自身的渲染方法渲染
         * 
         * 
         * IBlock block = this.getBlock(x, y,
         * z);//this.blockManager.getBlock(currentBlockType);
         * block.renderShader(vao, shapeFace, ti,worldx,y,worldz);
         * 
         * } else { try { if (currentBlockType != ItemType.water.id &&
         * currentBlockType != ItemType.glass.id) {//正常的block的
         * ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, vao,
         * vertices[faces[0]], vertices[faces[1]], vertices[faces[2]],
         * vertices[faces[3]], BoxModel.dirAry[dir], ti); } else {//alpha的
         * ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, alphaVao,
         * vertices[faces[0]], vertices[faces[1]], vertices[faces[2]],
         * vertices[faces[3]], BoxModel.dirAry[dir], ti); } } catch (Exception
         * e) { LogUtil.err(e); } }
         */

        // 然后去取block的面
        // 如果取不到 就去取shapeface

    }

    public void addThisBack4shader(int x, int y, int z) {
        x += this.chunkPos.x * getChunkSizeX();
        z += this.chunkPos.z * getChunkSizeZ();
        this.faceIndex = Constants.BACK;
        getTexutreInfo();
        if (ti == null)
            return;

        count++;

        GL_Vector normal = new GL_Vector(0, 0, -1);
        GL_Vector p1 = new GL_Vector(x + 1, y, z);
        GL_Vector p2 = new GL_Vector(x, y, z);
        GL_Vector p3 = new GL_Vector(x, y + 1, z);
        GL_Vector p4 = new GL_Vector(x + 1, y + 1, z);
        // ShaderUtils.drawImage(ShaderManager.terrainShaderConfig,vao,p1, p2,
        // p3,p4, normal, ti);
        addToVao(p1, p2, p3, p4, normal);
        /*
         * veticesBuffer.put(x+1).put(y).put(z
         * ).put(0).put(0).put(-1).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x ).put(y).put(z
         * ).put(0).put(0).put(-1).put(ti.maxX).put(ti.minY);//p2
         * veticesBuffer.put(x ).put(y +
         * 1).put(z).put(0).put(0).put(-1).put(ti.maxX).put(ti.maxY);//p3
         * veticesBuffer.put(x+1).put(y + 1).put(z
         * ).put(0).put(0).put(-1).put(ti.minX).put(ti.maxY);//p4
         * veticesBuffer.put(x+1).put(y).put(z
         * ).put(0).put(0).put(-1).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x ).put(y +
         * 1).put(z).put(0).put(0).put(-1).put(ti.maxX).put(ti.maxY);//p3
         */
    }

    public void addThisLeft(int x, int y, int z) {
        this.faceIndex = 3;
        count++;
        vetices.put(x);
        vetices.put(y);
        vetices.put(z);

        vetices.put(x);
        vetices.put(y);
        vetices.put(z + 1);

        vetices.put(x);
        vetices.put(y + 1);
        vetices.put(z + 1);

        vetices.put(x);
        vetices.put(y + 1);
        vetices.put(z);
        normalizes.put(-1);
        normalizes.put(0);
        normalizes.put(0);
        Draw();
    }

    public void addThisLeft4shader(int x, int y, int z) {
        x += this.chunkPos.x * getChunkSizeX();
        z += this.chunkPos.z * getChunkSizeZ();
        this.faceIndex = Constants.LEFT;
        getTexutreInfo();
        if (ti == null)
            return;
        count++;

        GL_Vector normal = new GL_Vector(-1, 0, 0);
        GL_Vector p1 = new GL_Vector(x, y, z);
        GL_Vector p2 = new GL_Vector(x, y, z + 1);
        GL_Vector p3 = new GL_Vector(x, y + 1, z + 1);
        GL_Vector p4 = new GL_Vector(x, y + 1, z);
        addToVao(p1, p2, p3, p4, normal);

        /*
         * veticesBuffer.put(x).put(y).put(z
         * ).put(-1).put(0).put(0).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x ).put(y).put(z
         * +1).put(-1).put(0).put(0).put(ti.maxX).put(ti.minY);//p2
         * veticesBuffer.put(x ).put(y +
         * 1).put(z+1).put(-1).put(0).put(0).put(ti.maxX).put(ti.maxY);//p3
         * veticesBuffer.put(x).put(y + 1).put(z
         * ).put(-1).put(0).put(0).put(ti.minX).put(ti.maxY);//p4
         * veticesBuffer.put(x).put(y).put(z
         * ).put(-1).put(0).put(0).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x ).put(y +
         * 1).put(z+1).put(-1).put(0).put(0).put(ti.maxX).put(ti.maxY);//p3
         */

    }

    public void addThisRight(int x, int y, int z) {
        this.faceIndex = 4;
        count++;

        vetices.put(x + 1);
        vetices.put(y);
        vetices.put(z + 1);

        vetices.put(x + 1);
        vetices.put(y);
        vetices.put(z);

        vetices.put(x + 1);
        vetices.put(y + 1);
        vetices.put(z);

        vetices.put(x + 1);
        vetices.put(y + 1);
        vetices.put(z + 1);

        normalizes.put(1);
        normalizes.put(0);
        normalizes.put(0);
        Draw();

    }

    public void addThisRight4shader(int x, int y, int z) {
        x += this.chunkPos.x * getChunkSizeX();
        z += this.chunkPos.z * getChunkSizeZ();
        this.faceIndex = Constants.RIGHT;
        getTexutreInfo();
        if (ti == null)
            return;
        count++;

        GL_Vector normal = new GL_Vector(1, 0, 0);
        GL_Vector p1 = new GL_Vector(x + 1, y, z + 1);
        GL_Vector p2 = new GL_Vector(x + 1, y, z);
        GL_Vector p3 = new GL_Vector(x + 1, y + 1, z);
        GL_Vector p4 = new GL_Vector(x + 1, y + 1, z + 1);
        addToVao(p1, p2, p3, p4, normal);

        /*
         * veticesBuffer.put(x+1).put(y).put(z+1
         * ).put(1).put(0).put(0).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x+1 ).put(y).put(z
         * ).put(1).put(0).put(0).put(ti.maxX).put(ti.minY);//p2
         * veticesBuffer.put(x+1 ).put(y +
         * 1).put(z).put(1).put(0).put(0).put(ti.maxX).put(ti.maxY);//p3
         * veticesBuffer.put(x+1).put(y + 1).put(z+1
         * ).put(1).put(0).put(0).put(ti.minX).put(ti.maxY);//p4
         * veticesBuffer.put(x+1).put(y).put(z+1
         * ).put(1).put(0).put(0).put(ti.minX).put(ti.minY);//p1
         * veticesBuffer.put(x+1 ).put(y +
         * 1).put(z).put(1).put(0).put(0).put(ti.maxX).put(ti.maxY);//p3
         */

    }

    /*
     * public List<Integer> list = new ArrayList(); public int count = 0; public
     * List<Integer> normalizelist = new ArrayList();
     * 
     * public void addThisTop(int x, int y, int z) {this.faceIndex=1; count++;
     * list.add(x); list.add(y + 1); list.add(z + 1);
     * 
     * list.add(x + 1); list.add(y + 1); list.add(z + 1);
     * 
     * list.add(x + 1); list.add(y + 1); list.add(z);
     * 
     * list.add(x); list.add(y + 1); list.add(z);
     * 
     * normalizelist.add(0); normalizelist.add(1); normalizelist.add(0);Draw();
     * }
     * 
     * public void addThisBottom(int x, int y, int z) {this.faceIndex=2;
     * count++; list.add(x); list.add(y); list.add(z);
     * 
     * list.add(x + 1); list.add(y); list.add(z);
     * 
     * list.add(x + 1); list.add(y); list.add(z + 1);
     * 
     * list.add(x); list.add(y); list.add(z + 1); normalizelist.add(0);
     * normalizelist.add(-1); normalizelist.add(0);Draw(); }
     * 
     * public void addThisFront(int x, int y, int z) { list.add(x); count++;
     * list.add(y); list.add(z + 1);
     * 
     * list.add(x + 1); list.add(y); list.add(z + 1);
     * 
     * list.add(x + 1); list.add(y + 1); list.add(z + 1);
     * 
     * list.add(x); list.add(y + 1); list.add(z + 1);
     * 
     * normalizelist.add(0); normalizelist.add(0); normalizelist.add(1);Draw();
     * 
     * }
     * 
     * public void addThisBack(int x, int y, int z) { count++;
     * 
     * list.add(x + 1); list.add(y); list.add(z);
     * 
     * list.add(x); list.add(y); list.add(z);
     * 
     * list.add(x); list.add(y + 1); list.add(z);
     * 
     * list.add(x + 1); list.add(y + 1); list.add(z);
     * 
     * normalizelist.add(0); normalizelist.add(0); normalizelist.add(-1);Draw();
     * 
     * }
     * 
     * public void addThisLeft(int x, int y, int z) { count++; list.add(x);
     * list.add(y); list.add(z);
     * 
     * list.add(x); list.add(y); list.add(z + 1);
     * 
     * list.add(x); list.add(y + 1); list.add(z + 1);
     * 
     * list.add(x); list.add(y + 1); list.add(z); normalizelist.add(-1);
     * normalizelist.add(0); normalizelist.add(0);Draw(); }
     * 
     * public void addThisRight(int x, int y, int z) { count++;
     * 
     * list.add(x + 1); list.add(y); list.add(z);
     * 
     * list.add(x + 1); list.add(y + 1); list.add(z);
     * 
     * list.add(x + 1); list.add(y + 1); list.add(z + 1);
     * 
     * list.add(x + 1); list.add(y); list.add(z + 1);
     * 
     * normalizelist.add(1); normalizelist.add(0); normalizelist.add(0);
     * 
     * }
     */

    public int getChunkWorldPosX() {
        return chunkPos.x * getChunkSizeX();
    }

    @Override
    public int getChunkWorldPosY() {
        return chunkPos.y * getChunkSizeY();
    }

    @Override
    public int getChunkWorldPosZ() {
        return chunkPos.z * getChunkSizeZ();
    }

    @Override
    public int getChunkSizeX() {
        return ChunkConstants.SIZE_X;
    }

    @Override
    public int getChunkSizeY() {
        return ChunkConstants.SIZE_Y;
    }

    @Override
    public int getChunkSizeZ() {
        return ChunkConstants.SIZE_Z;
    }

    @Override
    public IBlock setBlock(Vector3i pos, IBlock block) {
        // VIP Auto-generated method stub
        return null;
    }

    /*
     * @Override public void setLiquid(Vector3i pos, LiquidData state) { // VIP
     * Auto-generated method stub
     *
     * }
     *
     * @Override public void setLiquid(int x, int y, int z, LiquidData newState)
     * { // VIP Auto-generated method stub
     *
     * }
     *
     * @Override public LiquidData getLiquid(Vector3i pos) { // VIP
     * Auto-generated method stub return null; }
     *
     * @Override public LiquidData getLiquid(int x, int y, int z) { // VIP
     * Auto-generated method stub return null; }
     */
    @Override
    public Vector3i getChunkWorldPos() {
        // VIP Auto-generated method stub
        return null;
    }

    @Override
    public Vector3i getBlockWorldPos(Vector3i blockPos) {
        // VIP Auto-generated method stub
        return null;
    }

    @Override
    public Vector3i getBlockWorldPos(int x, int y, int z) {
        // VIP Auto-generated method stub
        return null;
    }

    @Override
    public int getBlockWorldPosX(int x) {
        return x + getChunkWorldPosX();
    }

    @Override
    public int getBlockWorldPosY(int y) {
        return y + getChunkWorldPosY();
    }

    @Override
    public int getBlockWorldPosZ(int z) {
        return z + getChunkWorldPosZ();
    }

    public ChunkBlockIterator getBlockIterator() {
        return new ChunkBlockIteratorImpl(blockManager, getChunkWorldPos(), blockData);
    }

    public int getBlockData(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0) {
            GLApp.msg("ChunkImpl  getBlockData error");
            return blockData.get(0, 0, 0);
            // System.exit(0);
        }
        return blockData.get(x, y, z);
    }

    public boolean disposed = false;

    @Override
    public void disposeMesh() {

        // if(this.displayId!=0){
        if (!disposed) {
            // GL11.glDeleteLists(this.displayId, 1);
            // GL11.glDeleteLists(this.alphaDisplayId, 1);
            // this.displayId=0;
            this.disposed = true;
            //
            // 保存数据
            /*
             * String fileName = "" + chunkPos.x + "_" + chunkPos.y + "_" +
             * chunkPos.z + ".chunk"; try { Path path =
             * PathManager.getInstance().getInstallPath().resolve("saves").
             * resolve(fileName); ObjectOutputStream out = new
             * ObjectOutputStream(new FileOutputStream(path.toFile()));
             * this.blockData.writeExternal(out); out.close(); } catch
             * (FileNotFoundException e) { // VIP Auto-generated catch block
             * e.printStackTrace(); } catch (IOException e) { // VIP
             * Auto-generated catch block e.printStackTrace(); }
             */
            // this.blockData = null;
        } else {
            System.out.println("为什么要对没有初始化的chunkimpl取消");
        }
    }

    public void update() {
        if (Switcher.SHADER_ENABLE) {
            if (vao == null) {
                vao = new Vao(102400, ShaderManager.terrainShaderConfig);
                alphaVao = new Vao(102400, ShaderManager.terrainShaderConfig);
            }
            if (this.disposed) {
                // if(this.displayId==0){

                this.disposed = false;
            } else if (this.vao.getVaoId() == 0 || needupdate) {
                this.buildVao();// this.buildAlpha();
                /*
                 * int error = GL11.glGetError(); System.out.println("error: " +
                 * GLU.gluErrorString(error)); System.out.println(
                 * "vaoid should not be 0 in preRender");
                 */
            }
        } else {
            if (this.disposed) {
                // if(this.displayId==0){
                this.build();// this.buildAlpha();
                this.disposed = false;
            }
            if (this.displayId == 0) {
                int error = GL11.glGetError();
                System.out.println("error: " + GLU.gluErrorString(error));
                System.out.println("displayId should not be 0 in preRender");
            }
        }
        // GLApp.callDisplayList(this.displayId);
    }

    public void render(ShaderConfig config) {
        if (vao.getVaoId() == 0) {
            int error = GL11.glGetError();
            System.out.println(this.chunkPos + "displayId should not be 0 in render");
        } else {
            for (int i = 0; i < animationBlock.size(); i++) {
                AnimationBlock animationBlock = (AnimationBlock) this.animationBlock.get(i);
                GL_Matrix translateMatrix  = GL_Matrix.translateMatrix(chunkPos.x * 16 + animationBlock.x, animationBlock.y,
                        chunkPos.z * 16 + animationBlock.z);
                GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,-animationBlock.dir*3.14f/2, 0);
                rotateMatrix = GL_Matrix.multiply(translateMatrix, rotateMatrix);
                //animationBlock.reComputePoints();
                animationBlock.renderShader(config,
                        ShaderManager.anotherShaderConfig.getVao(),rotateMatrix
                       );
                ShaderManager. anotherShaderConfig.getVao().getVertices().rewind();
                //GamingState.editEngine.needUpdate=true;
                // colorGroup.render(ShaderManager.terrainShaderConfig,ShaderManager.anotherShaderConfig.getVao(),chunkPos.x*16
                // +colorGroup.x ,colorGroup.y,chunkPos.z*16
                // +colorGroup.z,true,true,true,true,true ,true );
            }

            ShaderUtils.finalDraw(config, vao);

        }

    }

    public void render() {
        if (Switcher.SHADER_ENABLE) {
            if (vao.getVaoId() == 0) {
                int error = GL11.glGetError();
                System.out.println(this.chunkPos + "displayId should not be 0 in render");
            } else {
                ShaderUtils.finalDraw(ShaderManager.terrainShaderConfig, vao);
                // 开始绘制动画
                for (int i = 0; i < animationBlock.size(); i++) {
                    AnimationBlock animationBlock = (AnimationBlock) this.animationBlock.get(i);
                    GL_Matrix translateMatrix  = GL_Matrix.translateMatrix(chunkPos.x * 16 + animationBlock.x, animationBlock.y,
                            chunkPos.z * 16 + animationBlock.z);
                    GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,-animationBlock.dir*3.14f/2, 0);
                    rotateMatrix = GL_Matrix.multiply(translateMatrix, rotateMatrix);
                    //animationBlock.reComputePoints();
                    animationBlock.renderShader(ShaderManager.terrainShaderConfig,
                            ShaderManager.anotherShaderConfig.getVao(),rotateMatrix
                         );
                    // colorGroup.render(ShaderManager.terrainShaderConfig,ShaderManager.anotherShaderConfig.getVao(),chunkPos.x*16
                    // +colorGroup.x ,colorGroup.y,chunkPos.z*16
                    // +colorGroup.z,true,true,true,true,true ,true );
                }
            }
        } else {
            // GLApp.renderCube();
            if (this.displayId == 0) {
                // int error =GL11.glGetError();
                System.out.println(this.chunkPos + "displayId should not be 0 in render");
            } else {

                // OpenglUtil.glVertex3fv4rect(P1, P2, P6, P5, ti,
                // Constants.FRONT);
                GLApp.callDisplayList(this.displayId);
                /*
                 * GL11.glNormal3f( 0.0f, 0.0f, 1.0f); GL11.glVertex3f(-1.0f,
                 * -1.0f, 1.0f); // Bottom Left GL11.glVertex3f( 1.0f, -1.0f,
                 * 1.0f); // Bottom Right GL11.glVertex3f( 1.0f, 1.0f, 1.0f); //
                 * Top Right GL11.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left
                 */
            }
        }
    }

    public void renderAlpha() {
        if (Switcher.SHADER_ENABLE) {
            if (alphaVao.getVaoId() == 0) {
                int error = GL11.glGetError();
                System.out.println(this.chunkPos + "displayId should not be 0 in render");
            } else {
                ShaderUtils.finalDraw(ShaderManager.terrainShaderConfig, alphaVao);
            }
        } else {
            // GLApp.renderCube();
            if (this.displayId == 0) {
                // int error =GL11.glGetError();
                System.out.println(this.chunkPos + "displayId should not be 0 in render");
            } else {

                // OpenglUtil.glVertex3fv4rect(P1, P2, P6, P5, ti,
                // Constants.FRONT);
                GLApp.callDisplayList(this.displayId);
                /*
                 * GL11.glNormal3f( 0.0f, 0.0f, 1.0f); GL11.glVertex3f(-1.0f,
                 * -1.0f, 1.0f); // Bottom Left GL11.glVertex3f( 1.0f, -1.0f,
                 * 1.0f); // Bottom Right GL11.glVertex3f( 1.0f, 1.0f, 1.0f); //
                 * Top Right GL11.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left
                 */
            }
        }
    }

    public void save() {
        // 保存数据
        String fileName = "" + chunkPos.x + "_" + chunkPos.y + "_" + chunkPos.z + ".chunk";
        try {
            Path path = PathManager.getInstance().getInstallPath().resolve("saves").resolve(fileName);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path.toFile()));
            this.blockData.writeExternal(out);
            out.close();


            //遍历所有的blockMap x ,y ,z ,dir
            if(blockMap!=null){
                StringBuffer sb = new StringBuffer();
                for(Map.Entry<Integer,IBlock> entry: blockMap.entrySet()){
                    Integer index = entry.getKey();
                    BaseBlock block = (BaseBlock) entry.getValue();

                    if(block.dir>0){
                        int y = index /16/16;
                        int yu = index %( 16 *16);
                        int z = yu /16;
                        int x = yu % 16;
                        sb.append(x).append(",").append(y).append(",").append(z).append(",").append(block.dir).append("\r\n");
                    }


                }
                String afixfileName = "" + chunkPos.x + "_" + chunkPos.y + "_" + chunkPos.z + ".map";
                Path afixPath = PathManager.getInstance().getInstallPath().resolve("saves").resolve(afixfileName);
                FileUtil.writeFile(afixPath.toFile(),sb.toString() );
            }
        } catch (FileNotFoundException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 将chunk 数据压缩 block 的xyz和blockvalue 组成一个int值存储
     * 
     * @return
     */
    @Override
    public Integer[] zip() {
        List<Integer> arr = new ArrayList<>();
        for (int x = 0; x < ChunkConstants.SIZE_X; x++) {

            for (int y = 0; y < ChunkConstants.SIZE_Y; y++) {

                for (int z = 0; z < ChunkConstants.SIZE_Z; z++) {
                    int value = blockData.get(x, y, z);
                    // x<<12 && y<<8&& z <<4 && value
                    if (value > 0) {
                        int unionValue = ByteUtil.unionBinary4_4_8_16(x, z, y, value);
                        /*
                         * if(y>8){ LogUtil.println("hello"); }
                         */
                        arr.add(unionValue);
                        // 自检 看
                        int newArr[] = ByteUtil.getValueSplit8Slot(unionValue);
                        if (newArr[0] != x || newArr[2] != y || newArr[1] != z || newArr[3] != value) {
                            LogUtil.err("some err xyz is not negative");
                        }
                        /*
                         * if(y>17){ LogUtil.println("hello"); }
                         */
                    }

                }
            }
        }
        // arr.clear();arr.add(ByteUtil.unionBinary4_4_8_16(0, 0,24, 103));
        Integer newArr[] = new Integer[arr.size()];
        arr.toArray(newArr);
        return newArr;
    }

    public static void main(String args[]) {
        /*
         * GL_Matrix translateMatrix = GL_Matrix.translateMatrix(-0.5f, 0,
         * -0.5f); // translateMatrix = GL_Matrix.multiply(
         * translateMatrix,GL_Matrix.rotateMatrix(0, 90 * 3.14f / 180, 0));
         * 
         * GL_Vector vector =new GL_Vector(0,0,0); vector=
         * GL_Matrix.multiply(translateMatrix,vector);
         * 
         * vector= GL_Matrix.multiply(GL_Matrix.rotateMatrix(0, 90 * 3.14f /
         * 180, 0),vector); vector=
         * GL_Matrix.multiply(GL_Matrix.translateMatrix(0.5f, 0, 0.5f),vector);
         */
        GL_Matrix translateMatrix = GL_Matrix.multiply(GL_Matrix.translateMatrix(0.5f, 0, 0.5f),
                GL_Matrix.rotateMatrix(0, 90 * 3.14f / 180, 0));
        translateMatrix = GL_Matrix.multiply(translateMatrix, GL_Matrix.translateMatrix(-0.5f, 0, -0.5f));
        GL_Vector vector = new GL_Vector(0, 0, 0);
        vector = GL_Matrix.multiply(translateMatrix, vector);

        int x = 8, y = 18, z = 0, value = 102;
        int unionValue = ByteUtil.unionBinary4_4_8_16(x, z, y, value);
        int[] terr = ByteUtil.getValueSplit8Slot(unionValue);

        System.out.println(ByteUtil.toBinaryStr(ByteUtil.get32_28Value(unionValue)));
        // System.out.println( ByteUtil.toBinaryStr(unionValue));
        // System.out.println( 102<<12 >>12);
        // System.out.println( (ByteUtil.toBinaryStr((8&ByteUtil.HEX_0_0_0_1 )
        // )));

    }

    /*
     * public static void main(String[] args) { IntBuffer vetices1 =
     * BufferUtils.createIntBuffer(16); vetices1.put(1); vetices1.put(3);
     * vetices1.position(vetices1.position() - 1);
     * System.out.println(vetices1.get()); vetices1.put(2);
     * vetices1.position(vetices1.position() - 1);
     * System.out.println(vetices1.get()); }
     */
    @Override
    public Map<Integer, IBlock> getBlockMap() {
        return this.blockMap;
    }
}
