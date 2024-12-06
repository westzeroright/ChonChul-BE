package com.chonchul.auth.application.exception;

import com.chonchul.common.exception.BusinessException;

public class TokenParsingException extends BusinessException {
    public TokenParsingException() {
        super(AuthErrorCode.TOKEN_PARSE_FAIL);
    }
}
