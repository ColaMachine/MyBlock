package cola.machine.game.myblocks.config;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.terasology.naming.Name;

import org.terasology.naming.Version;
import org.terasology.naming.gson.NameTypeAdapter;
import org.terasology.naming.gson.VersionTypeAdapter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by luying on 14-10-5.
 */
public class Config {
    private static final Logger logger =LoggerFactory.getLogger(Config.class);

    private SystemConfig system = new SystemConfig();

    private PlayerConfig player =new PlayerConfig();
    private InputConfig input =new InputConfig();
//    private AudioConfig auidio =new InputConfig();
    private RenderingConfig rendering =new RenderingConfig();
//    private ModuleConfig rendering =new RenderingConfig();
//    private ModuleCOnfig defaultModSelection =new ModuleConfig();
//    private WorldGenerationConfig worldGeneration = new WorldGenerationConfig();
//    private Map<SimpleUri,Map<String,JsonElement>> moduleConfigs =Maps.newHashMap();
//    private NetworkConfig network =new NetworkConfig();
//    private SecurityConfig security =new SecurityConfig();
    
    public Config(){
    	
    }
    public InputConfig getInput(){
    	return input;
    }
	public SystemConfig getSystem() {
		return system;
	}
	public PlayerConfig getPlayer() {
		return player;
	}
//	public AudioConfig getAuidio() {
//		return auidio;
//	}
	public RenderingConfig getRendering() {
		return rendering;
	}
//	public ModuleConfig getRendering() {
//		return rendering;
//	}
//	public ModuleCOnfig getDefaultModSelection() {
//		return defaultModSelection;
//	}
//	public WorldGenerationConfig getWorldGeneration() {
//		return worldGeneration;
//	}
//	public Map<SimpleUri, Map<String, JsonElement>> getModuleConfigs() {
//		return moduleConfigs;
//	}
//	public NetworkConfig getNetwork() {
//		return network;
//	}
//	public SecurityConfig getSecurity() {
//		return security;
//	}
	public void save(){
		try{
			save(getConfigFile(),this);
		}catch (IOException e) {
			logger.error("Failed to save config", e );
		}
	}
	public static Path getConfigFile(){
		return PathManager.getInstance().getHomePath().resolve("config.cfg");
	}
	
	public static void save(Path toFile,Config config)throws IOException{
		try(BufferedWriter writer=Files.newBufferedWriter(toFile, Constants.CHARSET)){
			createGson().toJson(config,writer);
		}
	}
	protected static Gson createGson(){
		/*return new GsonBuilder()
				.registerTypeAdapter(Name.class, new NameTypeAdapter())
                .registerTypeAdapter(Version.class, new VersionTypeAdapter())
                .registerTypeAdapter(BindsConfig.class, new BindsConfig.Handler())
        .setPrettyPrinting().create();
		*/
        return null;
	}
    
}
