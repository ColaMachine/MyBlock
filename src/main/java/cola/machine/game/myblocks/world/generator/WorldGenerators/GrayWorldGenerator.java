package cola.machine.game.myblocks.world.generator.WorldGenerators;


import cola.machine.game.myblocks.world.generator.AbstractBaseWorldGenerator;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.GrayTerrainGenerator;

public class GrayWorldGenerator extends AbstractBaseWorldGenerator {


    

    @Override
    public void initialize() {
        register(new GrayTerrainGenerator());
       
    }

   

}
