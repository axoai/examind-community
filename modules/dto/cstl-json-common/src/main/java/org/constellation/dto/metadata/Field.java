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

package org.constellation.dto.metadata;

import com.fasterxml.jackson.annotation.JsonRawValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Pojo class used for Jackson that represents the binding for field
 * in metadata template json.
 *
 * @author Mehdi Sidhoum (Geomatys).
 * @since 0.9
 */
public class Field implements Serializable {
    private String name;
    private int multiplicity;
    private String help;
    private String path;
    private String type;
    private String render;
    @JsonRawValue
    public String defaultValue;
    @JsonRawValue
    public String value;
    private String obligation;
    private String ignore;
    private String tag;
    private List<String> predefinedValues;
    private boolean strict;
    private String completion;
    private boolean outOfBlock;

    public Field() {

    }
    
    public Field(final Field field) {
        this.name         = field.name;
        this.multiplicity = field.multiplicity;
        this.help         = field.help;
        this.path         = field.path;
        this.type         = field.type;
        this.render       = field.render;
        this.defaultValue = field.defaultValue;
        this.value        = field.value;
        this.obligation   = field.obligation;
        this.ignore       = field.ignore;
        this.tag          = field.tag;
        this.strict       = field.strict;
        this.completion   = field.completion;
        this.outOfBlock   = field.outOfBlock;
        this.predefinedValues = field.predefinedValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(int multiplicity) {
        this.multiplicity = multiplicity;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public void updatePath(String oldPrefix, String newPrefix) {
        path = path.replace(oldPrefix, newPrefix);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRender() {
        return render;
    }

    public void setRender(String render) {
        this.render = render;
    }

    public String getDefaultValue() {
        if (defaultValue == null || (render != null && (render.equals("integer") || render.equals("decimal")) && !"NaN".equalsIgnoreCase(value))) {
            return defaultValue;
        } else {
            return '"' + defaultValue + '"';
        }
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        if (value == null || (render != null && (render.equals("integer") || render.equals("decimal")) && !"NaN".equalsIgnoreCase(value))) {
            return value;
        } else {
            return '"' + value + '"';
        }
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getObligation() {
        return obligation;
    }

    public void setObligation(String obligation) {
        this.obligation = obligation;
    }

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    
     /**
     * @return the predefinedValues
     */
    public List<String> getPredefinedValues() {
        if (predefinedValues == null) {
            predefinedValues = new ArrayList<>();
        }
        return predefinedValues;
    }

    /**
     * @param predefinedValues the predefinedValues to set
     */
    public void setPredefinedValues(List<String> predefinedValues) {
        this.predefinedValues = predefinedValues;
    }
    
    /**
     * @return the strict
     */
    public boolean isStrict() {
        return strict;
    }

    /**
     * @param strict the strict to set
     */
    public void setStrict(boolean strict) {
        this.strict = strict;
    }
    
    public static boolean diff(Field original, Field modified) {
        return !original.value.equals(modified.value);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Block]\n");
        sb.append("name:").append(name).append('\n');
        sb.append("path:").append(path).append('\n');
        sb.append("type:").append(type).append('\n');
        sb.append("strict:").append(strict).append('\n');
        sb.append("outOfBlock:").append(outOfBlock).append('\n');
        sb.append("multiplicity:").append(multiplicity).append('\n');
        sb.append("defaultValue:").append(defaultValue).append('\n');
        if (predefinedValues != null) {
            sb.append("predefined values:").append('\n');
            for (String pv : predefinedValues) {
                sb.append(pv).append('\n');
            }
        }
        sb.append("value:").append(value);
        return sb.toString();
    }

    /**
     * @return the completion
     */
    public String getCompletion() {
        return completion;
    }

    /**
     * @param completion the completion to set
     */
    public void setCompletion(String completion) {
        this.completion = completion;
    }

    /**
     * @return the outOfBlock
     */
    public boolean isOutOfBlock() {
        return outOfBlock;
    }

    /**
     * @param outOfBlock the outOfBlock to set
     */
    public void setOutOfBlock(boolean outOfBlock) {
        this.outOfBlock = outOfBlock;
    }
}
