/*
 *    Constellation - An open source and standard compliant SDI
 *    http://www.constellation-sdi.org
 *
 * Copyright 2016-2017 Geomatys.
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
package org.constellation.api.rest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.constellation.api.ServiceDef;
import org.constellation.ws.IWSEngine;
import static org.constellation.api.rest.AbstractRestAPI.LOGGER;
import org.constellation.business.IDataBusiness;
import org.constellation.business.ILayerBusiness;
import org.constellation.business.ISensorBusiness;
import org.constellation.business.IServiceBusiness;
import org.constellation.configuration.ConfigDirectory;
import org.constellation.dto.DataBrief;
import org.constellation.dto.StyleBrief;
import org.constellation.dto.service.Instance;
import org.constellation.dto.service.config.wxs.Layer;
import org.constellation.dto.service.config.wxs.LayerSummary;
import org.constellation.dto.service.config.wxs.ServiceLayersDTO;
import org.constellation.dto.service.ServiceReport;
import org.constellation.dto.service.ServiceComplete;
import org.constellation.exception.ConfigurationException;
import org.constellation.exception.NotRunningServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.MediaType;
import org.constellation.security.SecurityManager;
import org.constellation.util.Util;
import org.constellation.ws.ICSWConfigurer;
import org.constellation.ws.ITHWConfigurer;
import org.constellation.ws.IWPSConfigurer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Johann Sorel (Geomatys)
 */
@RestController
public class ServiceRestAPI extends AbstractRestAPI {

    /**
     * File buffer size.
     */
    private static final int BUFFER_1024 = 1024;

    /**
     * Size of log file to read.
     */
    private static final int DEFAULT_LIMIT_4096 = 4096;

    @Inject
    private IWSEngine wsengine;
    @Inject
    private IServiceBusiness serviceBusiness;
    @Inject
    private ILayerBusiness layerBusiness;
    @Inject
    private IDataBusiness dataBusiness;
    @Inject
    private ISensorBusiness sensorBusiness;
    @Inject
    private SecurityManager securityManager;

    /**
     * Get available service types.
     *
     * @return ServiceReport
     */
    @RequestMapping(value="/services/types", method=GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceReport> getServiceTypes() {
        try {
            final ServiceReport report = new ServiceReport(wsengine.getRegisteredServices());
            return new ResponseEntity(report,OK);
        } catch(Throwable ex) {
            LOGGER.log(Level.WARNING, ex.getLocalizedMessage(), ex);
            return new ErrorMessage(ex).build();
        }
    }

    /**
     * Get all services instances.
     *
     * @param lang metadata lang, can be null
     * @param type service type, can be null
     * @return
     */
    @RequestMapping(value="/services/instances", method=GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listInstances(
            @RequestParam(value="lang",required=false) String lang,
            @RequestParam(value="type",required=false) String type) {
        try {
            final List<Instance> instances = new ArrayList<>();
            final List<ServiceComplete> services = (type==null) ? serviceBusiness.getAllServices(lang) : serviceBusiness.getAllServicesByType(lang,type);
            for (ServiceComplete service : services) {
                final Instance instance = convertToInstance(service);
                instances.add(instance);
            }
            return new ResponseEntity(instances, OK);
        } catch(Throwable ex) {
            LOGGER.log(Level.WARNING, ex.getLocalizedMessage(), ex);
            return new ErrorMessage(ex).build();
        }
    }

    /**
     * List service layers.
     *
     * @param lang metadata lang, can be null
     * @param type service type, not null
     * @return
     */
    @RequestMapping(value="/services/layers", method=GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listServiceLayers(
            @RequestParam(value="lang",required=false) String lang,
            @RequestParam(value="type",required=false) String type) {
        try {
            final List<ServiceLayersDTO> serviceLayers = new ArrayList<>();
            final List<ServiceComplete> services = serviceBusiness.getAllServicesByType(lang, type);
            for (final ServiceComplete service : services) {
                final List<Layer> layers = layerBusiness.getLayers(service.getId(), securityManager.getCurrentUserLogin());
                final List<LayerSummary> layerSummaries = new ArrayList<>();
                for (final Layer lay : layers) {
                    final DataBrief db = dataBusiness.getDataBrief(lay.getName(), lay.getProviderID());
                    final List<StyleBrief> sBriefs = Util.convertIntoStylesBrief(lay.getStyles());
                    final LayerSummary sum = new LayerSummary(lay, db, sBriefs);
                    layerSummaries.add(sum);
                }
                final ServiceLayersDTO servLay = new ServiceLayersDTO(service, layerSummaries);
                serviceLayers.add(servLay);
            }
            return new ResponseEntity(serviceLayers, OK);
        } catch(Throwable ex) {
            LOGGER.log(Level.WARNING, ex.getLocalizedMessage(), ex);
            return new ErrorMessage(ex).build();
        }
    }

    /**
     * Return a stream of last log.
     *
     * @param serviceType
     * @param serviceId
     * @param offset
     * @param limit
     * @param response
     */
    @RequestMapping(value = "services/logs/{serviceType}/{serviceId}", method=GET, produces=MediaType.TEXT_PLAIN_VALUE)
    public void getLogByService(
            @PathVariable("serviceType") final String serviceType,
            @PathVariable("serviceId") final String serviceId,
            @RequestParam(value="o", required=false) final Integer offset,
            @RequestParam(value="l", required=false) final Integer limit,
            HttpServletResponse response) {

        try {
            final ServletOutputStream output = response.getOutputStream();
            final Path logfileRelative = Paths.get("logs", "cstl", serviceType, serviceId + "-service.log");
            final Path logFile = ConfigDirectory.getConfigDirectory().resolve(logfileRelative);
            if (Files.exists(logFile)) {
                int toread = limit == null ? DEFAULT_LIMIT_4096 : limit;
                if (toread < BUFFER_1024) toread = 1024;

                try (FileChannel fc = (FileChannel.open(logFile))) {
                    int nread;
                    long length = fc.size();
                    if (offset != null) {
                        if (offset > 0 && offset < length)
                            fc.position(offset);
                    } else if (length > toread) {
                        fc.position(length - toread - 1);
                    }
                    ByteBuffer copy = ByteBuffer.allocate(1024);
                    do {
                        nread = fc.read(copy);
                        toread -= nread;
                        if (nread > 0 && copy.hasArray())
                            output.write(copy.array(), 0, nread);
                        copy.rewind();
                    } while (nread > 0 && toread > 0);

                } catch (IOException e) {
                    LOGGER.log(Level.WARNING,"I/O Exception while reading: " + logfileRelative.toString(), e);
                }
            } else {
                LOGGER.log(Level.WARNING,"No log file for " + serviceType + ", " + serviceId);
                output.write(("No log file for " + serviceType + ", " + serviceId).getBytes());
            }

        } catch (IOException ex) {
            LOGGER.log(Level.WARNING,"No log file for " + serviceType + ", " + serviceId);
        }

    }

    private Instance convertToInstance(final ServiceComplete service) throws ConfigurationException {
        final Instance instance = new Instance(service);
        int count;
        if("csw".equalsIgnoreCase(service.getType())){
            try {
                count = getCSWConfigurer().getMetadataCount(service.getIdentifier());
            }catch(Exception ex){
                count = 0;
            }
        } else if("sos".equalsIgnoreCase(service.getType())){
            try {
                count = sensorBusiness.getCountByServiceId(service.getIdentifier());
            }catch(Exception ex){
                count = 0;
            }
        } else if("wps".equalsIgnoreCase(service.getType())){
            try {
                count = getWPSConfigurer().getProcessCount(service.getId());
            }catch(Exception ex){
                count = 0;
            }
        } else if("thw".equalsIgnoreCase(service.getType())){
            try {
                count = getTHWConfigurer().getThesaurusCount(service.getId());
            }catch(Exception ex){
                count = 0;
            }
        } else if("webdav".equalsIgnoreCase(service.getType())){

            count = 0;

        } else {
            count = layerBusiness.getLayers(service.getId(), securityManager.getCurrentUserLogin()).size();
        }
        instance.setLayersNumber(count);
        return instance;
    }

    private ICSWConfigurer getCSWConfigurer() throws NotRunningServiceException {
        return (ICSWConfigurer) wsengine.newInstance(ServiceDef.Specification.CSW);
    }

    private IWPSConfigurer getWPSConfigurer() throws NotRunningServiceException {
        return (IWPSConfigurer) wsengine.newInstance(ServiceDef.Specification.WPS);
    }

    private ITHWConfigurer getTHWConfigurer() throws NotRunningServiceException {
        return (ITHWConfigurer) wsengine.newInstance(ServiceDef.Specification.THW);
    }

}
