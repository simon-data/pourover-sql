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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.simondata.pouroversql.monitors.LogPercentDone;

/**
 * <h1>SFTP Client</h1>
 * Provide facilities for accessing SFTP
 *
 * @author  Jared Schwantz
 * @since   2020-04-23
 */

public class SFTPClient {
    private static final int DEFAULT_PORT = 22;
    private final Logger logger = LoggerFactory.getLogger(SFTPClient.class);
    protected SFTPParams params;
    protected Session sftpSession;
    protected ChannelSftp sftpChannel;

    /**
     * Constructor
     * @param params SFTPParams for the connection.
     */

    public SFTPClient(SFTPParams params) {
        this.params = params;
    }

    protected Session initSession() {
        Session session = getJschSession();
        session.setPassword(this.params.getPassword());
        
        java.util.Properties config = new java.util.Properties(); 
        config.put("StrictHostKeyChecking", "no");
        config.put("compression.s2c", "zlib@openssh.com,zlib,none");
        config.put("compression.c2s", "zlib@openssh.com,zlib,none");
        config.put("compression_level", "9");
        session.setConfig(config);
        
        return session;
    }

    protected Session getJschSession() {
        Session session = null;

        JSch jsch = new JSch();
        try {
            session = jsch.getSession(this.params.getUser(), this.params.getHost());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }
    
    protected ChannelSftp initChannelSftp(Session session) {
        ChannelSftp sftpChannel = null;
        try {
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            logger.info("I'm connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sftpChannel;
    }

    public ChannelSftp openSessionChannelSftp() {
        ChannelSftp sftpChannel = null;
        try {
            Session session = initSession();
            this.sftpSession = session;
            this.sftpSession.connect();
            sftpChannel = initChannelSftp(this.sftpSession);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return sftpChannel;
    }

    public void closeSessionChannelSftp() {
        try {
            this.sftpChannel.exit();
            this.sftpSession.disconnect();
            this.sftpSession = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadFile(String outputFile, String inputFile) {
        try {
            ChannelSftp sftpChannel = this.openSessionChannelSftp();
            this.sftpChannel = sftpChannel;
            this.sftpChannel.get(inputFile, outputFile, new LogPercentDone());
            logger.info("Downloaded the file from " + inputFile + " to " + outputFile);
            this.closeSessionChannelSftp();
        } catch (Exception e) {
            e.printStackTrace();
            this.closeSessionChannelSftp();
        }
    }
}
