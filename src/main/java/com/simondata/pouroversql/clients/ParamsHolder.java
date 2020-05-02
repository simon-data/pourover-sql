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

import com.simondata.pouroversql.writers.FileOutputFormat;

/**
 * SQLParams
 * SQLParams are provided to the SQL client and are used in setting up an initial connection.
 * They will be reused as long as the client is in use.
 *
 * @author Chet Mancini
 * @since   2019-03-31
 */
public class ParamsHolder {

    // private final static Logger logger = LoggerFactory.getLogger(ParamsHolder.class);

    private String extractorType;
    private ExtractorEngine extractorEngine;
    private SqlEngine sqlEngine;
    private SQLParams sqlParams;
    private SFTPParams sftpParams;
    private FormattingParams formattingParams;
    private String inputSql;
    private String inputSftpFile;
    private String outputFile;
    private FileOutputFormat outputFormat;
    private QueryParams queryParams;

    public ParamsHolder(String extractorType, SqlEngine sqlEngine, SQLParams sqlParams,
            SFTPParams sftpParams, FormattingParams formattingParams, String inputSql, String inputSftpFile,
            String outputFile, FileOutputFormat outputFormat, QueryParams queryParams) {
        this.extractorType = extractorType;
        this.extractorEngine = null;
        this.sqlEngine = sqlEngine;
        this.sqlParams = sqlParams;
        this.sftpParams = sftpParams;
        this.formattingParams = formattingParams;
        this.inputSql = inputSql;
        this.inputSftpFile = inputSftpFile;
        this.outputFile = outputFile;
        this.outputFormat = outputFormat;
        this.queryParams = queryParams;
    }

    // Skipping setting most variables for now
    public String getExtractorType() {
        return this.extractorType;
    }

    public ExtractorEngine getExtractorEngine() {
        return this.extractorEngine;
    }

    public void setExtractorEngine(ExtractorEngine engine) {
        this.extractorEngine = engine;
    }

    public SqlEngine getSqlEngine() {
        return this.sqlEngine;
    }

    public SQLParams getSqlParams() {
        return this.sqlParams;
    }

    public SFTPParams getSftpParams() {
        return this.sftpParams;
    }

    public FormattingParams getFormattingParams() {
        return this.formattingParams;
    }

    public String getInputSql() {
        return this.inputSql;
    }

    public String getInputSftpFile() {
        return this.inputSftpFile;
    }

    public String getOutputFile() {
        return this.outputFile;
    }

    public FileOutputFormat getOutputFormat() {
        return this.outputFormat;
    }

    public QueryParams getQueryParams() {
        return this.queryParams;
    }
}
