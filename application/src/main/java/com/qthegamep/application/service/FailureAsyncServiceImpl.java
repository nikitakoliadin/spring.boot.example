package com.qthegamep.application.service;

import com.qthegamep.application.exception.FailureAsyncException;
import com.qthegamep.application.model.ErrorType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class FailureAsyncServiceImpl implements FailureAsyncService {

    @Async
    @Override
    public void failureAsync(String requestId) throws FailureAsyncException {
        throw new FailureAsyncException(ErrorType.FAILURE_ASYNC_ERROR);
    }
}
