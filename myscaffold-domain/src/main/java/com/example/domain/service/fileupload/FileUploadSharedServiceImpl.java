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
import com.example.domain.model.TempFileExample;
import com.example.domain.repository.fileupload.TempFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.date.ClassicDateFactory;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class FileUploadSharedServiceImpl implements FileUploadSharedService {

    @Inject
    TempFileRepository tempFileRepository;

    @Inject
    ClassicDateFactory dateFactory;

    @Override
    public String uploadTempFile(TempFile tempFile) {
        tempFile.setId(UUID.randomUUID().toString());
        tempFile.setUploadedDate(dateFactory.newTimestamp().toLocalDateTime());
        tempFileRepository.insert(tempFile);
        return tempFile.getId();
    }

    @Override
    public TempFile findTempFile(String id) {
        return tempFileRepository.selectByPrimaryKey(id);
    }

    @Override
    public void deleteTempFile(String id) {
        tempFileRepository.deleteByPrimaryKey(id);

    }

    @Override
    public void cleanUp(LocalDateTime deleteTo) {
        TempFileExample example = new TempFileExample();
        example.or().andUploadedDateLessThan(deleteTo);
        tempFileRepository.deleteByExample(example);
    }
}
