package com.thankyou_for.payment.exception;

import com.thankyou_for.common.exception.BaseException;

public class RefundVerificationReadyException extends BaseException {

    public static final String ERROR_CODE = "payment-009";
    private static final String MESSAGE = "인증메일 보내기 요청 시에 넘기는 Request의 값이 유효한 값이 아닙니다.";

    public RefundVerificationReadyException() {
        super(ERROR_CODE, MESSAGE);
    }
}
