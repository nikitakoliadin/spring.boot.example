package com.qthegamep.spring.boot.example.exception;

import com.qthegamep.spring.boot.example.model.ErrorType;

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
