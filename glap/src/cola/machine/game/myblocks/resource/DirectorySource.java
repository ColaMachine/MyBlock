package cola.machine.game.myblocks.resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectorySource extends AbstractSource{
	public DirectorySource(String id,Path rootAssetsDirectory) {
		super(id);
		clear();
		
		if(Files.isDirectory(rootAssetsDirectory)){
			scanResources(rootAssetsDirectory,rootAssetsDirectory);
		}
	}
	
	private void scanResources(Path path,Path basePath){
		try(DirectoryStream<Path> stream =Files.newDirectoryStream(path)){
			for(Path child : stream){
				if(Files.isDirectory(child)){
					scanResources(child,basePath);
				}else if(Files.isRegularFile(child)){
					Path relativePath = basePath.relativize(child);
					ResourceUri uri =getUri(relativePath);//获取一级目录 文件名 还有后缀名 去获得对应的resourcetype
					if(uri!=null){
						try{
							addItem(uri,child.toUri().toURL());// type - uri  uri - url
						}catch(MalformedURLException e){
							logger.warn("Failed to load resource {}",relativePath,e);
						}
					}
				}
			}
		}catch(IOException e){
			logger.warn("Failed to scan path {}",path,e);
		}
	}
	
	
	private static final Logger logger =LoggerFactory.getLogger(DirectorySource.class);
	
	
	
	
}
