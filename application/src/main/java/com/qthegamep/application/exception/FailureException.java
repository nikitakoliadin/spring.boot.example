package com.qthegamep.application.exception;

import com.qthegamep.application.model.ErrorType;

public class FailureException extends GeneralServiceException {

    public FailureException(ErrorType errorType) {
        super(errorType);
    }
}
