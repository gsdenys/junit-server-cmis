/*
 * Copyright 2016 gsdenys@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.gsdenys.cmisrunner;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Start a CMIS In Memory with Jetty Server.
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public class CmisInMemoryRunner extends BlockJUnit4ClassRunner {

    private static Integer CMIS_PORT;
    private static boolean initialized = false;

    private PortDefinition portDefinition;
    private CmisWarFinder cmisWarFinder;

    private static Server server;

    /**
     * CMIS In Memory Starter
     *
     * @param clazz The class that will be use to sincronize
     * @throws InitializationError If any error cour
     */
    public CmisInMemoryRunner(Class<?> clazz) throws InitializationError {
        super(clazz);

        this.portDefinition = new PortDefinition(this);
        this.cmisWarFinder = new CmisWarFinder();

        synchronized (clazz) {
            Integer port = portDefinition.getPortDefinedByUser();
            if (port != null && server != null && server.isRunning() && !CMIS_PORT.equals(port)) {
                try {
                    server.stop();
                    initialized = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new InitializationError("Unable to stop server");
                }
            }

            if (!initialized) {
                String filePath = this.cmisWarFinder.getCmisWarPath();
                CMIS_PORT = this.portDefinition.defineCmisServerPort();

                try {
                    initialized = this.startJettyServer(filePath, CMIS_PORT);
                } catch (Exception e) {
                    throw new InitializationError(e);
                }
            }
        }
    }

    /**
     * Start jetty server
     *
     * @param cmisWar the full path to the cmis in memory war file
     * @param port    the defineCmisServerPort to start jetty server
     * @return boolean
     * @throws Exception any exception that can occur
     */
    private boolean startJettyServer(String cmisWar, Integer port) throws Exception {
        this.server = new Server(port);

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/cmis/");
        webapp.setWar(cmisWar);

        this.server.setHandler(webapp);

        this.server.start();

        return true;
    }

    /**
     * Get the defineCmisServerPort where the CMIS server was started
     *
     * @return the CMIS server defineCmisServerPort
     */
    public static Integer getCmisPort() {
        return CMIS_PORT;
    }
}
