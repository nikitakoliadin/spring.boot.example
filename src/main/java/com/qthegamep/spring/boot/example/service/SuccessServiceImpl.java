package com.qthegamep.spring.boot.example.service;

import com.qthegamep.spring.boot.example.dto.SuccessResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SuccessServiceImpl implements SuccessService {

    @Override
    public SuccessResponseDTO success(String requestId) {
        SuccessResponseDTO successResponseDTO = new SuccessResponseDTO();
        successResponseDTO.setNow(new Date());
        successResponseDTO.setRequestId(requestId);
        return successResponseDTO;
    }
}
