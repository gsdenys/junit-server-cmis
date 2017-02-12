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
package com.github.gsdenys.runner;

import com.github.gsdenys.CmisInMemoryRunner;
import com.github.gsdenys.runner.type.creator.TypeCreator;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.enums.CmisVersion;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Test for the {@link CmisInMemoryRunner} class
 *
 * @author Denys G. Santos (gsdenys@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@RunWith(CmisInMemoryRunner.class)
@Configure(
        port = 9191,
        cmisVersion = CmisVersion.CMIS_1_1,
        docTypes = {
                @TypeDescriptor(
                        file = "cmis-type.json",
                        loader = TypeLoader.JSON
                )
        }
)
public class ConfigureTest {

    @Test
    public void testConnection() throws Exception {
        URI uri = CmisInMemoryRunner.getCmisURI();
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        Assert.assertEquals("The Response code should be 200", conn.getResponseCode(), 200);

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        Assert.assertNotNull("The return should be null", br.readLine());

        conn.disconnect();
    }

    @Test
    public void checkDocumentType() throws Exception {
        TypeCreator creator = new TypeCreator();

        Session session = creator.getSession();

        ObjectType objType = session.getTypeDefinition("tst:doc");

        Assert.assertNotNull("The Object type should not be null", objType);
        Assert.assertEquals(
                "The object type local namespace should be 'tst'",
                objType.getLocalNamespace(),
                "tst"
        );

    }
}