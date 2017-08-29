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
package cola.machine.game.myblocks.entitySystem.prefab.internal;

//import org.terasology.module.Module;

/**
 * @author Immortius
 */
public class PrefabLoader{
//        implements AssetLoader<PrefabData> {
//
//    @Override
//    public PrefabData load(Module module, InputStream stream, List<URL> urls, List<URL> deltas) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charsets.UTF_8));
//        EntityData.Prefab prefabData = EntityDataJSONFormat.readPrefab(reader);//从文件中读取prefab数据
//        if (prefabData != null) {
//            EngineEntityManager entityManager = CoreRegistry.get(EngineEntityManager.class);
//            List<EntityData.Prefab> deltaData = Lists.newArrayListWithCapacity(deltas.size());
//            for (URL deltaUrl : deltas) {//再从delta里读取对应的数据
//                try (BufferedReader deltaReader = new BufferedReader(new InputStreamReader(deltaUrl.openStream(), Charsets.UTF_8))) {
//                    EntityData.Prefab delta = EntityDataJSONFormat.readPrefab(deltaReader);
//                    deltaData.add(delta);
//                }
//            }
//            PrefabSerializer serializer = new PrefabSerializer(entityManager.getComponentLibrary(), entityManager.getTypeSerializerLibrary());
//            return serializer.deserialize(prefabData, deltaData);
//        }
//        return null;
//    }
}
