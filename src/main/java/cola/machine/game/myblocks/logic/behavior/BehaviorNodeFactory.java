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
package cola.machine.game.myblocks.logic.behavior;

/**
 * Factory to create node instances from node entities.
 *
 * @author synopia
 */
public class BehaviorNodeFactory {
//        extends BaseComponentSystem {
//    private static final Logger logger = LoggerFactory.getLogger(BehaviorNodeFactory.class);
//
//    private Map<ClassMetadata<? extends Node, ?>, BehaviorNodeComponent> nodes = Maps.newHashMap();
//    private Map<String, List<BehaviorNodeComponent>> categoryComponents = Maps.newHashMap();
//    private List<String> categories;
//
//    @In
//    private ModuleManager moduleManager;
//    @In
//    private EntityManager entityManager;
//    @In
//    private PrefabManager prefabManager;
//    @In
//    private AssetManager assetManager;
//    @In
//    private NodesClassLibrary nodesClassLibrary;
//    @In
//    private OneOfProviderFactory providerFactory;
//
//    private List<AssetUri> sounds = Lists.newArrayList();
//    private List<AssetUri> music = Lists.newArrayList();
//
//    public BehaviorNodeFactory() {
//        CoreRegistry.put(BehaviorNodeFactory.class, this);
//    }
//
//    @Override
//    public void postBegin() {
//        refreshLibrary();
//    }
//
//    public void refreshLibrary() {
//        for (AssetUri uri : assetManager.listAssets(AssetType.SOUND)) {
//            sounds.add(uri);
//        }
//        for (AssetUri uri : assetManager.listAssets(AssetType.MUSIC)) {
//            music.add(uri);
//        }
//        providerFactory.register("sounds", new ReadOnlyBinding<List<AssetUri>>() {
//                    @Override
//                    public List<AssetUri> get() {
//                        return sounds;
//                    }
//                }, new StringTextRenderer<AssetUri>() {
//                    @Override
//                    public String getString(AssetUri value) {
//                        return value.getAssetName().toString();
//                    }
//                }
//        );
//        providerFactory.register("music", new ReadOnlyBinding<List<AssetUri>>() {
//                    @Override
//                    public List<AssetUri> get() {
//                        return music;
//                    }
//                }, new StringTextRenderer<AssetUri>() {
//                    @Override
//                    public String getString(AssetUri value) {
//                        return value.getAssetName().toString();
//                    }
//                }
//        );
//        refreshPrefabs();
//        sortLibrary();
//    }
//
//    private void sortLibrary() {
//        categories = Lists.newArrayList(categoryComponents.keySet());
//        Collections.sort(categories);
//        for (String category : categories) {
//            Collections.sort(categoryComponents.get(category), new Comparator<BehaviorNodeComponent>() {
//                @Override
//                public int compare(BehaviorNodeComponent o1, BehaviorNodeComponent o2) {
//                    return o1.name.compareTo(o2.name);
//                }
//            });
//        }
//    }
//
//    private void refreshPrefabs() {
//        Collection<Prefab> prefabs = prefabManager.listPrefabs(BehaviorNodeComponent.class);
//        for (Prefab prefab : prefabs) {
//            EntityRef entityRef = entityManager.create(prefab);
//            entityRef.setPersistent(false);
//            BehaviorNodeComponent component = entityRef.getComponent(BehaviorNodeComponent.class);
//            ClassMetadata<? extends Node, ?> classMetadata = nodesClassLibrary.resolve(component.type);
//            if (classMetadata != null) {
//                if (classMetadata.isConstructable()) {
//                    nodes.put(classMetadata, component);
//                    logger.debug("Found behavior node for class " + component.type + " name=" + component.name);
//                    List<BehaviorNodeComponent> list = categoryComponents.get(component.category);
//                    if (list == null) {
//                        list = Lists.newArrayList();
//                        categoryComponents.put(component.category, list);
//                    }
//                    list.add(component);
//                } else {
//                    logger.warn("Node cannot be constructed! -> ignoring " + component.type + " name=" + component.name);
//                }
//            } else {
//                logger.warn("Node not found -> ignoring! " + component.type + " name=" + component.name);
//            }
//        }
//    }
//
//    public BehaviorNodeComponent getNodeComponent(Node node) {
//        ClassMetadata<? extends Node, ?> metadata = nodesClassLibrary.getMetadata(node.getClass());
//        if (metadata != null) {
//            BehaviorNodeComponent nodeComponent = nodes.get(metadata);
//            if (nodeComponent == null) {
//                return BehaviorNodeComponent.DEFAULT;
//            }
//            return nodeComponent;
//        } else {
//            return null;
//        }
//    }
//
//    public Node getNode(BehaviorNodeComponent nodeComponent) {
//        for (Map.Entry<ClassMetadata<? extends Node, ?>, BehaviorNodeComponent> entry : nodes.entrySet()) {
//            if (nodeComponent == entry.getValue()) {
//                return entry.getKey().newInstance();
//            }
//        }
//        return null;
//    }
//
//    public Collection<BehaviorNodeComponent> getNodeComponents() {
//        return nodes.values();
//    }
//
//    public List<String> getCategories() {
//        return categories;
//    }
//
//    public List<BehaviorNodeComponent> getNodesComponents(String category) {
//        return categoryComponents.get(category);
//    }
}
