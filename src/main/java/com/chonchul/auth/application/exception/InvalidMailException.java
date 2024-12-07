package com.chonchul.auth.application.exception;

import com.chonchul.common.exception.BusinessException;

public class InvalidMailException extends BusinessException {
    public InvalidMailException() {
        super(AuthErrorCode.INVALID_SCHOOL_MAIL);
    }
}
