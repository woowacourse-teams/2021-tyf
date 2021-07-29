package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;

public class PaymentNotFoundException extends BaseException {

    public static final String ERROR_CODE = "payment-006";
    private static final String MESSAGE = "해당 회원을 찾을 수 없습니다.";

    public PaymentNotFoundException() {
        super(ERROR_CODE, MESSAGE);
    }
}
