package com.thankyou_for.auth.exception;

import com.thankyou_for.common.exception.BaseException;

public class AlreadyRegisteredException extends BaseException {

    public static final String ERROR_CODE = "auth-005";
    private static final String MESSAGE = " 로 이미 가입된 회원입니다.";

    public AlreadyRegisteredException(String oauth2type) {
        super(ERROR_CODE, oauth2type + MESSAGE);
    }
}
