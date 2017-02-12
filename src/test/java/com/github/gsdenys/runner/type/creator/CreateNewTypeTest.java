package com.github.gsdenys.runner.type.creator;

import com.github.gsdenys.CmisInMemoryRunner;
import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.PropertyType;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.DocumentTypeDefinitionImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.PropertyBooleanDefinitionImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gsdenys on 11/02/17.
 */
@RunWith(CmisInMemoryRunner.class)
public class CreateNewTypeTest {
    private TypeCreator createNewType;

    @Before
    public void setUp() throws Exception {
        if (this.createNewType == null) {
            this.createNewType = new TypeCreator();
        }
    }

    @Test
    public void execute() throws Exception {
        DocumentTypeDefinitionImpl docDef = new DocumentTypeDefinitionImpl();

        docDef.setId("tst:doctype");
        docDef.setBaseTypeId(BaseTypeId.CMIS_DOCUMENT);
        docDef.setDescription("a new type");
        docDef.setDisplayName("Test Document Type");
        docDef.setLocalName("Doc Type");
        docDef.setParentTypeId("cmis:document");

        Map<String, PropertyDefinition<?>> propertyDefinitions = new HashMap<>();

        PropertyBooleanDefinitionImpl booleanDefinition = new PropertyBooleanDefinitionImpl();
        booleanDefinition.setId("tst:boolean");
        booleanDefinition.setLocalName("boolean");
        booleanDefinition.setDisplayName("Test Boolean");
        booleanDefinition.setDescription("A boolean test");
        booleanDefinition.setLocalNamespace("tst");
        booleanDefinition.setPropertyType(PropertyType.BOOLEAN);
        booleanDefinition.setQueryName("tst:boolean");

        propertyDefinitions.put(booleanDefinition.getId(), booleanDefinition);
        docDef.setPropertyDefinitions(propertyDefinitions);


        this.createNewType.execute(docDef);
    }

}