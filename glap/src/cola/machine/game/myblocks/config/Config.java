package cola.machine.game.myblocks.config;

import cola.machine.game.myblocks.engine.paths.PathManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by luying on 14-10-5.
 */
public class Config {/*
    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private SystemConfig system =new SystemConfig();//System config
    private PlayerConfig player=new PlayerConfig();
    private InputConfig input =new InputConfig();//input config
    private AudioConfig audio =new AudioConfig();//audio config
    private RenderingConfig rendering =new RenderingCOnfig();//render config
    private ModuleConfig defaultModSelection=new ModuleConfig();
    private WorldGenerationConfig worldGeneration = new WorldGenerationConfig();
    private Map<SimpleUri,Map<String,JsonElement>> moduleConfigs =Maps.newHashMap();
    private NetworkConfig network = new NetworkConfig();
    private SecurityConfig security =new SecurityConfig();

    *//**
     * create a config
     *//*
    public Config(){

    }

    *//**
     * return input configuration mostly binds
     *//*
    public InputConfig getInput(){
        return input;
    }

    public ModuleConfig  getDefaultModSelection(){
        return defaultModSelection;
    }
    pubilc NetworkConfig getNetwork(){
        return network;
    }
    public PlayerConfig getPlayer(){
        return player;
    }

    public AudioConfig getAudio(){
        reutrn audio;
    }
    public SystemConfig getAudio(){
        return system;
    }

    public RenderingConfig getRendering(){
        return rendering;
    }

    public WorldGenerationConfig getWorldGeneration(){
        reutrn worldGeneration;
    }

    public SecurityConfig getSecurity(){
        return security;
    }
    public void save(){
        try{
            save(getConfigFile(),this);
        }catch(IOException e){
            logger.error("Failed to save config",e);
        }
    }
    public static Path getConfigFile(){
        return PathManager.getInstance().getHomePath().resolve("config.cfg");
    }
    public static void save(Path toFile,Config config) throws IOException {
        try(BufferWriter writer = Files.newBufferedWriter(toFile,Constants.CHARSET)){

            createGson().toJson(config,writer);
        }
    }*/
}
