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
package com.github.gsdenys.runner;

/**
 * Annotation to define a type description.
 * <p>
 * This description have a file description that define the document type file and a loader
 * that point to the loader that will be used to parse the document type file
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 1.0.2
 * @since 1.3.0
 */
public @interface TypeDescriptor {

    /**
     * The name of document type file. This document file needs to be in classpath.
     * <p>
     * <b>PS:</b> you can put the document type file at src/test/resources and it automatically
     * will stay at classpath in test runtime.
     *
     * @return the document type file
     */
    String file();

    /**
     * The loader that will be used to parse file and load document type.
     *
     * @return TypeLoader the loader to be used
     */
    TypeLoader loader() default TypeLoader.JSON;
}
