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

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.ServerSocket;
import java.util.Arrays;
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

    public static Integer CMIS_PORT;
    private static boolean initialized = false;

    private static Server server;
    private final String CMIS_LIB_NAME_WAR = "chemistry-opencmis-server-inmemory";

    /**
     * CMIS In Memory Starter
     *
     * @param klass The class that will be use to sincronize
     * @throws InitializationError If any error cour
     */
    public CmisInMemoryRunner(Class<?> klass) throws InitializationError {
        super(klass);

        synchronized (CmisInMemoryRunner.class) {
            Integer port = this.getPortDefinedByUser();
            if(port != null) {
                if (server != null && server.isRunning()) {
                    if (!CMIS_PORT.equals(port)) {
                        try {
                            server.stop();
                            initialized = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new InitializationError("Unable to stop server");
                        }
                    }
                }
            }

            if (!initialized) {
                String filePath = this.getWarFromJavaClassPath();

                try {
                    CMIS_PORT = this.port();
                    initialized = this.startJettyServer(filePath, CMIS_PORT);
                } catch (Exception e) {
                    throw new InitializationError(e);
                }
            }
        }
    }

    /**
     * Define the port to the jetty server execution
     *
     * @return Integer the port of jetty exeecution
     */
    private Integer port() {
        Integer port = this.getPortDefinedByUser();

        if(port != null) {
            return port;
        }

        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            return 8080;
        }
    }

    private Integer getPortDefinedByUser() {
        //in case of test class was annotated with @configure
        if(super.getTestClass().getJavaClass().isAnnotationPresent(Configure.class)) {
            Configure configure = super.getTestClass().getJavaClass().getDeclaredAnnotation(Configure.class);
            return configure.port();
        }

        return null;
    }

    /**
     * Start jetty server
     *
     * @param cmisWar the full path to the cmis in memory war file
     * @param port    the port to start jetty server
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
     * Build a URL based on a url base.
     *
     * @param base the url base
     * @return StringBuilder
     */
    private StringBuilder buildLibURL(final String base) {
        final String cmisRelativePath = "apache/chemistry/opencmis/";
        final String libVersion = "1.0.0";

        StringBuilder builder = new StringBuilder();
        // e.g. /home/username/.m2/repository/org/
        builder.append(base);

        // e.g. apache/chemistry/opencmis/chemistry-opencmis-server-inmemory/1.0.0/
        builder.append(cmisRelativePath);
        builder.append(CMIS_LIB_NAME_WAR);
        builder.append("/");
        builder.append(libVersion);
        builder.append("/");

        // e.g. chemistry-opencmis-server-inmemory-1.0.0.war
        builder.append(CMIS_LIB_NAME_WAR);
        builder.append("-");
        builder.append(libVersion);
        builder.append(".war");

        return builder;
    }


    /**
     * Get the cmis.war from a relative path based on eclipse-jetty.
     * <p>
     * This method is just used if you use maven, that instead gradle, cannot put the war in classpath
     *
     * @return String
     */
    private String getWarFromRelativePath() {
        String JETTY_RELATIVE_PATH = "eclipse/jetty/jetty-xml";
        final String regex = "(.*:)([a-zA-Z0-9/.-]+)(" + JETTY_RELATIVE_PATH + ")(.*)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(System.getProperty("java.class.path"));

        if (!matcher.find()) {
            return null;
        }

        return this.buildLibURL(matcher.group(2)).toString();
    }

    /**
     * Discovery the full path to the chemistry-opencmis-server-inmemory war file
     *
     * @return String
     * the cmis war file path
     */
    private String getWarFromJavaClassPath() {
        String regex = "(.*:)([a-zA-Z0-9/.-]+" + CMIS_LIB_NAME_WAR + "[a-zA-Z0-9/.-]*.war)(.*)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(System.getProperty("java.class.path"));

        return matcher.find() ? matcher.group(2) : this.getWarFromRelativePath();
    }
}
