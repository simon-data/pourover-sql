/*
  Copyright 2019-present, Simon Data, Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.simondata.pouroversql;

import com.simondata.pouroversql.clients.*;
import com.simondata.pouroversql.writers.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * SQLExtractor is the primary class to use and is designed for most use cases.
 */
public class SQLExtractor extends AbstractExtractor {

    private final SQLClient sqlClient;
    private FormattingParams formattingParams;
    private ParamsHolder paramsHolder;

    /**
     * Constructor
     * @param engine the engine to use
     * @param host the server to connect to.
     * @param port the server port to connect to.
     * @param username the user to connect as.
     * @param password the user's password to connect with.
     * @param database the database to use for the queries.
     */

    /**
     * Primary constructor
     * @param engine the SQLEngine to use
     * @param sqlParams the SQLParams to use when building the connection.
     * @param formattingParams the FormattingParams to use for formatting the output.
     */
    private SQLExtractor(SqlEngine engine, SQLParams sqlParams, FormattingParams formattingParams) {
        this.sqlClient = ClientFactory.makeSQLClient(engine, sqlParams);
        this.formattingParams = formattingParams;
    }

    public SQLExtractor(ParamsHolder paramsHolder) {
        this(paramsHolder.getSqlEngine(), paramsHolder.getSqlParams(), paramsHolder.getFormattingParams());
        this.paramsHolder = paramsHolder;
    }

    /**
     * Execute a query and return an arraylist of Maps
     * @param sql the SQL to query
     * @return An ArrayList of rows from the query result.
     */
    public List<Map<String, Object>> queryAsList(String sql) {
        return this.queryAsList(sql, QueryParams.getDefaultQueryParams());
    }

    /**
     * Execute a query and return an arraylist of Maps
     *
     * @param sql the SQL text to query.
     * @param queryParams parameters for the query itself.
     * @return ArrayList of rows for the query result.
     */
    public List<Map<String, Object>> queryAsList(String sql, QueryParams queryParams) {
        this.sqlClient.setQueryParams(queryParams);
        return this.sqlClient.queryAsList(sql);
    }

    /**
     * @param sql the SQL query text.
     * @param callback The callback function to invoke on each row.
     * @return Number of rows in the result set.
     */
    public Integer queryWithCallback(String sql, Function<Map<String, Object>, ?> callback) {
        return queryWithCallback(sql, callback, QueryParams.getDefaultQueryParams());
    }

    /**
     * @param sql the SQL querytext.
     * @param callback the callback to invoke for each row.
     * @param queryParams the parameters to use in the query process.
     * @return Number of rows in the resultset.
     */
    public Integer queryWithCallback(
            String sql,
            Function<Map<String, Object>, ?> callback,
            QueryParams queryParams
    ) {
        this.sqlClient.setQueryParams(queryParams);
        CallbackRowWriter writer = new CallbackRowWriter(callback);
        RowHandler rh = new RowHandler(writer, queryParams.getLogFrequency(), this.formattingParams);
        return this.sqlClient.queryWithHandler(sql, rh);
    }

    private static FileRowWriter getRowWriter(FileOutputFormat outputFormat) {
        if (outputFormat == FileOutputFormat.JSON) {
            return new JsonLRowWriter();
        } else if (outputFormat == FileOutputFormat.CSV) {
            return new CSVRowWriter();
        }
        return null;
    }

    /**
     * Query and send output to a file.
     * @param sql the SQL querytext.
     * @param file the output file object.
     * @param outputFormat the output format to write to (CSV/JSON)
     * @return the file output.
     */
    public File queryToFile(String sql, File file, FileOutputFormat outputFormat) {
        return this.queryToFile(
                sql,
                file,
                outputFormat,
                QueryParams.getDefaultQueryParams()
        );
    }

    /**
     * Query and send output to a file.
     * @param sql the SQL querytext.
     * @param file the output file object.
     * @param outputFormat the output file object.
     * @param queryParams the output format to write as (CSV/JSON)
     * @return the file written to.
     */
    public File queryToFile(
            String sql,
            File file,
            FileOutputFormat outputFormat,
            QueryParams queryParams
    ) {
        this.sqlClient.setQueryParams(queryParams);
        FileRowWriter writer = SQLExtractor.getRowWriter(outputFormat);
        try {
            writer.open(file);
            RowHandler rh = new RowHandler(writer, queryParams.getLogFrequency(), this.formattingParams);
            this.sqlClient.queryWithHandler(sql, rh);
        } finally {
            writer.close();
        }
        return file;
    }

    public void extract() {
        this.extract(
            this.paramsHolder.getInputSql(), this.paramsHolder.getOutputFile(),
            this.paramsHolder.getOutputFormat(), this.paramsHolder.getQueryParams()
        );
    }

    private void extract(String inputSql, String outputFile,
            FileOutputFormat outputFormat, QueryParams queryParams) {
        queryToFile(inputSql, new File(outputFile), outputFormat, queryParams);
    }
}
