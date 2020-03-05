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
 * <h1>QueryParams</h1>
 * QueryParms are parameters that are passed at query-time.
 * They control the actual querying mechanism, such as number
 * of rows to retrieve at a time.
 */
public class QueryParams implements InputParams {

    private final static Logger logger = LoggerFactory.getLogger(InputParams.class);

    private static final Integer DEFAULT_FETCH_SIZE = 10000;
    private static final Integer DEFAULT_LOG_FREQUENCY = 100_000;

    private Integer fetchSize = null;
    private Integer maxRows = null;
    private Integer timeout = null;
    private Integer logFrequency = null;

    /**
     * Todo make optional.
     */
    private Boolean readOnly = true;


    public QueryParams() {
        this.fetchSize = DEFAULT_FETCH_SIZE;
        this.logFrequency = DEFAULT_LOG_FREQUENCY;
    }

    /**
     * Constructor
     * @param fetchSize the number of rows to fetch in each batch
     * @param maxRows the maximum number of rows to retrieve.
     * @param timeout the timeout period on the query in seconds.
     */
    public QueryParams(Integer fetchSize, Integer maxRows, Integer timeout) {
        this.fetchSize = fetchSize;
        this.maxRows = maxRows;
        this.timeout = timeout;
    }

    /**
     * Get setting for fetch size
     * @return the number of rows to retrieve per batch.
     */
    public Integer getFetchSize() {
        if (this.fetchSize == null) {
            return DEFAULT_FETCH_SIZE;
        } else {
            return this.fetchSize;
        }
    }

    /**
     * Get setting for maxrows
     * @return the maximum rows to return from a query.
     */
    public Integer getMaxRows() {
        return this.maxRows;
    }

    /**
     * Get setting for timeout.
     * @return the maximum time a query will wait for result (seconds)
     */
    public Integer getTimeout() {
        return this.timeout;
    }

    /**
     * Get setting for readonly.
     * @return whether this query is readonly.
     */
    public Boolean getReadOnly() {
        return this.readOnly;
    }

    /**
     * Get setting for how often to print rows retrieved.
     * @return how often to log rowcount.
     */
    public Integer getLogFrequency() {
        if (this.logFrequency == null) {
            return this.logFrequency;
        } else {
            return DEFAULT_LOG_FREQUENCY;
        }
    }

    /**
     * Get default query params
     * @return a Queryparams that represents default settings.
     */
    public static QueryParams getDefaultQueryParams() {
        return new QueryParams();
    }

    @Override
    public void logValues() {
        logger.info("Query Max Rows: " + this.getMaxRows());
        logger.info("Query Timeout: " + this.getTimeout());
        logger.info("Query Fetch Size: " + this.getFetchSize());
    }
}
