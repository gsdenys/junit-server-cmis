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
package com.github.gsdenys;

import com.github.gsdenys.runner.base.CmisWarFinder;
import com.github.gsdenys.runner.base.PortDefinition;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Start a CMIS In Memory with Jetty Server.
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 1.0.3
 * @since 0.0.1
 */
public class CmisInMemoryRunner extends BlockJUnit4ClassRunner {

    private static int cmisPort;
    private static boolean initialized = false;
    private static Server server;


    /**
     * CMIS In Memory Starter
     *
     * @param clazz The class that will be use to sincronize
     * @throws InitializationError If any error cour
     */
    public CmisInMemoryRunner(Class<?> clazz) throws InitializationError {
        super(clazz);

        PortDefinition def = new PortDefinition(this);
        int port = def.defineCmisServerPort();

        //change the jetty running port and mark the server initialized as false
        //it means tha the server needs to be restarted using the new port
        if(cmisPort != port) {
            cmisPort = port;
            initialized = false;
        }

        //stop jetty server if it is started and the initialized was marked as false
        //sometimes it occur when some test define a port different that the actual
        //jetty server port
        if(server != null && server.isRunning() && !initialized) {
            try {
                server.stop();
                server.destroy();
            } catch (Exception e) {
               throw new InitializationError(
                       "Unable to stop and destroy the active instance of jetty  server"
               );
            }
        }

        //initialize the server
        synchronized (clazz) {
            if (!initialized) {
                try {
                    initialized = this.startJettyServer(
                            new CmisWarFinder().getCmisWarPath(),
                            cmisPort
                    );
                } catch (Exception e) {
                    throw new InitializationError(e);
                }
            }
        }
    }

    /**
     * Get the defineCmisServerPort where the CMIS server was started
     *
     * @return the CMIS server defineCmisServerPort
     */
    public static Integer getCmisPort() {
        return cmisPort;
    }

    /**
     * Start jetty server
     *
     * @param cmisWar the full path to the cmis in memory war file
     * @param port    the defineCmisServerPort to start jetty server
     * @return boolean
     * @throws Exception any exception that can occur
     */
    private boolean startJettyServer(final String cmisWar, final Integer port) throws Exception {
        this.server = new Server(port);

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/cmis/");
        webapp.setWar(cmisWar);

        server.setHandler(webapp);

        server.start();

        return true;
    }
}
