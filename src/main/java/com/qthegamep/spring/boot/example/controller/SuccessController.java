package com.qthegamep.spring.boot.example.controller;

import com.qthegamep.spring.boot.example.dto.SuccessResponseDTO;
import com.qthegamep.spring.boot.example.util.Constants;
import org.springframework.web.bind.annotation.RequestHeader;

@FunctionalInterface
public interface SuccessController {

    SuccessResponseDTO success(@RequestHeader(name = Constants.REQUEST_ID_HEADER) String requestId);
}
