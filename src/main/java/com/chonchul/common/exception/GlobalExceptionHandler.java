package com.chonchul.common.exception;

import com.chonchul.common.response.FailureBody;
import com.chonchul.common.response.ResponseEntityGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<FailureBody> handleConflict(BusinessException e) {
        return ResponseEntityGenerator.fail(e.getErrorCode().getCode(), e.getErrorCode().getMessage(), e.getErrorCode().getHttpStatus());
    }
}
