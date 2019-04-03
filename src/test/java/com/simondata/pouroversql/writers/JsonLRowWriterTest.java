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
import java.util.*;

import static org.junit.Assert.*;

public class JsonLRowWriterTest {

    @Test
    public void writeQuery() throws Exception {

        Map<String, Object> test1 = new HashMap<>();
        test1.put("a", "abc");
        test1.put("b", 1);
        Map<String, Object> test2 = new HashMap<>();
        test2.put("a", "jkl");
        test2.put("b", 2);

        List<Map<String, Object>> input = Arrays.asList(test1, test2);
        FileRowWriter subject = new JsonLRowWriter();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        subject.open(baos);

        int lines = subject.writeRows(input);
        subject.close();
        assertEquals(
                "{\"a\":\"abc\",\"b\":1}\n{\"a\":\"jkl\",\"b\":2}\n",
                baos.toString(JsonLRowWriter.ENCODING)
        );
        assertEquals(2, lines);
    }
}