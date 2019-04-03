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

package com.simondata.pouroversql.util;

import org.junit.Test;

import static com.simondata.pouroversql.util.TextFormat.toCamelCase;
import static com.simondata.pouroversql.util.TextFormat.toSnakeCase;
import static org.junit.Assert.assertEquals;

public class TextFormatTest {

    @Test
    public void testToCamelCase() throws Exception {
        assertEquals("theRainInSpain", toCamelCase("the rain in spain"));
        assertEquals("theRainInSpain", toCamelCase("theRainInSpain"));
        assertEquals("theRainInSpain", toCamelCase("the_rain_in_spain"));
        assertEquals("thisOneHas3Dig1tsInI7", toCamelCase("this_one_has_3_dig1tsInI7"));
    }

    @Test
    public void testToSnakeCase() throws Exception {
        assertEquals("the_rain_in_spain", toSnakeCase("the rain in spain"));
        assertEquals("the_rain_in_spain", toSnakeCase("the_rain_in_spain"));
        assertEquals("the_rain_in_spain", toSnakeCase("theRainInSpain"));
        assertEquals("this_one_has3_dig1ts_in_i7", toSnakeCase("thisOneHas3Dig1tsInI7"));
    }

}
