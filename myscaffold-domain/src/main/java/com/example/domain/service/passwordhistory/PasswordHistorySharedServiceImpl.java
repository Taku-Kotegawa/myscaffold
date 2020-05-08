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
package com.example.domain.service.passwordhistory;

import com.example.domain.model.PasswordHistory;
import com.example.domain.model.PasswordHistoryExample;
import com.example.domain.repository.passwordhistory.PasswordHistoryRepository;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PasswordHistorySharedServiceImpl implements PasswordHistorySharedService {

    @Inject
    PasswordHistoryRepository passwordHistoryRepository;

    @Override
    public int insert(PasswordHistory history) {
        return passwordHistoryRepository.insert(history);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PasswordHistory> findHistoriesByUseFrom(String username, LocalDateTime useFrom) {

        PasswordHistoryExample example = new PasswordHistoryExample();
        example.or()
                .andUsernameEqualTo(username)
                .andUseFromGreaterThanOrEqualTo(useFrom);
        example.setOrderByClause("use_from DESC");

        return passwordHistoryRepository.selectByExample(example);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PasswordHistory> findLatest(String username, int limit) {

        PasswordHistoryExample example = new PasswordHistoryExample();
        example.or()
                .andUsernameEqualTo(username);
        example.setOrderByClause("use_from DESC");

        RowBounds rowBounds = new RowBounds(0, limit);

        return passwordHistoryRepository.selectByExampleWithRowbounds(example, rowBounds);
    }
}