package com.chonchul.auth.application.exception;

import com.chonchul.common.exception.BusinessException;

public class NotVerifiedMailException extends BusinessException {
    public NotVerifiedMailException() {
        super(AuthErrorCode.NOT_VERIFIED_MAIL);
    }
}
