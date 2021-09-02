package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;

public class PaymentAlreadyCancelledException extends BaseException {

    public static final String ERROR_CODE = "payment-013";
    private static final String MESSAGE = "이미 환불된 결제 건입니다.";

    public PaymentAlreadyCancelledException() {
        super(ERROR_CODE, MESSAGE);
    }
}
