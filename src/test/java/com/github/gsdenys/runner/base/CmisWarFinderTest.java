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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

/**
 * Test case for {@link CmisWarFinder} class.
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 1.3.0
 * @since 1.3.0
 */
@RunWith(CmisInMemoryRunner.class)
public class CmisWarFinderTest {

    private CmisWarFinder warFinder;

    @Before
    public void setUp() throws Exception {
        if (this.warFinder == null) {
            this.warFinder = new CmisWarFinder();
        }
    }

    @Test
    public void buildLibURL() throws Exception {
        String base = "/home/user/.m2/repository/org/";

        Method method = CmisWarFinder.class.getDeclaredMethod("buildLibURL", String.class);
        method.setAccessible(true);

        StringBuilder builder = (StringBuilder) method.invoke(this.warFinder, base);

        Assert.assertNotNull("The path should not be null", builder.toString());
    }

    @Test
    public void getWarFromRelativePath() throws Exception {
        String javaPath = "/home/user/workspace/testmavencmisrunner/target/test-classes:" +
                "/home/user/workspace/testmavencmisrunner/target/classes:" +
                "/home/user/.m2/repository/com/github/gsdenys/junit-server-cmis/1.1.2/junit-server-cmis-1.1.2.jar:" +
                "/home/user/.m2/repository/junit/junit/4.11/junit-4.11.jar:" +
                "/home/user/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:" +
                "/home/user/.m2/repository/org/eclipse/jetty/jetty-server/9.4.0.v20161208/jetty-server-9.4.0.v20161208.jar:" +
                "/home/user/.m2/repository/javax/servlet/javax.servlet-api/3.1.0/javax.servlet-api-3.1.0.jar:" +
                "/home/user/.m2/repository/org/eclipse/jetty/jetty-http/9.4.0.v20161208/jetty-http-9.4.0.v20161208.jar:" +
                "/home/user/.m2/repository/org/eclipse/jetty/jetty-util/9.4.0.v20161208/jetty-util-9.4.0.v20161208.jar:" +
                "/home/user/.m2/repository/org/eclipse/jetty/jetty-io/9.4.0.v20161208/jetty-io-9.4.0.v20161208.jar:" +
                "/home/user/.m2/repository/org/eclipse/jetty/jetty-webapp/9.4.0.v20161208/jetty-webapp-9.4.0.v20161208.jar:" +
                "/home/user/.m2/repository/org/eclipse/jetty/jetty-xml/9.4.0.v20161208/jetty-xml-9.4.0.v20161208.jar:" +
                "/home/user/.m2/repository/org/eclipse/jetty/jetty-servlet/9.4.0.v20161208/jetty-servlet-9.4.0.v20161208.jar:" +
                "/home/user/.m2/repository/org/eclipse/jetty/jetty-security/9.4.0.v20161208/jetty-security-9.4.0.v20161208.jar";

        Method method = CmisWarFinder.class.getDeclaredMethod("getWarFromRelativePath", String.class);
        method.setAccessible(true);

        String path = (String) method.invoke(this.warFinder, javaPath);

        Assert.assertNotNull("The path should not be null", path);
    }

    @Test
    public void getWarFromJavaClassPath() throws Exception {
        String javaPath = System.getProperty("java.class.path");

        Method method = CmisWarFinder.class.getDeclaredMethod("getWarFromJavaClassPath", String.class);
        method.setAccessible(true);

        String path = (String) method.invoke(this.warFinder, javaPath);

        Assert.assertNotNull("The path should not be null", path);
    }

    @Test
    public void getCmisWarPath() throws Exception {
        String path = this.warFinder.getCmisWarPath();

        Assert.assertNotNull("The path should not be null", path);
    }

}