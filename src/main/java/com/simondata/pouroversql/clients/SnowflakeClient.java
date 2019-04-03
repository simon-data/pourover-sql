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
import net.snowflake.client.jdbc.SnowflakeBasicDataSource;

/**
 * <h1>PostgreSQL Client</h1>
 * Provide facilities for querying Snowflake.
 *
 * This client is in DEVELOPMENT and has not yet been tested in production.
 *
 * @author  Chet Mancini
 * @since   2019-03-31
 */
public class SnowflakeClient extends AbstractSQLClient {

    /**
     * Constructor
     * @param params SQLParams for the connection
     */
    SnowflakeClient(SQLParams params) {
        super(params);
    }

    /**
     * Constructor
     * @param params SQLParams for the connection
     * @param queryParams QueryParams to default to future queries.
     */
    SnowflakeClient(SQLParams params, QueryParams queryParams) {
        super(params, queryParams);
    }

    @Override
    protected DataSource initDataSource() {
        SnowflakeBasicDataSource snowflakeDS = new SnowflakeBasicDataSource();
        snowflakeDS.setDatabaseName(this.params.getDatabase());
        snowflakeDS.setUser(this.params.getUser());
        snowflakeDS.setPassword(this.params.getPassword());
        snowflakeDS.setPortNumber(this.params.getPort());
        return snowflakeDS;
    }

    @Override
    protected String getDriverName() {
        return "net.snowflake.client.jdbc.SnowflakeDriver";
    }
}
