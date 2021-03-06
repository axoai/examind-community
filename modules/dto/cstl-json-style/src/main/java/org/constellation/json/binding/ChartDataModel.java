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

package org.constellation.json.binding;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This model used as a response json from server to client.
 *
 * @author Mehdi Sidhoum (Geomatys).
 * @version 0.9
 * @since 0.9
 */
public class ChartDataModel implements Serializable {

    private boolean isNumberField;

    private Double minimum;

    private Double maximum;

    private Map<Object,Long> mapping = new LinkedHashMap<>();

    public ChartDataModel(){}

    public Map<Object, Long> getMapping() {
        return mapping;
    }

    public void setMapping(Map<Object, Long> mapping) {
        this.mapping = mapping;
    }

    public boolean isNumberField() {
        return isNumberField;
    }

    public void setNumberField(boolean isNumberField) {
        this.isNumberField = isNumberField;
    }

    public Double getMinimum() {
        return minimum;
    }

    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }

    public Double getMaximum() {
        return maximum;
    }

    public void setMaximum(Double maximum) {
        this.maximum = maximum;
    }
}
