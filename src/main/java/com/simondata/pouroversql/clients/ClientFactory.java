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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClientFactory
 *
 * Factory class to build the correct type of SQLClient based on input.
 */
public class ClientFactory {
    private static final Logger logger = LoggerFactory.getLogger(ClientFactory.class);

    public static SQLClient makeSQLClient(String sqlType, SQLParams params) {
        SqlEngine engine = SqlEngine.byName(sqlType);
        return ClientFactory.makeSQLClient(engine, params);
    }

    /**
     * Factory method to build a client by engine type.
     * @param engine the type of client to build
     * @param params SQLParams to put in the client
     * @return SQLClient
     */
    public static SQLClient makeSQLClient(SqlEngine engine, SQLParams params) {
        SQLClient client = null;
        switch (engine) {
            case ATHENA:
                client = new AwsAthenaClient(params);
                break;
            case SQLSERVER:
                client = new SQLServerClient(params);
                break;
            case MYSQL:
                client = new MySQLClient(params);
                break;
            case POSTGRESQL:
                client = new PostgreSQLClient(params);
                break;
            case REDSHIFT:
                client = new RedshiftClient(params);
                break;
            case ORACLE:
                logger.error("Oracle uses logins to retrieve artifacts. Customize your own.");
                break;
            case INFORMIX:
                client = new InformixSQLClient(params);
                break;
            case SNOWFLAKE:
                client = new SnowflakeClient(params);
                break;
            case BIGQUERY:
                logger.error("Not supported yet.");
                break;
            default:
                logger.error("DB type not supported.");
                break;
        }
        return client;
    }
}
