package com.qthegamep.application.controller;

import com.qthegamep.application.util.Constants;
import org.springframework.web.bind.annotation.RequestAttribute;

@FunctionalInterface
public interface SuccessAsyncController {

    void successAsync(@RequestAttribute(name = Constants.REQUEST_ID_HEADER) String requestId);
}
