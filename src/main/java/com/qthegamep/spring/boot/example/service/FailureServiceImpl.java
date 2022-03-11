package com.qthegamep.spring.boot.example.service;

import com.qthegamep.spring.boot.example.exception.FailureException;
import com.qthegamep.spring.boot.example.model.ErrorType;
import org.springframework.stereotype.Service;

@Service
public class FailureServiceImpl implements FailureService {

    @Override
    public void failure() throws FailureException {
        throw new FailureException(ErrorType.FAILURE_ERROR);
    }
}
