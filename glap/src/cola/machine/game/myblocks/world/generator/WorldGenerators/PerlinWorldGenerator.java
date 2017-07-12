package cola.machine.game.myblocks.world.generator.WorldGenerators;

import java.util.Map;


import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.generator.AbstractBaseWorldGenerator;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.PerlinTerrainGenerator;

import cola.machine.game.myblocks.world.generator.ChunkGenerators.SimpleGenerator;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;

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
