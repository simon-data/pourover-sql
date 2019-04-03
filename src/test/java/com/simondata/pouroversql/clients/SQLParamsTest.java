/*
Copyright 2019-present, Simon Data, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at:
http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.simondata.pouroversql.clients;

import org.junit.Test;

import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class SQLParamsTest {

    @Test
    public void testConstructor() {
        String host = "fake.database";
        Integer port = 999;
        String user = "admin";
        String password = "******";
        String database = "main";
        SQLParams queryParams = new SQLParams(host, port, user, password, database);
        assertEquals(host, queryParams.getHost());
        assertEquals(port, queryParams.getPort());
        assertEquals(user, queryParams.getUser());
        assertEquals(password, queryParams.getPassword());
        assertEquals(database, queryParams.getDatabase());
    }

    @Test
    public void testGetWithDefaultsIfNull() {
        String host = null;
        Integer port = null;
        String user = null;
        String password = null;
        String database = null;
        SQLParams queryParams = new SQLParams(host, port, user, password, database);
        assertEquals("localhost", queryParams.getHost("localhost"));
        assertEquals((Integer) 1234, queryParams.getPort(1234));
    }

    @Test
    public void testGetWithDefaultsIfSet() {
        String host = "fake.database";
        Integer port = 999;
        String user = "admin";
        String password = "******";
        String database = "main";
        SQLParams queryParams = new SQLParams(host, port, user, password, database);
        assertEquals(host, queryParams.getHost("localhost"));
        assertEquals(port, queryParams.getPort(1234));
    }

    @Test
    public void testGetProperties() {
        String host = "fake.database";
        Integer port = 999;
        String user = "admin";
        String password = "******";
        String database = "main";
        Properties props = new Properties();
        props.setProperty("testInt", "1234");
        props.setProperty("testBoolean", "false");
        props.setProperty("testStr", "val");

        SQLParams queryParams = new SQLParams(host, port, user, password, database, props);
        assertEquals((Integer) 1234, queryParams.getPropertyAsInteger("testInt"));
        assertFalse(queryParams.getPropertyAsBoolean("testBoolean"));
        assertEquals("val", queryParams.getPropertyAsString("testStr"));
    }
}
