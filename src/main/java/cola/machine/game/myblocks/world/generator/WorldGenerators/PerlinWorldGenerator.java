package cola.machine.game.myblocks.world.generator.WorldGenerators;

import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.generator.AbstractBaseWorldGenerator;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.PerlinTerrainGenerator;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.SimpleGenerator;

public class PerlinWorldGenerator extends AbstractBaseWorldGenerator {


    

    @Override
    public void initialize() {
        if(Switcher.test){
            register (new SimpleGenerator());//
        }else {
            register(new PerlinTerrainGenerator());
        }
       
    }

   

}
