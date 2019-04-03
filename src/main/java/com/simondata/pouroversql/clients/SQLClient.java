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

import com.simondata.pouroversql.writers.RowHandler;

import java.util.List;
import java.util.Map;

/**
 * SQLClient
 */
public interface SQLClient {

    void setQueryParams(QueryParams queryParams);

    List<Map<String, Object>> queryAsList(String queryText);

    int queryWithHandler(String queryText, RowHandler handler);
}
