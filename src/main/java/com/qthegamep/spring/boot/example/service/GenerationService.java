package com.qthegamep.spring.boot.example.service;

@FunctionalInterface
public interface GenerationService {

    String generateUniqueId(Long length);
}
