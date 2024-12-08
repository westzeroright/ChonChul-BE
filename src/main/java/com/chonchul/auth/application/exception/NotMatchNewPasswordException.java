package com.chonchul.auth.application.exception;

import com.chonchul.common.exception.BusinessException;

public class NotMatchNewPasswordException extends BusinessException {
    public NotMatchNewPasswordException() {
        super(AuthErrorCode.NOT_MATCH_NEWPW);
    }
}
