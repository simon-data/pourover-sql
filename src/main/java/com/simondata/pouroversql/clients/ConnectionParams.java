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

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * SQLParams
 * SQLParams are provided to the SQL client and are used in setting up an initial connection.
 * They will be reused as long as the client is in use.
 *
 * @author Chet Mancini
 * @since   2019-03-31
 */
public class ConnectionParams implements InputParams {

    private final static Logger logger = LoggerFactory.getLogger(InputParams.class);

    private String host;
    private Integer port;
    private String user;
    private String password;
    private Properties customProperties;

    public ConnectionParams(String host, Integer port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.customProperties = new Properties();
    }

    public ConnectionParams(
            String host, Integer port, String user, String password, Properties customProperties) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.customProperties = customProperties;
    }

    public ConnectionParams(ConnectionParams params) {
        this.host = params.getHost();
        this.port = params.getPort();
        this.user = params.getUser();
        this.password = params.getPassword();
        this.customProperties = params.getCustomProperties();
    }

    protected String getCustomStringParameter(String instanceVar, String key) {
        if (instanceVar != null) {
            return instanceVar;
        } else {
            return this.getCustomProperties().getProperty(key);
        }
    }

    protected Boolean getCustomBooleanParameter(Boolean instanceVar, String key) {
        if (instanceVar != null) {
            return instanceVar;
        } else {
            return getPropertyAsBoolean(key);
        }
    }

    public String getHost() {
        return this.host;
    }

    public String getHost(String defaultHost) {
        return defaultIfNull(this.host, defaultHost);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public Integer getPort(int defaultPort) {
        return defaultIfNull(this.port, defaultPort);
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getCustomProperties() {
        return this.customProperties;
    }

    public boolean hasProperty(String propertyName) {
        return this.customProperties.containsKey(propertyName);
    }

    public Boolean getPropertyAsBoolean(String propertyName) {
        return BooleanUtils.toBooleanObject(this.customProperties.getProperty(propertyName));
    }

    public Integer getPropertyAsInteger(String propertyName) {
        String prop = this.customProperties.getProperty(propertyName);
        if (prop == null) {
            return null;
        } else {
            return Integer.parseInt(prop);
        }
    }

    public String getPropertyAsString(String propertyName) {
        return this.customProperties.getProperty(propertyName);
    }

    public java.util.Set<String> getStringPropertyNames() {
        return this.customProperties.stringPropertyNames();
    }

    @Override
    public void logValues() {
        logger.info("User: " + this.getUser());
        logger.info("Password: <not shown>");
        logger.info("Port: " + this.getPort());
        for (String name : this.getStringPropertyNames()) {
            logger.info(name + ":" + this.getPropertyAsString(name));
        }
    }
}
