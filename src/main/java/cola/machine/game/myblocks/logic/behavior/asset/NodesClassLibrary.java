/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cola.machine.game.myblocks.logic.behavior.asset;

/**
 * @author synopia
 */
public class NodesClassLibrary {//extends AbstractClassLibrary<Node> {
//    private static final Logger logger = LoggerFactory.getLogger(NodesClassLibrary.class);
//
//    public NodesClassLibrary(ReflectFactory factory, CopyStrategyLibrary copyStrategies) {
//        super(factory, copyStrategies);
//    }
//
//    public void scan(ModuleEnvironment environment) {
//        for (Class<? extends Node> entry : environment.getSubtypesOf(Node.class)) {
//            logger.debug("Found node class {}", entry);
//            register(new SimpleUri(environment.getModuleProviding(entry), entry.getSimpleName()), entry);
//        }
//    }
//
//    @Override
//    protected <N extends Node> ClassMetadata<N, ?> createMetadata(Class<N> type, ReflectFactory factory, CopyStrategyLibrary copyStrategies, SimpleUri uri) {
//        try {
//            return new DefaultClassMetadata<>(uri, type, factory, copyStrategies);
//        } catch (NoSuchMethodException e) {
//            logger.error("Unable to register class {}: Default Constructor Required", type.getSimpleName(), e);
//            return null;
//        }
//    }
}
