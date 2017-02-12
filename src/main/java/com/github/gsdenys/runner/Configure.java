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
package com.github.gsdenys.runner;

import org.apache.chemistry.opencmis.commons.enums.CmisVersion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to configure some parameters of CMIS server. In case that this annotation is not
 * present, the server will select an unused port.
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 1.2.0
 * @since 1.2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configure {

    /**
     * the port where the cmis server will run. By json 0 that means will be used a random port
     *
     * @return int  the port where the cmis server need to be started
     */
    int port() default 0;

    /**
     * The cmisVersion
     *
     * @return CmisVersion the cmisVersion of cmis
     */
    CmisVersion cmisVersion() default CmisVersion.CMIS_1_1;

    /**
     * The document type descriptor file. This parameter use needs a {@link Configure#cmisVersion()}
     * equals {@link CmisVersion#CMIS_1_1}
     *
     * @return the descriptor file
     */
    TypeDescriptor[] docTypes() default {};
}
