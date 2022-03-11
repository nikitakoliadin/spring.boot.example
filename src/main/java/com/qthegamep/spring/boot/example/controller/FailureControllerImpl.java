package com.qthegamep.spring.boot.example.controller;

import com.qthegamep.spring.boot.example.exception.FailureException;
import com.qthegamep.spring.boot.example.service.FailureService;
import com.qthegamep.spring.boot.example.util.Paths;
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
