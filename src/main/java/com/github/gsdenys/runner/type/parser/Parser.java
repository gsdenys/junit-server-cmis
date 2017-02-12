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
package com.github.gsdenys.runner.type.parser;

import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinition;

import java.io.InputStream;
import java.util.List;

/**
 * Interface to parser Document Type.
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 1.0.0
 * @since 2.0.0
 */
public interface Parser {

    final String ID = "id";
    final String LOCAL_NAME = "localName";
    final String LOCAL_NAMESPACE = "localNamespace";
    final String QUERY_NAME = "queryName";
    final String DISPLAY_NAME = "displayName";
    final String DESCRIPTION = "description";

    final String IS_QUERYABLE = "isQueryable";

    //type
    final String PARENT_TYPE_ID = "parentTypeId";
    final String TYPE_BASE_ID = "baseId";

    final String IS_CREATEBLE = "isCreatable";
    final String IS_INCLUDED_IN_SYPERTYPE_QUERY = "isIncludedInSupertypeQuery";
    final String IS_FULL_TEXT_INDEXED = "isFulltextIndexed";
    final String IS_CONTROLLABLE_ACL = "isControllableACL";
    final String IS_CONTROLLABLE_POLICY = "isControllablePolicy";

    //property
    final String PROPERTY_TYPE = "propertyType";
    final String PROPERTY_DEFINITIONS = "propertyDefinitions";

    final String IS_INHERITED = "isInherited";
    final String IS_ORDERABLE = "isOrderable";
    final String IS_REQUIRED = "isRequired";
    final String IS_OPEN_CHOICE = "isOpenChoice";

    /**
     * Get a {@link List} of {@link TypeDefinition} based on a {@link InputStream}
     *
     * @param is the document type {@link InputStream}
     * @return List the {@link TypeDefinition} {@link List}
     * @throws ParserException any errors during the parser action
     */
    List<TypeDefinition> getTypes(InputStream is) throws ParserException;

    /**
     * Get the {@link TypeDefinition}
     *
     * @param obj the object to be parsed to generated the {@link TypeDefinition} object
     * @param <E> The element to be loaded
     * @return TypeDefinition the type definition loaded
     * @throws ParserException any errors during the parser action
     */
    <E> TypeDefinition loadType(E obj) throws ParserException;

    /**
     * Get a {@link List} of {@link PropertyDefinition}
     *
     * @param obj the object to be parsed to generated the {@link List} of {@link PropertyDefinition} object
     * @param <E> The element to be loaded
     * @return List the {@link List} of {@link PropertyDefinition}
     * @throws ParserException any errors during the parser action
     */
    <E> List<PropertyDefinition> getProperties(E obj) throws ParserException;

    /**
     * Load the {@link PropertyDefinition}
     *
     * @param obj the object to be parsed to generated the {@link PropertyDefinition} object
     * @param <E> The element to be loaded
     * @return PropertyDefinition the {@link PropertyDefinition} object
     * @throws ParserException any errors during the parser action
     */
    <E> PropertyDefinition loadProperty(E obj) throws ParserException;
}