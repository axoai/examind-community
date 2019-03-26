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

package org.constellation.metadata.utils;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.sis.xml.MarshallerPool;
import org.constellation.ws.CstlServiceException;
import org.constellation.ws.MimeType;
import org.geotoolkit.csw.xml.AbstractCswRequest;

import static org.constellation.metadata.core.CSWConstants.ACCEPTED_OUTPUT_FORMATS;
import org.constellation.util.NodeUtilities;
import org.geotoolkit.csw.xml.CSWMarshallerPool;
import org.geotoolkit.csw.xml.v202.RecordType;
import static org.geotoolkit.ows.xml.OWSExceptionCode.INVALID_PARAMETER_VALUE;
import org.w3c.dom.Node;

/**
 *
 * @author Guilhem Legal (Geomatys)
 */
public class CSWUtils {

     /**
     * Return the request (or default) outputFormat (MIME type) of the response.
     * if the format is not supported it throws a WebService Exception.
     *
     * @param request
     * @return the outputFormat (MIME type) of the response.
     * @throws CstlServiceException
     */
    public static String getOutputFormat(final AbstractCswRequest request) throws CstlServiceException {

        // we initialize the output format of the response
        final String format = request.getOutputFormat();
        if (format != null && ACCEPTED_OUTPUT_FORMATS.contains(format)) {
            return format;
        } else if (format != null && !ACCEPTED_OUTPUT_FORMATS.contains(format)) {
            final StringBuilder supportedFormat = new StringBuilder();
            for (String s: ACCEPTED_OUTPUT_FORMATS) {
                supportedFormat.append(s).append('\n');
            }
            throw new CstlServiceException("The server does not support this output format: " + format + '\n' +
                                             " supported ones are: " + '\n' + supportedFormat.toString(),
                                             INVALID_PARAMETER_VALUE, "outputFormat");
        } else {
            return MimeType.APPLICATION_XML;
        }
    }
    
    public static Node transformToNode(final Object obj, final MarshallerPool pool) throws CstlServiceException {
        if (obj instanceof Node) {
            return (Node) obj;
        } else {
            try {
                // special case for RecordType
                if (obj instanceof RecordType) {
                    return NodeUtilities.getNodeFromGeotkMetadata(obj, CSWMarshallerPool.getInstance());
                }
                return NodeUtilities.getNodeFromGeotkMetadata(obj, pool);
            } catch (JAXBException | ParserConfigurationException ex) {
                throw new CstlServiceException("Unable to parse the object.", ex);
            }
        }
    }
}