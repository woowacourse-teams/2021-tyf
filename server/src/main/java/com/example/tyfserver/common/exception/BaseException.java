package com.example.tyfserver.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final String errorCode;

    public BaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
