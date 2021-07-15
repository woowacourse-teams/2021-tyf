package com.example.tyfserver.auth.exception;

import com.example.tyfserver.common.exception.BaseException;

public class AlreadyRegisteredException extends BaseException {

    private static final String ERROR_CODE = "auth-005";
    private static final String MESSAGE = " 로 이미 가입된 회원입니다.";

    public AlreadyRegisteredException(String oauth2type) {
        super(ERROR_CODE, oauth2type + MESSAGE);
    }
}
