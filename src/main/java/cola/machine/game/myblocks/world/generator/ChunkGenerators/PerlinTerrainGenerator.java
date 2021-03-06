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
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.WorldBiomeProvider;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkConstants;
import cola.machine.game.myblocks.world.generator.FirstPassGenerator;
import com.dozenx.game.engine.command.ItemType;

import javax.vecmath.Vector2f;
import java.util.Map;

/**
 * Terasology's legacy map generator. Still rocks!
 *
 * @author Benjamin Glatzel <benjamin.glatzeo@me.com>
 */
public class PerlinTerrainGenerator implements FirstPassGenerator {
    private static final int SAMPLE_RATE_3D_HOR = 4;
    private static final int SAMPLE_RATE_3D_VERT = 4;

    private Noise3D pGen1;//这货内置8个生成器 生成噪声 用来服务不同的地形
    private Noise3D pGen2;
    private Noise3D pGen3;
    private Noise3D pGen4;
    private Noise3D pGen5;
    private Noise3D pGen8;
    private WorldBiomeProvider biomeProvider;//生物提供者 暂时不理会

    /*private Block mantle;
    private Block water;
    private Block ice;
    private Block stone;
    private Block sand;
    private Block grass;
    private Block snow;
    private Block dirt;*/

 /*   private ItemBlockType mantle = ItemBlockType.MANTLE;
    private ItemBlockType water =ItemBlockType.WATER;
    //private ItemBlockType ice = ;
    private ItemBlockType stone = ItemBlockType.STONE;
    private ItemBlockType sand = ItemBlockType.SAND;
    private ItemBlockType grass= ItemBlockType.SAND;
    private ItemBlockType snow= ItemBlockType.SAND;
    private ItemBlockType dirt= ItemBlockType.SAND;
    */

//    private ItemType mantle = ItemType.mantle;
//    private ItemType water =ItemType.water;
//    //private ItemBlockType ice = ;
//    private ItemType stone = ItemType.stone;
//    private ItemType sand = ItemType.sand;
//    private ItemType grass= ItemType.soil;
//    private ItemType snow= ItemType.stone;
//    private ItemType dirt= ItemType.stone;




    public PerlinTerrainGenerator() {//开始的时候创建构造函数没有什么内容
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        if(blockManager!=null) {
           /* mantle = blockManager.getBlock("mantle");
            water = blockManager.getBlock("water");
            stone = blockManager.getBlock("stone");
            sand = blockManager.getBlock("sand");
            grass = blockManager.getBlock("soil");
            snow = blockManager.getBlock("soil");
            dirt = blockManager.getBlock("soil");
            ice = blockManager.getBlock("soil");*/
        }
       
    }

    @Override
    public void setWorldSeed(String seed) {
        if (seed != null) {
            pGen1 = new BrownianNoise3D(new PerlinNoise(seed.hashCode()), 8);

            pGen2 = new BrownianNoise3D(new PerlinNoise(seed.hashCode() + 1), 8);

            pGen3 = new BrownianNoise3D(new PerlinNoise(seed.hashCode() + 2), 8);

            pGen4 = new BrownianNoise3D(new PerlinNoise(seed.hashCode() + 3));
            pGen5 = new BrownianNoise3D(new PerlinNoise(seed.hashCode() + 4));
            pGen8 = new BrownianNoise3D(new PerlinNoise(seed.hashCode() + 7));
        }
    }

    @Override
    public void setWorldBiomeProvider(WorldBiomeProvider value) {
        this.biomeProvider = value;
    }

    @Override
    public void generateChunk(Chunk chunk) {
        double[][][] densityMap = new double[chunk.getChunkSizeX() + 1][chunk.getChunkSizeY() + 1][chunk.getChunkSizeZ() + 1];

        /*
         * Create the density map at a lower sample rate.
         */
        for (int x = 0; x <= chunk.getChunkSizeX(); x += SAMPLE_RATE_3D_HOR) {
            for (int z = 0; z <= chunk.getChunkSizeZ(); z += SAMPLE_RATE_3D_HOR) {
                for (int y = 0; y <= chunk.getChunkSizeY(); y += SAMPLE_RATE_3D_VERT) {
                    densityMap[x][y][z] = calcDensity(chunk.getBlockWorldPosX(x), y, chunk.getBlockWorldPosZ(z));
                }
            }
        }

        /*
         * Trilinear interpolate the missing values.
         */
        triLerpDensityMap(densityMap);//插值

        /*
         * Generate the chunk from the density map.
         */

        for (int x = 0; x < chunk.getChunkSizeX(); x++) {
            for (int z = 0; z < chunk.getChunkSizeZ(); z++) {
                WorldBiomeProvider.Biome type = biomeProvider.getBiomeAt(chunk.getBlockWorldPosX(x), chunk.getBlockWorldPosZ(z));
                int firstBlockHeight = -1;

                for (int y = chunk.getChunkSizeY() - 1; y >= 0; y--) {//

                    if (y == 0) { // The very deepest layer of the world is an indestructible mantle
                        chunk.setBlock(x, y, z, ItemType.mantle.id);
                        break;
                    }
                    //if(1==1)
                   // continue;
                    if (y <= 32 && y > 0) { // Ocean
                        chunk.setBlock(x, y, z, ItemType. water.id);
                       // chunk.setLiquid(x, y, z, new LiquidData(LiquidType.WATER, LiquidData.MAX_LIQUID_DEPTH));

                        if (y == 32) {
                            // Ice layer
                           /* if (type == WorldBiomeProvider.Biome.SNOW) {
                                chunk.setBlock(x, y, z, ice);
                            }*/
                        }
                    }

                    double dens = densityMap[x][y][z];

                    if ((dens >= 0 && dens < 32)) {

                        // Some block was set...
                        if (firstBlockHeight == -1) {
                            firstBlockHeight = y;
                        }

                        if (calcCaveDensity(chunk.getBlockWorldPosX(x), y, chunk.getBlockWorldPosZ(z)) > -0.7) {
                            generateOuterLayer(x, y, z, firstBlockHeight, chunk, type);
                        } else {
                           // chunk.setBlock(x, y, z, air);
                        }

                        continue;
                    } else if (dens >= 32) {

                        // Some block was set...
                        if (firstBlockHeight == -1) {
                            firstBlockHeight = y;
                        }

                        if (calcCaveDensity(chunk.getBlockWorldPosX(x), y, chunk.getBlockWorldPosZ(z)) > -0.6) {
                           // generateInnerLayer(x, y, z, chunk, type);
                        } else {
                            //chunk.setBlock(x, y, z, air);
                        }

                        continue;
                    }

                    // Nothing was set!
                    firstBlockHeight = -1;
                }
            }
        }
    }

    private void generateInnerLayer(int x, int y, int z, Chunk c, WorldBiomeProvider.Biome type) {
        // TODO: GENERATE MINERALS HERE - config waiting at org\terasology\logic\manager\DefaultConfig.groovy 2012/01/22
        c.setBlock(x, y, z, ItemType. stone.id);
    }

    private void generateOuterLayer(int x, int y, int z, int firstBlockHeight, Chunk c, WorldBiomeProvider.Biome type) {

        int depth = (firstBlockHeight - y);

        switch (type) {
            case FOREST:
            case PLAINS:
            case MOUNTAINS:
                // Beach
//                if(y>30){
//                    return;
//                }
                if (y >= 28 && y <= 34) {
                    c.setBlock(x, y, z,  ItemType.sand.id);
                } else if (depth == 0 && y > 32 && y < 128) {
                    // Grass on top
                    c.setBlock(x, y, z, ItemType.soil.id);
                } else if (depth == 0 && y >= 128) {
                    // Grass on top
                    c.setBlock(x, y, z,  ItemType.soil.id);
                } else if (depth > 32) {
                    // Stone
                    c.setBlock(x, y, z,  ItemType.stone.id);
                } else {
                    // Dirt
                    c.setBlock(x, y, z,  ItemType.soil.id);
                }

                break;
            case SNOW:
//                if(y>30){
//                    return;
//                }
                if (depth == 0.0 && y > 32) {
                    // Snow on top
                    c.setBlock(x, y, z,  ItemType.soil.id);
                } else if (depth > 32) {
                    // Stone
                    c.setBlock(x, y, z,  ItemType.stone.id);
                } else {
                    // Dirt
                    c.setBlock(x, y, z,  ItemType.soil.id);
                }

                break;

            case DESERT:
//                if(y>30){
//                    return;
//                }
                if (depth > 8) {
                    // Stone
                    c.setBlock(x, y, z,  ItemType.stone.id);
                } else {
                    c.setBlock(x, y, z,  ItemType.sand.id);
                }

                break;
        }
    }

    private void triLerpDensityMap(double[][][] densityMap) {
        for (int x = 0; x < ChunkConstants.SIZE_X; x++) {
            for (int y = 0; y < ChunkConstants.SIZE_Y; y++) {
                for (int z = 0; z < ChunkConstants.SIZE_Z; z++) {
                    if (!(x % SAMPLE_RATE_3D_HOR == 0 && y % SAMPLE_RATE_3D_VERT == 0 && z % SAMPLE_RATE_3D_HOR == 0)) {
                        int offsetX = (x / SAMPLE_RATE_3D_HOR) * SAMPLE_RATE_3D_HOR;
                        int offsetY = (y / SAMPLE_RATE_3D_VERT) * SAMPLE_RATE_3D_VERT;
                        int offsetZ = (z / SAMPLE_RATE_3D_HOR) * SAMPLE_RATE_3D_HOR;
                        densityMap[x][y][z] = TeraMath.triLerp(x, y, z,
                                densityMap[offsetX][offsetY][offsetZ],
                                densityMap[offsetX][SAMPLE_RATE_3D_VERT + offsetY][offsetZ],
                                densityMap[offsetX][offsetY][offsetZ + SAMPLE_RATE_3D_HOR],
                                densityMap[offsetX][offsetY + SAMPLE_RATE_3D_VERT][offsetZ + SAMPLE_RATE_3D_HOR],
                                densityMap[SAMPLE_RATE_3D_HOR + offsetX][offsetY][offsetZ],
                                densityMap[SAMPLE_RATE_3D_HOR + offsetX][offsetY + SAMPLE_RATE_3D_VERT][offsetZ],
                                densityMap[SAMPLE_RATE_3D_HOR + offsetX][offsetY][offsetZ + SAMPLE_RATE_3D_HOR],
                                densityMap[SAMPLE_RATE_3D_HOR + offsetX][offsetY + SAMPLE_RATE_3D_VERT][offsetZ + SAMPLE_RATE_3D_HOR],
                                offsetX, SAMPLE_RATE_3D_HOR + offsetX, offsetY, SAMPLE_RATE_3D_VERT + offsetY, offsetZ, offsetZ + SAMPLE_RATE_3D_HOR);
                    }
                }
            }
        }
    }

    public double calcDensity(int x, int y, int z) {
        double height = calcBaseTerrain(x, z);//计算地形高度 //计算地形
        double ocean = calcOceanTerrain(x, z);//计算水的高度 //计算海洋
        double river = calcRiverTerrain(x, z);//计算河流

        float temp =10;//biomeProvider.getTemperatureAt(x, z);
        float humidity = 10;//biomeProvider.getHumidityAt(x, z) * temp;

        Vector2f distanceToMountainBiome = new Vector2f(temp - 0.25f, humidity - 0.35f);

        double mIntens = TeraMath.clamp(1.0 - distanceToMountainBiome.length() * 3.0);
        double densityMountains = calcMountainDensity(x, y, z) * mIntens;
        double densityHills = calcHillDensity(x, y, z) * (1.0 - mIntens);

        int plateauArea = (int) (ChunkConstants.SIZE_Y * 0.10);
        double flatten = TeraMath.clamp(((ChunkConstants.SIZE_Y - 16) - y) / plateauArea);

        return -y + (((32.0 + height * 32.0) * TeraMath.clamp(river + 0.25) * TeraMath.clamp(ocean + 0.25)) + densityMountains * 1024.0 + densityHills * 128.0) * flatten;
    }

    private double calcBaseTerrain(double x, double z) {
        return TeraMath.clamp((pGen1.noise(0.004 * x, 0, 0.004 * z) + 1.0) / 2.0);
    }

    private double calcOceanTerrain(double x, double z) {
        return TeraMath.clamp(pGen2.noise(0.0009 * x, 0, 0.0009 * z) * 8.0);
    }

    private double calcRiverTerrain(double x, double z) {
        return TeraMath.clamp((java.lang.Math.sqrt(java.lang.Math.abs(pGen3.noise(0.0008 * x, 0, 0.0008 * z))) - 0.1) * 7.0);
    }

    private double calcMountainDensity(double x, double y, double z) {
        double x1 = x * 0.002;
        double y1 = y * 0.001;
        double z1 = z * 0.002;

        double result = pGen4.noise(x1, y1, z1);
        return result > 0.0 ? result : 0;
    }

    private double calcHillDensity(double x, double y, double z) {
        double x1 = x * 0.008;
        double y1 = y * 0.006;
        double z1 = z * 0.008;

        double result = pGen5.noise(x1, y1, z1) - 0.1;
        return result > 0.0 ? result : 0;
    }

    private double calcCaveDensity(double x, double y, double z) {
        return pGen8.noise(x * 0.02, y * 0.02, z * 0.02);
    }

    @Override
    public Map<String, String> getInitParameters() {
        return null;
    }

    @Override
    public void setInitParameters(final Map<String, String> initParameters) {
    }

}
