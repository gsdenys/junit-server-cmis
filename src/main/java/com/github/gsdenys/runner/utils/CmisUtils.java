package com.github.gsdenys.runner.utils;

import com.github.gsdenys.CmisInMemoryRunner;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * com.github.gsdenys.runner.utils
 *
 * @author gsdenys
 * @version 0.0.0
 * @since 0.0.0
 */
public class CmisUtils {

    /**
     * Get CMIS {@link Session}
     *
     * @return Session cmis session
     */
    public Session getSession(String repositoryID) {
        URI uri = CmisInMemoryRunner.getCmisURI();

        Map<String, String> parameter = new HashMap<>();
        parameter.put(SessionParameter.USER, "test");
        parameter.put(SessionParameter.PASSWORD, "test");
        parameter.put(SessionParameter.ATOMPUB_URL, uri.toString());
        parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
        parameter.put(SessionParameter.REPOSITORY_ID, (
                repositoryID == null)? "A1" : repositoryID
        );

        SessionFactory factory = SessionFactoryImpl.newInstance();
        return factory.createSession(parameter);
    }
}
