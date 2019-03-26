/*
 *    Constellation - An open source and standard compliant SDI
 *    http://www.constellation-sdi.org
 *
 * Copyright 2014 Geomatys.
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

package org.constellation.coverage.ws.rs;

import org.geotoolkit.coverage.grid.GridCoverage2D;
import org.geotoolkit.image.io.metadata.SpatialMetadata;

/**
 *
 * @author Guilhem Legal (Geomatys)
 */
public class GeotiffResponse {

    public GridCoverage2D coverage;
    
    public SpatialMetadata metadata;

    public String compression;

    public boolean tiling = false;

    public int tileHeight = 0;

    public int tileWidth = 0;
}