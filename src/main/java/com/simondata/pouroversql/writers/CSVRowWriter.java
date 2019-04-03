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

package com.simondata.pouroversql.writers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <h1>CSVRowWriter</h1>
 * CSVRowWriter is a type of writer that outputs to CSV.
 */
public class CSVRowWriter extends FileRowWriter {

    private static String QUOTE = "\"";

    private List<String> orderedKeys;

    private CharSequence delimiter;

    private boolean headersWritten;

    private boolean shouldWriteHeaders = true;

    /**
     * Constructor using default delimiter.
     */
    public CSVRowWriter() {
        this.delimiter = ",";
    }

    /**
     * Constructor with custom delimiter.
     * @param delimiter the delimiter to separate the columns.
     */
    public CSVRowWriter(CharSequence delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Constructor with custom delimiter and
     * flag to turn off headers.
     * @param delimiter The delimiter for the CSV. defaults to comma.
     * @param shouldWriteHeaders Whether to write the headers as the first row of the CSV.
     */
    public CSVRowWriter(CharSequence delimiter, boolean shouldWriteHeaders) {
        this.delimiter = delimiter;
        this.shouldWriteHeaders = shouldWriteHeaders;
    }

    @Override
    protected void postCloseHook() {
        this.headersWritten = false;
    }

    @Override
    protected void postOpenHook() {
        this.headersWritten = false;
    }

    private void writeHeadersOnce(Map<String, Object> row) {
        if (!this.headersWritten && this.shouldWriteHeaders) {
            this.orderedKeys = row.keySet()
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            this.writer.println(String.join(this.delimiter, this.orderedKeys));
            this.headersWritten = true;
        }
    }

    private String quoteString(String input) {
        return QUOTE + input.replace(QUOTE, "\"\"") + QUOTE;
    }

    private String handleValue(Object inputObj) {
        if (inputObj == null) {
            return "";
        } else if (! (inputObj instanceof String)) {
            return inputObj.toString();
        } else {
            String inputStr = (String) inputObj;
            if (inputStr.contains(System.lineSeparator())
                    || inputStr.contains(this.delimiter)
                    || inputStr.contains(QUOTE)) {
                return quoteString(inputStr);
            } else {
                return inputStr;
            }
        }
    }

    private String rowToString(Map<String, Object> row) {
        return this.orderedKeys.stream()
                .map(row::get)
                .map(this::handleValue)
                .collect(Collectors.joining(this.delimiter));
    }

    @Override
    public void writeRow(Map<String, Object> row) {
        this.writeHeadersOnce(row);
        this.writer.println(rowToString(row));
    }

}
