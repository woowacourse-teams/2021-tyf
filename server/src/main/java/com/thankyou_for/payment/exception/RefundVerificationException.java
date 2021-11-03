package com.thankyou_for.payment.exception;

import com.thankyou_for.common.exception.BaseException;

public class RefundVerificationException extends BaseException {

    public static final String ERROR_CODE = "payment-010";
    private static final String MESSAGE = "인증번호 확인 요청시에 넘기는 Request의 값이 유효한 값이 아닙니다.";

    public RefundVerificationException() {
        super(ERROR_CODE, MESSAGE);
    }
}
