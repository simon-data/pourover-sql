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

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>AbstractRowWriter</h1>
 */
public abstract class AbstractRowWriter implements RowWriter {

    public abstract void writeRow(Map<String, Object> row);

    public int writeRows(List<Map<String, Object>> rows) {
        AtomicInteger counter = new AtomicInteger();
        rows.forEach(row -> {
            this.writeRow(row);
            counter.getAndIncrement();
        });
        return counter.intValue();
    }

    public abstract void close();
}
