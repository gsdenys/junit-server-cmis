package com.github.gsdenys.runner.type.parser;

import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinition;

import java.io.InputStream;
import java.util.List;

/**
 * Created by gsdenys on 09/02/17.
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





    List<TypeDefinition> getTypes(InputStream is) throws ParserException;

    <E> TypeDefinition loadType(E obj) throws ParserException;

    <E> List<PropertyDefinition> getProperties(E obj) throws ParserException;

    <E> PropertyDefinition loadProperty(E obj) throws ParserException;

}
