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

import com.simondata.pouroversql.writers.KeyCaseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>FormattingParams</h1>
 *
 * These parameters are supposed to provide information on how to format the output
 * result of a query. Currently key case formats may be changed.
 *
 * @author Chet Mancini
 * @since   2019-03-31
 */
public class FormattingParams implements InputParams {
    private final static Logger logger = LoggerFactory.getLogger(InputParams.class);

    private KeyCaseFormat keyCaseFormat;

    /**
     * Constructor. Default FormattingParams.
     */
    public FormattingParams() {
        this.keyCaseFormat = KeyCaseFormat.DEFAULT;
    }

    /**
     * Constructor
     * @param keyCaseFormat the KeyCaseFormat to use
     */
    public FormattingParams(KeyCaseFormat keyCaseFormat) {
        this.keyCaseFormat = keyCaseFormat;
    }

    /**
     * Set setting for keyCaseFormat
     * @param keyCaseFormat String representing the keyCaseFormat to use.
     */
    public void setKeyCaseFormat(String keyCaseFormat) {
        this.keyCaseFormat = getKeyCaseFormat(keyCaseFormat);
    }

    /**
     * Set setting for keyCaseFormat
     * @param keyCaseFormat KeyCaseFormat to use.
     */
    public void setKeyCaseFormat(KeyCaseFormat keyCaseFormat) {
        this.keyCaseFormat = keyCaseFormat;
    }

    /**
     * Get settinf for keyCaseFormat.
     * @return the configured KeyCaseFormat. Defaults to no modifications.
     */
    public KeyCaseFormat getKeyCaseFormat() {
        return this.keyCaseFormat;
    }

    private static KeyCaseFormat getKeyCaseFormat(String input) {
        if (input == null) {
            return KeyCaseFormat.DEFAULT;
        }
        if (input.toLowerCase().contains("camel")) {
            return KeyCaseFormat.CAMEL_CASE;
        }
        if (input.toLowerCase().contains("snake")) {
            return KeyCaseFormat.SNAKE_CASE;
        }
        return KeyCaseFormat.DEFAULT;
    }

    public static FormattingParams getDefaultFormattingParams() {
        return new FormattingParams();
    }

    @Override
    public void logValues() {
        logger.info("Key Case Format: " + this.getKeyCaseFormat().name());
    }
}
