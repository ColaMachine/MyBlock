package cola.machine.game.myblocks.world.chunks.Internal;

import glapp.GLApp;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkBlockIterator;
import cola.machine.game.myblocks.world.chunks.ChunkConstants;
import cola.machine.game.myblocks.world.chunks.Region3i;
import cola.machine.game.myblocks.world.chunks.Vector3i;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraDenseArray16Bit;

public class ChunkImpl implements Chunk {

	public int displayId=0;
	public ChunkImpl(Vector3i chunkPos) {
		this(chunkPos.x, chunkPos.y, chunkPos.z);
	}

	private final Vector3i chunkPos = new Vector3i();
	private BlockManager blockManager;
	private Region3i region;

	protected ChunkImpl() {
		blockData = new TeraDenseArray16Bit(getChunkSizeX(), getChunkSizeY(),
				getChunkSizeZ());
		// sunlightData =
		// c.getSunlightDataEntry().factory.create(getChunkSizeX(),
		// getChunkSizeY(), getChunkSizeZ());
		// lightData = c.getLightDataEntry().factory.create(getChunkSizeX(),
		// getChunkSizeY(), getChunkSizeZ());
		// extraData = c.getExtraDataEntry().factory.create(getChunkSizeX(),
		// getChunkSizeY(), getChunkSizeZ());
		blockManager = CoreRegistry.get(BlockManager.class);
	}

	public ChunkImpl(int x, int y, int z) {
		this();
		chunkPos.x = x;
		chunkPos.y = y;
		chunkPos.z = z;
		region = Region3i.createFromMinAndSize(new Vector3i(x
				* ChunkConstants.SIZE_X, y * ChunkConstants.SIZE_Y, z
				* ChunkConstants.SIZE_Z), ChunkConstants.CHUNK_SIZE);
	}

	@Override
	public Vector3i getPos() {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public Block getBlock(Vector3i pos) {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		// VIP Auto-generated method stub
		return null;
	}

	private TeraArray blockData;

	@Override
	public Block setBlock(int x, int y, int z, Block block) {
		int oldValue = blockData.set(x, y, z, block.getId());
		if (oldValue != block.getId()) {
			/*
			 * if (!block.isLiquid()) { setLiquid(x, y, z, new LiquidData()); }
			 */
		}
		return blockManager.getBlock((short) oldValue);
	}
	TextureInfo ti=TextureManager.getIcon("soil");
	public void build() {

displayId = GLApp.beginDisplayList();

//block.render();
GL11.glBegin(GL11.GL_QUADS);

		for (int x = 0; x < this.getChunkSizeX(); x++) {
			for (int z = 0; z < this.getChunkSizeZ(); z++) {
				for (int y = 0; y < this.getChunkSizeY(); y++) {
					int i = blockData.get(x, y, z);
					if (i > 0) {//System.out.printf("%d %d %d /n\n",x,y,z);
						// 判断上面
						if (y < this.getChunkSizeY() - 1) {
							if (blockData.get(x, y + 1, z) == 0) {
								addThisTop(x, y, z);
							} else {

							}
						} else {
							addThisTop(x, y, z);
						}

						// 判断下面
						if (y > 0) {
							if (blockData.get(x, y - 1, z) == 0) {
								addThisBottom(x, y, z);
							}
						} else {
							addThisBottom(x, y, z);
						}

						// 判断左面
						if (x > 0) {
							if (blockData.get(x - 1, y, z) == 0) {
								addThisLeft(x, y, z);
							}
						} else {
							addThisLeft(x, y, z);
						}

						// 判断右面

						if (x < this.getChunkSizeX() - 1) {
							if (blockData.get(x + 1, y, z) == 0) {
								addThisRight(x, y, z);
							}
						} else {
							addThisLeft(x, y, z);
						}

						// 前面
						if (z < this.getChunkSizeZ() - 1) {
							if (blockData.get(x, y, z + 1) == 0) {
								addThisFront(x, y, z);
							}
						} else {
							addThisFront(x, y, z);
						}

						// 后面

						if (z >0) {
							if (blockData.get(x, y, z - 1) == 0) {
								addThisBack(x, y, z);
							}
						} else {
							addThisBack(x, y, z);
						}

					}

				}
			}
		}		GL11.glEnd();
		System.out.println(this.count);
		GLApp.endDisplayList();
	}
	public IntBuffer vetices = BufferUtils.createIntBuffer(14);
	public int count = 0;
	public IntBuffer normalizes = BufferUtils.createIntBuffer(4);

	public void addThisTop(int x, int y, int z) {
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
		normalizes.put(0);Draw();
	}
	public static void main(String[] args){
		 IntBuffer vetices1 = BufferUtils.createIntBuffer(16);
		 vetices1.put(1); vetices1.put(3);
		 vetices1.position(vetices1.position()-1);
		 System.out.println(vetices1.get());
		 vetices1.put(2);
		 vetices1.position(vetices1.position()-1);
		 System.out.println(vetices1.get());
	}
	public void Draw(){
		vetices.position(vetices.position()-12);
		normalizes.position(normalizes.position()-3);
		 GL11.glNormal3f( normalizes.get(), normalizes.get(),  normalizes.get());
			
		   GL11.glTexCoord2f(ti.minX,ti.minY);GL11.glVertex3d(vetices.get(), vetices.get(), vetices.get());
		   GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3d(vetices.get(),vetices.get(), vetices.get());
		   GL11.glTexCoord2f(ti.maxX,ti.maxY);GL11.glVertex3d(vetices.get(),vetices.get(), vetices.get());
		   GL11.glTexCoord2f(ti.minX,ti.maxY);GL11.glVertex3d(vetices.get(),vetices.get(), vetices.get());
		  // vetices.flip();
		   normalizes.position(0);
		   vetices.position(0);
	}
	public void addThisBottom(int x, int y, int z) {
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

	public void addThisBack(int x, int y, int z) {
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

	public void addThisLeft(int x, int y, int z) {
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
		normalizes.put(0);Draw();
	}

	public void addThisRight(int x, int y, int z) {
		count++;

		vetices.put(x + 1);
		vetices.put(y);
		vetices.put(z);

		vetices.put(x + 1);
		vetices.put(y + 1);
		vetices.put(z);

		vetices.put(x + 1);
		vetices.put(y + 1);
		vetices.put(z + 1);

		vetices.put(x + 1);
		vetices.put(y);
		vetices.put(z + 1);

		normalizes.put(1);
		normalizes.put(0);
		normalizes.put(0);Draw();

	}
	/*public List<Integer> list = new ArrayList();
	public int count = 0;
	public List<Integer> normalizelist = new ArrayList();

	public void addThisTop(int x, int y, int z) {
		count++;
		list.add(x);
		list.add(y + 1);
		list.add(z + 1);

		list.add(x + 1);
		list.add(y + 1);
		list.add(z + 1);

		list.add(x + 1);
		list.add(y + 1);
		list.add(z);

		list.add(x);
		list.add(y + 1);
		list.add(z);

		normalizelist.add(0);
		normalizelist.add(1);
		normalizelist.add(0);Draw();
	}

	public void addThisBottom(int x, int y, int z) {
		count++;
		list.add(x);
		list.add(y);
		list.add(z);

		list.add(x + 1);
		list.add(y);
		list.add(z);

		list.add(x + 1);
		list.add(y);
		list.add(z + 1);

		list.add(x);
		list.add(y);
		list.add(z + 1);
		normalizelist.add(0);
		normalizelist.add(-1);
		normalizelist.add(0);Draw();
	}

	public void addThisFront(int x, int y, int z) {
		list.add(x);
		count++;
		list.add(y);
		list.add(z + 1);

		list.add(x + 1);
		list.add(y);
		list.add(z + 1);

		list.add(x + 1);
		list.add(y + 1);
		list.add(z + 1);

		list.add(x);
		list.add(y + 1);
		list.add(z + 1);

		normalizelist.add(0);
		normalizelist.add(0);
		normalizelist.add(1);Draw();

	}

	public void addThisBack(int x, int y, int z) {
		count++;

		list.add(x + 1);
		list.add(y);
		list.add(z);

		list.add(x);
		list.add(y);
		list.add(z);

		list.add(x);
		list.add(y + 1);
		list.add(z);

		list.add(x + 1);
		list.add(y + 1);
		list.add(z);

		normalizelist.add(0);
		normalizelist.add(0);
		normalizelist.add(-1);Draw();

	}

	public void addThisLeft(int x, int y, int z) {
		count++;
		list.add(x);
		list.add(y);
		list.add(z);

		list.add(x);
		list.add(y);
		list.add(z + 1);

		list.add(x);
		list.add(y + 1);
		list.add(z + 1);

		list.add(x);
		list.add(y + 1);
		list.add(z);
		normalizelist.add(-1);
		normalizelist.add(0);
		normalizelist.add(0);Draw();
	}

	public void addThisRight(int x, int y, int z) {
		count++;

		list.add(x + 1);
		list.add(y);
		list.add(z);

		list.add(x + 1);
		list.add(y + 1);
		list.add(z);

		list.add(x + 1);
		list.add(y + 1);
		list.add(z + 1);

		list.add(x + 1);
		list.add(y);
		list.add(z + 1);

		normalizelist.add(1);
		normalizelist.add(0);
		normalizelist.add(0);

	}*/

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
	public Block setBlock(Vector3i pos, Block block) {
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
		return new ChunkBlockIteratorImpl(blockManager, getChunkWorldPos(),
				blockData);
	}
	
	public void disposeMesh(){
		GL11.glDeleteLists(this.displayId,GL11.GL_COMPILE);
		this.blockData=null;
		
		
	}
}
