package cola.machine.game.myblocks.world.chunks.Internal;



import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkBlockIterator;
import cola.machine.game.myblocks.world.chunks.ChunkConstants;
import cola.machine.game.myblocks.world.chunks.Region3i;
import cola.machine.game.myblocks.world.chunks.Vector3i;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraDenseArray16Bit;

public class ChunkImpl implements Chunk{

	  public ChunkImpl(Vector3i chunkPos) {
	        this(chunkPos.x, chunkPos.y, chunkPos.z);
	    }
	  private final Vector3i chunkPos = new Vector3i();
	    private BlockManager blockManager;
	    private Region3i region;
	  protected ChunkImpl() {
	        blockData =
	        new TeraDenseArray16Bit(getChunkSizeX(), getChunkSizeY(), getChunkSizeZ());
	       // sunlightData = c.getSunlightDataEntry().factory.create(getChunkSizeX(), getChunkSizeY(), getChunkSizeZ());
	       // lightData = c.getLightDataEntry().factory.create(getChunkSizeX(), getChunkSizeY(), getChunkSizeZ());
	      //  extraData = c.getExtraDataEntry().factory.create(getChunkSizeX(), getChunkSizeY(), getChunkSizeZ());
	        blockManager = CoreRegistry.get(BlockManager.class);
	    }

	  public ChunkImpl(int x, int y, int z) {
	        this();
	        chunkPos.x = x;
	        chunkPos.y = y;
	        chunkPos.z = z;
	        region = Region3i.createFromMinAndSize(new Vector3i(x * ChunkConstants.SIZE_X, y * ChunkConstants.SIZE_Y, z * ChunkConstants.SIZE_Z), ChunkConstants.CHUNK_SIZE);
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
            /*if (!block.isLiquid()) {
                setLiquid(x, y, z, new LiquidData());
            }*/
        }
        return blockManager.getBlock((short) oldValue);
    }
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
		@Override
		public void setLiquid(Vector3i pos, LiquidData state) {
			// VIP Auto-generated method stub
			
		}

		@Override
		public void setLiquid(int x, int y, int z, LiquidData newState) {
			// VIP Auto-generated method stub
			
		}

		@Override
		public LiquidData getLiquid(Vector3i pos) {
			// VIP Auto-generated method stub
			return null;
		}

		@Override
		public LiquidData getLiquid(int x, int y, int z) {
			// VIP Auto-generated method stub
			return null;
		}
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
			// VIP Auto-generated method stub
			return 0;
		}

		@Override
		public int getBlockWorldPosY(int y) {
			// VIP Auto-generated method stub
			return 0;
		}

		@Override
		public int getBlockWorldPosZ(int z) {
			// VIP Auto-generated method stub
			return 0;
		}

		 public ChunkBlockIterator getBlockIterator() {
		        return new ChunkBlockIteratorImpl(blockManager, getChunkWorldPos(), blockData);
		    }
}
