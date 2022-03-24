package com.qthegamep.application.service;

import com.qthegamep.application.exception.FailureAsyncException;

@FunctionalInterface
public interface FailureAsyncService {

    void failureAsync(String requestId) throws FailureAsyncException;
}
