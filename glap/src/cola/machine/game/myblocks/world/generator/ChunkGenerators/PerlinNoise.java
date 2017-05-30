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



/**
 * Improved Perlin noise based on the reference implementation by Ken Perlin.
 *http://www.cnblogs.com/Memo/archive/2008/09/08/1286963.html
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class PerlinNoise implements Noise3D {

    private final int[] noisePermutations;

    /**
     * Init. a new generator with a given seed value.
     *
     * @param seed The seed value
     */
    public PerlinNoise(int seed) {//来讲一下 PerlinNoise的构造函数生成一个快随机值
        FastRandom rand = new FastRandom(seed);//

        noisePermutations = new int[512];//噪声排列  感觉类似声波图 上下都很尖 密密麻麻一个二维图像
        int[] noiseTable = new int[256];//创建一个噪声表 256

        // Init. the noise table
        for (int i = 0; i < 256; i++) {
            noiseTable[i] = i;//先是按照0~256 赋值
        }

        // Shuffle the array
        for (int i = 0; i < 256; i++) {
            int j = rand.nextInt(256);//利用快随机数生成随机数
            //对调

            int swap = noiseTable[i];
            noiseTable[i] = noiseTable[j];
            noiseTable[j] = swap;
        }

        // Finally replicate the noise permutations in the remaining 256 index positions
        for (int i = 0; i < 256; i++) {
            noisePermutations[i] = noiseTable[i];
            noisePermutations[i + 256] = noiseTable[i];//生成两份数据 512 //噪声序列
        }

    }

    /**
     * Returns the noise value at the given position.
     *
     * @param posX Position on the x-axis
     * @param posY Position on the y-axis
     * @param posZ Position on the z-axis
     * @return The noise value
     */
    @Override
    public double noise(double posX, double posY, double posZ) {
        int xInt = (int) TeraMath.fastFloor(posX) & 255;//取后面8位
        int yInt = (int) TeraMath.fastFloor(posY) & 255;
        int zInt = (int) TeraMath.fastFloor(posZ) & 255;

        double x = posX - TeraMath.fastFloor(posX);//取小数点后的数
        double y = posY - TeraMath.fastFloor(posY);
        double z = posZ - TeraMath.fastFloor(posZ);

        double u = fade(x);//一般是很小很小的负数
        double v = fade(y);
        double w = fade(z);
        int a = noisePermutations[xInt] + yInt;//xInt +yInt
        int aa = noisePermutations[a] + zInt;
        int ab = noisePermutations[(a + 1)] + zInt;
        int b = noisePermutations[(xInt + 1)] + yInt;
        int ba = noisePermutations[b] + zInt;
        int bb = noisePermutations[(b + 1)] + zInt;

        return lerp(w, lerp(v, lerp(u, grad(noisePermutations[aa], x, y, z),
                grad(noisePermutations[ba], x - 1, y, z)),
                lerp(u, grad(noisePermutations[ab], x, y - 1, z),
                        grad(noisePermutations[bb], x - 1, y - 1, z))),
                lerp(v, lerp(u, grad(noisePermutations[(aa + 1)], x, y, z - 1),
                        grad(noisePermutations[(ba + 1)], x - 1, y, z - 1)),
                        lerp(u, grad(noisePermutations[(ab + 1)], x, y - 1, z - 1),
                                grad(noisePermutations[(bb + 1)], x - 1, y - 1, z - 1))));
    }

    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private static double grad(int hash, double x, double y, double z) {
        int h = hash & 15;//小于15 00001111
        double u = h < 8 ? x : y;//0111 
        double v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

}
