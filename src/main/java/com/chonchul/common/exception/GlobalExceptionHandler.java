package com.chonchul.common.exception;

import com.chonchul.common.response.FailureBody;
import com.chonchul.common.response.ResponseEntityGenerator;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<FailureBody> handleConflict(BusinessException e) {
        return ResponseEntityGenerator.fail(e.getErrorCode().getCode(), e.getErrorCode().getMessage(), e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<FailureBody> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return ResponseEntityGenerator.fail(
                e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
                HttpStatus.BAD_REQUEST);
    }
}
