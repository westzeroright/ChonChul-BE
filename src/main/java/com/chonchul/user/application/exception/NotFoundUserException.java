package com.chonchul.user.application.exception;

import com.chonchul.common.exception.BusinessException;

public class NotFoundUserException extends BusinessException {
    public NotFoundUserException() {super(UserErrorCode.NOT_FOUND_USER);};
}
