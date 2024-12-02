package com.chonchul.auth.token;

import com.chonchul.auth.AuthErrorCode;
import com.chonchul.common.exception.BusinessException;

public class TokenParsingException extends BusinessException {
    public TokenParsingException() {
        super(AuthErrorCode.TOKEN_PARSE_FAIL);
    }
}
