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

import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import com.jcraft.jsch.*;

/**
 * <h1>Redshift Client</h1>
 * Provide facilities for querying to Redshift
 *
 * @author  Chet Mancini
 * @since   2019-03-31
 */
public class SFTPClient extends AbstractSQLClient {

    private static final int DEFAULT_PORT = 22;

    /**
     * Constructor
     * @param params SQLParams for the connection.
     */
    public SFTPClient(SQLParams params) {
        super(params);
    }

    private String getSFTPURL() {
        return String.format("sftp://%s:%s/%s",
                this.params.getHost(), this.params.getPort(DEFAULT_PORT), this.params.getDatabase()
        );
    }

    @Override
    public DataSource initDataSource() {
        DataSource ds = new MysqlDataSource();
        return ds;
    }

    // @Override
    // public Session initSession() {
    //     Session session;
    //     try {
    //         JSch jsch = new JSch();
    //         session = jsch.getSession(params.getUser(), params.getHost());
    //         session.setPassword(params.getPassword());
    //         session.connect();

    //         ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
    //         sftpChannel.connect();
    //     } catch (Exception e) {
    //         //TODO: handle exception
    //     }
    //     return session;
    // }

    @Override
    protected String getDriverName() {
        return "com.amazon.redshift.jdbc42.Driver";
    }

    // Override QueryToFile
}
