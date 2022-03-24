package com.qthegamep.application.controller;

import com.qthegamep.application.exception.FailureAsyncException;
import com.qthegamep.application.service.FailureAsyncService;
import com.qthegamep.application.util.Constants;
import com.qthegamep.application.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FailureAsyncControllerImpl implements FailureAsyncController {

    private final FailureAsyncService failureAsyncService;

    @Autowired
    public FailureAsyncControllerImpl(FailureAsyncService failureAsyncService) {
        this.failureAsyncService = failureAsyncService;
    }

    @Override
    @GetMapping(
            path = Paths.FAILURE_ASYNC_PATH,
            produces = {MimeTypeUtils.APPLICATION_JSON_VALUE}
    )
    public void failure(@RequestAttribute(name = Constants.REQUEST_ID_HEADER) String requestId) throws FailureAsyncException {
        failureAsyncService.failureAsync(requestId);
    }
}
