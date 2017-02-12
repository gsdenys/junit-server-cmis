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
import org.apache.chemistry.opencmis.commons.enums.CmisVersion;

/**
 /**
 * Class to define help define CMIS Version.
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 1.3.0
 * @since 1.3.0
 */
public class CmisVersionDefinition {

    private CmisInMemoryRunner runner;

    /**
     * Constructor
     *
     * @param runner the {@link CmisInMemoryRunner}
     */
    public CmisVersionDefinition(CmisInMemoryRunner runner) {
        this.runner = runner;
    }

    /**
     * Get the CMIS version defined at {@link Configure} annotation
     *
     * @return CmisVersion the version of CMIS
     */
    public CmisVersion getCmisVersion(){
        //in case of test class was annotated with @configure
        if (this.runner.getTestClass().getJavaClass().isAnnotationPresent(Configure.class)) {
            Configure configure = this.runner.getTestClass().getJavaClass().getDeclaredAnnotation(Configure.class);

            return configure.cmisVersion();
        }

        return CmisVersion.CMIS_1_1;
    }

    /**
     * Get the CMIS relative path based on a CMIS version
     *
     * @return String the CMIS relative path
     */
    public String getCmisPath() {
        if(this.getCmisVersion().equals(CmisVersion.CMIS_1_0)) {
            return "/atom";
        }

        return "/atom11";
    }
}