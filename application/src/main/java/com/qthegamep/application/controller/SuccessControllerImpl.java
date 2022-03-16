package com.qthegamep.application.controller;

import com.qthegamep.application.dto.SuccessResponseDTO;
import com.qthegamep.application.service.SuccessService;
import com.qthegamep.application.util.Constants;
import com.qthegamep.application.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuccessControllerImpl implements SuccessController {

    private final SuccessService successService;

    @Autowired
    public SuccessControllerImpl(SuccessService successService) {
        this.successService = successService;
    }

    @Override
    @GetMapping(
            path = Paths.SUCCESS_PATH,
            produces = {MimeTypeUtils.APPLICATION_JSON_VALUE}
    )
    public SuccessResponseDTO success(@RequestAttribute(name = Constants.REQUEST_ID_HEADER) String requestId) {
        return successService.success(requestId);
    }
}
