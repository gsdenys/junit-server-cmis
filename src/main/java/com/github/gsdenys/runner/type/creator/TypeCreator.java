package com.github.gsdenys.runner.type.creator;


import com.github.gsdenys.CmisInMemoryRunner;
import com.github.gsdenys.runner.utils.CmisUtils;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinition;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gsdenys on 09/02/17.
 */
public class TypeCreator {

    private CmisUtils cmisUtils;

    /**
     * Create a new document type
     *
     * @param typeDefinition the type definition
     */
    public void execute(TypeDefinition typeDefinition) {
        this.cmisUtils = new CmisUtils();

        Session session = this.cmisUtils.getSession(null);
        session.createType(typeDefinition);
    }


}
