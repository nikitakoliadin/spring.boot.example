package com.qthegamep.spring.boot.example.model;

public enum ErrorType {

    FAILURE_ERROR(501);

    private final int errorCode;

    ErrorType(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
