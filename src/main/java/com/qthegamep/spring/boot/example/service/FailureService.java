package com.qthegamep.spring.boot.example.service;

import com.qthegamep.spring.boot.example.exception.FailureException;

@FunctionalInterface
public interface FailureService {

    void failure() throws FailureException;
}
