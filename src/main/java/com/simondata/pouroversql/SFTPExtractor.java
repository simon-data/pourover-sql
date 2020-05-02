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

package com.simondata.pouroversql;

import com.simondata.pouroversql.clients.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SFTPExtractor is the primary class to use and is designed for most use cases.
 */
public class SFTPExtractor extends AbstractExtractor {

    private final static Logger logger = LoggerFactory.getLogger(ExtractorRunner.class);

    private final SFTPClient sftpClient;
    private ParamsHolder paramsHolder;

    /**
     * Constructor
     * @param host the server to connect to.
     * @param port the server port to connect to.
     * @param username the user to connect as.
     * @param password the user's password to connect with.
     */
    public SFTPExtractor(
            String host, Integer port, String username, String password
    ) {
        this(
                new SFTPParams(host, port, username, password)
        );
    }

    /**
     * Primary constructor
     * @param sftpParams the SFTPParams to use when building the connection.
     */
    public SFTPExtractor(SFTPParams sftpParams) {
        this.sftpClient = ClientFactory.makeSFTPClient("sftp", sftpParams);
    }

    /**
     * If you need to implement your own SFTPClient
     * @param sftpClient an existing SFTPClient that wraps a connection.
     */
    public SFTPExtractor(SFTPClient sftpClient) {
        this.sftpClient = sftpClient;
    }

    public SFTPExtractor(ParamsHolder paramsHolder) {
        this(paramsHolder.getSftpParams());
        this.paramsHolder = paramsHolder;
    }

    public void extract() {
        try {
            this.sftpClient.downloadFile(
                this.paramsHolder.getInputSftpFile(),
                this.paramsHolder.getOutputFile());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public void extract(String inputFile, String outputFile) {
        try {
            this.sftpClient.downloadFile(inputFile, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
