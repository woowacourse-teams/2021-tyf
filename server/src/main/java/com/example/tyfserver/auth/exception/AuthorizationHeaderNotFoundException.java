package com.example.tyfserver.auth.exception;

import com.example.tyfserver.common.exception.BaseException;

public class AuthorizationHeaderNotFoundException extends BaseException {

    private static final String ERROR_CODE = "auth-001";
    private static final String MESSAGE = "Authorization 헤더를 찾을 수가 없습니다.";

    public AuthorizationHeaderNotFoundException() {
        super(ERROR_CODE, MESSAGE);
    }
}
