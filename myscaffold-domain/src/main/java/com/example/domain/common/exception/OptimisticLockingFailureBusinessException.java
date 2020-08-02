package com.example.domain.common.exception;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;

public class OptimisticLockingFailureBusinessException extends BusinessException {
    public OptimisticLockingFailureBusinessException(ResultMessages messages) {
        super(messages);
    }
}
