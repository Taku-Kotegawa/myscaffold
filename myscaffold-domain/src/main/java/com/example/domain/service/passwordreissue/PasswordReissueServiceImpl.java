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
package com.example.domain.service.passwordreissue;

import com.example.domain.common.message.MessageKeys;
import com.example.domain.model.AccountRoles;
import com.example.domain.model.FailedPasswordReissueExample;
import com.example.domain.model.PasswordReissueInfo;
import com.example.domain.model.PasswordReissueInfoExample;
import com.example.domain.repository.passwordreissue.FailedPasswordReissueRepository;
import com.example.domain.repository.passwordreissue.PasswordReissueInfoRepository;
import com.example.domain.service.account.AccountSharedService;
import com.example.domain.service.mail.PasswordReissueMailSharedService;
import org.passay.CharacterRule;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import org.terasoluna.gfw.common.date.ClassicDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PasswordReissueServiceImpl implements PasswordReissueService {

    @Inject
    ClassicDateFactory dateFactory;

    @Inject
    PasswordReissueFailureSharedService passwordReissueFailureSharedService;

    @Inject
    PasswordReissueMailSharedService mailSharedService;

    @Inject
    PasswordReissueInfoRepository passwordReissueInfoRepository;

    @Inject
    FailedPasswordReissueRepository failedPasswordReissueRepository;

    @Inject
    AccountSharedService accountSharedService;

    @Inject
    PasswordEncoder passwordEncoder;

    @Inject
    PasswordGenerator passwordGenerator;

    @Resource(name = "passwordGenerationRules")
    List<CharacterRule> passwordGenerationRules;

    @Value("${security.tokenLifeTimeSeconds}")
    int tokenLifeTimeSeconds;

    @Value("${app.host}")
    String host;

    @Value("${app.port}")
    String port;

    @Value("${app.contextPath}")
    String contextPath;

    @Value("${app.passwordReissueProtocol}")
    String protocol;

    @Value("${security.tokenValidityThreshold}")
    int tokenValidityThreshold;

    @Override
    public String createAndSendReissueInfo(String username) {
        String rowSecret = passwordGenerator.generatePassword(10,
                passwordGenerationRules);

        if (!accountSharedService.exists(username)) {
            return rowSecret;
        }

        AccountRoles accountRoles = accountSharedService.findOne(username);

        String token = UUID.randomUUID().toString();

        LocalDateTime expiryDate = dateFactory.newTimestamp().toLocalDateTime()
                .plusSeconds(tokenLifeTimeSeconds);

        PasswordReissueInfo info = new PasswordReissueInfo();
        info.setUsername(username);
        info.setToken(token);
        info.setSecret(passwordEncoder.encode(rowSecret));
        info.setExpiryDate(expiryDate);

        passwordReissueInfoRepository.insert(info);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        uriBuilder.scheme(protocol).host(host).port(port).path(contextPath)
                .pathSegment("reissue").pathSegment("resetpassword")
                .queryParam("form").queryParam("token", info.getToken());
        String passwordResetUrl = uriBuilder.build().toString();

        mailSharedService.send(accountRoles.getEmail(), passwordResetUrl);

        return rowSecret;
    }

    @Override
    @Transactional(readOnly = true)
    public PasswordReissueInfo findOne(String token) {
        PasswordReissueInfo info = passwordReissueInfoRepository.selectByPrimaryKey(token);

        if (info == null) {
            throw new ResourceNotFoundException(ResultMessages.error().add(
                    MessageKeys.E_SL_PR_5002, token));
        }

        if (dateFactory.newTimestamp().toLocalDateTime()
                .isAfter(info.getExpiryDate())) {
            throw new BusinessException(ResultMessages.error().add(
                    MessageKeys.E_SL_PR_2001));
        }

        FailedPasswordReissueExample example = new FailedPasswordReissueExample();
        example.or().andTokenEqualTo(token);

        long count = failedPasswordReissueRepository.countByExample(example);
        if (count >= tokenValidityThreshold) {
            throw new BusinessException(ResultMessages.error().add(
                    MessageKeys.E_SL_PR_5004));
        }

        return info;
    }

    @Override
    public boolean resetPassword(String username, String token, String secret, String rawPassword) {
        PasswordReissueInfo info = this.findOne(token);
        if (!passwordEncoder.matches(secret, info.getSecret())) {
            passwordReissueFailureSharedService.resetFailure(username, token);
            throw new BusinessException(ResultMessages.error().add(
                    MessageKeys.E_SL_PR_5003));
        }

        FailedPasswordReissueExample example = new FailedPasswordReissueExample();
        example.or().andTokenEqualTo(token);
        failedPasswordReissueRepository.deleteByExample(example);
        passwordReissueInfoRepository.deleteByPrimaryKey(token);

        return accountSharedService.updatePassword(username, rawPassword);
    }

    @Override
    public boolean removeExpired(LocalDateTime date) {

        // SELECT token FROM password_reissue_info WHERE expiry_date < #{date}
        PasswordReissueInfoExample expiryDateExample = new PasswordReissueInfoExample();
        expiryDateExample.or().andExpiryDateLessThan(date);
        List<PasswordReissueInfo> passwordReissueInfoList =
                passwordReissueInfoRepository.selectByExample(expiryDateExample);

        // DELETE FROM failed_password_reissue WHERE expiry_date in (password_reissue_info.token)
        List<String> deleteTokens =
                passwordReissueInfoList.stream().map(c -> c.getToken()).collect(Collectors.toList());
        FailedPasswordReissueExample example = new FailedPasswordReissueExample();
        example.or().andTokenIn(deleteTokens);
        failedPasswordReissueRepository.deleteByExample(example);

        // DELETE FROM password_reissue_info WHERE expiry_date < #{date}
        passwordReissueInfoRepository.deleteByExample(expiryDateExample);

        return true;
    }
}
