package com.qthegamep.spring.boot.example.service;

import com.qthegamep.spring.boot.example.dto.SuccessResponseDTO;

@FunctionalInterface
public interface SuccessService {

    SuccessResponseDTO success(String requestId);
}
