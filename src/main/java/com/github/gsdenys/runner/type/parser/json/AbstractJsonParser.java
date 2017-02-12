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
package com.github.gsdenys.runner.type.parser.json;

import com.github.gsdenys.runner.type.parser.Parser;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinition;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.PropertyType;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.*;
import org.json.simple.JSONObject;

/**
 * Abstract Class to help parse the JSON document type file
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 1.0.0
 * @since 2.0.0
 */
abstract class AbstractJsonParser {
    /**
     * define the type of Metadata
     *
     * @param propertyType the property type
     */
    protected AbstractPropertyDefinition definePropertyType(final String propertyType) {
        AbstractPropertyDefinition propDef = null;

        if (propertyType.equals(PropertyType.BOOLEAN.value())) {
            propDef = new PropertyBooleanDefinitionImpl();
            propDef.setPropertyType(PropertyType.BOOLEAN.BOOLEAN);
        } else if (propertyType.equals(PropertyType.DATETIME.value())) {
            propDef = new PropertyDateTimeDefinitionImpl();
            propDef.setPropertyType(PropertyType.DATETIME);
        } else if (propertyType.equals(PropertyType.DECIMAL.value())) {
            propDef = new PropertyDecimalDefinitionImpl();
            propDef.setPropertyType(PropertyType.DECIMAL);
        } else if (propertyType.equals(PropertyType.HTML.value())) {
            propDef = new PropertyHtmlDefinitionImpl();
            propDef.setPropertyType(PropertyType.HTML);
        } else if (propertyType.equals(PropertyType.ID.value())) {
            propDef = new PropertyIdDefinitionImpl();
            propDef.setPropertyType(PropertyType.ID);
        } else if (propertyType.equals(PropertyType.INTEGER.value())) {
            propDef = new PropertyIntegerDefinitionImpl();
            propDef.setPropertyType(PropertyType.INTEGER);
        } else if (propertyType.equals(PropertyType.STRING.value())) {
            propDef = new PropertyStringDefinitionImpl();
            propDef.setPropertyType(PropertyType.STRING);
        } else {
            propDef = new PropertyUriDefinitionImpl();
            propDef.setPropertyType(PropertyType.URI);
        }

        return propDef;
    }

    /**
     * Load the {@link AbstractTypeDefinition} with the JSON parameters
     *
     * @param prop
     * @param jObj
     */
    protected void loadPropertyDef(AbstractPropertyDefinition prop, JSONObject jObj) {
        //String Required
        prop.setId((String) jObj.get(Parser.ID));
        prop.setLocalName((String) jObj.get(Parser.LOCAL_NAME));
        prop.setLocalNamespace((String) jObj.get(Parser.LOCAL_NAMESPACE));

        prop.setQueryName(
                (jObj.get(Parser.QUERY_NAME) != null) ?
                        (String) jObj.get(Parser.QUERY_NAME) :
                        (String) jObj.get(Parser.ID)
        );

        prop.setDisplayName(
                (jObj.get(Parser.DISPLAY_NAME) != null) ?
                        (String) jObj.get(Parser.DISPLAY_NAME) :
                        (String) jObj.get(Parser.ID)
        );
        prop.setDescription(
                (jObj.get(Parser.DESCRIPTION) != null) ?
                        (String) jObj.get(Parser.DESCRIPTION) :
                        ""
        );

        prop.setIsQueryable(
                jObj.get(Parser.IS_QUERYABLE) == null || (boolean) jObj.get(Parser.IS_QUERYABLE)
        );

        prop.setIsInherited(
                jObj.get(Parser.IS_INHERITED) == null || (boolean) jObj.get(Parser.IS_INHERITED)
        );

        prop.setIsOpenChoice(
                jObj.get(Parser.IS_OPEN_CHOICE) != null && (boolean) jObj.get(Parser.IS_OPEN_CHOICE)
        );

        prop.setIsRequired(
                jObj.get(Parser.IS_REQUIRED) != null && (boolean) jObj.get(Parser.IS_REQUIRED)
        );

        prop.setIsOrderable(
                jObj.get(Parser.IS_ORDERABLE) == null || (boolean) jObj.get(Parser.IS_ORDERABLE)
        );

    }

    /**
     * Define the implementation of {@link TypeDefinition}
     *
     * @param baseId the base ID of element type
     */
    protected AbstractTypeDefinition defineType(final String baseId) {
        AbstractTypeDefinition typeDef = null;

        //define the type of
        if (baseId.equals(BaseTypeId.CMIS_DOCUMENT.value())) {
            typeDef = new DocumentTypeDefinitionImpl();
            typeDef.setBaseTypeId(BaseTypeId.CMIS_DOCUMENT);
            typeDef.setIsFileable(true);
        } else if (baseId.equals(BaseTypeId.CMIS_FOLDER.value())) {
            typeDef = new FolderTypeDefinitionImpl();
            typeDef.setBaseTypeId(BaseTypeId.CMIS_FOLDER);
            typeDef.setIsFileable(false);
        } else if (baseId.equals(BaseTypeId.CMIS_ITEM.value())) {
            typeDef = new ItemTypeDefinitionImpl();
            typeDef.setBaseTypeId(BaseTypeId.CMIS_ITEM);
            typeDef.setIsFileable(false);
        } else {
            typeDef = new SecondaryTypeDefinitionImpl();
            typeDef.setBaseTypeId(BaseTypeId.CMIS_SECONDARY);
            typeDef.setIsFileable(false);
        }
        return typeDef;
    }

    /**
     * Populate the {@link AbstractTypeDefinition} object with the JSON content
     *
     * @param typeDef the type to be populated
     * @param jObj    the content used to populate the type
     */
    protected void loadTypeDef(AbstractTypeDefinition typeDef, final JSONObject jObj) {
        //String Required
        typeDef.setId((String) jObj.get(Parser.ID));
        typeDef.setLocalName((String) jObj.get(Parser.LOCAL_NAME));
        typeDef.setLocalNamespace((String) jObj.get(Parser.LOCAL_NAMESPACE));
        typeDef.setParentTypeId((String) jObj.get(Parser.PARENT_TYPE_ID));

        //string no required
        typeDef.setQueryName(
                (jObj.get(Parser.QUERY_NAME) != null) ?
                        (String) jObj.get(Parser.QUERY_NAME) :
                        (String) jObj.get(Parser.ID)
        );
        typeDef.setDisplayName(
                (jObj.get(Parser.DISPLAY_NAME) != null) ?
                        (String) jObj.get(Parser.DISPLAY_NAME) :
                        (String) jObj.get(Parser.ID)
        );
        typeDef.setDescription(
                (jObj.get(Parser.DESCRIPTION) != null) ?
                        (String) jObj.get(Parser.DESCRIPTION) :
                        ""
        );

        //booleans no required
        typeDef.setIsCreatable(
                jObj.get(Parser.IS_CREATEBLE) == null || (boolean) jObj.get(Parser.IS_CREATEBLE)
        );
        typeDef.setIsQueryable(
                jObj.get(Parser.IS_QUERYABLE) == null || (boolean) jObj.get(Parser.IS_QUERYABLE)
        );
        typeDef.setIsIncludedInSupertypeQuery(
                jObj.get(Parser.IS_INCLUDED_IN_SYPERTYPE_QUERY) == null ||
                        (boolean) jObj.get(Parser.IS_INCLUDED_IN_SYPERTYPE_QUERY)
        );
        typeDef.setIsFulltextIndexed(
                jObj.get(Parser.IS_FULL_TEXT_INDEXED) == null ||
                        (boolean) jObj.get(Parser.IS_FULL_TEXT_INDEXED)
        );
        typeDef.setIsControllableAcl(
                (jObj.get(Parser.IS_CONTROLLABLE_ACL) != null) &&
                        (boolean) jObj.get(Parser.IS_CONTROLLABLE_ACL)
        );
        typeDef.setIsControllablePolicy(
                (jObj.get(Parser.IS_CONTROLLABLE_POLICY) != null) &&
                        (boolean) jObj.get(Parser.IS_CONTROLLABLE_POLICY)
        );
    }
}