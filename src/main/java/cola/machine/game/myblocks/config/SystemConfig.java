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

package cola.machine.game.myblocks.config;

/**
 * @author Immortius
 */
public class SystemConfig {
    private long dayNightLengthInMs = 1800000;//白天的长度
    private int maxThreads = 2;//最大线程数量
    private int verticalChunkMeshSegments = 1;//垂直chunkmesh段

    private boolean debugEnabled;//是否可调试
    private boolean monitoringEnabled;//是否可以监控
    private boolean reflectionsCacheEnabled;//反射缓存开启

    public long getDayNightLengthInMs() {
        return dayNightLengthInMs;
    }

    public void setDayNightLengthInMs(long dayNightLengthInMs) {
        this.dayNightLengthInMs = dayNightLengthInMs;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getVerticalChunkMeshSegments() {
        return verticalChunkMeshSegments;
    }

    public void setVerticalChunkMeshSegments(int verticalChunkMeshSegments) {
        this.verticalChunkMeshSegments = verticalChunkMeshSegments;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public boolean isReflectionsCacheEnabled() {
        return reflectionsCacheEnabled;
    }

    public void setReflectionsCacheEnabled(boolean reflectionsCacheEnabled) {
        this.reflectionsCacheEnabled = reflectionsCacheEnabled;
    }
    
    public boolean isMonitoringEnabled() {
        return monitoringEnabled;
    }

    public void setMonitoringEnabled(boolean monitoringEnabled) {
        this.monitoringEnabled = monitoringEnabled;
    }
}
