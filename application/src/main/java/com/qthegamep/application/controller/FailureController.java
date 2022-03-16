package com.qthegamep.application.controller;

import com.qthegamep.application.exception.FailureException;

@FunctionalInterface
public interface FailureController {

    void failure() throws FailureException;
}
