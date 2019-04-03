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

import java.util.Properties;

/**
 * PostgreSQLSQLParams
 * Use in place of SQLParams when working with PostgreSQL.
 */
public class PostgreSQLSQLParams extends SQLParams {
    private Boolean ssl;
    private Boolean binaryTransfer;
    private String kerberosServerName;
    private String sslCert;
    private String sslKey;
    private String sslMode;
    private String sslPassword;
    private String sslRootCert;

    /**
     * Base Constructor
     * @param host server host. Defaults to localhost.
     * @param port server port. Defaults to postgres port.
     * @param user database username.
     * @param password database user's password.
     * @param database the database to use.
     */
    public PostgreSQLSQLParams(String host, Integer port, String user, String password, String database) {
        super(host, port, user, password, database);
    }

    /**
     * Constructor with custom properties.
     * @param host server host. Defaults to localhost.
     * @param port server port. Defaults to postgres port.
     * @param user database username.
     * @param password database user's password.
     * @param database the database to use.
     * @param customProperties the custom properties to use in the connection.
     */
    public PostgreSQLSQLParams(
            String host,
            Integer port,
            String user,
            String password,
            String database,
            Properties customProperties) {
        super(host, port, user, password, database, customProperties);
    }

    /**
     * init engine params
     * @param params existing SQLParams for a connection
     * @return SQLParams of type PostgresSQL with the properties all setup.
     */
    public static PostgreSQLSQLParams initEngineParams(SQLParams params) {
        return new PostgreSQLSQLParams(
                params.getHost(),
                params.getPort(),
                params.getUser(),
                params.getPassword(),
                params.getDatabase(),
                params.getCustomProperties()
        );
    }

    /**
     * Get SSL parameter. Boolean.
     * @return whether to use SSL
     */
    public Boolean getSsl() {
        return getCustomBooleanParameter(this.ssl, "ssl");
    }

    /**
     * Get binary transfer parameter. Boolean
     * @return whether to use binary transfer.
     */
    public Boolean getBinaryTransfer() {
        return getCustomBooleanParameter(this.binaryTransfer, "binaryTransfer");
    }

    /**
     * Get Kerberos Server Name. String.
     * @return Kerberos server name.
     */
    public String getKerberosServerName() {
        return getCustomStringParameter(this.kerberosServerName, "kerberosServerName");
    }

    /**
     * Get SSL cert. String.
     * @return SSL cert
     */
    public String getSslCert() {
        return getCustomStringParameter(this.sslCert, "sslCert");
    }

    /**
     * SSL Key. String
     * @return SSL Key
     */
    public String getSslKey() {
        return getCustomStringParameter(this.sslKey, "sslKey");
    }

    /**
     * SSL Mode. String.
     * @return SSL mode
     */
    public String getSslMode() {
        return getCustomStringParameter(this.sslMode, "sslMode");
    }

    /**
     * SSL password. String.
     * @return SSL password
     */
    public String getSslPassword() {
        return getCustomStringParameter(this.sslMode, "sslPassword");
    }

    /**
     * Get SSL Root cert.
     * @return SSL Root cert.
     */
    public String getSslRootCert() {
        return getCustomStringParameter(this.sslRootCert, "sslRootCert");
    }

}
