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

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CSVRowWriterTest {
    @Test
    public void writeQuery() throws Exception {

        Map<String, Object> test1 = new HashMap<>();
        test1.put("a", "abc");
        test1.put("b", 1);
        Map<String, Object> test2 = new HashMap<>();
        test2.put("a", "jkl");
        test2.put("b", 2);

        List<Map<String, Object>> input = Arrays.asList(test1, test2);
        FileRowWriter subject = new CSVRowWriter(",", true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        subject.open(baos);
        int lines = subject.writeRows(input);
        subject.close();
        assertEquals(
                "a,b\nabc,1\njkl,2\n",
                baos.toString()
        );
        assertEquals(2, lines);


    }

    @Test
    public void writeQueryQuotesValues() throws Exception {

        Map<String, Object> test1 = new HashMap<>();
        test1.put("a", "abc,def,ghi");
        test1.put("b", 1);
        Map<String, Object> test2 = new HashMap<>();
        test2.put("a", "the rain\n" +
                "in Spain\n" +
                "falls mainly\n" +
                "on the plain");
        test2.put("b", 2);
        Map<String, Object> test3 = new HashMap<>();
        test3.put("a", "\"sarcasm\"");
        test3.put("b", 3);
        Map<String, Object> test4 = new HashMap<>();
        test4.put("a", null);
        test4.put("b", 4);

        List<Map<String, Object>> input = Arrays.asList(test1, test2, test3, test4);
        FileRowWriter subject = new CSVRowWriter(",", true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        subject.open(baos);
        int lines = subject.writeRows(input);
        subject.close();
        assertEquals(
                "a,b\n" +
                        "\"abc,def,ghi\",1\n" +
                        "\"the rain\n" +
                        "in Spain\n" +
                        "falls mainly\n" +
                        "on the plain\",2\n" +
                        "\"\"\"sarcasm\"\"\",3\n" +
                        ",4\n",
                baos.toString()
        );
        assertEquals(4, lines);
    }
}
