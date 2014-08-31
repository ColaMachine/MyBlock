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
        this.put("gui","glap/images/gui.png");
        iconMap.put("cross",new TextureInfo("gui",1/12f+0.01f,10/12f,1/12f,1/12f,true));
        this.put("widgets","glap/assets/minecraft/textures/gui/widgets.png");
        iconMap.put("toolbar",new TextureInfo("widgets",0,469,362,43));

        this.put("inventory","glap/assets/minecraft/textures/gui/container/inventory.png");
        iconMap.put("bag",new TextureInfo("inventory",0,179,352,332));

        this.put("human","glap/images/2000.png");
        //iconMap.put("bag",new TextureInfo("inventory",0,179,352,332));

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
