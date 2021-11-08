package com.thankyou_for.auth.exception;

import com.thankyou_for.common.exception.BaseException;

public class SignUpRequestException extends BaseException {

    public static final String ERROR_CODE = "auth-007";
    private static final String MESSAGE = "회원가입 Request의 값이 유효한 값이 아닙니다.";

    public SignUpRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
