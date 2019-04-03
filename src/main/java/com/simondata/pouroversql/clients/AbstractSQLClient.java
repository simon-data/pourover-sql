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

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.StatementConfiguration;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.simondata.pouroversql.writers.RowHandler;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * <h1>AbstractSQLClient</h1>
 * Subclass for all different engine types
 */
public abstract class AbstractSQLClient implements SQLClient {

    protected SQLParams params;
    protected QueryParams queryParams;

    private final Logger logger = LoggerFactory.getLogger(SQLClient.class);

    /**
     * Constructor
     * @param params SQLParams to setup the connection.
     */
    AbstractSQLClient(SQLParams params) {
        this.params = params;
        this.queryParams = QueryParams.getDefaultQueryParams();
    }

    /**
     * Constructor with a default set of queryparams for future queries
     * @param params SQLParams to setup the connection.
     * @param queryParams QueryParams for future queries.
     */
    AbstractSQLClient(SQLParams params, QueryParams queryParams) {
        this.params = params;
        this.queryParams = queryParams;
    }

    abstract protected DataSource initDataSource();

    abstract protected String getDriverName();

    @Override
    public void setQueryParams(QueryParams queryParams) {
        this.queryParams = queryParams;
    }

    /**
     * Builds the StatementConfiguration based on the
     * provided QueryParams.
     * @return StatementConfiguration
     */
    private StatementConfiguration buildStatementConfiguration() {
        return new StatementConfiguration.Builder()
                .fetchSize(this.queryParams.getFetchSize())
                .queryTimeout(this.queryParams.getTimeout())
                .maxRows(this.queryParams.getMaxRows())
                .build();
    }

    /**
     * Base StatementConfiguration
     * @return StatementConfiguration
     */
    private StatementConfiguration getDefaultStatementConfiguration(){
        return new StatementConfiguration.Builder().build();
    }

    @Override
    public List<Map<String, Object>> queryAsList(String queryText) {
        logger.debug("Querying for: " + queryText);
        try {
            DbUtils.loadDriver(this.getDriverName());
            DataSource ds = this.initDataSource();
            StatementConfiguration sc = this.buildStatementConfiguration();
            QueryRunner queryRunner = new QueryRunner(ds, sc);
            MapListHandler handler = new MapListHandler();
            return queryRunner.query(queryText, handler);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int queryWithHandler(String queryText, RowHandler handler) {
        logger.debug("Querying for: " + queryText);
        try {
            DbUtils.loadDriver(this.getDriverName());
            DataSource ds = this.initDataSource();
            StatementConfiguration sc = this.buildStatementConfiguration();
            CustomQueryRunner cqr = new CustomQueryRunner(ds, sc, handler);
            return cqr.query(queryText);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

}
