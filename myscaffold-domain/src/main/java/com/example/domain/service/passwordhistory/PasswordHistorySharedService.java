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

import java.time.LocalDateTime;
import java.util.List;

public interface PasswordHistorySharedService {


    /**
     * @param history
     * @return
     */
    public int insert(PasswordHistory history);

    /**
     * @param username
     * @param useFrom
     * @return
     */
    public List<PasswordHistory> findHistoriesByUseFrom(String username, LocalDateTime useFrom);

    /**
     * @param username
     * @param limit
     * @return
     */
    public List<PasswordHistory> findLatest(String username, int limit);

}
