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
package org.constellation.process.service;

import org.constellation.business.IServiceBusiness;
import org.constellation.exception.ConfigurationException;
import org.constellation.process.AbstractCstlProcess;
import org.geotoolkit.process.ProcessDescriptor;
import org.geotoolkit.process.ProcessException;
import org.opengis.parameter.ParameterValueGroup;
import org.springframework.beans.factory.annotation.Autowired;

import static org.constellation.process.service.GetConfigServiceDescriptor.CONFIGURATION;
import static org.constellation.process.service.GetConfigServiceDescriptor.IDENTIFIER;
import static org.constellation.process.service.GetConfigServiceDescriptor.SERVICE_TYPE;
import static org.geotoolkit.parameter.Parameters.getOrCreate;

/**
 *
 * @author Quentin Boileau (Geoamtys)
 */
public class GetConfigService extends AbstractCstlProcess {
    @Autowired
    public IServiceBusiness serviceBusiness;

    public GetConfigService(final ProcessDescriptor desc, final ParameterValueGroup parameter) {
        super(desc, parameter);
    }

    /**
     * Get the configuration of an existing instance for a specified service and instance name.
     *
     * @throws ProcessException in cases :
     * - if the service name is different from WMS, WMTS, WCS of WFS (no matter of case).
     * - if instance name doesn't exist.
     * - if error during file creation or unmarshalling phase.
     */
    @Override
    protected void execute() throws ProcessException {
        final String serviceType       = inputParameters.getValue(SERVICE_TYPE);
        final String identifier        = inputParameters.getValue(IDENTIFIER);
        try {
            serviceBusiness.ensureExistingInstance(serviceType.toLowerCase(), identifier);
            final Object obj = serviceBusiness.getConfiguration(serviceType.toLowerCase(), identifier);
            getOrCreate(CONFIGURATION, outputParameters).setValue(obj);
        } catch (ConfigurationException ex) {
            throw new ProcessException(ex.getMessage(), this, ex);
        }
    }
}
