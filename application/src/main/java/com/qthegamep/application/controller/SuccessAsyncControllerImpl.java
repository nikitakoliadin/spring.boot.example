package com.qthegamep.application.controller;

import com.qthegamep.application.service.SuccessAsyncService;
import com.qthegamep.application.util.Constants;
import com.qthegamep.application.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuccessAsyncControllerImpl implements SuccessAsyncController {

    private final SuccessAsyncService successAsyncService;

    @Autowired
    public SuccessAsyncControllerImpl(SuccessAsyncService successAsyncService) {
        this.successAsyncService = successAsyncService;
    }

    @Override
    @GetMapping(
            path = Paths.SUCCESS_ASYNC_PATH,
            produces = {MimeTypeUtils.APPLICATION_JSON_VALUE}
    )
    public void successAsync(@RequestAttribute(name = Constants.REQUEST_ID_HEADER) String requestId) {
        successAsyncService.successAsync(requestId);
    }
}
