package com.example.tyfserver.auth.exception;

import com.example.tyfserver.common.exception.BaseException;

public class UnregisteredMemberException extends BaseException {

    private static final String ERROR_CODE = "auth-006";
    private static final String MESSAGE = "가입되어 있지 않은 사용자입니다.";

    public UnregisteredMemberException() {
        super(ERROR_CODE, MESSAGE);
    }
}
