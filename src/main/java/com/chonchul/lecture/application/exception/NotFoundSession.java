package com.chonchul.lecture.application.exception;

import com.chonchul.common.exception.BusinessException;

public class NotFoundSession extends BusinessException {
    public NotFoundSession() {
        super(LectureErrorCode.NOT_FOUND_SESSION);
    }
}
