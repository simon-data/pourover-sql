/**
 * Copyright 2019-present, Simon Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simondata.pouroversql;

import com.simondata.pouroversql.clients.*;
import com.simondata.pouroversql.writers.*;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.time.*;
import com.jcraft.jsch.*;
// import com.jcraft.jzlib.*;

import static com.simondata.pouroversql.util.TextFormat.parseInteger;

/**
 * <h1>ExtractorRunner</h1>
 *
 */
public class ExtractorRunner {

    private final static Logger logger = LoggerFactory.getLogger(ExtractorRunner.class);

    private static final String ENV_VAR_PASSWORD_KEY = "EXTRACT_DB_PASSWORD";
    private static final String DEFAULT_OUTPUT_FILENAME = "query_result.json";

    private static Options getOptions() {
        Options options = new Options();
        options.addRequiredOption("u", "user", true, "user");
        options.addOption("h", "host", true, "host");
        options.addOption(
                "p",
                "port",
                true,
                "Port, defaults to engine's default port."
        );
        options.addOption("d", "database", true, "database");
        options.addOption("t", "type", true, "Driver type (SQLServer | MySQL | Postgres )");
        options.addOption("s", "sql", true,
                "SQL file to read.");
        options.addOption("dry", "dry", false, "Dry run");
        options.addOption("format", "format", true, "The output format, defaults to json (JSON | CSV)");
        options.addOption(
                "f",
                "file",
                true,
                "File to write to. Defaults to " + DEFAULT_OUTPUT_FILENAME + '.'
        );
        options.addOption("c", "case", true, "Key case format (DEFAULT | Snake | Camel)");
        options.addOption("fetchsize", "fetchsize", true, "Fetch size");
        options.addOption("timeout", "timeout", true, "Query Timeout in seconds");
        options.addOption("maxrows", "maxrows", true, "Maximum rows");
        options.addOption("sftp", "sftp", false, "Connecting to SFTP");

        Option customParams = Option.builder("custom")
                .longOpt("custom")
                .hasArgs()
                .valueSeparator()
                .argName("property=value")
                .desc("Custom params")
                .numberOfArgs(2)
                .optionalArg(true)
                .build();

        options.addOption(customParams);
        return options;
    }

    private static String getPassword() {
        /*
          Try to get password from environment var.
          If that doesn't exist prompt on console.
         */
        String value = System.getenv(ENV_VAR_PASSWORD_KEY);
        if (value != null) {
            return value;
        } else {
            Console console = System.console();
            return new String(console.readPassword("Password: "));
        }
    }

    private static void configureLogging() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "trace");
    }

    private static String readSqlFromFile(String filename) throws IOException {
        logger.debug("Reading " + filename);
        return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
    }

    private static String readSqlFromStdIn() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    private static SQLParams getSqlParams(CommandLine commandLine) {
        String user = commandLine.getOptionValue("user");
        String host = commandLine.getOptionValue("host", "localhost");
        Integer port = parseInteger(commandLine.getOptionValue("port"));
        String database = commandLine.getOptionValue("database");
        String password = getPassword();
        Properties props = commandLine.getOptionProperties("custom");
        return new SQLParams(host, port, user, password, database, props);
    }

    private static QueryParams getQueryParams(CommandLine commandLine) {
        Integer fetchSize = parseInteger(commandLine.getOptionValue("fetchsize"));
        Integer maxRows = parseInteger(commandLine.getOptionValue("maxRows"));
        Integer timeout = parseInteger(commandLine.getOptionValue("timeout"));
        return new QueryParams(fetchSize, maxRows, timeout);
    }

    private static FormattingParams getFormattingParams(CommandLine commandLine) {
        String caseFormat = commandLine.getOptionValue("case");
        FormattingParams params = new FormattingParams();
        params.setKeyCaseFormat(caseFormat);
        return params;
    }

    public static class progressMonitor implements SftpProgressMonitor{
        private long max                = 0;
        private long count              = 0;
        private long percent            = 0;
        // private CallbackContext callbacks = null;
        
        // If you need send something to the constructor, change this method
        public progressMonitor() {}
    
        public void init(int op, java.lang.String src, java.lang.String dest, long max) {
            this.max = max;
            LocalTime time = LocalTime.now();
            logger.info("starting at " + time + ": 0 of " + max);
        }
    
        public boolean count(long bytes){
            this.count += bytes;
            LocalTime time = LocalTime.now();
            long percentNow = this.count*100/max;
            if(percentNow>this.percent){
                this.percent = percentNow;
    
                logger.info(time + "progress: " + this.percent + "%"); // Progress 0,0
            }
            return(true);
        }
    
        public void end(){
            LocalTime time = LocalTime.now();
            logger.info("finished download at " + time);// The process is over
        }
    }

    public static void SFTPdownloadFile(SQLParams params, String outputFile, String inputFile) {
        Session session;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(params.getUser(), params.getHost());
            session.setPassword(params.getPassword());
            
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
            sftpChannel.get(inputFile, outputFile, new progressMonitor());
            logger.info("Downloaded the file from " + inputFile + " to " + outputFile);
            sftpChannel.exit();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        configureLogging();

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(getOptions(), args);
            SQLParams sqlParams = getSqlParams(line);
            FormattingParams formattingParams = getFormattingParams(line);
            QueryParams queryParams = getQueryParams(line);
            SqlEngine engine = SqlEngine.byName(line.getOptionValue("type", "SQLSERVER"));
            FileOutputFormat outputFormat = FileOutputFormat.valueOf(
                    line.getOptionValue("format", "json").toUpperCase());

            if (line.hasOption("dry")) {
                sqlParams.logValues();
                queryParams.logValues();
                formattingParams.logValues();
                System.exit(0);
            }
            if (line.hasOption("sftp")) {
                try {
                    logger.info("Got started with sftp!");
                    String inputSql = line.getOptionValue("sql");
                    logger.info("InputSql: " + inputSql);
                    String outputFile = line.getOptionValue("file", DEFAULT_OUTPUT_FILENAME);
                    logger.info("outputFile: " + outputFile);
                    // sftpClient.initSession(sqlParams);
                    SFTPdownloadFile(sqlParams, outputFile, inputSql);
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            } else {
                SQLExtractor sqlExtractor = new SQLExtractor(engine, sqlParams, formattingParams);
                try {
                    String inputFilename = line.getOptionValue("sql");
                    String inputSql;
                    if (inputFilename != null) {
                        inputSql = readSqlFromFile(inputFilename);
                    } else {
                        inputSql = readSqlFromStdIn();
                    }
                    String outputFile = line.getOptionValue("file", DEFAULT_OUTPUT_FILENAME);
                    sqlExtractor.queryToFile(inputSql, new File(outputFile), outputFormat, queryParams);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (ParseException exp) {
            logger.error("Parsing failed.  Reason: " + exp.getMessage());
        }
    }
}
