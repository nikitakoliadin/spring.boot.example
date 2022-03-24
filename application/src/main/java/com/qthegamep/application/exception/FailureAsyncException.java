package com.qthegamep.application.exception;

import com.qthegamep.application.model.ErrorType;

public class FailureAsyncException extends GeneralServiceException {

    public FailureAsyncException(ErrorType errorType) {
        super(errorType);
    }
}
