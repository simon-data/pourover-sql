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

import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;

/**
 * <h1>MySQL Client</h1>
 * Provide facilities for querying MySQL or compatible databases
 * such as AWS Aurora or MariaDB
 *
 * @author  Chet Mancini
 * @since   2019-03-31
 */
public class MySQLClient extends AbstractSQLClient {

    private static final int DEFAULT_PORT = 3306;
    private static final String DEFAULT_HOST = "localhost";

    /**
     * Constructor
     * @param params the parameters to use for the connection.
     */
    public MySQLClient(SQLParams params) {
        super(params);
    }

    /**
     * Constructor.
     * @param params the params for the connection
     * @param queryParams the params for each query./
     */
    public MySQLClient(SQLParams params, QueryParams queryParams) {
        super(params, queryParams);
    }

    @Override
    protected DataSource initDataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUser(this.params.getUser());
        ds.setServerName(this.params.getHost(DEFAULT_HOST));
        ds.setPort(this.params.getPort(DEFAULT_PORT));
        ds.setPassword(this.params.getPassword());
        ds.setDatabaseName(this.params.getDatabase());
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "com.mysql.jdbc.Driver";
    }
}
