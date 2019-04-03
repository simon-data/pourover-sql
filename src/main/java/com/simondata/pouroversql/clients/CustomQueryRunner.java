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

import org.apache.commons.dbutils.AbstractQueryRunner;
import org.apache.commons.dbutils.StatementConfiguration;
import com.simondata.pouroversql.writers.RowHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * <h1>CustomQueryRunner</h1>
 * A subtype of the Apache DbUtils AbstractQueryRunner
 * that offers more flexibility.
 */
class CustomQueryRunner extends AbstractQueryRunner {

    private final RowHandler rh;

    /**
     * Constructor
     * @param ds
     * @param stmtConfig
     * @param rh
     */
    CustomQueryRunner(DataSource ds, StatementConfiguration stmtConfig, RowHandler rh) {
        super(ds, stmtConfig);
        this.rh = rh;
    }

    int query(String sql) throws SQLException {
        Connection conn = this.prepareConnection();
        return this.query(conn, true, sql);
    }

    private int query(Connection conn, boolean closeConn, String sql, Object... params)
            throws SQLException {
        if (conn == null) {
            throw new SQLException("Null connection");
        }

        if (sql == null) {
            if (closeConn) {
                close(conn);
            }
            throw new SQLException("Null SQL statement");
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            stmt = this.prepareStatement(conn, sql);
            this.fillStatement(stmt, params);
            rs = this.wrap(stmt.executeQuery());
            count = rh.handle(rs);
        } catch (SQLException e) {
            this.rethrow(e, sql, params);
        } finally {
            try {
                close(rs);
            } finally {
                close(stmt);
                if (closeConn) {
                    close(conn);
                }
            }
        }
        return count;
    }
}
