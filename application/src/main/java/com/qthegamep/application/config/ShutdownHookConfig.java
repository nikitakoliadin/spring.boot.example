package com.qthegamep.application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Slf4j
@Configuration
public class ShutdownHookConfig {

    @PreDestroy
    public void onExit() {
        log.warn("Shutting down main server...");
        log.info("Main server stopped!");
    }
}
