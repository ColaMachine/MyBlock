package cola.machine.game.myblocks.world.generator.WorldGenerators;

import java.util.Map;




import cola.machine.game.myblocks.world.generator.AbstractBaseWorldGenerator;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.PerlinTerrainGenerator;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

public class PerlinWorldGenerator extends AbstractBaseWorldGenerator {


    

    @Override
    public void initialize() {
        register(new PerlinTerrainGenerator());
       
    }

   

}
