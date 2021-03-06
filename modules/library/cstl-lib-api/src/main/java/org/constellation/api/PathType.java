/*
 *    Constellation - An open source and standard compliant SDI
 *    http://www.constellation-sdi.org
 *
 * Copyright 2016 Geomatys.
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

package org.constellation.api;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Guilhem Legal (Geomatys)
 */
public class PathType {

    public List<String> paths;
    public Class type;
    public Map<String, String> prefixMapping;

    public PathType(Class type, List<String> paths, Map<String, String> prefixMapping) {
        this.type = type;
        this.paths = paths;
        this.prefixMapping = prefixMapping;
    }
}
