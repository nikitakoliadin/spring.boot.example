package com.qthegamep.application.service;

import com.qthegamep.application.exception.FailureException;
import com.qthegamep.application.model.ErrorType;
import org.springframework.stereotype.Service;

@Service
public class FailureServiceImpl implements FailureService {

    @Override
    public void failure() throws FailureException {
        throw new FailureException(ErrorType.FAILURE_ERROR);
    }
}
