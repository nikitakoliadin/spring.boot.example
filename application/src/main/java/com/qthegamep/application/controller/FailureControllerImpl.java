package com.qthegamep.application.controller;

import com.qthegamep.application.exception.FailureException;
import com.qthegamep.application.service.FailureService;
import com.qthegamep.application.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FailureControllerImpl implements FailureController {

    private final FailureService failureService;

    @Autowired
    public FailureControllerImpl(FailureService failureService) {
        this.failureService = failureService;
    }

    @Override
    @GetMapping(
            path = Paths.FAILURE_PATH,
            produces = {MimeTypeUtils.APPLICATION_JSON_VALUE}
    )
    public void failure() throws FailureException {
        failureService.failure();
    }
}
