package com.github.gsdenys.runner.utils;

import com.github.gsdenys.CmisInMemoryRunner;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * com.github.gsdenys.runner.utils
 *
 * @author gsdenys
 * @version 0.0.0
 * @since 0.0.0
 */
public class CmisUtilsTest {
    private CmisUtils cmisUtils;

    @Before
    public void setUp() throws Exception {
        if(this.cmisUtils == null) {
            this.cmisUtils = new CmisUtils();
        }
    }

    @Test
    public void getSession() throws Exception {

        Session session = this.cmisUtils.getSession(null);

        RepositoryInfo info = session.getRepositoryInfo();
        Assert.assertNotNull("the info should not be null", info);
    }

}