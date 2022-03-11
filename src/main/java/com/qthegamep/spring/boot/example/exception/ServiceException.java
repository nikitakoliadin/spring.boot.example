package com.qthegamep.spring.boot.example.exception;

import com.qthegamep.spring.boot.example.model.ErrorType;

@FunctionalInterface
public interface ServiceException {

    ErrorType getErrorType();
}
