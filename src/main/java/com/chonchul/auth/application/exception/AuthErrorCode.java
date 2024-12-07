package com.chonchul.auth.application.exception;

import com.chonchul.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {
    TOKEN_PARSE_FAIL("40001", HttpStatus.BAD_REQUEST, "토큰 검증 시 문제가 생겼습니다."),
    EMAIL_CODE_EXPIRE("4002", HttpStatus.BAD_REQUEST, "메일 인증 코드가 만료되었습니다."),
    INVALID_LOGIN_PASSWORD("4003", HttpStatus.BAD_REQUEST, "로그인을 다시 수행하십시오.");
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
