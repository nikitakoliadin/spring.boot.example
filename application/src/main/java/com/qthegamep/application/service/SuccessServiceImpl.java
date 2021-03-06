package com.qthegamep.application.service;

import com.qthegamep.application.dto.SuccessResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RefreshScope
public class SuccessServiceImpl implements SuccessService {

    private final String springApplicationName;
    private final String serverIp;
    private final String dockerImageName;

    @Autowired
    public SuccessServiceImpl(@Value("${spring.application.name}") String springApplicationName,
                              @Value("${server.ip}") String serverIp,
                              @Value("${docker.image.name}") String dockerImageName) {
        this.springApplicationName = springApplicationName;
        this.serverIp = serverIp;
        this.dockerImageName = dockerImageName;
    }

    @Override
    public SuccessResponseDTO success(String requestId) {
        SuccessResponseDTO successResponseDTO = new SuccessResponseDTO();
        successResponseDTO.setNow(new Date());
        successResponseDTO.setRequestId(requestId);
        successResponseDTO.setApplicationName(springApplicationName);
        successResponseDTO.setServerIp(serverIp);
        successResponseDTO.setDockerImageName(dockerImageName);
        return successResponseDTO;
    }
}
