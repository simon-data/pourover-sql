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
package com.simondata.pouroversql;

import com.simondata.pouroversql.clients.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ExtractorFactory
 *
 * Factory class to build the correct type of Extractor based on input.
 */
public class ExtractorFactory {
    private static final Logger logger = LoggerFactory.getLogger(ExtractorFactory.class);

    public static AbstractExtractor makeExtractor(
            String extractorType, SqlEngine sqlEngine, SQLParams sqlParams, SFTPParams sftpParams) {
        ExtractorEngine extractorEngine = ExtractorEngine.byName(extractorType);
        return ExtractorFactory.makeExtractor(extractorEngine, sqlEngine, sqlParams, sftpParams);
    }

    /**
     * Factory method to build an extractor by engine type.
     * @param extractorEngine the type of extractor to build
     * @param sqlEngine the type of sqlEngine to build
     * @param sqlParams SQLParams to put in the client
     * @param sftpParams SFTPParams to put in the client
     * @return SQLClient
     */
    public static AbstractExtractor makeExtractor(
            ExtractorEngine extractorEngine, SqlEngine sqlEngine, SQLParams sqlParams, SFTPParams sftpParams) {
        AbstractExtractor extractor = null;
        switch (extractorEngine) {
            case SFTP:
                extractor = new SFTPExtractor(sftpParams);
                break;
            case SQL:
                extractor = new SQLExtractor(sqlEngine, sqlParams);
                break;
            default:
                logger.error("Extraction type not supported.");
                break;
        }
        return extractor;
    }
}
