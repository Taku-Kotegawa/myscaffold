package com.example.domain.service.passwordreissue;

import com.example.domain.model.FailedPasswordReissue;
import com.example.domain.repository.passwordreissue.FailedPasswordReissueRepository;
import com.example.domain.repository.passwordreissue.PasswordReissueInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.date.ClassicDateFactory;

import javax.inject.Inject;

@Service
@Transactional
public class PasswordReissueFailureSharedServiceImpl implements PasswordReissueFailureSharedService {

    @Inject
    ClassicDateFactory dateFactory;

    @Inject
    FailedPasswordReissueRepository failedPasswordReissueRepository;

    @Inject
    PasswordReissueInfoRepository passwordReissueInfoRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void resetFailure(String username, String token) {
        FailedPasswordReissue event = new FailedPasswordReissue();
        event.setToken(token);
        event.setAttemptDate(dateFactory.newTimestamp().toLocalDateTime());
        failedPasswordReissueRepository.insert(event);

    }
}
