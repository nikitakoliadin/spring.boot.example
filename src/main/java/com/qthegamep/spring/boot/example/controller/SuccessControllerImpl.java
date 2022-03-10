package com.qthegamep.spring.boot.example.controller;

import com.qthegamep.spring.boot.example.dto.SuccessResponseDTO;
import com.qthegamep.spring.boot.example.service.SuccessService;
import com.qthegamep.spring.boot.example.util.Constants;
import com.qthegamep.spring.boot.example.util.MediaTypeVersions;
import com.qthegamep.spring.boot.example.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
            produces = {MediaTypeVersions.VERSION_1, MediaTypeVersions.DEFAULT_VERSION}
    )
    public SuccessResponseDTO success(@RequestHeader(name = Constants.REQUEST_ID_HEADER) String requestId) {
        return successService.success(requestId);
    }
}
