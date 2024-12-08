package com.chonchul.auth.application.exception;

import com.chonchul.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {
    TOKEN_PARSE_FAIL("40001", HttpStatus.UNAUTHORIZED, "토큰 검증 시 문제가 생겼습니다."),
    EMAIL_CODE_EXPIRE("4002", HttpStatus.BAD_REQUEST, "메일 인증 코드가 만료되었습니다."),
    INVALID_LOGIN_PASSWORD("4003", HttpStatus.BAD_REQUEST, "로그인을 다시 수행하십시오."),
    INVALID_SCHOOL_MAIL("4004", HttpStatus.BAD_REQUEST, "학교 메일을 사용해주십시오."),
    NOT_VERIFIED_MAIL("4005", HttpStatus.BAD_REQUEST, "메일 인증이 완료되지 않았습니다."),
    NOT_MATCH_NEWPW("4006", HttpStatus.BAD_REQUEST, "새 비밀번호와 확인 비밀번호가 일치하지 않습니다."),
    NOT_MATCH_CURRENTPW("4007", HttpStatus.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다."),
    NOT_MATCH_EMAIL("4008", HttpStatus.BAD_REQUEST,"현재 이메일 주소가 정확한지 확인해 주세요."),
    ALREADY_EXIST_USER("4009", HttpStatus.BAD_REQUEST, "이미 가입된 계정이 존재합니다.");

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
