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
    private final String CMIS_LIB_NAME_WAR = "chemistry-opencmis-server-inmemory";
    private final String JETTY_RELATIVE_PATH = "eclipse/jetty/jetty-xml";
    private static boolean initialized = false;

    /**
     * CMIS In Memory Starter
     *
     * @param klass The class that will be use to sincronize
     * @throws InitializationError If any error cour
     */
    public CmisInMemoryRunner(Class<?> klass) throws InitializationError {
        super(klass);

        synchronized (CmisInMemoryRunner.class) {
            if (!initialized) {
                System.out.println("Let's run cmis in memory server with jetty ...");

                String filePath = this.getWarFromJavaClassPath();

                try {
                    initialized = this.startJettyServer(filePath, 8080);
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
     * @param port    the port to start jetty server
     * @return boolean
     * @throws Exception any exception that can occur
     */
    private boolean startJettyServer(String cmisWar, Integer port) throws Exception {
        Server server = new Server(port);

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/cmis/");
        webapp.setWar(cmisWar);

        server.setHandler(webapp);

        server.start();

        return true;
    }

    /**
     * Build a URL based on a url base.
     *
     * @param base
     *          the url base
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
     *
     * This method is just used if you use maven, that instead gradle, cannot put the war in classpath
     *
     * @return String
     */
    private String getWarFromRelativePath() {
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
