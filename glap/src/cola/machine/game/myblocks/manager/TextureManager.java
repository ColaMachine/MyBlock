package cola.machine.game.myblocks.manager;

import cola.machine.game.myblocks.model.textture.TextureInfo;
import glapp.GLApp;
import glapp.GLImage;

import java.util.HashMap;

/**
 * Created by luying on 14-8-28.
 */
public class TextureManager {

    public static HashMap<String,GLImage> textureMap =new HashMap<String,GLImage>();

    public static HashMap<String,TextureInfo> iconMap=new HashMap<String,TextureInfo>();
    public TextureManager(){
        this.put("widgets","glap/assets/minecraft/textures/gui/widgets.png");
        iconMap.put("toolbar",new TextureInfo("widgets",0,469,362,43));
    }
    public void put(String name ,String textureImagePath){
        int textureHandle = 0;
        GLImage textureImg = GLApp.loadImage(textureImagePath);
        if (textureImg != null) {
            textureImg.textureHandle = GLApp.makeTexture(textureImg);
            GLApp.makeTextureMipMap(textureHandle,textureImg);
        }

        textureMap.put(name, textureImg);
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
