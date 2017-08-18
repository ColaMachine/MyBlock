package cola.machine.game.myblocks.world.generator.ChunkGenerators;
import glapp.GLImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dozenx.util.ImageUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.WorldBiomeProvider;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Chunk;
import  cola.machine.game.myblocks.world.generator.*;
public class BasicHMTerrainGenerator implements FirstPassGenerator{
	  private IBlock mantle;
	    private IBlock water;
	    private IBlock stone;
	    private IBlock sand;
	    private IBlock grass;
	    private IBlock snow;
	private static final Logger logger = LoggerFactory.getLogger(BasicHMTerrainGenerator.class);
	  private float[][] heightmap;
	  public BasicHMTerrainGenerator() {
	        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
	        mantle = blockManager.getBlock("mantle");
	        water = blockManager.getBlock("water");
	        stone = blockManager.getBlock("stone");
	        sand = blockManager.getBlock("sand");
	        grass = blockManager.getBlock("soil");
	        snow = blockManager.getBlock("soil");
	    }
	@Override
	public void setWorldBiomeProvider(WorldBiomeProvider biomeProvider) {
		// VIP Auto-generated method stub
		
	}

	@Override
	public void setWorldSeed(String seed) {
		if(seed==null){
			return;
		}
		logger.info("Reading height map..");
		GLImage image = TextureManager.getImage("heightmap");
		
		ByteBuffer bb =image.getPixelBytes();
	    IntBuffer intBuf = bb.asIntBuffer();
	    int width =image.w;
	    int height=image.h;
	      heightmap =new float[width][height];
	      try {
	    	  heightmap = ImageUtil.getGrayPicturef("assets/images/gray.png");
			} catch (Exception e) {
				// VIP Auto-generated catch block
				e.printStackTrace();
			}
	  /*  while (intBuf.position() < intBuf.limit()) {
            int pos = intBuf.position();
            int val = intBuf.get();
            heightmap[pos % width][pos / width] = val / (256);
        }*/
	    //heightmap = shiftArray(rotateArray(heightmap), -50, -100);
		//ByteBuffer[] bb= image.get
		/*try {
			int[][] heights = ImageUtil.getGrayPicture("assets.images/gray.png");
		} catch (Exception e) {
			// VIP Auto-generated catch block
			e.printStackTrace();
		}*/
	    
		
	}
	 public static float[][] shiftArray(float[][] array, int x, int y) {
	        int size = array.length;
	        float[][] newArray = new float[size][size];
	        for (int i = 0; i < size; i++) {
	            for (int j = 0; j < size; j++) {
	                newArray[i][j] = array[(i + x + size) % size][(j + y + size) % size];
	            }
	        }
	        return newArray;
	    }

	@Override
	public Map<String, String> getInitParameters() {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public void setInitParameters(Map<String, String> initParameters) {
		// VIP Auto-generated method stub
		
	}
    //helper functions for the Mapdesign until real mapGen is in
    public static float[][] rotateArray(float[][] array) {
        float[][] newArray = new float[array[0].length][array.length];
        for (int i = 0; i < newArray.length; i++) {
            for (int j = 0; j < newArray[0].length; j++) {
                newArray[i][j] = array[j][array[j].length - i - 1];
            }
        }
        return newArray;
    }
    

    public void generateChunk(Chunk chunk) {
    	/* for (int x = 0; x < chunk.getChunkSizeX(); x++) {
             for (int z = 0; z < chunk.getChunkSizeZ(); z++) {
             	 chunk.setBlock(x, 3, z, mantle);
             }
         }
    	 if(true)return;*/
        int hmX = (((chunk.getChunkWorldPosX() / chunk.getChunkSizeX()) % 256) + 256) % 256;
        int hmZ = (((chunk.getChunkWorldPosZ() / chunk.getChunkSizeZ()) % 256) + 256) % 256;

        double scaleFactor = 0.05;

        double p00 = heightmap[hmX][hmZ] * scaleFactor;
        double p10 = heightmap[(hmX - 1 + 256) % 256][(hmZ) % 256] * scaleFactor;
        double p11 = heightmap[(hmX - 1 + 256) % 256][(hmZ + 1 + 256) % 256] * scaleFactor;
        double p01 = heightmap[(hmX) % 256][(hmZ + 1 + 256) % 256] * scaleFactor;
        
        
      
        for (int x = 0; x < chunk.getChunkSizeX(); x++) {
            for (int z = 0; z < chunk.getChunkSizeZ(); z++) {
               /* WorldBiomeProvider.Biome type = biomeProvider.getBiomeAt(
                        chunk.getBlockWorldPosX(x), chunk.getBlockWorldPosZ(z));*/

                //calculate avg height
                double interpolatedHeight = lerp(x / (double) chunk.getChunkSizeX(), lerp(z / (double) chunk.getChunkSizeZ(), p10, p11),
                        lerp(z / (double) chunk.getChunkSizeZ(), p00, p01));
                System.out.println("interpolatedHeight:"+p00);;

                //Scale the height to fit one chunk (suppose we have max height 20 on the Heigthmap
                //ToDo: Change this formula in later implementation of vertical chunks
                double threshold = Math.floor(interpolatedHeight);

                for (int y = chunk.getChunkSizeY() - 1; y >= 0; y--) {
                    if (y == 0) { // The very deepest layer of the world is an
                        // indestructible mantle
                        chunk.setBlock(x, y, z, mantle);
                        break;
                    } else if (y < threshold) {
                        chunk.setBlock(x, y, z, stone);
                    } else if (y == threshold) {
                        if (y < chunk.getChunkSizeY() * 0.05 + 1) {
                            chunk.setBlock(x, y, z, sand);
                        } else if (y < chunk.getChunkSizeY() * 0.05 * 12) {
                            chunk.setBlock(x, y, z, grass);
                        } else {
                            //chunk.setBlock(x, y, z, snow);
                        }
                    } else {
                        if (y <= chunk.getChunkSizeY() / 20) { // Ocean
                            chunk.setBlock(x, y, z, water);
                           /* chunk.setLiquid(x, y, z, new LiquidData(LiquidType.WATER,
                                    LiquidData.MAX_LIQUID_DEPTH));*/

                        } else {
                            //chunk.setBlock(x, y, z, air);
                        }
                    }
                }
            }
        }
    }

    private static double lerp(double t, double a, double b) {
        return a + fade(t) * (b - a);  //not sure if i should fade t, needs a bit longer to generate chunks but is definately nicer
    }

    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
}
