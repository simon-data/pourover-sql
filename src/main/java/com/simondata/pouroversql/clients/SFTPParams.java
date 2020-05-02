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

// import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

// import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * SFTPParams
 * SFTPParams are provided to the SFTP client and are used in setting up an initial connection.
 * They will be reused as long as the client is in use.
 *
 * @author Chet Mancini
 * @since   2019-03-31
 */
public class SFTPParams extends ConnectionParams {

    private final static Logger logger = LoggerFactory.getLogger(InputParams.class);

    private Boolean sftpCompression;
    private Boolean checkHostKey;

    public SFTPParams(String host, Integer port, String user, String password) {
        super(host, port, user, password);
    }

    public SFTPParams(
            String host, Integer port, String user, String password, Properties customProperties) {
        super(host, port, user, password, customProperties);
    }

    public SFTPParams(ConnectionParams params, Boolean sftpCompression, Boolean checkHostKey) {
        super(params);
        this.sftpCompression = sftpCompression;
        this.checkHostKey = checkHostKey;
    }

    public Boolean getSftpCompression() {
        return this.sftpCompression;
    }

    public void setSftpCompression(Boolean sftpCompression) {
        this.sftpCompression = sftpCompression;
    }

    public Boolean getCheckHostKey() {
        return this.checkHostKey;
    }

    public void setCheckHostKey(Boolean checkHostKey) {
        this.checkHostKey = checkHostKey;
    }

    @Override
    public void logValues() {
        logger.info("User: " + this.getUser());
        logger.info("Password: <not shown>");
        logger.info("Port: " + this.getPort());
        logger.info("SFTP Compression: " + this.getSftpCompression());
        logger.info("Check Host Key: " + this.getCheckHostKey());
        for (String name : this.getStringPropertyNames()) {
            logger.info(name + ":" + this.getPropertyAsString(name));
        }
    }

}
