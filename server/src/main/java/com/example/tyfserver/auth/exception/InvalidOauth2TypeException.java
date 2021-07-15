package com.example.tyfserver.auth.exception;

import com.example.tyfserver.common.exception.BaseException;

public class InvalidOauth2TypeException extends BaseException {

    private static final String ERROR_CODE = "auth-003";
    private static final String MESSAGE = "유효하지 않은 OAuth2 타입입니다.";

    public InvalidOauth2TypeException() {
        super(ERROR_CODE, MESSAGE);
    }
}
