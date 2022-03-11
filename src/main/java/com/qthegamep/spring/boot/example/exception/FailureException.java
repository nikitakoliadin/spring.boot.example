package com.qthegamep.spring.boot.example.exception;

import com.qthegamep.spring.boot.example.model.ErrorType;

public class FailureException extends GeneralServiceException {

    public FailureException(ErrorType errorType) {
        super(errorType);
    }
}
