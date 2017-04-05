/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cola.machine.game.myblocks.world.generator.ChunkGenerators;


import cola.machine.game.myblocks.math.TeraMath;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.WorldBiomeProvider;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkConstants;
import cola.machine.game.myblocks.world.generator.FirstPassGenerator;

import javax.vecmath.Vector2f;
import java.util.Map;

/**
 * Terasology's legacy map generator. Still rocks!
 *
 * @author Benjamin Glatzel <benjamin.glatzeo@me.com>
 */
public class SimpleGenerator implements FirstPassGenerator {
    private static final int SAMPLE_RATE_3D_HOR = 4;
    private static final int SAMPLE_RATE_3D_VERT = 4;

    private Noise3D pGen1;
    private Noise3D pGen2;
    private Noise3D pGen3;
    private Noise3D pGen4;
    private Noise3D pGen5;
    private Noise3D pGen8;
    private WorldBiomeProvider biomeProvider;

    private Block mantle;
    private Block water;
    private Block ice;
    private Block stone;
    private Block sand;
    private Block grass;
    private Block snow;
    private Block dirt;

    public SimpleGenerator() {
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        if(blockManager!=null ) {
            mantle = blockManager.getBlock("mantle");
            water = blockManager.getBlock("water");
            stone = blockManager.getBlock("stone");
            sand = blockManager.getBlock("sand");
            grass = blockManager.getBlock("soil");
            snow = blockManager.getBlock("soil");
            dirt = blockManager.getBlock("soil");
            ice = blockManager.getBlock("soil");
        }

    }

    @Override
    public void setWorldSeed(String seed) {

    }

    @Override
    public Map<String, String> getInitParameters() {
        return null;
    }

    @Override
    public void setInitParameters(Map<String, String> initParameters) {

    }

    @Override
    public void setWorldBiomeProvider(WorldBiomeProvider value) {
        this.biomeProvider = value;
    }

    @Override
    public void generateChunk(Chunk chunk) {


        for (int x = 0; x < chunk.getChunkSizeX(); x++) {
            for (int z = 0; z < chunk.getChunkSizeZ(); z++) {



                     // The very deepest layer of the world is an indestructible mantle
                        chunk.setBlock(x, 0, z, mantle);



            }
        }
    }



}
