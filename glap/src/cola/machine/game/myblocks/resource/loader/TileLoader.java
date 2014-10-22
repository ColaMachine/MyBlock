package cola.machine.game.myblocks.resource.loader;

import glapp.GLApp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.math.IntMath;

import cola.machine.game.myblocks.resource.ResourceLoader;
import cola.machine.game.myblocks.resource.ResourceType;
import cola.machine.game.myblocks.resource.data.TileData;

public class TileLoader implements ResourceLoader<TileData>{
	private static final Logger logger =LoggerFactory.getLogger(TileLoader.class);
	@Override
	public TileData load(ResourceType type, InputStream inputStream) throws IOException {
		BufferedImage image =ImageIO.read(inputStream);
		if(!IntMath.isPowerOfTwo(image.getHeight()) || !(image.getWidth() == image.getHeight()))
		{
			logger.warn("invalid tile - must be square with powere of two slides 图片的长宽必须是2的倍数 并且长宽必须相等 是正方形");
			
		}
		return new TileData(image);
	}

}
