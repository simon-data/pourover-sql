// /*
//   Copyright 2019-present, Simon Data, Inc.

//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at

//      http://www.apache.org/licenses/LICENSE-2.0

//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
//  */

package com.simondata.pouroversql;

/**
 * The primary class that drives all extractions. Immediate descendants of this class should be
 * differentiated by the high-level category of data source they represent, e.g. "Database" or "SFTP"
 */
public class AbstractExtractor {

    /**
     * Performs the actual extract from the data source and writes the extracted
     * data to the location configured by the launch arguments
     */
    public void extract() {

    }
}
