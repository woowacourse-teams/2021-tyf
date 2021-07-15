package com.example.tyfserver.auth.exception;

import com.example.tyfserver.common.exception.BaseException;

public class SignUpRequestException extends BaseException {
    private static final String ERROR_CODE = "auth-007";
    private static final String MESSAGE = "배너 생성 시에 넘기는 Request의 값이 유효한 값이 아닙니다.";

    public SignUpRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
