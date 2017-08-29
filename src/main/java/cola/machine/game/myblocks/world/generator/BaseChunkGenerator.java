package cola.machine.game.myblocks.world.generator;

import cola.machine.game.myblocks.world.WorldBiomeProvider;

import java.util.Map;

public interface BaseChunkGenerator {
	void setWorldBiomeProvider(WorldBiomeProvider biomeProvider);
	
	void setWorldSeed(String seed);
	
	Map<String,String> getInitParameters();
	
	void setInitParameters(Map<String,String> initParameters);
	
}
