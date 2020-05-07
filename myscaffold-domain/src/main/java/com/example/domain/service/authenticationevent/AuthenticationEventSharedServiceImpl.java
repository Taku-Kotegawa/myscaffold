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
package com.example.domain.service.authenticationevent;

import com.example.domain.model.FailedAuthentication;
import com.example.domain.model.FailedAuthenticationExample;
import com.example.domain.model.SuccessfulAuthentication;
import com.example.domain.model.SuccessfulAuthenticationExample;
import com.example.domain.repository.authenticationevent.FailedAuthenticationRepository;
import com.example.domain.repository.authenticationevent.SuccessfulAuthenticationRepository;
import com.example.domain.service.account.AccountSharedService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.date.ClassicDateFactory;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class AuthenticationEventSharedServiceImpl implements AuthenticationEventSharedService {

    @Inject
    ClassicDateFactory dateFactory;

    @Inject
    FailedAuthenticationRepository failedAuthenticationRepository;

    @Inject
    SuccessfulAuthenticationRepository successAuthenticationRepository;

    @Inject
    AccountSharedService accountSharedService;

    @Override
    @Transactional(readOnly = true)
    public List<SuccessfulAuthentication> findLatestSuccessEvents(String username, int count) {
        SuccessfulAuthenticationExample example = new SuccessfulAuthenticationExample();
        example.setOrderByClause("authentication_timestamp DESC");
        RowBounds rowBounds = new RowBounds(0, count);
        return successAuthenticationRepository.selectByExampleWithRowbounds(example, rowBounds);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FailedAuthentication> findLatestFailureEvents(String username, int count) {
        FailedAuthenticationExample example = new FailedAuthenticationExample();
        example.setOrderByClause("authentication_timestamp DESC");
        RowBounds rowBounds = new RowBounds(0, count);
        return failedAuthenticationRepository.selectByExampleWithRowbounds(example, rowBounds);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void authenticationSuccess(String username) {
        SuccessfulAuthentication successEvent = new SuccessfulAuthentication();
        successEvent.setUsername(username);
        successEvent.setAuthenticationTimestamp(dateFactory.newTimestamp()
                .toLocalDateTime());

        successAuthenticationRepository.insert(successEvent);
        deleteFailureEventByUsername(username);
    }

    @Override
    public void authenticationFailure(String username) {
        if (accountSharedService.exists(username)) {
            FailedAuthentication failureEvents = new FailedAuthentication();
            failureEvents.setUsername(username);
            failureEvents.setAuthenticationTimestamp(dateFactory.newTimestamp()
                    .toLocalDateTime());

            failedAuthenticationRepository.insert(failureEvents);
        }
    }

    @Override
    public int deleteFailureEventByUsername(String username) {
        FailedAuthenticationExample example = new FailedAuthenticationExample();
        example.or().andUsernameEqualTo(username);
        return failedAuthenticationRepository.deleteByExample(example);
    }
}
