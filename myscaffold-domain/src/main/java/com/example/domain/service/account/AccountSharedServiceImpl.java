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
package com.example.domain.service.account;

import com.example.domain.common.message.MessageKeys;
import com.example.domain.model.*;
import com.example.domain.repository.account.AccountImageRepository;
import com.example.domain.repository.account.AccountRepository;
import com.example.domain.repository.account.RoleRepository;
import com.example.domain.service.authenticationevent.AuthenticationEventSharedService;
import com.example.domain.service.fileupload.FileUploadSharedService;
import com.example.domain.service.passwordhistory.PasswordHistorySharedService;
import com.github.dozermapper.core.Mapper;
import org.passay.CharacterRule;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.terasoluna.gfw.common.date.DefaultClassicDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountSharedServiceImpl implements AccountSharedService {

    @Inject
    AccountRepository accountRepository;

    @Inject
    RoleRepository roleRepository;

    @Inject
    AccountImageRepository accountImageRepository;

    @Inject
    AuthenticationEventSharedService authenticationEventSharedService;

    @Inject
    FileUploadSharedService fileUploadSharedService;

    @Inject
    PasswordEncoder passwordEncoder;

    @Inject
    DefaultClassicDateFactory dateFactory;

    @Inject
    PasswordGenerator passwordGenerator;

    @Inject
    PasswordHistorySharedService passwordHistorySharedService;

    @Resource(name = "passwordGenerationRules")
    List<CharacterRule> passwordGenerationRules;

    @Value("${security.lockingDurationSeconds}")
    int lockingDurationSeconds;

    @Value("${security.lockingThreshold}")
    int lockingThreshold;

    @Value("${security.passwordLifeTimeSeconds}")
    int passwordLifeTimeSeconds;

    @Inject
    Mapper beanMapper;

    @Override
    @Transactional(readOnly = true)
    public AccountRoles findOne(String username) {

        Account account = accountRepository.selectByPrimaryKey(username);
        if (account == null) {
            throw new ResourceNotFoundException(ResultMessages.error().add(
                    MessageKeys.E_SL_FA_5001, username));
        }
        AccountRoles accountRoles = beanMapper.map(account, AccountRoles.class);

        RoleExample roleExample = new RoleExample();
        roleExample.or().andUsernameEqualTo(username);
        accountRoles.setRoleLables(
                roleRepository.selectByExample(roleExample).stream()
                        .map(c -> c.getRole()).collect(Collectors.toList()));

        return accountRoles;
    }

    @Override
    @Transactional(readOnly = true)
    public LocalDateTime getLastLoginDate(String username) {
        List<SuccessfulAuthentication> events = authenticationEventSharedService
                .findLatestSuccessEvents(username, 1);

        if (events.isEmpty()) {
            return null;
        } else {
            return events.get(0).getAuthenticationTimestamp();
        }
    }

    @Override
    public String create(AccountRoles accountRoles, String imageId) {
        if (exists(accountRoles.getUsername())) {
            throw new BusinessException(ResultMessages.error().add(
                    MessageKeys.E_SL_AC_5001));
        }
        String rawPassword = passwordGenerator.generatePassword(10,
                passwordGenerationRules);
        accountRoles.setPassword(passwordEncoder.encode(rawPassword));
        accountRepository.insert(accountRoles);
        for (Role role : accountRoles.getRoles()) {
            roleRepository.insert(role);
        }
        TempFile tempFile = fileUploadSharedService.findTempFile(imageId);
        byte[] image = tempFile.getBody();
        AccountImage accountImage = new AccountImage();
        accountImage.setUsername(accountRoles.getUsername());
        accountImage.setBody(image);
        accountImage.setExtension(StringUtils.getFilenameExtension(tempFile
                .getOriginalName()));
        accountImageRepository.insert(accountImage);

        fileUploadSharedService.deleteTempFile(imageId);
        return rawPassword;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String username) {
        Account account = accountRepository.selectByPrimaryKey(username);
        return account != null;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLocked(String username) {
        List<FailedAuthentication> failureEvents = authenticationEventSharedService
                .findLatestFailureEvents(username, lockingThreshold);

        if (failureEvents.size() < lockingThreshold) {
            return false;
        }

        return !failureEvents
                .get(lockingThreshold - 1)
                .getAuthenticationTimestamp()
                .isBefore(
                        dateFactory.newTimestamp().toLocalDateTime()
                                .minusSeconds(lockingDurationSeconds));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("isInitialPassword")
    public boolean isInitialPassword(String username) {
        List<PasswordHistory> passwordHistories = passwordHistorySharedService
                .findLatest(username, 1);
        return passwordHistories.isEmpty();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("isCurrentPasswordExpired")
    public boolean isCurrentPasswordExpired(String username) {
        List<PasswordHistory> passwordHistories = passwordHistorySharedService
                .findLatest(username, 1);

        if (passwordHistories.isEmpty()) {
            return true;
        }

        return passwordHistories
                .get(0)
                .getUseFrom()
                .isBefore(
                        dateFactory.newTimestamp().toLocalDateTime()
                                .minusSeconds(passwordLifeTimeSeconds));
    }

    @Override
    @CacheEvict(value = {"isInitialPassword", "isCurrentPasswordExpired"}, key = "#username")
    public boolean updatePassword(String username, String rawPassword) {
        String password = passwordEncoder.encode(rawPassword);

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        int result = accountRepository.updateByPrimaryKeySelective(account);

        LocalDateTime passwordChangeDate = dateFactory.newTimestamp()
                .toLocalDateTime();

        PasswordHistory passwordHistory = new PasswordHistory();
        passwordHistory.setUsername(username);
        passwordHistory.setPassword(password);
        passwordHistory.setUseFrom(passwordChangeDate);
        passwordHistorySharedService.insert(passwordHistory);

        return (result == 1);
    }

    @Override
    @CacheEvict(value = {"isInitialPassword", "isCurrentPasswordExpired"}, key = "#username")
    public void clearPasswordValidationCache(String username) {

    }

    @Override
    @Transactional(readOnly = true)
    public AccountImage getImage(String username) {
        return accountImageRepository.selectByPrimaryKey(username);
    }
}
