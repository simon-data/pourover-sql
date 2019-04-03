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

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

/**
 * <h1>PostgreSQLClient</h1>
 * Provide facilities for querying to postgres.
 *
 * @author  Chet Mancini
 * @since   2019-03-31
 */
public class PostgreSQLClient extends AbstractSQLClient {

    private static final int DEFAULT_PORT = 5432;
    private static final String DEFAULT_HOST = "localhost";

    /**
     * Constructor
     * @param params SQLParams for the connection.
     */
    public PostgreSQLClient(SQLParams params) {
        super(params);
    }

    /**
     * Constructor
     * @param params the SQLParams for the connection.
     * @param queryParams the default params for each query.
     */
    public PostgreSQLClient(SQLParams params, QueryParams queryParams) {
        super(params, queryParams);
    }

    @Override
    public DataSource initDataSource() {
        /*
        TODO can use connection pooling in future.
         */
        PostgreSQLSQLParams postgresParams = PostgreSQLSQLParams.initEngineParams(this.params);
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUser(postgresParams.getUser());
        ds.setServerName(postgresParams.getHost(DEFAULT_HOST));
        ds.setPassword(postgresParams.getPassword());
        ds.setDatabaseName(postgresParams.getDatabase());
        ds.setPortNumber(postgresParams.getPort(DEFAULT_PORT));
        ds.setReadOnly(this.queryParams.getReadOnly());
        ds.setDefaultRowFetchSize(this.queryParams.getFetchSize());

        // Custom params
        if (postgresParams.getSsl() != null) {
            ds.setSsl(postgresParams.getSsl());
        }
        if (postgresParams.getBinaryTransfer() != null) {
            ds.setBinaryTransfer(postgresParams.getBinaryTransfer());
        }
        if (postgresParams.getKerberosServerName() != null) {
            ds.setKerberosServerName(postgresParams.getKerberosServerName());
        }
        if (postgresParams.getSslCert() != null) {
            ds.setSslCert(postgresParams.getSslCert());
        }
        if (postgresParams.getSslKey() != null) {
            ds.setSslKey(postgresParams.getSslKey());
        }
        if (postgresParams.getSslMode() != null) {
            ds.setSslMode(postgresParams.getSslMode());
        }
        if (postgresParams.getSslPassword() != null) {
            ds.setSslPassword(postgresParams.getSslPassword());
        }
        if (postgresParams.getSslRootCert() != null) {
            ds.setSslRootCert(postgresParams.getSslRootCert());
        }
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "org.postgresql.jdbc.Driver";
    }

}
