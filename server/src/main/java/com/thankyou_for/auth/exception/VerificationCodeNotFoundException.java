package com.thankyou_for.auth.exception;

import com.thankyou_for.common.exception.BaseException;

public class VerificationCodeNotFoundException extends BaseException {

    public static final String ERROR_CODE = "refund-002";
    private static final String MESSAGE = "해당 주문번호로 발급된 인증번호가 없습니다.";

    public VerificationCodeNotFoundException() {
        super(ERROR_CODE, MESSAGE);
    }
}
