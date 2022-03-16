package com.qthegamep.application.exception;

import com.qthegamep.application.model.ErrorType;

public abstract class GeneralServiceException extends Exception implements ServiceException {

    private final ErrorType errorType;

    GeneralServiceException(ErrorType errorType) {
        this.errorType = errorType;
    }

    @Override
    public ErrorType getErrorType() {
        return errorType;
    }
}
