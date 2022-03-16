package com.qthegamep.application.service;

import com.qthegamep.application.exception.FailureException;

@FunctionalInterface
public interface FailureService {

    void failure() throws FailureException;
}
