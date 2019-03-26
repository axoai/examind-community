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

import org.opengis.style.ContrastEnhancement;

import static org.apache.sis.util.ArgumentChecks.ensureNonNull;
import static org.constellation.json.util.StyleFactories.SF;

/**
 * @author Fabien Bernard (Geomatys).
 * @version 0.9
 * @since 0.9
 */
public final class SelectedChannelType implements StyleElement<org.opengis.style.SelectedChannelType> {

    private String name = "";

    public SelectedChannelType() {
    }

    public SelectedChannelType(final org.opengis.style.SelectedChannelType selectedChannelType) {
        ensureNonNull("selectedChannelType", selectedChannelType);
        this.name = selectedChannelType.getChannelName();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public org.opengis.style.SelectedChannelType toType() {
        return SF.selectedChannelType(name, (ContrastEnhancement) null);
    }
}