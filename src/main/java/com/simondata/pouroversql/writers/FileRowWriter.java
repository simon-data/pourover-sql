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
package com.simondata.pouroversql.writers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public abstract class FileRowWriter extends AbstractRowWriter implements RowWriter {

    private final Logger logger = LoggerFactory.getLogger(FileRowWriter.class);

    static String ENCODING = "UTF-8";

    protected PrintWriter writer = null;

    /**
     * Abstract method.
     * @param row the row data to write out.
     */
    public abstract void writeRow(Map<String, Object> row);

    /**
     * Convenience method.
     * @param outputFilename the file name to write to.
     */
    public void open(String outputFilename) {
        this.open(new File(outputFilename));
    }

    /**
     * Open an output file for writing
     * @param outputFile the output file to open.
     */
    public void open(File outputFile) {
        try {
            this.writer = new PrintWriter(outputFile, ENCODING);
            logger.info("Opening file: " + outputFile.getName());
            this.postOpenHook();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open an OutputStream and initialize writing process for a new query.
     * @param outputStream the OutputStream to open.
     */
    public void open(OutputStream outputStream) {
        this.writer = new PrintWriter(outputStream);
        this.postOpenHook();
    }

    /**
     * Open Stdout as an OutputStream.
     */
    public void openStdOut() {
        this.open(System.out);
    }

    protected void postOpenHook() {

    }

    protected void postCloseHook() {

    }

    private void flush() {
        if (this.writer != null) {
            this.writer.flush();
        }
    }

    /**
     * Close out the writer safely.
     */
    public void close() {
        this.flush();
        if (this.writer != null) {
            this.writer.close();
        }
        this.writer = null;
        this.postCloseHook();
    }
}
