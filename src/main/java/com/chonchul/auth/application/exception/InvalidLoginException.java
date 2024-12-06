package com.chonchul.auth.application.exception;

import com.chonchul.common.exception.BusinessException;

public class InvalidLoginException extends BusinessException {
    public InvalidLoginException() {
        super(AuthErrorCode.INVALID_LOGIN_PASSWORD);
    }
}
