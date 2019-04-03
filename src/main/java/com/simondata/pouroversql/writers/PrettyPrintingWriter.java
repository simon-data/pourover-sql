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

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Writer that prints to stdout.
 */
public class PrettyPrintingWriter extends FileRowWriter {

    @Override
    public void writeRow(Map<String, Object> row) {
        System.out.println(String.join("\t|\t",
                row.values().stream().map(Object::toString)
                        .collect(Collectors.toList())));
    }
}
