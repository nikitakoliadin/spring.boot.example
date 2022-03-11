package com.qthegamep.spring.boot.example.controller;

import com.qthegamep.spring.boot.example.exception.FailureException;

@FunctionalInterface
public interface FailureController {

    void failure() throws FailureException;
}
