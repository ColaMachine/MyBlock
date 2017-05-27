/*
 * Copyright 2014 MovingBlocks
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

/**
 * Computes Brownian noise based on some noise generator.
 * Originally, Brown integrates white noise, but using other noises can be sometimes useful, too.
 * @author Martin Steiger
 */
public abstract class BrownianNoise {

    /**
     * Default persistence value
     */
    public static final double DEFAULT_PERSISTENCE = 0.836281;//这个值如果过小就会比较离散

    /**
     * Default lacunarity value
     */
    public static final double DEFAULT_LACUNARITY = 2.1379201;//这个值太大 就没有方块了 太小也没有方块

    private double lacunarity = DEFAULT_LACUNARITY;
    
    private double persistence = DEFAULT_PERSISTENCE;

    private int octaves;
    private double[] spectralWeights;
    
    /**
     * Initialize with 9 octaves - <b>this is insanely expensive, but backwards compatible</b>
     */
    protected BrownianNoise() {
        setOctaves(9);
    }

    /**
     * Values of noise() are in the range [-scale..scale]
     * @return the scale
     */
    public double getScale() {
        double sum = 0;
        for (double weight : spectralWeights) {
            sum += weight;
        }
        return sum;
    }

    /**
     * @param octaves the number of octaves used for computation
     */
    public void setOctaves(int octaves) {
        this.octaves = octaves;
        
        // recompute weights eagerly
        spectralWeights = new double[octaves];

        for (int i = 0; i < octaves; i++) {
            spectralWeights[i] = Math.pow(lacunarity, -persistence * i);
        }
   }
   /* 1.0
            0.5297054034719835
            0.2805878144674168
            0.14862888147178507
            0.07872952162760152
            0.04170345301890493
            0.022090544407553935
            0.011701480738319123*/
    public static void  main(String args[]){
        for (int i = 0; i < 8; i++) {
        System.out.println( Math.pow(BrownianNoise.DEFAULT_LACUNARITY, BrownianNoise.DEFAULT_PERSISTENCE *- i));
        }
    }
    /**
     * @return the number of octaves
     */
    public int getOctaves() {
        return octaves;
    }

    /**
     * Lacunarity is what makes the frequency grow. Each octave 
     * the frequency is multiplied by the lacunarity.
     * @return the lacunarity
     */
    public double getLacunarity() {
        return this.lacunarity;
    }

    /**
     * Lacunarity is what makes the frequency grow. Each octave 
     * the frequency is multiplied by the lacunarity.
     * @param lacunarity the lacunarity
     */
    public void setLacunarity(double lacunarity) {
        this.lacunarity = lacunarity;
    }

    /**
     * Persistence is what makes the amplitude shrink.
     * More precicely the amplitude of octave i = lacunarity^(-persistence * i) 
     * @return the persistance
     */
    public double getPersistance() {
        return this.persistence;
    }

    /**
     * Persistence is what makes the amplitude shrink.
     * More precisely the amplitude of octave i = lacunarity^(-persistence * i) 
     * @param persistence the persistence to set
     */
    public void setPersistence(double persistence) {
        this.persistence = persistence;
    }

    /**
     * @return the spectralWeights
     */
    protected double getSpectralWeight(int octave) {
        return spectralWeights[octave];
    }
    
}
