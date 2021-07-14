package com.example.tyfserver.auth.exception;

import com.example.tyfserver.common.exception.BaseException;

public class NotValidTokenException extends BaseException {

    private static final String ERROR_CODE = "auth-002";
    private static final String MESSAGE = "유효하지 않은 토큰입니다.";

    public NotValidTokenException() {
        super(ERROR_CODE, MESSAGE);
    }
}
