package com.chonchul.auth.application.exception;

import com.chonchul.common.exception.BusinessException;

public class NotMatchCurrentPwException extends BusinessException {
    public NotMatchCurrentPwException() {
        super(AuthErrorCode.NOT_MATCH_CURRENTPW);
    }
}
