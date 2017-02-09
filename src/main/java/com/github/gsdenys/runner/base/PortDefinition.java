/*
 * Copyright 2017 gsdenys@gmail.com
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
package com.github.gsdenys.runner.base;

import com.github.gsdenys.CmisInMemoryRunner;
import com.github.gsdenys.runner.Configure;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class to define a better way to use port.
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 1.3.0
 * @since 1.3.0
 */
public class PortDefinition {

    private CmisInMemoryRunner runner;

    /**
     * Constructor
     *
     * @param runner the runner object
     */
    public PortDefinition(CmisInMemoryRunner runner) {
        this.runner = runner;
    }

    /**
     * Define the defineCmisServerPort to the jetty server execution
     *
     * @return Integer the defineCmisServerPort of jetty exeecution
     */
    public int defineCmisServerPort() {
        Integer port = this.getPortDefinedByUser();

        if (port != 0) {
            return port;
        }

        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            return 8080;
        }
    }

    /**
     * Get the CMIS port defined by user if the annotation {@link Configure} was present of Test Unit Class
     *
     * @return the CMIS server port
     */
    public int getPortDefinedByUser() {

        System.out.println("\n\n CLASS: " + this.runner.getTestClass());

        //in case of test class was annotated with @configure
        if (this.runner.getTestClass().getJavaClass().isAnnotationPresent(Configure.class)) {
            Configure configure = this.runner.getTestClass().getJavaClass().getDeclaredAnnotation(Configure.class);

            System.out.println("\n\n\n PORT: " + configure.port());

            return configure.port();
        }

        return 0;
    }

    /**
     * check the port availability
     *
     * @param port the port
     * @return boolean <code>true</code> if the port is available
     */
    public boolean isPortAvailable(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }
}
