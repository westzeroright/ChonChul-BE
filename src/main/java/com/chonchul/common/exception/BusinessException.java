package com.chonchul.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;
}
