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
import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinition;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * Created by gsdenys on 11/02/17.
 */
public class DefaultParserTest {

    private InputStream is;
    private Parser parser;

    private JSONObject typeJson = null;

    @Before
    public void setUp() throws Exception {
        if (this.is == null) {
            this.is = this.getClass().getResourceAsStream("/cmis-type.json");
        }

        if (this.parser == null) {
            this.parser = new JsonParser();
        }

        if(typeJson == null) {
            this.typeJson = (JSONObject) JSONValue.parse("{\n" +
                    "      \"id\" : \"tst:doc\",\n" +
                    "      \"localName\" : \"Test Document\",\n" +
                    "      \"localNamespace\" : \"tst\",\n" +
                    "      \"queryName\" : \"tst:doc\",\n" +
                    "      \"displayName\" : \"Test Document\",\n" +
                    "      \"description\" : \"The document type used by test\",\n" +
                    "      \"baseId\" : \"cmis:document\",\n" +
                    "      \"parentTypeId\" : \"cmis:document\",\n" +
                    "      \"propertyDefinitions\" : [\n" +
                    "        {\n" +
                    "          \"id\" : \"tst:booleanX\",\n" +
                    "          \"localName\" : \"booleanX\",\n" +
                    "          \"localNamespace\" : \"tst\",\n" +
                    "          \"displayName\" : \"New Boolean\",\n" +
                    "          \"description\" : \"A New Booolean\",\n" +
                    "          \"queryName\" : \"tst:booleanX\",\n" +
                    "          \"propertyType\" : \"boolean\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\" : \"tst:stringX\",\n" +
                    "          \"localName\" : \"stringX\",\n" +
                    "          \"localNamespace\" : \"tst\",\n" +
                    "          \"displayName\" : \"New String\",\n" +
                    "          \"propertyType\" : \"string\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }");
        }
    }

    @Test
    public void getTypes() throws Exception {
        List<TypeDefinition> typeDefinitionList = parser.getTypes(this.is);

        Assert.assertNotNull("The list shold not be null", typeDefinitionList);
        Assert.assertFalse("The list should not be empty", typeDefinitionList.isEmpty());
    }

    @Test
    public void loadType() throws Exception {
        TypeDefinition def = this.parser.loadType(this.typeJson);

        Assert.assertNotNull("The type definition should not be null", def);
        Assert.assertEquals("The query name should be 'tst:doc'", def.getQueryName(), "tst:doc");
        Assert.assertEquals(
                "The type definition should have 2 properties definitions",
                def.getPropertyDefinitions().size(),
                2
        );
    }

    @Test
    public void getProperties() throws Exception {
        JSONArray array = (JSONArray) this.typeJson.get("propertyDefinitions");

        List<PropertyDefinition> propList = this.parser.getProperties(array);

        Assert.assertNotNull("the property definition list should not be null");
        Assert.assertEquals("the property definition list should have 2 elements", propList.size(), 2);
    }

    @Test
    public void loadProperty() throws Exception {
        JSONArray array = (JSONArray) this.typeJson.get("propertyDefinitions");
        JSONObject jObj = (JSONObject) array.get(0);

        PropertyDefinition pDef = this.parser.loadProperty(jObj);

        Assert.assertNotNull("The PropertyDefinition should not be null", pDef);
        Assert.assertEquals("The local name should be 'booleanX'", pDef.getLocalName(), "booleanX");
    }

}