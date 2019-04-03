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

/**
 * <h1>SnowflakeSQLParams</h1>
 * A subtype of SQLParams used to work with Snowflake.
 */
public class SnowflakeSQLParams extends SQLParams {

    public SnowflakeSQLParams(String host, Integer port, String user, String password, String database) {
        super(host, port, user, password, database);
    }
}
