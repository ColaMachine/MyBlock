package cola.machine.game.myblocks.manager;

import cola.machine.game.myblocks.model.textture.TextureInfo;
import glapp.GLApp;
import glapp.GLImage;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.terasology.engine.paths.PathManager;

/**
 * Created by luying on 14-8-28.
 */
public class TextureManager {
Path installPath;
    public static HashMap<String,GLImage> textureMap =new HashMap<String,GLImage>();

    public static HashMap<String,TextureInfo> iconMap=new HashMap<String,TextureInfo>();
    public TextureManager(){
   	//installPath =PathManager.getInstance().getInstallPath();
   	
   	
    this.put("background","images/background.png");
    iconMap.put("background",new TextureInfo("background"));
       this.put("gui","images/gui.png");
        iconMap.put("cross",new TextureInfo("gui",1/12f+0.01f,10/12f,1/12f,1/12f,true));
        
        iconMap.put("selectBox",new TextureInfo("gui",0,210,25,26));
        
        
        this.put("widgets","assets/minecraft/textures/gui/widgets.png");
        iconMap.put("toolbar",new TextureInfo("widgets",0,469,362,43));

        this.put("inventory","assets/minecraft/textures/gui/container/inventory.png");
        iconMap.put("bag",new TextureInfo("inventory",0,179,352,332));

        this.put("human","images/2000.png");
        //iconMap.put("bag",new TextureInfo("inventory",0,179,352,332));

        this.put("apple_golden","assets/minecraft/textures/items/apple_golden.png");

        iconMap.put("apple_golden",new TextureInfo("apple_golden"));
        
        this.put("terrain","assets/minecraft/textures/terrain.png");
        iconMap.put("water",new TextureInfo("terrain",14,2,1,1,16,16));
        
        iconMap.put("glass",new TextureInfo("terrain",13,0,1,1,16,16));
        
        iconMap.put("wood",new TextureInfo("terrain",4,15,1,1,16,16));
        
        
        iconMap.put("soil",new TextureInfo("terrain",2,15,1,1,16,16));
        
        iconMap.put("soil_side",new TextureInfo("terrain",3,15,1,1,16,16));
        
        iconMap.put("stone",new TextureInfo("terrain",1,15,1,1,16,16));
        
        iconMap.put("sand",new TextureInfo("terrain",2,14,1,1,16,16));
    }
    public void put(String name ,String textureImagePath){
        int textureHandle = 0;
        
        GLImage textureImg;
		try {
			textureImg = GLApp.loadImage(textureImagePath);//installPath.resolve(textureImagePath).toUri()
			//ImageIO.read(new File(installPath.resolve(textureImagePath).toUri()));
			if (textureImg != null) {
		            textureImg.textureHandle = GLApp.makeTexture(textureImg);
		            GLApp.makeTextureMipMap(textureHandle,textureImg);
		        }

		        textureMap.put(name, textureImg);
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
      
    }

    public void preInit(){

    }
    public static TextureInfo getIcon(String name){
        return iconMap.get(name);
    }
    public static GLImage getTextureHandle(String name){
        return textureMap.get(name);
    }
}
