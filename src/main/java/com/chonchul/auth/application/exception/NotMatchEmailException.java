package com.chonchul.auth.application.exception;

import com.chonchul.common.exception.BusinessException;

public class NotMatchEmailException extends BusinessException {
    public NotMatchEmailException() {
        super(AuthErrorCode.NOT_MATCH_EMAIL);
    }
}
