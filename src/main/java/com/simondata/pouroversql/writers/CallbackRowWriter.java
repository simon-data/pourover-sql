/*
  Copyright 2019-present, Simon Data, Inc.
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.simondata.pouroversql.writers;

import java.util.Map;
import java.util.function.Function;

/**
 * <h1>CallbackRowWriter</h1>
 * A RowWriter that 'writes' rows by invoking a provided callback function instead of a file like output.
 */
public class CallbackRowWriter extends AbstractRowWriter implements RowWriter {

    private Function<Map<String, Object>, ?> callback;

    /**
     * Constructor
     * @param callback the function to invoke on each row.
     */
    public CallbackRowWriter(Function<Map<String, Object>, ?> callback) {
        this.callback = callback;
    }

    @Override
    public void writeRow(Map<String, Object> row) {
        this.callback.apply(row);
    }

    @Override
    public void close() {
    }
}
