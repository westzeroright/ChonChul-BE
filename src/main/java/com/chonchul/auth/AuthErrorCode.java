package com.chonchul.auth;

import com.chonchul.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {
    TOKEN_PARSE_FAIL("40001", HttpStatus.BAD_REQUEST,"토큰 검증 시 문제가 생겼습니다.");
    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    AuthErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
