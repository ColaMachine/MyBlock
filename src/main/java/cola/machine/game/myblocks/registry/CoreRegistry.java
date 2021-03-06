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
package cola.machine.game.myblocks.registry;

import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.LocalChunkProvider;
import cola.machine.game.myblocks.world.chunks.RemoteChunkProvider;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public final class CoreRegistry {
    private static Map<Class<? extends Object>, Object> store = Maps.newConcurrentMap();
    private static Set<Class<? extends Object>> permStore = Sets.newSetFromMap(Maps.<Class<? extends Object>, Boolean>newConcurrentMap());

    private CoreRegistry() {
    }

    /**
     * Registers an object. These objects will be removed when CoreRegistry.clear() is called (typically when game state changes)
     *
     * @param type   The interface which the system fulfils
     * @param object The system itself
     * @param <T>
     */
    public static <T, U extends T> U put(Class<T> type, U object) {
        store.put(type, object);
        return object;
    }

    /**
     * Registers an object. These objects are not removed when CoreRegistry.clear() is called.
     *
     * Requires the "permRegister" RuntimePermission
     *
     * @param type   The interface which the system fulfils
     * @param object The system itself
     * @param <T>
     */
    public static <T, U extends T> U putPermanently(Class<T> type, U object) {
        if (System.getSecurityManager() != null) {
            System.getSecurityManager().checkPermission(new RuntimePermission("permRegister"));
        }
        store.put(type, object);
        permStore.add(type);
        return object;
    }

    /**
     *
     * @param type
     * @param <T>
     * @return The system fulfilling the given interface
     */
    public static <T> T get(Class<T> type) {
        return type.cast(store.get(type));
    }

    /**
     * Clears all non-permanent objects from the registry.
     *
     * Requires the "clearRegistry" RuntimePermission
     */
    public static void clear() {
        if (System.getSecurityManager() != null) {
            System.getSecurityManager().checkPermission(new RuntimePermission("clearRegistry"));
        }
        Iterator<Map.Entry<Class<?>, Object>> iterator = store.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Class<?>, Object> entry = iterator.next();
            if (!permStore.contains(entry.getKey())) {
                iterator.remove();
            }
        }
    }

    public static <T> void remove(Class<T> type) {
        store.remove(type);
    }

    public static void main(String args[]){
        CoreRegistry.put(ChunkProvider.class ,new LocalChunkProvider());

        CoreRegistry.put(RemoteChunkProvider.class,new RemoteChunkProvider());
        ChunkProvider chunkProvider = CoreRegistry.get(ChunkProvider.class);
        System.out.println(chunkProvider);
    }

}
