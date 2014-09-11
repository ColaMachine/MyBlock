package cola.machine.game.myblocks.world.generator.ChunkGenerators;
import glapp.GLImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ImageUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.WorldBiomeProvider;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Chunk;
import  cola.machine.game.myblocks.world.generator.*;
public class GrayTerrainGenerator implements FirstPassGenerator{
	  private Block mantle;
	    private Block water;
	    private Block stone;
	    private Block sand;
	    private Block grass;
	    private Block snow;
	private static final Logger logger = LoggerFactory.getLogger(GrayTerrainGenerator.class);
	  private float[][] heightmap;
	  public GrayTerrainGenerator() {
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
		GLImage image = TextureManager.getTextureHandle("heightmap");
		
		ByteBuffer bb =image.getPixelBytes();
	    IntBuffer intBuf = bb.asIntBuffer();
	    int width =image.w;
	    int height=image.h;
	      heightmap =new float[width][height];
	      try {
	    	  heightmap = ImageUtil.getGrayPicturef("images/gray.png");
			} catch (Exception e) {
				// VIP Auto-generated catch block
				e.printStackTrace();
			}
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
   
        double scaleFactor = 0.05;

        
		
      
        for (int x = 0; x < chunk.getChunkSizeX(); x++) {
            for (int z = 0; z < chunk.getChunkSizeZ(); z++) {
            	int _x= (chunk.getBlockWorldPosX(x)+256)%256;
            	int _z= (chunk.getBlockWorldPosZ(z)+256)%256;
            	
            	float height = heightmap[_x][_z];
				//System.out.println("H:"+height);
            double interpolatedHeight= heightmap[_x][_z]*scaleFactor;
                double threshold = Math.floor(interpolatedHeight);
                for (int y = 0; y <= height / 20; y++) {
					 chunk.setBlock(x, y, z, mantle);
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
