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

import org.constellation.process.ExamindProcessFactory;
import org.constellation.test.utils.Order;
import org.constellation.test.utils.SpringTestRunner;
import org.geotoolkit.process.ProcessDescriptor;
import org.geotoolkit.process.ProcessException;
import org.geotoolkit.process.ProcessFinder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opengis.parameter.ParameterValueGroup;
import org.opengis.util.NoSuchIdentifierException;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author Quentin Boileau (Geomatys).
 */
@RunWith(SpringTestRunner.class)
public abstract class RestartServiceTest extends ServiceProcessTest {

    public RestartServiceTest(final String serviceName, final Class workerClass) {
        super(RestartServiceDescriptor.NAME, serviceName, workerClass);
    }


    /**
     * Start all the existing  instance.
     */
    private void startAllInstance() {
        final List<String> serviceIDs = serviceBusiness.getServiceIdentifiers(serviceName.toLowerCase());
        for (String serviceID : serviceIDs) {
            startInstance(serviceID);
        }
    }

    @Test
    @Order(order = 1)
    public void testRestartOneNoClose() throws NoSuchIdentifierException, ProcessException {

        LOGGER.info("TEST Restart One no close");
        createInstance("restartInstance1");
        startInstance("restartInstance1");

        try {
            final int initSize = engine.getInstanceSize(serviceName);
            final ProcessDescriptor desc = ProcessFinder.getProcessDescriptor(ExamindProcessFactory.NAME, RestartServiceDescriptor.NAME);

            final ParameterValueGroup in = desc.getInputDescriptor().createValue();
            in.parameter(RestartServiceDescriptor.SERVICE_TYPE_NAME).setValue(serviceName);
            in.parameter(RestartServiceDescriptor.IDENTIFIER_NAME).setValue("restartInstance1");
            in.parameter(RestartServiceDescriptor.CLOSE_NAME).setValue(false);
            org.geotoolkit.process.Process proc = desc.createProcess(in);
            proc.call();

            assertTrue(engine.getInstanceSize(serviceName) == initSize);
            assertTrue(engine.serviceInstanceExist(serviceName, "restartInstance1"));
        } finally {
            deleteInstance("restartInstance1");
        }
    }

    @Test
    @Order(order = 2)
    public void testRestartOneClose() throws NoSuchIdentifierException, ProcessException {

        LOGGER.info("TEST Restart One close");

        createInstance("restartInstance2");
        startInstance("restartInstance2");

        try {
            final int initSize = engine.getInstanceSize(serviceName);
            final ProcessDescriptor desc = ProcessFinder.getProcessDescriptor(ExamindProcessFactory.NAME, RestartServiceDescriptor.NAME);

            final ParameterValueGroup in = desc.getInputDescriptor().createValue();
            in.parameter(RestartServiceDescriptor.SERVICE_TYPE_NAME).setValue(serviceName);
            in.parameter(RestartServiceDescriptor.IDENTIFIER_NAME).setValue("restartInstance2");
            in.parameter(RestartServiceDescriptor.CLOSE_NAME).setValue(true);
            org.geotoolkit.process.Process proc = desc.createProcess(in);
            proc.call();
            Thread.sleep(1000);

            assertTrue(engine.getInstanceSize(serviceName) == initSize);
            assertTrue(engine.serviceInstanceExist(serviceName, "restartInstance2"));
        } catch (InterruptedException ex) {
            fail(ex.getMessage());
        } finally {
            deleteInstance("restartInstance2");
        }
    }

    @Test
    @Order(order = 3)
    public void testRestartAllNoClose() throws NoSuchIdentifierException, ProcessException, InterruptedException {

        LOGGER.info("TEST Restart all no close");

        startAllInstance();
        createInstance("restartInstance3");
        createInstance("restartInstance4");
        startInstance("restartInstance3");
        startInstance("restartInstance4");

        try {
            final int initSize = engine.getInstanceSize(serviceName);
            final Set<String> instancesBefore = engine.getInstanceNames(serviceName);
            final ProcessDescriptor desc = ProcessFinder.getProcessDescriptor(ExamindProcessFactory.NAME, RestartServiceDescriptor.NAME);

            final ParameterValueGroup in = desc.getInputDescriptor().createValue();
            in.parameter(RestartServiceDescriptor.SERVICE_TYPE_NAME).setValue(serviceName);
            in.parameter(RestartServiceDescriptor.IDENTIFIER_NAME).setValue(null);
            in.parameter(RestartServiceDescriptor.CLOSE_NAME).setValue(false);
            org.geotoolkit.process.Process proc = desc.createProcess(in);
            proc.call();
            Thread.sleep(1000);

            final int newSize =  engine.getInstanceSize(serviceName);
            final Set<String> instances = engine.getInstanceNames(serviceName);
            assertTrue("expected " + initSize + " (" +  instancesBefore + ") but was:" + newSize + "(" + instances + ")", newSize == initSize);
            assertTrue(engine.serviceInstanceExist(serviceName, "restartInstance3"));
            assertTrue(engine.serviceInstanceExist(serviceName, "restartInstance4"));

        } finally {
            deleteInstance("restartInstance3");
            deleteInstance("restartInstance4");
        }
    }

    @Test
    @Order(order = 4)
    public void testRestartAllClose() throws NoSuchIdentifierException, ProcessException {

        LOGGER.info("TEST Restart all close");

        startAllInstance();
        createInstance("restartInstance5");
        createInstance("restartInstance6");
        startInstance("restartInstance5");
        startInstance("restartInstance6");

        try {
            final int initSize = engine.getInstanceSize(serviceName);
            final ProcessDescriptor desc = ProcessFinder.getProcessDescriptor(ExamindProcessFactory.NAME, RestartServiceDescriptor.NAME);

            final ParameterValueGroup in = desc.getInputDescriptor().createValue();
            in.parameter(RestartServiceDescriptor.SERVICE_TYPE_NAME).setValue(serviceName);
            in.parameter(RestartServiceDescriptor.IDENTIFIER_NAME).setValue(null);
            in.parameter(RestartServiceDescriptor.CLOSE_NAME).setValue(true);

            org.geotoolkit.process.Process proc = desc.createProcess(in);
            proc.call();

            assertTrue(engine.getInstanceSize(serviceName) == initSize);
            assertTrue(engine.serviceInstanceExist(serviceName, "restartInstance5"));
            assertTrue(engine.serviceInstanceExist(serviceName, "restartInstance6"));
        } finally {
            deleteInstance("restartInstance5");
            deleteInstance("restartInstance6");
        }
    }

    /**
     * Restart an instance that exist but no started.
     */
    @Test
    @Order(order = 5)
    public void testStart() throws NoSuchIdentifierException, ProcessException {

        LOGGER.info("TEST start");

        createInstance("restartInstance40");
        try {
            final int initSize = engine.getInstanceSize(serviceName);
            final ProcessDescriptor desc = ProcessFinder.getProcessDescriptor(ExamindProcessFactory.NAME, RestartServiceDescriptor.NAME);

            final ParameterValueGroup in = desc.getInputDescriptor().createValue();
            in.parameter(RestartServiceDescriptor.SERVICE_TYPE_NAME).setValue(serviceName);
            in.parameter(RestartServiceDescriptor.IDENTIFIER_NAME).setValue("restartInstance40");

            org.geotoolkit.process.Process proc = desc.createProcess(in);
            proc.call();
            Thread.sleep(1000);

            assertTrue(engine.serviceInstanceExist(serviceName, "restartInstance40"));
        } catch (InterruptedException ex) {
            fail(ex.getMessage());
        } finally {
            deleteInstance("restartInstance40");
        }
    }

    /**
     * Try to restart an instance that doesn't exist.
     * @throws NoSuchIdentifierException
     * @throws ProcessException
     */
    @Test
    @Order(order = 6)
    public void testFailRestart2() throws NoSuchIdentifierException, ProcessException {
        LOGGER.info("TEST fail Restart 2");

        final ProcessDescriptor desc = ProcessFinder.getProcessDescriptor(ExamindProcessFactory.NAME, RestartServiceDescriptor.NAME);

        final ParameterValueGroup in = desc.getInputDescriptor().createValue();
        in.parameter(RestartServiceDescriptor.SERVICE_TYPE_NAME).setValue(serviceName);
        in.parameter(RestartServiceDescriptor.IDENTIFIER_NAME).setValue("restartInstance5");

        try {
            org.geotoolkit.process.Process proc = desc.createProcess(in);
            proc.call();
            fail();
        } catch (ProcessException ex) {
            //do nothing
        }
    }

}
