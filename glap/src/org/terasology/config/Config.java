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

package org.terasology.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Terasology user config. Holds the various global configuration information that the user can modify. It can be saved
 * and loaded in a JSON format.
 *
 * @author Immortius
 */
public final class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);

  
    /**
     * Create a new, empty config
     */
    public Config() {
    }

  
}
