package com.example.domain.common.exception;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;

public class InvalidArgumentBusinessException extends BusinessException {
    public InvalidArgumentBusinessException(ResultMessages messages) {
        super(messages);
    }
}

