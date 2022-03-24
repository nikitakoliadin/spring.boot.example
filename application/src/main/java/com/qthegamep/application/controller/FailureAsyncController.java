package com.qthegamep.application.controller;

import com.qthegamep.application.exception.FailureAsyncException;
import com.qthegamep.application.util.Constants;
import org.springframework.web.bind.annotation.RequestAttribute;

@FunctionalInterface
public interface FailureAsyncController {

    void failure(@RequestAttribute(name = Constants.REQUEST_ID_HEADER) String requestId) throws FailureAsyncException;
}
