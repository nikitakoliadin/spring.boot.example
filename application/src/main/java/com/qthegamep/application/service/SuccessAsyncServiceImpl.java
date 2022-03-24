package com.qthegamep.application.service;

import com.qthegamep.application.dto.SuccessAsyncResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RefreshScope
public class SuccessAsyncServiceImpl implements SuccessAsyncService {

    private final String springApplicationName;
    private final String serverIp;
    private final String dockerImageName;

    @Autowired
    public SuccessAsyncServiceImpl(@Value("${spring.application.name}") String springApplicationName,
                                   @Value("${server.ip}") String serverIp,
                                   @Value("${docker.image.name}") String dockerImageName) {
        this.springApplicationName = springApplicationName;
        this.serverIp = serverIp;
        this.dockerImageName = dockerImageName;
    }

    @Async
    @Override
    public void successAsync(String requestId) {
        SuccessAsyncResponseDTO successAsyncResponseDTO = new SuccessAsyncResponseDTO();
        successAsyncResponseDTO.setNow(new Date());
        successAsyncResponseDTO.setRequestId(requestId);
        successAsyncResponseDTO.setApplicationName(springApplicationName);
        successAsyncResponseDTO.setServerIp(serverIp);
        successAsyncResponseDTO.setDockerImageName(dockerImageName);
        log.info("Success async response DTO: {} RequestId: {}", successAsyncResponseDTO, requestId);
    }
}
