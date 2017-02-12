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
import com.github.gsdenys.runner.type.parser.ParserException;
import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinition;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.AbstractPropertyDefinition;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.AbstractTypeDefinition;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to parse the JSON document type file
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 2.0.0
 * @since 2.0.0
 */
public class JsonParser extends AbstractJsonParser implements Parser {

    @Override
    public List<TypeDefinition> getTypes(InputStream is) throws ParserException {
        List<TypeDefinition> typeDefList = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(is, "UTF-8"));

            JSONArray array = (JSONArray) jsonObject.get("types");
            for (int i = 0; i < array.size(); i++) {
                TypeDefinition def = this.loadType(array.get(i));
                typeDefList.add(def);
            }
        } catch (IOException e) {
            throw new ParserException("IOException", e.getCause());
        } catch (ParseException e) {
            throw new ParserException("The JSON contain erros", e.getCause());
        }

        return typeDefList;
    }

    @Override
    public <E> TypeDefinition loadType(E obj) throws ParserException {
        JSONObject object = (JSONObject) obj;

        AbstractTypeDefinition typeDef = null;
        String baseId = (String) object.get(Parser.TYPE_BASE_ID);

        typeDef = this.defineType(baseId);
        this.loadTypeDef(typeDef, object);

        Object props = object.get(Parser.PROPERTY_DEFINITIONS);

        if (props != null && !((JSONArray) props).isEmpty()) {
            //load a list of properties definitions
            List<PropertyDefinition> propertyDefinitionList = this.getProperties(props);
            //iterate over property definition list and add each one to type
            propertyDefinitionList.forEach(typeDef::addPropertyDefinition);
        }

        return typeDef;
    }

    @Override
    public <E> List<PropertyDefinition> getProperties(E obj) throws ParserException {
        JSONArray array = (JSONArray) obj;

        List<PropertyDefinition> list = new ArrayList<>();

        for (Object propJ : array) {
            list.add(this.loadProperty(propJ));
        }

        return list;
    }

    @Override
    public <E> PropertyDefinition loadProperty(E obj) throws ParserException {
        JSONObject object = (JSONObject) obj;

        String propertyType = (String) object.get(Parser.PROPERTY_TYPE);

        AbstractPropertyDefinition propDef = this.definePropertyType(propertyType);
        super.loadPropertyDef(propDef, object);

        return propDef;
    }
}