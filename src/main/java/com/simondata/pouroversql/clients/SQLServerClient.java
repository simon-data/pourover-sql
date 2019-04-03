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

import com.microsoft.sqlserver.jdbc.*;

import javax.sql.DataSource;

/**
 * <h1>SQLServerClient</h1>
 * Client for SQLServer
 */
public class SQLServerClient extends AbstractSQLClient {

    private static final int DEFAULT_PORT = 1433;
    private static final String DEFAULT_HOST = "localhost";

    /**
     * Constructor
     * @param params the SQLParams for the connection.
     */
    public SQLServerClient(SQLParams params) {
        super(params);
    }

    /**
     * Constructor.
     * @param params the SQLParams for the connection
     * @param queryParams the params for the queries.
     */
    public SQLServerClient(SQLParams params, QueryParams queryParams) {
        super(params, queryParams);
    }

    @Override
    public DataSource initDataSource() {
        SQLServerSQLParams params = SQLServerSQLParams.initEngineParams(this.params);
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(params.getUser());
        ds.setPassword(params.getPassword());
        ds.setServerName(params.getHost(DEFAULT_HOST));
        ds.setPortNumber(params.getPort(DEFAULT_PORT));
        ds.setDatabaseName(params.getDatabase());

        if (params.getEncrypt() != null) {
            ds.setEncrypt(params.getEncrypt());
        }
        if (params.getTrustServerCertificate() != null) {
            ds.setTrustServerCertificate(params.getTrustServerCertificate());
        }
        if (params.getTrustStore() != null) {
            ds.setTrustStore(params.getTrustStore());
        }
        if (params.getTrustStorePassword() != null) {
            ds.setTrustStorePassword(params.getTrustStorePassword());
        }
        if (params.getTrustStoreType() != null) {
            ds.setTrustStoreType(params.getTrustStoreType());
        }
        if (params.getAccessToken() != null) {
            ds.setAccessToken(params.getAccessToken());
        }
        if (params.getAuthentication() != null) {
            ds.setAuthentication(params.getAuthentication());
        }
        if (params.getAuthenticationScheme() != null) {
            ds.setAuthenticationScheme(params.getAuthenticationScheme());
        }
        if (params.getColumnEncryptionSetting() != null) {
            ds.setColumnEncryptionSetting(params.getColumnEncryptionSetting());
        }
        if (params.getFailoverPartner() != null) {
            ds.setFailoverPartner(params.getFailoverPartner());
        }
        if (params.getIntegratedSecurity() != null) {
            ds.setIntegratedSecurity(params.getIntegratedSecurity());
        }
        if (params.getHostNameInCertificate() != null) {
            ds.setHostNameInCertificate(params.getHostNameInCertificate());
        }
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "com.microsoft.sqlserver.jdbc.Driver";
    }

}
