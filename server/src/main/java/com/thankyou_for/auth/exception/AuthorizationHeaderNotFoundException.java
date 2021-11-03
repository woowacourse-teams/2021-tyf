package com.thankyou_for.auth.exception;

import com.thankyou_for.common.exception.BaseException;

public class AuthorizationHeaderNotFoundException extends BaseException {

    public static final String ERROR_CODE = "auth-001";
    private static final String MESSAGE = "Authorization 헤더를 찾을 수가 없습니다.";

    public AuthorizationHeaderNotFoundException() {
        super(ERROR_CODE, MESSAGE);
    }
}
