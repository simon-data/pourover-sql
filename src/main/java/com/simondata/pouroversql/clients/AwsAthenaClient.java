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

import javax.sql.DataSource;

/**
 * <h1>Athena Client</h1>
 * Provide facilities for querying AWS Athena
 *
 * This client is in DEVELOPMENT and has not yet been tested in production.
 *
 * @author  Chet Mancini
 * @version 1.0
 * @since   2019-03-31
 */
public class AwsAthenaClient extends AbstractSQLClient {

    /**
     * Constructor
     * @param params SQLParams the params for the connection.
     */
    public AwsAthenaClient(SQLParams params) {
        super(params);
    }

    private String getUrl() {
        return "jdbc:awsathena://athena.{REGION}.amazonaws.com:443";
    }

    @Override
    protected DataSource initDataSource() {
        com.simba.athena.jdbc.DataSource ds = new com.simba.athena.jdbc.DataSource();
        ds.setUserID(this.params.getUser());
        ds.setURL(this.params.getHost());
        ds.setPassword(this.params.getPassword());
//        ds.setDatabaseName(this.params.getDatabase());
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "com.simba.athena.jdbc.Driver";
    }
}
