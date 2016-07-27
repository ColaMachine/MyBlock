package cola.machine.game.myblocks.manager;

import cola.machine.game.myblocks.engine.paths.PathManager;
import glapp.GLApp;
import glapp.GLImage;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

import cola.machine.game.myblocks.model.textture.TextureInfo;

import javax.imageio.ImageIO;


/**
 * Created by luying on 14-8-28.
 */
public class TextureManager {
    Path installPath;
    public static HashMap<String, GLImage> imageMap = new HashMap<String, GLImage>();

    public static HashMap<String, TextureInfo> textureMap = new HashMap<String, TextureInfo>();

    public TextureManager() {
        installPath = PathManager.getInstance().getInstallPath();
        this.put("grass_top", "assets/blockTiles/plant/Grass.png");
        this.put(
                "human", "images/char.png"
        );
        textureMap.put("human", new TextureInfo("human"));
        this.put("heightmap", "images/gray.png");
        textureMap.put("heightmap", new TextureInfo("heightmap"));
        this.put("background", "images/background.png");

        textureMap.put("background", new TextureInfo("background"));
        this.put("gui", "images/gui.png");
        textureMap.put("cross", new TextureInfo("gui", 1 / 12f + 0.01f, 10 / 12f, 1 / 12f, 1 / 12f, true));

        textureMap.put("selectBox", new TextureInfo("gui", 0, 210, 23, 23));


        this.put("widgets", "assets/minecraft/textures/gui/widgets.png");
        textureMap.put("toolbar", new TextureInfo("widgets", 0, 469, 362, 43));

        //this.put("night", "images/night.jpg");
        //textureMap.put("night", new TextureInfo("night"));

        //this.put("most","images/fewest.tif");
        //textureMap.put("most",new TextureInfo("most"));
        this.put("sun", "assets/minecraft/textures/environment/sun.png");
        textureMap.put("sun", new TextureInfo("sun"));

        this.put("inventory", "assets/minecraft/textures/gui/container/inventory.png");
        textureMap.put("bag", new TextureInfo("inventory", 0, 179, 352, 332));

        this.put("human", "images/2000.png");
        //textureMap.put("bag",new TextureInfo("inventory",0,179,352,332));

        this.put("apple_golden", "assets/minecraft/textures/items/apple_golden.png");

        this.put("items", "images/items.png");


        textureMap.put("apple_golden", new TextureInfo("apple_golden"));

        this.put("terrain", "assets/minecraft/textures/terrain.png");
        textureMap.put("water", new TextureInfo("terrain", 14, 2, 1, 1, 16, 16));

       this.put("gold_armor","assets/minecraft/textures/models/armor/gold_layer_2.png");
        textureMap.put("gold_armor", new TextureInfo("gold_armor"));
      
        this.put("particle","images/Particle.bmp");
        textureMap.put("particle", new TextureInfo("particle"));
      
       
        textureMap.put("grass_top", new TextureInfo("terrain", 8, 5, 0.8f, 0.8f, 16, 16));
        textureMap.put("mantle", new TextureInfo("terrain", 1, 14, 1, 1, 16, 16));
        textureMap.put("glass", new TextureInfo("terrain", 1, 12, 1, 1, 16, 16));

        textureMap.put("wood", new TextureInfo("terrain", 4, 15, 1, 1, 16, 16));


        textureMap.put("soil", new TextureInfo("terrain", 2, 15, 1, 1, 16, 16));

        textureMap.put("soil_side", new TextureInfo("terrain", 3, 15, 1, 1, 16, 16));

        textureMap.put("stone", new TextureInfo("terrain", 1, 15, 1, 1, 16, 16));

        textureMap.put("sand", new TextureInfo("terrain", 2, 14, 1, 1, 16, 16));
        textureMap.put("grass_side", new TextureInfo("terrain", 3, 15, 1, 1, 16, 16));
        textureMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));

        textureMap.put("borken_0", new TextureInfo("terrain", 0, 0, 1, 1, 16, 16));
        textureMap.put("borken_1", new TextureInfo("terrain", 1, 0, 1, 1, 16, 16));
        textureMap.put("borken_2", new TextureInfo("terrain", 2, 0, 1, 1, 16, 16));
        textureMap.put("borken_3", new TextureInfo("terrain", 3, 0, 1, 1, 16, 16));
        textureMap.put("borken_4", new TextureInfo("terrain", 4, 0, 1, 1, 16, 16));
        textureMap.put("borken_5", new TextureInfo("terrain", 5, 0, 1, 1, 16, 16));
        textureMap.put("borken_6", new TextureInfo("terrain", 6, 0, 1, 1, 16, 16));
        textureMap.put("borken_7", new TextureInfo("terrain", 7, 0, 1, 1, 16, 16));
        textureMap.put("borken_8", new TextureInfo("terrain", 8, 0, 1, 1, 16, 16));
        textureMap.put("borken_9", new TextureInfo("terrain", 9, 0, 1, 1, 16, 16));
        textureMap.put("borken_10", new TextureInfo("terrain", 10, 0, 1, 1, 16, 16));

        textureMap.put("humanBody-front", new TextureInfo("human", 20, 0, 8, 12, 64, 32));
        textureMap.put("humanBody-back", new TextureInfo("human", 32, 0, 8, 12, 64, 32));
        textureMap.put("humanBody-left", new TextureInfo("human", 16, 0, 4, 12, 64, 32));
        textureMap.put("humanBody-right", new TextureInfo("human", 28, 0, 4, 12, 64, 32));
        textureMap.put("humanBody-top", new TextureInfo("human", 20, 12, 8, 4, 64, 32));
        textureMap.put("humanBody-bottom", new TextureInfo("human", 28, 12, 8, 4, 64, 32));

        textureMap.put("humanHand-front", new TextureInfo("human", 40, 0, 4, 12, 64, 32));
        textureMap.put("humanHand-back", new TextureInfo("human", 44, 0, 4, 12, 64, 32));
        textureMap.put("humanHand-left", new TextureInfo("human", 48, 0, 4, 12, 64, 32));
        textureMap.put("humanHand-right", new TextureInfo("human", 52, 0, 4, 12, 64, 32));
        textureMap.put("humanHand-top", new TextureInfo("human", 44, 12, 4, 4, 64, 32));
        textureMap.put("humanHand-bottom", new TextureInfo("human", 48, 12, 4, 4, 64, 32));

        textureMap.put("humanLeg-front", new TextureInfo("human", 0, 0, 4, 12, 64, 32));
        textureMap.put("humanLeg-back", new TextureInfo("human", 0, 0, 4, 12, 64, 32));
        textureMap.put("humanLeg-left", new TextureInfo("human", 0, 0, 4, 12, 64, 32));
        textureMap.put("humanLeg-right", new TextureInfo("human", 0, 0, 4, 12, 64, 32));
        textureMap.put("humanLeg-top", new TextureInfo("human", 4, 12, 4, 4, 64, 32));
        textureMap.put("humanLeg-bottom", new TextureInfo("human", 4, 12, 4, 4, 64, 32));

        textureMap.put("humanHead-front", new TextureInfo("human", 8, 16, 8, 8, 64, 32));
        textureMap.put("humanHead-back", new TextureInfo("human", 24,16, 8, 8, 64, 32));
        textureMap.put("humanHead-left", new TextureInfo("human", 16, 16, 8, 8, 64, 32));
        textureMap.put("humanHead-right", new TextureInfo("human", 0, 16, 8, 8, 64, 32));
        textureMap.put("humanHead-top", new TextureInfo("human", 8, 24, 8, 8, 64, 32));
        textureMap.put("humanHead-bottom", new TextureInfo("human", 16, 16, 8, 8, 64, 32));

//        textureMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));
//        textureMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));
//        textureMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));
//        textureMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));
//        textureMap.put("gold_sword", new TextureInfo("items", 4, 10, 1, 1, 16, 16));

    }

    public void put(String name, String textureImagePath) {
        int textureHandle = 0;

        GLImage textureImg;
        try {
            textureImg = GLApp.loadImage(installPath.resolve(textureImagePath).toUri());//
            //Image image=        ImageIO.read(new File(installPath.resolve(textureImagePath).toUri()));
            if (textureImg != null) {
                textureImg.textureHandle = GLApp.makeTexture(textureImg);
                GLApp.makeTextureMipMap(textureHandle, textureImg);
            }

            imageMap.put(name, textureImg);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }

    }

    public void preInit() {

    }

    public static TextureInfo getTextureInfo(String name) {
        return textureMap.get(name);
    }

    public static GLImage getImage(String name) {
        return imageMap.get(name);
    }
}
