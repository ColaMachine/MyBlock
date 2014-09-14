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

package cola.machine.game.myblocks.world.generator;

import cola.machine.game.myblocks.world.WorldBiomeProvider;
import cola.machine.game.myblocks.world.WorldBiomeProvider.Biome;
import cola.machine.game.myblocks.world.chunks.TeraMath;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.BrownianNoise3D;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.Noise3D;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.PerlinNoise;


/**
 * @author Immortius
 */
// TODO: Evolve this class into a world description provider (density, height, etc) to feed into the world generators
public class WorldBiomeProviderImpl implements WorldBiomeProvider {

    private final Noise3D temperatureNoise;
    private final Noise3D humidityNoise;

    public WorldBiomeProviderImpl(String worldSeed) {
        temperatureNoise = new BrownianNoise3D(new PerlinNoise(worldSeed.hashCode() + 5));//ʪ�� ��һ��ʼsetworldseed��ʱ�� hashCode�ǰ����ӱ��һ������ perlinNoise�ǰ�������ɿ�������� Ȼ�� ����һ��0~255��256���ȵ����� Ȼ�����Щֵҡ�� ��ֵһ�� ƴ����һ���γ�512�������� BrownianNoise3Dֻ�ǰ�perlin�ŵ���������
        humidityNoise = new BrownianNoise3D(new PerlinNoise(worldSeed.hashCode() + 6));//�¶� 
    }

    @Override
    public float getHumidityAt(int x, int z) {
        double result = humidityNoise.noise(x * 0.0005, 0, 0.0005 * z);
        return (float) TeraMath.clamp((result + 1.0f) / 2.0f);
    }

    @Override
    public float getTemperatureAt(int x, int z) {
        double result = temperatureNoise.noise(x * 0.0005, 0, 0.0005 * z);
        return (float) TeraMath.clamp((result + 1.0f) / 2.0f);
    }

    @Override
    public float getFogAt(float x, float y, float z) {
        return getBiomeAt(x, z).getFog();
    }

    @Override
    public WorldBiomeProvider.Biome getBiomeAt(int x, int z) {
        double temp = getTemperatureAt(x, z);
        double humidity = getHumidityAt(x, z) * temp;

        if (temp >= 0.5 && humidity < 0.3) {
            return Biome.DESERT;
        } else if (humidity >= 0.3 && humidity <= 0.6 && temp >= 0.5) {
            return Biome.PLAINS;
        } else if (temp <= 0.3 && humidity > 0.5) {
            return Biome.SNOW;
        } else if (humidity >= 0.2 && humidity <= 0.6 && temp < 0.5) {
            return Biome.MOUNTAINS;
        }

        return Biome.FOREST;
    }

    @Override
    public Biome getBiomeAt(float x, float z) {
        return getBiomeAt(TeraMath.floorToInt(x + 0.5f), TeraMath.floorToInt(z + 0.5f));
    }
}
