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

import com.jcraft.jsch.*;
import java.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.simondata.pouroversql.ExtractorRunner;
import com.simondata.pouroversql.util.ProgressMonitor;

/**
 * <h1>SFTP Client</h1>
 * Provide facilities for accessing SFTP
 *
 * @author  Jared Schwantz
 * @since   2020-04-23
 */

public class SFTPClient {
    private static final int DEFAULT_PORT = 22;
    private final Logger logger = LoggerFactory.getLogger(ExtractorRunner.class);
    protected SQLParams params;

    /**
     * Constructor
     * @param params SQLParams for the connection.
     */
    public SFTPClient(SQLParams params) {
        this.params = params;
    }

    public void downloadFile(String outputFile, String inputFile) {
        Session session;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(this.params.getUser(), this.params.getHost());
            session.setPassword(this.params.getPassword());
            
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            config.put("compression.s2c", "zlib@openssh.com,zlib,none");
            config.put("compression.c2s", "zlib@openssh.com,zlib,none");
            config.put("compression_level", "9");
            session.setConfig(config);
            session.connect();
            
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            logger.info("I'm connected!");
            sftpChannel.get(inputFile, outputFile, new ProgressMonitor());
            logger.info("Downloaded the file from " + inputFile + " to " + outputFile);
            sftpChannel.exit();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
