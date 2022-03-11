package com.qthegamep.spring.boot.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class ShutdownHookConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ShutdownHookConfig.class);

    @PreDestroy
    public void onExit() {
        LOG.warn("Shutting down main server...");
        LOG.info("Main server stopped!");
    }
}
