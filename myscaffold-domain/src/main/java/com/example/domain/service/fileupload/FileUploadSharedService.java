/*
 * Copyright(c) 2013 NTT DATA Corporation. Copyright(c) 2013 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.example.domain.service.fileupload;

import com.example.domain.model.TempFile;

import java.time.LocalDateTime;

public interface FileUploadSharedService {

    /**
     * @param tempFile
     * @return
     */
    String uploadTempFile(TempFile tempFile);

    /**
     * @param id
     * @return
     */
    TempFile findTempFile(String id);

    /**
     * @param id
     */
    void deleteTempFile(String id);

    /**
     * @param deleteTo
     */
    void cleanUp(LocalDateTime deleteTo);
}
