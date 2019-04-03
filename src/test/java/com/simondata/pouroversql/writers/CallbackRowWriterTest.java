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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class CallbackRowWriterTest {
    @Test
    public void writeQuery() throws Exception {
        Map<String, Object> test1 = new HashMap<>();
        test1.put("a", "abc");
        test1.put("b", 1);
        Map<String, Object> test2 = new HashMap<>();
        test2.put("a", "jkl");
        test2.put("b", 2);

        AtomicInteger invocationCount = new AtomicInteger();

        Function<Map<String, Object>, ?> callback = (Function<Map<String, Object>, Object>) stringObjectMap -> {
            invocationCount.incrementAndGet();
            return null;
        };

        List<Map<String, Object>> input = Arrays.asList(test1, test2);
        CallbackRowWriter subject = new CallbackRowWriter(callback);
        int lines = subject.writeRows(input);
        assertEquals(2, invocationCount.get());
        assertEquals(2, lines);
    }
}
