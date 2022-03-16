package com.qthegamep.application.service;

import com.qthegamep.application.dto.SuccessResponseDTO;

@FunctionalInterface
public interface SuccessService {

    SuccessResponseDTO success(String requestId);
}
