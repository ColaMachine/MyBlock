package cola.machine.game.myblocks.utilities.concurrency;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;

import org.lwjgl.LWJGLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import cola.machine.game.myblocks.engine.paths.PathManager;

public class LWJGLHelper {
	
	private static final Logger logger =LoggerFactory.getLogger(LWJGLHelper.class);
	public static void initNativeLibs(){
		//判断当前的系统
		Path nativePath= PathManager.getInstance().getNativesPath();
		Path osNativePath=null;
		int platForm= LWJGLUtil.getPlatform();
		switch (platForm){
			case LWJGLUtil.PLATFORM_WINDOWS:
				osNativePath=nativePath.resolve("windows");
				break;
			case LWJGLUtil.PLATFORM_MACOSX:
				osNativePath=nativePath.resolve("macosx");
				break;
			case LWJGLUtil.PLATFORM_LINUX:
				osNativePath=nativePath.resolve("linux");
				break;

		}
		addLibraryPath(osNativePath);
	}
	public static void addLibraryPath(Path path){//通过反射的方式设置java.library.pathx
		String envpath= System.getProperty("java.library.path");
		//System.setProperty("java.library.path",path.toAbsolutePath().toString()+File.pathSeparator+ envpath);
        // envpath= System.getProperty("java.library.path");
		try{
		final Field usrPathField = ClassLoader.class.getDeclaredField("usr_paths");
		usrPathField.setAccessible(true);
		List<String> paths = Lists.newArrayList((String[]) usrPathField.get(null));
		if(paths.contains(path.toAbsolutePath().toString())){
			return;
		}
		paths.add(0,path.toAbsolutePath().toString());
		usrPathField.set(null, paths.toArray(new String[paths.size()]));
		}catch(SecurityException e){
			
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
