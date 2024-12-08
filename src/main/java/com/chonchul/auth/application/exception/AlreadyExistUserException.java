package com.chonchul.auth.application.exception;

import com.chonchul.common.exception.BusinessException;

public class AlreadyExistUserException extends BusinessException {
    public AlreadyExistUserException() {
        super(AuthErrorCode.ALREADY_EXIST_USER);
    }
}
