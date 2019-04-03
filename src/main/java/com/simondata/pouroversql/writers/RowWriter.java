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
package com.simondata.pouroversql.writers;

import java.util.List;
import java.util.Map;

/**
 * RowWriter
 * Provides an interface to control writing out query results to various outputs.
 */
public interface RowWriter {

    /**
     * Write a row out.
     * @param row the row data to write out.
     */
    void writeRow(Map<String, Object> row);

    /**
     * Write a set of rows out.
     * @param rows List of all the rows to write.
     * @return the number of rows written.
     */
    int writeRows(List<Map<String, Object>> rows);

    /**
     * Should be called after all rows written.
     * Hook to perform all cleanup on the writer.
     */
    void close();
}
