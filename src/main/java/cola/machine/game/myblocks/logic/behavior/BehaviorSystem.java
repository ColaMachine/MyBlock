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
 * Behavior tree system
 * <p/>
 * Each entity with BehaviorComponent is kept under control by this system. For each such entity a behavior tree
 * is loaded and an interpreter is started.
 * <p/>
 * Modifications made to a behavior tree will reflect to all entities using this tree.
 *
 * @author synopia
 */
public class BehaviorSystem {//extends BaseComponentSystem implements UpdateSubscriberSystem {
//    public static final Name BEHAVIORS = new Name("Behaviors");
//
//    @In
//    private EntityManager entityManager;
//    @In
//    private PrefabManager prefabManager;
//    @In
//    private AssetManager assetManager;
//
//    private Map<EntityRef, Interpreter> entityInterpreters = Maps.newHashMap();
//    private List<BehaviorTree> trees = Lists.newArrayList();
//
//    @Override
//    public void initialise() {
//        List<AssetUri> uris = Lists.newArrayList();
//        for (AssetUri uri : assetManager.listAssets(AssetType.SOUND)) {
//            uris.add(uri);
//        }
//        for (AssetUri uri : assetManager.listAssets(AssetType.BEHAVIOR)) {
//
//            BehaviorTree asset = assetManager.loadAsset(uri, BehaviorTree.class);
//            if (asset != null) {
//                trees.add(asset);
//            }
//        }
//    }
//
//    @ReceiveEvent
//    public void onBehaviorAdded(OnAddedComponent event, EntityRef entityRef, BehaviorComponent behaviorComponent) {
//        addEntity(entityRef, behaviorComponent);
//    }
//
//    @ReceiveEvent
//    public void onBehaviorActivated(OnActivatedComponent event, EntityRef entityRef, BehaviorComponent behaviorComponent) {
//        addEntity(entityRef, behaviorComponent);
//    }
//
//    @ReceiveEvent
//    public void onBehaviorRemoved(BeforeRemoveComponent event, EntityRef entityRef, BehaviorComponent behaviorComponent) {
//        if (behaviorComponent.tree != null) {
//            entityInterpreters.remove(entityRef);
//        }
//    }
//
//    @Override
//    public void update(float delta) {
//        for (Interpreter interpreter : entityInterpreters.values()) {
//            interpreter.tick(delta);
//        }
//    }
//
//    public BehaviorTree createTree(String name, Node root) {
//        BehaviorTreeData data = new BehaviorTreeData();
//        data.setRoot(root);
//        BehaviorTree behaviorTree = new BehaviorTree(new AssetUri(AssetType.BEHAVIOR, BEHAVIORS, name.replaceAll("\\W+", "")), data);
//        trees.add(behaviorTree);
//        save(behaviorTree);
//        return behaviorTree;
//    }
//
//    public void save(BehaviorTree tree) {
//        Path savePath;
//        AssetUri uri = tree.getURI();
//        if (BEHAVIORS.equals(uri.getModuleName())) {
//            savePath = PathManager.getInstance().getHomeModPath().resolve(BEHAVIORS.toString()).resolve("assets").resolve("behaviors");
//        } else {
//            Path overridesPath = PathManager.getInstance().getHomeModPath().resolve(BEHAVIORS.toString()).resolve("overrides");
//            savePath = overridesPath.resolve(uri.getModuleName().toString()).resolve("behaviors");
//        }
//        BehaviorTreeLoader loader = new BehaviorTreeLoader();
//        try {
//            Files.createDirectories(savePath);
//            Path file = savePath.resolve(uri.getAssetName() + ".behavior");
//            try (FileOutputStream fos = new FileOutputStream(file.toFile())) {
//                loader.save(fos, tree.getData());
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Cannot save asset " + uri + " to " + savePath, e);
//        }
//    }
//
//    public List<BehaviorTree> getTrees() {
//        return trees;
//    }
//
//    public List<Interpreter> getInterpreter() {
//        List<Interpreter> interpreters = Lists.newArrayList();
//        interpreters.addAll(entityInterpreters.values());
//        Collections.sort(interpreters, new Comparator<Interpreter>() {
//            @Override
//            public int compare(Interpreter o1, Interpreter o2) {
//                return o1.toString().compareTo(o2.toString());
//            }
//        });
//        return interpreters;
//    }
//
//    public void treeModified(BehaviorTree tree) {
//        for (Interpreter interpreter : entityInterpreters.values()) {
//            interpreter.reset();
//        }
//        save(tree);
//    }
//
//    private void addEntity(EntityRef entityRef, BehaviorComponent behaviorComponent) {
//        Interpreter interpreter = entityInterpreters.get(entityRef);
//        if (interpreter == null) {
//            interpreter = new Interpreter(new Actor(entityRef));
//            BehaviorTree tree = behaviorComponent.tree;
//            entityInterpreters.put(entityRef, interpreter);
//            if (tree != null) {
//                interpreter.start(tree.getRoot());
//            }
//        }
//    }
}
