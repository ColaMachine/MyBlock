package cola.machine.game.myblocks.world.generator.ChunkGenerators;

import cola.machine.game.myblocks.world.generator.AbstractBaseWorldGenerator;



public class HeightMapWorldGenerator  extends AbstractBaseWorldGenerator{


  /*  public HeightMapWorldGenerator(SimpleUri uri) {
        super(uri);
    }*/

    @Override
    public void initialize() {
        register(new BasicHMTerrainGenerator());
       /* register(new FloraGenerator());
        register(new LiquidsGenerator());
        register(new OreGenerator());
        ForestGenerator forestGenerator = new ForestGenerator();
        DefaultGenerators.addDefaultForestGenerators(forestGenerator);
        register(forestGenerator);*/
    }

}
