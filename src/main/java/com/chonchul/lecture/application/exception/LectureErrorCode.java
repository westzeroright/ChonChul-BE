package com.chonchul.lecture.application.exception;

import com.chonchul.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum LectureErrorCode implements ErrorCode {
    NOT_FOUND_LECTURE("2001", HttpStatus.NOT_FOUND, "강의를 찾을 수 없습니다."),
    NOT_FOUND_SESSION("3001", HttpStatus.NOT_FOUND, "해당 일자의 수업을 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    LectureErrorCode(String code, HttpStatus httpStatus, String message) {
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
