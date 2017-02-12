package com.github.gsdenys.runner.type.creator;


import com.github.gsdenys.CmisInMemoryRunner;
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

    /**
     * Get CMIS {@link Session}
     *
     * @return Session cmis session
     */
    public Session getSession() {
        URI uri = CmisInMemoryRunner.getCmisURI();

        Map<String, String> parameter = new HashMap<>();
        parameter.put(SessionParameter.USER, "test");
        parameter.put(SessionParameter.PASSWORD, "test");
        parameter.put(SessionParameter.ATOMPUB_URL, uri.toString());
        parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
        parameter.put(SessionParameter.REPOSITORY_ID, "A1");
        //parameter.put(SessionParameter.FORCE_CMIS_VERSION, CmisVersion.CMIS_1_1.value());

        SessionFactory factory = SessionFactoryImpl.newInstance();
        return factory.createSession(parameter);
    }

    /**
     * Create a new document type
     *
     * @param typeDefinition the type definition
     */
    public void execute(TypeDefinition typeDefinition) {
        Session session = this.getSession();
        session.createType(typeDefinition);
    }


}
