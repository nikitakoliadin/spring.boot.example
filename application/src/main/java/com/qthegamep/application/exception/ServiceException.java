package com.qthegamep.application.exception;

import com.qthegamep.application.model.ErrorType;

@FunctionalInterface
public interface ServiceException {

    ErrorType getErrorType();
}
