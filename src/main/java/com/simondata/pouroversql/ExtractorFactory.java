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

/**
 * Factory to create Extractor instances
 */
public class ExtractorFactory {

    public static AbstractExtractor create(ParamsHolder paramsHolder) {
        ExtractorEngine extractorEngine = ExtractorEngine.byName(paramsHolder.getExtractorType());
        paramsHolder.setExtractorEngine(extractorEngine);
        return ExtractorFactory.makeExtractor(extractorEngine, paramsHolder);
    }

    /**
     * Factory method to build an extractor by engine type.
     * @param extractorEngine the type of extractor to build
     * @param paramsHolder params to pass to create extractors/clients
     * @return AbstractExtractor
     */
    public static AbstractExtractor makeExtractor(
            ExtractorEngine extractorEngine, ParamsHolder paramsHolder) {
        AbstractExtractor extractor = null;
        switch (extractorEngine) {
            case SFTP:
                extractor = new SFTPExtractor(paramsHolder);
                break;
            case SQL:
                extractor = new SQLExtractor(paramsHolder);
                break;
            default:
                throw new AssertionError("Extraction type not supported.");
        }
        return extractor;
    }
}
