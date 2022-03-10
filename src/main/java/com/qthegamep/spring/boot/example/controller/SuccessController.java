package com.qthegamep.spring.boot.example.controller;

import com.qthegamep.spring.boot.example.dto.SuccessResponseDTO;
import com.qthegamep.spring.boot.example.util.Constants;
import org.springframework.web.bind.annotation.RequestAttribute;

@FunctionalInterface
public interface SuccessController {

    SuccessResponseDTO success(@RequestAttribute(name = Constants.REQUEST_ID_HEADER) String requestId);
}
