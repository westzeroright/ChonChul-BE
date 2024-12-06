package com.chonchul.auth.application.exception;

import com.chonchul.common.exception.BusinessException;

public class EmailCodeExpireException extends BusinessException {
    public EmailCodeExpireException() {
        super(AuthErrorCode.EMAIL_CODE_EXPIRE);
    }
}
