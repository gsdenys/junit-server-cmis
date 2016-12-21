/*
 * Copyright 2016 gsdenys@gmail.com
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
package com.github.gsdenys;

import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Complementar test case for chemistry cmis connection
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@RunWith(CmisInMemoryRunner.class)
public class ChemistryCmisTest {

    private Map<String, String> parameter;
    private SessionFactory factory;

    @Before
    public void setUp() throws Exception {
        this.factory = SessionFactoryImpl.newInstance();

        if (this.parameter == null) {
            this.parameter = new HashMap<>();

            this.parameter.put(SessionParameter.USER, "test");
            this.parameter.put(SessionParameter.PASSWORD, "test");
            this.parameter.put(SessionParameter.ATOMPUB_URL, "http://localhost:8080/cmis/atom");
            this.parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
            this.parameter.put(SessionParameter.REPOSITORY_ID, "A1");
        }
    }

    @Test
    public void getSession() throws Exception {
        Session session = factory.createSession(this.parameter);

        Assert.assertNotNull("The Session Should not be null.", session);
    }

    @Test
    public void listRepositories() throws Exception {
        List<Repository> repositoryList = factory.getRepositories(parameter);

        Assert.assertNotNull("The repository List should not be null.", repositoryList);
        Assert.assertFalse("The repository list should not be empty", repositoryList.isEmpty());
        Assert.assertEquals("The number of repositories should be 1.", repositoryList.size(), 1);
    }

    @Test
    public void listDocuments() throws Exception {
        Session session = factory.createSession(this.parameter);

        String queryDoc = "Select * from cmis:document";

        ItemIterable<QueryResult> docItems = session.query(queryDoc, false);

        Assert.assertNotNull("The document list should not be null", docItems);
        Assert.assertEquals("The number of documents should be 21", docItems.getTotalNumItems(), 21);
    }

    @Test
    public void listFolder() throws Exception {
        Session session = factory.createSession(this.parameter);

        String queryDoc = "Select * from cmis:folder";

        ItemIterable<QueryResult> folderItems = session.query(queryDoc, false);

        Assert.assertNotNull("The folder list should not be null", folderItems);
        Assert.assertEquals("The number of documents should be 15", folderItems.getTotalNumItems(), 15);
    }
}