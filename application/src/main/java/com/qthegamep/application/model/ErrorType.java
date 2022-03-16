package com.qthegamep.application.model;

public enum ErrorType {

    INTERNAL_ERROR(500),
    FAILURE_ERROR(501);

    private final int errorCode;

    ErrorType(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
