package com.chonchul.lecture.application.exception;

import com.chonchul.common.exception.BusinessException;

public class NotFoundLecture extends BusinessException {
    public NotFoundLecture() {
        super(LectureErrorCode.NOT_FOUND_LECTURE);
    }
}
