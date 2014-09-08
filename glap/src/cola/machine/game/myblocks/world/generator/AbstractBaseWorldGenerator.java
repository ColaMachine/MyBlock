package cola.machine.game.myblocks.world.generator;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.BasicHMTerrainGenerator;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;


public abstract class AbstractBaseWorldGenerator  implements WorldGenerator{
    private static final Logger logger = LoggerFactory.getLogger(AbstractBaseWorldGenerator.class);

    private String worldSeed;
    private final List<FirstPassGenerator> firstPassGenerators = Lists.newArrayList();
   // private final List<SecondPassGenerator> secondPassGenerators = Lists.newArrayList();
   // private final SimpleUri uri;

    /*public AbstractBaseWorldGenerator(SimpleUri uri) {
        this.uri = uri;
    }*/

    public abstract void initialize();

   /* public final SimpleUri getUri() {
        return uri;
    }*/

    public void setWorldSeed(final String seed) {
        worldSeed = seed;
       // biomeProvider = new WorldBiomeProviderImpl(seed);
        for (final BaseChunkGenerator generator : firstPassGenerators) {
            setBiome(generator);
            generator.setWorldSeed(seed);
        }
       /* for (final BaseChunkGenerator generator : secondPassGenerators) {
            setBiome(generator);
            generator.setWorldSeed(seed);
        }*/
    }

    protected final void register(final FirstPassGenerator generator) {
        registerBaseChunkGenerator(generator);
        firstPassGenerators.add(generator);
    }

 /*   protected final void register(final SecondPassGenerator generator) {
        registerBaseChunkGenerator(generator);
      //  secondPassGenerators.add(generator);
    }*/

    private void registerBaseChunkGenerator(final BaseChunkGenerator generator) {
        setBiome(generator);
        generator.setWorldSeed(worldSeed);
    }

    private void setBiome(BaseChunkGenerator generator) {
       // generator.setWorldBiomeProvider(biomeProvider);
    }

    @Override
    public void createChunk(final Chunk chunk) {
    	BasicHMTerrainGenerator generator = new BasicHMTerrainGenerator();
    	generator.setWorldSeed("123123");
    	 generator.generateChunk(chunk);
       /* for (final FirstPassGenerator generator : firstPassGenerators) {
            generator.generateChunk(chunk);
        }*/
    }

 /*   @Override
    public void applySecondPass(final Vector3i chunkPos, final ChunkView view) {
        for (final SecondPassGenerator generator : secondPassGenerators) {
            generator.postProcessChunk(chunkPos, view);
        }
    }

    @Override
    public float getFog(float x, float y, float z) {
        return biomeProvider.getFogAt(x, y, z);
    }

    @Override
    public float getTemperature(float x, float y, float z) {
        return biomeProvider.getTemperatureAt((int) x, (int) z);
    }

    @Override
    public float getHumidity(float x, float y, float z) {
        return biomeProvider.getHumidityAt((int) x, (int) z);
    }

    @Override
    public Color get(String layerName, int x, int z) {
        switch (layerName) {
            case "Biome":
                WorldBiomeProvider.Biome biome = biomeProvider.getBiomeAt(x, z);
                switch (biome) {
                    case DESERT:
                        return Color.YELLOW;
                    case FOREST:
                        return Color.GREEN;
                    case MOUNTAINS:
                        return new Color(240, 120, 120);
                    case PLAINS:
                        return new Color(220, 220, 60);
                    case SNOW:
                        return Color.WHITE;
                    default:
                        return Color.GREY;
                }
            case "Humidity":
                float hum = biomeProvider.getHumidityAt(x, z);
                return new Color(hum * 0.2f, hum * 0.2f, hum);
            case "Temperature":
                float temp = biomeProvider.getTemperatureAt(x, z);
                return new Color(temp, temp * 0.2f, temp * 0.2f);
            default:
                return new Color();
        }
    }*/

   /* public Iterable<String> getLayers() {
        return Arrays.asList("Biome", "Humidity", "Temperature");
    }

    @Override
    public Optional<WorldConfigurator> getConfigurator() {
        return Optional.absent();
    }*/
}
