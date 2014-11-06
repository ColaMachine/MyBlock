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


import cola.machine.game.myblocks.asset.AssetType;
import cola.machine.game.myblocks.asset.AssetUri;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.entitySystem.Component;
import cola.machine.game.myblocks.entitySystem.prefab.Prefab;
import cola.machine.game.myblocks.entitySystem.prefab.PrefabData;

import java.util.Collections;
import java.util.List;

/**
 * @author Immortius
 */
public class NullPrefab extends Prefab {

    public NullPrefab() {
        super(new AssetUri(AssetType.PREFAB, Constants.ENGINE_MODULE, "null"));
    }

    @Override
    public Prefab getParent() {
        return null;
    }

    @Override
    public List<Prefab> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public boolean isPersisted() {
        return true;
    }

    @Override
    public boolean isAlwaysRelevant() {
        return false;
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public void reload(PrefabData data) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isDisposed() {
        return false;
    }

    @Override
    public boolean hasComponent(Class<? extends Component> component) {
        return false;
    }

    @Override
    public <T extends Component> T getComponent(Class<T> componentClass) {
        return null;
    }

    @Override
    public Iterable<Component> iterateComponents() {
        return Collections.emptyList();
    }
}
