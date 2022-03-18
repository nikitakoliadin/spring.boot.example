package com.qthegamep.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
public class GenerationServiceImpl implements GenerationService {

    @Override
    public String generateUniqueId(Long length) {
        StringBuilder uniqueId = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; ++i) {
            boolean upperCase = secureRandom.nextBoolean();
            char symbol = (char) (secureRandom.nextInt(26) + 97);
            if (upperCase) {
                symbol = Character.toUpperCase(symbol);
            }
            uniqueId.append(symbol);
        }
        log.debug("Generated unique ID: {}", uniqueId);
        return uniqueId.toString();
    }
}
