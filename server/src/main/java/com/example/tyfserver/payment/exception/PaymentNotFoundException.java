package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;

public class PaymentNotFoundException extends BaseException {

    public static final String ERROR_CODE = "payment-007";
    private static final String MESSAGE = "해당 결제을 찾을 수 없습니다.";

    public PaymentNotFoundException() {
        super(ERROR_CODE, MESSAGE);
    }
}
