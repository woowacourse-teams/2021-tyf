package com.thankyou_for.auth.exception;

import com.thankyou_for.common.exception.BaseException;

public class InvalidOauth2TypeException extends BaseException {

    public static final String ERROR_CODE = "auth-003";
    private static final String MESSAGE = "유효하지 않은 OAuth2 타입입니다.";

    public InvalidOauth2TypeException() {
        super(ERROR_CODE, MESSAGE);
    }
}
