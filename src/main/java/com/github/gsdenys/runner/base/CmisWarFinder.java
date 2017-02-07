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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to find the CMIS im Memory War
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 1.3.0
 * @since 1.3.0
 */
public class CmisWarFinder {

    private final String CMIS_LIB_NAME_WAR = "chemistry-opencmis-server-inmemory";

    /**
     * Build a URL based on a url base.
     *
     * @param base the url base
     * @return StringBuilder
     */
    private StringBuilder buildLibURL(final String base) {
        final String CMIS_LIB_VERSION = "1.0.0";

        StringBuilder builder = new StringBuilder();
        // e.g. /home/username/.m2/repository/org/
        builder.append(base);

        // e.g. apache/chemistry/opencmis/chemistry-opencmis-server-inmemory/1.0.0/
        String CMIS_RELATIVE_PATH = "apache/chemistry/opencmis/";
        builder.append(CMIS_RELATIVE_PATH);
        builder.append(CMIS_LIB_NAME_WAR);
        builder.append("/");
        builder.append(CMIS_LIB_VERSION);
        builder.append("/");

        // e.g. chemistry-opencmis-server-inmemory-1.0.0.war
        builder.append(CMIS_LIB_NAME_WAR);
        builder.append("-");
        builder.append(CMIS_LIB_VERSION);
        builder.append(".war");

        return builder;
    }

    /**
     * Get the cmis.war from a relative path based on eclipse-jetty.
     * <p>
     * This method is just used if you use maven, that instead gradle cannot put the war in classpath
     *
     * @param javaPath the java classpath
     * @return String the CMIS war path
     */
    private String getWarFromRelativePath(final String javaPath) {
        String JETTY_RELATIVE_PATH = "eclipse/jetty/jetty-server";

        /*
         * Regex to parse java path searching to jetty base path
         *
         * The first group represents all libs before Jetty library ":".
         * The second group is the group that we wanna get, its represents the path of jetty lib
         * file less their relative path mapped at JETTY_RELATIVE_PATH variable
         * The third group is the jetty relative path like eclipse/jetty/jetty-xml
         * The fourth group is the name of jetty lib name and all others libs after Jetty library.
         */
        final String regex = "(.*:)([a-zA-Z0-9/.-]+)(" + JETTY_RELATIVE_PATH + ")(.*)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(javaPath);

        if (!matcher.find()) {
            return null;
        }

        return this.buildLibURL(matcher.group(2)).toString();
    }

    /**
     * Discovery the full path to the chemistry-opencmis-server-inmemory war file
     *
     * @param javaPath the java classpath
     * @return String the cmis war file path
     */
    private String getWarFromJavaClassPath(final String javaPath) {
        /*
         * Regex to parse the java path searching the CMIS war path
          *
         * The first group represents all libs before CMIS war library ":".
         * The second group is the group that we wanna get, its represents the path of CMIS war file.
         * The third is all others libs after CMIS war library.
         */
        String regex = "(.*:)([a-zA-Z0-9/.-]+" + CMIS_LIB_NAME_WAR + "[a-zA-Z0-9/.-]*.war)(.*)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(javaPath);

        return matcher.find() ? matcher.group(2) : null;
    }


    /**
     * Get CMIS war path
     *
     * @return String the cmis war path
     */
    public String getCmisWarPath() {
        String javaPath = System.getProperty("java.class.path");

        String path = this.getWarFromJavaClassPath(javaPath);

        return (path != null) ? path : this.getWarFromRelativePath(javaPath);
    }
}
